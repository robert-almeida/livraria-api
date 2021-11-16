package br.com.alura.livraria.infra;

public interface DisparadorDeEmail {

	void dispararEmail(String destinatario, String assunto, String mensagem);

}