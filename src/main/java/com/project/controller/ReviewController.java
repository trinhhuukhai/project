package com.project.controller;


import com.project.dto.request.ReviewRequest;
import com.project.dto.response.AllResponse;
import com.project.model.Review;
import com.project.response.ResponseResult;
import com.project.service.ProductService;
import com.project.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/v1/review")
@CrossOrigin("http://localhost:3000")
public class ReviewController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/getAllReview")
    private AllResponse<List<Review>> getAll(){
        List<Review> allReview = reviewService.getAllReview();
        return new AllResponse<>(allReview.size(), allReview);
    }


    @GetMapping("/{id}")
        //Let's return an object with: data, message, status
    ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        return reviewService.findById(id);
    }

    @PostMapping(value = {"/insert"})
    ResponseEntity<ResponseResult> insertReview( @RequestBody ReviewRequest newReview) {
        return reviewService.insertReview(newReview);

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseResult> updateReview(@RequestBody ReviewRequest newReview, @PathVariable Long id) {
        return reviewService.updateReview(newReview,id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseResult> deleteReview(@PathVariable Long id) {
        return reviewService.deleteReview(id);
    }

    @GetMapping("/paginAndSort/{pageNumber}/{pageSize}")
    public AllResponse<Page<Review>> reviewPagination(@PathVariable Integer pageNumber, @PathVariable Integer pageSize){
        Page<Review> reviewPagination = reviewService.getReviewPagination(pageNumber, pageSize);
        return new AllResponse<>(reviewPagination.getSize(), reviewPagination);
//        return new AllResponse<>(productService.getProductPagination(pageNumber, pageSize));
    }
    @GetMapping("/paginAndSort2/{pageNumber}/{pageSize}/{field}")
    public AllResponse<Page<Review>> reviewPaginationAndSort(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, @PathVariable("filed") String field){
        Page<Review> reviewPagination = reviewService.getReviewPaginationAndSort(pageNumber, pageSize, field);
        return new AllResponse<>(reviewPagination.getSize(), reviewPagination);
//        return new AllResponse<>(productService.getProductPagination(pageNumber, pageSize));
    }

}
