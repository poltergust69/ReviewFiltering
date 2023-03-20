package com.example.reviewfiltering.Web;

import com.example.reviewfiltering.Model.Review;
import com.example.reviewfiltering.Service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

    private ReviewService reviewService;

    @GetMapping
    public String showFilterForm() {
        return "filter.html";
    }

    @GetMapping("/filtered")
    public String getFilteredAndSortedReviews(
            @RequestParam(defaultValue = "false") boolean prioritizeByText,
            @RequestParam(defaultValue = "1") int minimumRating,
            @RequestParam(defaultValue = "Highest First") String orderByRating,
            @RequestParam(defaultValue = "Newest First") String orderByDate,
            Model model) throws IOException {

        List<Review> reviews = new ArrayList<>(reviewService.getReviewsFromJsonFile("C:\\Users\\Jarvis\\Desktop\\FINKI\\WP\\ReviewFiltering\\reviews.json"));
        List<Review> filteredReviews = new ArrayList<>(reviewService.filterAndSortReviews(reviews, prioritizeByText, minimumRating, orderByRating, orderByDate));
        model.addAttribute("filteredReviews", filteredReviews);
        return "filter.html";
    }
}