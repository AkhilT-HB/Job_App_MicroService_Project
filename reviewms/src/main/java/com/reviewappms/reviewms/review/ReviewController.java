package com.reviewappms.reviewms.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	@GetMapping
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
		return new ResponseEntity<>(reviewService.getAllReviews(companyId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> addReview(@RequestParam Long companyId ,@RequestBody Review review){
		if(reviewService.addReview(companyId, review)) {
			return new ResponseEntity<>("Review added Successfully", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Review not saved", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
		
		Review review = reviewService.getReview(reviewId);
		
			return new ResponseEntity<>(review,HttpStatus.OK);
	
	}
	
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReview( @PathVariable Long reviewId, 
												@RequestBody Review updatedReview){
		boolean isReviewUpdated = reviewService.updateReview(reviewId, updatedReview);
		if(isReviewUpdated) {
		return new ResponseEntity<>("Review Updated Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<>("Review not found",HttpStatus.NOT_FOUND);
	}
	
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
		boolean isReviewDeleted = reviewService.deleteReview(reviewId);
		if(isReviewDeleted) {
			return new ResponseEntity<>("Review deleted Successfully",HttpStatus.OK);
			}
			return new ResponseEntity<>("Review not deleted",HttpStatus.NOT_FOUND);
	}
	
	
	
}
