package com.example.apiweblaptop.controller;

import com.example.apiweblaptop.dto.*;
import com.example.apiweblaptop.exception.*;
import com.example.apiweblaptop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAll() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> listResponse = new ArrayList<>();

        try {
            List<ProductDTO> productDTOS = productService.retrieveProducts();
            List list = Collections.synchronizedList(new ArrayList(productDTOS));

            if (listResponse.addAll(list) == true) {
                response.setData(productDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_PRODUCT_SUCCESS);
        } catch (Exception e) {
            throw new GetDataFail("" + ErrorCode.GET_PRODUCT_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ResponseDTO> getPro(@PathVariable("product_id") Long id) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<ProductResponseDTO> productDTO = productService.getProduct(id);

            responseDTO.setData(productDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_PRODUCT_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_PRODUCT_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    // insert
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> createProduct(@RequestBody ProductDTO dto) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        System.out.println(dto);
        try{
            System.out.println("add product 1");

            ProductDTO productDTO = productService.saveProduct(dto);
            responseDTO.setData(productDTO);
            responseDTO.setSuccessCode(SuccessCode.ADD_PRODUCT_SUCCESS);
        }catch (Exception e)
        {
            System.out.println("add product err");

            throw new AddDataFail(e.getMessage()+""+ErrorCode.ADD_PRODUCT_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }

    // update

    @PutMapping("/put/{id}")
    public ResponseEntity<ResponseDTO> updatePro(@PathVariable(value = "id") Long id,  @RequestBody ProductDTO dtoRequest) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        System.out.println("put");
        try {
            ProductDTO updatePro = productService.updateProduct(id, dtoRequest);
            responseDTO.setData(updatePro);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_PRODUCT_SUCCESS);
            System.out.println("put success");
        } catch (Exception e){
            System.out.println("put error");

            throw new UpdateDataFail(""+ e.getMessage());
        }

        return ResponseEntity.ok(responseDTO);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable(value = "id") Long productId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = productService.deleteProduct(productId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_PRODUCT_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_PRODUCT_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

}
