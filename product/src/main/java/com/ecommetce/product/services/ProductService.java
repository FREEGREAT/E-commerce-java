package com.ecommetce.product.services;


import com.ecommetce.product.dto.ProductRequest;
import com.ecommetce.product.dto.ProductResponse;
import com.ecommetce.product.models.Product;
import com.ecommetce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(ProductRequest productRequest){
        Product product = new Product();
        mapProductFromRequest(productRequest, product);
        productRepository.save(product);
    }

    public Optional<ProductResponse> getProductById(Long id){
        return productRepository.findByIdAndActiveTrue(id)
                .map(this::mapToProductResponse);
    }

    public List<ProductResponse> getProducts(){
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest){
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updateProductFromRequest(existingProduct, productRequest);
                    Product savedProduct = productRepository.save(existingProduct);
                    return mapToProductResponse(savedProduct);
                });
    }

    public boolean deleteProduct(Long id){
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword){
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    private  void updateProductFromRequest(Product product, ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setActive(productRequest.getActive());
    }
    private ProductResponse mapToProductResponse(Product product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(String.valueOf(product.getId()));
        productResponse.setName(product.getName());
        productResponse.setActive(product.getActive());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategory(product.getCategory());
        productResponse.setImageUrl(product.getImageUrl());
        return  productResponse;
    }

    private void  mapProductFromRequest(ProductRequest productRequest, Product product){
        product.setActive(productRequest.getActive());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setName(productRequest.getName());

    }

}
