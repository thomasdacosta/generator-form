package br.com.generator.form.test;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.generator.form.data.TemplateRepository;

/**
 * Teste para popular o MongoDB e efetuar testes
 * 
 * @author thomasdacosta
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/populateMongoDBIntegrationTest.xml" })
public class PopulateMongoDBIntegrationTest {
	
	@Autowired
	private TemplateRepository templateRepository;
	
	/**
	 * Popula o MongoDB com 5 templates
	 */
	@Test
	@Ignore
	public void testPopulateMongoDB() {
		templateRepository.dropCollection();
		for (int i=1;i<=5;i++) {
			templateRepository.insert(TemplateDocumentGenerated.getTemplateDocumentForTest());
		}
	}

}
