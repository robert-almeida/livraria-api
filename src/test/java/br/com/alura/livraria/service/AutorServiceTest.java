package br.com.alura.livraria.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.livraria.dto.AutorDto;
import br.com.alura.livraria.dto.AutorFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

	@Mock
	private AutorRepository repository;
	
	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private AutorService service;

	@Test
	void deveriaCadastrarUmAutor() {
		AutorFormDto formDto = new AutorFormDto("Fulano", "fulano@email.com", LocalDate.now().minusYears(20), "minicurriculo do fulano de teste");

		Autor autor = new Autor("Fulano", "fulano@email.com", LocalDate.now().minusYears(20), "minicurriculo do fulano de teste");
		
		Mockito
		.when(modelMapper.map(formDto, Autor.class))
		.thenReturn(autor);
		
		Mockito
		.when(modelMapper.map(autor, AutorDto.class))
		.thenReturn(new AutorDto(
				null,
				autor.getNome(),
				autor.getEmail(),
				autor.getDataNascimento(),
				autor.getMiniCurriculo()
				));
		
		AutorDto dto = service.cadastrar(formDto);
		
		Mockito.verify(repository).save(Mockito.any());

		assertEquals(formDto.getNome(), dto.getNome());
		assertEquals(formDto.getEmail(), dto.getEmail());
		assertEquals(formDto.getDataNascimento(), dto.getDataNascimento());
		assertEquals(formDto.getMiniCurriculo(), dto.getMiniCurriculo());
	}

}
