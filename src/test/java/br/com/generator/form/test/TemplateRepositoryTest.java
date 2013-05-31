package br.com.generator.form.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.TemplateRepository;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

/**
 * Teste de integracao para validar o acesso ao MongoDB via Spring Data
 * Existe um MongoDB embedded para efetuar as validacoes. 
 * Essa API pode ser vista no site https://github.com/flapdoodle-oss/embedmongo.flapdoodle.de
 * 
 * @author thomasdacosta
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/templateRepositoryTest.xml" })
public class TemplateRepositoryTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private TemplateRepository templateRepository;

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
			mongodExe = runtime.prepare(new MongodConfig(Version.Main.PRODUCTION, 12345, Network.localhostIsIPv6()));
			mongod = mongodExe.start();
			mongo = new Mongo("localhost", 12345);
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
	 * Testa a insercao de um documento
	 */
	@Test
	public void testInsertDocument() {
		try {
			// Criando documento para insercao
			templateRepository.dropCollection();
			TemplateDocument templateDocument = TemplateDocumentGenerated.getTemplateDocumentForTest();
			templateRepository.insert(templateDocument);

			// Validando se documento foi inserido com sucesso
			assertEquals(templateRepository.findAll().size(), 1);
			assertNotNull(templateRepository.find(templateDocument.getId()));
			assertNull(templateRepository.find(UUID.randomUUID().toString()));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Testa a atualizacao de um documento
	 */
	@Test
	public void testUpdateDocument() {
		try {
			// Criando documento para insercao
			templateRepository.dropCollection();
			TemplateDocument templateDocument = TemplateDocumentGenerated.getTemplateDocumentForTest();
			templateRepository.insert(templateDocument);

			// Verificando o total de documentos na colecao
			assertEquals(templateRepository.findAll().size(), 1);

			// Buscando documento inserido
			TemplateDocument templateSearch = templateRepository.find(templateDocument.getId());
			assertNotNull(templateSearch);
			assertEquals(templateSearch.getFields().size(), 2);
			assertEquals(templateSearch.getFields().get(0).getRadios().size(), 3);

			// Modificando documento inserido
			templateSearch.setTitle("modificando o titulo");
			templateSearch.addFieldsDocument(TemplateDocumentGenerated.getFieldsDocumentForTest());
			templateSearch.getFields().get(0).addRadioDocument(TemplateDocumentGenerated.getRadioDocumentForTest());
			templateRepository.update(templateSearch);

			// Validando documento atualizado
			TemplateDocument templateSearch2 = templateRepository.find(templateSearch.getId());
			assertNotNull(templateSearch2);
			assertEquals(templateSearch2.getFields().size(), 3);
			assertEquals(templateSearch2.getFields().get(0).getRadios().size(), 4);
			assertEquals(templateSearch2.getTitle(), "modificando o titulo");
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Testa a remocao de um documento
	 */
	@Test
	public void testDeleteDocument() {
		try {
			// Criando documento para insercao
			templateRepository.dropCollection();
			TemplateDocument templateDocument = TemplateDocumentGenerated.getTemplateDocumentForTest();
			templateRepository.insert(templateDocument);

			// Verificando se documento foi inserido
			assertNotNull(templateRepository.find(templateDocument.getId()));

			// Removendo documento
			templateRepository.delete(templateDocument.getId());

			// Verificando se documento foi removido
			assertNull(templateRepository.find(templateDocument.getId()));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	/**
	 * Testa a listagem de varios documentos
	 */
	@Test
	public void testListDocument() {
		try {
			// Inserindo varios documentos
			templateRepository.dropCollection();
			for (int i = 0; i < 30; i++) {
				templateRepository.insert(TemplateDocumentGenerated.getTemplateDocumentForTest());
			}

			// Verificando se todos foram inseridos
			assertEquals(templateRepository.findAll().size(), 30);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
}
