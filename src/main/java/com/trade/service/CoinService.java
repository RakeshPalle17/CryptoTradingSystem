package com.trade.service;

import java.util.List;

import com.trade.model.Coin;

public interface CoinService {

    List<Coin> getCoinList(int page);

    String getMarketChart(String coinId, int days);

    String getCoinDetails(String coinId);

    Coin findById(String coinId);

    String searchCoin(String keyword);

    String getTop50ByMarketCapRank();

    String getTrendingCoins();
           
}