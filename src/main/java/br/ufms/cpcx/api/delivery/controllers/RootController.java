package br.ufms.cpcx.api.delivery.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class RootController {
    @GetMapping
    public RepresentationModel root() {
        var rootResource = new RepresentationModel();
        var pageable = PageRequest.of(0, 10);
        rootResource.add(linkTo(methodOn(RootController.class).root()).withSelfRel());
        rootResource.add(linkTo(methodOn(PedidoController.class).getAllPedidos(pageable)).withRel("pedido"));
        rootResource.add(linkTo(methodOn(ProdutoController.class).getAllProdutos(pageable)).withRel("produto"));
        rootResource.add(linkTo(methodOn(UsuarioController.class).getAllUsuarios(pageable)).withRel("usuario"));
        rootResource.add(linkTo(methodOn(EntregadorController.class).getAllEntregadores(pageable)).withRel("entregador"));
        return rootResource;
    }
}
