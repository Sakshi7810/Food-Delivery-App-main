package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;
import com.example.demo.repositories.OrderRepository;

@Service
public class OrderServices {

    @Autowired
    private OrderRepository orderRepository;

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders saveOrder(Orders order) {
        // Calculate total amount automatically
        order.setTotalAmount(order.getoPrice() * order.getoQuantity());
        return orderRepository.save(order);
    }

    public Orders updateOrder(int id, Orders orderDetails) {
        Orders existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        existingOrder.setoName(orderDetails.getoName());
        existingOrder.setoPrice(orderDetails.getoPrice());
        existingOrder.setoQuantity(orderDetails.getoQuantity());
        existingOrder.setOrderDate(orderDetails.getOrderDate());
        existingOrder.setTotalAmount(orderDetails.getoPrice() * orderDetails.getoQuantity());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }

    public List<Orders> getOrdersForUser(User user) {
        return orderRepository.findByUser(user);
    }
}
