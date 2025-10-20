package org.example.evaluations.productservice.controllers;

import jakarta.validation.Valid;
import org.example.evaluations.productservice.dtos.ProductRequestDto;
import org.example.evaluations.productservice.exceptions.ProductNotFoundException;
import org.example.evaluations.productservice.models.Product;
import org.example.evaluations.productservice.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

//    private ProductRequestDto productRequestDto;
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream()
                    .map(f -> f.getField() + ": " + f.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errors);
        }

//        if (productRequestDto.getCategoryId() == null) {
//            return ResponseEntity.badRequest().body("categoryId: must not be null");
//        }

        Product product = productService.createProduct(
                productRequestDto.getName(),
                productRequestDto.getDescription(),
                productRequestDto.getPrice(),
                productRequestDto.getCategoryId(),
                productRequestDto.getImage()
        );
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {

    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}

    /*
    We got an exception but we don't want to send the stack trace to the client
        1. We can handle the exception in the controller method itself
        2. We can have a global exception handler using @ControllerAdvice
        3. We can have a local exception handler using @ExceptionHandler
     */