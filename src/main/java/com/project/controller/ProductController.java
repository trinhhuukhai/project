package com.project.controller;

import com.project.dto.request.ProductRequest;
import com.project.dto.response.AllResponse;
import com.project.model.Customer;
import com.project.model.Product;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/getAllProduct")
    private AllResponse<List<Product>> getAll(){
        List<Product> allProduct = productService.getAllProduct();
        return new AllResponse<>(allProduct.size(), allProduct);
    }
//    List<Product> getAll(){
//        return (List<Product>) productService.getAllProduct();
//    }

    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping(value = {"/insert"})
    ResponseEntity<ResponseResult> insertProduct( @ModelAttribute ProductRequest newPro) {
        return productService.insertProduct(newPro);

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updateProduct(@ModelAttribute ProductRequest newPro, @PathVariable Long id) {
        return productService.updateProduct(newPro,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/paginAndSort/{pageNumber}/{pageSize}")
    public AllResponse<Page<Product>> productPagination(@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
        Page<Product> productPagi = productService.getProductPagination(pageNumber, pageSize);
        return new AllResponse<>(productPagi.getSize(), productPagi);
//        return new AllResponse<>(productService.getProductPagination(pageNumber, pageSize));
    }
    @GetMapping("/paginAndSort2/{pageNumber}/{pageSize}/{field}")
    public AllResponse<Page<Product>> productPaginationAndSort(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, @PathVariable("filed") String field){
        Page<Product> productPagi = productService.getProductPaginationAndSort(pageNumber, pageSize, field);
        return new AllResponse<>(productPagi.getSize(), productPagi);
//        return new AllResponse<>(productService.getProductPagination(pageNumber, pageSize));
    }

}
