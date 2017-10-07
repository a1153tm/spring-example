
package com.example.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Setter
    @Getter
    private Integer id;
    private String firstName;
    private String lastName;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner")
    private List<BlogPost> blogPosts = new ArrayList<BlogPost>();
    
    public List<BlogPost> getBlogPosts() {
    		return blogPosts;
    }
    
    public void addBlogPost(BlogPost blogPost) {
    		blogPost.setOwner(this);
    		this.blogPosts.add(blogPost);
    }
    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User )) return false;
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 32;
    }
}