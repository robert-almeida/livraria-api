package br.com.alura.livraria.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.livraria.dto.LoginFormDto;
import br.com.alura.livraria.dto.TokenDto;
import br.com.alura.livraria.infra.security.AutenticacaoService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/auth")
@Api(tags= "Autenticação")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService service;
	
	@PostMapping
	public TokenDto autenticar(@RequestBody @Valid LoginFormDto dto) {
		return new TokenDto(service.autenticar(dto));
	}
}