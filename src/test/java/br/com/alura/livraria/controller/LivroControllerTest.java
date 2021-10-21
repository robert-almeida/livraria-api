package br.com.alura.livraria.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.repository.AutorRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class LivroControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private AutorRepository repository;
	
	@Test
	void naoDeveriaCadastrarLivroComDadosIncompletos() throws Exception {
		String json = "{}";
		
		mvc
		.perform(
				post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isBadRequest());
	}

	@Test
	void deveriaCadastrarLivroComDadosCompletos() throws Exception {
		Autor autor = new Autor();
		autor.setNome("fulano");
		autor.setEmail("fulano@email.com");
		autor.setDataNascimento(LocalDate.now().minusYears(40));
		repository.save(autor);
				
		String json = "{\"titulo\":\"Inserindo teste do controller\",\"dataLancamento\":\"2021-06-10\",\"numeroPaginas\":200,\"autor_id\":" + autor.getId() + "}";
		String jsonRetorno = "{\"titulo\":\"Inserindo teste do controller\",\"dataLancamento\":\"2021-06-10\",\"numeroPaginas\":200}";
		
		mvc
		.perform(
				post("/livros")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(content().json(jsonRetorno));
	}

}
