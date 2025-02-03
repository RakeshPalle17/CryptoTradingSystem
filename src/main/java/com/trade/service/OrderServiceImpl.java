package com.trade.service;

import java.util.List;

import com.trade.domain.OrderType;
import com.trade.model.Coin;
import com.trade.model.Order;
import com.trade.model.OrderItem;
import com.trade.model.User;

public class OrderServiceImpl implements OrderService {
    
    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createOrder'");
    }

    @Override
    public Order getOrderById(Long orderId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderById'");
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrdersOfUser'");
    }

    @Override
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processOrder'");
    }

}
