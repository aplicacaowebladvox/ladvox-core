package br.com.jovencio.projetofonocore.services.auth;

import br.com.jovencio.projetofonocore.model.auth.dtos.PermissionDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.RoleDTO;
import br.com.jovencio.projetofonocore.model.auth.dtos.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

	@Value( "${security.jwt.secret-key}" )
	private String secretKey;
	@Value( "${security.jwt.expiration-time}" )
	private long jwtExpiration;
	@Value( "${security.jwt.refresh-token-expiration-time}" )
	private long jwtRefreshTokenExpiration;

	public String generateAccessToken( UserDTO user ) {
		Map<String, Object> data = new HashMap<>();
		var currentTimeMillis = System.currentTimeMillis();
		data.put( "typ", "Bearer" );
		data.put( "azp", "projeto-fono-api" );
		data.put( "session_state", UUID.randomUUID().toString() );
		data.put( "roles", user.getRoles().stream().map( RoleDTO::getName ).toList() );
		data.put( "permissions", user.getRoles().stream().flatMap( role -> role.getPermissions().stream() ).map( PermissionDTO::getAuthority ).toList() );
		data.put( "login", user.getUsername() );
		data.put( "name", user.getName() );

		return Jwts
				.builder()
				.setId( UUID.randomUUID().toString() )
				.setExpiration( new Date( currentTimeMillis + getExpirationTime() ) )
				.setIssuedAt( new Date( currentTimeMillis ) )
				.setIssuer( ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() )
				.setSubject( user.getId().toString() )
				.setAudience( "projeto-fono" )
				.addClaims( data )
				.signWith( getSignInKey(), SignatureAlgorithm.HS256 )
				.compact();
	}

	public String generateRefreshToken( UserDTO user ) {
		Map<String, Object> data = new HashMap<>();
		var currentTimeMillis = System.currentTimeMillis();
		data.put( "typ", "Bearer" );
		data.put( "azp", "projeto-fono-api" );
		data.put( "session_state", UUID.randomUUID().toString() );
		data.put( "login", user.getUsername() );
		data.put( "name", user.getName() );
		data.put( "roles", user.getRoles().stream().map( RoleDTO::getName ).toList() );

		return Jwts
				.builder()
				.setId( UUID.randomUUID().toString() )
				.setExpiration( new Date( currentTimeMillis + getRefreshTokenExpirationTime() ) )
				.setIssuedAt( new Date( currentTimeMillis ) )
				.setIssuer( ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() )
				.setSubject( user.getId().toString() )
				.setAudience( "projeto-fono" )
				.addClaims( data )
				.signWith( getSignInKey(), SignatureAlgorithm.HS256 )
				.compact();
	}

	public String extractUsername( String token ) {
		return extractClaim( token, Claims::getSubject );
	}

	public String extractSubject( String token ) {
		return extractClaim( token, Claims::getSubject );
	}

	public String extractLogin( String token ) {
		final Claims claims = extractAllClaims( token );
		return ( String ) claims.get( "login" );
	}

	public List<String> extractRoles( String token ) {
		final Claims claims = extractAllClaims( token );
		return ( List<String> ) claims.get( "roles" );
	}

	public List<String> extractPermissions( String token ) {
		final Claims claims = extractAllClaims( token );
		return ( List<String> ) claims.get( "permissions" );
	}

	public <T> T extractClaim( String token, Function<Claims, T> claimsResolver ) {
		final Claims claims = extractAllClaims( token );
		return claimsResolver.apply( claims );
	}

	public String generateToken( UserDTO user ) {
		Map<String, Object> details = new HashMap<>();
		details.put( "roles", user.getRoles().stream().map( RoleDTO::getName ).toList() );
		details.put( "permissions", user.getRoles().stream().flatMap( role -> role.getPermissions().stream() ).map( PermissionDTO::getAuthority ).toList() );
		details.put( "login", user.getUsername() );
		return generateToken( details, user );
	}

	public String generateToken( Map<String, Object> extraClaims, UserDTO userDetails ) {
		return buildToken( extraClaims, userDetails, jwtExpiration );
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}

	public long getRefreshTokenExpirationTime() {
		return jwtRefreshTokenExpiration;
	}

	private String buildToken(
			Map<String, Object> extraClaims,
			UserDTO userDetails,
			long expiration
	) {
		return Jwts
				.builder()
				.setId( UUID.randomUUID().toString() )
				.setIssuedAt( new Date( System.currentTimeMillis() ) )
				.setExpiration( new Date( System.currentTimeMillis() + expiration ) )
				.setIssuer( ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() )
				.setAudience( "projeto-fono" )
				.addClaims( extraClaims )
				.setSubject( userDetails.getId().toString() )
				.signWith( getSignInKey(), SignatureAlgorithm.HS256 )
				.compact();
	}

	public boolean isTokenValid( String token, UserDetails userDetails ) {
		final String subject = extractLogin( token );
		return ( subject.equals( userDetails.getUsername() ) ) && !isTokenExpired( token );
	}

	public boolean isTokenValid( String token, UserDTO userDetails ) {
		final String username = extractUsername( token );
		return ( username.equals( userDetails.getUsername() ) ) && !isTokenExpired( token );
	}

	public boolean isTokenExpired( String token ) {
		return extractExpiration( token ).before( new Date() );
	}

	private Date extractExpiration( String token ) {
		return extractClaim( token, Claims::getExpiration );
	}

	private Claims extractAllClaims( String token ) {
		return Jwts
				.parserBuilder()
				.setSigningKey( getSignInKey() )
				.build()
				.parseClaimsJws( token )
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode( secretKey );
		return Keys.hmacShaKeyFor( keyBytes );
	}

}
