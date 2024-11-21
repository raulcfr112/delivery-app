package br.ufms.cpcx.api.delivery.models;

import br.ufms.cpcx.api.delivery.controllers.PedidoController;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoRepresentationModelAssembler implements SimpleRepresentationModelAssembler<Pedido> {

    @Override
    public void addLinks(EntityModel<Pedido> resource) {
        Long id = resource.getContent().getId();
        resource.add(linkTo(methodOn(PedidoController.class).getPedidoById(id)).withSelfRel());
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Pedido>> resources) {
        var pageable = PageRequest.of(0, 10);
        resources.add(linkTo(methodOn(PedidoController.class).getAllPedidos(pageable)).withSelfRel());
    }
}

