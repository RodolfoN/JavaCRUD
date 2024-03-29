package com.teste.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exceptions.ResourceNotFoundException;

@Repository
public class ProdutoRepository_old {

    private List<Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoID = 0;

    public List<Produto> findAll() {
        return produtos;
    }

    public Optional<Produto> findById(Integer ID) {
        return produtos
                .stream()
                .filter(produto -> produto.getId() == ID)
                .findFirst();
    }

    public Produto save(Produto produto) {
        ultimoID++;
        produto.setId(ultimoID);
        produtos.add(produto);

        return produto;
    }

    public void deleteById(Integer id) {
        produtos.removeIf(produto -> produto.getId() == id);
    }

    public Produto update(Produto produto) {
        Optional<Produto> produtoEncontrado = findById(produto.getId());
        if (produtoEncontrado.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        deleteById(produto.getId());
        produtos.add(produto);

        return produto;
    }
}