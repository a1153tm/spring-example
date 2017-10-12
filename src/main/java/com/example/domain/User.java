
package com.example.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@GeneratedValue
	private Integer id;

	//@Unique
	private String firstName;

	@NotBlank
	private String lastName;

	@Column(nullable = false, updatable = false)
	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date modifiedDate;

	@Enumerated(EnumType.STRING)
	private Role role;

	private EmailAddress emailAddress;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner", fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<BlogPost> blogPosts = new ArrayList<BlogPost>();

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "owner", fetch=FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	private List<Setting> settings = new ArrayList<>();

	public List<BlogPost> getBlogPosts() {
		return blogPosts;
	}

	public Optional<BlogPost> findBlogPost(Integer blogPostId) {
		return this.blogPosts.stream().filter(b -> b.getId().equals(blogPostId)).findFirst();
	}

	public void addBlogPost(BlogPost blogPost) {
		blogPost.setOwner(this);
		this.blogPosts.add(blogPost);
	}

	public void updateBlogPosts(List<BlogPost> blogPosts) {
		for (BlogPost blogPost : blogPosts) {
			Optional<BlogPost> old = this.blogPosts.stream().filter(b -> b.getTitle().equals(blogPost.getTitle()))
					.findFirst();
			if (old.isPresent()) {
				old.get().overWrite(blogPost);
			} else {
				this.addBlogPost(blogPost);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof User))
			return false;
		return id != null && id.equals(((User) o).id);
	}

	@Override
	public int hashCode() {
		return 32;
	}
}