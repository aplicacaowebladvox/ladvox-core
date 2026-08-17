package br.com.jovencio.projetofonocore.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

	private Long id;
	private String country;
	private String state;
	private String city;
	private String neighborhood;
	private String street;
	private String complement;
	private String number;
	private String zipCode;

}
