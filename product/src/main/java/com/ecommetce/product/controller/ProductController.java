package com.ecommetce.product.controller;



import com.ecommetce.product.dto.ProductRequest;
import com.ecommetce.product.dto.ProductResponse;
import com.ecommetce.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/api/product")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        productService.addProduct(productRequest);
        return new ResponseEntity<>("Product created",HttpStatus.CREATED);
    };
    @GetMapping(path = "/api/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    };
    @GetMapping(path = "/api/product")
    public ResponseEntity<List<ProductResponse>> fetchAllProduct(){
        return  new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    };

    @PutMapping(path = "/api/product/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/api/product/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id){
        boolean isDeleted = productService.deleteProduct(id);
        return  isDeleted ?  ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/api/product/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

}
