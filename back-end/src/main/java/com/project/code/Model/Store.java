package com.project.code.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

public class Store {
    /*-------------Private Attributes-------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //could also be a Long (Wrapper)
    @NotBlank(message = "Store name cannot be empty or null")
    private String name;
    @NotBlank(message = "Store address cannot be empty or null")
    private String address;
    @OneToMany(mappedBy = "store")
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









































// 1. Add 'id' field:
//    - Type: private long
//    - This field will be auto-incremented.
//    - Use @Id to mark it as the primary key.
//    - Use @GeneratedValue(strategy = GenerationType.IDENTITY) to auto-increment it.

// 2. Add 'name' field:
//    - Type: private String
//    - This field cannot be empty, use the @NotNull annotation to enforce this rule.

// 3. Add 'address' field:
//    - Type: private String
//    - This field cannot be empty, use the @NotNull and @NotBlank annotations to enforce this rule.

// 4. Add relationships:
//    - **Inventory**: A store can have multiple inventory entries.
//    - Use @OneToMany(mappedBy = "store") to reflect the one-to-many relationship with Inventory.
//    - Use @JsonManagedReference("inventory-store") to manage bidirectional relationships and avoid circular references.

// 5. Add constructor:
//    - Create a constructor that accepts name and address as parameters to initialize the Store object.

// 6. Add @Entity annotation:
//    - Use @Entity above the class name to mark it as a JPA entity.

// 7. Add Getters and Setters:
//    - Add getter and setter methods for all fields (id, name, address).