package com.alura.blog.repository;

import com.alura.blog.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    
    List<Comentario> findByPostIdOrderByDataCriacaoDesc(Long postId);
    
    List<Comentario> findByAutorIdOrderByDataCriacaoDesc(Long autorId);
}
