package com.ShopEase.ShopEase.Service;

import com.ShopEase.ShopEase.Model.Product;
import com.ShopEase.ShopEase.Repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1, product2;

    @BeforeEach
    void setUp() {
        product1 = new Product(1L, "Laptop", new BigDecimal("999.99"), 10, "Gaming Laptop");
        product2 = new Product(2L, "Mouse", new BigDecimal("49.99"), 50, "Wireless Mouse");
    }

    @Test
    void testGetAllProducts_Success() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Laptop", products.get(0).getName());
        assertEquals("Mouse", products.get(1).getName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));

        Optional<Product> foundProduct = productService.getProductById(productId);

        assertTrue(foundProduct.isPresent());
        assertEquals(productId, foundProduct.get().getId());
        assertEquals("Laptop", foundProduct.get().getName());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductById_NotFound() {
        Long productId = 99L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> foundProduct = productService.getProductById(productId);

        assertFalse(foundProduct.isPresent());

        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testCreateProduct_Success() {
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product savedProduct = productService.createProduct(product1);

        assertNotNull(savedProduct);
        assertEquals("Laptop", savedProduct.getName());

        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void testUpdateProduct_Success() {
        Long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Laptop");
        updatedProduct.setPrice(new BigDecimal("899.99"));

        when(productRepository.findById(productId)).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Laptop", result.getName());
        assertEquals(new BigDecimal("899.99"), result.getPrice());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void testUpdateProduct_NotFound() {
        Long productId = 99L;
        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(new BigDecimal("100.00"));

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(productId, updatedProduct);
        });

        assertEquals("Product not found with id 99", exception.getMessage());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}
