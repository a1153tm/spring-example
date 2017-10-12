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
@Table(name = "settings")
public class Setting {
    @Id
    @GeneratedValue
    private Integer id;

    @JsonBackReference
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private User owner;
	
	private String value;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Setting )) return false;
        return id != null && id.equals(((Setting) o).id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
