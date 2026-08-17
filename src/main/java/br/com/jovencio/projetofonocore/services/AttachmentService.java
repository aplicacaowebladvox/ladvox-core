package br.com.jovencio.projetofonocore.services;

import br.com.jovencio.projetofonocore.dao.repositories.IAttachmentReposiory;
import br.com.jovencio.projetofonocore.model.dtos.AttachmentDTO;
import br.com.jovencio.projetofonocore.model.entities.Attachment;
import br.com.jovencio.projetofonocore.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

	private final IAttachmentReposiory reposiory;
	private final View error;

	public Attachment save( AttachmentDTO dto ) {
		var entity = ModelMapperUtils.map( dto );
		validate( entity );
		return reposiory.save( entity );
	}

	public void delete( Long id ) {
		reposiory.deleteById( id );
	}

	private void validate( Attachment entity ) {
		if( entity == null ) {
			throw new IllegalArgumentException( "Anexo não informado" );
		}
		List<String> errors = new ArrayList<>();

		if( entity.getCreatedDate() == null ) {
			entity.setCreatedDate( LocalDateTime.now() );
		}
		if( entity.getName() == null || entity.getName().isEmpty() ) {
			errors.add( "Nome não informado" );
		} else if( entity.getName().length() > 120 ) {
			errors.add( "Nome muito longo(máximo 120 caracteres)" );
		}
		if( entity.getBytesSize() <= 0 ) {
			errors.add( "Tamanho deve ser maior que zero" );
		} else if( entity.getBytesSize() > ( 15 * 1000000 ) ) {
			errors.add( "Tamanho muito grande(máximo 15MB)" );
		}
		if( entity.getType() == null ) {
			errors.add( "Tipo não informado" );
		} else if( entity.getType().length() > 120 ) {
			errors.add( "Tipo muito longo(máximo 120 caracteres)" );
		}
		if( entity.getBase64() == null ) {
			errors.add( "Arquivo base64 não informado" );
		}
		if( !errors.isEmpty() ) {
			throw new IllegalArgumentException( errors.toString() );
		}
	}
}
