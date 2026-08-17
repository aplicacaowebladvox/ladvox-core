package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.ISystemContactRepository;
import br.com.jovencio.projetofonocore.model.dtos.SystemContactDTO;
import br.com.jovencio.projetofonocore.model.entities.SystemContact;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SystemContactService {

	private final ISystemContactRepository repository;
	private final AddressService addressService;
	private final PhoneContactService phoneService;
	private final EmailContactService emailService;

	public SystemContactDTO getActive() {
		return repository.findById( 1L ).map( ModelMapperUtils::map ).orElse( null );
	}

	public SystemContactDTO mergeChanges( SystemContactDTO systemContactDTO ) {
		var entity = ModelMapperUtils.map( systemContactDTO );
		validate( entity );
		addressService.save( entity.getAddresses(), false );
		phoneService.save( entity.getPhones(), false );
		emailService.save( entity.getEmails(), false );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	private void validate( SystemContact entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Contatos do sistema não informado" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getAddresses() == null || entity.getAddresses().isEmpty() ) {
			errors.add( "Nenhum endereços informado" );
		} else {
			int i = 1;
			for( var address : entity.getAddresses() ) {
				var childErrors = addressService.validate( address );
				if( !childErrors.isEmpty() ) {
					errors.add( "Endereço[" + i + "]: " + childErrors );
				}
			}
		}
		if( entity.getPhones() == null || entity.getPhones().isEmpty() ) {
			errors.add( "Nenhum telefone informado" );
		} else {
			int i = 1;
			for( var phone : entity.getPhones() ) {
				var childErrors = phoneService.validate( phone );
				if( !childErrors.isEmpty() ) {
					errors.add( "Telefone[" + i + "]: " + childErrors );
				}
			}
		}
		if( entity.getEmails() == null || entity.getEmails().isEmpty() ) {
			errors.add( "Nenhum e-mail informado" );
		} else {
			int i = 1;
			for( var email : entity.getEmails() ) {
				var childErrors = emailService.validate( email );
				if( !childErrors.isEmpty() ) {
					errors.add( "E-mail[" + i + "]: " + childErrors );
				}
			}
		}

		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}
}

