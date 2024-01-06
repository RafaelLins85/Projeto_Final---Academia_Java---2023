package com.suporte_tecnico;

import java.util.logging.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.suporte_tecnico.log.Log;

@SpringBootApplication
public class ProjetoSuporteTecnicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoSuporteTecnicoApplication.class, args);

		try {
			Log meuLogger = new Log("Log.txt");
			meuLogger.logger.setLevel(Level.FINE);
			// meuLogger.logger.setLevel(Level.WARNING);
			meuLogger.logger.info("Sistema de suporte técnico.");
			meuLogger.logger.warning("Muito bem vindo!");
			meuLogger.logger.warning("Faça bom uso.");

		} catch (Exception e) {

		}
	}
}
