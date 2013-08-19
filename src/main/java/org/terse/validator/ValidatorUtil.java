package org.terse.validator;

import java.util.Map;

public class ValidatorUtil {
	/**
	 * 获得配置中的条件参数
	 * @param conditionMap
	 * @param key
	 * @return
	 * @throws Exception
	 */
	protected static Integer getConditionNumber(Map<String, String> conditionMap,String key) throws Exception{
		String numstr = conditionMap.get(key);
		if(numstr!=null){
			try{
				int num = Integer.parseInt(numstr);
				return num;
			}catch(Exception e){
				throw new Exception("action validator config error !!!");
			}
		}
		return null;
	}
	
	protected static String getConditionString(Map<String, String> conditionMap,String key) throws Exception{
		String str = conditionMap.get(key);
		if(str!=null){
			return str;
		}
		return null;
	}
}
