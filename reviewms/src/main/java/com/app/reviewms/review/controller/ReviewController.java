package com.app.reviewms.review.controller;


import com.app.reviewms.review.service.ReviewService;
import com.app.reviewms.review.entity.Review;
import com.app.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;


    public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }


    @GetMapping
    public ResponseEntity<?> getAllReviews(@RequestParam Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> addReview(@RequestParam Long companyId,@RequestBody Review review){
        Boolean isReviewAdded =   reviewService.addReview(companyId,review);
        if(isReviewAdded){
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review Added Successfully",HttpStatus.CREATED);
        }
        else{

            return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
        }

    }


    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable Long reviewId){
        if(reviewService.getReview(reviewId)!=null){

        return new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
        }
        return new ResponseEntity<>("No Review Available",HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId,@RequestBody Review review){

        Boolean isReviewUpdated=reviewService.updateReview(reviewId,review);


        if(isReviewUpdated){
            return new ResponseEntity<>("Review Updated successfully",HttpStatus.OK);
        }
        else {

            return new ResponseEntity<>("Review Not updated",HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId){
        Boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if(isReviewDeleted){
            return new ResponseEntity<>(  "Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("No review Available to delete",HttpStatus.REQUEST_TIMEOUT);
    }



    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId){
        List<Review> reviewList=reviewService.getAllReviews(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }



}
