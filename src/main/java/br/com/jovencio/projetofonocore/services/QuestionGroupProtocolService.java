package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IQuestionGroupProtocolRepository;
import br.com.jovencio.projetofonocore.model.dtos.QuestionGroupProtocolDTO;
import br.com.jovencio.projetofonocore.model.entities.Protocol;
import br.com.jovencio.projetofonocore.model.entities.QuestionGroupProtocol;
import br.com.jovencio.projetofonocore.utils.ConvertUtils;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionGroupProtocolService {

	private final IQuestionGroupProtocolRepository repository;
	private final BaseResultQuestionGroupProtocolService baseResultQuestionGroupProtocolService;

	public List<QuestionGroupProtocolDTO> saveAll( long protocolId, List<QuestionGroupProtocolDTO> dtos, boolean needValidation ) {
		var entities = ModelMapperUtils.mapAllQuestionGroupProtocolDTO( dtos );
		if( needValidation ) {
			validate( protocolId, entities );
		}
		for( var entity : entities ) {
			entity.setProtocol( new Protocol( protocolId ) );
			var entityWithId = repository.save( entity );
			for( var b : entity.getBaseResults() ) {
				b.setQuestionGroupProtocol( entityWithId );
			}
			entity.setBaseResults( baseResultQuestionGroupProtocolService.saveAll( entity.getBaseResults(), false ) );
		}
		var ids = entities.stream().map( QuestionGroupProtocol::getId ).toList();
		repository.deleteByProtocolIdAndIdsNotIntList( protocolId, ids );
		return ModelMapperUtils.mapAllQuestionGroupProtocol( entities );
	}

	private void validate( long protocolId, List<QuestionGroupProtocol> entities ) {
		if( entities == null || entities.isEmpty() ) {
			throw new IllegalArgumentException( "Grupo de perguntas não informado" );
		}
		List<String> errors = new ArrayList<>();
		int position = 1;
		for( QuestionGroupProtocol entity : entities ) {
			List<String> childErrors = validate( entity.getId(), entity );
			if( !childErrors.isEmpty() ) {
				errors.add( "Grupo de pergunta do protocolo[" + position + "]: " + childErrors );
			}
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

	private List<String> validate( Long id, QuestionGroupProtocol entity ) {
		if( entity == null ) {
			return List.of( "Grupo de pergunta do protocol não informado" );
		}
		List<String> errors = new ArrayList<>();

		if( id != null && entity.getKey() == null ) {
			errors.add( "Chave não informada" );
		}
		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}
		if( entity.getBaseResults() != null && !entity.getBaseResults().isEmpty() ) {
			var childErrors = baseResultQuestionGroupProtocolService.validate( entity.getId(), entity.getBaseResults() );
			if( !childErrors.isEmpty() ) {
				errors.add( "Resultados Base: " + childErrors );
			}
		}
		if( entity.getQuestions() == null || entity.getQuestions().isEmpty() ) {
			if( entity.getKey() != null ) {
				if( entity.getFunctions().stream().anyMatch( f -> f.getFunctionText().contains( ConvertUtils.removeOperators( entity.getKey() ) ) ) ) {
					errors.add( "Perguntas não informadas" );
				}
			}
		}

		return errors;
	}

}
