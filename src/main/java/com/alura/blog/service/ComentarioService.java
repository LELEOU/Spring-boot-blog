package com.alura.blog.service;

import com.alura.blog.dto.ComentarioDTO;
import com.alura.blog.dto.ComentarioResponseDTO;
import com.alura.blog.model.Comentario;
import com.alura.blog.model.Post;
import com.alura.blog.model.Usuario;
import com.alura.blog.repository.ComentarioRepository;
import com.alura.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PostRepository postRepository;

    public List<ComentarioResponseDTO> listarPorPost(Long postId) {
        return comentarioRepository.findByPostIdOrderByDataCriacaoDesc(postId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<ComentarioResponseDTO> listarPorAutor(Long autorId) {
        return comentarioRepository.findByAutorIdOrderByDataCriacaoDesc(autorId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<ComentarioResponseDTO> criar(Long postId, ComentarioDTO comentarioDTO, Usuario autor) {
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) {
            return Optional.empty();
        }

        Comentario comentario = new Comentario();
        comentario.setConteudo(comentarioDTO.getConteudo());
        comentario.setAutor(autor);
        comentario.setPost(postOptional.get());

        comentario = comentarioRepository.save(comentario);
        return Optional.of(converterParaDTO(comentario));
    }

    public boolean deletar(Long id, Usuario autor) {
        Optional<Comentario> comentarioOptional = comentarioRepository.findById(id);

        if (comentarioOptional.isPresent()) {
            Comentario comentario = comentarioOptional.get();

            // Verifica se o usuário é o autor do comentário
            if (!comentario.getAutor().getId().equals(autor.getId())) {
                throw new RuntimeException("Você não tem permissão para deletar este comentário");
            }

            comentarioRepository.delete(comentario);
            return true;
        }

        return false;
    }

    private ComentarioResponseDTO converterParaDTO(Comentario comentario) {
        ComentarioResponseDTO dto = new ComentarioResponseDTO();
        dto.setId(comentario.getId());
        dto.setConteudo(comentario.getConteudo());
        dto.setAutorId(comentario.getAutor().getId());
        dto.setAutorNome(comentario.getAutor().getNome());
        dto.setPostId(comentario.getPost().getId());
        dto.setDataCriacao(comentario.getDataCriacao());
        return dto;
    }
}
