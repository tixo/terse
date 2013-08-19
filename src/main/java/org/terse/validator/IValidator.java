package org.terse.validator;

import java.util.Map;

public interface IValidator {
	public String validate(String value, Map<String, String> conditionMap, String message) throws Exception ;
}
