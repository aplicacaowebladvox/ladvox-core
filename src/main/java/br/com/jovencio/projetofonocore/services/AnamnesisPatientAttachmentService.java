package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IAnamnesisPatientAttachmentRepository;
import br.com.jovencio.projetofonocore.model.dtos.AnamnesisPatientAttachmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.AttachmentDTO;
import br.com.jovencio.projetofonocore.model.entities.AnamnesisPatient;
import br.com.jovencio.projetofonocore.model.entities.AnamnesisPatientAttachment;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnamnesisPatientAttachmentService {

	private final IAnamnesisPatientAttachmentRepository repository;
	private final AttachmentService attachmentService;
	private final AnamnesisPatientService anamnesisPatientService;

	public List<AnamnesisPatientAttachmentDTO> findAllBy( long anamnesisPatientId ) {
		return ModelMapperUtils.mapAllAnamnesisPatientAttachment( repository.findAllByAnamnesisPatientId( anamnesisPatientId ) );
	}

	public AnamnesisPatientAttachmentDTO saveNewForAnamnesisPatient( long anamnesisPatientId, AttachmentDTO attachment ) {
		var entity = new AnamnesisPatientAttachment();
		entity.setAnamnesisPatient( new AnamnesisPatient( anamnesisPatientId ) );
		entity.setAttachment( attachmentService.save( attachment ) );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public AnamnesisPatientAttachmentDTO saveNewForPatient( long patientId, AttachmentDTO attachment ) {
		var anamnesisPatient = anamnesisPatientService.generateNewForAttachment( patientId );
		var entity = new AnamnesisPatientAttachment();
		entity.setAnamnesisPatient( anamnesisPatient );
		entity.setAttachment( attachmentService.save( attachment ) );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public void delete( long id ) {
		var entity = repository.findById( id ).orElseThrow();
		attachmentService.delete( entity.getAttachment().getId() );
		repository.deleteById( entity.getId() );
	}
}
