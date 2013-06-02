package br.com.generator.form.data;

import javax.validation.constraints.NotNull;

/**
 * POJO relacionado aos radios de um template
 * 
 * @author thomasdacosta
 *
 */
public class RadioDocument {
	
	@NotNull(message="label de um radio nao pode ser vazio")
	private String label;
	
	@NotNull(message="value de um radio nao pode ser vazio")
	private String value;
	
	public RadioDocument() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
