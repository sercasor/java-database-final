package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * this service class is used to validate inventory and products, and fetch related data.
 */
@Service
public class ServiceClass {

    //-------------------------------------Attributes-------------------------------------

    @Autowired
    private  InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    //-------------------------------------Methods-------------------------------------

    /**
     * Validates  that the product is not duplicate using Product ID and Store ID. Can be used for before adding a new inventory.
     * @param inventory (A) product unit(s) that belong(s) to a store
     * @return True if the inventory (product stock) already exists
     */
    public boolean validateInventory (Inventory inventory){
        boolean isValid=false;
        Inventory inventoryToValidate= this.inventoryRepository.findByProductIdAndStoreId(inventory.getProduct().getId(),inventory.getStore().getId());
        if(inventoryToValidate!=null){
            isValid=true;
        }


        return isValid;
    }

    /**
     * Validates if the product exists in the DB via its name.
     * @param product Product object, represents a product (not necessarily associated with a store)
     * @return True if the product exists
     */
    public boolean validateProduct (Product product){
        boolean isValid=false;
        List <Product> productList= this.productRepository.findByName(product.getName());
        if(productList.size()>0){ //query methods return an empty list should they find no results in contrast to query methods with no collections as a return type according to docs.spring.io/spring-data/jpa/reference/repositories/null-handling.html
            isValid=true;
        }
        return isValid;
    }

    /**
     * Checks if a product exists in our DB using its ID
     * @param id A product ID (not stock)
     * @return
     */
     public boolean ValidateProductId (long id){
            Optional<Product> product=this.productRepository.findById(id);
            return product.isPresent(); //returns a boolean
        }

    /**
     * Fetches an inventory from the database that belongs to a product and a specific store
     * @param inventory Inventory object that represents a product associated with a store
     * @return Returns an inventory (stock unit)
     */
    public Inventory getInventoryId (Inventory inventory){
        return this.inventoryRepository.findByProductIdAndStoreId(inventory.getProduct().getId(),inventory.getStore().getId());
    }


}
















// 1. **validateInventory Method**:
//    - Checks if an inventory record exists for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `boolean` (Returns `false` if inventory exists, otherwise `true`)

// 2. **validateProduct Method**:
//    - Checks if a product exists by its name.
//    - Parameters: `Product product`
//    - Return Type: `boolean` (Returns `false` if a product with the same name exists, otherwise `true`)

// 3. **ValidateProductId Method**:
//    - Checks if a product exists by its ID.
//    - Parameters: `long id`
//    - Return Type: `boolean` (Returns `false` if the product does not exist with the given ID, otherwise `true`)

// 4. **getInventoryId Method**:
//    - Fetches the inventory record for a given product and store combination.
//    - Parameters: `Inventory inventory`
//    - Return Type: `Inventory` (Returns the inventory record for the product-store combination)
