package br.com.generator.form.data;

/**
 * POJO relacionado aos radios de um template
 * 
 * @author thomasdacosta
 *
 */
public class RadioDocument {
	
	private String label;
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
