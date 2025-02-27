package com.lifesupport.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
@Entity
@Table(name="blogtag")
public class BlogTag {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
    @Column(length = 500)
	private String name;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<Blog> blog = new ArrayList<>();
	public BlogTag(Integer id, List<Blog> blog) {
		super();
		this.id = id;
		this.blog = blog;
	}
	public BlogTag() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Blog> getBlog() {
		return blog;
	}
	public void setBlog(List<Blog> blog) {
		this.blog = blog;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
