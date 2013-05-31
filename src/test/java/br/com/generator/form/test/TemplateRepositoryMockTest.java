package br.com.generator.form.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.TemplateRepository;

/**
 * Efetua os testes repositorio de dados da aplicação
 * 
 * @author thomasdacosta
 *
 */
@RunWith(value=MockitoJUnitRunner.class)
public class TemplateRepositoryMockTest {
	
	@Mock
	private TemplateRepository templateRepository;
	
	/**
	 * Testa a inserção de um documento
	 */
	@Test
	public void testInsertDocument() {
		TemplateDocument templateDocument = mock(TemplateDocument.class);
		when(templateRepository.find(templateDocument.getId())).thenReturn(templateDocument);

		templateRepository.insert(templateDocument);
		assertNotNull(templateRepository.find(templateDocument.getId()));
	}
	
	/**
	 * Testa a listagem dos documentos
	 */
	@Test
	public void testListDocument() {
		List<TemplateDocument> templateDocuments = new ArrayList<TemplateDocument>();
		for (int i=0;i<15;i++) {
			templateDocuments.add(mock(TemplateDocument.class));
		}
		
 		when(templateRepository.findAll()).thenReturn(templateDocuments);
 		assertEquals(templateRepository.findAll().size(), 15);
	}

}
