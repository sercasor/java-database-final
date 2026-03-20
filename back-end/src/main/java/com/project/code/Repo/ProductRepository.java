package com.project.code.Repo;

import com.project.code.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    public List<Product> findAll();
    public List<Product> findByCategory(String category);
    public List<Product> findByPriceBetween(double MinPrice, double MaxPrice); //query annotation should not be needed as o JOINs are used
    public List<Product> findBySku(String sku);
    public List<Product> findByName(String name);

    /**
     * Find products by a name pattern for a specific store
     * @param name (pattern)
     * @param id
     * @return List of Product products
     */
    @Query("SELECT i.product FROM Inventory i WHERE  i.product.category = :category AND i.store.id = :storeId")
    public List<Product> findByNameLike(@Param("name")String name,@Param("id") Long id);

    @Query("select i.product from Inventory i where i.product.category = :category and i.product.id = :id")//important: this is JPQL. see spring data JPA "Using Named Parameters". Parameters are marked by the colon (:) and i represents the object. we ca access its properties as if it was an object instead of creating traditional SQL querries with JOIN
    public List<Product> findByCategoryAndStoreId(@Param("category") String category, @Param("id") Long id);


    /**
     * Find products by name and category for a specific store.
     * @param id
     * @param name
     * @param category
     * @return
     */
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND LOWER(i.product.name) LIKE LOWER(CONCAT('%', :pname, '%')) AND i.product.category = :category")  //using CONCAT is a good practice cause you'd have to add '%' when you call the method. Some kind of concatenation is required so % can be used as wildcards for any number of characters. LIKE expects a string. An example would be productRepository.findByStoreAndCategory(storeId, "%" + nombre + "%", category);
    public List<Product> findByNameAndCategory(@Param("id") Long id,@Param("name") String name, @Param("category") String category);


    //DOING

    /**
     * Find products by category for a specific store.
     * @param id
     * @param category
     * @return
     */
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId AND i.product.category = :category")
    public List<Product> findByCategoryAndStoreId(@Param("id") Long id, @Param("category") String category);

    /**
     * Find products by a name pattern (ignoring case).
     * @param name
     * @return
     */
    public List<Product> findByNameIgnoreCase(String name);

    /**
     * Find all products for a specific store.
     * @param id
     * @return
     */
    @Query("SELECT i.product FROM Inventory i WHERE i.store.id = :storeId")
    public List<Product> findAllByStoreId(@Param("id") Long id);

    /**
     * Find products by a name pattern and category.
     * @param name
     * @param category
     * @return
     */
    public List<Product> findByNameAndCategory(String name, String category);

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