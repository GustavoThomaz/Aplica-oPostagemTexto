package com.example.teste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.teste.model.Post;
import com.example.teste.service.PostService;

@RestController
@CrossOrigin (origins = "*", allowedHeaders = "*")
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostService service;
	
	@GetMapping("/")
	public ResponseEntity<List<Post>> listarPosts() {
		return service.listarPosts();
	}
	
	@PostMapping("/")
	public ResponseEntity<Post> adicionarPost(@RequestBody Post post) {
		return service.adicionarPost(post);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Post> adicionarUpvote(@PathVariable Long id) {
		return service.adicionarUpvotes(id);
	}
}
