package br.com.generator.form.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.generator.form.data.TemplateDocument;
import br.com.generator.form.data.TemplateRepository;
import br.com.generator.form.wrappers.JSon;
import br.com.generator.form.wrappers.ReturnCodeWrapper;

@Controller
@RequestMapping("/templates")
public class TemplateController {
	
	private static final String JSON_PAGE = "/json";
	private static final Logger logger = Logger.getLogger(TemplateController.class);
	
	@Autowired
	private TemplateRepository templateRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public String listTemplates(ModelMap modelMap) {
		try {
			List<TemplateDocument> documents = templateRepository.findAll();
			if (documents != null && documents.size() > 0) {
				modelMap.addAttribute("json", JSon.javaToJson(documents));
			} else {
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.emptyList()));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.exceptionError()));
		}
		return JSON_PAGE;
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.GET)
	public String listTemplatesById(@PathVariable String id, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = templateRepository.find(id);
			if (templateDocument != null) {
				modelMap.addAttribute("json", JSon.javaToJson(templateDocument));
			} else {
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.notFound()));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.exceptionError()));
		}
		return JSON_PAGE;
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public String deleteTemplate(@PathVariable String id, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = templateRepository.find(id);
			if (templateDocument != null) {
				templateRepository.delete(id);
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.deleteSucess()));
			} else {
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.notFound()));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.exceptionError()));
		}
		return JSON_PAGE;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String insertTemplate(@RequestBody String body, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = JSon.jsonToJava(body);
			if (templateDocument != null) {
				templateRepository.insert(templateDocument);
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.insertSucess()));
			} else {
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.erroConversionJsonToJava()));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.exceptionError()));			
		}
		return JSON_PAGE;
	}
	
	@RequestMapping(value="{id}", method = RequestMethod.PUT)
	public String updateTemplate(@PathVariable String id, @RequestBody String body, ModelMap modelMap) {
		try {
			TemplateDocument templateDocument = JSon.jsonToJava(body);
			if (templateDocument != null) {
				templateRepository.update(templateDocument);
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.updateSucess()));
			} else {
				modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.erroConversionJsonToJava()));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			modelMap.addAttribute("json", JSon.javaToJson(ReturnCodeWrapper.exceptionError()));			
		}
		return JSON_PAGE;
	}
	
}
