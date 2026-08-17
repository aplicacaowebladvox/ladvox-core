package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "PHONES_CONTACT" )
public class PhoneContact {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_PHONE_CONTACT" )
	private Long id;

	@Column( name = "TX_PHONE_CONTACT_NUMBER", columnDefinition = "VARCHAR(15)", nullable = false )
	private String phoneNumber;

}
