package br.com.jovencio.projetofonocore.model.dtos;

import br.com.jovencio.projetofonocore.model.entities.Address;
import br.com.jovencio.projetofonocore.model.entities.EmailContact;
import br.com.jovencio.projetofonocore.model.entities.PhoneContact;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SystemContactDTO {

	private Long id;
	private List<AddressDTO> addresses;
	private List<EmailContactDTO> emails;
	private List<PhoneContactDTO> phones;

}
