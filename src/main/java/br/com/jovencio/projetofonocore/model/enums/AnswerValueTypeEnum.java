package br.com.jovencio.projetofonocore.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnswerValueTypeEnum {

	NUMERIC( "NUMERIC", "Numérico" ),
	TEXT( "TEXT", "Texto" );

	private String id;
	private String displayName;

	public static AnswerValueTypeEnum parse( String value ) {
		if( value == null || value.isEmpty() ) {
			return null;
		}
		for( var answerValueTypeEnum : AnswerValueTypeEnum.values() ) {
			if( answerValueTypeEnum.getId().equalsIgnoreCase( value ) || answerValueTypeEnum.getDisplayName().equalsIgnoreCase( value ) || answerValueTypeEnum.name().equalsIgnoreCase( value ) )
				return answerValueTypeEnum;
		}
		return null;
	}
}
