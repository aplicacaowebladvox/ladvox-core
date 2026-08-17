package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlanAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITherapeuticPlanAttachmentRepository extends JpaRepository<TherapeuticPlanAttachment, Long> {

	@Query( value = "SELECT tpa FROM TherapeuticPlanAttachment tpa WHERE tpa.therapeuticPlan.id = :therapeuticPlanId " )
	List<TherapeuticPlanAttachment> findAllByTherapeuticPlanId( long therapeuticPlanId );

	@Query( value = "SELECT tpa FROM TherapeuticPlanAttachment tpa WHERE tpa.attachment.id = :attachmentId" )
	Optional<TherapeuticPlanAttachment> findByAttachmentId( long attachmentId );
}
