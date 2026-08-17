package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanCommentChatViewDTO;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlanComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ITherapeuticPlanCommentRepository extends JpaRepository<TherapeuticPlanComment, Long> {

	@Query( value = "SELECT new br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanCommentChatViewDTO( "
			+ "therapeuticPlanComment.id, "
			+ "therapeuticPlanComment.therapeuticPlan.id, "
			+ "user.id, "
			+ "user.name, "
			+ "profileImage, "
			+ "therapeuticPlanComment.createdDate, "
			+ "therapeuticPlanComment.textComment ) "
			+ "FROM TherapeuticPlanComment therapeuticPlanComment "
			+ "JOIN therapeuticPlanComment.user user "
			+ "LEFT JOIN user.profileImage profileImage "
			+ "WHERE therapeuticPlanComment.therapeuticPlan.id = :therapeuticPlanId ORDER BY therapeuticPlanComment.createdDate ASC" )
	List<TherapeuticPlanCommentChatViewDTO> findAllByTherapeuticPlanId( long therapeuticPlanId );
}
