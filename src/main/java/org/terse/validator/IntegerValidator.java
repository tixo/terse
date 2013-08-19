package org.terse.validator;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class IntegerValidator implements IValidator{

	public static final String MIN_NUMBER = "min";
	
	public static final String MAX_NUMBER = "max";
	
	@Override
	public String validate(String value,Map<String, String> conditionMap,String message) throws Exception {
		Integer min = ValidatorUtil.getConditionNumber(conditionMap,MIN_NUMBER);
		Integer max = ValidatorUtil.getConditionNumber(conditionMap,MAX_NUMBER);
		int num = 0;
		try{
			num = Integer.parseInt(value);
		}catch(Exception e){
			return getErrorMessage(message,min,max);
		}
		if(conditionMap!=null && conditionMap.size()>0){
			if((min==null || (min!=null && num > min)) && 
				(max==null || (max!=null && num<max))){
				return null;
			}else{
				return getErrorMessage(message,min,max);
			}
		}
		return null;
	}
	
	/**
	 * 获取错误信息
	 * @param message
	 * @param min
	 * @param max
	 * @return
	 */
	private String getErrorMessage(String message,Integer min,Integer max){
		if(message==null){
			StringBuffer errorMessage = new StringBuffer("数字必须");
			if(min!=null){
				errorMessage.append("大于").append(min);
			}
			if(max!=null){
				errorMessage.append("小于").append(max);
			}
			message = errorMessage.toString();
		}else{
			message.replaceAll("\\$\\{min\\}", String.valueOf(min));
			message.replaceAll("\\$\\{max\\}", String.valueOf(max));
		}
		String result = null;
		try {
			result = new String(message.getBytes("gbk"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
