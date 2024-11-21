package br.ufms.cpcx.api.delivery.models;

import br.ufms.cpcx.api.delivery.controllers.UsuarioController;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UsuarioRepresentationModelAssembler implements SimpleRepresentationModelAssembler<Usuario> {

    @Override
    public void addLinks(EntityModel<Usuario> resource) {
        Long id = resource.getContent().getId();
        resource.add(linkTo(methodOn(UsuarioController.class).getUsuarioById(id)).withSelfRel());
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Usuario>> resources) {
        var pageable = PageRequest.of(0, 10);
        resources.add(linkTo(methodOn(UsuarioController.class).getAllUsuarios(pageable)).withSelfRel());
    }
}

