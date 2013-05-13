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
            	 header:'基础组件',
            	 sortable:true,
            	 dataIndex:'bundle_0',
            	 renderer : function() {
							return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
				 }
             },
             {
            	 header:'平台组件',
            	 sortable:true,
            	 dataIndex:'bundle_1',
            	 renderer : function() {
						return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
            	 }
             },
             {
            	 header:'业务组件',
            	 sortable:true,
            	 dataIndex:'bundle_2',
            	 renderer : function() {
						return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
            	 }
             },
             {
            	 header:'交易组件',
            	 sortable:true,
            	 dataIndex:'bundle_3',
            	 renderer : function() {
						return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
            	 }
             },
             {
            	 header:'其他组件',
            	 sortable:true,
            	 dataIndex:'bundle_4',
            	 renderer : function() {
						return '<a href="javascript:void(0);"><img src="../../resource/image/ext/bundleMng.gif" style="cursor:pointer; height:18;"></a>';
            	 }
             }
        ],
        stripeRows:true,
        columnLines:true,
        edit : false,
        listAction:"jsp/osgi/OpmsSystemCfg_list.action?state=1"
    });

	grid.on("cellclick",
			function(grid, rowIndex, columnIndex, e) {
				var store = grid.getStore();
				var record = store.getAt(rowIndex);
				var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
				
				if (fieldName == 'bundle_0' && columnIndex == 3) {
					bundleAdmin(record.get("hostIp"), record.get("hostPort"), record.get("systemId"), "0");
				}
				if (fieldName == 'bundle_1' && columnIndex == 4) {
					bundleAdmin(record.get("hostIp"), record.get("hostPort"), record.get("systemId"), "1");
				}
				if (fieldName == 'bundle_2' && columnIndex == 5) {
					bundleAdmin(record.get("hostIp"), record.get("hostPort"), record.get("systemId"), "2");
				}
				if (fieldName == 'bundle_3' && columnIndex == 6) {
					bundleAdmin(record.get("hostIp"), record.get("hostPort"), record.get("systemId"), "3");
				}
				if (fieldName == 'bundle_4' && columnIndex == 7) {
					bundleAdmin(record.get("hostIp"), record.get("hostPort"), record.get("systemId"), "4");
				}
			});
	
	function bundleAdmin(hostIp, hostPort, systemId, bundleType) {
		var bundleWinTitle = "组件管理";
		if("0" == bundleType) {
			bundleWinTitle = "基础组件管理";
		} else if("1" == bundleType) {
			bundleWinTitle = "平台组件管理";
		} else if("2" == bundleType) {
			bundleWinTitle = "业务组件管理";
		} else if("3" == bundleType) {
			bundleWinTitle = "交易组件管理";
		} else if("4" == bundleType) {
			bundleWinTitle = "其他组件管理";
		}
		var bundleExpander = new Ext.grid.RowExpander(
				{
					tpl : new Ext.Template(
							'<p style=margin-left:50px;><span style=color:Teal;font-weight:bold;>Manifest Headers</span><br><span>{manifestHeaders}</span></p>'),
					// 屏蔽双击事件
					expandOnDblClick : false
				});
		var bundleStateStore=new Ext.data.SimpleStore({
			 data:[['ACTIVE','ACTIVE'], ['RESOLVED','RESOLVED'], ['INSTALLED','INSTALLED']],
			 fields:['code','codedesc']
		   });
		
		var bundleGrid = new Ext.ux.FunctionGrid(
				{
//					title : "OSGI组件管理",
					searchColumn : 2,
					dataId : "id",
					columns : [
							bundleExpander,
							{
								header : 'ID',
								sortable : true,
								dataIndex : 'id',
								width : 20
							},
							{
								header : 'Name',
								sortable : true,
								dataIndex : 'name'
							},
							{
								header : 'Version',
								sortable : true,
								dataIndex : 'version'
							},
							{
								header : 'State',
								sortable : true,
								dataIndex : 'state',
								width : 35,
								renderer : function(value, m) {
									if (value == 'ACTIVE') {
										m.css = 'x-grid-back-green';
									} else if (value == 'RESOLVED') {
										m.css = 'x-grid-back-red';
									} else {
										m.css = 'x-grid-back-yellow';
									}
									return value;
								}
							},
							{
								header : 'manifestHeaders',
								sortable : true,
								dataIndex : 'manifestHeaders',
								hidden : true
							},
							// {header:'Location',sortable:true,dataIndex:'location'
							// },
							{
								header : 'LastModified',
								sortable : true,
								dataIndex : 'lastModified',
								width : 70
							},
							{
								header : 'Start',
								sortable : true,
								dataIndex : 'startFlag',
								width : 25,
								renderer : function(value) {
									if (value == '1') {
										return '<a href="javascript:void(0);"><img src="../../resource/image/ext/start.gif" style="cursor:pointer;"></a>';
									}
								}
							},
							{
								header : 'Stop',
								sortable : true,
								dataIndex : 'stopFlag',
								width : 25,
								renderer : function(value) {
									if (value == '1') {
										return '<a href="javascript:void(0);"><img src="../../resource/image/ext/stop.gif" style="cursor:pointer;"></a>';
									}
								}
							},
							{
								header : 'Refresh',
								sortable : true,
								dataIndex : 'refreshFlag',
								width : 25,
								renderer : function(value) {
									if (value == '1') {
										return '<a href="javascript:void(0);"><img src="../../resource/image/ext/refresh.gif" style="cursor:pointer;"></a>';
									}
								}
							},
							{
								header : 'Update',
								sortable : true,
								dataIndex : 'updateFlag',
								width : 25,
								renderer : function(value) {
									if (value == '1') {
										return '<a href="javascript:void(0);"><img src="../../resource/image/ext/update.gif" style="cursor:pointer;"></a>';
									}
								}
							},
							{
								header : 'Uninstall',
								sortable : true,
								dataIndex : 'uninstallFlag',
								width : 25,
								renderer : function(value) {
									if (value == '1') {
										return '<a href="javascript:void(0);"><img src="../../resource/image/ext/uninstall.gif" style="cursor:pointer;"></a>';
									}
								}
							} ],
					stripeRows : true,
					columnLines : true,
					autoSearch : true,
					searchSet:[
				                {"anchor":"95%","fieldLabel":"组件名","name":"bundleName","xtype":"textfield"},
				                {"anchor":"95%","fieldLabel":"组件状态","name":"bundleState","xtype":"dcombo", "store": bundleStateStore,renderer : function(value) {
			        				var returnValue;
			        				hostStateStore.each(function(record) {
			        					if (record.get('code') != value) {
			        					} else {
			        						returnValue = record.get('codedesc');
			        						return false;
			        					}
			        				});
			        				return returnValue;
			        			}}
				            ],
					plugins : bundleExpander,
					edit : false,
					listAction : "jsp/osgi/OpmsBundleMng_list.action?hostIp=" + hostIp + "&hostPort=" + hostPort + "&systemId=" + systemId + "&bundleType=" + bundleType,
					tbar : [
							new Ext.Toolbar.TextItem('<b>组件列表</b>'),
							{
								xtype : "tbfill"
							},
							{
								xtype : "tbseparator"
							},
							{
								pressed : true,
								text : 'Install Bundle',
								width : 160,
								handler : function() {
									install("install");
								}
							} ]
				});
		
		bundleGrid.on("cellclick",
				function(grid, rowIndex, columnIndex, e) {
					var store = grid.getStore();
					var record = store.getAt(rowIndex);
					var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
					if (fieldName == 'startFlag' && columnIndex == 7 && record.get('startFlag') == 1) {
						Ext.Msg.confirm('提示', '确定执行此操作?', function(button, text) {
							if (button == 'yes') {
								moduleOper("start");
							}
						});
					}
					if (fieldName == 'stopFlag' && columnIndex == 8	&& record.get('stopFlag') == 1) {
						Ext.Msg.confirm('提示', '确定执行此操作?', function(button, text) {
							if (button == 'yes') {
								moduleOper("stop");
							}
						});
					}
					if (fieldName == 'refreshFlag' && columnIndex == 9	&& record.get('refreshFlag') == 1) {
						Ext.Msg.confirm('提示', '确定执行此操作?', function(button, text) {
							if (button == 'yes') {
								moduleOper("refresh");
							}
						});
					}
					if (fieldName == 'updateFlag' && columnIndex == 10	&& record.get('updateFlag') == 1) {
								install("update");
					}
					if (fieldName == 'uninstallFlag' && columnIndex == 11 && record.get('uninstallFlag') == 1) {
						Ext.Msg.confirm('提示', '确定执行此操作?', function(button, text) {
							if (button == 'yes') {
								moduleOper("uninstall");
							}
						});
					}
				});
		
		function moduleOper(oper) {
			var record = bundleGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url : _contextPath_ + '/jsp/osgi/OpmsBundleMng_' + oper + '.action',
				params : {
					id : record.get('id'),
					hostIp: hostIp,
					hostPort: hostPort
				},
				callback : function(opts, success, response) {
					var datas = response.responseText;
					var objs = Ext.decode(datas);
					if (objs.success) {
						Ext.MessageBox.alert('提示', objs.msg);
					} else {
						Ext.MessageBox.show({
							title : '提示',
							msg : objs.msg,
							buttons : Ext.MessageBox.OK,
							icon : Ext.MessageBox.WARNING
						});
					}
					bundleGrid.store.reload();
				}
			});
		}
		
		var bundleWin = new Ext.Window({
//			layout : 'fit',
			width:window.screen.width-250,
			height:window.screen.height-340,
			closable : true,
			constrain : false,
			modal : true,
			title : bundleWinTitle,
			layout:'border',
			closeAction : 'close',
			plain : true,
			items : [bundleGrid.panels]
		});
		bundleWin.show();
	
			function install(oper) {
				var bundleTitle = "安装组件";
				var submitText = "安装";
				var checkboxDisabled = false;
				if("update" == oper) {
					bundleTitle = "更新组件";
					submitText = "更新";
					checkboxDisabled = true;
				}
				var bundleForm = new Ext.form.FormPanel({
					region:'south',
					id : 'bundleForm',
					name : 'bundleForm',
					height:40,
					labelWidth : 70,
					frame : true,
					defaultType : 'textfield',
					labelAlign : 'right',
					bodyStyle : 'padding:2 2 2 2',
					items : [{
						xtype : 'checkbox',
						id : 'startFlag',
						name : 'startFlag',
						checked : true,
						boxLabel : '启动',
						disabled: checkboxDisabled
					}]
				});
				var uploadForm = new Ext.form.FormPanel({
					region:'center',
					id : 'uploadForm',
					name : 'uploadForm',
					labelWidth : 70,
					frame : true,
					fileUpload : true, // 一定要设置这个属性,否则获取不到上传对象的
					defaultType : 'textfield',
					labelAlign : 'right',
					bodyStyle : 'padding:2 2 2 2',
					items : [{
						fieldLabel : '组件上传',
						id : 'upload',
						name : 'upload',
						xtype : 'fileuploadfield',
						allowBlank : false,
						anchor : '100%',
						edit: false,
						listeners : { 
							'blur' : function(obj) {
								var value = obj.getValue();
								if(value == ""){
									Ext.Msg.alert('提示', '请选择上传组件');
								}
							}
						}
					}]
				});

				var firstWindow = new Ext.Window({
					title : bundleTitle,
					layout : 'border',
					width : 300,
					height : 150,
					closable : true,
					collapsible : true,
					maximizable : true,
					border : false,
					constrain : true,
					modal : true,
					pageY : 260,
					pageX : document.body.clientWidth / 2 - 380 / 2,
					items : [uploadForm, bundleForm],
					buttons : [{
						text : submitText,
						iconCls : 'wrenchIcon',
						handler : function() {
							Ext.MessageBox.show({
								title : '提示',
								msg : '确定执行此操作？',
								width : 200,
								buttons : Ext.MessageBox.OKCANCEL,
								fn : function(btn, text) {
									if (btn == 'ok'){	
										if(Ext.getCmp('upload').getValue()==''){
											Ext.Msg.alert('提示', '请选择上传组件');
										} else {
											if (bundleForm.getForm().isValid() && uploadForm.getForm().isValid()) {
												var bundleId = "";
												if("update"==oper) {
													var record = bundleGrid.getSelectionModel().getSelected();
													if(record != undefined) {
														bundleId = record.get('id');
													}
												}
												uploadForm.form.submit({
												url : _contextPath_ + '/jsp/osgi/OpmsBundleMng_upload.action',
												method : 'POST',
												params:{
													fileNm:encodeURI(Ext.getCmp('upload').getValue()),
													hostIp : hostIp,
													hostPort : hostPort,
													bundleType : bundleType,
													bundleId: bundleId,
													startFlag: Ext.getCmp('startFlag').getValue()
												},
												timeout: 60000, // 60s
												waitMsg : '文件上传中，请稍候。。。',
												success : function(form, action){
													Ext.MessageBox.alert('提示', action.result.msg);
													firstWindow.close();
													bundleGrid.getStore().reload();
//													saveOperateRcd(operateParams);
												},
												failure : function(form, action) {
													Ext.MessageBox.show({
														title : '提示',
														msg : action.result.msg,
														buttons : Ext.MessageBox.OK,
														icon : Ext.MessageBox.WARNING
													});
												}});
											}	
										}
									}	
								}
							});
						}
					}, {
						text : '重置',
						iconCls : 'tbar_synchronizeIcon',
						handler : function() {
							uploadForm.form.reset();
							bundleForm.form.reset();
						}
					}]
				});
				firstWindow.show();
		}
	}
	
    new Ext.Viewport({
            layout:'border',
            items:[grid.panels]
        });
 });