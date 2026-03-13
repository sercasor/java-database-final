package com.project.code.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Customer {

    /*-------------Private Attributes-------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //the example suggested  NotNull
    @NotBlank(message = "Customer name cannot be empty or null")
    private String name;

    //the example suggested  using  NotNull, NotBlak sounds good as well
    @Email(message = "Customer email is invalid")
    private String email;

    //the example suggested  using  NotNull
    @NotBlank(message = "Customer phone cannot be empty or null")
    private String phone;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER) //Eager is a nice since the orderDetails and Customer will be frequently accessed together
    //ensures JSON serialization of related orders
    @JsonManagedReference //requires specific import
    private OrderDetails orderDetails; //OrderItem represents the actual item so there's no relationship between it and the Customer since the details contains the date and such

    /*-------------Getters and setters-------------*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

}

