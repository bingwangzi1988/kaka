package org.codegen;

import com.common.base.BaseAction;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.rapid_framework.generator.GeneratorFacade;
import org.rapid_framework.generator.GeneratorProperties;
import org.rapid_framework.generator.provider.db.table.TableFactory;
import org.rapid_framework.generator.provider.db.table.model.ColumnWeb;
import org.rapid_framework.generator.provider.db.table.model.ForeignLink;
import org.rapid_framework.generator.provider.db.table.model.TableWeb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-7
 * Time: 上午10:09
 * desc:
 */
public class CodeGenAction extends BaseAction {

    private JSON resultArray;

    public JSON getResultArray() {
        return resultArray;
    }

    public void setResultArray(JSON resultArray) {
        this.resultArray = resultArray;
    }

    /**
     * 数据库表展现
     *
     * @return
     */
    public String dbTree() {
        List<String> tables = TableFactory.getInstance().getAllTableNames();
        JSONArray jsonArray = new JSONArray();
        for (String table : tables) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", table);
            jsonObject.element("text", table);
            jsonObject.element("checked", false);
            jsonObject.element("leaf", true);
            jsonArray.element(jsonObject);
        }
        setResultArray(jsonArray);
        return SUCCESS;
    }

    /**
     * 初始化表的生成属性
     *
     * @return
     */
    public String initTable() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String tableName = request.getParameter("table");

        HashMap<String, TableWeb> tableMap = (HashMap<String, TableWeb>) session.getAttribute("table");
        TableWeb table = null;
        if (tableMap != null) {
            table = tableMap.get(tableName);
        } else {
            tableMap = new HashMap<String, TableWeb>();
            session.setAttribute("table", tableMap);
        }
        if (table == null) {
            table = new TableWeb();
            tableMap.put(tableName, table);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.element("paging", table.getPaging());
        jsonObject.element("columnLock", table.getColumnLock());
        jsonObject.element("edit", table.getCanedit());
        jsonObject.element("stripeRows", table.getStripeRows());
        jsonObject.element("columnLines", table.getColumnLines());
        List list = table.getGridPlugins();
        if (list != null && !list.isEmpty()) {
            String girdPlugins = StringUtils.join(list, ",");
            jsonObject.element("girdPlugins", girdPlugins);
        }
        setResultArray(jsonObject);
        return SUCCESS;
    }

    /**
     * 更改表的生成属性
     */
    public void changeTable() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String tableName = request.getParameter("table");

        HashMap<String, TableWeb> tableMap = (HashMap<String, TableWeb>) session.getAttribute("table");
        TableWeb table = tableMap.get(tableName);
        String attribute = request.getParameter("attribute");
        String value = request.getParameter("value");
        if ("paging".equals(attribute)) {
            table.setPaging(Boolean.valueOf(value));
        } else if ("columnLock".equals(attribute)) {
            table.setColumnLock(Boolean.valueOf(value));
        } else if ("edit".equals(attribute)) {
            table.setCanedit(Boolean.valueOf(value));
        } else if ("stripeRows".equals(attribute)) {
            table.setStripeRows(Boolean.valueOf(value));
        } else if ("columnLines".equals(attribute)) {
            table.setColumnLines(Boolean.valueOf(value));
        } else if ("girdPlugins".equals(attribute)) {
            List<String> list = new ArrayList<String>();
            if (StringUtils.isNotBlank(value)) {
                String[] strings = value.split(",");
                Collections.addAll(list, strings);
            }
            table.setGridPlugins(list);
        }
    }

    /**
     * 展现一张表的列
     *
     * @return
     * @throws SQLException
     */
    public String dbColumn() throws SQLException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String tableName = request.getParameter("table");

        HashMap<String, TableWeb> tableMap = (HashMap<String, TableWeb>) session.getAttribute("table");
        TableWeb table = null;
        List<ColumnWeb> columnWebList = null;
        if (tableMap != null) {
            table = tableMap.get(tableName);
        } else {
            tableMap = new HashMap<String, TableWeb>();
            session.setAttribute("table", tableMap);
        }
        if (table != null) {
            columnWebList = table.getColumns();
            if (columnWebList == null) {
                columnWebList = TableFactory.getInstance().getTableColumns(tableName);
                table.setColumns(columnWebList);
            }
        } else {
            table = new TableWeb();
            columnWebList = TableFactory.getInstance().getTableColumns(tableName);
            table.setColumns(columnWebList);
            tableMap.put(tableName, table);
        }


        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (ColumnWeb columnWeb : columnWebList) {
            JSONObject object = new JSONObject();
            object.element("name", columnWeb.getSqlName());
            object.element("remark", columnWeb.getRemarks());
            object.element("type", columnWeb.getSimpleJavaType());
            object.element("size", columnWeb.getSize());
            object.element("pk", columnWeb.isPk());
            object.element("grid", columnWeb.isGridField());
            object.element("search", columnWeb.isSearchField());
            object.element("form", columnWeb.isFormField());
            object.element("xtype", columnWeb.getxType());
            object.element("vtype", columnWeb.getvType());
            object.element("editReadOnly", columnWeb.isEditReadOnly());
            jsonArray.element(object);
        }
        jsonObject.element("root", jsonArray);
        setResultArray(jsonObject);
        return SUCCESS;
    }

    /**
     * 保存列中更改
     */
    public void makeParams() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String table = request.getParameter("table");
        String column = request.getParameter("columnName");
        String field = request.getParameter("field");
        String value = request.getParameter("value");

        HashMap<String, TableWeb> tableMap = (HashMap<String, TableWeb>) session.getAttribute("table");
        TableWeb tableWeb = tableMap.get(table);
        List<ColumnWeb> columnWebList = tableWeb.getColumns();
        for (ColumnWeb columnWeb : columnWebList) {
            if (columnWeb.getSqlName().equals(column)) {
                if ("pk".equals(field)) {
                    columnWeb.setPk(Boolean.valueOf(value));
                } else if ("grid".equals(field)) {
                    columnWeb.setGridField(Boolean.valueOf(value));
                } else if ("search".equals(field)) {
                    columnWeb.setSearchField(Boolean.valueOf(value));
                } else if ("form".equals(field)) {
                    columnWeb.setFormField(Boolean.valueOf(value));
                } else if ("xtype".equals(field)) {
                    columnWeb.setxType(value);
                } else if ("vtype".equals(field)) {
                    columnWeb.setvType(StringUtils.isBlank(value) ? null : value);
                } else if ("editReadOnly".equals(field)) {
                    columnWeb.setEditReadOnly(Boolean.valueOf(value));
                }
            }
        }
    }

    /**
     * 初始化下拉
     */
    public String loadSelectCode() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String table = request.getParameter("table");
        String column = request.getParameter("columnName");

        HashMap<String, TableWeb> tableMap = (HashMap<String, TableWeb>) session.getAttribute("table");
        TableWeb tableWeb = tableMap.get(table);
        List<ColumnWeb> columnWebList = tableWeb.getColumns();
        for (ColumnWeb columnWeb : columnWebList) {
            if (columnWeb.getSqlName().equals(column)) {
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.element("success", true);
                jsonObject2.element("data", columnWeb.getSelectCode());
                setResultArray(jsonObject2);
                break;
            }
        }
        return SUCCESS;
    }

    /**
     * 保存关联表
     *
     * @return
     */
    public String saveCode() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String table = request.getParameter("table");
        String column = request.getParameter("columnName");

        String tableSelect = request.getParameter("tableName");
        String field = request.getParameter("field");
        String code = request.getParameter("code");
        String codedesc = request.getParameter("codedesc");
        String fieldvalue = request.getParameter("fieldvalue");

        ForeignLink foreignLink = new ForeignLink(tableSelect, field, code, codedesc, fieldvalue);

        HashMap<String, TableWeb> tableMap = (HashMap<String, TableWeb>) session.getAttribute("table");
        TableWeb tableWeb = tableMap.get(table);
        List<ColumnWeb> columnWebList = tableWeb.getColumns();
        for (ColumnWeb columnWeb : columnWebList) {
            if (columnWeb.getSqlName().equals(column)) {
                columnWeb.setSelectCode(foreignLink);
                break;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.element(SUCCESS, true);
        setResultArray(jsonObject);
        return SUCCESS;
    }

    /**
     * 初始化全局配置
     *
     * @return
     */
    public String initglobalConfig() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        HashMap<String, String> hashMap = (HashMap<String, String>) session.getAttribute("globalConfig");
        JSONObject jsonObject2 = new JSONObject();
        if (hashMap != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.element("basepackage", hashMap.get("basepackage"));
            jsonObject.element("namespace", hashMap.get("namespace"));
            jsonObject.element("outRoot", hashMap.get("outRoot"));
            jsonObject2.element("data", jsonObject);
        }
        jsonObject2.element("success", true);
        setResultArray(jsonObject2);
        return SUCCESS;
    }

    /**
     * 保存全局配置
     */
    public void saveGlobal() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String basepackage = request.getParameter("basepackage");
        String namespace = request.getParameter("namespace");
        String outRoot = request.getParameter("outRoot");

        HashMap<String, String> hashMap = (HashMap<String, String>) session.getAttribute("globalConfig");
        if (hashMap == null) {
            hashMap = new HashMap<String, String>();
            session.setAttribute("globalConfig", hashMap);
        }
        hashMap.put("basepackage", basepackage);
        hashMap.put("namespace", namespace);
        hashMap.put("outRoot", outRoot);
    }

    public String generator() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        String tables = request.getParameter("tables");
        String[] tableNames = StringUtils.split(tables, ",");


        GeneratorFacade g = new GeneratorFacade();
//		g.printAllTableNames();				//打印数据库中的表名称

        g.deleteOutRootDir();                            //删除生成器的输出目录

        saveGlobal();
        HashMap<String, TableWeb> tableMap = (HashMap<String, TableWeb>) session.getAttribute("table");

        g.generateByTables(tableNames, tableMap, request.getSession().getServletContext().getRealPath("template/activate"));    //通过数据库表生成文件,template为模板的根目录
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("success", true);
        setResultArray(jsonObject);

//        g.generateByAllTable("template");	//自动搜索数据库中的所有表并生成文件,template为模板的根目录
//		g.generateByClass(Blog.class,"template_clazz");

//		g.deleteByTable("table_name", "template"); //删除生成的文件
        //打开文件夹
//        Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));

//        session.setAttribute("table", null);
        return SUCCESS;
    }

    public void openOutRoot() throws IOException {
        Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
    }

}
