package org.terse.validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringValidator implements IValidator {

	private static final String MIN_LENGTH = "minLength";
	private static final String MAX_LENGTH = "maxLength";
	private static final String REGEX = "regex";
	
	
	@Override
	public String validate(String value, Map<String, String> conditionMap,String message) throws Exception {
		Integer min = ValidatorUtil.getConditionNumber(conditionMap, MIN_LENGTH);
		Integer max = ValidatorUtil.getConditionNumber(conditionMap, MAX_LENGTH);
		String regex = ValidatorUtil.getConditionString(conditionMap, REGEX);
		if(validateLength(value,min,max) && validateRegex(value,min,max)){
			return null;
		}else{
			return message;
		}
		
	}

	private boolean validateRegex(String value, Integer min, Integer max) {
		return false;
	}

	private boolean validateLength(String value,Integer min,Integer max){
		return false;
	}

	
	private boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
