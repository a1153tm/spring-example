package com.example.api;

import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/users")
public class UserRestController {
    @Autowired
    UserService userService;

    @GetMapping
    Page<User> getCustomers(@PageableDefault Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return users;
    }

    @GetMapping(path = "{id}")
    User getCustomer(@PathVariable Integer id) {
        User user = userService.findOne(id);
        return user;
    }

    @PostMapping
    ResponseEntity<User> postCustomers(@RequestBody User user, UriComponentsBuilder uriBuilder) {
        User created = userService.create(user);
        URI location = uriBuilder.path("api/users/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "{id}")
    User putCustomer(@PathVariable Integer id, @RequestBody User user) {
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Integer id) {
        userService.delete(id);
    }
}
