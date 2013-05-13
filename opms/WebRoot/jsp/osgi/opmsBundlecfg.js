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
	
	var bundleTypeStore = new Ext.data.Store( {
		baseParams : {
			code : 'BUNDLE_TYPE'
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
	bundleTypeStore.load();
	
	var bundleOpFlagStore = new Ext.data.Store( {
		baseParams : {
			code : 'BUNDLE_OP_FLAG'
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
	bundleOpFlagStore.load();
	
	var bundlePathStore = new Ext.data.Store( {
		baseParams : {
			code : 'BUNDLE_PATH'
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
	bundlePathStore.load();
	
    var grid = new Ext.ux.FunctionGrid({
        title:"ϵͳ�б�",
        dataId:"id",
        autoSearch:true,
        columns:[
            {
            	 header:'ϵͳ��ʶ',
            	 sortable:true,
            	 dataIndex:'code',
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
            	 header:'�������',
            	 sortable:true,
            	 dataIndex:'bundleConfig',
            	 renderer : function() {
							return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
				 }
             }
        ],
        stripeRows:true,
        columnLines:true,
        edit : false,
        paging: false,
        listAction:"jsp/osgi/OpmsSystemCfg_listSystemId.action?code=SYSTEM_ID"
    });

	grid.on("cellclick",
			function(grid, rowIndex, columnIndex, e) {
				var store = grid.getStore();
				var record = store.getAt(rowIndex);
				var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
				
				if (fieldName == 'bundleConfig' && columnIndex == 1) {
					bundleConfig(record.get("code"));
				}
			});
	function bundleConfig(systemId) {
		var systemDesc;
		systemIdStore.each(function(record) {
			if (record.get('code') != systemId) {
			} else {
				systemDesc = record.get('codedesc');
				return false;
			}
		});
		var bundleCfgWinTitle = systemDesc + "�������";
		var bundleCfgGrid = new Ext.ux.FunctionGrid({
	        title:"������������б�",
	        searchColumn:2,
	        dataId:"id",
	        autoSearch:true,
	        winWidth: 650,
	        winHeight:600,
	        columns:[
//	     	    {
//	     	    	header:'ϵͳ��ʶ',
//	     	    	sortable:true,
//	     	    	dataIndex:'systemId',
//	     	    	renderer : function(value, m) {
//		 				var returnValue;
//		 				systemIdStore.each(function(record) {
//		 					if (record.get('code') != value) {
//		 					} else {
//		 						returnValue = record.get('codedesc');
//		 						return false;
//		 					}
//		 				});
//		 				return returnValue;
//		 			}
//	             },
		        {header:'�����',sortable:true,dataIndex:'bundleName'
	             },
		        {
	            	 header:'�������',
	            	 sortable:true,
	            	 dataIndex:'bundleType',
	            	 renderer : function(value, m) {
	 	 				var returnValue;
	 	 				bundleTypeStore.each(function(record) {
	 	 					if (record.get('code') != value) {
	 	 					} else {
	 	 						returnValue = record.get('codedesc');
	 	 						return false;
	 	 					}
	 	 				});
	 	 				return returnValue;
	 	 			}
	             },
		        {header:'���·��',sortable:true,dataIndex:'bundlePath'
	             },
		        {
	            	 header:'������ʶ',
	            	 sortable:true,
	            	 dataIndex:'startFlag',
	            	 renderer : function(value, m) {
	  	 				var returnValue;
	  	 				bundleOpFlagStore.each(function(record) {
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
	             },
		        {
	            	 header:'ֹͣ��ʶ',
	            	 sortable:true,
	            	 dataIndex:'stopFlag',
	            	 renderer : function(value, m) {
	   	 				var returnValue;
	   	 				bundleOpFlagStore.each(function(record) {
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
	             },
		        {
	            	 header:'ˢ�±�ʶ',
	            	 sortable:true,
	            	 dataIndex:'refreshFlag',
	            	 renderer : function(value, m) {
	   	 				var returnValue;
	   	 				bundleOpFlagStore.each(function(record) {
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
	             },
		        {
	            	 header:'���±�ʶ',
	            	 sortable:true,
	            	 dataIndex:'updateFlag',
	            	 renderer : function(value, m) {
	   	 				var returnValue;
	   	 				bundleOpFlagStore.each(function(record) {
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
	             },
		        {
	            	 header:'ж�ر�ʶ',
	            	 sortable:true,
	            	 dataIndex:'uninstallFlag',
	            	 renderer : function(value, m) {
	   	 				var returnValue;
	   	 				bundleOpFlagStore.each(function(record) {
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
	        listAction:"jsp/osgi/OpmsBundleCfg_list.action?systemId=" + systemId,
	        addTitle:"���������������",
	        editTitle:"�༭�����������",
	        viewTitle:"�鿴�����������",
	        initEditAction:"jsp/osgi/OpmsBundleCfg_find",
	        saveAction:"jsp/osgi/OpmsBundleCfg_save",
	        deleteAction:"jsp/osgi/OpmsBundleCfg_delete",
	        searchSet:[
//	                {
//	                	"anchor":"95%",
//	                	"fieldLabel":"ϵͳ��ʶ",
//	                	"name":"systemId",
//	                	"xtype":"dcombo", 
//	                	store: systemIdStore
//	                },
	                {"anchor":"95%","fieldLabel":"�����","name":"bundleName","xtype":"textfield"},
	                {
	                	"anchor":"95%",
	                	"fieldLabel":"�������",
	                	"name":"bundleType",
	                	"xtype":"dcombo",
	                	store: bundleTypeStore
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
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"ϵͳ��ʶ","name":"systemId","xtype":"textfield", "value": systemId, "hidden": true},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"�����","maxLength":100,"name":"bundleName","xtype":"textfield"},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"���·��","name":"bundlePath","xtype":"dcombo",store:bundlePathStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"ֹͣ��ʶ","name":"stopFlag","xtype":"dcombo",store:bundleOpFlagStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"���±�ʶ","name":"updateFlag","xtype":"dcombo",store:bundleOpFlagStore}
	                        ]
	                    },
	                    {
	                        columnWidth:.5,
	                        layout: 'form',
	                        items: [
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"�������","name":"bundleType","xtype":"dcombo",store:bundleTypeStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"������ʶ","name":"startFlag","xtype":"dcombo",store:bundleOpFlagStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"ˢ�±�ʶ","name":"refreshFlag","xtype":"dcombo",store:bundleOpFlagStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"ж�ر�ʶ","name":"uninstallFlag","xtype":"dcombo",store:bundleOpFlagStore}
	                        ]
	                    }
	                ]
	            }
	        ]
	    });
		
		var bundleCfgWin = new Ext.Window({
//			layout : 'fit',
			width:window.screen.width-250,
			height:window.screen.height-340,
			closable : true,
			constrain : false,
			modal : true,
			title : bundleCfgWinTitle,
			layout:'border',
			closeAction : 'close',
			plain : true,
			items : [bundleCfgGrid.panels]
		});
		bundleCfgWin.show();
	}
	
    new Ext.Viewport({
            layout:'border',
            items:[grid.panels]
        });
 });