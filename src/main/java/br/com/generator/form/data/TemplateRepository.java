package br.com.generator.form.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repositorio do Spring Data que efetua as principais operacoes
 * relacionadas aos templates da aplicacao
 * 
 * @author thomasdacosta
 *
 */
@Repository
public class TemplateRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public TemplateRepository() {
	}
	
	/**
	 * Busca todos os templates da aplicacao
	 * 
	 * @return
	 */
	public List<TemplateDocument> findAll() {
		return mongoTemplate.findAll(TemplateDocument.class);
	}
	
	/**
	 * Insere um template
	 * 
	 * @param templateDocument
	 */
	public void insert(TemplateDocument templateDocument) {
		mongoTemplate.insert(templateDocument);
	}
	
	/**
	 * Atualiza um template
	 * 
	 * @param templateDocument
	 */
	public void update(TemplateDocument templateDocument) {
		mongoTemplate.save(templateDocument);
	}
	
	/**
	 * Busca um template
	 * 
	 * @param id
	 * @return
	 */
	public TemplateDocument find(String id) {
		return mongoTemplate.findById(id, TemplateDocument.class);
	}
	
	/**
	 * Remove um template
	 * 
	 * @param id
	 */
	public void delete(String id) {
		TemplateDocument templateDocument = new TemplateDocument();
		templateDocument.setId(id);
		mongoTemplate.remove(templateDocument);
	}
	
	/**
	 * Remove todos os templates
	 */
	public void dropCollection() {
		mongoTemplate.dropCollection(TemplateDocument.class);
	}

}
