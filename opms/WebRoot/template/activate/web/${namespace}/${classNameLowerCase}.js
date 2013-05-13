<#assign className = table.className>
<#assign classNameLower = className?lower_case>
Ext.onReady(function() {
     <#list table.columns as column>
       <#if column.needStore>
        <#if column.eacode>
        var ${column.columnNameLower}Store = new Ext.data.Store({
           baseParams:{code:'${column.selectCode.fieldvalue}'},
           proxy:new Ext.data.HttpProxy({url:_contextPath_ + '/codeSelect.action'}),
           reader: new Ext.data.JsonReader({}, [
             {name: "code"},
             {name: "codedesc"}
           ]),
           remoteSort:false
           });
         ${column.columnNameLower}Store.load();
       <#else>
       var ${column.columnNameLower}Store = new Ext.data.Store({
          baseParams:{tableName:'${column.selectCode.tableName}',field:'${column.selectCode.field}',code:'${column.selectCode.code}',codedesc:'${column.selectCode.codedesc}',fieldvalue:'${column.selectCode.fieldvalue}'},
          proxy:new Ext.data.HttpProxy({url:_contextPath_ + '/common/codeSelect.action'}),
          reader: new Ext.data.JsonReader({}, [
            {name: "code"},
            {name: "codedesc"}
          ]),
          remoteSort:false
          });
        ${column.columnNameLower}Store.load();
       </#if>
       </#if>
     </#list>

    <#list table.gridPlugins as column>
        <#switch column>
            <#case "RowExpander">
            var expander = new Ext.ux.grid.RowExpander({
                tpl : new Ext.Template(
                    '<p><b>Company:</b> {company}</p><br>',
                    '<p><b>Summary:</b> {desc}</p>'
                )
            });
            <#break>
            <#case "Checkbox">
             var sm = new Ext.grid.CheckboxSelectionModel();
            <#break>
            <#case "RowNumberer">
            var rowNumberer = new Ext.grid.RowNumberer();
            <#break>
        </#switch>
    </#list>

    var grid = new Ext.ux.FunctionGrid({
        title:"${table.tableAlias}列表",
        searchColumn:3,
        <#if table.pkCount gt 1>
        dataId:[<#list table.pkColumns as column>"${column.columnNameLower}"<#if column_has_next>,</#if></#list>],
        <#else>
        dataId:"${table.pkColumn.columnNameLower}",
        </#if>
        columns:[
        <#list table.gridPlugins as column>
            <#switch column>
                <#case "RowNumberer">
                rowNumberer,
                <#break>
                <#case "RowExpander">
                 expander,
                <#break>
                <#case "Checkbox">
                 sm,
                <#break>
            </#switch>
        </#list>
         <#list table.gridColumns as column>
	        {header:'${column.columnAlias}',sortable:true,dataIndex:'${column.columnNameLower}'
            <#if column.needStore>
            ,renderer : function(value) {
                var returnValue;
                ${column.columnNameLower}Store.each(function(record) {
                    if (record.get('code') != value) {
                    } else {
                        returnValue = record.get('codedesc');
                        return false;
                    }
                });
                return returnValue;
            }
            </#if>
             }<#if column_has_next>,</#if>
	    </#list>
        ],
        <#if table.columnLock>
        lock:true,
        </#if>
        <#if table.stripeRows>
        stripeRows:true,
        </#if>
        <#if table.stripeRows>
        columnLines:true,
        </#if>
        listAction:"${namespace}/${className}_list.action",
        <#if !table.paging>
        paging:false,
        </#if>
        <#if !table.canedit>
        edit:false,
        </#if>
       <#if (table.gridPlugins?size>0)>
        pluginSize:${table.gridPlugins?size},
           <#list table.gridPlugins as column>
               <#switch column>
                   <#case "RowExpander">
                    plugins:expander,
                   <#break>
                   <#case "Checkbox">
                    sm:sm,
                   <#break>
               </#switch>
           </#list>
       </#if>
        addTitle:"增加${table.tableAlias}",
        editTitle:"编辑${table.tableAlias}",
        viewTitle:"查看${table.tableAlias}",
        initEditAction:"${namespace}/${className}_find",
        saveAction:"${namespace}/${className}_save",
        deleteAction:"${namespace}/${className}_delete",
        <#if (table.searchColumns?size>0)>
        searchSet:[
            <#list table.searchColumns as column>
                ${extjs.genSearchField(column)}<#if column_has_next>,</#if>
            </#list>
            ],
        </#if>
        formSet:[
            <#list table.pkColumns as column>
               <#if !column.formField>{xtype:'hidden',fieldLabel:'${column.columnAlias}',name:'${column.columnNameLower}'},</#if>
           </#list>
                {
                layout:'column',
                items:[
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                            <#list table.formColumns as column>
                               <#if column_index%2==0>
                               ${extjs.genFormField(column)}<#if column_has_next&&(column_index!=(table.formColumns?size-2))>,</#if>
                              </#if>
                           </#list>
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                            <#list table.formColumns as column>
                               <#if column_index%2==1>
                               ${extjs.genFormField(column)}<#if column_has_next&&(column_index!=(table.formColumns?size-2))>,</#if>
                            </#if>
                           </#list>
                        ]
                    }
                ]
            }
        ]
    });

    new Ext.Viewport({
            layout:'border',
            items:[grid.panels]
        });
 });