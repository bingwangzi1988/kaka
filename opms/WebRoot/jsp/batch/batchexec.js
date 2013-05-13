Ext.onReady(function() {

	var batchStatusStore = new Ext.data.Store( {
		baseParams : {
			code : 'BATCHSTATUS'
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
	batchStatusStore.load();
	
	var batchHostStore = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'jsp/batch/BatchJobgrpExecution_listHost.action'
		}),
		reader : new Ext.data.JsonReader( {}, [ {
			name : "code"
		}, {
			name : "codedesc"
		} ]),
		remoteSort : false
	});
	batchHostStore.load();
	
	var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	
	var cmWidth = (window.screen.width-280)/6;
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([
	                                   sm,
	                                   {header:'ID',dataIndex:'id', hidden:true},
	                                   {header:'批次号',sortable:true,dataIndex:'batchNo',width: cmWidth},
	                                   {header:'批量组名称',sortable:true,dataIndex:'name',width: cmWidth},
	                                   {header:'IP地址',sortable:true,dataIndex:'ipAddr',width: cmWidth},
	                                   {header:'开始时间',sortable:true,dataIndex:'startTime',width: cmWidth},
	                                   {header:'结束时间',sortable:true,dataIndex:'endTime',width: cmWidth},
	                                   {header:'执行状态',sortable:true,dataIndex:'status',width: cmWidth,
	                   					renderer : function(value, m) {
	                	  	 				var returnValue;
	                	  	 				batchStatusStore.each(function(record) {
	                	  	 					if (record.get('code') != value) {
	                	  	 					} else {
	                	  	 						returnValue = record.get('codedesc');
	                	  	 						return false;
	                	  	 					}
	                	  	 				});
	                	  	 				if (value == 'S') {
	                							m.css = 'x-grid-back-green';
	                						} else if (value == 'F') {
	                							m.css = 'x-grid-back-red';
	                						} else {
	                							m.css = 'x-grid-back-yellow';
	                						}
	                	  	 				return returnValue;
	                	  	 			}}
	                                  ]
									);
	// 数据存储
	var store = new Ext.data.Store({
		// 获取数据的方式
		proxy : new Ext.data.HttpProxy({
					url : 'jsp/opms/batch/BatchJobgrpExecution_list.action'
				}),
		// 数据读取器
		reader : new Ext.data.JsonReader({
					totalProperty : 'total', // 记录总数
					root : 'root' // Json中的列表数据根节点
				}, [{
							name : 'id' // Json中的属性Key值
						}, {
							name : 'name'
						}, {
							name : 'batchNo'
						}, {
							name : 'ipAddr'
						}, {
							name : 'startTime'
						}, {
							name : 'endTime'
						}, {
							name : 'status'
						}])
			});
	
	// 翻页排序时带上查询条件
	store.on('beforeload', function() {
				this.baseParams = {
						batchNo : Ext.getCmp('batchNo').getValue(),
						startTimeBegin : Ext.getCmp('startTimeBegin').getValue()
//						clrinstno : Ext.getCmp('clrinstno').getValue(),
//						dealtype : Ext.getCmp('dealtype').getValue(),
//						dealflag : Ext.getCmp('dealFlag').getValue()
				};
			});
	
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				editable : false,
				width : 85
			});
	var number = parseInt(pagesize_combo.getValue());
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(comboBox.getValue());
				number = parseInt(comboBox.getValue());
				store.reload({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			});
	
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				emptyMsg : "没有符合条件的记录",
				items : [
				         '-', 
				         '&nbsp;&nbsp;', 
				         pagesize_combo
				       ]
			});
	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [
				         {xtype:'label', text : '批次号 ', width: 80},
				         {
							xtype : 'textfield',
							id : 'batchNo',
							name : 'batchNo',
							width : 140
						},
						{xtype:'tbseparator'},
						{xtype:'label',text : '执行日期'},
						{
							xtype : 'datefield',
							id : 'startTimeBegin',
							name : 'startTimeBegin',
							width : 150,
							format:"Y-m-d"
						},
						{
							text : '<span style="color: green; font-weight: bold;">查询</span>',
							iconCls : 'page_findIcon',
							handler : function() {
								queryCatalogItem();
							}
						},
						{xtype:'tbseparator'},
						{
							text : '<span style="color: green; font-weight: bold;">续跑</span>',
//							style: 'background-color: yellow;',
							iconCls : 'runitIcon',
							handler : function() {
								continueRunJobGrp();
							}
						}
						]
			});
	
	var batchJobGrpGrid = new Ext.grid.GridPanel({
		// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
		title : '任务组执行列表',
//		renderTo : 'gridDiv', // 和JSP页面的DIV元素ID对应
		height : 500,
		width: window.screen.width-300,
		frame : true,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm: sm,
		columnLines:true,
		tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

 // 是否默认选中第一行数据
	bbar.on("change", function() {
				// grid.getSelectionModel().selectFirstRow();

			});
	
	// 查询表格数据
	function queryCatalogItem() {
		store.load({
					params : {
						start : 0,
						limit : bbar.pageSize,
						batchNo : Ext.getCmp('batchNo').getValue(),
						startTimeBegin : Ext.getCmp('startTimeBegin').getValue()
					}
				});
	}
	
	// 查询表格数据
	function continueRunJobGrp() {
		var c = batchJobGrpGrid.getSelectionModel().getSelections();
		if(c.length == 1){
			var record = batchJobGrpGrid.getSelectionModel().getSelected();
			if('F'==record.get('status')) {
				var continueRunform = new Ext.form.FormPanel({
	//		    	title:'批量续跑',  
			    	labelWidth:0.1,  
			    	bodyStyle:'padding:5 5 5 5',
			    	labelWidth : 65, 
			    	defaultType : 'textfield', // 表单元素默认类型
			    	frame:true,  
			    	height:210,  
			    	width:300,  
			    	region:'center',
	//		    	collapsible:true,
			    	items:[
							{
								fieldLabel : '批次号', // 标签
								id : 'batNo',
								name : 'batNo',
								value: record.get('batchNo'),
								readOnly: true,
								anchor : '100%' // 宽度百分比
							},
							{
								fieldLabel : '批量名', // 标签
								id : 'name',
								name : 'name',
								value: record.get('name'),
								readOnly: true,
								anchor : '100%' // 宽度百分比
							},
							{
								fieldLabel : '续跑机器', // 标签
								id : 'hostIp',
								name : 'hostIp',
								"xtype":"dcombo",
								store: batchHostStore,
								allowBlank: false,
								anchor : '100%' // 宽度百分比
							}
			    	      ],
			    	buttons:[  
			    	           {
			    	        	   text:'执行续跑',
			    	        	   iconCls : 'runitIcon',
			    	        	   handler : function() {
			    	        		   if(continueRunform.getForm().isValid()) {
			    							Ext.Msg.confirm('提示', '确定执行此操作?', function(button, text) {
			    								if (button == 'yes') {
			    									var params = continueRunform.getForm().getValues();
			    							    	Ext.Ajax.request({
			    								 		   url:'jsp/opms/batch/BatchJobgrpExecution_continueRun.action',
			    								 		   params:params,
			    								 		   callback:function(opts,success,response){
			    								 			   var datas=response.responseText;
			    								 			   var objs=Ext.decode(datas);
			    								 			   if(objs.success){
			    								 				  Ext.MessageBox.alert('提示', objs.msg);
			    								 			   } else {
			    								 				  Ext.MessageBox.show({
			    							                          title: '提示',
			    							                          msg: objs.msg,
			    							                          buttons: Ext.MessageBox.OK,
			    							                          icon: Ext.MessageBox.WARNING
			    							                      });
			    								 			   }
			    								 			  jobWin.close();
			    								 			  batchJobGrpGrid.store.reload();
			    								 		   }
			    								 	   });
			    								}
			    							});
			    						}
			    	   			   }
			    	           }  
			    	        ]  
			    });
				var jobWin = new Ext.Window({
	//				layout : 'fit',
					width:350,
					height:240,
					closable : true,
					constrain : false,
					modal : true,
					title : '<span style="color: red; font-weight:bold;">批量续跑</span>',
					layout:'border',
					closeAction : 'close',
					plain : true,
					items : [continueRunform]
				});
				jobWin.show();
			} else {
				Ext.MessageBox.show({
					title: '警告',
					msg: "只有执行失败才能续跑",
					buttons: Ext.MessageBox.OK,
					icon: Ext.MessageBox.WARNING
	        	});
			}
		}
		else
		{
			Ext.MessageBox.show({
				title: '警告',
				msg: "请选择一个对象",
				buttons: Ext.MessageBox.OK,
				icon: Ext.MessageBox.WARNING
        	});
		}
	}

	// 页面初始自动查询数据
	store.load({params : {start : 0,limit : bbar.pageSize}});
	
    new Ext.Viewport({
            layout:'border',
            items:[batchJobGrpGrid]
        });
    
 });