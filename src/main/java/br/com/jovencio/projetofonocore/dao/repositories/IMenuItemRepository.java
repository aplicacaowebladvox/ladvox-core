package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMenuItemRepository extends JpaRepository<MenuItem, Long> {

	@Query(value = "SELECT mi "
			+ "FROM MenuItem mi "
			+ "WHERE mi.parent IS NULL ")
	List<MenuItem> findAllParentIsNull();
}
