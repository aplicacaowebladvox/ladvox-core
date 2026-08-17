package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.auth.entities.Permission;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IPermissionRepository extends JpaRepository<Permission, UUID> {

	List<Permission> findByName( String name );

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO("
			+ "p.id, "
			+ "p.name "
			+ ") "
			+ "FROM Permission p "
			+ "WHERE p.code not like 'paciente-view.%'" )
	List<SelectOptionDTO<UUID, String>> queryAllForSelectOptions();

}
