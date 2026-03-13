package com.project.code.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

/**
 * This class represents each record  of the total stock rather than the number of available units or the place in which they are stored (warehouse)
 */
@Entity
public class Product {

    /*-------------Private Attributes-------------*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //could also be a Long (Wrapper)

    @NotBlank(message = "Product name cannot be empty or null")
    private String name;
    @NotBlank(message = "Product category cannot be empty or null")
    private String category;
    @NotNull(message = "Price cannot be null")
    private Double price;

    /*
    Another option to point out this attribute should be unique (should not be required since it doesn't involve  multiple columns):
            Use the @Table annotation with a uniqueConstraints attribute to enforce uniqueness on the sku column. Example: `@Table(name = “product”,
            uniqueConstraints = @UniqueConstraint(columnNames = “sku”)) - Add@Entity` annotation above class name
     */
    @Column(unique = true)
    @NotBlank(message = "SKU is required,can't be blank")
    private String sku;

    @OneToMany(mappedBy = "product") //not  OneToOne cause Inventory represents  each record  of the total stock. In other words: A product can have multiple inventory entries
    @JsonManagedReference("inventory-product")
    private List<Inventory> inventory;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public List<Inventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }
}


