package br.com.generator.form.wrappers;

import br.com.generator.form.data.ReturnCodeError;

/**
 * Wrapper para retorno caso ocorra algum erro no acesso das 
 * operacoes da aplicacao
 * 
 * @author thomasdacosta
 *
 */
public class ReturnCode {
	
	
	/**
	 * Mensagem de retorno quando template eh inserido
	 * 
	 * @return
	 */
	public static ReturnCodeError insertSucess() {
		ReturnCodeError returnCode = new ReturnCodeError();
		returnCode.setCode(1000);
		returnCode.setMsg("Template inserido com sucesso");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno quando template eh atualizado
	 * 
	 * @return
	 */
	public static ReturnCodeError updateSucess() {
		ReturnCodeError returnCode = new ReturnCodeError();
		returnCode.setCode(6000);
		returnCode.setMsg("Template atualizado com sucesso");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno quando ocorre um erro de conversao
	 * 
	 * @return
	 */
	public static ReturnCodeError erroConversionJsonToJava() {
		ReturnCodeError returnCode = new ReturnCodeError();
		returnCode.setCode(7000);
		returnCode.setMsg("Erro ao converter objeto");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno caso nao tenha sido encontrado nenhum template
	 * 
	 * @return
	 */
	public static ReturnCodeError emptyList() {
		ReturnCodeError returnCode = new ReturnCodeError();
		returnCode.setCode(2000);
		returnCode.setMsg("Nao existe template cadastrado");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno caso nao tenha encontrado o objeto informado
	 * 
	 * @return
	 */
	public static ReturnCodeError notFound() {
		ReturnCodeError returnCode = new ReturnCodeError();
		returnCode.setCode(3000);
		returnCode.setMsg("Informacao nao existe");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno de alguma excecao da aplicacao
	 * 
	 * @return
	 */
	public static ReturnCodeError exceptionError() {
		ReturnCodeError returnCode = new ReturnCodeError();
		returnCode.setCode(5000);
		returnCode.setMsg("Ocorreu um erro na aplicacao. Tente novamente mais tarde");
		return returnCode;
	}
	
	/**
	 * Mensagem de retorno quando um template eh excluido
	 * 
	 * @return
	 */
	public static ReturnCodeError deleteSucess() {
		ReturnCodeError returnCode = new ReturnCodeError();
		returnCode.setCode(4000);
		returnCode.setMsg("Template excluido com sucesso");
		return returnCode;
	}

}
