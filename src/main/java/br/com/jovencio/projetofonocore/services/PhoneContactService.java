package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IPhoneContactRepository;
import br.com.jovencio.projetofonocore.model.entities.PhoneContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneContactService {

	private final IPhoneContactRepository repository;

	public List<PhoneContact> save( List<PhoneContact> entities ) {
		return save( entities, true );
	}

	public List<PhoneContact> save( List<PhoneContact> entities, boolean needValidation ) {
		if( needValidation ) {
			var invalids = entities.stream().map( this::validate ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		return repository.saveAll( entities );
	}

	public List<String> validate( PhoneContact entity ) {
		var errors = new ArrayList<String>();
		if( entity == null ) {
			return List.of( "Telefone não informado" );
		}

		if( entity.getPhoneNumber() == null || entity.getPhoneNumber().isEmpty() ) {
			errors.add( "Número de telefone não informada" );
		} else if( entity.getPhoneNumber().length() > 15 ) {
			errors.add( "Número de telefone muito longo(máximo 15 caracteres)" );
		}

		return errors;
	}
}
