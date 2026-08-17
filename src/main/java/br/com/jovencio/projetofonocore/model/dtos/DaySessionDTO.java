package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.enums.WeekDayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DaySessionDTO {

	private WeekDayEnum weekday;
	private List<SessionDTO> sessions;

}
