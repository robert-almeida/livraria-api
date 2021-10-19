package br.com.alura.livraria.repository;

import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.livraria.dto.ItemPublicacoesDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class LivroRepositoryTest {
	
	@Autowired
	private LivroRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	void deveriaRetornarRelatorioDeLivrosPorAutor() {
		Autor a1 = new Autor("Fulano", "fulano@email.com", LocalDate.now(), "Teste do programa"); em.persist(a1);
		Autor a2 = new Autor("Ciclano", "ciclano@email.com", LocalDate.now(), "Teste do programa"); em.persist(a2);
		Autor a3 = new Autor("Beltrano", "beltrano@email.com", LocalDate.now(), "Teste do programa"); em.persist(a3);
		Autor a4 = new Autor("Pepito", "pepito@email.com", LocalDate.now(), "Teste do programa"); em.persist(a4);
	
		
		Livro l1 = new Livro("Testanto a aplicacao 1", LocalDate.now(), 101, a1); em.persist(l1);
		Livro l2 = new Livro("Testanto a aplicacao 2", LocalDate.now(), 201, a2); em.persist(l2);
		Livro l3 = new Livro("Testanto a aplicacao 3", LocalDate.now(), 301, a3); em.persist(l3);
		Livro l4 = new Livro("Testanto a aplicacao 4", LocalDate.now(), 401, a4); em.persist(l4);
		
		List<ItemPublicacoesDto> relatorio = repository.relatorioLivrosPorAutor();
		Assertions
		.assertThat(relatorio)
		.hasSize(4)
		.extracting(ItemPublicacoesDto::getAutor, ItemPublicacoesDto::getQuantidadeLivros, ItemPublicacoesDto::getPercentual)
		.containsExactlyInAnyOrder(
				Assertions.tuple("Fulano", 1l, 0.25000),
				Assertions.tuple("Ciclano", 1l, 0.25000),
				Assertions.tuple("Beltrano", 1l, 0.25000),
				Assertions.tuple("Pepito", 1l, 0.25000)
				);		
		
	}

}
