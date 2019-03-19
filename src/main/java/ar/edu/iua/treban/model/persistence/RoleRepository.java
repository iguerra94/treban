package ar.edu.iua.treban.model.persistence;

import ar.edu.iua.treban.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "SELECT * FROM roles",nativeQuery = true)
    List<Role> getAll();

}
