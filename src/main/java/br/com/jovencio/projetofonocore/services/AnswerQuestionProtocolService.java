package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IAnswerQuestionProtocolRepository;
import br.com.jovencio.projetofonocore.model.dtos.AnswerQuestionProtocolDTO;
import br.com.jovencio.projetofonocore.model.entities.ProtocolTherapeuticPlan;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerQuestionProtocolService {

	private final IAnswerQuestionProtocolRepository repository;

	public void save( ProtocolTherapeuticPlan protocolTherapeuticPlan, List<AnswerQuestionProtocolDTO> dtos ) {
		var entities = dtos.stream().map( ModelMapperUtils::map ).toList();
		if( entities.stream()
				.anyMatch( e -> e.getAnswer() == null || e.getAnswerValueType() == null || ( e.getQuestion() == null || e.getQuestion().getId() == null || e.getQuestion().getId().compareTo( 0L ) <= 0 ) || ( e.getAnswerType() == null || e.getAnswerType().getId() == null
						|| e.getAnswerType().getId().compareTo( 0L ) <= 0 ) ) ) {
			throw new IllegalArgumentException( "Há perguntas sem respostas" );
		}
		if( protocolTherapeuticPlan.getAnswers() != null && !protocolTherapeuticPlan.getAnswers().isEmpty() ) {
			protocolTherapeuticPlan.getAnswers()
					.forEach( savedAnswer -> entities.stream().filter( e -> e.getQuestion().getId().equals( savedAnswer.getQuestion().getId() ) && e.getAnswerType().getId().equals( savedAnswer.getAnswerType().getId() ) ).findFirst().ifPresent( e -> e.setId( savedAnswer.getId() ) ) );
		}
		entities.forEach( e -> {
			e.setProtocolTherapeuticPlan( protocolTherapeuticPlan );
			e.setKey( Optional.ofNullable( e.getKey() ).orElse( UUID.randomUUID() ) );
		} );
		repository.saveAll( entities );
	}
}
