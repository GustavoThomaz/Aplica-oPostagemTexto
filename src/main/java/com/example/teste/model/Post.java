package com.example.teste.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String conteudo_post ;
	
	private Long upvotes;
	
	public Post() {
		
	}

	public Post(@NotNull String conteudo_post, Long upvotes) {
		this.conteudo_post = conteudo_post;
		this.upvotes = upvotes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConteudo_post() {
		return conteudo_post;
	}

	public void setConteudo_post(String conteudo_post) {
		this.conteudo_post = conteudo_post;
	}

	public Long getUpvotes() {
		return upvotes;
	}

	public void setUpvotes(int i) {
		this.upvotes = (long) i;
	}
	
	public void adicionarUpvotes() {
		this.upvotes = this.upvotes+1;
	}
	
}
