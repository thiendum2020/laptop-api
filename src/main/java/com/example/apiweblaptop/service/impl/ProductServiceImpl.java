package com.example.apiweblaptop.service.impl;


import com.example.apiweblaptop.dto.ErrorCode;
import com.example.apiweblaptop.dto.ProductDTO;
import com.example.apiweblaptop.dto.ProductResponseDTO;
import com.example.apiweblaptop.entity.Brand;
import com.example.apiweblaptop.entity.Category;
import com.example.apiweblaptop.entity.Product;
import com.example.apiweblaptop.exception.BadRequestException;
import com.example.apiweblaptop.exception.ResourceNotFoundException;
import com.example.apiweblaptop.repo.BrandRepository;
import com.example.apiweblaptop.repo.CategoryRepository;
import com.example.apiweblaptop.repo.ProductRepository;
import com.example.apiweblaptop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BrandRepository branchRepository;


    @Override
    public List<ProductDTO> retrieveProducts() {
        List<Product> products = productRepository.findAll();
        return new ProductDTO().entityToDTO(products);
    }

    @Override
    public Optional<ProductResponseDTO> getProduct(Long productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));
        return Optional.of(new ProductResponseDTO().convertToDto(product));
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) throws ResourceNotFoundException, BadRequestException {
        Product product = new ProductDTO().dtoToEntity(productDTO);

        Category category = categoryRepository.findById(productDTO.getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Loi category"));

        product.setCategory(category);

        Brand branch = branchRepository.findById(productDTO.getBrand_id()).orElseThrow(() -> new ResourceNotFoundException("Loi branch"));
        product.setBrand(branch);
        product.setQty(0);
        try {
            product =productRepository.save(product);
        }
        catch (Exception e) {
            throw new BadRequestException("Invalid request"+ e.getMessage());
        }
        return new ProductDTO().entityToDTO(productRepository.findById(product.getId()).orElseThrow(()-> new ResourceNotFoundException("Them that bai")));
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws ResourceNotFoundException {
        Product proExist = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));

        Category category = categoryRepository.findById(productDTO.getCategory_id()).orElseThrow(() ->
                new ResourceNotFoundException(""+ ErrorCode.FIND_CATEGORY_ERROR));

        Brand branch = branchRepository.findById(productDTO.getBrand_id()).orElseThrow(()->
                new ResourceNotFoundException(""+ErrorCode.FIND_BRAND_ERROR));
        proExist.setProductName(productDTO.getProduct_name());
        proExist.setPrice(productDTO.getProduct_price());
        proExist.setDiscount(productDTO.getProduct_discount());
        proExist.setDescription(productDTO.getProduct_description());
        proExist.setBrand(branch);
        proExist.setCategory(category);

        Product product = new Product();
        product = productRepository.save(proExist);

        return new ProductDTO().entityToDTO(product);
    }
    @Override
    public Boolean deleteProduct(Long productId) throws ResourceNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR));
        this.productRepository.delete(product);
        return true;
    }

}
