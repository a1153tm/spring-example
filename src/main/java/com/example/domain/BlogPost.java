package com.example.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	
	private String title;
	
	private String content;
	
	public void overWrite(BlogPost other) {
		content = other.content;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogPost )) return false;
        return id != null && id.equals(((BlogPost) o).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
