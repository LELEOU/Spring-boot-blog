package com.alura.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    
    private String token;
    private String tipo = "Bearer";
    private Long usuarioId;
    private String nome;
    private String email;

    public AuthResponseDTO(String token, Long usuarioId, String nome, String email) {
        this.token = token;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
    }
}
