package com.project.code.Controller;

import com.project.code.Model.CombinedRequest;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    //----------------------PRIVATE ATTRIBUTES----------------------//

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ServiceClass serviceClass;

    @Autowired
    private InventoryRepository inventoryRepository;

    private Logger logger = LoggerFactory.getLogger(InventoryController.class);

    //----------------------METHODS----------------------//

    @PostMapping()
    @Transactional
    public Map<String, Object>addProduct(@RequestBody Product requestProduct){
        Map<String, Object> resultsMap=new HashMap<>();
        String message;
        try {
            if (this.serviceClass.validateProduct(requestProduct)){
                this.productRepository.save(requestProduct);
                message="Product got updated successfully";
                logger.info(message);
                resultsMap.put("message",message);
            }else {
                message="Invalid Product (not found), couldn't update";
                logger.info(message);
                resultsMap.put("message",message);
            }

        } catch (Exception e) {
            logger.error("Error trying to update Product: "+e.getMessage());        }
        return resultsMap;
    }

    @PutMapping()
    public Map<String, Object>  updateProduct(@RequestBody Product requestProduct){
        Map<String, Object> resultsMap=new HashMap<>();
        String message;
        try {

                this.productRepository.save(requestProduct);
                message="Product got updated successfully";
                logger.info(message);
                resultsMap.put("message",message);

        } catch (Exception e) {
            message="Error trying to update Product: ";
            logger.error(message+e.getMessage());
            resultsMap.put("message",message);
        }
        return resultsMap;

    }

    @GetMapping("/category/{name}/{category}")
    public Map<String, Object> filterbyCategoryProduct(
            @PathVariable String name,
            @PathVariable String category
    ){
        List<Product> productList;
        Map<String, Object> resultsMap=new HashMap<>();
        String message;

        if (name==null){
            productList=this.productRepository.findByCategory(category);
            logger.info("Name not provided, product list found via category provided");
            resultsMap.put("products",productList);

        }else if (category==null){
            productList=this.productRepository.findByName(name);
            logger.info("Category not provided, product list found via Name provided");
        } else {
            productList=this.productRepository.findByNameAndCategory(name,category);
            logger.info("Name and category provided, here's the product list found");
        }

        resultsMap.put("product",productList);
        return resultsMap;
    }
    @GetMapping()
    public Map<String, Object> listProduct(){

        List<Product> productList;
        Map<String, Object> resultsMap=new HashMap<>();
        productList=this.productRepository.findAll();
        if (productList.isEmpty()||productList==null){
            logger.info("No products found in the database");
        }else {
            logger.info("Products successfully found in the database");
        }
        resultsMap.put("product",productList);
        return resultsMap;
    }
    @GetMapping("filter/{category}/{storeid}")
    public Map<String, Object> getProductbyCategoryAndStoreId(
            @PathVariable String category,
            @PathVariable Long storeid

    ){

        List<Product> productList;
        Map<String, Object> resultsMap=new HashMap<>();

        productList=this.productRepository.findByCategoryAndStoreId(category,storeid);
        if (productList.isEmpty()||productList==null){
            logger.info("No products found for the provided category and store");
        }else {
            logger.info("Category Products successfully found in the store");
        }

        resultsMap.put("product",productList);
        return resultsMap;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public Map <String,String> deleteProduct(
            @PathVariable Long id
    ){
        Map<String, String> resultsMap=new HashMap<>();
        String message;
        try {
            if (this.serviceClass.ValidateProductId(id)){
                this.inventoryRepository.deleteByProductId(id);
                logger.info("Inventory deleted successfully");
                this.productRepository.deleteById(id);
                message="Product got deleted successfully";
                logger.info(message);
                resultsMap.put("message",message);
            }else {
                message=" Product not found, couldn't delete";
                logger.info(message);
                resultsMap.put("message",message);
            }

        } catch (Exception e) {
            logger.error("Error trying to delete Product: "+e.getMessage());        }
        return resultsMap;

    }
    
}







// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to designate it as a REST controller for handling HTTP requests.
//    - Map the class to the `/product` URL using `@RequestMapping("/product")`.


// 2. Autowired Dependencies:
//    - Inject the following dependencies via `@Autowired`:
//        - `ProductRepository` for CRUD operations on products.
//        - `ServiceClass` for product validation and business logic.
//        - `InventoryRepository` for managing the inventory linked to products.


// 3. Define the `addProduct` Method:
//    - Annotate with `@PostMapping` to handle POST requests for adding a new product.
//    - Accept `Product` object in the request body.
//    - Validate product existence using `validateProduct()` in `ServiceClass`.
//    - Save the valid product using `save()` method of `ProductRepository`.
//    - Catch exceptions (e.g., `DataIntegrityViolationException`) and return appropriate error message.


// 4. Define the `getProductbyId` Method:
//    - Annotate with `@GetMapping("/product/{id}")` to handle GET requests for retrieving a product by ID.
//    - Accept product ID via `@PathVariable`.
//    - Use `findById(id)` method from `ProductRepository` to fetch the product.
//    - Return the product in a `Map<String, Object>` with key `products`.


// 5. Define the `updateProduct` Method:
//    - Annotate with `@PutMapping` to handle PUT requests for updating an existing product.
//    - Accept updated `Product` object in the request body.
//    - Use `save()` method from `ProductRepository` to update the product.
//    - Return a success message with key `message` after updating the product.


// 6. Define the `filterbyCategoryProduct` Method:
//    - Annotate with `@GetMapping("/category/{name}/{category}")` to handle GET requests for filtering products by `name` and `category`.
//    - Use conditional filtering logic if `name` or `category` is `"null"`.
//    - Fetch products based on category using methods like `findByCategory()` or `findProductBySubNameAndCategory()`.
//    - Return filtered products in a `Map<String, Object>` with key `products`.


// 7. Define the `listProduct` Method:
//    - Annotate with `@GetMapping` to handle GET requests to fetch all products.
//    - Fetch all products using `findAll()` method from `ProductRepository`.
//    - Return all products in a `Map<String, Object>` with key `products`.


// 8. Define the `getProductbyCategoryAndStoreId` Method:
//    - Annotate with `@GetMapping("filter/{category}/{storeid}")` to filter products by `category` and `storeId`.
//    - Use `findProductByCategory()` method from `ProductRepository` to retrieve products.
//    - Return filtered products in a `Map<String, Object>` with key `product`.


// 9. Define the `deleteProduct` Method:
//    - Annotate with `@DeleteMapping("/{id}")` to handle DELETE requests for removing a product by its ID.
//    - Validate product existence using `ValidateProductId()` in `ServiceClass`.
//    - Remove product from `Inventory` first using `deleteByProductId(id)` in `InventoryRepository`.
//    - Remove product from `Product` using `deleteById(id)` in `ProductRepository`.
//    - Return a success message with key `message` indicating product deletion.


// 10. Define the `searchProduct` Method:
//    - Annotate with `@GetMapping("/searchProduct/{name}")` to search for products by `name`.
//    - Use `findProductBySubName()` method from `ProductRepository` to search products by name.
//    - Return search results in a `Map<String, Object>` with key `products`.
