package org.eredlab.g4.ccl.id.test;

import org.rapid_framework.generator.genUtil.ExtField;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-5-11
 * Time: ÉÏÎç10:33
 * desc:
 */
public class MyTest {


    public static void main(String[] args) {
        ExtField extField = new ExtField();
        extField.setStore("sunliwei");
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
        });

        JSONObject jsonObject = JSONObject.fromObject(extField, jsonConfig);
        String result = jsonObject.toString();
        String dd = result.replaceAll("(\"store\":)\"(\\w*)\"", "$1$2");
        System.out.println(dd);


//        String o = "\"name\":\"partyclasscd\",\"store\":\"partyclasscdStore\",\"xtype\":\"dcombo\"";
//        Matcher m = Pattern.compile("(store\":)\"(\\w*)\"").matcher(o);
//        m.find();
//        String n = m.replaceAll(m.group(1) + m.group(2));
//
//        System.out.println(o);
//        System.out.println(n);
    }
}
