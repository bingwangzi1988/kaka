package org.rapid_framework.generator.provider.db.table.model;

import org.rapid_framework.generator.genUtil.FieldConverter;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-1
 * Time: ?¦¶5:45
 * desc:
 */
public class ColumnWeb extends Column implements java.io.Serializable,Cloneable{

    private boolean formField = true;
    private boolean searchField;
    private boolean gridField = true;
    private String xType;
    private ForeignLink selectCode = new ForeignLink();
    private String vType;
    private boolean editReadOnly;

    public ColumnWeb() {
    }

    public ColumnWeb(Table table, int sqlType, String sqlTypeName, String sqlName, int size, String decimalDigits, boolean isPk, boolean isNullable, boolean isIndexed, boolean isUnique, String defaultValue, String remarks) {
        super(table, sqlType, sqlTypeName, sqlName, size, decimalDigits, isPk, isNullable, isIndexed, isUnique, defaultValue, remarks);

        this.xType = FieldConverter.getXtype(this);
    }

    public ColumnWeb(Table table, int sqlType, String sqlTypeName, String sqlName, int size, String decimalDigits, boolean isPk, boolean isNullable, boolean isIndexed, boolean isUnique, String defaultValue, String remarks, boolean formField, boolean gridField) {
        super(table, sqlType, sqlTypeName, sqlName, size, decimalDigits, isPk, isNullable, isIndexed, isUnique, defaultValue, remarks);
        this.formField = formField;
        this.gridField = gridField;

        this.xType = FieldConverter.getXtype(this);
    }

    public boolean isFormField() {
        return formField;
    }

    public void setFormField(boolean formField) {
        this.formField = formField;
    }

    public boolean isSearchField() {
        return searchField;
    }

    public void setSearchField(boolean searchField) {
        this.searchField = searchField;
    }

    public boolean isGridField() {
        return gridField;
    }

    public void setGridField(boolean gridField) {
        this.gridField = gridField;
    }

    public String getxType() {
        return xType;
    }

    public void setxType(String xType) {
//        if (xType.equals("combo"))
//            xType = "d"+xType;
        this.xType = xType;
    }

    public ForeignLink getSelectCode() {
        return selectCode;
    }

    public void setSelectCode(ForeignLink selectCode) {
        this.selectCode = selectCode;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public boolean isEditReadOnly() {
        return editReadOnly;
    }

    public void setEditReadOnly(boolean editReadOnly) {
        this.editReadOnly = editReadOnly;
    }

    public boolean getNeedStore() {
    	return "dcombo".equals(getxType()) || "radiogroup".equals(getxType()) || "checkboxgroup".equals(getxType());
//        return getxType().equals("dcombo") || getxType().equals("radiogroup") || getxType().equals("checkboxgroup");
    }

    public boolean isEacode() {
        return "EACODE".equals(getSelectCode().getTableName());
    }
}
