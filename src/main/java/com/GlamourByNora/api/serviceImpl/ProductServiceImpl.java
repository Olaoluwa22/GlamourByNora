package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.dto.ProductDto;
import com.GlamourByNora.api.dto.ProductResponseDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
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
        product.setProductionDate(productDto.getProductionDate());
        product.setExpiryDate(product.getExpiryDate());
        product.setAvailability(productDto.isAvailability());
        product.setCountryOfOrigin(productDto.getCountryOfOrigin());
        productRepository.save(product);
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
            return new ResponseEntity<>(product,HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public ResponseEntity<?> getProductByPageable(int page, int size){
        ApiResponseMessages<List<ProductResponseDto>> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Pageable pageable = PageRequest.of(page, size);
        try {
            Page<Product> allProduct = productRepository.findAll(pageable);
            List<ProductResponseDto> response = new ArrayList<>();
            allProduct.forEach(product -> {
                ProductResponseDto productResponseDto = new ProductResponseDto();
                productResponseDto.setName(product.getName());
                productResponseDto.setBrand(product.getBrand());
                productResponseDto.setFragranceFamily(product.getFragranceFamily());
                productResponseDto.setSize(product.getSize());
                productResponseDto.setType(product.getType());
                productResponseDto.setGender(product.getGender());
                productResponseDto.setDescription(product.getDescription());
                productResponseDto.setPrice(product.getPrice());
                productResponseDto.setStockQuantity(product.getStockQuantity());
                productResponseDto.setIngredients(product.getIngredients());
                productResponseDto.setImageUrl(product.getImageUrl());
                productResponseDto.setProductionDate(product.getProductionDate());
                productResponseDto.setExpiryDate(product.getExpiryDate());
                productResponseDto.setAvailability(product.isAvailability());
                productResponseDto.setCountryOfOrigin(product.getCountryOfOrigin());
                response.add(productResponseDto);
            });
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            apiResponseMessages.setData(response);
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);

            } catch(Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> updateProductById(Long productId, ProductDto productDto, HttpServletRequest request) {
        appSecurityService.getLoggedInUser(request);
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
    public ResponseEntity<?> deleteProductById(Long productId, HttpServletRequest request) {
        appSecurityService.getLoggedInUser(request);
        productRepository.deleteById(productId);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.DELETED.getMessage());
        return new ResponseEntity<>(apiResponseMessages,HttpStatus.OK);
    }
}
