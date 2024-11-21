package br.ufms.cpcx.api.delivery.service;

import br.ufms.cpcx.api.delivery.dtos.ProdutoDTO;
import br.ufms.cpcx.api.delivery.models.Produto;
import br.ufms.cpcx.api.delivery.repositories.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto saveProduto(ProdutoDTO produtoDto) {
        Produto produto = new Produto();
        produto.setCategoria(produtoDto.getCategoria());
        produto.setValor(produtoDto.getValor());
        produto.setDescricao(produtoDto.getDescricao());

        return produtoRepository.save(produto);
    }

    @Transactional
    public Produto updateProduto(Long id, ProdutoDTO produtoDto) {
        Produto produto = findProdutoById(id);

        produto.setDescricao(produtoDto.getDescricao());
        produto.setValor(produtoDto.getValor());
        produto.setCategoria(produtoDto.getCategoria());

        return produtoRepository.save(produto);
    }

    @Transactional
    public void deleteProduto(Long id) {
        Produto produto = findProdutoById(id);

        produtoRepository.delete(produto);
    }

    public Produto findProdutoById(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado com o ID: " + id));
    }

    public Page<Produto> findAllProdutos(Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }
}

