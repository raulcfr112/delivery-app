package br.ufms.cpcx.api.delivery.controllers;

import br.ufms.cpcx.api.delivery.dtos.EntregadorDTO;
import br.ufms.cpcx.api.delivery.models.Entregador;
import br.ufms.cpcx.api.delivery.models.EntregadorRepresentationModelAssembler;
import br.ufms.cpcx.api.delivery.service.EntregadorService;
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
@RequestMapping("/api/entregador")
@AllArgsConstructor
public class EntregadorController {

    @Autowired
    private EntregadorService entregadorService;

    final EntregadorRepresentationModelAssembler entregadorRepresentationModelAssembler;
    final PagedResourcesAssembler pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Entregador>> saveEntregador(@RequestBody @Valid EntregadorDTO entregadorDto) {
        EntityModel<Entregador> entregador = entregadorRepresentationModelAssembler.toModel(entregadorService.saveEntregador(entregadorDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(entregador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Entregador>> updateEntregador(@PathVariable Long id, @RequestBody @Valid EntregadorDTO entregadorDto) {
        Entregador updatedEntregador = entregadorService.updateEntregador(id, entregadorDto);
        EntityModel<Entregador> entregador = entregadorRepresentationModelAssembler.toModel(updatedEntregador);
        return ResponseEntity.ok(entregador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntregador(@PathVariable Long id) {
        entregadorService.deleteEntregador(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Entregador>> getEntregadorById(@PathVariable Long id) {
        Entregador entregador = entregadorService.findEntregadorById(id);
        EntityModel<Entregador> entregadorModel = entregadorRepresentationModelAssembler.toModel(entregador);
        return ResponseEntity.ok(entregadorModel);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Entregador>> getAllEntregadores(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var page = entregadorService.findAllEntregadores(pageable);
        PagedModel<Entregador> collModel = pagedResourcesAssembler.toModel(page, entregadorRepresentationModelAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }
}
