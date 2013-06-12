package br.com.generator.form.data;

/**
 * Retorna mensagem de erro de acordo com a operacao da aplicacao
 * 
 * @author thomasdacosta
 *
 */
public class ReturnCodeError {
	
	private Integer code;
	private String msg;
	
	public ReturnCodeError() {
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
