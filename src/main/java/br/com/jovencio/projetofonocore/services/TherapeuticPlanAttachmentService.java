package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.ITherapeuticPlanAttachmentRepository;
import br.com.jovencio.projetofonocore.model.dtos.AttachmentDTO;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanAttachmentDTO;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlan;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlanAttachment;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TherapeuticPlanAttachmentService {

	private final ITherapeuticPlanAttachmentRepository repository;
	private final AttachmentService attachmentService;

	public List<TherapeuticPlanAttachmentDTO> findAllBy( long therapeuticPlanId ) {
		return ModelMapperUtils.mapAllTherapeuticPlanAttachment( repository.findAllByTherapeuticPlanId( therapeuticPlanId ) );
	}

	public TherapeuticPlanAttachmentDTO saveNewForTherapeuticPlan( long therapeuticPlanId, AttachmentDTO attachment ) {
		var entity = new TherapeuticPlanAttachment();
		entity.setTherapeuticPlan( new TherapeuticPlan( therapeuticPlanId ) );
		entity.setAttachment( attachmentService.save( attachment ) );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public void deleteByAttachment( long attachmentId ) {
		var entity = repository.findByAttachmentId( attachmentId ).orElseThrow();
		attachmentService.delete( attachmentId );
		repository.deleteById( entity.getId() );
	}
}
