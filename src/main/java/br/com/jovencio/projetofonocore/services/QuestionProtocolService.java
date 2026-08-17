package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IQuestionProtocolRepository;
import br.com.jovencio.projetofonocore.model.dtos.QuestionProtocolDTO;
import br.com.jovencio.projetofonocore.model.entities.Protocol;
import br.com.jovencio.projetofonocore.model.entities.QuestionProtocol;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionProtocolService {

	private final IQuestionProtocolRepository repository;

	public List<QuestionProtocolDTO> saveAll( Long protocolId, List<QuestionProtocolDTO> dtos, boolean needValidation ) {
		var entities = ModelMapperUtils.mapAllQuestionProtocolDTO( dtos );
		if( needValidation ) {
			var invalids = entities.stream().map( e -> validate( e.getId(), e ) ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		entities.forEach( e -> e.setProtocol( new Protocol( protocolId ) ) );
		entities = repository.saveAll( entities );
		var forDelete = repository.findAllByProtocolAndIdNotIn( entities.getFirst().getProtocol(), entities.stream().map( QuestionProtocol::getId ).toList() );
		if( !forDelete.isEmpty() ) {
			repository.deleteAllJoinWithQuestionGroupProtocol( forDelete.stream().map( QuestionProtocol::getId ).toList() );
			repository.deleteAll( forDelete );
		}
		return ModelMapperUtils.mapAllQuestionProtocol( entities );
	}

	private List<String> validate( Long id, QuestionProtocol entity ) {
		List<String> errors = new ArrayList<>();
		if( entity.getKey() == null && entity.getId() != null ) {
			errors.add( "Chave não informada" );
		}
		if( entity.getSequence() < 0 ) {
			errors.add( "Sequência inválida" );
		}
		if( entity.getQuestion() == null || entity.getQuestion().isEmpty() ) {
			errors.add( "Pergunta não informada" );
		} else if( entity.getQuestion().length() > 255 ) {
			errors.add( "Pergunta é muito longa(máximo 255 caracteres)" );
		}
		if( entity.getAbbreviation() != null && !entity.getAbbreviation().isEmpty() ) {
			if( entity.getAbbreviation().length() > 10 ) {
				errors.add( "Abreviação muito longa(máximo 10 caracteres)" );
			}
		} else {
			entity.setAbbreviation( null );
		}
		return errors;
	}
}
