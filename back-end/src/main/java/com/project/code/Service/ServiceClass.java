package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//this service class is used to validate inventory and products, and fetch related data.
@Service
public class ServiceClass {

    //-------------------------------------Attributes-------------------------------------

    @Autowired
    private  InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;

    //-------------------------------------Methods-------------------------------------
    //TODO
    public boolean validateInventory (Inventory inventory){
        boolean isValid=false;
        Inventory inventoryToValidate= this.inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(),inventory.getStore().getId());
        if(inventoryToValidate!=null){
            isValid=true;
        }


        return isValid;
    }

    //TODO
    public boolean validateProduct (Product product){
        boolean isValid=false;
        List <Product> productList= this.productRepository.findByName(product.getName());
        if(productList.size()>0){ //query methods return an empty list should they find no results in contrast to query methods with no collections as a return type according to docs.spring.io/spring-data/jpa/reference/repositories/null-handling.html
            isValid=true;
        }
        return isValid;
    }
    //TODO
     public boolean ValidateProductId (long id){
            Optional<Product> product=this.productRepository.findById(id);
            return product.isPresent(); //returns a boolean
        }
    //TODO
    public Inventory getInventoryId (Inventory inventory){
        return this.inventoryRepository.findByProductIdandStoreId(inventory.getProduct().getId(),inventory.getStore().getId());
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
