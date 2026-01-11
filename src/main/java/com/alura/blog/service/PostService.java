package com.alura.blog.service;

import com.alura.blog.dto.PostDTO;
import com.alura.blog.dto.PostResponseDTO;
import com.alura.blog.model.Post;
import com.alura.blog.model.Usuario;
import com.alura.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<PostResponseDTO> listarTodos() {
        return postRepository.findAllOrderByDataCriacaoDesc()
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public Optional<PostResponseDTO> buscarPorId(Long id) {
        return postRepository.findById(id)
                .map(this::converterParaDTO);
    }

    public List<PostResponseDTO> buscarPorAutor(Long autorId) {
        return postRepository.findByAutorIdOrderByDataCriacaoDesc(autorId)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> buscarPorTitulo(String titulo) {
        return postRepository.findByTituloContainingIgnoreCaseOrderByDataCriacaoDesc(titulo)
                .stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO criar(PostDTO postDTO, Usuario autor) {
        Post post = new Post();
        post.setTitulo(postDTO.getTitulo());
        post.setConteudo(postDTO.getConteudo());
        post.setAutor(autor);

        post = postRepository.save(post);
        return converterParaDTO(post);
    }

    public Optional<PostResponseDTO> atualizar(Long id, PostDTO postDTO, Usuario autor) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // Verifica se o usuário é o autor do post
            if (!post.getAutor().getId().equals(autor.getId())) {
                throw new RuntimeException("Você não tem permissão para editar este post");
            }

            post.setTitulo(postDTO.getTitulo());
            post.setConteudo(postDTO.getConteudo());

            post = postRepository.save(post);
            return Optional.of(converterParaDTO(post));
        }

        return Optional.empty();
    }

    public boolean deletar(Long id, Usuario autor) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // Verifica se o usuário é o autor do post
            if (!post.getAutor().getId().equals(autor.getId())) {
                throw new RuntimeException("Você não tem permissão para deletar este post");
            }

            postRepository.delete(post);
            return true;
        }

        return false;
    }

    private PostResponseDTO converterParaDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setTitulo(post.getTitulo());
        dto.setConteudo(post.getConteudo());
        dto.setAutorId(post.getAutor().getId());
        dto.setAutorNome(post.getAutor().getNome());
        dto.setDataCriacao(post.getDataCriacao());
        dto.setDataAtualizacao(post.getDataAtualizacao());
        dto.setTotalComentarios(post.getComentarios().size());
        return dto;
    }
}
