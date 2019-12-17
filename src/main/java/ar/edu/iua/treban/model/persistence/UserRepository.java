package ar.edu.iua.treban.model.persistence;

import ar.edu.iua.treban.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	@Query("SELECT u FROM User u JOIN u.roles WHERE u.username = ?1")
	User findByUsername(String username);

	User findByEmail(String email);
}
