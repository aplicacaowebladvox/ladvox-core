package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.AcceptedValueAnswerTypeProtocol;
import br.com.jovencio.projetofonocore.model.entities.AnswerTypeProtocol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAcceptedValueAnswerTypeProtocolRepository extends JpaRepository<AcceptedValueAnswerTypeProtocol, Long> {

	List<AcceptedValueAnswerTypeProtocol> findAllByAnswerTypeProtocolAndIdNotIn( AnswerTypeProtocol answerTypeProtocol, List<Long> id );

	@Transactional
	@Modifying
	@Query( "DELETE FROM AcceptedValueAnswerTypeProtocol WHERE answerTypeProtocol.protocol.id = :protocolId AND answerTypeProtocol.id NOT IN (:answerTypeProtocolIds)" )
	void deleteByProtocolIdAndIdNotIn( long protocolId, List<Long> answerTypeProtocolIds );
}
