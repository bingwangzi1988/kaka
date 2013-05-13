package org.rapid_framework.generator.provider.db.table.model;


import org.apache.commons.lang.StringUtils;
import org.rapid_framework.generator.GeneratorProperties;
import org.rapid_framework.generator.provider.db.table.TableFactory;
import org.rapid_framework.generator.util.StringHelper;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * �������ɴ����Table����.��Ӧ���ݿ��table
 *
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class Table implements java.io.Serializable, Cloneable {

    String sqlName;
    String remarks;
    String className;

    /**
     * the name of the owner of the synonym if this table is a synonym
     */
    private String ownerSynonymName = null;
    /**
     * real table name for oracle SYNONYM
     */
    private String tableSynonymName = null;

    LinkedHashSet<Column> columns = new LinkedHashSet<Column>();
    List<Column> primaryKeyColumns = new ArrayList<Column>();
    Map<String, List> uniqueColumns = new HashMap<String, List>();

    private boolean paging;
    private boolean columnLock;
    private boolean canedit;
    private boolean stripeRows;
    private boolean columnLines;
    private List<String> gridPlugins = new ArrayList<String>();

    public Table() {
    }

    public Table(Table t) {
        setSqlName(t.getSqlName());
        this.remarks = t.getRemarks();
        this.className = t.getClassName();
        this.ownerSynonymName = t.getOwnerSynonymName();
        this.columns = t.getColumns();
        this.primaryKeyColumns = t.getPrimaryKeyColumns();
        this.tableAlias = t.getTableAlias();
        this.exportedKeys = t.exportedKeys;
        this.importedKeys = t.importedKeys;
    }

    public LinkedHashSet<Column> getColumns() {
        return columns;
    }

    public void setColumns(LinkedHashSet<Column> columns) {
        this.columns = columns;
    }

    public boolean getPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public String getOwnerSynonymName() {
        return ownerSynonymName;
    }

    public void setOwnerSynonymName(String ownerSynonymName) {
        this.ownerSynonymName = ownerSynonymName;
    }

    public String getTableSynonymName() {
        return tableSynonymName;
    }

    public void setTableSynonymName(String tableSynonymName) {
        this.tableSynonymName = tableSynonymName;
    }

    public boolean getColumnLock() {
        return columnLock;
    }

    public void setColumnLock(boolean columnLock) {
        this.columnLock = columnLock;
    }

    public boolean getCanedit() {
        return canedit;
    }

    public void setCanedit(boolean canedit) {
        this.canedit = canedit;
    }

    public boolean getStripeRows() {
        return stripeRows;
    }

    public void setStripeRows(boolean stripeRows) {
        this.stripeRows = stripeRows;
    }

    public boolean getColumnLines() {
        return columnLines;
    }

    public void setColumnLines(boolean columnLines) {
        this.columnLines = columnLines;
    }

    public List<String> getGridPlugins() {
        return gridPlugins;
    }

    public void setGridPlugins(List<String> gridPlugins) {
        this.gridPlugins = gridPlugins;
    }

    /**
     * ʹ�� getPkColumns() �滻
     */
    @Deprecated
    public List<Column> getPrimaryKeyColumns() {
        return primaryKeyColumns;
    }

    /**
     * ʹ�� setPkColumns() �滻
     */
    @Deprecated
    public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
        this.primaryKeyColumns = primaryKeyColumns;
    }

    /**
     * ���ݿ��б�ı�����,�������Ժܶ඼�Ǹ��ݴ���������
     */
    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public static String removeTableSqlNamePrefix(String sqlName) {
        String prefixs = GeneratorProperties.getProperty("tableRemovePrefixes", "");
        for (String prefix : prefixs.split(",")) {
            String removedPrefixSqlName = StringHelper.removePrefix(sqlName, prefix, true);
            if (!removedPrefixSqlName.equals(sqlName)) {
                return removedPrefixSqlName;
            }
        }
        return sqlName;
    }

    /**
     * ���ݿ��б�ı�ע
     */
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void setClassName(String customClassName) {
        this.className = customClassName;
    }

    /**
     * ����sqlName�õ��������ƣ�ʾ��ֵ: UserInfo
     *
     * @return
     */
    public String getClassName() {
        if (StringHelper.isBlank(className)) {
            String removedPrefixSqlName = removeTableSqlNamePrefix(sqlName);
            return StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(removedPrefixSqlName));
        } else {
            return className;
        }
    }

    /**
     * ���ݿ��б�ı������ȼ���:  getRemarks().isEmpty() ? getClassName() : getRemarks()
     */
    public String getTableAlias() {
        if (StringHelper.isNotBlank(tableAlias)) return tableAlias;
        return StringHelper.removeCrlf(StringHelper.defaultIfEmpty(getRemarks(), getClassName()));
    }

    public void setTableAlias(String v) {
        this.tableAlias = v;
    }

    /**
     * �ȼ���getClassName().toLowerCase()
     *
     * @return
     */
    public String getClassNameLowerCase() {
        return getClassName().toLowerCase();
    }

    /**
     * �õ����»��߷ָ��������ƣ���className=UserInfo,��underscoreName=user_info
     *
     * @return
     */
    public String getUnderscoreName() {
        return StringHelper.toUnderscoreName(getClassName()).toLowerCase();
    }

    /**
     * ����ֵΪgetClassName()�ĵ�һ����ĸСд,��className=UserInfo,��ClassNameFirstLower=userInfo
     *
     * @return
     */
    public String getClassNameFirstLower() {
        return StringHelper.uncapitalize(getClassName());
    }

    /**
     * ����getClassName()�������,���ڵõ�������,��className=UserInfo,��constantName=USER_INFO
     *
     * @return
     */
    public String getConstantName() {
        return StringHelper.toUnderscoreName(getClassName()).toUpperCase();
    }

    /**
     * ʹ�� getPkCount() �滻
     */
    @Deprecated
    public boolean isSingleId() {
        return getPkCount() == 1 ? true : false;
    }

    /**
     * ʹ�� getPkCount() �滻
     */
    @Deprecated
    public boolean isCompositeId() {
        return getPkCount() > 1 ? true : false;
    }

    /**
     * ʹ�� getPkCount() �滻
     */
    @Deprecated
    public boolean isNotCompositeId() {
        return !isCompositeId();
    }

    /**
     * �õ���������
     *
     * @return
     */
    public int getPkCount() {
        int pkCount = 0;
        for (Column c : columns) {
            if (c.isPk()) {
                pkCount++;
            }
        }
        return pkCount;
    }

    /**
     * use getPkColumns()
     *
     * @deprecated
     */
    public List<Column> getCompositeIdColumns() {
        return getPkColumns();
    }

    /**
     * �õ���������ȫ��column
     *
     * @return
     */
    public List<Column> getPkColumns() {
        List<Column> results = new ArrayList<Column>();
        for (Column c : getColumns()) {
            if (c.isPk())
                results.add(c);
        }
        return results;
    }

    /**
     * �õ�����������ȫ��column
     *
     * @return
     */
    public List<Column> getNotPkColumns() {
        List<Column> results = new ArrayList<Column>();
        for (Column c : getColumns()) {
            if (!c.isPk())
                results.add(c);
        }
        return results;
    }

    /**
     * �õ����������ȼ���getPkColumns().get(0)
     */
    public Column getPkColumn() {
        if (getPkColumns().isEmpty()) {
            throw new IllegalStateException("not found primary key on table:" + getSqlName());
        }
        return getPkColumns().get(0);
    }

    /**
     * ʹ�� getPkColumn()�滻
     */
    @Deprecated
    public Column getIdColumn() {
        return getPkColumn();
    }

    public List<Column> getEnumColumns() {
        List<Column> results = new ArrayList<Column>();
        for (Column c : getColumns()) {
            if (!c.isEnumColumn())
                results.add(c);
        }
        return results;
    }

    public Column getColumnByName(String name) {
        Column c = getColumnBySqlName(name);
        if (c == null) {
            c = getColumnBySqlName(StringHelper.toUnderscoreName(name));
        }
        return c;
    }

    public Map<String, List> getUniqueColumns() {
        return uniqueColumns;
    }

    public Map<String, IdName> getUniqueMaps() {
        Map<String, IdName> map = new HashMap<String, IdName>();
        if (!getUniqueColumns().isEmpty()) {
            for (String key : getUniqueColumns().keySet()) {
                List<String> idList = new ArrayList<String>();
                List<String> nameList = new ArrayList<String>();
                List list = getUniqueColumns().get(key);
                boolean flag = false;
                for (Object o : list) {
                    String id = (String) o;
                    for (Column column : getColumns()) {
                        if (id.equals(column.getSqlName()) && !column.isPk()) {
                            idList.add(column.getColumnNameFirstLower());
                            nameList.add(column.getRemarks());
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    String ids = StringUtils.join(idList, ",");
                    String names = StringUtils.join(nameList, ",");
                    IdName idName = new IdName(ids, names);
                    map.put(key, idName);
                }
            }
        }
        return map;
    }

    public void setUniqueColumns(Map<String, List> uniqueColumns) {
        this.uniqueColumns = uniqueColumns;
    }

    public Column getColumnBySqlName(String sqlName) {
        for (Column c : getColumns()) {
            if (c.getSqlName().equalsIgnoreCase(sqlName)) {
                return c;
            }
        }
        return null;
    }

    public Column getRequiredColumnBySqlName(String sqlName) {
        if (getColumnBySqlName(sqlName) == null) {
            throw new IllegalArgumentException("not found column with sqlName:" + sqlName + " on table:" + getSqlName());
        }
        return getColumnBySqlName(sqlName);
    }

    /**
     * ���Թ��˵�ĳЩ�ؼ��ֵ���,�ؼ��ֲ����ִ�Сд,�Զ��ŷָ�
     *
     * @param ignoreKeywords
     * @return
     */
    public List<Column> getIgnoreKeywordsColumns(String ignoreKeywords) {
        List<Column> results = new ArrayList<Column>();
        for (Column c : getColumns()) {
            String sqlname = c.getSqlName().toLowerCase();
            if (StringHelper.contains(sqlname, ignoreKeywords.split(","))) {
                continue;
            }
            results.add(c);
        }
        return results;
    }

    /**
     * This method was created in VisualAge.
     */
    public void initImportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {

        // get imported keys a

        ResultSet fkeys = dbmd.getImportedKeys(catalog, schema, this.sqlName);

        while (fkeys.next()) {
            String pktable = fkeys.getString(PKTABLE_NAME);
            String pkcol = fkeys.getString(PKCOLUMN_NAME);
            String fktable = fkeys.getString(FKTABLE_NAME);
            String fkcol = fkeys.getString(FKCOLUMN_NAME);
            String seq = fkeys.getString(KEY_SEQ);
            Integer iseq = new Integer(seq);
            getImportedKeys().addForeignKey(pktable, pkcol, fkcol, iseq);
        }
        fkeys.close();
    }

    /**
     * This method was created in VisualAge.
     */
    public void initExportedKeys(DatabaseMetaData dbmd) throws java.sql.SQLException {
        // get Exported keys

        ResultSet fkeys = dbmd.getExportedKeys(catalog, schema, this.sqlName);

        while (fkeys.next()) {
            String pktable = fkeys.getString(PKTABLE_NAME);
            String pkcol = fkeys.getString(PKCOLUMN_NAME);
            String fktable = fkeys.getString(FKTABLE_NAME);
            String fkcol = fkeys.getString(FKCOLUMN_NAME);
            String seq = fkeys.getString(KEY_SEQ);
            Integer iseq = new Integer(seq);
            getExportedKeys().addForeignKey(fktable, fkcol, pkcol, iseq);
        }
        fkeys.close();
    }

    /**
     * @return Returns the exportedKeys.
     */
    public ForeignKeys getExportedKeys() {
        if (exportedKeys == null) {
            exportedKeys = new ForeignKeys(this);
        }
        return exportedKeys;
    }

    /**
     * @return Returns the importedKeys.
     */
    public ForeignKeys getImportedKeys() {
        if (importedKeys == null) {
            importedKeys = new ForeignKeys(this);
        }
        return importedKeys;
    }

    public List<ColumnWeb> getGridColumns() {
        List<ColumnWeb> results = new ArrayList<ColumnWeb>();
        for (Column column : getColumns()) {
            ColumnWeb c = (ColumnWeb) column;
            if (c.isGridField())
                results.add(c);
        }
        return results;

    }

    public List<ColumnWeb> getSearchColumns() {
        List<ColumnWeb> results = new ArrayList<ColumnWeb>();
        for (Column column : getColumns()) {
            ColumnWeb c = (ColumnWeb) column;
            if (c.isSearchField())
                results.add(c);
        }
        return results;
    }

    public List<ColumnWeb> getFormColumns() {
        List<ColumnWeb> results = new ArrayList<ColumnWeb>();
        for (Column column : getColumns()) {
            ColumnWeb c = (ColumnWeb) column;
            if (c.isFormField())
                results.add(c);
        }
        return results;
    }

    public String toString() {
        return "Database Table:" + getSqlName() + " to ClassName:" + getClassName();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            //ignore
            return null;
        }
    }

    String catalog = TableFactory.getInstance().getCatalog();
    String schema = TableFactory.getInstance().getSchema();

    private String tableAlias;
    private ForeignKeys exportedKeys;
    private ForeignKeys importedKeys;

    public static final String PKTABLE_NAME = "PKTABLE_NAME";
    public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
    public static final String FKTABLE_NAME = "FKTABLE_NAME";
    public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
    public static final String KEY_SEQ = "KEY_SEQ";
}
