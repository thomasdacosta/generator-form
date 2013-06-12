package br.com.generator.form.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import br.com.generator.form.data.FieldsDocument;
import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.TemplateDocumentBusiness;
import br.com.generator.form.wrappers.JSon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Efetua teste de convesao de Java para JSON usando a API Google GSON
 * 
 * @author thomasdacosta
 * 
 */
public class JavaToJsonTest {

	private static final Logger logger = Logger.getLogger(JavaToJsonTest.class);
	
	@Test
	public void testJsonTemplateDocumentData() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String url = this.getClass().getResource("/json/templateDocumentData.json").getPath();

		try (BufferedReader in = new BufferedReader(new FileReader(url))) {
			StringBuffer json = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				json.append(line);
			}
			
			TemplateDocument templateDocument = gson.fromJson(json.toString(), TemplateDocument.class);
			assertNotNull(templateDocument);
			
			String jsonStr = gson.toJson(templateDocument);
			assertNotNull(json);
			logger.info(jsonStr);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}		
	}

	@Test
	public void testJsonMap() {
		Gson gson = new Gson();
		String url = this.getClass().getResource("/json/data.json").getPath();

		try (BufferedReader in = new BufferedReader(new FileReader(url))) {
			StringBuffer json = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				json.append(line);
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> objects = gson.fromJson(json.toString(), Map.class);
			assertEquals(objects.size(), 3);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

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

		for (int i = 1; i <= 10; i++) {
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
	
	@Test
	public void testAddElementJson() {
		try {
			TemplateDocument templateDocument1 = new TemplateDocument();
			templateDocument1.setTitle("titulo");
			
			TemplateDocument templateDocument2 = new TemplateDocument();
			templateDocument2.setTitle("titulo");		
			
			List<TemplateDocument> templateDocuments = new ArrayList<>();
			templateDocuments.add(templateDocument1);
			templateDocuments.add(templateDocument2);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(templateDocuments);
			JsonElement jsonElement = gson.toJsonTree(templateDocuments);
			jsonElement.getAsJsonArray().get(0).getAsJsonObject().addProperty("data", "1000");
			jsonElement.getAsJsonArray().get(1).getAsJsonObject().addProperty("data", "2000");
			
			json = gson.toJson(jsonElement);
			assertNotNull(json);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testValidateData() {
		TemplateDocumentBusiness templateDocumentBusiness = new TemplateDocumentBusiness();
		TemplateDocument templateDocument = new TemplateDocument();
		templateDocument.setTitle("form de validacao");
		
		FieldsDocument fieldsDocument1 = new FieldsDocument();
		fieldsDocument1.setLabel("nome");
		fieldsDocument1.setMaxLength(10);
		fieldsDocument1.setRequired(true);
		fieldsDocument1.setType("text");
		
		FieldsDocument fieldsDocument2 = new FieldsDocument();
		fieldsDocument2.setLabel("email");
		fieldsDocument2.setMaxLength(100);
		fieldsDocument2.setRequired(false);
		fieldsDocument2.setType("text");	
		
		templateDocument.addFieldsDocument(fieldsDocument1);
		templateDocument.addFieldsDocument(fieldsDocument2);
		
		List<Map<String, Object>> maps = new ArrayList<>();
		Map<String, Object> value = new HashMap<>();
		value.put("nome", "Thomas da Costa");
		value.put("email", "thomasdacosta@gmail.com");
		maps.add(value);
		
		String json = templateDocumentBusiness.validateData(templateDocument, maps);
		assertNotNull(json);
		logger.info(json);
	}
	
	@Test
	public void testCreateJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		JsonElement jsonElementNome = new JsonObject();
		jsonElementNome.getAsJsonObject().addProperty("label", "nome");
		
		JsonElement jsonElementEmail = new JsonObject();
		jsonElementEmail.getAsJsonObject().addProperty("label", "email");
		
		JsonArray jsonArray = new JsonArray();
		jsonArray.add(jsonElementNome);
		jsonArray.add(jsonElementEmail);
		
		JsonElement jsonElement = new JsonObject();
		jsonElement.getAsJsonObject().add("fields", jsonArray);
		String json = gson.toJson(jsonElement);
		
		assertNotNull(json);
		logger.info(json);		
	}
	
}
