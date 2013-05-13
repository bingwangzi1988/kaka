package org.rapid_framework.generator.provider.db.table.model;



import java.util.List;

import org.rapid_framework.generator.GeneratorProperties;
import org.rapid_framework.generator.provider.db.table.model.ForeignKey.ReferenceKey;
import org.rapid_framework.generator.provider.db.table.model.util.ColumnHelper;
import org.rapid_framework.generator.util.GLogger;
import org.rapid_framework.generator.util.StringHelper;
import org.rapid_framework.generator.util.TestDataGenerator;
import org.rapid_framework.generator.util.typemapping.ActionScriptDataTypesUtils;
import org.rapid_framework.generator.util.typemapping.DatabaseDataTypesUtils;
import org.rapid_framework.generator.util.typemapping.JavaPrimitiveTypeMapping;
import org.rapid_framework.generator.util.typemapping.JdbcType;

/**
 * �������ɴ����Column����.��Ӧ���ݿ��column
 * @author badqiu
 * @email badqiu(a)gmail.com
 */
public class Column implements java.io.Serializable,Cloneable{
	/**
	 * Reference to the containing table
	 */
	private Table _table;

	/**
	 * The java.sql.Types type
	 */
	private int _sqlType;

	/**
	 * The sql typename. provided by JDBC driver
	 */
	private String _sqlTypeName;

	/**
	 * The name of the column
	 */
	private String _sqlName;

	/**
	 * True if the column is a primary key
	 */
	private boolean _isPk;

	/**
	 * True if the column is a foreign key
	 */
	private boolean _isFk;

	/**
	 * @todo-javadoc Describe the column
	 */
	private int _size;

	/**
	 * @todo-javadoc Describe the column
	 */
	private String _decimalDigits;

	/**
	 * True if the column is nullable
	 */
	private boolean _isNullable;

	/**
	 * True if the column is indexed
	 */
	private boolean _isIndexed;

	/**
	 * True if the column is unique
	 */
	private boolean _isUnique;

	/**
	 * Null if the DB reports no default value
	 */
	private String _defaultValue;
	
	/**
	 * The comments of column
	 */
	private String _remarks;
			
	/**
	 * @param table
	 * @param sqlType
	 * @param sqlTypeName
	 * @param sqlName
	 * @param size
	 * @param decimalDigits
	 * @param isPk
	 * @param isNullable
	 * @param isIndexed
	 * @param isUnique
	 * @param defaultValue
	 * @param remarks
	 */
	public Column(Table table, int sqlType, String sqlTypeName,
			String sqlName, int size, String decimalDigits, boolean isPk,
			boolean isNullable, boolean isIndexed, boolean isUnique,
			String defaultValue,String remarks) {
		if(sqlName == null) throw new NullPointerException();
		_table = table;
		_sqlType = sqlType;
		_sqlName = sqlName;
		_sqlTypeName = sqlTypeName;
		_size = size;
		_decimalDigits = decimalDigits;
		_isPk = isPk;
		_isNullable = isNullable;
		_isIndexed = isIndexed;
		_isUnique = isUnique;
		_defaultValue = defaultValue;
		_remarks = remarks;
		
		GLogger.trace(sqlName + " isPk -> " + _isPk);
		
		initOtherProperties();
	}

	public Column(Column c) {
        this(c.getTable(),
           c.getSqlType(),
           c.getSqlTypeName(),
           c.getSqlName(),
           c.getSize(),
           c.getDecimalDigits(),
           c.isPk(),
           c.isNullable(),
           c.isIndexed(),
           c.isUnique(),
           c.getDefaultValue(),
           c.getRemarks());
	}
	
	public Column() {
	}
	
	/**
	 * Gets the SqlType attribute of the Column object
	 * 
	 * @return The SqlType value
	 */
	public int getSqlType() {
		return _sqlType;
	}

	/**
	 * Gets the Table attribute of the DbColumn object
	 * 
	 * @return The Table value
	 */
	public Table getTable() {
		return _table;
	}

	/**
	 * Gets the Size attribute of the DbColumn object
	 * 
	 * @return The Size value
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * Gets the DecimalDigits attribute of the DbColumn object
	 * 
	 * @return The DecimalDigits value
	 */
	public String getDecimalDigits() {
		return _decimalDigits;
	}

	/**
	 * Gets the SqlTypeName attribute of the Column object
	 * 
	 * @return The SqlTypeName value
	 */
	public String getSqlTypeName() {
		return _sqlTypeName;
	}

	/**
	 * Gets the SqlName attribute of the Column object
	 * 
	 * @return The SqlName value
	 */
	public String getSqlName() {
		if(_sqlName == null) throw new NullPointerException();
		return _sqlName;
	}

	
	/**
	 * Gets the Pk attribute of the Column object
	 * 
	 * @return The Pk value
	 */
	public boolean isPk() {
		return _isPk;
	}

	/**
	 * Gets the Fk attribute of the Column object
	 * 
	 * @return The Fk value
	 */
	public boolean isFk() {
		return _isFk;
	}

	/**
	 * Gets the Nullable attribute of the Column object
	 * 
	 * @return The Nullable value
	 */
	public  boolean isNullable() {
		return _isNullable;
	}

	/**
	 * Gets the Indexed attribute of the DbColumn object
	 * 
	 * @return The Indexed value
	 */
	public  boolean isIndexed() {
		return _isIndexed;
	}

	/**
	 * Gets the Unique attribute of the DbColumn object
	 * 
	 * @return The Unique value
	 */
	public boolean isUnique() {
		return _isUnique;
	}

	/**
	 * Gets the DefaultValue attribute of the DbColumn object
	 * 
	 * @return The DefaultValue value
	 */
	public  String getDefaultValue() {
		return _defaultValue;
	}

    /**
     * �е����ݿⱸע
     * @return
     */
	public  String getRemarks() {
		return _remarks;
	}

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }
    
    public void setNullable(boolean v) {
        this._isNullable = v;
    }
    
    public void setUnique(boolean unique) {
        _isUnique = unique;
    }

    public void setPk(boolean v) {
        this._isPk = v;
    }	
	/**
	 * Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	public int hashCode() {
		if(getTable() != null) {
			return (getTable().getSqlName() + "#" + getSqlName()).hashCode();
		}else {
			return (getSqlName()).hashCode();
		}
	}

	/**
	 * Describe what the method does
	 * 
	 * @param o
	 *            Describe what the parameter does
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for method parameter
	 * @todo-javadoc Write javadocs for return value
	 */
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o instanceof Column) {
			Column other = (Column)o;
			if(getSqlName().equals(other.getSqlName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	public String toString() {
		return getSqlName();
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			//ignore
			return null;
		}
	}
	
	/**
	 * Describe what the method does
	 * 
	 * @return Describe the return value
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 */
	protected  String prefsPrefix() {
		return "tables/" + getTable().getSqlName() + "/columns/" + getSqlName();
	}

	/**
	 * Sets the Pk attribute of the DbColumn object
	 * 
	 * @param flag
	 *            The new Pk value
	 */
	void setFk(boolean flag) {
		_isFk = flag;
	}
	
	public String getUnderscoreName() {
		return getSqlName().toLowerCase();
	}

    /** 
     * ��������������sqlName����ó���ʾ��ֵ�� BirthDate
     **/
	public String getColumnName() {
		return columnName;
	}

    /** 
     * ��һ����ĸСд��columName,�ȼ���: StringHelper.uncapitalize(getColumnName()),ʾ��ֵ: birthDate
     **/
	public String getColumnNameFirstLower() {
		return StringHelper.uncapitalize(getColumnName());
	}

    /** 
     * ȫ��Сд��columName,�ȼ���: getColumnName().toLowerCase(),ʾ��ֵ: birthdate
     **/
	public String getColumnNameLowerCase() {
		return getColumnName().toLowerCase();
	}

    /**
     * ʹ�� getColumnNameFirstLower()�滻
     * @deprecated use getColumnNameFirstLower() instead
     */
	public String getColumnNameLower() {
		return getColumnNameFirstLower();
	}

    /**
     * �õ� jdbcSqlType�������ƣ�ʾ��ֵ:VARCHAR,DECIMAL, ��Ibatis3ʹ�ø�����
     */
	public String getJdbcSqlTypeName() {
		return getJdbcType();
	}
	
	/**
     * �õ� jdbcSqlType�������ƣ�ʾ��ֵ:VARCHAR,DECIMAL, ��Ibatis3ʹ�ø�����
     */
    public String getJdbcType() {
        String result = JdbcType.getJdbcSqlTypeName(getSqlType());
        return result;
    }
    /**
     * �еı������ȼ��ڣ�getRemarks().isEmpty() ? getColumnNameFirstLower() : getRemarks()
     * 
     * <br />
     * ʾ��ֵ: birthDate
     */
	public String getColumnAlias() {
		return columnAlias;
	}

    /**
     * �еĳ�������
     * 
     * <br />
     * ʾ��ֵ: BIRTH_DATE
     */
	public String getConstantName() {
		return StringHelper.toUnderscoreName(getColumnName()).toUpperCase();
	}
	
	/**
	 * 
	 * @deprecated
	 */
	public boolean getIsNotIdOrVersionField() {
		return !isPk();
	}
	
	/**�õ� rapid-validation����֤���ʽ: required min-value-800  */
	public String getValidateString() {
		return isNullable() ? getNoRequiredValidateString() :  "required " + getNoRequiredValidateString();
	}
	
	/**�õ� rapid-validation����֤���ʽ: min-value-800  */
	public String getNoRequiredValidateString() {
		return ColumnHelper.getRapidValidation(this);
	}

	/** �õ�JSR303 bean validation(Hibernate Validator)����֤���ʽ: @NotNull @Min(100) @Max(800) */
	public String[] getHibernateValidatorConstraintNames() {
		return ColumnHelper.removeHibernateValidatorSpecialTags(getHibernateValidatorExprssion());
	}
	
	/** �õ�JSR303 bean validation(Hibernate Validator)����֤���ʽ: @NotNull @Min(100) @Max(800) */
	public String getHibernateValidatorExprssion() {
		return hibernateValidatorExprssion;
	}

	public void setHibernateValidatorExprssion(String v) {
		hibernateValidatorExprssion = v;
	}
	
	/** ���Ƿ���String���� */
	public boolean getIsStringColumn() {
		return DatabaseDataTypesUtils.isString(getJavaType());
	}
	
	/** ���Ƿ����������� */
	public boolean getIsDateTimeColumn() {
		return DatabaseDataTypesUtils.isDate(getJavaType());
	}
	
	/** ���Ƿ���Number���� */
	public boolean getIsNumberColumn() {
		return DatabaseDataTypesUtils.isFloatNumber(getJavaType()) 
			|| DatabaseDataTypesUtils.isIntegerNumber(getJavaType());
	}
	
	public boolean getIsFloatColumn() {
		return DatabaseDataTypesUtils.isFloatNumber(getJavaType());

	}

	public boolean getIsIntegerColumn() {
		return  DatabaseDataTypesUtils.isIntegerNumber(getJavaType());
	}

	/** ����Ƿ����ĳЩ�ؼ���,�ؼ����Զ��ŷָ� */
	public boolean contains(String keywords) {
		if(keywords == null) throw new IllegalArgumentException("'keywords' must be not null");
		return StringHelper.contains(getSqlName(), keywords.split(","));
	}
	
	public boolean isHtmlHidden() {
		return isPk() && _table.isSingleId();
	}

    /**
     * �õ���Ӧ��javaType,��java.lang.String,
     * @return
     */
	public String getJavaType() {
		return javaType;
	}

    /**
     * �õ���̵�javaType�����ƣ���com.company.model.UserInfo,������ UserInfo
     * @return
     */
	public String getSimpleJavaType() {
		return StringHelper.getJavaClassSimpleName(getJavaType());
	}
	/**
     * �õ������ܼ�̵�javaType�����ƣ������java.lang.String,������String, ��com.company.model.UserInfo,������ com.company.model.UserInfo
     * @return
     */
	public String getPossibleShortJavaType() {
	    if(getJavaType().startsWith("java.lang.")) {
	        return getSimpleJavaType();
	    }else {
	        return getJavaType();
	    }
    }

	public boolean isPrimitive() {
	    return JavaPrimitiveTypeMapping.getWrapperTypeOrNull(getJavaType()) != null;
	}
    /**
     * �õ�ԭ�����͵�javaType,��java.lang.Integer������int,����ԭ������,��ֱ�ӷ���getSimpleJavaType()
     * @return
     */	
	public String getPrimitiveJavaType() {
		return JavaPrimitiveTypeMapping.getPrimitiveType(getSimpleJavaType());
	}
	
	/** �õ�ActionScript��ӳ������,����Flex���������  */
	public String getAsType() {
		return asType;
	}
	
	/** �õ��еĲ�������  */
	public String getTestData() {
		return new TestDataGenerator().getDBUnitTestData(getColumnName(),getJavaType(),getSize());
	}
	
	/** ���Ƿ���Ը���  */
	public boolean isUpdatable() {
		return updatable;
	}

	/** ���Ƿ���Բ���  */
	public boolean isInsertable() {
		return insertable;
	}
	
	/** �õ�ö��(enum)�������ƣ�ʾ��ֵ��SexEnum  */
	public String getEnumClassName() {
		return enumClassName;
	}
	
	/** ö��ֵ,�Էֺŷָ�,ʾ��ֵ:M(1,��);F(0,Ů) ������:M(��);F(Ů)  */
	public void setEnumString(String str) {
		this.enumString = str;
	}
	/** ö��ֵ,�Էֺŷָ�,ʾ��ֵ:M(1,��);F(0,Ů) ������:M(��);F(Ů)  */
	public String getEnumString() {
		return enumString;
	}
	/** ����getEnumString()�ַ���ת��ΪList<EnumMetaDada>����  */
	public List<EnumMetaDada> getEnumList() {
		return StringHelper.string2EnumMetaData(getEnumString());
	}
	/** �Ƿ���ö���У��ȼ���:return getEnumList() != null && !getEnumList().isEmpty()  */
	public boolean isEnumColumn() {
		return getEnumList() != null && !getEnumList().isEmpty();
	}
	
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setAsType(String asType) {
		this.asType = asType;
	}

	public void setEnumClassName(String enumClassName) {
		this.enumClassName = enumClassName;
	}
	
	

//	public void setBelongsTo(String foreignKey) {
//		ReferenceKey ref = ReferenceKey.fromString(foreignKey);
//		if(ref != null && _table != null) {
//			_table.getImportedKeys().addForeignKey(ref.tableName, ref.columnSqlName, getSqlName(), ref.columnSqlName.hashCode());
//		}
//	}
//	
//	public void setHasAndBelongsToMany(String foreignKey) {
//	}

	private ReferenceKey hasOne;
	public String getHasOne() {
		return ReferenceKey.toString(hasOne);
	}
	
	/** nullValue for ibatis sqlmap: <result property="age" column="age" nullValue="0"  /> */
	public String getNullValue() {
		return JavaPrimitiveTypeMapping.getDefaultValue(getJavaType());
	}

	public boolean isHasNullValue() {
		return JavaPrimitiveTypeMapping.getWrapperTypeOrNull(getJavaType()) != null;
	}
	
    /**
     * ����many-to-one,foreignKey��ʽ: fk_table_name(fk_column) ���� schema_name.fk_table_name(fk_column)
     * @param foreignKey
     * @return
     */
	public void setHasOne(String foreignKey) {
		hasOne = ReferenceKey.fromString(foreignKey);
		if(hasOne != null && _table != null) {
//			Table refTable = TableFactory.getInstance().getTable(hasOne.tableName);
//			_table.getImportedKeys().addForeignKey(refTable.getSqlName(), hasOne.columnSqlName, getSqlName(), hasOne.columnSqlName.toLowerCase().hashCode());
			_table.getImportedKeys().addForeignKey(hasOne.tableName, hasOne.columnSqlName, getSqlName(), hasOne.columnSqlName.toLowerCase().hashCode());
		}
	}
	
	private ReferenceKey hasMany = null;
	public String getHasMany() {
		return ReferenceKey.toString(hasMany);
	}

    /**
     * ����one-to-many,foreignKey��ʽ: fk_table_name(fk_column) ���� schema_name.fk_table_name(fk_column)
     * @param foreignKey
     * @return
     */
	public void setHasMany(String foreignKey) {
		hasMany = ReferenceKey.fromString(foreignKey);
		if(hasMany != null && _table != null) {
//			Table refTable = TableFactory.getInstance().getTable(hasMany.tableName);
//			_table.getExportedKeys().addForeignKey(refTable.getSqlName(), hasMany.columnSqlName, getSqlName(), hasMany.columnSqlName.toLowerCase().hashCode());
			_table.getExportedKeys().addForeignKey(hasMany.tableName, hasMany.columnSqlName, getSqlName(), hasMany.columnSqlName.toLowerCase().hashCode());
		}
	}

	private void initOtherProperties() {
		String normalJdbcJavaType = DatabaseDataTypesUtils.getPreferredJavaType(getSqlType(), getSize(), getDecimalDigits());
		javaType = GeneratorProperties.getProperty("java_typemapping."+normalJdbcJavaType,normalJdbcJavaType).trim();
		columnName = StringHelper.makeAllWordFirstLetterUpperCase(StringHelper.toUnderscoreName(getSqlName()));
		enumClassName = getColumnName()+"Enum";		
		asType = ActionScriptDataTypesUtils.getPreferredAsType(getJavaType());	
		columnAlias = StringHelper.removeCrlf(StringHelper.defaultIfEmpty(getRemarks(), getColumnNameFirstLower()));
		setHibernateValidatorExprssion(ColumnHelper.getHibernateValidatorExpression(this));
	}
	
	/** ɾ���ۼ����������char,ʾ��ת�� count(*) => count, max(age) => max_age, sum(income) => sum_income */
    public static String removeAggregationColumnChars(String columSqlName) {
        return columSqlName.replace('(', '_').replace(")", "").replace("*", "");
    }
	
	private String enumString = "";
	private String javaType;
	private String columnAlias;
	private String columnName;
	private String asType;	
	private String enumClassName;
	private boolean updatable = true;	
	private boolean insertable = true;
	private String hibernateValidatorExprssion;
//	private String rapidValidation;
	/**
	 * public enum ${enumClassName} {
	 * 		${enumAlias}(${enumKey},${enumDesc});
	 * 		private String key;
	 * 		private String value;
	 * }
	 * @author badqiu
	 */
	public static class EnumMetaDada {
		private String enumAlias;
		private String enumKey;
		private String enumDesc;
		public EnumMetaDada(String enumAlias, String enumKey, String enumDesc) {
			super();
			this.enumAlias = enumAlias;
			this.enumKey = enumKey;
			this.enumDesc = enumDesc;
		}
		
		public String getEnumAlias() {
			return enumAlias;
		}
		public String getEnumKey() {
			return enumKey;
		}
		public String getEnumDesc() {
			return enumDesc;
		}
	}



}
