package com.project.code.Controller;

import com.project.code.Model.Inventory;
import com.project.code.Model.PlaceOrderRequestDTO;
import com.project.code.Model.Product;
import com.project.code.Model.Store;
import com.project.code.Repo.StoreRepository;
import com.project.code.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class StoreController {

    //----------------------PRIVATE ATTRIBUTES----------------------//
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private OrderService orderService;
    private Logger logger = LoggerFactory.getLogger(StoreController.class);

    //----------------------METHODS (ENDPOINTS)----------------------//

    @PostMapping
    @Transactional
    public Map<String, String> addStore(@RequestBody Store requestStore){
        String message;
        Map<String, String> resultsMap=new HashMap<>();
        try {
                Optional<Store> repoStore=this.storeRepository.findById(requestStore.getId());
                if(repoStore.isEmpty()){//isEmpty also checks if null
                this.storeRepository.save(requestStore);
                message="Store created  successfully";
                logger.info(message);
                resultsMap.put("message",message);
            }else {
                message="Store already exists or there's an error, couldn't create it";
                logger.info(message);
                resultsMap.put("message",message);
            }

        } catch (Exception e) {
            logger.error("Error trying to create Store: "+e.getMessage());        }
        return resultsMap;
    }





    @GetMapping("validate/{storeId}")
    public boolean validateStore(
            @PathVariable Long storeId
    ){
        Optional<Store> repoStore=this.storeRepository.findById(storeId);
        boolean isValid;
        if(repoStore.isEmpty()){
            logger.error("Store not found in database");
            isValid=false;
        }else {
            logger.info("Store found in database");
            isValid=true;
        }
        return isValid;
    }


    @PostMapping
    @Transactional
    public Map<String, String> placeOrder(@RequestBody PlaceOrderRequestDTO placeOrderRequestDTO){
        String message;
        Map<String, String> resultsMap=new HashMap<>();
        try {
            Optional<Store> repoStore=this.storeRepository.findById(placeOrderRequestDTO.getStoreId());
            if(repoStore.isEmpty()){//isEmpty also checks if null. All validations should be already checked via orderService.saveOrder
                message="Error in StoreController: Store does not exist";
                logger.info(message);
                resultsMap.put("message",message);
            }else {
                this.orderService.saveOrder(placeOrderRequestDTO);
                message="Order placed successfully";
                logger.info(message);
                resultsMap.put("message",message);
            }

        } catch (Exception e) {
            message="Error trying to place order: "+e.getMessage();
            logger.error(message);
            resultsMap.put("message",message);
        }
        return resultsMap;
    }





}//class end





















