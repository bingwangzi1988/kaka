package org.rapid_framework.generator.genUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.lang.StringUtils;
import org.rapid_framework.generator.provider.db.table.model.Column;
import org.rapid_framework.generator.provider.db.table.model.ColumnWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-3
 * Time: ?Χ1:47
 * desc:
 */
public class FieldConverter {

    static HashMap<String, String> hashMap = new HashMap<String, String>();

    static {
        hashMap.put("String", "textfield");
        hashMap.put("Short", "numberfield");
        hashMap.put("Integer", "numberfield");
        hashMap.put("Long", "numberfield");
        hashMap.put("Float", "numberfield");
        hashMap.put("Double", "numberfield");
        hashMap.put("BigDecimal", "numberfield");
        hashMap.put("Date", "datefield");

    }

    public static String getXtype(Column column) {
        return hashMap.get(column.getSimpleJavaType());
    }

    public static String getFieldLabel(Column column) {
        return column.getColumnAlias();
    }

    public static String getName(Column column) {
        //todo databasetpye
        return column.getColumnNameFirstLower();
    }

    public static Boolean getAllowBlank(Column column) {
        return column.isNullable();
    }

    public static Integer getMaxLength(ColumnWeb column) {
        if ("textfield".equals(column.getxType()) || "textarea".equals(column.getxType()))
            return column.getSize();
        else
            return null;
    }

    public static Boolean getAllowDecimals(ColumnWeb column) {
        if ("numberfield".equals(column.getxType())) {
            if (column.getIsFloatColumn())
                return true;
            else if (column.getIsIntegerColumn())
                return false;
        }
        return null;
    }

    public static Integer getDecimalPrecision(ColumnWeb column) {
        if ("numberfield".equals(column.getxType())) {
            if (column.getIsFloatColumn()) {
                if (StringUtils.isNotBlank(column.getDecimalDigits())) {
                    return Integer.parseInt(column.getDecimalDigits());
                }
            }
        }
        return null;
    }

    public static String getVtype(ColumnWeb column) {
        if ("textfield".equals(column.getxType()) || "textarea".equals(column.getxType()))
            return column.getvType();
        else
            return null;
    }

    public static Boolean getEditReadOnly(ColumnWeb column) {
        return column.isEditReadOnly() ? column.isEditReadOnly() : null;
    }

    public static String getFormat(ColumnWeb column) {
        if ("datefield".equals(column.getxType()))
            return "Y-m-d";
        else
            return null;
    }

    public static String getComboStore(ColumnWeb column) {
        if (column.getNeedStore()) {
            return column.getColumnNameFirstLower() + "Store";
        } else
            return null;
    }

    public static String ConverterToForm(ColumnWeb column) {
        ExtField extField = new ExtField();
        extField.setXtype(column.getxType());
        extField.setAllowBlank(getAllowBlank(column));
        extField.setFieldLabel(getFieldLabel(column));
        extField.setMaxLength(getMaxLength(column));
        extField.setAllowDecimals(getAllowDecimals(column));
        extField.setDecimalPrecision(getDecimalPrecision(column));
        extField.setName(getName(column));
        extField.setFormat(getFormat(column));
        extField.setVtype(getVtype(column));
        extField.setEditReadOnly(getEditReadOnly(column));
        extField.setStore(getComboStore(column));
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerDefaultValueProcessor(Integer.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        jsonConfig.registerDefaultValueProcessor(Boolean.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });

        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
        });
        JSONObject jsonObject = JSONObject.fromObject(extField, jsonConfig);
        String result = jsonObject.toString();
        return result.replaceAll("(\"store\":)\"(\\w*)\"", "$1$2");
    }

    public static String ConverterToFormRadio(ColumnWeb column) {
        ExtField compositefield = new ExtField();
        compositefield.setXtype("radiogroup");
        compositefield.setFieldLabel(getFieldLabel(column));
        compositefield.setName(getName(column));
        compositefield.setStore(getComboStore(column));

        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.registerDefaultValueProcessor(Integer.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });

        jsonConfig.registerDefaultValueProcessor(Boolean.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        //value 为null的字段不拼接
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
        });
        JSONObject jsonObject = JSONObject.fromObject(compositefield, jsonConfig);

        String result = jsonObject.toString();
        return result.replaceAll("(\"store\":)\"(\\w*)\"", "$1$2");
    }

    public static String ConverterToSearch(ColumnWeb column) {
        ExtField extField = new ExtField();
        extField.setXtype(column.getxType());
        extField.setFieldLabel(getFieldLabel(column));
        extField.setAllowDecimals(getAllowDecimals(column));
        extField.setName(getName(column));
        extField.setFormat(getFormat(column));
        extField.setStore(getComboStore(column));
        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.registerDefaultValueProcessor(Integer.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });

        jsonConfig.registerDefaultValueProcessor(Boolean.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });

        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
        });
        JSONObject jsonObject = JSONObject.fromObject(extField, jsonConfig);

        String result = jsonObject.toString();
        return result.replaceAll("(\"store\":)\"(\\w*)\"", "$1$2");
    }

    public static String ConverterToDateSearch(ColumnWeb column) {
        ExtField compositefield = new ExtField();
        compositefield.setXtype("compositefield");
        compositefield.setFieldLabel(getFieldLabel(column));

        ExtField beginField = new ExtField();
        beginField.setXtype(column.getxType());
        //todo
        beginField.setName(getName(column) + "Begin");
        beginField.setFormat(getFormat(column));

        ExtField endField = new ExtField();
        endField.setXtype(column.getxType());
        endField.setName(getName(column) + "End");
        endField.setFormat(getFormat(column));

        ExtField diplayField = new ExtField();
        diplayField.setXtype("displayfield");
        diplayField.setValue("-");

        List<ExtField> items = new ArrayList<ExtField>();
        items.add(beginField);
        items.add(diplayField);
        items.add(endField);

        compositefield.setItems(items);

        JsonConfig jsonConfig = new JsonConfig();

        jsonConfig.registerDefaultValueProcessor(Integer.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });

        jsonConfig.registerDefaultValueProcessor(Boolean.class,
                new DefaultValueProcessor() {
                    public Object getDefaultValue(Class type) {
                        return null;
                    }
                });
        //value 为null的字段不拼接
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
        });
        JSONObject jsonObject = JSONObject.fromObject(compositefield, jsonConfig);

        String result = jsonObject.toString();
        return result;
    }

}
