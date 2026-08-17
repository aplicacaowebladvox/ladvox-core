package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IProtocolTherapeuticPlanRepository;
import br.com.jovencio.projetofonocore.model.dtos.ProtocolCalculatedDTO;
import br.com.jovencio.projetofonocore.model.dtos.ProtocolCalculatedGroupDTO;
import br.com.jovencio.projetofonocore.model.dtos.ProtocolCalculatedGroupFunctionDTO;
import br.com.jovencio.projetofonocore.model.dtos.ProtocolCalculatedGroupFunctionEvaluatedDTO;
import br.com.jovencio.projetofonocore.model.entities.*;
import br.com.jovencio.projetofonocore.model.enums.FunctionCalculatorType;
import br.com.jovencio.projetofonocore.utils.ConvertUtils;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProtocolCalculatorService {

	private final IProtocolTherapeuticPlanRepository protocolTherapeuticPlanRepository;

	public ProtocolCalculatedDTO calulate( long protocolTherapeuticPlanId ) {
		var protocolTherapeuticPlan = protocolTherapeuticPlanRepository.findById( protocolTherapeuticPlanId ).orElseThrow();
		var protocol = protocolTherapeuticPlan.getProtocol();
		var answers = protocolTherapeuticPlan.getAnswers();
		return calulate( protocol, answers, protocolTherapeuticPlanId );
	}

	public ProtocolCalculatedDTO calulate( Protocol protocol, List<AnswerQuestionProtocol> answers, long protocolTherapeuticPlanId ) {
		var groups = protocol.getGroups();

		Map<String, Double> groupsKeyEvalueted = new HashMap<>();

		var protocolCalculator = new ProtocolCalculatedDTO();
		protocolCalculator.setId( protocol.getId() );
		protocolCalculator.setName( protocol.getName() );
		protocolCalculator.setAbbreviation( protocol.getAbbreviation() );
		protocolCalculator.setProtocolTherapeuticPlanId( protocolTherapeuticPlanId );
		protocolCalculator.setAnswersType( ModelMapperUtils.mapAllAnswerTypeProtocol( protocol.getAnswersType() ) );
		protocolCalculator.setGroups( new ArrayList<>() );
		ProtocolCalculatedGroupDTO protocolCalculatorGroup = null;
		for( var group : groups ) {
			protocolCalculatorGroup = new ProtocolCalculatedGroupDTO();
			protocolCalculatorGroup.setGroupId( group.getId() );
			protocolCalculatorGroup.setGroupName( group.getName() );
			protocolCalculatorGroup.setGroupBaseResults( ModelMapperUtils.mapAllBaseResultQuestionGroupProtocol( group.getBaseResults() ) );
			protocolCalculatorGroup.setGroupFunctions( new ArrayList<>() );
			for( var function : group.getFunctions() ) {
				var protocolCalculatorGroupFunction = new ProtocolCalculatedGroupFunctionDTO();

				protocolCalculatorGroupFunction.setId( function.getId() );
				protocolCalculatorGroupFunction.setName( function.getName() );
				protocolCalculatorGroupFunction.setFunctionText( function.getFunctionText() );
				protocolCalculatorGroupFunction.setType( function.getType() );
				protocolCalculatorGroupFunction.setValues( new ArrayList<>() );
				var keyWithoutOperators = ConvertUtils.removeOperators( group.getKey() );
				for( var answerType : protocol.getAnswersType() ) {
					if( protocolCalculatorGroupFunction.getType() == FunctionCalculatorType.EQUATION ) {
						var protocolCalculatorGroupFunctionEvaluatedDTO = new ProtocolCalculatedGroupFunctionEvaluatedDTO();
						protocolCalculatorGroupFunctionEvaluatedDTO.setAnswersTypeId( answerType.getId() );
						protocolCalculatorGroupFunctionEvaluatedDTO.setFunctionTextEvaluated( protocolCalculatorGroupFunction.getFunctionText() );

						var groupSumValue = getGroupSumValue( answerType, group, answers );
						groupsKeyEvalueted.put( String.format( "@%s#groupSum", keyWithoutOperators ), groupSumValue );

						var groupQuestionsLengthValue = getGroupQuestionsLengthValue( group );
						groupsKeyEvalueted.put( String.format( "@%s#groupQuestionsLength", keyWithoutOperators ), groupQuestionsLengthValue );

						var groupMaxSumValue = getGroupMaxSumValue( answerType, group );
						groupsKeyEvalueted.put( String.format( "@%s#groupMaxSum", keyWithoutOperators ), groupMaxSumValue );

						protocolCalculatorGroupFunction.getValues().add( protocolCalculatorGroupFunctionEvaluatedDTO );
					} else {
						var groupAcceptableValue = answerType.getAcceptableValues().stream().filter( av -> group.getName().equalsIgnoreCase( av.getPresentation() ) ).findFirst().get();
						var groupQuestionsIds = group.getQuestions().stream().map( QuestionProtocol::getId ).toList();
						var groupQuestionsSequence = group.getQuestions().stream().map( QuestionProtocol::getSequence ).toList();
						var rightAnswers = answers.stream().filter( a -> groupAcceptableValue.getValue().compareTo( a.getAnswer().intValue() ) == 0 && groupQuestionsIds.contains( a.getQuestion().getId() ) ).toList();
						groupsKeyEvalueted.put( String.format( "@%s#groupSum", keyWithoutOperators ), rightAnswers.size() * 1D );
						groupsKeyEvalueted.put( String.format( "@%s#groupQuestionsLength", keyWithoutOperators ), groupQuestionsIds.size() * 1D );
						groupsKeyEvalueted.put( String.format( "@%s#groupMaxSum", keyWithoutOperators ), groupQuestionsIds.size() * 1D );
						protocolCalculatorGroupFunction.getValues().add( new ProtocolCalculatedGroupFunctionEvaluatedDTO( ( rightAnswers.size() * 1D ) + "", answerType.getId(), rightAnswers.size() * 1D ) );
					}
				}
				protocolCalculatorGroup.getGroupFunctions().add( protocolCalculatorGroupFunction );
			}
			protocolCalculator.getGroups().add( protocolCalculatorGroup );
		}
		for( var group : protocolCalculator.getGroups() ) {
			for( var groupFunction : Optional.ofNullable( group.getGroupFunctions() ).orElse( Collections.emptyList() ) ) {
				for( var function : groupFunction.getValues() ) {
					groupsKeyEvalueted.forEach( ( key, value ) -> function.setFunctionTextEvaluated( function.getFunctionTextEvaluated().replaceAll( key, value.toString() ) ) );
					function.setValue( new DoubleEvaluator().evaluate( function.getFunctionTextEvaluated() ) );
				}
			}
		}
		protocolCalculator.setFunctions( new ArrayList<>() );
		for( var function : Optional.ofNullable( protocol.getFunctions() ).orElse( Collections.emptyList() ) ) {
			var protocolCalculatorGroupFunction = new ProtocolCalculatedGroupFunctionDTO();

			protocolCalculatorGroupFunction.setId( function.getId() );
			protocolCalculatorGroupFunction.setName( function.getName() );
			protocolCalculatorGroupFunction.setFunctionText( function.getFunctionText() );
			protocolCalculatorGroupFunction.setValues( new ArrayList<>() );
			for( var answerType : protocol.getAnswersType() ) {
				var protocolCalculatorGroupFunctionEvaluatedDTO = new ProtocolCalculatedGroupFunctionEvaluatedDTO();
				protocolCalculatorGroupFunctionEvaluatedDTO.setAnswersTypeId( answerType.getId() );
				protocolCalculatorGroupFunctionEvaluatedDTO.setFunctionTextEvaluated( protocolCalculatorGroupFunction.getFunctionText() );
				groupsKeyEvalueted.forEach( ( key, value ) -> protocolCalculatorGroupFunctionEvaluatedDTO.setFunctionTextEvaluated( protocolCalculatorGroupFunctionEvaluatedDTO.getFunctionTextEvaluated().replaceAll( key, value.toString() ) ) );
				protocolCalculatorGroupFunctionEvaluatedDTO.setValue( new DoubleEvaluator().evaluate( protocolCalculatorGroupFunctionEvaluatedDTO.getFunctionTextEvaluated() ) );
				protocolCalculatorGroupFunction.getValues().add( protocolCalculatorGroupFunctionEvaluatedDTO );
			}
			protocolCalculator.getFunctions().add( protocolCalculatorGroupFunction );
		}

		return protocolCalculator;
	}

	private Double getGroupSumValue( AnswerTypeProtocol answerTypeProtocol, QuestionGroupProtocol group, List<AnswerQuestionProtocol> answers ) {
		var groupQuestionsIds = group.getQuestions().stream().map( QuestionProtocol::getId ).toList();
		return answers.stream().filter( answer -> groupQuestionsIds.stream().anyMatch( gqi -> answer.getQuestion().getId().equals( gqi ) && answer.getAnswerType().getId().equals( answerTypeProtocol.getId() ) ) ).map( AnswerQuestionProtocol::getAnswer )
				.reduce( 0D, Double::sum );
	}

	private Double getGroupQuestionsLengthValue( QuestionGroupProtocol group ) {
		return Integer.valueOf( group.getQuestions().size() ).doubleValue();
	}

	private Double getGroupMaxSumValue( AnswerTypeProtocol answerTypeProtocol, QuestionGroupProtocol group ) {
		var maxAcceptableValue = answerTypeProtocol.getAcceptableValues().stream().max( Comparator.comparingInt( AcceptedValueAnswerTypeProtocol::getValue ) ).orElseThrow().getValue();
		return Integer.valueOf( maxAcceptableValue * group.getQuestions().size() ).doubleValue();
	}
}
