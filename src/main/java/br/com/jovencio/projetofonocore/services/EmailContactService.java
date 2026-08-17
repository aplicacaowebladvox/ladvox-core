package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IEmailContactRepository;
import br.com.jovencio.projetofonocore.model.entities.EmailContact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailContactService {

	private final IEmailContactRepository repository;

	public List<EmailContact> save( List<EmailContact> entities ) {
		return save( entities, true );
	}

	public List<EmailContact> save( List<EmailContact> entities, boolean needValidation ) {
		if( needValidation ) {
			var invalids = entities.stream().map( this::validate ).filter( le -> le != null && !le.isEmpty() ).toList();
			if( !invalids.isEmpty() ) {
				throw new IllegalArgumentException( invalids.toString() );
			}
		}
		return repository.saveAll( entities );
	}

	public List<String> validate( EmailContact entity ) {
		var errors = new ArrayList<String>();
		if( entity == null ) {
			return List.of( "E-mail não informado" );
		}

		if( entity.getEmailAddress() == null || entity.getEmailAddress().isEmpty() ) {
			errors.add( "E-mail não informado" );
		} else if( entity.getEmailAddress().length() > 255 ) {
			errors.add( "E-mail muito longo(máximo 255 caracteres)" );
		} else {
			entity.setEmailAddress( entity.getEmailAddress().toLowerCase() );
		}

		return errors;
	}
}
