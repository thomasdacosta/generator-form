package br.com.generator.form.test.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.generator.form.data.TemplateRepository;

@RunWith(value=MockitoJUnitRunner.class)
public class TemplateRepositoryMockTest {
	
	@Mock
	private TemplateRepository templateRepository;
	
	@Test
	public void testInsertDocument() {
		System.out.println(templateRepository);
	}

}
