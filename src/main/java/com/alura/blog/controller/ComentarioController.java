package com.alura.blog.controller;

import com.alura.blog.dto.ComentarioDTO;
import com.alura.blog.dto.ComentarioResponseDTO;
import com.alura.blog.model.Usuario;
import com.alura.blog.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/posts/{postId}/comentarios")
    public ResponseEntity<List<ComentarioResponseDTO>> listarPorPost(@PathVariable Long postId) {
        List<ComentarioResponseDTO> comentarios = comentarioService.listarPorPost(postId);
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping("/comentarios/autor/{autorId}")
    public ResponseEntity<List<ComentarioResponseDTO>> listarPorAutor(@PathVariable Long autorId) {
        List<ComentarioResponseDTO> comentarios = comentarioService.listarPorAutor(autorId);
        return ResponseEntity.ok(comentarios);
    }

    @PostMapping("/posts/{postId}/comentarios")
    public ResponseEntity<?> criar(@PathVariable Long postId,
                                   @Valid @RequestBody ComentarioDTO comentarioDTO,
                                   @AuthenticationPrincipal Usuario autor) {
        Optional<ComentarioResponseDTO> comentario = comentarioService.criar(postId, comentarioDTO, autor);
        
        return comentario.map(c -> ResponseEntity.status(HttpStatus.CREATED).body(c))
                        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id,
                                     @AuthenticationPrincipal Usuario autor) {
        try {
            boolean deletado = comentarioService.deletar(id, autor);
            return deletado ? ResponseEntity.noContent().build()
                           : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
