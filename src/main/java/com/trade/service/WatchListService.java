package com.trade.service;

import com.trade.model.Coin;
import com.trade.model.User;
import com.trade.model.WatchList;

public interface WatchListService {
    WatchList findUserWatchList(Long userid);

    WatchList createWatchList(User user);

    WatchList findById(Long id);

    Coin addItemToWatchlist(Coin coin, User user);

}
