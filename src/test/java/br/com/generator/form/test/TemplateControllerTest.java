package br.com.generator.form.test;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.generator.form.data.FieldsDocument;
import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.TemplateRepository;
import br.com.generator.form.wrappers.JSon;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration(locations = { "classpath:spring/templateControllerTest.xml" }),
	@ContextConfiguration(locations = { "classpath:spring/dispatcher-servlet.xml" }),
})
public class TemplateControllerTest {
	
	@Autowired
	private WebApplicationContext applicationContext;
	
	@Autowired
	private TemplateRepository templateRepository;
	
	private TemplateDocument templateDocument1 = null; 
	private TemplateDocument templateDocument2 = null;
	private TemplateDocument templateDocument3 = null;
	
	private MockMvc mvc;
	
	private void setupData() {
		templateRepository.dropCollection();
		
		String id1 = UUID.randomUUID().toString();
		templateDocument1 = TemplateDocumentGenerated.getTemplateDocumentForTest(id1);
		templateDocument1.setTitle("template numero 1");
		
		String id2 = UUID.randomUUID().toString();
		templateDocument2 = TemplateDocumentGenerated.getTemplateDocumentForTest(id2);
		templateDocument2.setTitle("template numero 2");
		
		String id3 = UUID.randomUUID().toString();
		templateDocument3 = TemplateDocumentGenerated.getTemplateDocumentForTest(id3);
		templateDocument3.setTitle("template numero 3");
		
		templateRepository.insert(templateDocument1);
		templateRepository.insert(templateDocument2);
		templateRepository.insert(templateDocument3);
	}
	
	@BeforeClass
	public static void setup() {
		System.setProperty("http.proxyHost", "proxy.viverebrasil");
		System.setProperty("http.proxyPort", "3128");
		System.setProperty("http.proxyUser", "thomas.costa");
		System.setProperty("http.proxyPassword", "takuma79");		
	}
	
	@Before
	public void setupMvc() {
		mvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}
	
	@Test
	public void testGet() throws Exception {
		setupData();
		mvc.perform(get("/templates").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].title", equalTo("template numero 1")));
	}
	
	@Test
	public void testGet_Id() throws Exception {
		setupData();
		mvc.perform(get("/templates/" + templateDocument3.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", equalTo("template numero 3")));
	}
	
	@Test
	public void testDelete_Id() throws Exception {
		setupData();
		mvc.perform(delete("/templates/" + templateDocument2.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))				
				.andExpect(jsonPath("$.code", equalTo(4000)));
		
		mvc.perform(get("/templates/" + templateDocument2.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))				
				.andExpect(jsonPath("$.code", equalTo(3000)));				
	}
	
	@Test
	public void testPost() throws Exception {
		setupData();
		
		UUID uuid = UUID.randomUUID();
		TemplateDocument templateDocument = new TemplateDocument();
		templateDocument.setId(uuid.toString());
		templateDocument.setTitle("novo template");
		
		FieldsDocument fieldsDocument = new FieldsDocument();
		fieldsDocument.setLabel("label");
		fieldsDocument.setType("type");
		
		templateDocument.addFieldsDocument(fieldsDocument);

		mvc.perform(post("/templates")
				.content(JSon.javaToJson(templateDocument).getBytes()))
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))				
				.andExpect(jsonPath("$.code", equalTo(1000)));
	}
	
	@Test
	public void testPut_Id() throws Exception {
		setupData();
		templateDocument2.setTitle("atualizando um template");
		
		mvc.perform(put("/templates/" + templateDocument2.getId())
				.content(JSon.javaToJson(templateDocument2).getBytes()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))				
				.andExpect(jsonPath("$.code", equalTo(6000)));
		
		mvc.perform(get("/templates/" + templateDocument2.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))				
				.andExpect(jsonPath("$.title", equalTo("atualizando um template")));		
	}

}
