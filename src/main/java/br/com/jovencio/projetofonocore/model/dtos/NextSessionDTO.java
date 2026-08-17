package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.WeekDayEnum;
import br.com.jovencio.projetofonocore.utils.TupleUtils;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NextSessionDTO {

	private List<DaySessionDTO> daysSessions;

	public static NextSessionDTO map( List<Tuple> tuples ) {
		var mapDays = new HashMap<WeekDayEnum, DaySessionDTO>();
		for( var tuple : tuples ) {
			var weekdayEnum = WeekDayEnum.parse( TupleUtils.getString( tuple, "weekday" ) );
			var patientName = TupleUtils.getString( tuple, "patientName" );
			var sessionHour = TupleUtils.getLocalTime( tuple, "sessionHour" );
			var sessionRoom = TupleUtils.getString( tuple, "sessionRoom" );
			var therapistsTeamDescription = TupleUtils.getString( tuple, "therapistsTeamDescription" );
			var daySession = mapDays.computeIfAbsent( weekdayEnum, ( e ) -> new DaySessionDTO( e, new ArrayList<>() ) );
			daySession.getSessions().add( new SessionDTO( patientName, sessionHour.toString(), sessionRoom, therapistsTeamDescription ) );
			mapDays.put( weekdayEnum, daySession );
		}

		return new NextSessionDTO( mapDays.values().stream().sorted( WeekDayEnum::compareAsc ).toList() );
	}
}
