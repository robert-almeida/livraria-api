package br.com.alura.livraria.service;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.livraria.dto.UsuarioDto;
import br.com.alura.livraria.dto.UsuarioFormDto;
import br.com.alura.livraria.infra.DisparadorDeEmail;
import br.com.alura.livraria.modelo.Perfil;
import br.com.alura.livraria.modelo.Usuario;
import br.com.alura.livraria.repository.PerfilRepository;
import br.com.alura.livraria.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private DisparadorDeEmail disparadorDeEmail;

	public Page<UsuarioDto> listar(Pageable paginacao) {
		return repository.findAll(paginacao).map(t -> modelMapper.map(t, UsuarioDto.class));
	}

	@Transactional
	public UsuarioDto cadastrar(UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);

		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		usuario.adicionarPerfil(perfil);
		
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(bCryptPasswordEncoder.encode(senha));

		usuario.setId(null);
		repository.save(usuario);
		
		String destinatario = usuario.getEmail();
		String assunto = "Livaria - Bem vindo(a)";
		
		String mensagem = String.format("Olá %s!\n\n"
				+ "Segue seus dados de acesso ao sistema Livraria:"
				+ "\nLogin: %s"
				+ "\nSenha:%s", usuario.getNome(), usuario.getLogin(), senha);
		
		disparadorDeEmail.dispararEmail(destinatario, assunto, mensagem);
		
		return modelMapper.map(usuario, UsuarioDto.class);
	}

}
