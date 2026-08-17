package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.AnswerTypeProtocol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAnswerTypeProtocolRepository extends JpaRepository<AnswerTypeProtocol, Long> {

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM AnswerTypeProtocol "
			+ "WHERE protocol.id = :protocolId"
			+ "  AND id not in (:list) ")
	void deleteByProtocolIdAndIdNotIn( long protocolId, List<Long> list );
}
