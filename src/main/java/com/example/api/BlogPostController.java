package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.BlogPost;
import com.example.service.UserService;

@RestController
@RequestMapping("api/users/{userId}/blog-posts")
public class BlogPostController {
	@Autowired
	private UserService userService;

	@GetMapping
	public List<BlogPost> getBlogPosts(@PathVariable("userId") Integer userId) {
		List<BlogPost> res = userService.findOne(userId).getBlogPosts();
		return res;
	}

	@GetMapping(path = "{blogPostId}")
	public BlogPost getBlogPost(@PathVariable("userId") Integer userId,
			@PathVariable("blogPostId") Integer blogPostId) {
		return userService.findOne(userId).findBlogPost(blogPostId).get();
	}
}
