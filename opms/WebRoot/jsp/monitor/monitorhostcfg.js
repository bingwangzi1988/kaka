Ext.onReady(function() {

    var grid = new Ext.ux.FunctionGrid({
        title:"������������б�",
        searchColumn:3,
        dataId:"id",
        autoSearch:true,
        columns:[
	        {header:'������',sortable:true,dataIndex:'hostName'
             },
	        {header:'����IP',sortable:true,dataIndex:'hostIp'
             },
	        {header:'�����˿�',sortable:true,dataIndex:'hostPort'
             }
        ],
        stripeRows:true,
        columnLines:true,
        listAction:"jsp/conmg/OpmsHostCfg_list.action?cfgType=0",
        addTitle:"������������",
        editTitle:"�༭��������",
        viewTitle:"�鿴��������",
        winWidth: 650,
        winHeight:600,
        initEditAction:"jsp/conmg/OpmsHostCfg_find",
        saveAction:"jsp/conmg/OpmsHostCfg_save",
        deleteAction:"jsp/conmg/OpmsHostCfg_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":"������","name":"hostName","xtype":"textfield"},
                {"anchor":"95%","fieldLabel":"����IP","name":"hostIp","xtype":"textfield"},
                {"allowDecimals":true,"anchor":"95%","fieldLabel":"�����˿�","name":"hostPort","xtype":"numberfield"}
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
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"������","maxLength":100,"name":"hostName","xtype":"textfield"},
                               {"allowBlank":false,"allowDecimals":true,"anchor":"95%","fieldLabel":"�����˿�","name":"hostPort","xtype":"textfield"}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"����IP","maxLength":15,"name":"hostIp","xtype":"textfield"},
                               {"allowBlank":false,"anchor":"95%","hidden":"true","fieldLabel":"���ͣ�0���������ع���1��������","maxLength":1,"name":"cfgType","xtype":"textfield", "value":"0"}
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