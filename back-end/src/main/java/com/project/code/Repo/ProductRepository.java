package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    public List<Product> findAll();
    public List<Product> findByCategory(String category);
    public List<Product> findByPriceBetween(Double price1, Double price2);
    public List<Product> findBySku(String sku);
    public List<Product> findByName(String name);

    /**
     * Find products by a name pattern for a specific store
     * @param pattern SQL pattern
     * @return List of Product products
     */
    public List<Product> findByNameLike(String pattern); //TODO: faltan cosas porque ?
    public List<Product> findByNameAndCategory(String name, String category);
    @Query("select p from Product p where u.emailAddress = ?1")
    public List<Product> findByCategoryAndStoreId(String category, Long id); //TODO: faltan cosas porque en Product no hay store, sino Iventory auque detro de este último sí tenemos un Store?

//    public List<Product> findById(Long id); //example suggests using this method but this is a reserved method in JPA so it got commented at least for now. Ref: docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html#:~:text=findById%28ID%20identifier




}










































// 1. Add the repository interface:
//    - Extend JpaRepository<Product, Long> to inherit basic CRUD functionality.
//    - This allows the repository to perform operations like save, delete, update, and find without having to implement these methods manually.

// Example: public interface ProductRepository extends JpaRepository<Product, Long> {}

// 2. Add custom query methods:
//    - **findAll**:
//      - This method will retrieve all products.
//      - Return type: List<Product>

// Example: public List<Product> findAll();

//    - **findByCategory**:
//      - This method will retrieve products by their category.
//      - Return type: List<Product>
//      - Parameter: String category

// Example: public List<Product> findByCategory(String category);

//    - **findByPriceBetween**:
//      - This method will retrieve products within a price range.
//      - Return type: List<Product>
//      - Parameters: Double minPrice, Double maxPrice

// Example: public List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

//    - **findBySku**:
//      - This method will retrieve a product by its SKU.
//      - Return type: Product
//      - Parameter: String sku

// Example: public Product findBySku(String sku);

//    - **findByName**:
//      - This method will retrieve a product by its name.
//      - Return type: Product
//      - Parameter: String name

// Example: public Product findByName(String name);

//    - **findByNameLike**:
//      - This method will retrieve products by a name pattern for a specific store.
//      - Return type: List<Product>
//      - Parameters: Long storeId, String pname
//      - Use @Query annotation to write a custom query.