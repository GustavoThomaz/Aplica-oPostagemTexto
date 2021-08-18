package com.example.teste.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.teste.model.Post;
import com.example.teste.repository.PostRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private PostRepository repository;
	
	private Post post;
	private Post postagem;
	
	
	@BeforeAll
	public void start() {
		
		Post primeiroPost = new Post ("Primeiro Post", (long) 0);
		Post segundoPost = new Post ("Segundo Post", (long) 0);
		Post terceiroPost = new Post ("Terceiro Post", (long) 0);
		
		repository.save(primeiroPost);
		repository.save(segundoPost);
		repository.save(terceiroPost);
		
		post = new Post ("Quarto Post", (long) 0);
		postagem = new Post("Postagem teste", (long)0);
	}
	
	@Test
	void TestaRetornoListarPostRetorna200() {
		ResponseEntity<List> resposta = testRestTemplate.exchange("/post/", HttpMethod.GET, null, List.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	void TestaAdicionarUpvotesNoTerceiroPostRetorna200() {
		String idDoTerceiroPost= Long.toString(repository.findAll().get(2).getId());
		String url = "/post/"+ idDoTerceiroPost;
		ResponseEntity<Post> resposta = testRestTemplate.exchange(url, HttpMethod.PUT, null , Post.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	void TestaAdicionarUpvotesNoSegundoPost() {
		Post segundoPost = repository.findAll().get(1);
		String idDoSegundoPost= Long.toString(segundoPost.getId());
		Long upvoteAntesDeAtualizar = segundoPost.getUpvotes();
		String url = "/post/"+ idDoSegundoPost;
		ResponseEntity<Post> resposta = testRestTemplate.exchange(url, HttpMethod.PUT, null , Post.class);
		assertEquals(upvoteAntesDeAtualizar+1, resposta.getBody().getUpvotes());
	}
	
	@Test
	void TestaAdicionarPostRetorna201() {
		HttpEntity<Post> request = new HttpEntity<Post>(post);
		ResponseEntity<Post> resposta = testRestTemplate.exchange("/post/", HttpMethod.POST, request, Post.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
	}
	
	@Test
	void TestaFluxoPost() {
		HttpEntity<Post> request = new HttpEntity<Post>(postagem);
		ResponseEntity<Post> resposta = testRestTemplate.exchange("/post/", HttpMethod.POST, request, Post.class);
		ResponseEntity<List> resposta2 = testRestTemplate.exchange("/post/", HttpMethod.GET, null, List.class);
		if(resposta.getStatusCodeValue() == 201 && resposta2.getStatusCodeValue() == 200) {
			Long idPostagemNova = (long)repository.findAll().size()-1;
			String url = "/post/"+ Long.toString(resposta.getBody().getId());
			ResponseEntity<Post> resposta3 = testRestTemplate.exchange(url, HttpMethod.PUT, null , Post.class);
			assertEquals((long)1, resposta3.getBody().getUpvotes());
		}else {
			assertTrue(false);
		}
		
	}
	
	
	@AfterAll
	void end() {
		repository.deleteAll();
	}
	
}
