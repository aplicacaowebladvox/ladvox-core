package br.com.jovencio.projetofonocore.model.enums;

import br.com.jovencio.projetofonocore.model.dtos.DaySessionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeekDayEnum {

	SUNDAY( "Domingo" ),
	MONDAY( "Segunda-feira" ),
	TUESDAY( "Terça-feira" ),
	WEDNESDAY( "Quarta-feira" ),
	THURSDAY( "Quinta-feira" ),
	FRIDAY( "Sexta-feira" ),
	SATURDAY( "Sábado" );;

	private String displayName;

	public static WeekDayEnum parse( String value ) {
		if( value == null || value.isEmpty() ) {
			return null;
		}
		for( var weekDayEnum : WeekDayEnum.values() ) {
			if( weekDayEnum.getDisplayName().equalsIgnoreCase( value ) || weekDayEnum.name().equalsIgnoreCase( value ) )
				return weekDayEnum;
		}
		return null;
	}

	public static int compareAsc( DaySessionDTO daySessionDTO, DaySessionDTO daySessionDTO1 ) {
		return Integer.compare( daySessionDTO.getWeekday().ordinal(), daySessionDTO1.getWeekday().ordinal() );
	}
}
