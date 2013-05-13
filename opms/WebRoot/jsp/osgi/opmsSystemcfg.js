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
        title:"ϵͳ�����б�",
        searchColumn:2,
        dataId:"id",
        columns:[
	        {header:'����IP',sortable:true,dataIndex:'hostIp'
             },
	        {header:'�����˿�',sortable:true,dataIndex:'hostPort'
             },
	        {
            	 header:'ϵͳ��ʶ',
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
            	 header:'������־',
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
	 				if (returnValue == '����') {
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
        addTitle:"����ϵͳ����",
        editTitle:"�༭ϵͳ����",
        viewTitle:"�鿴ϵͳ����",
        initEditAction:"jsp/osgi/OpmsSystemCfg_find",
        saveAction:"jsp/osgi/OpmsSystemCfg_save",
        deleteAction:"jsp/osgi/OpmsSystemCfg_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":"����IP","name":"hostIp","xtype":"textfield"},
                {"allowDecimals":true,"anchor":"95%","fieldLabel":"�����˿�","name":"hostPort","xtype":"textfield"},
                {
                	"anchor":"95%",
                	"fieldLabel":"ϵͳ��ʶ",
                	"name":"systemId",
                	"xtype":"dcombo",
                	store: systemIdStore
                },
                {
                	"anchor":"95%",
                	"fieldLabel":"������־",
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
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"����IP","maxLength":15,"name":"hostIp","xtype":"textfield"},
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"ϵͳ��ʶ","name":"systemId","xtype":"dcombo",store: systemIdStore}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"allowDecimals":true,"anchor":"95%","fieldLabel":"�����˿�","name":"hostPort","xtype":"textfield"},
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"������־","name":"state","xtype":"dcombo",store: hostStateStore}
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