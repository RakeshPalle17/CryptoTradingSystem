package com.trade.service;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.model.Coin;
import com.trade.model.User;
import com.trade.model.WatchList;
import com.trade.repository.WatchListRepository;

@Service
public class WatchListServiceImpl implements WatchListService {

    @Autowired
    private WatchListRepository watchListRepository;

    @Override
    public WatchList findUserWatchList(Long userid) {
        WatchList watchList = watchListRepository.findByUserId(userid);
        if (watchList == null) {
            throw new RuntimeException("Watchlist not found");
        }
        return watchList;
    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList = new WatchList();
        watchList.setUser(user);
        return watchListRepository.save(watchList);
    }

    @Override
    public WatchList findById(Long id) {
        Optional<WatchList> watchList = watchListRepository.findById(id);
        if (watchList.isEmpty()) {
            throw new RuntimeException("Watchlist not found");
        }
        return watchList.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) {
        WatchList watchList = findUserWatchList(user.getId());
        if (watchList.getCoins().contains(coin)) {
            watchList.getCoins().remove(coin);
        } else {
            watchList.getCoins().add(coin);
        }
        watchListRepository.save(watchList);
        return coin;
    }

}
