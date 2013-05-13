package org.rapid_framework.generator.genUtil;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-3
 * Time: ?¦¶2:08
 * desc:
 */
public class ExtField {
    String xtype = "textfield";
    String fieldLabel;
    String name;
    Object value;
    Boolean allowBlank;
    String anchor = "95%";
    Integer maxLength;
    Boolean allowDecimals;
    Integer decimalPrecision;
    String format;
    String store;
    Boolean editReadOnly;
    String vtype;
    Object items;

    public ExtField() {
    }

    public ExtField(String xtype, String fieldLabel, String name, Boolean allowBlank, String anchor, Integer maxLength, Boolean allowDecimals) {
        this.xtype = xtype;
        this.fieldLabel = fieldLabel;
        this.name = name;
        this.allowBlank = allowBlank;
        this.anchor = anchor;
        this.maxLength = maxLength;
        this.allowDecimals = allowDecimals;
    }

    public String getXtype() {
        return xtype;
    }

    public void setXtype(String xtype) {
        this.xtype = xtype;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAllowBlank() {
        return allowBlank;
    }

    public void setAllowBlank(Boolean allowBlank) {
        this.allowBlank = allowBlank;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getAllowDecimals() {
        return allowDecimals;
    }

    public void setAllowDecimals(Boolean allowDecimals) {
        this.allowDecimals = allowDecimals;
    }

    public Integer getDecimalPrecision() {
        return decimalPrecision;
    }

    public void setDecimalPrecision(Integer decimalPrecision) {
        this.decimalPrecision = decimalPrecision;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Boolean getEditReadOnly() {
        return editReadOnly;
    }

    public void setEditReadOnly(Boolean editReadOnly) {
        this.editReadOnly = editReadOnly;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }
}
