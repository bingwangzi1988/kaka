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
        title:"系统列表",
        dataId:"id",
        autoSearch:true,
        columns:[
            {
            	 header:'系统标识',
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
            	 header:'组件配置',
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
		var bundleCfgWinTitle = systemDesc + "组件配置";
		var bundleCfgGrid = new Ext.ux.FunctionGrid({
	        title:"组件管理配置列表",
	        searchColumn:2,
	        dataId:"id",
	        autoSearch:true,
	        winWidth: 650,
	        winHeight:600,
	        columns:[
//	     	    {
//	     	    	header:'系统标识',
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
		        {header:'组件名',sortable:true,dataIndex:'bundleName'
	             },
		        {
	            	 header:'组件类型',
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
		        {header:'组件路径',sortable:true,dataIndex:'bundlePath'
	             },
		        {
	            	 header:'启动标识',
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
	  	 				if (returnValue == '允许') {
							m.css = 'x-grid-back-green';
						} else {
							m.css = 'x-grid-back-red';
						}
	  	 				return returnValue;
	  	 			}
	             },
		        {
	            	 header:'停止标识',
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
		   	 			if (returnValue == '允许') {
							m.css = 'x-grid-back-green';
						} else {
							m.css = 'x-grid-back-red';
						}
	   	 				return returnValue;
	   	 			}
	             },
		        {
	            	 header:'刷新标识',
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
		   	 			if (returnValue == '允许') {
							m.css = 'x-grid-back-green';
						} else {
							m.css = 'x-grid-back-red';
						}
	   	 				return returnValue;
	   	 			}
	             },
		        {
	            	 header:'更新标识',
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
		   	 			if (returnValue == '允许') {
							m.css = 'x-grid-back-green';
						} else {
							m.css = 'x-grid-back-red';
						}
	   	 				return returnValue;
	   	 			}
	             },
		        {
	            	 header:'卸载标识',
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
		   	 			if (returnValue == '允许') {
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
	        addTitle:"增加组件管理配置",
	        editTitle:"编辑组件管理配置",
	        viewTitle:"查看组件管理配置",
	        initEditAction:"jsp/osgi/OpmsBundleCfg_find",
	        saveAction:"jsp/osgi/OpmsBundleCfg_save",
	        deleteAction:"jsp/osgi/OpmsBundleCfg_delete",
	        searchSet:[
//	                {
//	                	"anchor":"95%",
//	                	"fieldLabel":"系统标识",
//	                	"name":"systemId",
//	                	"xtype":"dcombo", 
//	                	store: systemIdStore
//	                },
	                {"anchor":"95%","fieldLabel":"组件名","name":"bundleName","xtype":"textfield"},
	                {
	                	"anchor":"95%",
	                	"fieldLabel":"组件类型",
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
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"系统标识","name":"systemId","xtype":"textfield", "value": systemId, "hidden": true},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"组件名","maxLength":100,"name":"bundleName","xtype":"textfield"},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"组件路径","name":"bundlePath","xtype":"dcombo",store:bundlePathStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"停止标识","name":"stopFlag","xtype":"dcombo",store:bundleOpFlagStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"更新标识","name":"updateFlag","xtype":"dcombo",store:bundleOpFlagStore}
	                        ]
	                    },
	                    {
	                        columnWidth:.5,
	                        layout: 'form',
	                        items: [
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"组件类型","name":"bundleType","xtype":"dcombo",store:bundleTypeStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"启动标识","name":"startFlag","xtype":"dcombo",store:bundleOpFlagStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"刷新标识","name":"refreshFlag","xtype":"dcombo",store:bundleOpFlagStore},
	                               {"allowBlank":false,"anchor":"95%","fieldLabel":"卸载标识","name":"uninstallFlag","xtype":"dcombo",store:bundleOpFlagStore}
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