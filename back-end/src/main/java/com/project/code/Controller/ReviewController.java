package com.project.code.Controller;


import com.project.code.Model.Product;
import com.project.code.Model.Review;
import com.project.code.Repo.CustomerRepository;
import com.project.code.Repo.ReviewRepository;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    //----------------------PRIVATE ATTRIBUTES----------------------//
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Logger logger = LoggerFactory.getLogger(ReviewController.class);

    //----------------------METHODS (ENDPOINTS)----------------------//

    /*
    Quick explanation: a list of objects is created, containing a review object (an object whose values are a simplified version of the Review that we get using the repo query method) and a String which is the customer name.
     This list is the value for the resultsMap, whose key is "customerName"
     */
    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(
            @PathVariable Long storeId,
            @PathVariable Long productId

    ){

        List<Review> reviewList;
        List<Object> curatedReviewList=new ArrayList<>();
        String customerName;
        Map<String, Object> resultsMap=new HashMap<>();

        reviewList=this.reviewRepository.findByproductIdAndstoreId(storeId,productId);
        if (reviewList.isEmpty()||reviewList==null){
            logger.info("No reviews found for the provided product and store");
        }else {
            logger.info("Products Reviews successfully found for this store");
        }

        for (Review review:reviewList){
            Review curatedReview=new Review();
            curatedReview.setComment(review.getComment());
            curatedReview.setRating(review.getRating());
            customerName=this.customerRepository.findById(review.getCustomerId()).get().getName();
            curatedReviewList.add(curatedReview);
            curatedReviewList.add(customerName);
            resultsMap.put("customerName",curatedReviewList);
        }


        resultsMap.put("reviews",reviewList);
        return resultsMap;




    }






}







// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to designate it as a REST controller for handling HTTP requests.
//    - Map the class to the `/reviews` URL using `@RequestMapping("/reviews")`.


// 2. Autowired Dependencies:
//    - Inject the following dependencies via `@Autowired`:
//        - `ReviewRepository` for accessing review data.
//        - `CustomerRepository` for retrieving customer details associated with reviews.


// 3. Define the `getReviews` Method:
//    - Annotate with `@GetMapping("/{storeId}/{productId}")` to fetch reviews for a specific product in a store by `storeId` and `productId`.
//    - Accept `storeId` and `productId` via `@PathVariable`.
//    - Fetch reviews using `findByStoreIdAndProductId()` method from `ReviewRepository`.
//    - Filter reviews to include only `comment`, `rating`, and the `customerName` associated with the review.
//    - Use `findById(review.getCustomerId())` from `CustomerRepository` to get customer name.
//    - Return filtered reviews in a `Map<String, Object>` with key `reviews`.

