package org.eredlab.g4.ccl.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;

/**
 * XML������<br>
 * 
 * @author XiongChun
 * @since 2009-07-07
 */
public class XmlHelper {
	private static Log log = LogFactory.getLog(XmlHelper.class);

	/**
	 * ����XML������ڵ�Ԫ��ѹ��Dto����(���ڽڵ�ֵ��ʽ��XML��ʽ)
	 * 
	 * @param pStrXml
	 *            ��������XML�ַ���
	 * @return outDto ����Dto
	 */
	public static final Dto parseXml2DtoBasedNode(String pStrXml) {
		Dto outDto = new BaseDto();
		String strTitle = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		Document document = null;
		try {
			if (pStrXml.indexOf("<?xml") < 0)
				pStrXml = strTitle + pStrXml;
			document = DocumentHelper.parseText(pStrXml);
		} catch (DocumentException e) {
			log.error("==������Ա��ע��:==\n��XML��ʽ���ַ���ת��ΪXML DOM����ʱ����������!" + "\n��ϸ������Ϣ����:");
			e.printStackTrace();
		}
		// ��ȡ���ڵ�
		Element elNode = document.getRootElement();
		// �����ڵ�����ֵ����ѹ��Dto
		for (Iterator it = elNode.elementIterator(); it.hasNext();) {
			Element leaf = (Element) it.next();
			outDto.put(leaf.getName().toLowerCase(), leaf.getData());
		}
		return outDto;
	}

	/**
	 * ����XML������ڵ�Ԫ��ѹ��Dto����(���ڽڵ�ֵ��ʽ��XML��ʽ)
	 * 
	 * @param pStrXml
	 *            ��������XML�ַ���
	 * @param pXPath
	 *            �ڵ�·��(���磺"//paralist/row" ���ʾ���ڵ�paralist�µ�row�ڵ��xPath·��)
	 * @return outDto ����Dto
	 */
	public static final Dto parseXml2DtoBasedNode(String pStrXml, String pXPath) {
		Dto outDto = new BaseDto();
		String strTitle = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		Document document = null;
		try {
			if (pStrXml.indexOf("<?xml") < 0)
				pStrXml = strTitle + pStrXml;
			document = DocumentHelper.parseText(pStrXml);
		} catch (DocumentException e) {
			log.error("==������Ա��ע��:==\n��XML��ʽ���ַ���ת��ΪXML DOM����ʱ����������!" + "\n��ϸ������Ϣ����:");
			e.printStackTrace();
		}
		// ��ȡ���ڵ�
		Element elNode = document.getRootElement();
		// �����ڵ�����ֵ����ѹ��Dto
		for (Iterator it = elNode.elementIterator(); it.hasNext();) {
			Element leaf = (Element) it.next();
			outDto.put(leaf.getName().toLowerCase(), leaf.getData());
		}
		return outDto;
	}

	/**
	 * ����XML������ڵ�Ԫ��ѹ��Dto����(��������ֵ��ʽ��XML��ʽ)
	 * 
	 * @param pStrXml
	 *            ��������XML�ַ���
	 * @param pXPath
	 *            �ڵ�·��(���磺"//paralist/row" ���ʾ���ڵ�paralist�µ�row�ڵ��xPath·��)
	 * @return outDto ����Dto
	 */
	public static final Dto parseXml2DtoBasedProperty(String pStrXml, String pXPath) {
		Dto outDto = new BaseDto();
		String strTitle = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		Document document = null;
		try {
			if (pStrXml.indexOf("<?xml") < 0)
				pStrXml = strTitle + pStrXml;
			document = DocumentHelper.parseText(pStrXml);
		} catch (DocumentException e) {
			log.error("==������Ա��ע��:==\n��XML��ʽ���ַ���ת��ΪXML DOM����ʱ����������!" + "\n��ϸ������Ϣ����:");
			e.printStackTrace();
		}
		// ����Xpath�����ڵ�
		Element elRoot = (Element) document.selectSingleNode(pXPath);
		// �����ڵ�����ֵ����ѹ��Dto
		for (Iterator it = elRoot.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			outDto.put(attribute.getName().toLowerCase(), attribute.getData());
		}
		return outDto;
	}

	/**
	 * ��Dtoת��Ϊ����XML��׼�淶��ʽ���ַ���(���ڽڵ�ֵ��ʽ)
	 * 
	 * @param dto
	 *            �����Dto����
	 * @param pRootNodeName
	 *            �������
	 * @return string ����XML��ʽ�ַ���
	 */
	public static final String parseDto2Xml(Dto pDto, String pRootNodeName) {
		Document document = DocumentHelper.createDocument();
		// ����һ����Ԫ�ؽڵ�
		document.addElement(pRootNodeName);
		Element root = document.getRootElement();
		Iterator keyIterator = pDto.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = pDto.getAsString(key);
			Element leaf = root.addElement(key);
			leaf.setText(value);
		}
		// ��XML��ͷ������Ϣ��ȥ
		String outXml = document.asXML().substring(39);
		return outXml;
	}

	/**
	 * ��Dtoת��Ϊ����XML��׼�淶��ʽ���ַ���(��������ֵ��ʽ)
	 * 
	 * @param pDto
	 *            �����Dto����
	 * @param pRootNodeName
	 *            ���ڵ���
	 * @param pFirstNodeName
	 *            һ���ڵ���
	 * @return string ����XML��ʽ�ַ���
	 */
	public static final String parseDto2Xml(Dto pDto, String pRootNodeName, String pFirstNodeName) {
		Document document = DocumentHelper.createDocument();
		// ����һ����Ԫ�ؽڵ�
		document.addElement(pRootNodeName);
		Element root = document.getRootElement();
		root.addElement(pFirstNodeName);
		Element firstEl = (Element) document.selectSingleNode("/" + pRootNodeName + "/" + pFirstNodeName);
		Iterator keyIterator = pDto.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = pDto.getAsString(key);
			firstEl.addAttribute(key, value);
		}
		// ��XML��ͷ������Ϣ��ȥ
		String outXml = document.asXML().substring(39);
		return outXml;
	}

	/**
	 * ��List��������ת��Ϊ����XML��ʽ�淶���ַ���(���ڽڵ�����ֵ�ķ�ʽ)
	 * 
	 * @param pList
	 *            �����List����(List���������Dto��VO��Domain�����Լ�)
	 * @param pRootNodeName
	 *            ���ڵ�����
	 * @param pFirstNodeName
	 *            �нڵ�����
	 * @return string ����XML��ʽ�ַ���
	 */
	public static final String parseList2Xml(List pList, String pRootNodeName, String pFirstNodeName) {
		Document document = DocumentHelper.createDocument();
		Element elRoot = document.addElement(pRootNodeName);
		for (int i = 0; i < pList.size(); i++) {
			Dto dto = (Dto) pList.get(i);
			Element elRow = elRoot.addElement(pFirstNodeName);
			Iterator it = dto.entrySet().iterator();
			while (it.hasNext()) {
				Dto.Entry entry = (Dto.Entry) it.next();
				elRow.addAttribute((String) entry.getKey(), String.valueOf(entry.getValue()));
			}
		}
		String outXml = document.asXML().substring(39);
		return outXml;
	}

	/**
	 * ��List��������ת��Ϊ����XML��ʽ�淶���ַ���(���ڽڵ�ֵ�ķ�ʽ)
	 * 
	 * @param pList
	 *            �����List����(List���������Dto��VO��Domain�����Լ�)
	 * @param pRootNodeName
	 *            ���ڵ�����
	 * @param pFirstNodeName
	 *            �нڵ�����
	 * @return string ����XML��ʽ�ַ���
	 */
	public static final String parseList2XmlBasedNode(List pList, String pRootNodeName, String pFirstNodeName) {
		Document document = DocumentHelper.createDocument();
		Element output = document.addElement(pRootNodeName);
		for (int i = 0; i < pList.size(); i++) {
			Dto dto = (Dto) pList.get(i);
			Element elRow = output.addElement(pFirstNodeName);
			Iterator it = dto.entrySet().iterator();
			while (it.hasNext()) {
				Dto.Entry entry = (Dto.Entry) it.next();
				Element leaf = elRow.addElement((String) entry.getKey());
				leaf.setText(String.valueOf(entry.getValue()));
			}
		}
		String outXml = document.asXML().substring(39);
		return outXml;
	}

	/**
	 * ��XML�淶���ַ���תΪList����(XML���ڽڵ�����ֵ�ķ�ʽ)
	 * 
	 * @param pStrXml
	 *            ����ķ���XML��ʽ�淶���ַ���
	 * @return list ����List����
	 */
	public static final List parseXml2List(String pStrXml) {
		List lst = new ArrayList();
		String strTitle = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
		Document document = null;
		try {
			if (pStrXml.indexOf("<?xml") < 0)
				pStrXml = strTitle + pStrXml;
			document = DocumentHelper.parseText(pStrXml);
		} catch (DocumentException e) {
			log.error("==������Ա��ע��:==\n��XML��ʽ���ַ���ת��ΪXML DOM����ʱ����������!" + "\n��ϸ������Ϣ����:");
			e.printStackTrace();
		}
		// ��ȡ�����ڵ�
		Element elRoot = document.getRootElement();
		// ��ȡ���ڵ�������ӽڵ�Ԫ��
		Iterator elIt = elRoot.elementIterator();
		while (elIt.hasNext()) {
			Element el = (Element) elIt.next();
			Iterator attrIt = el.attributeIterator();
			Dto dto = new BaseDto();
			while (attrIt.hasNext()) {
				Attribute attribute = (Attribute) attrIt.next();
				dto.put(attribute.getName().toLowerCase(), attribute.getData());
			}
			lst.add(dto);
		}
		return lst;
	}
}