package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IProtocolRepository;
import br.com.jovencio.projetofonocore.model.dtos.*;
import br.com.jovencio.projetofonocore.model.entities.Protocol;
import br.com.jovencio.projetofonocore.model.grid.ProtocolGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProtocolService {

	private final IProtocolRepository repository;

	private final AnswerTypeProtocolService answerTypeProtocolService;
	private final QuestionProtocolService questionProtocolService;
	private final QuestionGroupProtocolService questionGroupProtocolService;
	private final ProtocolCalculatorService protocolCalculatorService;

	public SearchReturnDTO<ProtocolGrid> search( TermGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public Object get( Long id, Integer step ) {
		if( step == 1 ) {
			return repository.findById( id ).map( ModelMapperUtils::mapFirstStep ).orElseThrow();
		} else if( step == 2 ) {
			return repository.findById( id ).map( ModelMapperUtils::mapSecondStep ).orElseThrow();
		} else if( step == 3 ) {
			return repository.findById( id ).map( ModelMapperUtils::mapThirdStep ).orElseThrow();
		} else if( step == 4 ) {
			return repository.findById( id ).map( ModelMapperUtils::mapFourthStep ).orElseThrow();
		}
		return repository.findById( id ).map( ModelMapperUtils::mapFirstStep ).orElseThrow();
	}

	public ProtocolFirstStepDTO save( ProtocolFirstStepDTO dto ) {
		var entity = ModelMapperUtils.mapFirstStep( dto );
		validate( entity );
		return ModelMapperUtils.mapFirstStep( repository.save( entity ) );
	}

	public ProtocolFirstStepDTO save( long id, ProtocolFirstStepDTO dto ) {
		var entity = ModelMapperUtils.mapFirstStep( dto );
		validate( id, entity );
		repository.updateFirstStep( id, entity );
		return ModelMapperUtils.mapFirstStep( entity );
	}

	public ProtocolSecondStepDTO saveSecond( long id, ProtocolSecondStepDTO dto ) {
		dto.setAnswersType( answerTypeProtocolService.saveAll( id, dto.getAnswersType() ) );
		return dto;
	}

	public ProtocolThirdStepDTO saveThird( long id, ProtocolThirdStepDTO dto ) {
		dto.setQuestions( questionProtocolService.saveAll( id, dto.getQuestions(), true ) );
		return dto;
	}

	public ProtocolFourthStepDTO saveFourth( long id, ProtocolFourthStepDTO dto ) {
		dto.setGroups( questionGroupProtocolService.saveAll( id, dto.getGroups(), true ) );
		return dto;
	}

	public void delete( Long id ) {
		repository.findById( id ).orElseThrow();
		repository.deleteById( id );
	}

	public MyProtocolDTO getMyDataById( long id ) {
		return repository.findById( id ).map( ModelMapperUtils::mapMy ).orElseThrow();
	}

	private void validate( Protocol entity ) {
		validate( null, entity );
	}

	private void validate( Long id, Protocol entity ) {
		var isUpdate = id != null;
		if( entity == null ) {
			throw new IllegalArgumentException( "Protocolo não informado" );
		}
		List<String> errors = new ArrayList<>();

		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}

		if( entity.getAbbreviation() == null || entity.getAbbreviation().isEmpty() ) {
			errors.add( "Abreviação não informada" );
		} else if( entity.getAbbreviation().length() > 10 ) {
			errors.add( "Abreviação muito longa(máximo 10 caracteres)" );
		}

		if( entity.getDescription() == null || entity.getDescription().isEmpty() ) {
			entity.setDescription( null );
		} else if( entity.getDescription().length() > 4000 ) {
			errors.add( "Descrição muito longa(máximo 4000 caracteres)" );
		}

		if( entity.getInitialValidity() == null ) {
			errors.add( "Validade inicial não informada" );
		}

		if( entity.getInitialValidity() != null && entity.getFinalValidity() != null && entity.getInitialValidity().isAfter( entity.getFinalValidity() ) ) {
			errors.add( "Validade final precisar ser depois da inicial" );
		}

		if( isUpdate ) {
			var entityFromDatabase = repository.findById( id );
			if( !entity.getKey().equals( entityFromDatabase.orElseThrow().getKey() ) ) {
				errors.add( "Chave não pode ser alterada: " + entity.getKey() );
			}
		}

		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

	public List<SelectOptionDTO<Long, String>> getSelectOptions() {
		return repository.getSelectOptions();
	}

	public ProtocolCalculatedDTO calculate( long id, List<AnswerQuestionProtocolDTO> answers ) {
		if( answers.stream()
				.anyMatch( e -> e.getAnswer() == null || e.getAnswerValueType() == null || ( e.getQuestionId() == null || e.getQuestionId().compareTo( 0L ) <= 0 ) || ( e.getAnswerTypeId() == null
						|| e.getAnswerTypeId().compareTo( 0L ) <= 0 ) ) ) {
			throw new IllegalArgumentException( "Há perguntas sem respostas" );
		}
		return protocolCalculatorService.calulate( repository.findById( id ).orElseThrow( () -> new IllegalArgumentException( "Protocolo não encontrado" ) ), answers.stream().map( ModelMapperUtils::map ).toList(), -1L );
	}
}
