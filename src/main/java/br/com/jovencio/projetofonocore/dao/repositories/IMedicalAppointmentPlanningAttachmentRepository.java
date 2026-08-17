package br.com.jovencio.projetofonocore.dao.repositories;

import br.com.jovencio.projetofonocore.model.entities.MedicalAppointmentPlanningAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMedicalAppointmentPlanningAttachmentRepository extends JpaRepository<MedicalAppointmentPlanningAttachment, Long> {

	@Query(value = "SELECT mapa FROM MedicalAppointmentPlanningAttachment mapa WHERE mapa.medicalAppointmentPlanning.id = :medicalAppointmentPlanningId ")
	List<MedicalAppointmentPlanningAttachment> findAllByMedicalAppointmentPlanningId( long medicalAppointmentPlanningId );

	@Query(value = "SELECT mapa FROM MedicalAppointmentPlanningAttachment mapa WHERE mapa.attachment.id = :attachmentId ")
	List<MedicalAppointmentPlanningAttachment> findAllByAttachmentId( long attachmentId );
}
