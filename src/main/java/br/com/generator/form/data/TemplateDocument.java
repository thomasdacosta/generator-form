package br.com.generator.form.data;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * POJO relacionado ao template da aplicacao
 * 
 * @author thomasdacosta
 *
 */
@Document(collection="template")
public class TemplateDocument {
	
	@Id
	private String id;
	
	@NotNull(message="title nao pode ser nulo")
	private String title;

	@Valid
	@NotNull(message="fields n‹o pode ser vazio")
	private List<FieldsDocument> fields;
	
	public TemplateDocument() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FieldsDocument> getFields() {
		return fields;
	}

	public void setFields(List<FieldsDocument> fields) {
		this.fields = fields;
	}
	
	public void addFieldsDocument(FieldsDocument fieldsDocument) {
		if (fields == null) {
			fields = new ArrayList<FieldsDocument>();
		}
		fields.add(fieldsDocument);
	}
	
}
