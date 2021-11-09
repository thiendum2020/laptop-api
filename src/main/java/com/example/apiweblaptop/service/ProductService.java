package com.example.apiweblaptop.service;

import com.example.apiweblaptop.dto.ProductDTO;
import com.example.apiweblaptop.dto.ProductResponseDTO;
import com.example.apiweblaptop.exception.BadRequestException;
import com.example.apiweblaptop.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<ProductDTO> retrieveProducts();


    public ProductDTO saveProduct(ProductDTO productDTO) throws ResourceNotFoundException, BadRequestException;

    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws ResourceNotFoundException;

    public Boolean deleteProduct(Long productId) throws ResourceNotFoundException;

    public Optional<ProductResponseDTO> getProduct(Long productId) throws ResourceNotFoundException;
}
