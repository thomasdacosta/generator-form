package br.com.generator.form.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.generator.form.data.FieldsDocument;
import br.com.generator.form.data.RadioDocument;
import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.ValidatorCodeError;
import br.com.generator.form.wrappers.ValidatorFields;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/beanValidatorTest.xml" })
public class BeanValidatorTest {
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private ValidatorFields validatorFields;
	
	@Test
	public void testValidatorFields() {
		TemplateDocument templateDocument = new TemplateDocument();
		List<ValidatorCodeError> validatorCodeErrors = validatorFields.validate(templateDocument);
		assertTrue(validatorCodeErrors.size() > 0);
	}
	
	@Test
	public void testValidator() {
		// Validando os campos do TemplateDocument
		Set<ConstraintViolation<TemplateDocument>> constraintViolations = null;
		TemplateDocument templateDocument = new TemplateDocument();
		
		constraintViolations = validator.validate(templateDocument);
		assertEquals(constraintViolations.size(), 2);
		
		// Validando TemplateDocument com um FieldsDocument
		FieldsDocument fieldsDocument = new FieldsDocument();
		templateDocument.addFieldsDocument(fieldsDocument);
		
		constraintViolations = validator.validate(templateDocument);
		assertEquals(constraintViolations.size(), 3);
		
		// Validando TemplateDocument com um FieldsDocument e um RadioDocument
		RadioDocument radioDocument = new RadioDocument();
		fieldsDocument.addRadioDocument(radioDocument);
		
		constraintViolations = validator.validate(templateDocument);
		assertEquals(constraintViolations.size(), 5);
	}
	
}
