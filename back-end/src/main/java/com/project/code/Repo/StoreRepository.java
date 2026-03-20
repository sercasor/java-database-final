package com.project.code.Repo;

import com.project.code.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,Long> {

    /**
     * Retrieve stores whose name contains a given substring.
     * @param name
     * @return
     */
    public List<Store> findByNameLikeIgnoreCase(String name);

    // public List<Store> findById(Long ID);
//  shouldn't be needed, just here for reference in case de ID generic is problematic so overriding the method is required
   

}























// 1. Add the repository interface:
//    - Extend JpaRepository<Store, Long> to inherit basic CRUD functionality.
//    - This allows the repository to perform operations like save, delete, update, and find without having to implement these methods manually.

// Example: public interface StoreRepository extends JpaRepository<Store, Long> {}

// 2. Add custom query methods:
//    - **findById**:
//      - This method will retrieve a store by its ID.
//      - Return type: Store
//      - Parameter: Long id

// Example: public Store findById(Long id);

//    - **findBySubName**:
//      - This method will retrieve stores whose name contains a given substring.
//      - Return type: List<Store>
//      - Parameter: String pname
//      - Use @Query annotation to write a custom query.