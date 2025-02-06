package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trade.model.WatchList;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    WatchList findByUserId(Long userId);

}
