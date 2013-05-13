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
	
	var systemIdStore = new Ext.data.Store( {
		baseParams : {
			code : 'SYSTEM_ID'
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
	systemIdStore.load();

    var grid = new Ext.ux.FunctionGrid({
        title:"系统配置列表",
        searchColumn:2,
        dataId:"id",
        columns:[
	        {header:'主机IP',sortable:true,dataIndex:'hostIp'
             },
	        {header:'主机端口',sortable:true,dataIndex:'hostPort'
             },
	        {
            	 header:'系统标识',
            	 sortable:true,
            	 dataIndex:'systemId',
            	 renderer : function(value, m) {
 	 				var returnValue;
 	 				systemIdStore.each(function(record) {
 	 					if (record.get('code') != value) {
 	 					} else {
 	 						returnValue = record.get('codedesc');
 	 						return false;
 	 					}
 	 				});
 	 				return returnValue;
 	 			}
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
        autoSearch:true,
        winWidth: 650,
        winHeight:650,
        listAction:"jsp/osgi/OpmsSystemCfg_list.action",
        addTitle:"增加系统配置",
        editTitle:"编辑系统配置",
        viewTitle:"查看系统配置",
        initEditAction:"jsp/osgi/OpmsSystemCfg_find",
        saveAction:"jsp/osgi/OpmsSystemCfg_save",
        deleteAction:"jsp/osgi/OpmsSystemCfg_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":"主机IP","name":"hostIp","xtype":"textfield"},
                {"allowDecimals":true,"anchor":"95%","fieldLabel":"主机端口","name":"hostPort","xtype":"textfield"},
                {
                	"anchor":"95%",
                	"fieldLabel":"系统标识",
                	"name":"systemId",
                	"xtype":"dcombo",
                	store: systemIdStore
                },
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
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"主机IP","maxLength":15,"name":"hostIp","xtype":"textfield"},
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"系统标识","name":"systemId","xtype":"dcombo",store: systemIdStore}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"allowDecimals":true,"anchor":"95%","fieldLabel":"主机端口","name":"hostPort","xtype":"textfield"},
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