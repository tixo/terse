package org.terse.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class URL implements TemplateMethodModel{
	@SuppressWarnings("unchecked")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args==null || args.size()!=1){
			throw new TemplateModelException("参数错误");
		}
		return args.get(0);
	}

}
