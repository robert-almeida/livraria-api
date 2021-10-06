package br.com.alura.livraria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.livraria.dto.ItemPublicacoesDto;
import br.com.alura.livraria.modelo.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

	@Query("select new br.com.alura.livraria.dto.ItemPublicacoesDto("
			+ "a.nome, "
			+ "count(1), "
			+ "count(1) * 1.0 / ((select count(1) from Livro) * 1.0)) "
			+ "from Livro l "
			+ "join Autor a "
			+ "on l.autor = a.id "
			+ "group by a.nome")
	List<ItemPublicacoesDto> relatorioLivrosPorAutor();

}
