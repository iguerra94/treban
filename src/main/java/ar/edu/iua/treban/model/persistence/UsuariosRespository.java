package ar.edu.iua.treban.model.persistence;

import ar.edu.iua.treban.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuariosRespository extends JpaRepository<User, Long> {
	List<User> findByUsername(String username);
}
