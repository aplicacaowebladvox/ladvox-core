package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.ISocialMediaRepository;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.dtos.SocialMediaDTO;
import br.com.jovencio.projetofonocore.model.entities.SocialMedia;
import br.com.jovencio.projetofonocore.model.grid.options.SocialMediaGridOptions;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialMediaService {

	private final ISocialMediaRepository repository;

	public SearchReturnDTO<SocialMediaDTO> search( SocialMediaGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public SocialMediaDTO get( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow();
	}

	public SocialMediaDTO save( SocialMediaDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public SocialMediaDTO save( long id, SocialMediaDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( id, entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public void delete( Long id ) {
		repository.findById( id ).orElseThrow();
		repository.deleteById( id );
	}

	private void validate( SocialMedia entity ) {
		validate( null, entity );
	}

	private void validate( Long id, SocialMedia entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Rede social não informada" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getIconClass() == null || entity.getIconClass().isEmpty() ) {
			errors.add( "Ícone não informado" );
		} else if( entity.getIconClass().length() > 125 ) {
			errors.add( "Ícone muito long(máximo 125 caracteres)" );
		}

		if( entity.getIconColor() != null && !entity.getIconColor().isEmpty() ) {
			if( !entity.getIconColor().startsWith( "#" ) ) {
				errors.add( "Cor inválida: precisa começar com '#': " + entity.getIconColor() );
			}
		} else {
			entity.setIconColor( null );
		}
		if( entity.getTitle() == null || entity.getTitle().isEmpty() ) {
			errors.add( "Título não informado" );
		} else if( entity.getTitle().length() > 125 ) {
			errors.add( "Título muito longo(máximo 125 caracteres)" );
		}

		if( entity.getUrl() == null || entity.getUrl().isEmpty() ) {
			errors.add( "URL não informada" );
		} else if( entity.getUrl().length() > 255 ) {
			errors.add( "URL muito longa(máximo 255 caracteres)" );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}
}
