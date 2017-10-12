package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT x FROM User x ORDER BY x.firstName, x.lastName")
    List<User> findAllOrderByName();

    @Query("SELECT x FROM User x ORDER BY x.firstName, x.lastName")
    Page<User> findAllOrderByName(Pageable pageable);
    
    //@Query("SELECT u FROM User u LEFT JOIN FETCH u.blogPosts LEFT JOIN FETCH u.settings")
    //List<User> findAllWithBlogPosts();
    
    List<User> findByFirstName(String firstName);
}