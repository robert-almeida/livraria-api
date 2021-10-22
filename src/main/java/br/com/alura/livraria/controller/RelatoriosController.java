package br.com.alura.livraria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.livraria.dto.ItemPublicacoesDto;
import br.com.alura.livraria.service.RelatorioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/relatorios")
@Api(tags= "Relatório")
public class RelatoriosController {
	
	@Autowired
	private RelatorioService service;
	
	@GetMapping("/publicacoes")
	@ApiOperation("Relatório de livros por autor")
	public List<ItemPublicacoesDto> relatorioLivrosPorAutor(){
		return service.relatorioLivrosPorAutor();
	}
}
