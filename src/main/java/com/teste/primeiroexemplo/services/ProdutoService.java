package com.teste.primeiroexemplo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exceptions.ResourceNotFoundException;
import com.teste.primeiroexemplo.repository.ProdutoRepository;
import com.teste.primeiroexemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoDTO> obterTodos() {
        // Retorna uma lista de produtos
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(p -> convertProduct(p))
                .sorted((p1, p2) -> p1.getId().compareTo(p2.getId()))
                .collect(Collectors.toList());

    }

    @SuppressWarnings("null")
    public Optional<ProdutoDTO> obterPorID(Integer ID) {
        Optional<Produto> produto = produtoRepository.findById(ID);

        if (produto.isEmpty()) {
            throw new ResourceNotFoundException("Produto com id: " + ID + " não encontrado.");
        }

        return Optional.of(convertProduct(produto.get()));
    }

    public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
        produtoDto.setId(null);
        Produto produto = new Produto();
        produto.setNome(produtoDto.getNome());
        produto.setQuantidade(produtoDto.getQuantidade());
        produto.setValor(produtoDto.getValor());
        produto.setObservacao(produtoDto.getObservacao());

        produto = produtoRepository.save(produto);
        produtoDto.setId(produto.getId());
        return produtoDto;
    }

    @SuppressWarnings("null")
    public void deletar(Integer id) {
        // Verificar se o produto existe
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Não foi possivel delatar o produto com: " + id + " - Produto não existe.");
        } else {
            produtoRepository.deleteById(id);
        }
    }

    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto) {
        // Passar o id para o produtoDto
        produtoDto.setId(id);
        // Criar um objeto de mapeamento
        Produto produto = new Produto();
        produto.setId(produtoDto.getId());
        produto.setNome(produtoDto.getNome());
        produto.setQuantidade(produtoDto.getQuantidade());
        produto.setValor(produtoDto.getValor());
        produto.setObservacao(produtoDto.getObservacao());
        // Converter o dto em um Produto
        produtoRepository.save(produto);
        // Atualizar o produto no banco de dados

        return produtoDto;
    }

    private ProdutoDTO convertProduct(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getValor(),
                produto.getObservacao());
    }
}
