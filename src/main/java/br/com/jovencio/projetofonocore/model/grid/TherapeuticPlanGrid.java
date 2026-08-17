package br.com.jovencio.projetofonocore.model.grid;

import br.com.jovencio.projetofonocore.model.enums.WeekDayEnum;
import br.com.jovencio.projetofonocore.utils.TupleUtils;
import jakarta.persistence.Tuple;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TherapeuticPlanGrid {

	private Long id;
	private Long patientId;
	private String patientName;
	private Long medicalAppointmentPlanningId;
	private String medicalAppointmentPlanningDescription;
	private Long medicalAppointmentTeamId;
	private String medicalAppointmentTeamDescription;
	private String medicalAppointmentTeamColor;
	private Long medicalAppointmentId;
	private String medicalAppointmentRoom;
	private String medicalAppointmentConsultDescription;
	private UUID currentEditorId;

	public TherapeuticPlanGrid( Tuple tuple ) {
		this.id = TupleUtils.getLong( tuple, "id" );
		this.patientId = TupleUtils.getLong( tuple, "patientId" );
		this.patientName = TupleUtils.getString( tuple, "patientName" );
		var currentEditorStringId = TupleUtils.getString( tuple, "currentEditorId" );
		this.currentEditorId = currentEditorStringId == null ? null : UUID.fromString( currentEditorStringId );
		this.medicalAppointmentPlanningId = TupleUtils.getLong( tuple, "medicalAppointmentPlanningId" );
		this.medicalAppointmentPlanningDescription = TupleUtils.getString( tuple, "medicalAppointmentPlanningDescription" );
		this.medicalAppointmentTeamId = TupleUtils.getLong( tuple, "medicalAppointmentTeamId" );
		this.medicalAppointmentTeamDescription = TupleUtils.getString( tuple, "medicalAppointmentTeamDescription" );
		this.medicalAppointmentTeamColor = TupleUtils.getString( tuple, "medicalAppointmentTeamColor" );
		this.medicalAppointmentId = TupleUtils.getLong( tuple, "medicalAppointmentId" );
		this.medicalAppointmentRoom = TupleUtils.getString( tuple, "medicalAppointmentRoom" );
		var weekDayEnum = WeekDayEnum.parse( TupleUtils.getString( tuple, "medicalAppointmentWeekDay" ) );
		var hour = TupleUtils.getLocalTime( tuple, "medicalAppointmentHour" );
		if( weekDayEnum != null && hour != null ) {
			this.medicalAppointmentConsultDescription = weekDayEnum.getDisplayName() + " às " + hour;
		} else if( weekDayEnum != null ) {
			this.medicalAppointmentConsultDescription = weekDayEnum.getDisplayName();
		} else if( hour != null ) {
			this.medicalAppointmentConsultDescription = hour.toString();
		}
	}
}
