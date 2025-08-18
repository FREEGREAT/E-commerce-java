package com.ecommetce.product.controller;



import com.ecommetce.product.dto.ProductRequest;
import com.ecommetce.product.dto.ProductResponse;
import com.ecommetce.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        productService.addProduct(productRequest);
        return new ResponseEntity<>("Product created",HttpStatus.CREATED);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> fetchAllProduct(){
        return  new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable Long id){
        boolean isDeleted = productService.deleteProduct(id);
        return  isDeleted ?  ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

}
