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
        title:"���������б�",
        searchColumn:3,
        dataId:"id",
        autoSearch:true,
        columns:[
	        {header:'������',sortable:true,dataIndex:'hostName'
             },
	        {header:'����IP',sortable:true,dataIndex:'hostIp'
             },
	        {header:'�����˿�',sortable:true,dataIndex:'hostPort'
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
        listAction:"jsp/osgi/OpmsHostCfg_list.action",
        addTitle:"������������",
        editTitle:"�༭��������",
        viewTitle:"�鿴��������",
        initEditAction:"jsp/osgi/OpmsHostCfg_find",
        saveAction:"jsp/osgi/OpmsHostCfg_save",
        deleteAction:"jsp/osgi/OpmsHostCfg_delete",
        searchSet:[
                {"anchor":"95%","fieldLabel":"������","name":"hostName","xtype":"textfield"},
                {"anchor":"95%","fieldLabel":"����IP","name":"hostIp","xtype":"textfield"},
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
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"������","maxLength":100,"name":"hostName","xtype":"textfield"},
                               {"allowBlank":false,"allowDecimals":true,"anchor":"95%","fieldLabel":"�����˿�","name":"hostPort","xtype":"numberfield"}
                        ]
                    },
                    {
                        columnWidth:.5,
                        layout: 'form',
                        items: [
                               {"allowBlank":false,"anchor":"95%","fieldLabel":"����IP","maxLength":15,"name":"hostIp","xtype":"textfield"},
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