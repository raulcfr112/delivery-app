package br.ufms.cpcx.api.delivery.repositories;

import br.ufms.cpcx.api.delivery.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailAndSenha(String email, String senha);

    boolean existsByEmail(String email);
}
