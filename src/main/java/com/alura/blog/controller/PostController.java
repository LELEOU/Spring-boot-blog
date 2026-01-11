package com.alura.blog.controller;

import com.alura.blog.dto.PostDTO;
import com.alura.blog.dto.PostResponseDTO;
import com.alura.blog.model.Usuario;
import com.alura.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> listarTodos(
            @RequestParam(required = false) Long autorId,
            @RequestParam(required = false) String titulo) {
        
        List<PostResponseDTO> posts;
        
        if (autorId != null) {
            posts = postService.buscarPorAutor(autorId);
        } else if (titulo != null && !titulo.isEmpty()) {
            posts = postService.buscarPorTitulo(titulo);
        } else {
            posts = postService.listarTodos();
        }
        
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<PostResponseDTO> post = postService.buscarPorId(id);
        return post.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody PostDTO postDTO,
                                   @AuthenticationPrincipal Usuario autor) {
        try {
            PostResponseDTO post = postService.criar(postDTO, autor);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                      @Valid @RequestBody PostDTO postDTO,
                                      @AuthenticationPrincipal Usuario autor) {
        try {
            Optional<PostResponseDTO> post = postService.atualizar(id, postDTO, autor);
            return post.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id,
                                     @AuthenticationPrincipal Usuario autor) {
        try {
            boolean deletado = postService.deletar(id, autor);
            return deletado ? ResponseEntity.noContent().build()
                           : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
