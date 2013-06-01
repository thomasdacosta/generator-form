package br.com.generator.form.test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.AfterClass;
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

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

import br.com.generator.form.data.FieldsDocument;
import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.TemplateRepository;
import br.com.generator.form.wrappers.JSon;

/**
 * Teste para validacao do Controller do Spring MVC
 * 
 * @author thomasdacosta
 *
 */
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
	
	private static MongodExecutable mongodExe;
	private static MongodProcess mongod;
	private static Mongo mongo;

	/**
	 * Inicializa o MongoDB embedded
	 */
	@BeforeClass
	public static void setup() {
		MongodStarter runtime = MongodStarter.getDefaultInstance();
		try {
			mongodExe = runtime.prepare(new MongodConfig(Version.Main.PRODUCTION, 12346, Network.localhostIsIPv6()));
			mongod = mongodExe.start();
			mongo = new Mongo("localhost", 12346);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	/**
	 * Para o MongoDB embedded
	 */
	@AfterClass
	public static void tearDown() {
		try {
			mongod.stop();
			mongodExe.stop();
			mongo.close();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	/**
	 * Inicializa as informacoes para o testes 
	 */
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
	
	/**
	 * Inicializa o MockMvc do Spring
	 */
	@Before
	public void setupMvc() {
		mvc = MockMvcBuilders.webAppContextSetup(this.applicationContext).build();
	}
	
	/**
	 * Efetua o teste de requisicao por GET 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGet() throws Exception {
		setupData();
		mvc.perform(get("/templates").accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].title", equalTo("template numero 1")));
	}
	
	/**
	 * Efetua o teste de requisicao por GET com Id
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGet_Id() throws Exception {
		setupData();
		mvc.perform(get("/templates/" + templateDocument3.getId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title", equalTo("template numero 3")));
	}
	
	/**
	 * Efetua o teste de requisicao por DELETE
	 * 
	 * @throws Exception
	 */
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
	
	/**
	 * Efetua o teste de requisicao por POST
	 * 
	 * @throws Exception
	 */
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
	
	/**
	 * Efetua o teste de requisicao por PUT
	 * 
	 * @throws Exception
	 */
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
