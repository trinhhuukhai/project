package com.project.controller;

import com.project.dto.request.ProductRequest;
import com.project.model.Customer;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/getAllProduct")
    List<Product> getAll(){
        return (List<Product>) productService.getAllProduct();
    }

    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping(value = {"/insert"})
    ResponseEntity<ResponseResult> insertProduct( @RequestBody ProductRequest newPro) {
        return productService.insertProduct(newPro);

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updateProduct(@RequestBody ProductRequest newCus, @PathVariable Long id) {
        return productService.updateProduct(newCus,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
