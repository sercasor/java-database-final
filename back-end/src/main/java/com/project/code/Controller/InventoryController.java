package com.project.code.Controller;

import com.project.code.Model.CombinedRequest;
import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ServiceClass serviceClass; //validates product IDs and inventory
    Logger logger = LoggerFactory.getLogger(InventoryController.class);

    //TODO:this method requires reviewing
    @PutMapping()//is the path REQURIED?
    @Transactional
    public Map<String, String> updateInventory (@RequestBody CombinedRequest combinedRequest){//contains both a Product and Inventory
        Product requestProduct=combinedRequest.getProduct();
        Inventory requestInventory=combinedRequest.getInventory();
        boolean isValidProduct=false;
        boolean isValidInventory=false;
        Map<String, String> results=new HashMap<>();
        String message;
        Optional<Product> productRepo;
        Inventory inventoryRepo;

        try {
            if(this.serviceClass.ValidateProductId(requestProduct.getId())){
                isValidProduct=true;
                productRepo= this.productRepository.findById(requestProduct.getId());
                this.productRepository.save(requestProduct);
                message="Successfully updated product";
                results.put("message",message);
                logger.info(message);
            }else{
                message="Product not found in database";
                logger.error(message);
                results.put("message",message);
            }


            if (!this.serviceClass.validateInventory(requestInventory)){
                message="No data available for this product or store ID";
                results.put("message",message);
                logger.error(message);
            }else{
                inventoryRepo=this.serviceClass.getInventoryId(requestInventory);
                /*Another option that seems unnecessary*/
//                inventoryRepo.setId(requestInventory.getId());
//                inventoryRepo.setProduct(requestInventory.getProduct());
//                inventoryRepo.setStockLevel(requestInventory.getStockLevel());
//                inventoryRepo.setStore(requestInventory.getStore());
//                this.inventoryRepository.save(inventoryRepo);
                this.inventoryRepository.save(requestInventory);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            results.put("message",e.getMessage());
        }


        return results;
    }



    @PostMapping()
    public Map<String, String> saveInventory(@RequestBody Inventory inventory){
        boolean inventoryIsValid;
        Map<String, String> results= new HashMap<>();
        String message;
        try {
        if (!this.serviceClass.validateInventory(inventory)){
            message="Inventory doesn't exist";
            this.logger.warn(message);


                this.inventoryRepository.save(inventory);
                message="data saved successfully";
                results.put("message",message);
            }
        else {
            message="the data is already pressent";
            logger.error(message);
            results.put("message",message);
        }

        }
        catch (Exception e) {
            logger.error(e.getMessage());
            results.put("message",e.getMessage());
        }

        return  results;
    }

    @GetMapping("/{storeid}")
    public Map<String, Object>getAllProducts(@RequestBody Inventory inventory,@PathVariable Long storeid){

        Map<String, Object> productsMap=new HashMap<>();
        List<Product> allProductsList=this.productRepository.findAllByStoreId(inventory.getStore().getId());
        productsMap.put("products",allProductsList);
        return productsMap;
    }

    @GetMapping("filter/{category}/{name}/{storeid}")
    public Map<String, Object>getProductName(
            @RequestBody Product product,
            @PathVariable String category,
            @PathVariable String name,
            @PathVariable Long storeid){
        List<Product> productList;
        Map<String, Object> resultsMap=new HashMap<>();
        if (category==null){
            productList=this.productRepository.findByNameLike(name,storeid);
            resultsMap.put("product",productList);
        }else if (name==null){
            productList=this.productRepository.findByCategoryAndStoreId(category,storeid);

            resultsMap.put("product",productList);
        }else{
            productList=this.productRepository.findByNameAndCategory(name,category);
            resultsMap.put("product",productList);
        }
        return resultsMap;
    }


}






























// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to indicate that this is a REST controller, which handles HTTP requests and responses.
//    - Use `@RequestMapping("/inventory")` to set the base URL path for all methods in this controller. All endpoints related to inventory will be prefixed with `/inventory`.


// 2. Autowired Dependencies:
//    - Autowire necessary repositories and services:
//      - `ProductRepository` will be used to interact with product data (i.e., finding, updating products).
//      - `InventoryRepository` will handle CRUD operations related to the inventory.
//      - `ServiceClass` will help with the validation logic (e.g., validating product IDs and inventory data).


// 3. Define the `updateInventory` Method:
//    - This method handles HTTP PUT requests to update inventory for a product.
//    - It takes a `CombinedRequest` (containing `Product` and `Inventory`) in the request body.
//    - The product ID is validated, and if valid, the inventory is updated in the database.
//    - If the inventory exists, update it and return a success message. If not, return a message indicating no data available.


// 4. Define the `saveInventory` Method:
//    - This method handles HTTP POST requests to save a new inventory entry.
//    - It accepts an `Inventory` object in the request body.
//    - It first validates whether the inventory already exists. If it exists, it returns a message stating so. If it doesn’t exist, it saves the inventory and returns a success message.


// 5. Define the `getAllProducts` Method:
//    - This method handles HTTP GET requests to retrieve products for a specific store.
//    - It uses the `storeId` as a path variable and fetches the list of products from the database for the given store.
//    - The products are returned in a `Map` with the key `"products"`.


// 6. Define the `getProductName` Method:
//    - This method handles HTTP GET requests to filter products by category and name.
//    - If either the category or name is `"null"`, adjust the filtering logic accordingly.
//    - Return the filtered products in the response with the key `"product"`.


// 7. Define the `searchProduct` Method:
//    - This method handles HTTP GET requests to search for products by name within a specific store.
//    - It uses `name` and `storeId` as parameters and searches for products that match the `name` in the specified store.
//    - The search results are returned in the response with the key `"product"`.


// 8. Define the `removeProduct` Method:
//    - This method handles HTTP DELETE requests to delete a product by its ID.
//    - It first validates if the product exists. If it does, it deletes the product from the `ProductRepository` and also removes the related inventory entry from the `InventoryRepository`.
//    - Returns a success message with the key `"message"` indicating successful deletion.


// 9. Define the `validateQuantity` Method:
//    - This method handles HTTP GET requests to validate if a specified quantity of a product is available in stock for a given store.
//    - It checks the inventory for the product in the specified store and compares it to the requested quantity.
//    - If sufficient stock is available, return `true`; otherwise, return `false`.
