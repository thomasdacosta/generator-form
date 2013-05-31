package br.com.generator.form.wrappers;

import br.com.generator.form.data.ReturnCode;

/**
 * Wrapper para retorno caso ocorra algum erro no acesso das 
 * opera��es da aplica��o
 * 
 * @author thomasdacosta
 *
 */
public class ReturnCodeWrapper {
	
	
	/**
	 * Mensagem de retorno quando template � inserido
	 * 
	 * @return
	 */
	public static ReturnCode insertSucess() {
		ReturnCode returnCode = new ReturnCode();
		returnCode.setCode(1000);
		returnCode.setMsg("Template inserido com sucesso");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno quando template � atualizado
	 * 
	 * @return
	 */
	public static ReturnCode updateSucess() {
		ReturnCode returnCode = new ReturnCode();
		returnCode.setCode(6000);
		returnCode.setMsg("Template atualizado com sucesso");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno quando ocorre um erro de convers�o
	 * 
	 * @return
	 */
	public static ReturnCode erroConversionJsonToJava() {
		ReturnCode returnCode = new ReturnCode();
		returnCode.setCode(7000);
		returnCode.setMsg("Erro ao converter objeto");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno caso n�o tenha sido encontrado nenhum template
	 * 
	 * @return
	 */
	public static ReturnCode emptyList() {
		ReturnCode returnCode = new ReturnCode();
		returnCode.setCode(2000);
		returnCode.setMsg("Nao existe template cadastrado");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno caso n�o tenha encontrado o objeto informado
	 * 
	 * @return
	 */
	public static ReturnCode notFound() {
		ReturnCode returnCode = new ReturnCode();
		returnCode.setCode(3000);
		returnCode.setMsg("Informacao nao existe");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno de alguma exce��o da aplica��o
	 * 
	 * @return
	 */
	public static ReturnCode exceptionError() {
		ReturnCode returnCode = new ReturnCode();
		returnCode.setCode(5000);
		returnCode.setMsg("Ocorreu um erro na aplicacao. Tente novamente mais tarde");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno quando um template � excluido
	 * 
	 * @return
	 */
	public static ReturnCode deleteSucess() {
		ReturnCode returnCode = new ReturnCode();
		returnCode.setCode(4000);
		returnCode.setMsg("Template excluido com sucesso");
		return returnCode;
	}

}
