package com.common.util;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class XmlParseUtil {

	@SuppressWarnings("unchecked")
	public static <M> M parseXML(Class<M> className, File xmlFile) throws Exception {
		JAXBContext context = JAXBContext.newInstance(className);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		M m = (M) unmarshaller.unmarshal(xmlFile);
		
		return m;
	}
	
}
