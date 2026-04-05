package com.project.code.Controller;

import com.sun.source.tree.ModifiersTree;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * responsible for handling exceptions globally across all controllers.
 * It ensures that the application responds with meaningful error messages when an exception occurs, improving the user experience and maintaining consistent error handling.
 *  There's also more topics we can explore such as Custom Error MEssage handinling. REST validators and  SPring MVC Custom validation in Baeldung
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


// 2. Define the `handleJsonParseException` Method:
//    - Annotate with `@ExceptionHandler(HttpMessageNotReadableException.class)` to handle cases where the request body is not correctly formatted (e.g., invalid JSON).
//    - The `HttpMessageNotReadableException` typically occurs when the input data cannot be deserialized or is improperly formatted.
//    - Use `@ResponseStatus(HttpStatus.BAD_REQUEST)` to specify that the response status will be **400 Bad Request** when this exception is thrown.
//    - The method should return a `Map<String, Object>` with the following key:
//        - **`message`**: The error message should indicate that the input provided is invalid. The value should be `"Invalid input: The data provided is not valid."`.

    @ExceptionHandler(HttpMessageNotReadableException.class) //to handle cases where the request body is not correctly formatted (e.g., invalid JSON).
    @ResponseStatus(HttpStatus.BAD_REQUEST) //to specify that the response will have an HTTP status of 400 Bad Request when this exception is thrown.
    public Map<String, Object> handleJsonParseException(){
        String message;
        Map<String, Object> resultsMap=new HashMap<>();
        message="Invalid input: the data provided is not valid";
        resultsMap.put("message",message);
        return  resultsMap;


    }

}














