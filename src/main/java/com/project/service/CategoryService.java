package com.project.service;

import com.project.model.Category;
import com.project.model.Customer;
import com.project.repository.CategoryRepository;
import com.project.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public ResponseEntity<ResponseResult> insertCategory(Category newCat) {
        //2 products must not have the same name !
        List<Category> foundCate = categoryRepository.findByName(newCat.getName().trim());
        if(foundCate.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseResult("failed", "Product name already taken", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert Product successfully", categoryRepository.save(newCat))
        );
    }


    public ResponseEntity<ResponseResult> updateCategory(@RequestBody Category newCate, @PathVariable Long id) {
        Category updatedCat = categoryRepository.findById(id)
                .map(cate -> {
                    cate.setName(newCate.getName());
                    cate.setDescription(newCate.getDescription());
                    return categoryRepository.save(cate);
                }).orElseGet(() -> {
                    newCate.setId(id);
                    return categoryRepository.save(newCate);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update category successfully", updatedCat)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteCategory(@PathVariable Long id) {
        boolean exists = categoryRepository.existsById(id);
        if(exists) {
            categoryRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete category successfully", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find category to delete", "")
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Category> foundCategory = categoryRepository.findById(id);
        return foundCategory.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query category successfully", foundCategory)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find category with id = "+id, "")
                );
    }
}