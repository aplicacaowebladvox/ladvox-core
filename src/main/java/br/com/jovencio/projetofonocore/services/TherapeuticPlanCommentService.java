package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.ITherapeuticPlanCommentRepository;
import br.com.jovencio.projetofonocore.dao.repositories.ITherapeuticPlanRepository;
import br.com.jovencio.projetofonocore.model.auth.entities.User;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanCommentChatViewDTO;
import br.com.jovencio.projetofonocore.model.dtos.TherapeuticPlanTextCommentDTO;
import br.com.jovencio.projetofonocore.model.entities.TherapeuticPlanComment;
import br.com.jovencio.projetofonocore.services.auth.component.AuthComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TherapeuticPlanCommentService {

	private final ITherapeuticPlanCommentRepository repository;
	private final ITherapeuticPlanRepository therapeuticPlanRepository;
	private final AuthComponent authComponent;

	public List<TherapeuticPlanCommentChatViewDTO> findAllByTherapeuticPlanId( long therapeuticPlanId ) {
		return repository.findAllByTherapeuticPlanId( therapeuticPlanId );
	}

	public void newComment( long therapeuticPlanId, TherapeuticPlanTextCommentDTO dto ) {
		if( dto.getTextComment() == null || dto.getTextComment().isEmpty() || dto.getTextComment().isBlank() ) {
			throw new IllegalArgumentException( "Comentário não informado" );
		}
		var therapeuticPlan = therapeuticPlanRepository.findById( therapeuticPlanId ).orElseThrow( () -> new IllegalArgumentException( "Plano teraêutico não encontrado" ) );
		var authUser = authComponent.getUserDTO();
		if( authUser.getAuthorities().stream().noneMatch( a -> "atendimento.plano-terapeutico.comentario.admin".equals( a.getCode() ) ) &&
				therapeuticPlan.getMedicalAppointment().getTeam().getTherapists().stream().noneMatch( u -> u.getId().equals( authUser.getId() ) ) ) {
			throw new IllegalStateException( "Usuário não possui permissão suficiente para efetuar comentários nesse Plano Terapêutico" );
		}
		TherapeuticPlanComment comment = new TherapeuticPlanComment();
		comment.setTherapeuticPlan( therapeuticPlan );
		comment.setUser( new User( authUser.getId() ) );
		comment.setCreatedDate( LocalDateTime.now() );
		comment.setTextComment( dto.getTextComment() );
		repository.save( comment );
	}

	public void delete( long id ) {
		var entity = repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Comentário não encontrado" ) );
		if( !entity.getUser().getId().equals( authComponent.getUserDTO().getId() ) && authComponent.getUserDTO().getAuthorities().stream().noneMatch( a -> a.getCode().equals( "atendimento.plano-terapeutico.comentario.excluir" ) ) ) {
			throw new IllegalStateException( "Você não pode excluir esse comentário" );
		}
		repository.deleteById( id );
	}
}
