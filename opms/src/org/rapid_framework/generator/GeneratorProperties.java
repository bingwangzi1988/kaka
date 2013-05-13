package org.rapid_framework.generator;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.rapid_framework.generator.util.GLogger;
import org.rapid_framework.generator.util.PropertiesHelper;
import org.rapid_framework.generator.util.PropertyPlaceholderHelper;
import org.rapid_framework.generator.util.PropertyPlaceholderHelper.PropertyPlaceholderConfigurerResolver;
import org.rapid_framework.generator.util.typemapping.DatabaseTypeUtils;


/**
 * ������������
 * ����װ��generator.properties,generator.xml�ļ�
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class GeneratorProperties {
	static PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}", ":", false);
	
	static final String PROPERTIES_FILE_NAMES[] = new String[]{"generator.properties","generator.xml"};
	
	static PropertiesHelper props = new PropertiesHelper(new Properties(),true);
	private GeneratorProperties(){}
	static {
		reload();
	}
	
	public static void reload() {
		try {
			GLogger.println("Start Load GeneratorPropeties from classpath:"+Arrays.toString(PROPERTIES_FILE_NAMES));
			Properties p = new Properties();
			String[] loadedFiles = PropertiesHelper.loadAllPropertiesFromClassLoader(p,PROPERTIES_FILE_NAMES);
			GLogger.println("GeneratorPropeties Load Success,files:"+Arrays.toString(loadedFiles));
			
			setSepicalProperties(p, loadedFiles);
			
			setProperties(p);
		}catch(IOException e) {
			throw new RuntimeException("Load "+PROPERTIES_FILE_NAMES+" error",e);
		}
	}

	private static void setSepicalProperties(Properties p, String[] loadedFiles) {
		p.put("databaseType", getDatabaseType(p,"databaseType"));
		if(loadedFiles != null && loadedFiles.length > 0) {
			String basedir = p.getProperty("basedir");
			if(basedir != null && basedir.startsWith(".")) {
				p.setProperty("basedir", new File(new File(loadedFiles[0]).getParent(),basedir).getAbsolutePath());
			}
		}
	}
	
	private static String getDatabaseType(Properties p,String key) {
		String databaseType = DatabaseTypeUtils.getDatabaseTypeByJdbcDriver(p.getProperty("jdbc.driver"));
		return p.getProperty(key,databaseType == null ? "" : databaseType);
	}
	
	// �Զ��滻����value�� com.company �滻Ϊ com/company,������key = key+"_dir"��׺
	private static Properties autoReplacePropertiesValue2DirValue(Properties props) {
        Properties autoReplaceProperties = new Properties();
        for(Object key : getProperties().keySet()) {
            String dir_key = key.toString()+"_dir";
//            if(props.entrySet().contains(dir_key)) {
//                continue;
//            }
            String value = props.getProperty(key.toString());
            String dir_value = value.toString().replace('.', '/');
            autoReplaceProperties.put(dir_key, dir_value);           
        }
        return autoReplaceProperties;
    }
	
	public static Properties getProperties() {
		return getHelper().getProperties();
	}
	
	private static PropertiesHelper getHelper() {
		return props;
	}
	
	public static String getProperty(String key, String defaultValue) {
		return getHelper().getProperty(key, defaultValue);
	}
	
	public static String getProperty(String key) {
		return getHelper().getProperty(key);
	}
	
	public static String getRequiredProperty(String key) {
		return getHelper().getRequiredProperty(key);
	}
	
	public static int getRequiredInt(String key) {
		return getHelper().getRequiredInt(key);
	}
	
	public static boolean getRequiredBoolean(String key) {
		return getHelper().getRequiredBoolean(key);
	}
	
	public static String getNullIfBlank(String key) {
		return getHelper().getNullIfBlank(key);
	}
	
	public static void setProperty(String key,String value) {
		value = resolveProperty(value,getProperties());
		key = resolveProperty(key,getProperties());
	    GLogger.println("[setProperty()] "+key+"="+value);
		getHelper().setProperty(key, value);
		String dir_value = value.toString().replace('.', '/');
		getHelper().getProperties().put(key+"_dir", dir_value);
	}

	private static Properties resolveProperties(Properties props) {
		Properties result = new Properties();
		for(Object s : props.keySet()) {
			String sourceKey = s.toString();
			String key  = resolveProperty(sourceKey,props);
			String value = resolveProperty(props.getProperty(sourceKey),props);
			result.setProperty(key, value);
		}
		return result;
	}
	
	private static String resolveProperty(String v,Properties props) {
		PropertyPlaceholderConfigurerResolver propertyPlaceholderConfigurerResolver = new PropertyPlaceholderConfigurerResolver(props);
		return helper.replacePlaceholders(v, propertyPlaceholderConfigurerResolver);
	}
	
	public static void setProperties(Properties inputProps) {
		props = new PropertiesHelper(resolveProperties(inputProps),true);
        for(Iterator it = props.entrySet().iterator();it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            GLogger.println("[Property] "+entry.getKey()+"="+entry.getValue());
        }
        GLogger.println("");
        
        GLogger.println("[Auto Replace] [.] => [/] on generator.properties, key=source_key+'_dir', For example: pkg=com.company ==> pkg_dir=com/company  \n");
        Properties dirProperties = autoReplacePropertiesValue2DirValue(props.getProperties());
        props.getProperties().putAll(dirProperties);
	}

}
