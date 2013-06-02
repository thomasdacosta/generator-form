package br.com.generator.form.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.generator.form.data.ValidatorCodeError;

public class ValidatorFields {
	
	@Autowired
	private Validator validator;
	
	public ValidatorFields() {
	}
	
	public List<ValidatorCodeError> validate(Object object) {
		List<ValidatorCodeError> validatorCodeErrors = new ArrayList<>();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			ValidatorCodeError validatorCodeError = new ValidatorCodeError();
			validatorCodeError.setField(constraintViolation.getPropertyPath().toString());
			validatorCodeError.setMessage(constraintViolation.getMessage());
			validatorCodeErrors.add(validatorCodeError);
		}
		return validatorCodeErrors;
	}

}
