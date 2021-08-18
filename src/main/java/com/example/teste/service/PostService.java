package com.example.teste.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.teste.model.Post;
import com.example.teste.repository.PostRepository;


@Service
public class PostService {

	@Autowired
	private PostRepository repository;
	
	public ResponseEntity<Post> adicionarPost (Post post){
		if(post.getConteudo_post().isEmpty()) {
			return ResponseEntity.status(400).build();
		}
		post.setUpvotes(0);
		return ResponseEntity.status(201).body(repository.save(post));
	}
	
	public ResponseEntity<List<Post>> listarPosts (){
		return ResponseEntity.status(200).body(repository.findAll());
	}
	
	public ResponseEntity<Post> adicionarUpvotes(Long id){
		Optional<Post> postExistente = repository.findById(id);
		if(postExistente.isPresent()) {
			postExistente.get().adicionarUpvotes();
			Optional.ofNullable(repository.save(postExistente.get()));
			return ResponseEntity.status(200).body(postExistente.get());
		}else {
			return ResponseEntity.status(400).build();
		}
	}
}
