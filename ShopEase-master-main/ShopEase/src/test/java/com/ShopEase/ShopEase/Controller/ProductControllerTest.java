package com.ShopEase.ShopEase.Controller;

import com.ShopEase.ShopEase.Model.Product;
import com.ShopEase.ShopEase.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product1, product2;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();

        product1 = new Product(1L, "Laptop", new BigDecimal("999.99"), 10, "Gaming Laptop");
        product2 = new Product(2L, "Mouse", new BigDecimal("49.99"), 50, "Wireless Mouse");
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> productList = Arrays.asList(product1, product2);
        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Mouse"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Success() throws Exception {
        Long productId = 1L;
        when(productService.getProductById(productId)).thenReturn(Optional.of(product1));

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(999.99));

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        Long productId = 99L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(product1);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.price").value(999.99));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        Long productId = 1L;
        Product updatedProduct = new Product(1L, "Updated Laptop", new BigDecimal("899.99"), 8, "Updated Gaming Laptop");

        when(productService.updateProduct(eq(productId), any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Laptop"))
                .andExpect(jsonPath("$.price").value(899.99));

        verify(productService, times(1)).updateProduct(eq(productId), any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() throws Exception {
        Long productId = 99L;
        Product updatedProduct = new Product(99L, "Updated Product", new BigDecimal("500.00"), 5, "Not Found Product");

        when(productService.updateProduct(eq(productId), any(Product.class)))
                .thenThrow(new RuntimeException("Product not found with id " + productId));

        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).updateProduct(eq(productId), any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        Long productId = 1L;
        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(productId);
    }
}
