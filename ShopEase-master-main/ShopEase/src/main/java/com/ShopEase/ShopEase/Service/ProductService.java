package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Exception.ProductNotFoundException;
import com.ShopEase.ShopEase.Model.Product;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Service Interface


// Service Implementation
@Service
@Transactional
public class  ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public Product createProduct(Product product) {
        return productRepository.save(product);
    }


    public Product updateProduct(Long id, Product productDetails) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        // Add other fields to update

        return productRepository.save(product);
    }

    public boolean deleteProduct(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        productRepository.delete(product);
        return false;
    }
}