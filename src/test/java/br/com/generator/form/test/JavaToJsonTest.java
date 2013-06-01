package br.com.generator.form.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.wrappers.JSon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Efetua teste de convesao de Java para JSON usando a API Google GSON
 * 
 * @author thomasdacosta
 *
 */
public class JavaToJsonTest {
	
	private static final Logger logger = Logger.getLogger(JavaToJsonTest.class);
	
	/**
	 * Testa a conversao de um objeto Java para JSON
	 */
	@Test
	public void testConvertJavaToJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(TemplateDocumentGenerated.getTemplateDocumentForTest());
		assertNotNull(json);
		logger.info(json);
	}

	/**
	 * Testa a conversao de uma lista de templates para JSON
	 */
	@Test
	public void testConvertJavaListToJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<TemplateDocument> documents = new ArrayList<TemplateDocument>();
		
		for (int i=1;i<=10;i++) {
			documents.add(TemplateDocumentGenerated.getTemplateDocumentForTest());
		}
		String json = gson.toJson(documents);
		assertNotNull(json);
		logger.info(json);
	}
	
	/**
	 * Testa a conversao de JSON para Java
	 */
	@Test
	public void testConvertJsonToJava() {
		Gson gson = new Gson();
		String url = this.getClass().getResource("/json/templateDocument.json").getPath();
		
		try (BufferedReader in = new BufferedReader(new FileReader(url))) {  
		     StringBuffer json = new StringBuffer();
		     String line = null;
		     while ((line = in.readLine()) != null) {  
		         json.append(line);  
		     }  
		     
		     TemplateDocument templateDocument = gson.fromJson(json.toString(), TemplateDocument.class);
		     assertNotNull(templateDocument);
		 } catch (Exception ex) {  
		     fail(ex.getMessage());  
		 }  
	}
	
	/**
	 * Testa a classe Wrapper de conversao de Java para JSON
	 */
	@Test
	public void testWrapperJavaToJson() {
		String json = JSon.javaToJson(TemplateDocumentGenerated.getTemplateDocumentForTest());
		assertNotNull(json);
		logger.info(json);
	}
	
	/**
	 * Testa a classe Wrapper de conversao de JSON para Java
	 */
	@Test
	public void testWrapperJsonToJava() {
		String url = this.getClass().getResource("/json/templateDocument.json").getPath();
		
		try (BufferedReader in = new BufferedReader(new FileReader(url))) {  
		     StringBuffer json = new StringBuffer();
		     String line = null;
		     while ((line = in.readLine()) != null) {  
		         json.append(line);  
		     }  
		     
		     TemplateDocument templateDocument = JSon.jsonToJava(json.toString());
		     assertNotNull(templateDocument);
		 } catch (Exception ex) {  
		     fail(ex.getMessage());  
		 }
	}

}
