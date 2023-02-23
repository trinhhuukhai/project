package com.project.service;

import com.project.dto.request.ProductRequest;
import com.project.model.Category;
import com.project.model.Product;
import com.project.repository.CategoryRepository;
import com.project.repository.ProductRepository;
import com.project.response.ResponseResult;
import com.project.service.iService.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageStorageService storageService;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }


    public ResponseEntity<ResponseResult> insertProduct( ProductRequest newPro) {
        List<Product> foundPro = productRepository.findByName(newPro.getName().trim());


        if(foundPro.size() > 0) {

            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseResult("failed", "Product name already taken", "")
            );
        }

        Category category = categoryRepository.findById(newPro.getCategoryId()).orElseThrow();

        Product product = new Product();
        product.setName(newPro.getName());
        product.setDescription(newPro.getDescription());
        product.setPrice(newPro.getPrice());
        product.setBrand(newPro.getBrand());
        product.setColor(newPro.getColor());
        product.setInventory(newPro.getInventory());
        product.setCategory(category);

        String productImage = storageService.storageFile(newPro.getProductImage());


        product.setProductImage(productImage);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Product successfully",productRepository.save(product))
        );


    }

//    public ResponseEntity<ResponseResult> updateProduct(@RequestBody ProductRequest newPro, @PathVariable Long id) {
//
//        Category category = categoryRepository.findById(newPro.getCategoryId()).orElseThrow();
//        Optional<Product> updatedPro = productRepository.findById(id)
//                .map(pro -> {
//                    pro.setName(newPro.getName());
//                    pro.setDescription(newPro.getDescription());
//                    pro.setPrice(newPro.getPrice());
//                    pro.setBrand(newPro.getBrand());
//                    pro.setColor(newPro.getColor());
//                    pro.setInventory(newPro.getInventory());
//                    pro.setCategory(category);
//                    pro.setProductImage(newPro.getProductImage());
//                    return productRepository.save(pro);
//                });
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseResult("ok", "Update Customer successfully", updatedPro)
//        );
//    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteProduct(@PathVariable Long id) {
        boolean exists = productRepository.existsById(id);
        if(exists) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete Customer successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find Customer to delete", "")
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query Customer successfully", foundProduct)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find Customer with id = "+id, "")
                );
    }

    public Page<Product> getProductPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productRepository.findAll(pageable);
    }

    public Page<Product> getProductPaginationAndSort(Integer pageNumber, Integer pageSize , String field) {
        Pageable pageable = null;
        if (null != field){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC,field);

        }else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC,"name");

        }
        return productRepository.findAll(pageable);
    }

        //Let's return an object with: data, message, status
    //Optional: co the null


}
