package com.app.reviewms;

import com.app.reviewms.review.entity.Review;
import com.app.reviewms.review.repository.ReviewRepository;
import com.app.reviewms.review.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private List<Review> reviewList;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a single Review
        review = new Review();
        review.setId(1L);
        review.setCompanyId(1L);
        review.setTitle("Example Review");
        review.setDescription("This is an example review.");
        review.setRating(5.0);

        // Initialize a Review list
        reviewList = new ArrayList<>();
        reviewList.add(review);
    }

    @Test
    public void testGetAllReviews() {
        when(reviewRepository.findByCompanyId(1L)).thenReturn(reviewList);
        List<Review> reviews = reviewService.getAllReviews(1L);
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
    }

    @Test
    public void testAddReview() {
        when(reviewRepository.save(review)).thenReturn(review);
        Boolean result = reviewService.addReview(1L, review);
        assertTrue(result);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testAddReviewWithNullCompanyId() {
        Boolean result = reviewService.addReview(null, review);
        assertFalse(result);
        verify(reviewRepository, times(0)).save(review);
    }

    @Test
    public void testGetReview() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        Review found = reviewService.getReview(1L);
        assertNotNull(found);
        assertEquals("Example Review", found.getTitle());
    }

    @Test
    public void testGetReviewNotFound() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
        Review found = reviewService.getReview(1L);
        assertNull(found);
    }

    @Test
    public void testUpdateReview() {
        Review updatedReview = new Review();
        updatedReview.setCompanyId(1L);
        updatedReview.setTitle("Updated Review");
        updatedReview.setDescription("This is an updated description.");
        updatedReview.setRating(4.0);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(review)).thenReturn(review);

        Boolean result = reviewService.updateReview(1L, updatedReview);
        assertTrue(result);
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testUpdateReviewNotFound() {
        Review updatedReview = new Review();
        updatedReview.setCompanyId(1L);
        updatedReview.setTitle("Updated Review");
        updatedReview.setDescription("This is an updated description.");
        updatedReview.setRating(4.0);

        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        Boolean result = reviewService.updateReview(1L, updatedReview);
        assertFalse(result);
        verify(reviewRepository, times(0)).save(updatedReview);
    }

    @Test
    public void testDeleteReview() {
        when(reviewRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reviewRepository).deleteById(1L);

        Boolean result = reviewService.deleteReview(1L);
        assertTrue(result);
        verify(reviewRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteReviewNotFound() {
        when(reviewRepository.existsById(1L)).thenReturn(false);

        Boolean result = reviewService.deleteReview(1L);
        assertFalse(result);
        verify(reviewRepository, times(0)).deleteById(1L);
    }
}