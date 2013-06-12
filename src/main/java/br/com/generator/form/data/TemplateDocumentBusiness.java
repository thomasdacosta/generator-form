package br.com.generator.form.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TemplateDocumentBusiness {
	
	@Autowired
	private TemplateRepository templateRepository;
	
	public TemplateDocumentBusiness() {
	}
	
	public String generateDataJson(TemplateDocument templateDocument) {
		if (templateDocument != null) {
			JsonArray jsonArray = new JsonArray();
			
			for (FieldsDocument fieldsDocument : templateDocument.getFields()) {
				JsonElement jsonElement = new JsonObject();
				jsonElement.getAsJsonObject().addProperty("label", fieldsDocument.getLabel());
				jsonArray.add(jsonElement);
			}
			
			JsonElement jsonElement = new JsonObject();
			jsonElement.getAsJsonObject().add("fields", jsonArray);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(jsonElement);
			return json;
		}
		return null;
	}
	
	public String generateJson(String id) throws Exception {
		String json = null;
		
		if (!StringUtils.isEmpty(id)) {
			TemplateDocument templateDocument = templateRepository.find(id);
			if (templateDocument != null) {
				Integer totalData = 0;
				if (templateDocument.getData() != null) {
					totalData = templateDocument.getData().size();
				}
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonElement jsonElement = gson.toJsonTree(templateDocument);
				jsonElement.getAsJsonObject().addProperty("data", totalData);
				json = gson.toJson(jsonElement);
			}
		} else {
			List<TemplateDocument> templateDocuments = templateRepository.findAll();
			if (templateDocuments != null) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonElement jsonElement = gson.toJsonTree(templateDocuments);
				for (int i=0;i<=templateDocuments.size()-1;i++) {
					jsonElement.getAsJsonArray().get(i).getAsJsonObject().addProperty("data", templateDocuments.get(i).getData().size());
				}
				json = gson.toJson(jsonElement);
			}
		}
		
		return json;
	}
	
	/**
	 * Foi efetuado as validações básicas referentes aos campos
	 * 
	 * @param templateDocument
	 * @param listMap
	 * @return
	 */
	public String validateData(TemplateDocument templateDocument, List<Map<String, Object>> listMap) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		List<DataError> dataErrors = new ArrayList<>();
		
		if (templateDocument.getFields() != null && templateDocument.getFields().size() > 0) {
			for (FieldsDocument fieldsDocument : templateDocument.getFields()) {
				for (Map<String, Object> map : listMap) {
					for (String key : map.keySet()) {
						if (key.trim().equalsIgnoreCase(fieldsDocument.getLabel())) {
							String value = (String) map.get(key);
							if (fieldsDocument.getMaxLength() != null && (value.length() > fieldsDocument.getMaxLength())) {
								DataError dataError = new DataError();
								dataError.setField(key);
								dataError.setMsgError("tamanho invalido do campo");
								dataErrors.add(dataError);
							}
							
							if (fieldsDocument.getReadOnly() != null && fieldsDocument.getReadOnly()) {
								if (StringUtils.isEmpty(value)) {
									DataError dataError = new DataError();
									dataError.setField(key);
									dataError.setMsgError("campo requerido");
									dataErrors.add(dataError);									
								}
							}
						}
					}
				}
			}
		}
		
		if (dataErrors.size() > 0) {
			return gson.toJson(dataErrors);
		}
		
		return null;
	}
	
}
