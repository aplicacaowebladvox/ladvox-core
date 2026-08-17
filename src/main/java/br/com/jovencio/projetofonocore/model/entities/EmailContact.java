package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "EMAILS_CONTACT" )
public class EmailContact {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_EMAIL_CONTACT" )
	private Long id;

	@Column( name = "TX_EMAIL_CONTACT_ADDRESS", columnDefinition = "VARCHAR(255)", nullable = false )
	private String emailAddress;

}
