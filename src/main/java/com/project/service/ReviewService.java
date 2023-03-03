package com.project.service;

import com.project.dto.request.ProductRequest;
import com.project.dto.request.ReviewRequest;
import com.project.model.Category;
import com.project.model.Product;
import com.project.model.Review;
import com.project.repository.CategoryRepository;
import com.project.repository.ProductRepository;
import com.project.repository.ReviewRepository;
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

import java.util.List;
import java.util.Optional;
@Service
public class ReviewService {
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReview(){
        return reviewRepository.findAll();
    }


    public ResponseEntity<ResponseResult> insertReview(ReviewRequest newReview) {
//

        Product product = productRepository.findById(newReview.getProductId()).orElseThrow();

        Review review = new Review();
        review.setCustomerName(newReview.getCustomerName());
        review.setRating(newReview.getRating());
        review.setContent(newReview.getContent());
        review.setProduct(product);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Insert review successfully",reviewRepository.save(review),1)
        );

    }

    public ResponseEntity<ResponseResult> updateReview( ReviewRequest newReview, Long id) {

        Product product = productRepository.findById(newReview.getProductId()).orElseThrow();

        Optional<Review> updatedReview = reviewRepository.findById(id)
                .map(review -> {
                    review.setCustomerName(newReview.getCustomerName());
                    review.setRating(newReview.getRating());
                    review.setContent(newReview.getContent());
                    review.setProduct(product);
                    return reviewRepository.save(review);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseResult("ok", "Update review successfully", updatedReview,1)
        );
    }

    //Delete a Product => DELETE method
    public ResponseEntity<ResponseResult> deleteReview(Long id) {
        boolean exists = reviewRepository.existsById(id);
        if(exists) {
            reviewRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseResult("ok", "Delete review successfully", "",1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseResult("failed", "Cannot find review to delete", "",1)
        );
    }

    //Let's return an object with: data, message, status
    public ResponseEntity<ResponseResult> findById(@PathVariable Long id) {
        Optional<Review> foundReview = reviewRepository.findById(id);
        return foundReview.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseResult("ok", "Query review successfully", foundReview,1)
                        //you can replace "ok" with your defined "error code"
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseResult("failed", "Cannot find review with id = "+id, "",1)
                );
    }

    public Page<Review> getReviewPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("createdDate").descending());
        return reviewRepository.findAll(pageable);
    }

    public Page<Review> getReviewPaginationAndSort(Integer pageNumber, Integer pageSize , String field) {
        Pageable pageable = null;
        if (null != field){
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC,field);

        }else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.Direction.ASC,"name");

        }
        return reviewRepository.findAll(pageable);
    }
}
