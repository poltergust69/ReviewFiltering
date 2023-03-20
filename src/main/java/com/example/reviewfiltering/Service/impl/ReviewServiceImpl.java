package com.example.reviewfiltering.Service.impl;

import com.example.reviewfiltering.Model.Review;
import com.example.reviewfiltering.Service.ReviewService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ObjectMapper objectMapper;

    public ReviewServiceImpl() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
    }

    @Override
    public List<Review> filterAndSortReviews(List<Review> reviews, boolean prioritizeByText, int minimumRating, String orderByRating, String orderByDate) {
        List<Review> filteredReviews = new ArrayList<>(reviews.stream().filter(r -> r.getRating() >= minimumRating).toList());

        if (prioritizeByText) {
            List<Review> reviewsWithText = new ArrayList<>(filteredReviews.stream().filter(r -> r.getReviewText() != null && !r.getReviewText().isEmpty()).toList());
            List<Review> reviewsWithoutText = new ArrayList<>(filteredReviews.stream().filter(r -> r.getReviewText() == null || r.getReviewText().isEmpty()).toList());

            // sort reviews with text
            reviewsWithText.sort(orderByRating.equals("Highest First") ? Comparator.comparing(Review::getRating).reversed() : Comparator.comparing(Review::getRating));
            reviewsWithText.sort(orderByDate.equals("Newest First") ? Comparator.comparing(Review::getReviewCreatedOnDate).reversed() : Comparator.comparing(Review::getReviewCreatedOnDate));

            // sort reviews without text
            reviewsWithoutText.sort(orderByRating.equals("Highest First") ? Comparator.comparing(Review::getRating).reversed() : Comparator.comparing(Review::getRating));
            reviewsWithoutText.sort(orderByDate.equals("Newest First") ? Comparator.comparing(Review::getReviewCreatedOnDate).reversed() : Comparator.comparing(Review::getReviewCreatedOnDate));

            reviewsWithText.addAll(reviewsWithoutText);

            return reviewsWithText;
        }
        else {
            filteredReviews.sort(orderByRating.equals("Highest First") ? Comparator.comparing(Review::getRating).reversed() : Comparator.comparing(Review::getRating));
            filteredReviews.sort(orderByDate.equals("Newest First") ? Comparator.comparing(Review::getReviewCreatedOnDate).reversed() : Comparator.comparing(Review::getReviewCreatedOnDate));
            return filteredReviews;
        }
    }

    @Override
    public List<Review> getReviewsFromJsonFile(String filePath) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, new TypeReference<List<Review>>(){});
    }


}
