Ext.onReady(function() {
	var hostStateStore = new Ext.data.Store( {
		baseParams : {
			code : 'HOST_STATE'
		},
		proxy : new Ext.data.HttpProxy( {
			url : _contextPath_ + '/codeSelect.action'
		}),
		reader : new Ext.data.JsonReader( {}, [ {
			name : "code"
		}, {
			name : "codedesc"
		} ]),
		remoteSort : false
	});
	hostStateStore.load();
	
    var grid = new Ext.ux.FunctionGrid({
        title:"主机配置列表",
        searchColumn:3,
        dataId:"id",
        autoSearch:true,
        columns:[
	        {header:'主机名',sortable:true,dataIndex:'hostName'
             },
	        {header:'主机IP',sortable:true,dataIndex:'hostIp'
             },
	        {header:'主机端口',sortable:true,dataIndex:'hostPort'
             },
	        {
            	 header:'开启标志',
            	 sortable:true,
            	 dataIndex:'state',
            	 renderer : function(value, m) {
        				var returnValue;
        				hostStateStore.each(function(record) {
        					if (record.get('code') != value) {
        					} else {
        						returnValue = record.get('codedesc');
        						return false;
        					}
        				});
        				if (returnValue == '开启') {
							m.css = 'x-grid-back-green';
						} else {
							m.css = 'x-grid-back-red';
						}
        				return returnValue;
        			}
             }
        ],
        stripeRows:true,
        columnLines:true,
        listAction:"jsp/osgi/OpmsHostCfg_list.action",
        addTitle:"增加主机配置",
        editTitle:"编辑主机配置",
        viewTitle:"查看主机配置",
        initEditAction:"jsp/osgi/OpmsHostCfg_find",
        saveAction:"jsp/osgi/OpmsHostCfg_save",
        deleteAction:"jsp/osgi/OpmsHostCfg_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":"主机名","name":"hostName","xtype":"textfield"},
                {"anchor":"95%","fieldLabel":"主机IP","name":"hostIp","xtype":"textfield"},
                {
                	"anchor":"95%",
                	"fieldLabel":"开启标志",
                	"name":"state",
                	"xtype":"dcombo",
                	store: hostStateStore
                }
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
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"开启标志","name":"state","xtype":"dcombo",store: hostStateStore}
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