package com.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trade.model.Withdrawal;
import java.util.List;

public interface WithDrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByUserId(Long userId);

}
