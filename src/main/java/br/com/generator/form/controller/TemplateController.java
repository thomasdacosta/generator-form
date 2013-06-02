package br.com.generator.form.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.TemplateRepository;
import br.com.generator.form.data.ValidatorCodeError;
import br.com.generator.form.wrappers.JSon;
import br.com.generator.form.wrappers.ReturnCode;
import br.com.generator.form.wrappers.ValidatorFields;

/**
 * Controller para requisicoes REST
 * 
 * @author thomasdacosta
 *
 */
@Controller
@RequestMapping("/templates")
public class TemplateController {
	
	private static final Logger logger = Logger.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateRepository templateRepository;
	
	@Autowired
	private ValidatorFields validatorFields;
	
	/**
	 * Recebe as requisicoes GET
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody String listTemplates(ModelMap modelMap) {
		try {
			List<TemplateDocument> documents = templateRepository.findAll();
			if (documents != null && documents.size() > 0) {
				return JSon.javaToJson(documents);
			} else {
				return JSon.javaToJson(ReturnCode.emptyList());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return JSon.javaToJson(ReturnCode.exceptionError());
		}
	}
	
	/**
	 * Recebe as requisicoes GET com Id
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody String listTemplatesById(@PathVariable String id, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = templateRepository.find(id);
			if (templateDocument != null) {
				return JSon.javaToJson(templateDocument);
			} else {
				return JSon.javaToJson(ReturnCode.notFound());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return JSon.javaToJson(ReturnCode.exceptionError());
		}
	}
	
	/**
	 * Recebe as requisicoes DELETE
	 * 
	 * @param id
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody String deleteTemplate(@PathVariable String id, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = templateRepository.find(id);
			if (templateDocument != null) {
				templateRepository.delete(id);
				return JSon.javaToJson(ReturnCode.deleteSucess());
			} else {
				return JSon.javaToJson(ReturnCode.notFound());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return JSon.javaToJson(ReturnCode.exceptionError());
		}
	}
	
	/**
	 * Recebe as requisicoes POST
	 * 
	 * @param body
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(value=HttpStatus.CREATED)
	public @ResponseBody String insertTemplate(@RequestBody String body, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = JSon.jsonToJava(body);
			List<ValidatorCodeError> msgs = validatorFields.validate(templateDocument);
			
			if (msgs.size() > 0) {
				return JSon.javaToJson(msgs);
			} else {
				if (templateDocument != null) {
					templateRepository.insert(templateDocument);
					return JSon.javaToJson(ReturnCode.insertSucess());
				} else {
					return JSon.javaToJson(ReturnCode.erroConversionJsonToJava());
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return JSon.javaToJson(ReturnCode.exceptionError());			
		}
	}
	
	/**
	 * Recebe as requisicoes PUT
	 * 
	 * @param id
	 * @param body
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="{id}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseStatus(value=HttpStatus.OK)
	public @ResponseBody String updateTemplate(@PathVariable String id, @RequestBody String body, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = JSon.jsonToJava(body);
			if (templateDocument != null) {
				templateRepository.update(templateDocument);
				return JSon.javaToJson(ReturnCode.updateSucess());
			} else {
				return JSon.javaToJson(ReturnCode.erroConversionJsonToJava());
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return JSon.javaToJson(ReturnCode.exceptionError());			
		}
	}
	
}
