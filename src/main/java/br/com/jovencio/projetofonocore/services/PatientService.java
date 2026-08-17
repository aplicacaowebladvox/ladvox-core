package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IEducationRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IGenderRepository;
import br.com.jovencio.projetofonocore.dao.repositories.IPatientRepository;
import br.com.jovencio.projetofonocore.model.dtos.PatientDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.SelectOptionDTO;
import br.com.jovencio.projetofonocore.model.entities.Patient;
import br.com.jovencio.projetofonocore.model.grid.PatientGrid;
import br.com.jovencio.projetofonocore.model.grid.options.PatientGridOptions;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import br.com.jovencio.projetofonocore.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

	private final IPatientRepository repository;
	private final IGenderRepository genderRepository;
	private final IEducationRepository educationRepository;
	private final AddressService addressService;
	private final UserService userService;

	public SearchReturnDTO<PatientGrid> search( PatientGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public PatientDTO get( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow();
	}

	public PatientDTO save( PatientDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		entity.setAddress( addressService.save( entity.getAddress(), false ) );
		entity.setUser( userService.generateByPatient( dto ) );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public PatientDTO save( long id, PatientDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( id, entity );
		var patientFromDatabase = repository.findById( id ).orElseThrow();
		entity.setUser( Optional.ofNullable( patientFromDatabase.getUser() ).orElse( userService.generateByPatient( dto ) ) );
		entity.setUser( userService.refreshDataOfUser( entity.getUser(), dto ) );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	private void validate( Patient entity ) {
		validate( null, entity );
	}

	private void validate( Long id, Patient entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Paciente não informado" );
		}
		List<String> errors = new ArrayList<>();

		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres" );
		}
		if( entity.getDocument() == null || entity.getDocument().isEmpty() ) {
			errors.add( "Documento não informado" );
		} else if( !ValidationUtils.cpf( entity.getDocument() ) ) {
			errors.add( "Documento não é um CPF" );
		} else {
			repository.findByDocument( entity.getDocument() ).ifPresent( patient -> {
				if( patient.getId().compareTo( id ) != 0 ) {
					errors.add( "Documento já cadastrado" );
				}
			} );
		}
		if( entity.getNationalHealthCard() == null || entity.getNationalHealthCard().isEmpty() ) {
			errors.add( "Carteira nacional de saúde não informada" );
		} else if( entity.getNationalHealthCard().length() > 25 ) {
			errors.add( "Carteira nacional de saúde muito longo(máximo 25 caracteres)" );
		}
		if( entity.getGender() == null || entity.getGender().getId() == null ) {
			errors.add( "Gênero não informado" );
		} else {
			var optionalGender = genderRepository.findById( entity.getGender().getId() );
			if( optionalGender.isEmpty() ) {
				errors.add( "Gênero não encontrado na base de dados" );
			} else {
				if( optionalGender.get().isHasOtherDefinition() ) {
					if( entity.getOtherGenderDefinition() == null || entity.getOtherGenderDefinition().isEmpty() ) {
						errors.add( "Descrição do gênero não informada" );
					} else if( entity.getOtherGenderDefinition().length() > 50 ) {
						errors.add( "Descrição do gênero muito longo(máximo 50 caracteres)" );
					}
				} else if( entity.getOtherGenderDefinition() != null ) {
					errors.add( "Descrição do gênero não pode ser preenchida para o gênero " + optionalGender.get().getName() );
				}
			}
		}
		if( entity.getEducation() == null || entity.getEducation().getId() == null ) {
			errors.add( "Educação não informada" );
		} else {
			var optionalEducation = educationRepository.findById( entity.getEducation().getId() );
			if( optionalEducation.isEmpty() ) {
				errors.add( "Educação não encontrada na base de dados" );
			} else {
				if( optionalEducation.get().isHasOtherDefinition() ) {
					if( entity.getOtherEducationDefinition() == null || entity.getOtherEducationDefinition().isEmpty() ) {
						errors.add( "Descrição da educação não informada" );
					} else if( entity.getOtherEducationDefinition().length() > 50 ) {
						errors.add( "Descrição da educação muito longa(máximo 50 caracteres)" );
					}
				} else if( entity.getOtherEducationDefinition() != null ) {
					errors.add( "Descrição da educação não deve ser informada para a educação: " + optionalEducation.get().getName() );
				}
			}
		}
		if( entity.getProfession() == null || entity.getProfession().isEmpty() ) {
			errors.add( "Profissão não informada" );
		} else if( entity.getProfession().length() > 150 ) {
			errors.add( "Profissão muito longa(máximo 150 caracteres)" );
		}
		if( entity.getMedicalRecord() != null && !entity.getMedicalRecord().isEmpty() ) {
			if( entity.getMedicalRecord().length() > 50 ) {
				errors.add( "Número de protuário muito grande(máximo 150 caracteres" );
			}
		} else {
			entity.setMedicalRecord( null );
		}
		if( entity.getBirthDate() == null ) {
			errors.add( "Data de nascimento não informada" );
		} else if( entity.getBirthDate().isAfter( LocalDate.now() ) ) {
			errors.add( "Data de nascimento deve ser depois de hoje" );
		}
		if( entity.getMainPhone() == null || entity.getMainPhone().isEmpty() ) {
			errors.add( "Telefone principal não informado" );
		} else if( !ValidationUtils.phone( entity.getMainPhone() ) ) {
			errors.add( "Número do telefone principal é inválido" );
		}
		if( entity.getPhoneSecundary() != null && !ValidationUtils.phone( entity.getPhoneSecundary() ) ) {
			errors.add( "Número do telefone secundário é inválido" );
		}
		if( entity.getPhoneObservation() != null && entity.getPhoneObservation().length() > 500 ) {
			errors.add( "Observação dos telefones muito longa(máximo 500 caracteres)" );
		}
		if( entity.getMainMail() == null || entity.getMainMail().isEmpty() ) {
			errors.add( "E-mail principal não informado" );
		} else if( !ValidationUtils.mail( entity.getMainMail() ) ) {
			errors.add( "E-mail principal é invalido" );
		}
		if( entity.getMailSecundary() != null && !entity.getMailSecundary().isEmpty() && !ValidationUtils.mail( entity.getMailSecundary() ) ) {
			errors.add( "E-mail secundário é inválido" );
		} else {
			entity.setMailSecundary( null );
		}
		if( entity.getMailObservation() != null && entity.getMailObservation().length() > 500 ) {
			errors.add( "Observação dos e-mails muito longa(máximo 500 caracteres)" );
		}
		var addressErrros = addressService.validate( entity.getAddress() );
		if( !addressErrros.isEmpty() ) {
			errors.add( "Endereço: " + addressErrros );
		}

		if( id != null ) {
			var entityFromDatabase = repository.findById( id ).orElseThrow();
			if( !entityFromDatabase.getDocument().equals( entity.getDocument() ) ) {
				errors.add( "Documento não pode ser alterado" );
			} else if( !entity.getId().equals( entityFromDatabase.getId() ) ) {
				errors.add( "ID não pode ser alterado" );
			}
		}

		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}

	public List<SelectOptionDTO<Long, String>> getSelectOptions( Long id ) {
		return repository.getSelectOptions( id );
	}
}
