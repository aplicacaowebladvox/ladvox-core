package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IProjectRepository;
import br.com.jovencio.projetofonocore.model.dtos.ProjectDTO;
import br.com.jovencio.projetofonocore.model.dtos.SearchReturnDTO;
import br.com.jovencio.projetofonocore.model.entities.Project;
import br.com.jovencio.projetofonocore.model.grid.ProjectGrid;
import br.com.jovencio.projetofonocore.model.grid.options.TermGridOptions;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

	private final IProjectRepository repository;

	public SearchReturnDTO<ProjectGrid> search( TermGridOptions options ) {
		var count = repository.searchCount( options );
		if( count == null || count.equals( 0L ) ) {
			return new SearchReturnDTO<>();
		}
		return new SearchReturnDTO<>( repository.search( options ), count );
	}

	public ProjectDTO get( Long id ) {
		return repository.findById( id ).map( ModelMapperUtils::map ).orElseThrow();
	}

	public ProjectDTO save( ProjectDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public ProjectDTO save( long id, ProjectDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( id, entity );
		return ModelMapperUtils.map( repository.save( entity ) );
	}

	public void delete( Long id ) {
		repository.findById( id ).orElseThrow();
		repository.deleteById( id );
	}

	private void validate( Project entity ) {
		validate( null, entity );
	}

	private void validate( Long id, Project entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Projeto não informado" );
		}
		List<String> errors = new ArrayList<>();
		if( entity.getTitle() == null || entity.getTitle().isEmpty() ) {
			errors.add( "Título não informado" );
		} else if( entity.getTitle().length() > 255 ) {
			errors.add( "Título muito longo(máximo 255 caracteres)" );
		}

		if( entity.getStatus() == null || entity.getStatus().isEmpty() ) {
			errors.add( "Status não informado" );
		} else if( entity.getStatus().length() > 125 ) {
			errors.add( "Status muito longo(máximo 125 caracteres)" );
		}

		if( entity.getType() != null && !entity.getType().isEmpty() && entity.getType().length() > 125 ) {
			errors.add( "Tipo muito longo(máximo 125 caracteres)" );
		} else if( entity.getType() == null || entity.getType().isEmpty() ) {
			entity.setType( null );
		}

		if( entity.getDescription() == null || entity.getDescription().isEmpty() ) {
			errors.add( "Descrição não informada" );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}
}
