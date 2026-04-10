package com.project.code.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Store {
    /*-------------Private Attributes-------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //could also be a Long (Wrapper)
    @NotBlank(message = "Store name cannot be empty or null")
    private String name;
    @NotBlank(message = "Store address cannot be empty or null")
    private String address;
    @ManyToOne
    @JsonManagedReference("inventory-store")
    private Inventory inventory;

    /*-------------Constructor-------------*/

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}



































