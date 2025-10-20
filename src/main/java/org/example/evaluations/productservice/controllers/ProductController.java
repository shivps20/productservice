package org.example.evaluations.productservice.controllers;

import jakarta.validation.Valid;
import org.example.evaluations.productservice.dtos.ProductRequestDto;
import org.example.evaluations.productservice.exceptions.ProductNotFoundException;
import org.example.evaluations.productservice.models.Product;
import org.example.evaluations.productservice.services.IProductService;
import org.example.evaluations.productservice.util.AuthUtil;
import org.springframework.http.HttpStatus;
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
    private final AuthUtil authUtil;

    public ProductController(IProductService productService,
                             AuthUtil authUtil) {
        this.authUtil = authUtil;
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

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {

    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * This method was used to get the product by its ID without any authentication
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(id);
        return ResponseEntity.ok(product);
    }

    /**
     * This method is used to get the product by its ID with token authentication
     * It first checks if the token is valid or not, if valid then it returns the product
     * otherwise it throws UnauthorizedException
     *
     * It will call the User Service to validate the token
     */
    @GetMapping("/{id}/{tokenValue}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id,
                                                    @PathVariable("tokenValue") String tokenValue) throws ProductNotFoundException {

        //1 - First validate the token by calling the User Service
        if(!authUtil.validateToken(tokenValue)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }

        Product product = productService.getSingleProduct(id);
        if(product == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }

        return ResponseEntity.ok(product);
    }
}

    /*
    We got an exception but we don't want to send the stack trace to the client
        1. We can handle the exception in the controller method itself
        2. We can have a global exception handler using @ControllerAdvice
        3. We can have a local exception handler using @ExceptionHandler
     */