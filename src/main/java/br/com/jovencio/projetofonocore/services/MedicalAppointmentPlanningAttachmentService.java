package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IMedicalAppointmentPlanningAttachmentRepository;
import br.com.jovencio.projetofonocore.model.dtos.AttachmentDTO;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointmentPlanning;
import br.com.jovencio.projetofonocore.model.entities.MedicalAppointmentPlanningAttachment;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalAppointmentPlanningAttachmentService {

	private final IMedicalAppointmentPlanningAttachmentRepository repository;
	private final AttachmentService attachmentService;

	public List<AttachmentDTO> findAllAttachmentsOfMedicalAppointmentPlanning( long medicalAppointmentPlanningId ) {
		return Optional.ofNullable( repository.findAllByMedicalAppointmentPlanningId( medicalAppointmentPlanningId ) ).orElse( Collections.emptyList() ).stream().map( mapa -> ModelMapperUtils.map( mapa.getAttachment() ) ).toList();
	}

	public AttachmentDTO saveAttachmentForMedicalAppointmentPlanning( long medicalAppointmentPlanningId, AttachmentDTO dto ) {
		var entity = new MedicalAppointmentPlanningAttachment();
		entity.setMedicalAppointmentPlanning( new MedicalAppointmentPlanning( medicalAppointmentPlanningId ) );
		entity.setAttachment( attachmentService.save( dto ) );
		repository.save( entity );
		dto.setId( entity.getAttachment().getId() );
		return dto;
	}

	public void deleteByAttachment( long attachmentId ) {
		repository.deleteAll( repository.findAllByAttachmentId( attachmentId ) );
		attachmentService.delete( attachmentId );
	}
}
