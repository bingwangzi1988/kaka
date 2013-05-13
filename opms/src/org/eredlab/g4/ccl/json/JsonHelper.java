package org.eredlab.g4.ccl.json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.util.G4Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Json������<br>
 *
 * @author XiongChun
 * @since 2009-07-07
 */
public class JsonHelper {

    private static Log log = LogFactory.getLog(JsonHelper.class);

    /**
     * ����������ʱ���ʽ��Java����ϵ�л�ΪJson���ϸ�ʽ
     *
     * @param pObject �����Java����
     * @return
     */
    public static final String encodeObject2Json(Object pObject) {
        String jsonString = "[]";
        if (G4Utils.isEmpty(pObject)) {
            // log.warn("�����Java����Ϊ��,���ܽ������л�ΪJson���ϸ�ʽ.����!");
        } else {
            if (pObject instanceof ArrayList) {
                JSONArray jsonArray = JSONArray.fromObject(pObject);
                jsonString = jsonArray.toString();
            } else {
                JSONObject jsonObject = JSONObject.fromObject(pObject);
                jsonString = jsonObject.toString();
            }
        }
        if (log.isInfoEnabled()) {
            log.info("���л����JSON�������:\n" + jsonString);
        }
        return jsonString;
    }

    /**
     * ����������ʱ���ʽ��Java����ϵ�л�ΪJson���ϸ�ʽ<br>
     * Json-Lib�ڴ�������ʱ���ʽ����Ҫʵ����JsonValueProcessor�ӿ�,�����������ṩһ�����صķ����Ժ���<br>
     * ����ʱ���ʽ��Java����������л�
     *
     * @param pObject �����Java����
     * @return
     */
    public static final String encodeObject2Json(Object pObject, String pFormatString) {
        String jsonString = "[]";
        if (G4Utils.isEmpty(pObject)) {
            // log.warn("�����Java����Ϊ��,���ܽ������л�ΪJson���ϸ�ʽ.����!");
        } else {
            JsonConfig cfg = new JsonConfig();
            cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(pFormatString));
            cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(pFormatString));
            cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(pFormatString));
            if (pObject instanceof ArrayList) {
                JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
                jsonString = jsonArray.toString();
            } else {
                JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
                jsonString = jsonObject.toString();
            }
        }
        if (log.isInfoEnabled()) {
            log.info("���л����JSON�������:\n" + jsonString);
        }
        return jsonString;
    }

//    public static JSONObject encodeObject2Json(Object pObject, String pFormatString) {
//
//        JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
//        return jsonString;
//    }

    /**
     * ����ҳ��Ϣѹ��JSON�ַ���
     * �����ڲ�ʹ��,�����Ⱪ¶
     *
     * @param JSON�ַ���
     * @param totalCount
     * @return ���غϲ�����ַ���
     */
    private static String encodeJson2PageJson(String jsonString, Integer totalCount) {
        jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + jsonString + "}";
        if (log.isInfoEnabled()) {
            log.info("�ϲ����JSON�������:\n" + jsonString);
        }
        return jsonString;
    }

    /**
     * ֱ�ӽ�ListתΪ��ҳ����Ҫ��Json���ϸ�ʽ
     *
     * @param list        ��Ҫ�����List����
     * @param totalCount  ��¼����
     * @param pDataFormat ʱ�����ڸ�ʽ��,��null�����List����������ʱ������
     */
    public static final String encodeList2PageJson(List list, Integer totalCount, String dataFormat) {
        String subJsonString = "";
        if (G4Utils.isEmpty(dataFormat)) {
            subJsonString = encodeObject2Json(list);
        } else {
            subJsonString = encodeObject2Json(list, dataFormat);
        }
        String jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + subJsonString + "}";
        return jsonString;
    }

    public static JSONObject encodeList2JSONArray(List list, Integer totalCount, String dataFormat) {
        JSONArray jsonArray;
        JsonConfig cfg = new JsonConfig();
        cfg.registerDefaultValueProcessor(Integer.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(Long.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(Double.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(BigDecimal.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(Boolean.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });

        if (G4Utils.isEmpty(dataFormat)) {
            cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl());
            cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd"));
            cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd"));

            jsonArray = JSONArray.fromObject(list, cfg);
        } else {
            cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(dataFormat));
            cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(dataFormat));
            cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(dataFormat));

            jsonArray = JSONArray.fromObject(list, cfg);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.element("root", jsonArray);
        jsonObject.element("total", totalCount);
        return jsonObject;
    }

    public static JSONObject encodeObject2JSONObject(Object object, String dataFormat) {
        JSONObject jsonObject;
        JsonConfig cfg = new JsonConfig();
        cfg.registerDefaultValueProcessor(Integer.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(Long.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(Double.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(BigDecimal.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        cfg.registerDefaultValueProcessor(Boolean.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });

        if (G4Utils.isEmpty(dataFormat)) {

            cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl("yyyy-MM-dd"));
            cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd"));
            cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl("yyyy-MM-dd"));

            jsonObject = JSONObject.fromObject(object, cfg);
        } else {
            cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonValueProcessorImpl(dataFormat));
            cfg.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(dataFormat));
            cfg.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(dataFormat));

            jsonObject = JSONObject.fromObject(object, cfg);
        }

        return jsonObject;
    }

    /**
     * ������ϵ�л�Ϊ��������������Json��ʽ
     *
     * @param pObject       ��ϵ�л��Ķ���
     * @param pFormatString ����ʱ���ʽ��,���Ϊnull����Ϊû������ʱ�����ֶ�
     * @return
     */
    public static String encodeDto2FormLoadJson(Dto pDto, String pFormatString) {
        String jsonString = "";
        String sunJsonString = "";
        if (G4Utils.isEmpty(pFormatString)) {
            sunJsonString = encodeObject2Json(pDto);
        } else {
            sunJsonString = encodeObject2Json(pDto, pFormatString);
        }
        jsonString = "{success:"
                + (G4Utils.isEmpty(pDto.getAsString("success")) ? "true" : pDto.getAsString("success")) + ",data:"
                + sunJsonString + "}";
        if (log.isInfoEnabled()) {
            log.info("���л����JSON�������:\n" + jsonString);
        }
        return jsonString;
    }

    /**
     * ����һJson�������ΪDTOJava����
     *
     * @param jsonString �򵥵�Json����
     * @return dto
     */
    public static Dto parseSingleJson2Dto(String jsonString) {
        Dto dto = new BaseDto();
        if (G4Utils.isEmpty(jsonString)) {
            return dto;
        }
        JSONObject jb = JSONObject.fromObject(jsonString);
        dto = (BaseDto) JSONObject.toBean(jb, BaseDto.class);
        return dto;
    }

    /**
     * ������Json���ϸ�ʽת��ΪList����
     *
     * @param jsonString ����Json����,��ʽ�������������Լ<br>
     *                   {"1":{"name":"����.��","age":"27"},
     *                   "2":{"name":"���ӵ�","age":"72"}}
     * @return List
     */
    public static List parseJson2List(String jsonString) {
        List list = new ArrayList();
        JSONObject jbJsonObject = JSONObject.fromObject(jsonString);
        Iterator iterator = jbJsonObject.keySet().iterator();
        while (iterator.hasNext()) {
            Dto dto = parseSingleJson2Dto(jbJsonObject.getString(iterator.next().toString()));
            list.add(dto);
        }
        return list;
    }
}
