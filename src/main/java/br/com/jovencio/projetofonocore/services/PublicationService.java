package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IPublicationRepository;
import br.com.jovencio.projetofonocore.model.dtos.PublicationDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.entities.Publication;
import br.com.jovencio.projetofonocore.model.grid.PublicationGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {

	private final IPublicationRepository repository;

	public SearchReturnDTO<PublicationGrid> search( TermGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public PublicationDTO get( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow();
	}

	public PublicationDTO save( PublicationDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public PublicationDTO save( long id, PublicationDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( id, entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public void delete( Long id ) {
		repository.findById( id ).orElseThrow();
		repository.deleteById( id );
	}

	private void validate( Publication entity ) {
		validate( null, entity );
	}

	private void validate( Long id, Publication entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Publicação não informada" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getTitle() == null || entity.getTitle().isEmpty() ) {
			errors.add( "Título não informado" );
		} else if( entity.getTitle().length() > 255 ) {
			errors.add( "Título muito longo(máximo 255 caracteres)" );
		}

		if( entity.getBibliographicReference() == null || entity.getBibliographicReference().isEmpty() ) {
			errors.add( "Referência bibliográfica não informada" );
		} else if( entity.getBibliographicReference().length() > 255 ) {
			errors.add( "Referência bibliográfica muito longa(máximo 255 caracteres)" );
		}
		if( entity.getPublicationAbstract() == null || entity.getPublicationAbstract().isEmpty() ) {
			errors.add( "Resumo não informado" );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}
}
