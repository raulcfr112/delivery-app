package br.ufms.cpcx.api.delivery.controllers;

import br.ufms.cpcx.api.delivery.dtos.PedidoDTO;
import br.ufms.cpcx.api.delivery.models.Pedido;
import br.ufms.cpcx.api.delivery.models.PedidoRepresentationModelAssembler;
import br.ufms.cpcx.api.delivery.service.PedidoService;
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
@RequestMapping("/api/pedido")
@AllArgsConstructor
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    final PedidoRepresentationModelAssembler pedidoRepresentationModelAssembler;
    final PagedResourcesAssembler pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<Pedido>> savePedido(@RequestBody @Valid PedidoDTO pedidoDto) {
        EntityModel<Pedido> pedido = pedidoRepresentationModelAssembler
                .toModel(pedidoService.savePedido(pedidoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Pedido>> getAllPedidos(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var page = pedidoService.findAllPedidos(pageable);
        PagedModel<Pedido> collModel = pagedResourcesAssembler.toModel(page, pedidoRepresentationModelAssembler);
        return new ResponseEntity<>(collModel, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pedido>> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.findPedidoById(id);
        EntityModel<Pedido> pedidoModel = pedidoRepresentationModelAssembler.toModel(pedido);
        return ResponseEntity.ok(pedidoModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Pedido>> updatePedido(@PathVariable Long id, @RequestBody @Valid PedidoDTO pedidoDto) {
        Pedido updatedPedido = pedidoService.updatePedido(id, pedidoDto);
        EntityModel<Pedido> pedido = pedidoRepresentationModelAssembler.toModel(updatedPedido);
        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        pedidoService.deletePedido(id);
        return ResponseEntity.noContent().build();
    }
}
