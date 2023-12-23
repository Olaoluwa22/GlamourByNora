package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;
    private AppSecurityService appSecurityService;

    public ProductServiceImpl(ProductRepository productRepository, AppSecurityService appSecurityService){
        this.productRepository = productRepository;
        this.appSecurityService = appSecurityService;
    }
    @Override
    public ResponseEntity<?> createNewProduct(ProductDto productDto, HttpServletRequest request) {
        appSecurityService.getLoggedInUser(request);
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setFragranceFamily(productDto.getFragranceFamily());
        product.setSize(productDto.getSize());
        product.setType(productDto.getType());
        product.setGender(productDto.getGender());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setIngredients(productDto.getIngredients());
        product.setProductionDate();
        product.setExpiryDate();
        product.setAvailability(productDto.isAvailability());
        product.setCountryOfOrigin(productDto.getCountryOfOrigin());
        productRepository.save(product);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.CREATED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.CREATED);
    }

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<?> getProductById(Long productId) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            Optional<Product> byId = productRepository.findById(productId);
            if (byId.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            Product product = byId.get();
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            return new ResponseEntity<>(apiResponseMessages,HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateProductById(Long productId, ProductDto productDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Optional<Product> byId = productRepository.findById(productId);
        try {
            if (byId.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            Product product = byId.get();
            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setFragranceFamily(productDto.getFragranceFamily());
            product.setSize(productDto.getSize());
            product.setType(productDto.getType());
            product.setGender(productDto.getGender());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setStockQuantity(productDto.getStockQuantity());
            product.setImageUrl(productDto.getImageUrl());
            product.setIngredients(productDto.getIngredients());
            product.setProductionDate(productDto.getProductionDate());
            product.setExpiryDate(productDto.getExpiryDate());
            product.setAvailability(productDto.isAvailability());
            product.setCountryOfOrigin(productDto.getCountryOfOrigin());
            productRepository.save(product);
            apiResponseMessages.setMessage(ConstantMessages.UPDATED.getMessage());
            return new ResponseEntity<>(apiResponseMessages,HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
            return new ResponseEntity<>(apiResponseMessages,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteProductById(Long productId) {
        productRepository.deleteById(productId);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.DELETED.getMessage());
        return new ResponseEntity<>(apiResponseMessages,HttpStatus.OK);
    }
}