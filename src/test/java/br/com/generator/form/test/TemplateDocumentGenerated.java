package br.com.generator.form.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.generator.form.data.FieldsDocument;
import br.com.generator.form.data.RadioDocument;
import br.com.generator.form.data.TemplateDocument;

/**
 * Classe que cria os POJO do tipo TemplateDocument, FieldsDocument e RadioDocument
 * para ser utilizado nos testes unitários e integração
 * 
 * @author thomasdacosta
 *
 */
public class TemplateDocumentGenerated {
	
	/**
	 * Cria um POJO do tipo TemplateDocument
	 * 
	 * @return
	 */
	public static TemplateDocument getTemplateDocumentForTest() {
		UUID uuid = UUID.randomUUID();
		
		List<RadioDocument> radioDocuments = new ArrayList<RadioDocument>();
		List<FieldsDocument> fieldsDocuments = new ArrayList<FieldsDocument>();
		
		RadioDocument radioDocument1 = new RadioDocument();
		radioDocument1.setLabel("opcao1");
		radioDocument1.setValue("valor1");
		radioDocuments.add(radioDocument1);
		
		RadioDocument radioDocument2 = new RadioDocument();
		radioDocument2.setLabel("opcao2");
		radioDocument2.setValue("valor2");
		radioDocuments.add(radioDocument2);
		
		RadioDocument radioDocument3 = new RadioDocument();
		radioDocument3.setLabel("opcao3");
		radioDocument3.setValue("valor3");
		radioDocuments.add(radioDocument3);
		
		FieldsDocument fieldsDocument1 = new FieldsDocument();
		fieldsDocument1.setLabel("label1");
		fieldsDocument1.setMaxLength(10);
		fieldsDocument1.setPlaceholder("placeholder1");
		fieldsDocument1.setReadOnly(false);
		fieldsDocument1.setRequired(true);
		fieldsDocument1.setType("type1");
		fieldsDocument1.setValue("value1");
		fieldsDocument1.setRadios(radioDocuments);
		fieldsDocuments.add(fieldsDocument1);

		FieldsDocument fieldsDocument2 = new FieldsDocument();
		fieldsDocument2.setLabel("label1");
		fieldsDocument2.setMaxLength(10);
		fieldsDocument2.setPlaceholder("placeholder1");
		fieldsDocument2.setReadOnly(false);
		fieldsDocument2.setRequired(true);
		fieldsDocument2.setType("type1");
		fieldsDocument2.setValue("value1");
		fieldsDocuments.add(fieldsDocument2);
		
		TemplateDocument templateDocument = new TemplateDocument();
		templateDocument.setId(uuid.toString());
		templateDocument.setTitle("titulo para efetuar um teste");
		templateDocument.setFields(fieldsDocuments);
		
		return templateDocument;
	}
	
	/**
	 * Cria o POJO do tipo RadioDocument
	 * 
	 * @return
	 */
	public static RadioDocument getRadioDocumentForTest() {
		RadioDocument radioDocument = new RadioDocument();
		radioDocument.setLabel("labelRadio");
		radioDocument.setValue("valueRadio");
		return radioDocument;
	}
	
	/**
	 * Cria o POJO do tipo FieldsDocument
	 * 
	 * @return
	 */
	public static FieldsDocument getFieldsDocumentForTest() {
		FieldsDocument fieldsDocument = new FieldsDocument();
		
		fieldsDocument.setLabel("labelFieldsDocument");
		fieldsDocument.setMaxLength(10);
		fieldsDocument.setPlaceholder("placeholderFieldsDocument");
		fieldsDocument.setReadOnly(false);
		fieldsDocument.setRequired(true);
		fieldsDocument.setType("typeFieldsDocument");
		fieldsDocument.setValue("valueFieldsDocument");
		
		return fieldsDocument;
	}
	
}
