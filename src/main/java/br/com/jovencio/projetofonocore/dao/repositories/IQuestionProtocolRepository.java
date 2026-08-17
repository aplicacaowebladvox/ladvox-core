package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.Protocol;
import br.com.jovencio.projetofonocore.model.entities.QuestionProtocol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IQuestionProtocolRepository extends JpaRepository<QuestionProtocol, Long> {

	List<QuestionProtocol> findAllByProtocolAndIdNotIn( Protocol protocol, List<Long> ids );

	@Transactional
	@Modifying
	@Query( value = "DELETE FROM m2m_questiongroupsprotocol_questionsprotocol "
			+ "WHERE id_question_protocol IN (:ids) ", nativeQuery = true )
	void deleteAllJoinWithQuestionGroupProtocol( List<Long> ids );
}
