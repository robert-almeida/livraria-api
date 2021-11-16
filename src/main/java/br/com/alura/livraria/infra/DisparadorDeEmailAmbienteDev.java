package br.com.alura.livraria.infra;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Profile({"default","test"})
public class DisparadorDeEmailAmbienteDev implements DisparadorDeEmail{
	
	@Async
	public void dispararEmail(String destinatario, String assunto, String mensagem) {
		System.out.println("ENVIANDO EMAIL");
		System.out.println("Destinatario: " + destinatario);
		System.out.println("Assunto: " + assunto);
		System.out.println("Mensagem: " + mensagem);
	}
}
