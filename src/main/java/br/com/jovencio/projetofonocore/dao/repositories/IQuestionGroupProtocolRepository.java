package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.QuestionGroupProtocol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IQuestionGroupProtocolRepository extends JpaRepository<QuestionGroupProtocol, Long> {

	@Transactional
	@Modifying
	@Query( value = " DELETE FROM m2m_questiongroupsprotocol_questionsprotocol WHERE id_question_group_protocol IN ( "
			+ "    SELECT _i1.id_question_group_protocol FROM QUESTION_GROUPS_PROTOCOL _i1 WHERE _i1.id_protocol = :protocolId AND _i1.id_question_group_protocol NOT IN (:ids) "
			+ " ); "
			+ " DELETE FROM base_results_question_group_protocol _i2 WHERE _i2.id_question_group_protocol IN ( "
			+ "    SELECT _i1.id_question_group_protocol FROM QUESTION_GROUPS_PROTOCOL _i1 WHERE _i1.id_protocol = :protocolId AND _i1.id_question_group_protocol NOT IN (:ids) "
			+ " ); "
			+ " DELETE FROM m2m_questiongroupsprotocol_functionscalculator _i3 WHERE _i3.id_question_group_protocol IN ( "
			+ "    SELECT _i1.id_question_group_protocol FROM QUESTION_GROUPS_PROTOCOL _i1 WHERE _i1.id_protocol = :protocolId AND _i1.id_question_group_protocol NOT IN (:ids) "
			+ " ); "
			+ " DELETE FROM QUESTION_GROUPS_PROTOCOL WHERE id_protocol = :protocolId AND question_groups_protocol.id_question_group_protocol NOT IN (:ids) ", nativeQuery = true )
	void deleteByProtocolIdAndIdsNotIntList( long protocolId, List<Long> ids );
}
