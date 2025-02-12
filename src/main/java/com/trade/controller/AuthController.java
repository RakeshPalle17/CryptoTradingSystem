package com.trade.controller;

import com.trade.config.JwtProvider;
import com.trade.model.TwoFactorOTP;
import com.trade.model.User;
import com.trade.repository.UserRepository;
import com.trade.response.AuthResponse;
import com.trade.service.CustomUserDetailsService;
import com.trade.service.EmailService;
import com.trade.service.TwoFactorOTPService;
import com.trade.service.WatchListService;
import com.trade.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private TwoFactorOTPService twoFactorOTPService;
    @Autowired
    private EmailService emailService;

    @Autowired
    private WatchListService watchListService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail());

        if (isEmailExist != null) {
            throw new Exception("Email address is already used");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());

        User savedUser = userRepository.save(newUser);
        watchListService.createWatchList(savedUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("register success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepository.findByEmail(userName);

        if (user.getTwoFactorAuth().isEnabled()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("TwoFactorAuth is Enabled");
            authResponse.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOTP();

            TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.findByUserId(authUser.getId());

            if (oldTwoFactorOTP != null) {
                twoFactorOTPService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }

            TwoFactorOTP newTwoFactorOTP = twoFactorOTPService.createTwoFactorOtp(authUser, otp, jwt);

            authResponse.setSession(newTwoFactorOTP.getId());

            emailService.sendVerificationOTPEmail(userName, otp);

            return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("Login success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid user name");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userName, password, userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigninOTP(@PathVariable String otp, @RequestParam String id)
            throws Exception {

        TwoFactorOTP twoFactorOTP = twoFactorOTPService.findById(id);

        if (twoFactorOTPService.verifyTwoFactorOtp(twoFactorOTP, otp)) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two Factor Authentication (2FA) is Successful");
            authResponse.setJwt(twoFactorOTP.getJwt());
            authResponse.setTwoFactorAuthEnabled(true);

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }
}
