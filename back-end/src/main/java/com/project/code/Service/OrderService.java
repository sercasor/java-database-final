package com.project.code.Service;


import com.project.code.Model.*;
import com.project.code.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;


    //Methods
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequestDTO){

        //variables
        String customerEmail=placeOrderRequestDTO.getCustomerEmail();
        Customer customer;
        Optional<Store> optionalStore;
        Store store;
        Long storeID=placeOrderRequestDTO.getStoreId();
        Double totalPrice= placeOrderRequestDTO.getTotalPrice();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime dateTime = LocalDateTime.parse(placeOrderRequestDTO.getDatetime(), dateTimeFormatter);
        List<OrderItem> orderItems=new ArrayList<>();

        //(OrderDetails still not initialized) Retrieve or Create the Customer:Check if the customer already exists by their email using findByEmail. If the customer exists, use the existing customer, otherwise, create a new Customer and save it to the repository.

        if (customerRepository.findByEmail(customerEmail)!=null){
            customer=customerRepository.findByEmail(customerEmail);
        }else {
            customer=new Customer();
            customer.setEmail(placeOrderRequestDTO.getCustomerEmail());
            customer.setName(placeOrderRequestDTO.getCustomerName());
            customer.setPhone(placeOrderRequestDTO.getCustomerPhone());
        }

        customerRepository.save(customer);

        //Retrieve the Store:Fetch the store by ID from storeRepository. If the store doesn't exist, throw an exception.
        optionalStore=storeRepository.findById(storeID);
        store=optionalStore.orElseThrow(); //gets the value. Sends Exception otherwise


        //Create OrderDetails:Create a new OrderDetails object and set customer, store, total price, and the current datetime.
        OrderDetails orderDetails=new OrderDetails(customer, store,  totalPrice,  dateTime);

        //Create and Save OrderItems:For each product purchased, find the corresponding Inventory, update its stock level, and save the changes.
        List<PurchaseProductDTO>  purchaseProductDTOS=placeOrderRequestDTO.getPurchaseProduct();
        purchaseProductDTOS.forEach(purchaseProductDTO->{
            Inventory savedInventory=this.inventoryRepository.findByProductIdandStoreId(purchaseProductDTO.getId(),placeOrderRequestDTO.getStoreId());
            int stockLevel=savedInventory.getStockLevel();
            savedInventory.setStockLevel(stockLevel-purchaseProductDTO.getQuantity());
            this.inventoryRepository.save(savedInventory);

            Optional<Product> product=this.productRepository.findById(purchaseProductDTO.getId());
            Integer purchasedQuantity=purchaseProductDTO.getQuantity();
            Double price=purchaseProductDTO.getPrice();
            OrderItem orderItem=new OrderItem(orderDetails,  product.orElseThrow(), purchasedQuantity, price);
            this.orderItemRepository.save(orderItem);
            orderItems.add(orderItem);

        });

        //Create OrderItem for each product and associate it with the OrderDetails.
        if (!orderItems.isEmpty()){
            orderDetails.setOrderItems(orderItems);
            this.orderDetailsRepository.save(orderDetails);
        }else {
            throw new RuntimeException("OrderDetails has no OrderItems associated");
        }



//


    }

}



































// 1. **saveOrder Method**:
//    - Processes a customer's order, including saving the order details and associated items.
//    - Parameters: `PlaceOrderRequestDTO placeOrderRequest` (Request data for placing an order)
//    - Return Type: `void` (This method doesn't return anything, it just processes the order)

// 2. **Retrieve or Create the Customer**:
//    - Check if the customer exists by their email using `findByEmail`.
//    - If the customer exists, use the existing customer; otherwise, create and save a new customer using `customerRepository.save()`.

// 3. **Retrieve the Store**:
//    - Fetch the store by ID from `storeRepository`.
//    - If the store doesn't exist, throw an exception. Use `storeRepository.findById()`.

// 4. **Create OrderDetails**:
//    - Create a new `OrderDetails` object and set customer, store, total price, and the current timestamp.
//    - Set the order date using `java.time.LocalDateTime.now()` and save the order with `orderDetailsRepository.save()`.

// 5. **Create and Save OrderItems**:
//    - For each product purchased, find the corresponding inventory, update stock levels, and save the changes using `inventoryRepository.save()`.
//    - Create and save `OrderItem` for each product and associate it with the `OrderDetails` using `orderItemRepository.save()`.

