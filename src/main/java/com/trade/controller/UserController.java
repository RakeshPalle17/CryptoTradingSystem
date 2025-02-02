package com.trade.controller;

import com.trade.request.ForgetPasswordTokenRequest;
import com.trade.domain.VerificationType;
import com.trade.model.ForgetPasswordToken;
import com.trade.model.User;
import com.trade.model.VerificationCode;
import com.trade.request.ResetPasswordRequest;
import com.trade.response.APIResponse;
import com.trade.response.AuthResponse;
import com.trade.service.EmailService;
import com.trade.service.ForgetPasswordService;
import com.trade.service.UserService;
import com.trade.service.VerificationCodeService;
import com.trade.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verification-type}/send-otp")
    public ResponseEntity<String> sendVerificationOTP(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable VerificationType verificationType) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if(verificationCode == null){
            verificationCodeService.sendVerificationCode(user, verificationType);
        }

        if(verificationType.equals(VerificationType.EMAIL)){
            assert verificationCode != null;
            emailService.sendVerificationOTPEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<String>("Verification Code sent Successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());
        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail():verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if(isVerified){
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);

        }
        throw new Exception("Incorrect OPT Provided");
    }


    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<String> sendForgetPasswordOTP(@RequestBody ForgetPasswordTokenRequest request) throws Exception {

        User user = userService.findUserByEmail(request.getSentTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgetPasswordToken token = forgetPasswordService.findByUser(user.getId());

        if(token == null){
            token = forgetPasswordService.createToken(user, id, otp,
                    request.getVerificationType(), request.getSentTo());
        }

        if(request.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOTPEmail(user.getEmail(),token.getOtp());
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setSession(token.getId());
        authResponse.setMessage("Password Reset OTP Sent Successfully");

        return new ResponseEntity<String>("Forget Password OTP sent Successfully", HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<APIResponse> resetPassword(@RequestParam String id,
                                                     @RequestBody ResetPasswordRequest request,
                                                     @RequestHeader("Authorization") String jwt) throws Exception {

        ForgetPasswordToken forgetPasswordToken = forgetPasswordService.findById(id);
        boolean isVerified = forgetPasswordToken.getOtp().equals(request.getOtp());

        if (isVerified){
            userService.updatePassword(forgetPasswordToken.getUser(), request.getPassword());
            APIResponse response = new APIResponse();
            response.setMessage("Password update successful");
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }

        throw new Exception("Incorrect OPT Provided");
    }

}
