package com.trade.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.trade.domain.OrderStatus;
import com.trade.domain.OrderType;
import com.trade.model.Asset;
import com.trade.model.Coin;
import com.trade.model.Order;
import com.trade.model.OrderItem;
import com.trade.model.User;
import com.trade.repository.OrderItemRepository;
import com.trade.repository.OrderRepository;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private OrderService orderService;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price = orderItem.getCoin().getCurrentPrice() * orderItem.getQuantity();

        Order order = new Order();
        order.setUser(user);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setStatus(OrderStatus.PENDING);
        order.setOrderItem(orderItem);
        order.setTimestamp(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin, double quantity, double buyPrice, double sellPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);

        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAssest(Coin coin, double quantity, User user) {
        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        double buyPrice = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
        Order order = createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder = orderRepository.save(order);

        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
                order.getOrderItem().getCoin().getId());
        if (oldAsset != null) {
            assetService.updateAsset(oldAsset.getId(), quantity);
        } else {
            assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
        }

        return savedOrder;
    }

    @Transactional
    public Order sellAssest(Coin coin, double quantity, User user) {
        if (quantity <= 0) {
            throw new RuntimeException("Invalid quantity: quantity must be greater than 0");
        }

        double sellPrice = coin.getCurrentPrice();

        Asset assetToSell = assetService.findAssetByUserIdAndCoinId(user.getId(), coin.getId());
        double buyPrice = assetToSell.getBuyPrice();

        if (assetToSell != null) {

            OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
            Order order = createOrder(user, orderItem, OrderType.SELL);
            orderItem.setOrder(order);

            if (assetToSell.getQuantity() >= quantity) {
                order.setStatus(OrderStatus.SUCCESS);
                order.setOrderType(OrderType.SELL);
                Order savedOrder = orderRepository.save(order);

                walletService.payOrderPayment(order, user);
                Asset updateAsset = assetService.updateAsset(assetToSell.getId(), -quantity);

                if (updateAsset.getQuantity() * coin.getCurrentPrice() <= 1) {
                    assetService.deleteAsset(updateAsset.getId());
                }
                return savedOrder;
            }
            throw new RuntimeException("Insufficient quantity to sell");
        }
        throw new RuntimeException("Asset not found");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) {
        if (orderType == OrderType.BUY) {
            return buyAssest(coin, quantity, user);
        } else if (orderType == OrderType.SELL) {
            return sellAssest(coin, quantity, user);
        } else {
            throw new RuntimeException("Invalid order type");
        }
    }

}
