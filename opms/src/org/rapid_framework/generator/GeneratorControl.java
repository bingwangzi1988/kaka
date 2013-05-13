package org.rapid_framework.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.xml.sax.InputSource;

import org.rapid_framework.generator.provider.db.DataSourceProvider;
import org.rapid_framework.generator.util.FileHelper;
import org.rapid_framework.generator.util.GLogger;
import org.rapid_framework.generator.util.IOHelper;
import org.rapid_framework.generator.util.SqlExecutorHelper;
import org.rapid_framework.generator.util.StringHelper;
import org.rapid_framework.generator.util.SystemHelper;
import org.rapid_framework.generator.util.XMLHelper;
import freemarker.ext.dom.NodeModel;

/**
 * ������ģ�������,����ģ���п��Կ���������ִ����ؿ��Ʋ���
 * ��: �Ƿ񸲸�Ŀ���ļ�
 * 
 * <pre>
 * ʹ�÷�ʽ:
 * ������freemarker����veloctiy��ֱ�ӿ���ģ�������
 * 
 * ${gg.generateFile('d:/g_temp.log','info_from_generator')}
 * ${gg.setIgnoreOutput(true)}
 * </pre>
 * 
 * ${gg.setIgnoreOutput(true)}������Ϊtrue���������
 * 
 * @author badqiu
 *
 */
public class GeneratorControl {
	private boolean isOverride = Boolean.parseBoolean(System.getProperty("gg.isOverride","false")); 
	private boolean isAppend = false; //no pass
	private boolean ignoreOutput = false; 
	private boolean isMergeIfExists = true; //no pass
	private String mergeLocation; 
	private String outRoot; 
	private String outputEncoding; 
	private String sourceFile; 
	private String sourceDir; 
	private String sourceFileName; 
	private String sourceEncoding; //no pass //? �ѵ�process����ȷ��sourceEncoding
	private String outputFile;
	
	/** load xml data */
	public NodeModel loadXml(String file) {
		return loadXml(file,true);
	}	
	/** load xml data */
	public NodeModel loadXml(String file,boolean removeXmlNamespace) {
		try {
			if(removeXmlNamespace) {
				InputStream forEncodingInput = FileHelper.getInputStream(file);
				String encoding = XMLHelper.getXMLEncoding(forEncodingInput);
				forEncodingInput.close();
				
				InputStream input = FileHelper.getInputStream(file);
				String xml = IOHelper.toString(encoding,input);
				xml = XMLHelper.removeXmlns(xml);
				input.close();
				return NodeModel.parse(new InputSource(new StringReader(xml.trim())));
			}else {
				return NodeModel.parse(new InputSource(FileHelper.getInputStream(file)));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("loadXml error,file:"+file,e);
		}
	}

	/** load Properties data */
	public Properties loadProperties(String file) {
		try {
			Properties p = new Properties();
			InputStream in = FileHelper.getInputStream(file);
			if(file.endsWith(".xml")) {
				p.loadFromXML(in);
			}else {
				p.load(in);
			}
			in.close();
			return p;
		} catch (Exception e) {
			throw new IllegalArgumentException("loadProperties error,file:"+file,e);
		}
	}

    public void generateFile(String outputFile,String content) {
       generateFile(outputFile,content,false);
    }
	/**
	 * �����ļ�   
	 * @param outputFile
	 * @param content
	 * @param append
	 */
	public void generateFile(String outputFile,String content,boolean append) {
		try {
			String realOutputFile = null;
			if(new File(outputFile).isAbsolute()) {
				realOutputFile = outputFile ;
			}else {
				realOutputFile = new File(getOutRoot(),outputFile).getAbsolutePath();
			}
			
			if(deleteGeneratedFile) {
				GLogger.println("[delete gg.generateFile()] file:"+realOutputFile+" by template:"+getSourceFile());
				new File(realOutputFile).delete();
			}else {
				File file = new File(realOutputFile);
				FileHelper.parnetMkdir(file);
				GLogger.println("[gg.generateFile()] outputFile:"+realOutputFile+" append:"+append+" by template:"+getSourceFile());
				IOHelper.saveFile(file, content,getOutputEncoding(),append);
			}
		} catch (Exception e) {
			GLogger.warn("gg.generateFile() occer error,outputFile:"+outputFile+" caused by:"+e,e);
			throw new RuntimeException("gg.generateFile() occer error,outputFile:"+outputFile+" caused by:"+e,e);
		}
	}
	
	public boolean isOverride() {
		return isOverride;
	}
	/**���Ŀ���ļ�����,�����Ƿ�Ҫ�����ļ� */
	public void setOverride(boolean isOverride) {
		this.isOverride = isOverride;
	}

	public boolean isIgnoreOutput() {
		return ignoreOutput;
	}
	/** �����Ƿ�Ҫ�����ļ�  */
	public void setIgnoreOutput(boolean ignoreOutput) {
		this.ignoreOutput = ignoreOutput;
	}

	public boolean isMergeIfExists() {
		return isMergeIfExists;
	}

	public void setMergeIfExists(boolean isMergeIfExists) {
		this.isMergeIfExists = isMergeIfExists;
	}

	public String getMergeLocation() {
		return mergeLocation;
	}

	public void setMergeLocation(String mergeLocation) {
		this.mergeLocation = mergeLocation;
	}

	public String getOutRoot() {
		return outRoot;
	}
	/** ���ɵ������Ŀ¼ */
	public void setOutRoot(String outRoot) {
		this.outRoot = outRoot;
	}

	public String getOutputEncoding() {
		return outputEncoding;
	}
	/** �������encoding */
	public void setOutputEncoding(String outputEncoding) {
		this.outputEncoding = outputEncoding;
	}
	/** �õ�Դ�ļ� */
	public String getSourceFile() {
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	/** �õ�Դ�ļ�����Ŀ¼ */
	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	/** �õ�Դ�ļ����ļ����� */
	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	/** �õ�Դ�ļ���encoding */
	public String getSourceEncoding() {
		return sourceEncoding;
	}

	public void setSourceEncoding(String sourceEncoding) {
		this.sourceEncoding = sourceEncoding;
	}
	
	public String getOutputFile() {
	    if(outputFile != null && new File(outputFile).isAbsolute()) {
	        return outputFile;
	    }else {
	        return new File(getOutRoot(),outputFile).getAbsolutePath();
	    }
    }
	
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
    
    public boolean isExistsOutputFile() {
        return new File(outRoot,outputFile).exists();
    }
    
    public boolean outputFileMatchs(String regex) throws IOException {
        if(isExistsOutputFile()) {
            String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
            if(StringHelper.indexOfByRegex(content, regex) >= 0) {
                return true;
            }
        }
        return false;
    }
    
    public boolean outputFileContains(String s) throws IOException {
        if(isExistsOutputFile()) {
            String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
            return content.contains(s);
        }
        return false;
    }
    
    /** �õ�property,�鵽������ʹ��defaultValue */
	public String getProperty(String key,String defaultValue){
		return GeneratorProperties.getProperty(key, defaultValue);
	}
	
	public String insertAfter(String compareToken,String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile).getAbsoluteFile(), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
        return StringHelper.insertAfter(content, compareToken, str);
	}
	
	public String insertBefore(String compareToken,String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
        return StringHelper.insertBefore(content, compareToken, str);
	}
	
	public String append(String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
	    return new StringBuffer(content).append(str).toString();
	}
	
	public String prepend(String str) throws IOException {
	    String content = IOHelper.readFile(new File(outRoot,outputFile), sourceEncoding);
	    if(StringHelper.isBlank(content)) throw new IllegalArgumentException(new File(outRoot,outputFile).getAbsolutePath()+" is blank");
	    return new StringBuffer(content).insert(0,str).toString();
	}

//	public String getRequiredProperty(String key){
//		return GeneratorProperties.getRequiredProperty(key);
//	}

	/** ���û�����property,windows�򵯳������linux��Ϊ���������� */
	public String getInputProperty(String key) throws IOException {
		return getInputProperty(key, "Please input value for "+key+":");
	}
	
	public String getInputProperty(String key,String message) throws IOException {
		String v = GeneratorProperties.getProperty(key);
		if(v == null) {
			if(SystemHelper.isWindowsOS) {
				v = JOptionPane.showInputDialog(null,message,"template:"+getSourceFileName(),JOptionPane.OK_OPTION);
			}else {
				System.out.print("template:"+getSourceFileName()+","+message);
				v = new BufferedReader(new InputStreamReader(System.in)).readLine();
			}
			GeneratorProperties.setProperty(key, v);
		}
		return v;
	}
	
	public List<Map> queryForList(String sql,int limit) throws SQLException {
		Connection conn = DataSourceProvider.getConnection();
		return SqlExecutorHelper.queryForList(conn, sql, limit);
	}
	
	boolean deleteGeneratedFile = false;

    
    
    
}
