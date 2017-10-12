package com.example.api;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.BlogPost;
import com.example.domain.User;
import com.example.domain.UserJson;
import com.example.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping
    //Page<User> getUsers(@PageableDefault Pageable pageable) {
    List<User> getUsers() {
        //Page<User> users = userService.findAll(pageable);
        //List<User> users = userService.findAllWithBlogPosts();
        List<User> users = userService.findAll();
        return users;
    }

    @GetMapping(path = "{id}")
    User getUser(@PathVariable Integer id) {
        User user = userService.findOne(id);
        return user;
    }

    /*
    @PostMapping
    ResponseEntity<User> postUsers(@RequestBody @Valid User user, UriComponentsBuilder uriBuilder) {
        User created = userService.create(user);
        URI location = uriBuilder.path("api/users/{id}")
                .buildAndExpand(created.getId()).toUri() ;
        return ResponseEntity.created(location).body(created);
    }
    */

    @PostMapping
    ResponseEntity<User> postUsers(@RequestBody @Valid UserJson userJson, UriComponentsBuilder uriBuilder) {
    		User user = new User();
    		BeanUtils.copyProperties(userJson, user);
        User created = userService.create(user);
        URI location = uriBuilder.path("api/users/{id}")
                .buildAndExpand(created.getId()).toUri() ;
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping(path = "{id}/blog-posts")
    ResponseEntity<User> postBlogPost(@PathVariable Integer id, @RequestBody BlogPost blogPost, UriComponentsBuilder uriBuilder) {
        User user = userService.findOne(id);
        user.addBlogPost(blogPost);
        userService.update(user);
        URI location = uriBuilder.path("api/users/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }

    @PutMapping(path = "{id}/blog-posts")
    User putBlogPosts(@PathVariable Integer id, @RequestBody List<BlogPost> blogPosts) {
        User user = userService.findOne(id);
        user.updateBlogPosts(blogPosts);
        userService.update(user);
        return user;
    }

    @PutMapping(path = "{id}")
    User putUser(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable Integer id) {
        userService.delete(id);
    }
}
