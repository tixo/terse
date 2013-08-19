package org.terse.validator;

import java.util.HashMap;
import java.util.Map;
import org.dom4j.Document;

public class ValidatorConfig {
	
	private static Map<String, Document> configMap = new HashMap<String, Document>();

	protected static void put(String key, Document document) {
		configMap.put(key, document);
	}

	protected static Document get(String key) {
		return configMap.get(key);
	}
}
