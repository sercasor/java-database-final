package com.project.code.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * represents a customer's purchase. It ties together who bought what, from where, and when.
 *
 */
@Entity
public class OrderDetails {
    /*-------------Private Attributes-------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //could also be a Long (Wrapper)
    @ManyToOne
    @JsonManagedReference("customer-orderDetails")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JsonManagedReference("store-orderDetails")
    @JoinColumn(name = "store_id")
    private Store store;

    private Double totalPrice;
    private LocalDateTime date;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @JsonManagedReference("orderItem-orderDetails")
    private List<OrderItem> OrderItems;

    /*-------------Constructor-------------*/

    public OrderDetails() {
    }

    public OrderDetails(Customer customer, Store store, Double totalPrice, LocalDateTime date) {
        this.customer = customer;
        this.store = store;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    /*-------------Getters and setters-------------*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<OrderItem> getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        OrderItems = orderItems;
    }
}
























