package br.com.alura.livraria.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutorFormDto {
	
	@NotBlank
	private String nome;
	
	@Email
	private String email;
	
	@PastOrPresent
	private LocalDate dataNascimento;
	
	private String miniCurriculo;
}