package br.com.alura.livraria.infra.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.livraria.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jjwt.secretKey}")
	private String secretKey;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal();
		return Jwts
				.builder()
				.setId(logado.getId().toString())
				.signWith(SignatureAlgorithm.HS256, secretKey)
				.compact()
				;
	}

}
