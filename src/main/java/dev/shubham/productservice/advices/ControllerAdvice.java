package dev.shubham.productservice.advices;

import dev.shubham.productservice.dtos.ErrorDto;
import dev.shubham.productservice.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundExeption(ProductNotFoundException exception){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.valueOf(404));
    }
}
