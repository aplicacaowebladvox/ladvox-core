package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IAnswerTypeProtocolRepository;
import br.com.jovencio.projetofonocore.model.dtos.AnswerTypeProtocolDTO;
import br.com.jovencio.projetofonocore.model.entities.AnswerTypeProtocol;
import br.com.jovencio.projetofonocore.model.entities.Protocol;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerTypeProtocolService {

	private final IAnswerTypeProtocolRepository repository;
	private final AcceptedValueAnswerTypeProtocolService acceptedValueAnswerTypeProtocolService;

	public List<AnswerTypeProtocolDTO> saveAll( long protocolId, List<AnswerTypeProtocolDTO> dtos ) {
		var entities = ModelMapperUtils.mapAllAnswerTypeProtocolDTO( dtos );
		validate( protocolId, entities );
		for( var entity : entities ) {
			entity.setProtocol( new Protocol( protocolId ) );
			var entityTemp = repository.save( entity );
			entity.setId( entityTemp.getId() );
			for( var av : entity.getAcceptableValues() ) {
				av.setAnswerTypeProtocol( entity );
			}
			entity.setAcceptableValues( acceptedValueAnswerTypeProtocolService.saveAll( entity.getAcceptableValues(), false ) );
		}
		acceptedValueAnswerTypeProtocolService.deleteByProtocolIdAndIdNotIn( protocolId, entities.stream().map( AnswerTypeProtocol::getId ).toList() );
		repository.deleteByProtocolIdAndIdNotIn( protocolId, entities.stream().map( AnswerTypeProtocol::getId ).toList() );
		return ModelMapperUtils.mapAllAnswerTypeProtocol( entities );
	}

	private void validate( long protocolId, List<AnswerTypeProtocol> entities ) {
		if( entities == null || entities.isEmpty() ) {
			throw new IllegalArgumentException( "Tipos de resposta não informados" );
		}
		var errors = new ArrayList<String>();
		var answerTypeProtocolPosition = 1;
		for( var entity : entities ) {
			var childErrors = validate( protocolId, entity );
			if( !childErrors.isEmpty() ) {
				errors.add( "Tipo de resposta[" + answerTypeProtocolPosition + "]: " + childErrors );
			}
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

	private List<String> validate( long protocolId, AnswerTypeProtocol entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Tipo de resposta não informado" );
		}
		var errors = new ArrayList<String>();
		if( entity.getKey() == null && entity.getId() != null ) {
			throw new IllegalArgumentException( "Chave não informada" );
		}
		if( entity.getSequence() < 0 ) {
			throw new IllegalArgumentException( "Sequência inválida: " + entity.getSequence() );
		}
		if( entity.getName() == null || entity.getName().isEmpty() ) {
			entity.setName( null );
		} else if( entity.getName().length() > 120 ) {
			throw new IllegalArgumentException( "Nome muito longo(máximo 120 caracteres)" );
		}
		if( entity.getAcceptableValues() == null || entity.getAcceptableValues().isEmpty() ) {
			throw new IllegalArgumentException( "Valores aceitos não informados" );
		} else {
			var childErrors = acceptedValueAnswerTypeProtocolService.validate( entity.getId(), entity.getAcceptableValues() );
			if( !childErrors.isEmpty() ) {
				errors.add( "Valor aceito: " + childErrors );
			}
		}
		if( entity.getAnswerValueType() == null ) {
			throw new IllegalArgumentException( "Tipo de valor não informado" );
		}
		if( entity.getProtocol() != null && entity.getProtocol().getId() != protocolId ) {
			throw new IllegalArgumentException( "ID do Protocolo não pode ser alterado" );
		}

		return errors;
	}
}
