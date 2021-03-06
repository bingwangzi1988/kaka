Ext.onReady(function() {


    var grid = new Ext.ux.FunctionGrid({
        title:"主机配置列表",
        searchColumn:3,
        dataId:"id",
        columns:[
	        {header:'主机名',sortable:true,dataIndex:'hostName'
             },
	        {header:'主机IP',sortable:true,dataIndex:'hostIp'
             },
	        {header:'主机端口',sortable:true,dataIndex:'hostPort'
             },
	        {header:'类型（0：组件及监控管理；1：批量）',sortable:true,dataIndex:'cfgType'
             }
        ],
        stripeRows:true,
        columnLines:true,
        listAction:"jsp/conmg/OpmsHostCfg_list.action",
        addTitle:"增加主机配置",
        editTitle:"编辑主机配置",
        viewTitle:"查看主机配置",
        initEditAction:"jsp/conmg/OpmsHostCfg_find",
        saveAction:"jsp/conmg/OpmsHostCfg_save",
        deleteAction:"jsp/conmg/OpmsHostCfg_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":"主机名","name":"hostName","xtype":"textfield"},
                {"anchor":"95%","fieldLabel":"主机IP","name":"hostIp","xtype":"textfield"},
                {"allowDecimals":true,"anchor":"95%","fieldLabel":"主机端口","name":"hostPort","xtype":"numberfield"}
            ],
        formSet:[
               {xtype:'hidden',fieldLabel:'PK',name:'id'},
                {
                layout:'column',
                items:[
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"主机名","maxLength":100,"name":"hostName","xtype":"textfield"},
                               {"allowBlank":false,"allowDecimals":true,"anchor":"95%","fieldLabel":"主机端口","name":"hostPort","xtype":"numberfield"}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"主机IP","maxLength":15,"name":"hostIp","xtype":"textfield"},
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"类型（0：组件及监控管理；1：批量）","maxLength":1,"name":"cfgType","xtype":"textfield"}
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