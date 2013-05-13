<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.action;

import com.common.base.BaseAction;
import com.common.util.CountOrder;
import com.common.util.CountOrderUtil;
import com.common.util.QueryUitl;
import ${basepackage}.po.${className};
<#if table.pkCount gt 1>
import ${basepackage}.po.${className}Id;
</#if>
import ${basepackage}.service.${className}Service;
import ${basepackage}.vo.${className}Query;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.json.JsonHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.eredlab.g4.ccl.util.G4Utils.isEmpty;


public class  ${className}Action extends BaseAction implements Preparable,ModelDriven{

     ${className}Service  ${classNameLower}Service = (${className}Service) SpringBeanLoader.getSpringBean("${classNameLower}Service");

    private JSON resultJson;

    private ${className} ${classNameLower};

    public JSON getResultJson() {
        return resultJson;
    }

    public void setResultJson(JSON resultJson) {
        this.resultJson = resultJson;
    }

    public void prepare() throws Exception {
         ${classNameLower} = new ${className}();
    }

    public Object getModel() {
        return ${classNameLower};
    }

    public String list() {
       <#if table.paging>
        CountOrder countOrder = CountOrderUtil.getCountOrder();

        ${className}Query ${classNameLower}Query = QueryUitl.newQuery(${className}Query.class);
        List<${className}> list = ${classNameLower}Service.search${className}(${classNameLower}Query, countOrder);
        Long pageCount = ${classNameLower}Service.count${className}(${classNameLower}Query);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,pageCount.intValue(),null);
       <#else>
        List<${className}> list = ${classNameLower}Service.search${className}(${classNameLower}Query, null);

        JSONObject jsonObject = JsonHelper.encodeList2JSONArray(list,null,null);
       </#if>
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String find() {
        HttpServletRequest request = ServletActionContext.getRequest();
        <#if table.pkCount gt 1>
        <#list table.pkColumns as column>
          ${column.simpleJavaType} ${column.columnNameLower} = new ${column.simpleJavaType}(request.getParameter("${column.columnNameLower}"));
        </#list>
        ${className}Id id = new ${className}Id(<#list table.pkColumns as column>${column.columnNameLower}<#if column_has_next>,</#if></#list>);
        <#else>
        ${table.pkColumn.simpleJavaType} id =  new ${table.pkColumn.simpleJavaType}(request.getParameter("${table.pkColumn.columnNameLower}"));
        </#if>
        ${className} ${classNameLower} = ${classNameLower}Service.getById(id);

        JSONObject jsonObject = JsonHelper.encodeObject2JSONObject(${classNameLower} ,null);
        setResultJson(jsonObject);
        return SUCCESS;
    }

    public String save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        JSONObject returnObject = new JSONObject();
        try
        {
           <#if table.pkCount gt 1>
            <#list table.pkColumns as column>
              ${column.simpleJavaType} ${column.columnNameLower} = new ${column.simpleJavaType}(request.getParameter("${column.columnNameLower}"));
            </#list>
//            if(<#list table.pkColumns as column>StringUtils.isNotBlank(${column.columnNameLower})<#if column_has_next>&&</#if></#list>){
              ${className}Id id = new ${className}Id(<#list table.pkColumns as column>${column.columnNameLower}<#if column_has_next>,</#if></#list>);
              ${classNameLower}.setId(id);
//            }
           <#else>
           if(isEmpty(${classNameLower}.get${table.pkColumn.columnName}()))
            ${classNameLower}.set${table.pkColumn.columnName}(null);
           </#if>
            <#list table.uniqueMaps?keys as uniquekey>
                 <#assign unique=table.uniqueMaps[uniquekey] >
            String unique${uniquekey_index} = ${classNameLower}Service.isUnique(${classNameLower}, "${unique.id}", "${unique.name}");
            if (unique${uniquekey_index} != null) {
                returnObject.put("SUCCESS", false);
                returnObject.put("msg", unique${uniquekey_index});
                setResultJson(returnObject);
                return SUCCESS;
            }
            </#list>
            boolean isEdit = Boolean.parseBoolean(request.getParameter("dataEditflag"));
            if (isEdit)
                ${classNameLower}Service.update(${classNameLower});
            else
                ${classNameLower}Service.save(${classNameLower});
            returnObject.element(SUCCESS, true);
            returnObject.element("msg", "操作成功");

        }
        catch (Exception e)
        {
            returnObject.put("failure", true);
            returnObject.put("msg", e.getMessage());
            e.printStackTrace();
        }

        setResultJson(returnObject);
        return SUCCESS;
    }

    public String delete() {
        HttpServletRequest request = ServletActionContext.getRequest();

        String ids = request.getParameter("ids");
        String[] idarray = ids.split(",");

        JSONObject returnObject = new JSONObject();
        try
        {
            <#if table.pkCount gt 1>
            for (int i = 0; i < idarray.length; i=i+${table.pkCount})
            {
                ${className}Id id = new ${className}Id(<#list table.pkColumns as column>new ${column.simpleJavaType}(idarray[i+${column_index}])<#if column_has_next>,</#if></#list>);
                ${classNameLower}Service.removeById(id);
            }
            <#else>
            for (int i = 0; i < idarray.length; i++)
            {
                ${table.pkColumn.simpleJavaType} id = new ${table.pkColumn.simpleJavaType}(idarray[i]);
                ${classNameLower}Service.removeById(id);
            }
            </#if>
            returnObject.put("success", true);
            returnObject.put("msg", "删除成功");
        }
        catch (Exception e)
        {
            returnObject.put("failure", true);
            returnObject.put("msg", e.getMessage());
            e.printStackTrace();
        }
        setResultJson(returnObject);
        return SUCCESS;
    }
}
