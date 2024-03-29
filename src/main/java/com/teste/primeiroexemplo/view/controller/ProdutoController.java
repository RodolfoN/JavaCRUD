package com.teste.primeiroexemplo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teste.primeiroexemplo.services.ProdutoService;
import com.teste.primeiroexemplo.shared.ProdutoDTO;
import com.teste.primeiroexemplo.view.model.ProdutoRequest;
import com.teste.primeiroexemplo.view.model.ProdutoResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> obterTodos() {
        List<ProdutoDTO> produtos = produtoService.obterTodos();
        List<ProdutoResponse> produtosResponse = produtos.stream()
                .map(p -> new ProdutoResponse(p.getId(), p.getNome(), p.getQuantidade(), p.getValor(),
                        p.getObservacao()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(produtosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> obterPorID(@PathVariable Integer id) {
        Optional<ProdutoDTO> produtoDto = produtoService.obterPorID(id);
        ProdutoResponse produtoResponse = new ProdutoResponse(
                produtoDto.get().getId(), produtoDto.get().getNome(),
                produtoDto.get().getQuantidade(),
                produtoDto.get().getValor(),
                produtoDto.get().getObservacao());
        return ResponseEntity.ok(produtoResponse);
    }

    @SuppressWarnings("null")
    @PostMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq) {
        ProdutoDTO produtoDto = new ProdutoDTO(
                produtoReq.getNome(),
                produtoReq.getQuantidade(),
                produtoReq.getValor(),
                produtoReq.getObservacao());
        produtoDto = produtoService.adicionar(produtoDto);

        ProdutoResponse produtoResponse = new ProdutoResponse(
                produtoDto.getId(),
                produtoDto.getNome(),
                produtoDto.getQuantidade(),
                produtoDto.getValor(),
                produtoDto.getObservacao());
        return ResponseEntity.created(null).body(produtoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@RequestBody ProdutoRequest produtoReq, @PathVariable Integer id) {
        ProdutoDTO produto = new ProdutoDTO(
                id,
                produtoReq.getNome(),
                produtoReq.getQuantidade(),
                produtoReq.getValor(),
                produtoReq.getObservacao());
        produto = produtoService.atualizar(id, produto);

        return ResponseEntity.ok(new ProdutoResponse(
                produto.getId(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getValor(),
                produto.getObservacao()));
    }

}
