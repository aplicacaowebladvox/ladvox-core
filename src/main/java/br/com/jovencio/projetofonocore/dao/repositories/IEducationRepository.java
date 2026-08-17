package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.model.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEducationRepository extends JpaRepository<Education, Long> {

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO("
			+ "e.id, "
			+ "e.name, "
			+ "e.hasOtherDefinition "
			+ ") "
			+ "FROM Education e ")
	List<SelectOptionExtraAttributeDTO<Long, String, Boolean>> queryAllForSelectOptions();
}
