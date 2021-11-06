package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@Mock
	private LivroRepository repository;

	@Mock
	private AutorRepository autorRepository;
	
	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private LivroService service;

	private LivroFormDto criarLivroFormDto() {
		LivroFormDto formDto = new LivroFormDto("Teste do cadastro de livro", LocalDate.now(), 150, 999l);
		return formDto;
	}
	
	@Test
	void deveriaCadastrarUmLivro() {
		LivroFormDto formDto = criarLivroFormDto();
		
		Autor autor = new Autor("Teste", "teste@email.com", LocalDate.now().minusYears(20), "Teste");
		
		Mockito
		.when(autorRepository.getById(formDto.getAutorId()))
		.thenReturn(autor);
		
		Livro livro = new Livro(formDto.getTitulo(), formDto.getDataLancamento(), formDto.getNumeroPaginas(), autor);
		
		Mockito
		.when(modelMapper.map(formDto, Livro.class))
		.thenReturn(livro);
		
		Mockito
		.when(modelMapper.map(livro, LivroDto.class))
		.thenReturn(new LivroDto(
				null,
				livro.getTitulo(),
				livro.getDataLancamento(),
				livro.getNumeroPaginas()
				));
	
		LivroDto dto = service.cadastrar(formDto);
		
		Mockito.verify(repository).save(Mockito.any());

		assertEquals(formDto.getTitulo(), dto.getTitulo());
		assertEquals(formDto.getDataLancamento(), dto.getDataLancamento());
		assertEquals(formDto.getNumeroPaginas(), dto.getNumeroPaginas());
	}



	@Test
	void NaoDeveriaCadastrarUmLivroComAutorInexistente() {
		LivroFormDto formDto = criarLivroFormDto();

		Mockito.when(autorRepository.getById(formDto.getAutorId())).thenThrow(EntityNotFoundException.class);

		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto));
	}

}
