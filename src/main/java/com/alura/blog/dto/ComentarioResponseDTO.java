package com.alura.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioResponseDTO {
    
    private Long id;
    private String conteudo;
    private Long autorId;
    private String autorNome;
    private Long postId;
    private LocalDateTime dataCriacao;
}
