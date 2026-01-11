package com.alura.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioDTO {

    @NotBlank(message = "O conteúdo do comentário é obrigatório")
    private String conteudo;
}
