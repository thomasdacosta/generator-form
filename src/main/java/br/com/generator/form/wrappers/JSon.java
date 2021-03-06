package br.com.generator.form.wrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.generator.form.data.TemplateDocument;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Efetua a conversao de Java para JSON usando o Google GSON
 * 
 * @author thomasdacosta
 *
 */
public class JSon {
	
	/**
	 * Converte um objeto de Java para JSON
	 * 
	 * @param object
	 * @return
	 */
	public static String javaToJson(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(object);
		return json;
	}
	
	/**
	 * Converte um JSON para Java
	 * 
	 * @param json
	 * @return
	 */
	public static TemplateDocument jsonToJava(String json) {
		TemplateDocument templateDocument = null;
		Gson gson = new Gson();
		templateDocument = gson.fromJson(json, TemplateDocument.class);
		return templateDocument;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> jsonToJavaData(String json) {
		List<Map<String, Object>> listMap = new ArrayList<>();
		Gson gson = new Gson();
		listMap = gson.fromJson(json, listMap.getClass());
		return listMap;
	}	

}
