package com.alura.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    
    private Long id;
    private String titulo;
    private String conteudo;
    private Long autorId;
    private String autorNome;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private int totalComentarios;
}
