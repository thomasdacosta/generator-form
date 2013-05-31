package br.com.generator.form.data;

import java.util.ArrayList;
import java.util.List;

/**
 * POJO relacionado aos fields de um template
 * 
 * @author thomasdacosta
 *
 */
public class FieldsDocument {
	
	private String label;
	private String type;
	private Boolean required;
	private Boolean readOnly;
	private String value;
	private Integer maxLength;
	private String placeholder;
	private List<RadioDocument> radios;
	
	public FieldsDocument() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public List<RadioDocument> getRadios() {
		return radios;
	}

	public void setRadios(List<RadioDocument> radios) {
		this.radios = radios;
	}
	
	public void addRadioDocument(RadioDocument radioDocument) {
		if (radios == null) {
			radios = new ArrayList<RadioDocument>();
		}
		radios.add(radioDocument);
	}

}
