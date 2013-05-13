package com.opms.conmg.po;

import cn.org.rapid_framework.util.DateConvertUtils;
import com.common.base.BaseEntity;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "EAOPERATEROLE")
public class Eaoperaterole extends BaseEntity implements java.io.Serializable{
	
	//alias
	public static final String TABLE_ALIAS = "菜单权限控制表";
	public static final String ALIAS_ID = "PK";
	public static final String ALIAS_TABLENAME = "表名";
	public static final String ALIAS_INSERTFLAG = "插入数据权限标记";
	public static final String ALIAS_UPDATEFLAG = "更新数据权限标记";
	public static final String ALIAS_DELETEFLAG = "删除数据权限标记";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * PK       db_column: ID 
     */ 	
	@Length(max=32)
	private java.lang.String id;
    /**
     * 表名       db_column: TABLENAME 
     */ 	
	@NotBlank @Length(max=30)
	private java.lang.String tablename;
    /**
     * 插入数据权限标记       db_column: INSERTFLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String insertflag;
    /**
     * 更新数据权限标记       db_column: UPDATEFLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String updateflag;
    /**
     * 删除数据权限标记       db_column: DELETEFLAG 
     */ 	
	@NotBlank @Length(max=1)
	private java.lang.String deleteflag;
	//columns END


	public Eaoperaterole(){
	}

	public Eaoperaterole(
		java.lang.String id
	){
		this.id = id;
	}

	

	public void setId(java.lang.String value) {
		this.id = value;
	}
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "uuid")
	@Column(name = "ID", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getId() {
		return this.id;
	}
	
	@Column(name = "TABLENAME", unique = false, nullable = false, insertable = true, updatable = true, length = 30)
	public java.lang.String getTablename() {
		return this.tablename;
	}
	
	public void setTablename(java.lang.String value) {
		this.tablename = value;
	}
	
	@Column(name = "INSERTFLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getInsertflag() {
		return this.insertflag;
	}
	
	public void setInsertflag(java.lang.String value) {
		this.insertflag = value;
	}
	
	@Column(name = "UPDATEFLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getUpdateflag() {
		return this.updateflag;
	}
	
	public void setUpdateflag(java.lang.String value) {
		this.updateflag = value;
	}
	
	@Column(name = "DELETEFLAG", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public java.lang.String getDeleteflag() {
		return this.deleteflag;
	}
	
	public void setDeleteflag(java.lang.String value) {
		this.deleteflag = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Tablename",getTablename())
			.append("Insertflag",getInsertflag())
			.append("Updateflag",getUpdateflag())
			.append("Deleteflag",getDeleteflag())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Eaoperaterole == false) return false;
		if(this == obj) return true;
		Eaoperaterole other = (Eaoperaterole)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

