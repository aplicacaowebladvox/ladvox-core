package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IAcceptedValueAnswerTypeProtocolRepository;
import br.com.jovencio.projetofonocore.model.entities.AcceptedValueAnswerTypeProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcceptedValueAnswerTypeProtocolService {

	private final IAcceptedValueAnswerTypeProtocolRepository repository;

	public List<AcceptedValueAnswerTypeProtocol> saveAll( List<AcceptedValueAnswerTypeProtocol> entities, boolean needValidation ) {
		if( needValidation ) {
			var invalids = entities.stream().map( e -> validate( null, e ) ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		entities = repository.saveAll( entities );
		var forDelete = repository.findAllByAnswerTypeProtocolAndIdNotIn( entities.getFirst().getAnswerTypeProtocol(), entities.stream().map( AcceptedValueAnswerTypeProtocol::getId ).toList() );
		if( !forDelete.isEmpty() ) {
			repository.deleteAll( forDelete );
		}
		return entities;
	}

	public List<String> validate( Long answerTypeProtocolId, List<AcceptedValueAnswerTypeProtocol> entities ) {
		if( entities == null || entities.isEmpty() ) {
			return List.of( "Valores aceitos do tipo de resposta não informados" );
		}
		List<String> errors = new ArrayList<>();

		int i = 1;
		for( AcceptedValueAnswerTypeProtocol entity : entities ) {
			List<String> childErrors = validate( answerTypeProtocolId, entity );
			if( !childErrors.isEmpty() ) {
				errors.add( "Valor aceito do tipo de resposta[" + i + "]: " + childErrors );
			}
			i++;
		}

		return errors;
	}

	private List<String> validate( Long answerTypeProtocolId, AcceptedValueAnswerTypeProtocol entity ) {
		List<String> errors = new ArrayList<>();
		if( entity.getKey() == null && entity.getId() != null ) {
			errors.add( "Chave não informada" );
		}
		if( entity.getValue() == null ) {
			errors.add( "Valor não informado" );
		}
		if( entity.getPresentation() != null && !entity.getPresentation().isEmpty() ) {
			if( entity.getPresentation().length() > 120 ) {
				errors.add( "Valor de apresentação muito longo(máximo 120 caracteres)" );
			}
		} else {
			entity.setPresentation( null );
		}
		if( entity.getPresentation() != null && entity.getValue() != null && entity.getPresentation().equalsIgnoreCase( entity.getValue().toString() ) ) {
			errors.add( "Valor de apresentação deve ser diferente do valor" );
		}
		if( answerTypeProtocolId != null && entity.getAnswerTypeProtocol() != null && !answerTypeProtocolId.equals( entity.getAnswerTypeProtocol().getId() ) ) {
			errors.add( "ID do tipo de resposta não pode ser alterado" );
		}
		return errors;
	}

	public void deleteByProtocolIdAndIdNotIn( long protocolId, List<Long> answerTypeProtocolIds ) {
		repository.deleteByProtocolIdAndIdNotIn( protocolId, answerTypeProtocolIds );
	}
}
