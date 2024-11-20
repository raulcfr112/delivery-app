package br.ufms.cpcx.api.delivery.repositories;

import br.ufms.cpcx.api.delivery.models.Entregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
}
