package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.BlogPost;
import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAllOrderByName();
	}

	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAllOrderByName(pageable);
	}

	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}

	public User create(User customer) {
		return userRepository.save(customer);
	}

	public User update(User customer) {
		return userRepository.save(customer);
	}

	public void delete(Integer id) {
		userRepository.delete(id);
	}

	public void addBlogPost(Integer userId, BlogPost blogPost) {
		User user = userRepository.findOne(userId);
		user.addBlogPost(blogPost);
		// userRepository.save(user);
	}
}