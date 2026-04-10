package com.project.code.Repo;


import com.project.code.Model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review,String> {
    /**
     * Retrieve reviews for a specific product and store.
     * @param customerId
     * @param storeId
     * @return
     */
    public List<Review> findByProductIdAndStoreId(Long customerId, Long storeId);

}


