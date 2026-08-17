package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IAddressRepository;
import br.com.jovencio.projetofonocore.model.entities.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

	private final IAddressRepository repository;

	public List<Address> save( List<Address> addresses ) {
		return save( addresses, true );
	}

	public Address save( Address address, boolean needValidation ) {
		return save( List.of( address ), needValidation ).getFirst();
	}

	public List<Address> save( List<Address> addresses, boolean needValidation ) {
		if( needValidation ) {
			var invalids = addresses.stream().map( this::validate ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		return repository.saveAll( addresses );
	}

	public List<String> validate( Address entity ) {
		var errors = new ArrayList<String>();
		if( entity == null ) {
			errors.add( "Endereço não informado" );
			return errors;
		}
		if( entity.getCountry() == null || entity.getCountry().isEmpty() ) {
			errors.add( "País não informado" );
		} else if( entity.getCountry().length() > 50 ) {
			errors.add( "País muito longo(máximo 50 caracteres)" );
		}
		if( entity.getState() == null || entity.getState().isEmpty() ) {
			errors.add( "Estado não informado" );
		} else if( entity.getState().length() > 50 ) {
			errors.add( "Estado muito longo(máximo 50 caracteres)" );
		}
		if( entity.getCity() == null || entity.getCity().isEmpty() ) {
			errors.add( "Cidade não informada" );
		} else if( entity.getCity().length() > 50 ) {
			errors.add( "Cidade muito longa(máximo 50 caracteres)" );
		}
		if( entity.getNeighborhood() == null || entity.getNeighborhood().isEmpty() ) {
			errors.add( "Bairro não informado" );
		} else if( entity.getNeighborhood().length() > 50 ) {
			errors.add( "Bairro muito longo(máximo 50 caracteres)" );
		}
		if( entity.getStreet() == null || entity.getStreet().isEmpty() ) {
			errors.add( "Logradouro não informada" );
		} else if( entity.getStreet().length() > 255 ) {
			errors.add( "Logradouro muito longo(máximo 255 caracteres)" );
		}
		if( entity.getComplement() != null && !entity.getComplement().isEmpty() ) {
			if( entity.getComplement().length() > 50 ) {
				errors.add( "Complemento muito longo(máximo 50 caracteres)" );
			}
		} else {
			entity.setComplement( null );
		}
		if( entity.getNumber() != null && !entity.getNumber().isEmpty() ) {
			if( entity.getNumber().length() > 10 ) {
				errors.add( "Número muito longo(máximo 10 caracteres)" );
			}
		} else {
			entity.setNumber( null );
		}
		if( entity.getZipCode() != null && !entity.getZipCode().isEmpty() && entity.getZipCode().length() > 50 ) {
			errors.add( "CEP muito longo(máximo 50 caracteres)" );
		} else if( entity.getZipCode() == null || entity.getZipCode().isEmpty() ) {
			entity.setZipCode( null );
		}

		return errors;
	}

}
