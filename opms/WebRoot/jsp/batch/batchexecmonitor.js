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
	
	var root = new Ext.tree.AsyncTreeNode( {
		text : '批量任务',
		iconCls:'runitIcon',
		expanded : true,
		id : '00'
	});
	var menuTree = new Ext.tree.TreePanel( {
		loader : new Ext.tree.TreeLoader( {
			baseAttrs : {},
			dataUrl : 'jsp/opms/batch/BatchJobgrpExecution_listJobgrp.action'
		}),
		root : root,
		title : '',
		applyTo : 'menuTreeDiv',
		autoScroll : false,
		animate : false,
		useArrows : false,
		border : false
	});

	menuTree.on('click', function(node) {
		menuid = node.attributes.id;
		menuname = node.attributes.text;
		menutype = node.attributes.type;
		Ext.getCmp('parentmenuname').setValue(menuname);
//		Ext.getCmp('parentid').setValue(menuid);
		store.load( {
			params : {
//				start : 0,
//				limit : bbar.pageSize,
				menuid : menuid,
				menuname: menuname,
				menutype: menutype
			}
		});
	});
	menuTree.root.select();
	
	var cm = new Ext.grid.ColumnModel( [ 
	{
		header : '名称',
		dataIndex : 'name',
		width : 130
	}, {
		header : '类型',
		dataIndex : 'type',
		width : 50
	}, {
		header : 'ID',
		dataIndex : 'id',
		width : 60
	}, {
		header : 'PARENTID',
		dataIndex : 'parentid',
		hidden: true,
		width : 80
	}, {
		header : '批次号',
		dataIndex : 'batchNo',
		sortable : true,
		width : 110
	}, {
		header : '开始时间',
		dataIndex : 'starttime',
		width : 130
	}, {
		header : '结束时间',
		dataIndex : 'endtime',
		width : 130
	}, {
		header : '执行状态',
		dataIndex : 'status',
		width : 120,
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
			}
	}, {
		id : 'errmsg',
		header : '异常信息',
		dataIndex : 'errmsg'
	}, {
		header : '机器地址',
		dataIndex : 'ipAddr',
		width : 100
	}]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : 'jsp/opms/batch/BatchJobgrpExecution_list3.action'
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty : 'TOTALCOUNT',
			root : 'ROOT'
		}, [ {
			name : 'name'
		}, {
			name : 'type'
		}, {
			name : 'id'
		}, {
			name : 'parentid'
		}, {
			name : 'batchNo'
		}, {
			name : 'starttime'
		}, {
			name : 'endtime'
		}, {
			name : 'status'
		}, {
			name : 'errmsg'
		}, {
			name : 'ipAddr'
		} ])
	});

	// 翻页排序时带上查询条件
		store.on('beforeload', function() {
			this.baseParams = {
//				queryParam : Ext.getCmp('queryParam').getValue()
			};
		});

//		var pagesize_combo = new Ext.form.ComboBox( {
//			name : 'pagesize',
//			hiddenName : 'pagesize',
//			typeAhead : true,
//			triggerAction : 'all',
//			lazyRender : true,
//			mode : 'local',
//			store : new Ext.data.ArrayStore(
//					{
//						fields : [ 'value', 'text' ],
//						data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
//								[ 50, '50条/页' ], [ 100, '100条/页' ],
//								[ 250, '250条/页' ], [ 500, '500条/页' ] ]
//					}),
//			valueField : 'value',
//			displayField : 'text',
//			value : '50',
//			editable : false,
//			width : 85
//		});
//		var number = parseInt(pagesize_combo.getValue());
//		pagesize_combo.on("select", function(comboBox) {
//			bbar.pageSize = parseInt(comboBox.getValue());
//			number = parseInt(comboBox.getValue());
//			store.reload( {
//				params : {
//					start : 0,
//					limit : bbar.pageSize
//				}
//			});
//		});

//		var bbar = new Ext.PagingToolbar( {
//			pageSize : number,
//			store : store,
//			displayInfo : true,
//			displayMsg : '显示{0}条到{1}条,共{2}条',
//			emptyMsg : "没有符合条件的记录",
//			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
//		});

		var grid = new Ext.grid.GridPanel(
				{
					title : '<span style="font-weight:normal">当天批量执行情况</span>',
					iconCls : 'runitIcon', // 图标
//					renderTo : 'menuGridDiv',
					height : 500,
					// width:600,
					autoScroll : true,
					region : 'center',
					store : store,
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					},
					stripeRows : true,
					frame : true,
//					autoExpandColumn : 'errmsg',
					cm : cm,
					tbar : [ 
//					new Ext.form.TextField( {
//						id : 'queryParam',
//						name : 'queryParam',
//						emptyText : '请输入菜单名称',
//						enableKeyEvents : true,
//						listeners : {
//							specialkey : function(field, e) {
//								if (e.getKey() == Ext.EventObject.ENTER) {
//									queryMenuItem();
//								}
//							}
//						},
//						width : 130
//					}), {
//						text : '查询',
//						iconCls : 'previewIcon',
//						handler : function() {
//							queryMenuItem();
//						}
//					}, '-', {
					{
						text : '刷新',
						iconCls : 'arrow_refreshIcon',
						handler : function() {
							store.reload();
						}
					} ]
//					bbar : bbar
				});
		store.load();
//		store.load( {
//			params : {
//				start : 0,
//				limit : bbar.pageSize
//			}
//		});
		grid.on('rowdblclick', function(grid, rowIndex, event) {
//			editInit();
		});
		grid.on('sortchange', function() {
			// grid.getSelectionModel().selectFirstRow();
			});

//		bbar.on("change", function() {
//			// grid.getSelectionModel().selectFirstRow();
//			});

		var addMenuWindow, addMenuFormPanel;
		var comboxWithTree;
		var addRoot = new Ext.tree.AsyncTreeNode( {
			text : '海辉金信集成与开发平台',
			expanded : true,
			id : '00'
		});
		var addMenuTree = new Ext.tree.TreePanel( {
			loader : new Ext.tree.TreeLoader( {
				baseAttrs : {},
				dataUrl : 'jsp/opms/batch/BatchJobgrpExecution_listJobgrp.action'
			}),
			root : addRoot,
			autoScroll : true,
			animate : false,
			useArrows : false,
			border : false
		});
		// 监听下拉树的节点单击事件
		addMenuTree.on('click', function(node) {
			comboxWithTree.setValue(node.text);
			Ext.getCmp("addMenuFormPanel").findById('parentid').setValue(
					node.attributes.id);
			comboxWithTree.collapse();
		});

		comboxWithTree = new Ext.form.ComboBox(
				{
					id : 'parentmenuname',
					store : new Ext.data.SimpleStore( {
						fields : [],
						data : [ [] ]
					}),
					editable : false,
					value : ' ',
					emptyText : '请选择...',
					fieldLabel : '上级菜单',
					anchor : '100%',
					mode : 'local',
					triggerAction : 'all',
					maxHeight : 390,
					// 下拉框的显示模板,addMenuTreeDiv作为显示下拉树的容器
					tpl : "<tpl for='.'><div style='height:390px'><div id='addMenuTreeDiv'></div></div></tpl>",
					allowBlank : false,
					onSelect : Ext.emptyFn
				});
		// 监听下拉框的下拉展开事件
		comboxWithTree.on('expand', function() {
			// 将UI树挂到treeDiv容器
				addMenuTree.render('addMenuTreeDiv');
				// addMenuTree.root.expand(); //只是第一次下拉会加载数据
				addMenuTree.root.reload(); // 每次下拉都会加载数据

			});
		var expandedStore = new Ext.data.SimpleStore( {
			fields : [ 'value', 'text' ],
			data : [ [ '0', '0 收缩' ], [ '1', '1 展开' ] ]
		});
		var expandedCombo = new Ext.form.ComboBox( {
			name : 'expanded',
			hiddenName : 'expanded',
			store : expandedStore,
			mode : 'local',
			triggerAction : 'all',
			valueField : 'value',
			displayField : 'text',
			value : '0',
			fieldLabel : '节点初始',
			emptyText : '请选择...',
			allowBlank : false,
			forceSelection : true,
			editable : false,
			typeAhead : true,
			anchor : "99%"
		});
		
		var form = new Ext.form.FormPanel({
	    	title:'<span style="font-weight:normal">批量运行日志</span>',  
	    	labelWidth:0.1,  
	    	iconCls : 'runitIcon', // 图标
	    	bodyStyle:'padding:5 5 5 5',  
	    	frame:true,  
	    	height:200,  
	    	width:window.screen.width-550,
	    	region:'south',
	    	collapsible:true,
	    	items:[
	    	       new Ext.form.TextArea({
	    	    	   id:'log', 
//	    	    	   readOnly:true,
	    	    	   width:window.screen.width-240,
	    	    	   height: 150,
	    	    	   style : 'background:none repeat scroll 0 0 black;color:white;',
	    	    	   anchor : '100%'
	    	       })  
	    	      ]
//	    	buttons:[  
//	    	           {text:'确定',handler:addLog}  
//	    	        ]  
	    });
		
		addLog = function(msg) {
	        var log = form.findById('log');       //取得输入框控件
	        log.setValue(log.getValue() + msg);
	        log.scrollTop = log.scrollHeight;
	    }
		
		reloadData = function() {
			store.load();
		}
		
		/**
		 * 布局
		 */
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ {
				title : '<span style="font-weight:normal">功能菜单</span>',
				iconCls : 'layout_contentIcon',
				tools : [ {
					id : 'refresh',
					handler : function() {
						menuTree.root.reload()
					}
				} ],
				collapsible : true,
				width : 210,
				minSize : 160,
				maxSize : 280,
				split : true,
				region : 'west',
				autoScroll : true,
				// collapseMode:'mini',
				items : [ menuTree ]
			}, {
				region : 'center',
				layout : 'border',
				items : [ grid, form ]
			}, {
				
			} ]
		});
//		/**
//		 * 保存菜单数据
//		 */
//		function saveMenuItem(operateParams) {
//			if (!addMenuFormPanel.form.isValid()) {
//				return;
//			}
//			addMenuFormPanel.form.submit( {
//				url : './resource.ered?reqCode=saveMenuItem',
//				waitTitle : '提示',
//				method : 'POST',
//				waitMsg : '正在处理数据,请稍候...',
//				success : function(form, action) {
//					addMenuWindow.hide();
//					store.reload();
//					refreshNode(Ext.getCmp('parentid').getValue());
//					form.reset();
//					Ext.MessageBox.alert('提示', action.result.msg);
//					insertOperateRcd(operateParams);
//				},
//				failure : function(form, action) {
//					var msg = action.result.msg;
//					Ext.MessageBox.alert('提示', '菜单数据保存失败:<br>' + msg);
//				}
//			});
//		}
//
//		/**
//		 * 刷新指定节点
//		 */
//		function refreshNode(nodeid) {
//			var node = menuTree.getNodeById(nodeid);
//			/* 异步加载树在没有展开节点之前是获取不到对应节点对象的 */
//			if (Ext.isEmpty(node)) {
//				menuTree.root.reload();
//				return;
//			}
//			if (node.attributes.leaf) {
//				node.parentNode.reload();
//			} else {
//				node.reload();
//			}
//		}

		/**
		 * 根据条件查询菜单
		 */
//		function queryMenuItem() {
//			store.load( {
//				params : {
//					start : 0,
//					limit : bbar.pageSize,
//					queryParam : Ext.getCmp('queryParam').getValue()
//				}
//			});
//		}

		
		/**
		 * 删除菜单
		 */
//		function deleteMenuItems(pType, pMenuid,operateParams) {
//			var rows = grid.getSelectionModel().getSelections();
//			var fields = '';
//			for ( var i = 0; i < rows.length; i++) {
//				if (rows[i].get('menutype') == '1') {
//					fields = fields + rows[i].get('menuname') + '<br>';
//				}
//			}
//			if (fields != '') {
//				Ext.Msg
//						.alert(
//								'提示',
//								'<b>您选中的项目中包含如下系统内置的只读项目</b><br>' + fields + '<font color=red>只读项目不能删除!</font>');
//				return;
//			}
//			if (Ext.isEmpty(rows)) {
//				if (pType == '1') {
//					Ext.Msg.alert('提示', '请先选中要删除的项目!');
//					return;
//				}
//			}
//			var strChecked = jsArray2JsString(rows, 'menuid');
//			Ext.Msg
//					.confirm(
//							'请确认',
//							'你真的要删除选中菜单及其包含的所有子菜单吗?',
//							function(btn, text) {
//								if (btn == 'yes') {
//									if (runMode == '0') {
//										Ext.Msg
//												.alert('提示',
//														'系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
//										return;
//									}
//									showWaitMsg();
//									Ext.Ajax
//											.request( {
//												url : './resource.ered?reqCode=deleteMenuItems',
//												success : function(response) {
//													var resultArray = Ext.util.JSON
//															.decode(response.responseText);
//													store.reload();
//													if (pType == '1') {
//														menuTree.root.reload();
//													} else {
//														menuTree.root.reload();
//													}
//													Ext.Msg.alert('提示',
//															resultArray.msg);
//													insertOperateRcd(operateParams);
//												},
//												failure : function(response) {
//													var resultArray = Ext.util.JSON
//															.decode(response.responseText);
//													Ext.Msg.alert('提示',
//															resultArray.msg);
//												},
//												params : {
//													strChecked : strChecked,
//													type : pType,
//													menuid : pMenuid
//												}
//											});
//								}
//							});
//		}

		/**
		 * 修改菜单初始化
		 */
//		function editInit() {
//			var record = grid.getSelectionModel().getSelected();
//			if (Ext.isEmpty(record)) {
//				Ext.Msg.alert('提示', '请先选择您要修改的菜单项目');
//			}else{
//			record = grid.getSelectionModel().getSelected();
//			if (record.get('menutype') == '1') {
//				Ext.Msg.alert('提示', '您选中的记录为系统内置菜单,不允许修改');
//				return;
//			}
//			if (record.get('leaf') == '0') {
//				comboxWithTree.setDisabled(true);
//			} else {
//				comboxWithTree.setDisabled(false);
//			}
//			addMenuFormPanel.getForm().loadRecord(record);
//			addMenuWindow.show();
//			addMenuWindow
//					.setTitle('<span style="font-weight:normal">修改菜单</span>');
//			Ext.getCmp('windowmode').setValue('edit');
//			Ext.getCmp('parentid_old').setValue(record.get('parentid'));
//			Ext.getCmp('count').setValue(record.get('count'));
////			Ext.getCmp('btnReset').hide();
//           }
//		}

		/**
		 * 新增菜单初始化
		 */
		
//		function addInit() {
//			// clearForm(addMenuFormPanel.getForm());
//			var flag = Ext.getCmp('windowmode').getValue();
//			if (typeof (flag) != 'undefined') {
//				addMenuFormPanel.form.getEl().dom.reset();
//			} else {
//				clearForm(addMenuFormPanel.getForm());
//			}
//			var selectModel = menuTree.getSelectionModel();
//			var selectNode = selectModel.getSelectedNode();
//			Ext.getCmp('parentmenuname').setValue(selectNode.attributes.text);
//			Ext.getCmp('parentid').setValue(selectNode.attributes.id);
//			expandedCombo.setValue('0');
//			addMenuWindow.show();
//			addMenuWindow
//					.setTitle('<span style="font-weight:normal">新增菜单</span>');
//			Ext.getCmp('windowmode').setValue('add');
////			Ext.getCmp('btnReset').show();
//		}

		/**
		 * 修改菜单数据
		 */
//		function updateMenuItem(operateParams) {
//			if (!addMenuFormPanel.form.isValid()) {
//				return;
//			}
//			var parentid = Ext.getCmp('parentid').getValue();
//			var parentid_old = Ext.getCmp('parentid_old').getValue();
//			var count = Ext.getCmp('count').getValue();
//			if (parentid != parentid_old) {
//				if (count != '0') {
//					Ext.Msg.confirm('请确认',
//							'此菜单已经做过权限分配,修改上级菜单属性将导致其权限信息丢失,继续保存吗?', function(
//									btn, text) {
//								if (btn == 'yes') {
//									update(operateParams);
//								} else {
//									return;
//								}
//							});
//				} else {
//					update(operateParams);
//				}
//			} else {
//				update(operateParams);
//			}
//
//		}

		/**
		 * 更新
		 */
		function update(operateParams) {
			var parentid = Ext.getCmp('parentid').getValue();
			var parentid_old = Ext.getCmp('parentid_old').getValue();
			addMenuFormPanel.form.submit( {
				url : './resource.ered?reqCode=updateMenuItem',
				waitTitle : '提示',
				method : 'POST',
				waitMsg : '正在处理数据,请稍候...',
				success : function(form, action) {
					addMenuWindow.hide();
					store.reload();
					refreshNode(parentid);
					if (parentid != parentid_old) {
						refreshNode(parentid_old);
					}
					form.reset();
					Ext.MessageBox.alert('提示', action.result.msg);
					insertOperateRcd(operateParams);
				},
				failure : function(form, action) {
					var msg = action.result.msg;
					Ext.MessageBox.alert('提示', '菜单数据修改失败:<br>' + msg);
				}
			});
		}
		
	});