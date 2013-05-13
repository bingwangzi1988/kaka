package org.rapid_framework.generator.provider.db.table.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-28
 * Time: ÏÂÎç1:34
 * desc:
 */
public class ForeignLink implements Serializable {

    private String tableName = "EACODE";
    private String field ="FIELD";
    private String code ="CODE";
    private String codedesc ="CODEDESC";
    private String fieldvalue;

    public ForeignLink() {
    }

    public ForeignLink(String tableName, String field, String code, String codedesc, String fieldvalue) {
        this.tableName = tableName;
        this.field = field;
        this.code = code;
        this.codedesc = codedesc;
        this.fieldvalue = fieldvalue;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodedesc() {
        return codedesc;
    }

    public void setCodedesc(String codedesc) {
        this.codedesc = codedesc;
    }

    public String getFieldvalue() {
        return fieldvalue;
    }

    public void setFieldvalue(String fieldvalue) {
        this.fieldvalue = fieldvalue;
    }
}
