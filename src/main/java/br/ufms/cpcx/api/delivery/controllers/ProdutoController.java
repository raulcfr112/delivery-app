package br.ufms.cpcx.api.delivery.controllers;

import br.ufms.cpcx.api.delivery.dtos.ProdutoDTO;
import br.ufms.cpcx.api.delivery.models.Produto;
import br.ufms.cpcx.api.delivery.models.ProdutoRepresentationModelAssembler;
import br.ufms.cpcx.api.delivery.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/produto")
@AllArgsConstructor
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    final ProdutoRepresentationModelAssembler produtoRepresentationModelAssembler;
    final PagedResourcesAssembler pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Produto>> saveProduto(@RequestBody @Valid ProdutoDTO produtoDto) {
        EntityModel<Produto> produto = produtoRepresentationModelAssembler
                .toModel(produtoService.saveProduto(produtoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Produto>> updateProduto(@PathVariable Long id, @RequestBody @Valid ProdutoDTO produtoDto) {
        Produto updatedProduto = produtoService.updateProduto(id, produtoDto);
        EntityModel<Produto> produto = produtoRepresentationModelAssembler.toModel(updatedProduto);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Produto>> getProdutoById(@PathVariable Long id) {
        Produto produto = produtoService.findProdutoById(id);
        EntityModel<Produto> produtoModel = produtoRepresentationModelAssembler.toModel(produto);
        return ResponseEntity.ok(produtoModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Produto>> getAllProdutos(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var page = produtoService.findAllProdutos(pageable);
        PagedModel<Produto> collModel = pagedResourcesAssembler.toModel(page, produtoRepresentationModelAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }
}

