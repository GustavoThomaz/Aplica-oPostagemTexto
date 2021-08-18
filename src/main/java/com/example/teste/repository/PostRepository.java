package com.example.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.teste.model.Post;

public interface PostRepository extends JpaRepository<Post,Long>{

}
