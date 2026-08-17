package br.com.jovencio.projetofonocore.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table( name = "ADDRESSES" )
public class Address {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "ID_ADDRESS" )
	private Long id;

	@Column( name = "NM_ADDRESS_COUNTRY", columnDefinition = "VARCHAR(50)", nullable = false )
	private String country;

	@Column( name = "NM_ADDRESS_STATE", columnDefinition = "VARCHAR(50)", nullable = false )
	private String state;

	@Column( name = "NM_ADDRESS_CITY", columnDefinition = "VARCHAR(50)", nullable = false )
	private String city;

	@Column( name = "NM_ADDRESS_NEIGHBORHOOD", columnDefinition = "VARCHAR(50)", nullable = false )
	private String neighborhood;

	@Column( name = "NM_ADDRESS_STREET", columnDefinition = "VARCHAR(255)", nullable = false )
	private String street;

	@Column( name = "TX_ADDRESS_COMPLEMENT", columnDefinition = "VARCHAR(50)" )
	private String complement;

	@Column( name = "TX_ADDRESS_NUMBER", columnDefinition = "VARCHAR(10)" )
	private String number;

	@Column( name = "TX_ADDRESS_ZIP_CODE", columnDefinition = "VARCHAR(50)" )
	private String zipCode;

}
