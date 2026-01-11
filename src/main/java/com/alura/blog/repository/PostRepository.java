package com.alura.blog.repository;

import com.alura.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByAutorIdOrderByDataCriacaoDesc(Long autorId);
    
    @Query("SELECT p FROM Post p ORDER BY p.dataCriacao DESC")
    List<Post> findAllOrderByDataCriacaoDesc();
    
    List<Post> findByTituloContainingIgnoreCaseOrderByDataCriacaoDesc(String titulo);
}
