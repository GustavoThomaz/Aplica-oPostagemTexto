package com.example.teste.repository;

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
import org.springframework.test.context.junit4.SpringRunner;

import com.example.teste.model.Post;

@SpringBootTest(webEnvironment =  WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
public class PostRepositoryTest {

	@Autowired
	private PostRepository repository;
	
	private List<Post> listaDePosts;
	
	@BeforeAll
	void start() {		
		
		Post primeiroPost = new Post ("Primeiro Post", (long) 0);
		Post segundoPost = new Post ("Segundo Post", (long) 0);
		Post terceiroPost = new Post ("Terceiro Post", (long) 0);		
		
		repository.save(primeiroPost);
		repository.save(segundoPost);
		repository.save(terceiroPost);
		
		listaDePosts = repository.findAll();
	}

	@Test
	public void verificaSeFindAllRetornaTresPosts() {
		assertEquals(3, listaDePosts.size());
	}
	
	@Test
	public void verificaSeFindByIdRetornaRetornaPrimeiroPost() {
		Post postCorreto1 = repository.findById(listaDePosts.get(0).getId()).get();
		assertEquals("Primeiro Post", postCorreto1.getConteudo_post());
	}
	
	@Test
	public void verificaSeFindByIdRetornaRetornaSegundoPost() {
		Post postCorreto2 = repository.findById(listaDePosts.get(1).getId()).get();
		assertEquals("Segundo Post", postCorreto2.getConteudo_post());
	}
	
	@Test
	public void verificaSeFindByIdRetornaRetornaTerceiroPost() {
		Post postCorreto3 = repository.findById(listaDePosts.get(2).getId()).get();
		assertEquals("Terceiro Post", postCorreto3.getConteudo_post());
	}	
	
	@AfterAll
	void end() {
		repository.deleteAll();
	}
}
