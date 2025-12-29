package com.example.demo.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment in DB
    @Column(name = "o_id")
    private int oId;

    @Column(name = "o_name")
    private String oName;

    @Column(name = "o_price")
    private double oPrice;

    @Column(name = "o_quantity")
    private int oQuantity;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "total_amount")
    private double totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters & Setters
    public int getoId() { return oId; }
    public void setoId(int oId) { this.oId = oId; }

    public String getoName() { return oName; }
    public void setoName(String oName) { this.oName = oName; }

    public double getoPrice() { return oPrice; }
    public void setoPrice(double oPrice) { this.oPrice = oPrice; }

    public int getoQuantity() { return oQuantity; }
    public void setoQuantity(int oQuantity) { this.oQuantity = oQuantity; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Orders [oId=" + oId + ", oName=" + oName +
               ", totalAmount=" + totalAmount +
               ", orderDate=" + orderDate + "]";
    }
}
