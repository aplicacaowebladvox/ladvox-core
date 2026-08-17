package br.com.jovencio.projetofonocore.model.entities;

import br.com.jovencio.projetofonocore.model.auth.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table( name = "REQUEST_APPOINTMENTS" )
public class RequestAppointment {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_REQUEST_APPOINTMENT" )
	private Long id;

	@Column( name = "NM_REQUEST_APPOINTMENT", columnDefinition = "VARCHAR(120)", nullable = false )
	private String name;

	@Column( name = "TX_REQUEST_APPOINTMENT_MAIL", columnDefinition = "VARCHAR(255)" )
	private String email;

	@Column( name = "CD_REQUEST_APPOINTMENT_PHONE_1", columnDefinition = "VARCHAR(15)" )
	private String phone1;

	@Column( name = "CD_REQUEST_APPOINTMENT_PHONE_2", columnDefinition = "VARCHAR(15)" )
	private String phone2;

	@Column( name = "CD_REQUEST_APPOINTMENT_PROBLEM_DESCRIPTION", columnDefinition = "VARCHAR(500)", nullable = false )
	private String problemDescription;

	@Column( name = "DT_REQUEST_APPOINTMENT_CREATED_DATE", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false )
	private LocalDateTime createdDate;

	@Column( name = "DT_REQUEST_APPOINTMENT_CONCLUSION_DATE", columnDefinition = "TIMESTAMP" )
	private LocalDateTime conclusionDate;

	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "ID_USER_OF_CONCLUSION" )
	private User userOfConclusion;

}
