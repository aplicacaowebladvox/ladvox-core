package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO;
import br.com.jovencio.projetofonocore.model.entities.EmailContact;
import br.com.jovencio.projetofonocore.model.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IGenderRepository extends JpaRepository<Gender, Long> {


	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.SelectOptionExtraAttributeDTO("
			+ "g.id, "
			+ "g.name, "
			+ "g.hasOtherDefinition "
			+ ") "
			+ "FROM Gender g ")
	List<SelectOptionExtraAttributeDTO<Long, String, Boolean>> queryAllForSelectOptions();
}
