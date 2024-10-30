package com.app.reviewms;

import com.app.reviewms.review.Review;
import com.app.reviewms.review.ReviewRepository;
import com.app.reviewms.review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    public void setUp() {
        reviewRepository.deleteAll();

        // Initialize a Review
        review = new Review();
        review.setCompanyId(1L);
        review.setTitle("Integration Test Review");
        review.setDescription("This is an integration test review.");
        review.setRating(5.0);
    }

    @Test
    public void testGetAllReviews() throws Exception {
        reviewService.addReview(1L, review);

        mockMvc.perform(get("/reviews/company/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Integration Test Review"));
    }

    @Test
    public void testAddReview() throws Exception {
        mockMvc.perform(post("/reviews/company/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetReview() throws Exception {
        reviewService.addReview(1L, review);
        Review savedReview = reviewService.getAllReviews(1L).get(0);

        mockMvc.perform(get("/reviews/" + savedReview.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test Review"));
    }

    @Test
    public void testUpdateReview() throws Exception {
        reviewService.addReview(1L, review);
        Review savedReview = reviewService.getAllReviews(1L).get(0);

        savedReview.setTitle("Updated Integration Test Review");

        mockMvc.perform(put("/reviews/" + savedReview.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedReview)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/reviews/" + savedReview.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Integration Test Review"));
    }

    @Test
    public void testDeleteReview() throws Exception {
        reviewService.addReview(1L, review);
        Review savedReview = reviewService.getAllReviews(1L).get(0);

        mockMvc.perform(delete("/reviews/" + savedReview.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        mockMvc.perform(get("/reviews/" + savedReview.getId()))
                .andExpect(status().isNotFound());
    }
}