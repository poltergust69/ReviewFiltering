package com.example.reviewfiltering.Service;

import com.example.reviewfiltering.Model.Review;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    List<Review> filterAndSortReviews(List<Review> reviews, boolean prioritizeByText, int minimumRating, String orderByRating, String orderByDate);
    List<Review> getReviewsFromJsonFile(String filePath) throws IOException;
}
