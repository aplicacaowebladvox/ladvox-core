package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IBaseResultQuestionGroupProtocolRepository;
import br.com.jovencio.projetofonocore.model.entities.BaseResultQuestionGroupProtocol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseResultQuestionGroupProtocolService {

	private final IBaseResultQuestionGroupProtocolRepository repository;

	public List<BaseResultQuestionGroupProtocol> saveAll( List<BaseResultQuestionGroupProtocol> entities, boolean needValidation ) {
		if( needValidation ) {
			var invalids = entities.stream().map( e -> validate( e.getQuestionGroupProtocol() == null ? null : e.getQuestionGroupProtocol().getId(), e.getId(), e ) ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		return repository.saveAll( entities );
	}

	public List<String> validate( Long questionGroupProtocolId, List<BaseResultQuestionGroupProtocol> entities ) {
		if( entities == null || entities.isEmpty() ) {
			return List.of( "Resultados Base não informados" );
		}
		List<String> errors = new ArrayList<>();
		var position = 1;
		for( var entity : entities ) {
			var childErrors = validate( questionGroupProtocolId, entity.getId(), entity );
			if( !childErrors.isEmpty() ) {
				errors.add( "Resultado Base[" + position + "]: " + childErrors );
			}
		}
		return errors;
	}

	public List<String> validate( Long questionGroupProtocolId, Long id, BaseResultQuestionGroupProtocol entity ) {
		if( entity == null ) {
			return List.of( "Resultado Base não informado" );
		}
		List<String> errors = new ArrayList<>();
		if( id != null && entity.getKey() == null ) {
			errors.add( "Chave não informada" );
		}
		if( entity.getName() == null || entity.getName().trim().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}
		if( entity.isRange() ) {
			if( entity.getInitialValue() == null && entity.getFinalValue() == null ) {
				errors.add( "Valor inicial e final não informado" );
			} else if( entity.getInitialValue() != null && entity.getFinalValue() != null ) {
				if( entity.getInitialValue().compareTo( entity.getFinalValue() ) > 0 ) {
					errors.add( "Valor inicial é maior que o valor final" );
				}
			}
		} else {
			if( entity.getInitialValue() == null ) {
				errors.add( "Valor não informado" );
			}
			if( entity.getFinalValue() != null ) {
				errors.add( "Resultado não é intervalo: valor final não deve ser informado" );
			}
		}
		if( entity.getQuestionGroupProtocol() != null && entity.getQuestionGroupProtocol().getId().compareTo( questionGroupProtocolId ) != 0 ) {
			errors.add( "ID do Grupo de Perguntas do protocolo não pode ser alterado" );
		}
		return errors;
	}
}
