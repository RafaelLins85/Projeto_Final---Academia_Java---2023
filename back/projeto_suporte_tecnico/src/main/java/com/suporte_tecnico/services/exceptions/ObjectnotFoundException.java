package  com.suporte_tecnico.services.exceptions;

public class ObjectnotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	// ESSA CLASSE SERÁ USADA PARA CORRIGIR ERROS DE EXCEÇÕES DA CLASSE TecnicoResource. 
	// MAS, O MÉTODO DE CORREÇÃO ESTÁ DENTRO DA CLASSE TecnicoService.
	// E SERÁ PELO MÉTODO DA CLASSE TecnicoService, QUE SERÁ USADO EM TecnicoResource, ONDE ACONTECERÁ A CORREÇÃO.

	// Usando dois construtores da classe RuntimeException 
	public ObjectnotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectnotFoundException(String message) {
		super(message);
	}

}