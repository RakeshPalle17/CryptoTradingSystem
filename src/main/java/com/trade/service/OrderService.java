package com.trade.service;

import java.util.List;

import com.trade.domain.OrderType;
import com.trade.model.Coin;
import com.trade.model.Order;
import com.trade.model.OrderItem;
import com.trade.model.User;


public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId);

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user);

}
