package br.com.alura.livraria.service;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.dto.AtualizacaoLivroFormDto;
import br.com.alura.livraria.dto.LivroDto;
import br.com.alura.livraria.dto.LivroFormDto;
import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;

@Service
public class LivroService {

	@Autowired
	private LivroRepository livroRepository;
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public Page<LivroDto> listar(Pageable paginacao) {
		Page<Livro> livros = livroRepository.findAll(paginacao);
		return livros.map(t -> modelMapper.map(t, LivroDto.class));
	}
	
	@Transactional
	public LivroDto cadastrar(LivroFormDto dto) {
		Livro livro = modelMapper.map(dto, Livro.class);
		
		try {
			Autor autor = autorRepository.getById(dto.getAutorId());
			
			livro.setId(null);
			livro.setAutor(autor);
			livroRepository.save(livro);
			
			return modelMapper.map(livro, LivroDto.class);
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Autor inexistente!");
		}
	}

	@Transactional
	public LivroDto atualizar(AtualizacaoLivroFormDto dto) {
		Livro livro = livroRepository.getById(dto.getId());
		
		livro.atualizarInformacoes(dto.getTitulo(), dto.getDataLancamento(), dto.getNumeroPaginas());
		return modelMapper.map(livro, LivroDto.class);
	}

	@Transactional
	public void excluir(Long id) {
		livroRepository.deleteById(id);
	}

	public LivroDto detalhar(Long id) {
		Livro livro = livroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		return modelMapper.map(livro, LivroDto.class);
	}
}