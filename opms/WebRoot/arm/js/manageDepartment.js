/**
 * 部门管理
 * 
 * @author XiongChun
 * @since 2010-04-11
 */
Ext.onReady(function() {
	var root = new Ext.tree.AsyncTreeNode( {
		text : root_deptname,
		expanded : true,
		id : root_deptid
	});
	var deptTree = new Ext.tree.TreePanel( {
		loader : new Ext.tree.TreeLoader( {
			baseAttrs : {},
			dataUrl : './organization.ered?reqCode=departmentTreeInit'
		}),
		root : root,
		title : '',
		applyTo : 'deptTreeDiv',
		autoScroll : false,
		animate : false,
		useArrows : false,
		border : false
	});
	deptTree.root.select();
	deptTree.on('click', function(node) {
		deptid = node.attributes.id;
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize,
				deptid : deptid
			}
		});
	});

	var contextMenu = new Ext.menu.Menu( {
		id : 'deptTreeContextMenu',
		items : [ {
			text : '新增部门',
			iconCls : 'page_addIcon',
			handler : function() {
				addInit();
			}
		}, {
			text : '修改部门',
			iconCls : 'page_edit_1Icon',
			handler : function() {
				editInit();
			}
		}, {
			text : '删除部门',
			iconCls : 'page_delIcon',
			handler : function() {
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
//				deleteDeptItems('2', selectNode.attributes.id);
				authoriseRole('delete','Eadept','2', selectNode.attributes.id);
			}
		}, {
			text : '刷新节点',
			iconCls : 'page_refreshIcon',
			handler : function() {
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
				if (selectNode.attributes.leaf) {
					selectNode.parentNode.reload();
				} else {
					selectNode.reload();
				}
			}
		} ]
	});
	deptTree.on('contextmenu', function(node, e) {
		e.preventDefault();
		deptid = node.attributes.id;
		deptname = node.attributes.text;
		Ext.getCmp('parentdeptname').setValue(deptname);
		Ext.getCmp('parentid').setValue(deptid);
		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize,
				deptid : deptid
			},
			callback : function(r, options, success) {
				for ( var i = 0; i < r.length; i++) {
					var record = r[i];
					var deptid_g = record.data.deptid;
					if (deptid_g == deptid) {
						grid.getSelectionModel().selectRow(i);
					}
				}
			}
		});
		node.select();
		contextMenu.showAt(e.getXY());
	});

	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel( [ new Ext.grid.RowNumberer(), sm, {
		header : '部门名称',
		dataIndex : 'deptname',
		width : 130
	}, {
		header : '业务对照码',
		dataIndex : 'customid',
		hidden:true,
		width : 100
	}, {
		header : '上级部门',
		dataIndex : 'parentdeptname',
		width : 130
	}, {
		header : '排序号',
		dataIndex : 'sortno',
		sortable : true,
		width : 50
	}, {
		header : '节点类型',
		dataIndex : 'leaf',
		hidden : true,
		renderer : function(value) {
			if (value == '1')
				return '叶子节点';
			else if (value == '0')
				return '树枝节点';
			else
				return value;
		}
	}, {
		id : 'parentid',
		header : '父节点编号',
		hidden : true,
		dataIndex : 'parentid'
	}, {
		id : 'usercount',
		header : '下属用户数目',
		hidden : true,
		dataIndex : 'usercount'
	}, {
		id : 'rolecount',
		header : '下属角色数目',
		hidden : true,
		dataIndex : 'rolecount'
	}, {
		header : '部门编号',
		dataIndex : 'deptid',
		hidden : false,
		hidden : false,
		width : 130,
		sortable : true
	}, {
		id : 'remark',
		header : '备注',
		dataIndex : 'remark'
	} ]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store( {
		proxy : new Ext.data.HttpProxy( {
			url : './organization.ered?reqCode=queryDeptsForManage'
		}),
		reader : new Ext.data.JsonReader( {
			totalProperty : 'TOTALCOUNT',
			root : 'ROOT'
		}, [ {
			name : 'deptid'
		}, {
			name : 'deptname'
		}, {
			name : 'sortno'
		}, {
			name : 'customid'
		}, {
			name : 'parentdeptname'
		}, {
			name : 'leaf'
		}, {
			name : 'remark'
		}, {
			name : 'parentid'
		}, {
			name : 'usercount'
		}, {
			name : 'rolecount'
		} ])
	});

	// 翻页排序时带上查询条件
		store.on('beforeload', function() {
			this.baseParams = {
				queryParam : Ext.getCmp('queryParam').getValue()
			};
		});

		var pagesize_combo = new Ext.form.ComboBox( {
			name : 'pagesize',
			hiddenName : 'pagesize',
			typeAhead : true,
			triggerAction : 'all',
			lazyRender : true,
			mode : 'local',
			store : new Ext.data.ArrayStore(
					{
						fields : [ 'value', 'text' ],
						data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
								[ 50, '50条/页' ], [ 100, '100条/页' ],
								[ 250, '250条/页' ], [ 500, '500条/页' ] ]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '50',
			editable : false,
			width : 85
		});
		var number = parseInt(pagesize_combo.getValue());
		pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(comboBox.getValue());
			number = parseInt(comboBox.getValue());
			store.reload( {
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		});

		var bbar = new Ext.PagingToolbar( {
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
		});
		var grid = new Ext.grid.GridPanel( {
			title : '<span style="font-weight:normal">部门信息表</span>',
			iconCls : 'buildingIcon',
			renderTo : 'deptGridDiv',
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
			autoExpandColumn : 'remark',
			cm : cm,
			sm : sm,
			tbar : [ {
				text : '新增',
				iconCls : 'page_addIcon',
				handler : function() {
					addInit();
				}
			}, '-', {
				text : '修改',
				iconCls : 'page_edit_1Icon',
				handler : function() {
					editInit();
				}
			}, '-', {
				text : '删除',
				iconCls : 'page_delIcon',
				handler : function() {
//					deleteDeptItems('1', '');
					authoriseRole('delete','Eadept','1', '');
				}
			}, '->', new Ext.form.TextField( {
				id : 'queryParam',
				name : 'queryParam',
				emptyText : '请输入部门名称',
				enableKeyEvents : true,
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							queryDeptItem();
						}
					}
				},
				width : 130
			}), {
				text : '查询',
				iconCls : 'previewIcon',
				handler : function() {
					queryDeptItem();
				}
			}, '-', {
				text : '刷新',
				iconCls : 'arrow_refreshIcon',
				handler : function() {
					store.reload();
				}
			} ],
			bbar : bbar
		});

		store.load( {
			params : {
				start : 0,
				limit : bbar.pageSize,
				firstload : 'true'
			}
		});

		grid.on('rowdblclick', function(grid, rowIndex, event) {
			editInit();
		});
		grid.on('sortchange', function() {
			// grid.getSelectionModel().selectFirstRow();
			});

		bbar.on("change", function() {
			// grid.getSelectionModel().selectFirstRow();
			});

		var addRoot = new Ext.tree.AsyncTreeNode( {
			text : root_deptname,
			expanded : true,
			id : root_deptid
		});
		var addDeptTree = new Ext.tree.TreePanel( {
			loader : new Ext.tree.TreeLoader( {
				baseAttrs : {},
				dataUrl : './organization.ered?reqCode=departmentTreeInit'
			}),
			root : addRoot,
			autoScroll : true,
			animate : false,
			useArrows : false,
			border : false
		});
		// 监听下拉树的节点单击事件
		addDeptTree.on('click', function(node) {
			comboxWithTree.setValue(node.text);
			Ext.getCmp("addDeptFormPanel").findById('parentid').setValue(
					node.attributes.id);
			comboxWithTree.collapse();
		});
		var comboxWithTree = new Ext.form.ComboBox(
				{
					id : 'parentdeptname',
					store : new Ext.data.SimpleStore( {
						fields : [],
						data : [ [] ]
					}),
					editable : false,
					value : ' ',
					emptyText : '请选择...',
					fieldLabel : '上级部门',
					anchor : '100%',
					mode : 'local',
					triggerAction : 'all',
					maxHeight : 390,
					// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
					tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv'></div></div></tpl>",
					allowBlank : false,
					onSelect : Ext.emptyFn
				});
		// 监听下拉框的下拉展开事件
		comboxWithTree.on('expand', function() {
			// 将UI树挂到treeDiv容器
				addDeptTree.render('addDeptTreeDiv');
				// addDeptTree.root.expand(); //只是第一次下拉会加载数据
				addDeptTree.root.reload(); // 每次下拉都会加载数据

			});
		var addDeptFormPanel = new Ext.form.FormPanel( {
			id : 'addDeptFormPanel',
			name : 'addDeptFormPanel',
			defaultType : 'textfield',
			labelAlign : 'right',
			labelWidth : 65,
			frame : false,
			bodyStyle : 'padding:5 5 0',
			items : [ {
				fieldLabel : '部门名称',
				name : 'deptname',
				id : 'deptname',
				allowBlank : false,
                maxLength:50,
				anchor : '99%'
			}, comboxWithTree, {
				fieldLabel : '业务对照码',
				name : 'customid',
                maxLength:20,
				allowBlank : true,
				hidden:true,
				anchor : '99%'
			}, {
				fieldLabel : '排序号',
				name : 'sortno',
                maxLength:4,
				allowBlank : true,
				anchor : '99%'
			}, {
				fieldLabel : '备注',
				name : 'remark',
                maxLength:100,
				allowBlank : true,
				anchor : '99%'
			}, {
				id : 'parentid',
				name : 'parentid',
				hidden : true
			}, {
				id : 'windowmode',
				name : 'windowmode',
				hidden : true
			}, {
				id : 'deptid',
				name : 'deptid',
				hidden : true
			}, {
				id : 'parentid_old',
				name : 'parentid_old',
				hidden : true
			} ]
		});
		var addDeptWindow = new Ext.Window( {
			layout : 'fit',
			width : 400,
			height : 220,
			resizable : false,
			draggable : true,
			closable : true,
			modal : true,
			closeAction : 'hide',
			title : '<span style="font-weight:normal">新增部门</span>',
			// iconCls : 'page_addIcon',
			collapsible : true,
			titleCollapse : true,
			maximizable : false,
			buttonAlign : 'right',
			border : false,
			animCollapse : true,
			pageY : 20,
			pageX : document.body.clientWidth / 2 - 420 / 2,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [ addDeptFormPanel ],
			buttons : [
					{
						text : '保存',
						iconCls : 'acceptIcon',
						id : 'btn_id_save_update',
						handler : function() {
							if (runMode == '0') {
								Ext.Msg.alert('提示',
										'系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
								return;
							}
							var mode = Ext.getCmp('windowmode').getValue();
							if (mode == 'add'){
//								saveDeptItem();
								authoriseRole('save','Eadept','', '');
							}
							else{
//								updateDeptItem();
								authoriseRole('update','Eadept','', '');
							}
						}
//					}, {
//						text : '重置',
//						id : 'btnReset',
//						iconCls : 'tbar_synchronizeIcon',
//						handler : function() {
//							clearForm(addDeptFormPanel.getForm());
//						}
					}, {
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							addDeptWindow.hide();
						}
					} ]
		});
		/**
		 * 布局
		 */
		var viewport = new Ext.Viewport( {
			layout : 'border',
			items : [ {
				title : '<span style="font-weight:normal">组织机构</span>',
				iconCls : 'chart_organisationIcon',
				tools : [ {
					id : 'refresh',
					handler : function() {
						deptTree.root.reload()
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
				items : [ deptTree ]
			}, {
				region : 'center',
				layout : 'fit',
				items : [ grid ]
			} ]
		});

		/**
		 * 根据条件查询部门
		 */
		function queryDeptItem() {
			store.load( {
				params : {
					start : 0,
					limit : bbar.pageSize,
					queryParam : Ext.getCmp('queryParam').getValue()
				}
			});
		}

		/**
		 * 新增部门初始化
		 */
		function addInit() {
			// clearForm(addDeptFormPanel.getForm());
			var flag = Ext.getCmp('windowmode').getValue();
			if (typeof (flag) != 'undefined') {
				addDeptFormPanel.form.getEl().dom.reset();
			} else {
				clearForm(addDeptFormPanel.getForm());
			}
			var selectModel = deptTree.getSelectionModel();
			var selectNode = selectModel.getSelectedNode();
			Ext.getCmp('parentdeptname').setValue(selectNode.attributes.text);
			Ext.getCmp('parentid').setValue(selectNode.attributes.id);
			addDeptWindow.show();
			addDeptWindow
					.setTitle('<span style="font-weight:normal">新增部门</span>');
			Ext.getCmp('windowmode').setValue('add');
			comboxWithTree.setDisabled(false);
//			Ext.getCmp('btnReset').show();
		}

		/**
		 * 保存部门数据
		 */
		function saveDeptItem(operateParams) {
			if (!addDeptFormPanel.form.isValid()) {
				return;
			}
			addDeptFormPanel.form.submit( {
				url : './organization.ered?reqCode=saveDeptItem',
				waitTitle : '提示',
				method : 'POST',
				waitMsg : '正在处理数据,请稍候...',
				success : function(form, action) {
					addDeptWindow.hide();
					store.reload();
					refreshNode(Ext.getCmp('parentid').getValue());
					form.reset();
					Ext.MessageBox.alert('提示', action.result.msg);
					insertOperateRcd(operateParams);
				},
				failure : function(form, action) {
					var msg = action.result.msg;
					Ext.MessageBox.alert('提示', '部门数据保存失败:<br>' + msg);
				}
			});
		}

		/**
		 * 刷新指定节点
		 */
		function refreshNode(nodeid) {
			var node = deptTree.getNodeById(nodeid);
			/* 异步加载树在没有展开节点之前是获取不到对应节点对象的 */
			if (Ext.isEmpty(node)) {
				deptTree.root.reload();
				return;
			}
			if (node.attributes.leaf) {
				node.parentNode.reload();
			} else {
				node.reload();
			}
		}

		/**
		 * 修改部门初始化
		 */
		function editInit() {
			var record = grid.getSelectionModel().getSelected();
			if (Ext.isEmpty(record)) {
				Ext.MessageBox.alert('提示', '请先选择要修改的部门!');
			}
			record = grid.getSelectionModel().getSelected();
			if (record.get('leaf') == '0' || record.get('usercount') != '0'
					|| record.get('rolecount') != '0') {
				comboxWithTree.setDisabled(true);
			} else {
				comboxWithTree.setDisabled(false);
			}
			if (record.get('deptid') == '001') {
				var a = Ext.getCmp('parentdeptname');
				a.emptyText = '已经是顶级部门';
			} else {
			}
			addDeptFormPanel.getForm().loadRecord(record);
			addDeptWindow.show();
			addDeptWindow
					.setTitle('<span style="font-weight:normal">修改部门</span>');
			Ext.getCmp('windowmode').setValue('edit');
			Ext.getCmp('parentid_old').setValue(record.get('parentid'));
//			Ext.getCmp('btnReset').hide();
		}

		/**
		 * 修改部门数据
		 */
		function updateDeptItem(operateParams) {
			if (!addDeptFormPanel.form.isValid()) {
				return;
			}
			update(operateParams);
		}

		/**
		 * 更新
		 */
		function update(operateParams) {
			var parentid = Ext.getCmp('parentid').getValue();
			var parentid_old = Ext.getCmp('parentid_old').getValue();
			addDeptFormPanel.form.submit( {
				url : './organization.ered?reqCode=updateDeptItem',
				waitTitle : '提示',
				method : 'POST',
				waitMsg : '正在处理数据,请稍候...',
				success : function(form, action) {
					addDeptWindow.hide();
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
					Ext.MessageBox.alert('提示', '部门数据修改失败:<br>' + msg);
				}
			});
		}

		/**
		 * 删除部门
		 */
		function deleteDeptItems(pType, pDeptid,operateParams) {
			var rows = grid.getSelectionModel().getSelections();
			var fields = '';
			for ( var i = 0; i < rows.length; i++) {
				if (rows[i].get('deptid') == '001') {
					fields = fields + rows[i].get('deptname') + '<br>';
				}
			}
			if (fields != '') {
				Ext.Msg
						.alert(
								'提示',
								'<b>您选中的项目中包含如下系统内置的只读项目</b><br>' + fields + '<font color=red>只读项目不能删除!</font>');
				return;
			}
			if (Ext.isEmpty(rows)) {
				if (pType == '1') {
					Ext.Msg.alert('提示', '请先选中要删除的项目!');
					return;
				}
			}
			var strChecked = jsArray2JsString(rows, 'deptid');
			Ext.Msg
					.confirm(
							'请确认',
							'<span style="color:red"><b>提示:</b>删除部门将同时删除部门下属人员和角色以及它们的权限信息,请慎重.</span><br>继续删除吗?',
							function(btn, text) {
								if (btn == 'yes') {
									if (runMode == '0') {
										Ext.Msg
												.alert('提示',
														'系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
										return;
									}
									showWaitMsg();
									Ext.Ajax
											.request( {
												url : './organization.ered?reqCode=deleteDeptItems',
												success : function(response) {
													var resultArray = Ext.util.JSON
															.decode(response.responseText);
													store.reload();
													if (pType == '1') {
														deptTree.root.reload();
													} else {
														deptTree.root.reload();
													}
													Ext.Msg.alert('提示',
															resultArray.msg);
													insertOperateRcd(operateParams);
												},
												failure : function(response) {
													var resultArray = Ext.util.JSON
															.decode(response.responseText);
													Ext.Msg.alert('提示',
															resultArray.msg);
												},
												params : {
													strChecked : strChecked,
													type : pType,
													deptid : pDeptid
												}
											});
								}
							});
		}
		
		
		/*
	     * 授权模块
	     * */
	    
	    var userRoleStore = new Ext.data.Store({
			 baseParams:{valueflag:'1',valueflagvalue:':'},
	      proxy:new Ext.data.HttpProxy({url:_contextPath_ + '/common/codeSelect_getUserRole.action'}),
	      reader: new Ext.data.JsonReader({}, [
	        {name: "code"},
	        {name: "codedesc"}
	      ]),
	      remoteSort:false
	      });
//		userRoleStore.load();
		
		var authoriseId=new Ext.form.ComboBox({
//			id:'authoriseId',
			name : 'authoriseId',
			hiddenName : 'authoriseId',
			fieldLabel : '授权员工',
			emptyText : '请选择',
			triggerAction : 'all',
			store:userRoleStore,
			displayField : 'codedesc',
			valueField : 'code',
			mode : 'local',
			forceSelection : false, // 选中内容必须为下拉列表的子项
			editable : false,
			typeAhead : true,
			resizable : true,
			allowBlank : false,
			anchor : '100%'
		});

	    function authoriseRole(operation,operateObject,pType, pDeptid){
	    	var authoriseRoleForm = new Ext.form.FormPanel({	
	    		height:200,
	    		labelWidth : 90,
	    		frame : true,
	    		defaultType : 'textfield',
	    		labelAlign : 'right',
	    		bodyStyle : 'padding:5 5 5 5',
	    		items : [
//	    		         {
//	    			fieldLabel : '授权员工',
//	    			name : 'authoriseId',
////	    			minLength:7,
////	    			store:userRoleStore,
////	    			"xtype":"dcombo",
//	    			allowBlank : false,
//	    			anchor : '100%'
//	    		}, 
	    		authoriseId
	    		,
	    		{
	    			fieldLabel : '授权密码',
	    			name : 'authorisePwd',
	    			inputType : 'password',
	    			allowBlank : false,
	    			anchor : '100%'
	    		}, {
	    			fieldLabel : '操作类型',
	    			name : 'operateType',
	    			value:operation,
	    			hidden:true,
	    			anchor : '100%'
	    		}, {
	    			fieldLabel : '操作对象',
	    			name : 'operateObject',
	    			value:operateObject,
	    			hidden:true,
	    			anchor : '100%'
	    		}/*, {
	    			fieldLabel : '操作信息',
	    			name : 'operateInfo',
//	    			hidden:true,
	    			anchor : '100%'
	    		}*/]	
	    	});
	    	
	    	var authoriseRoleWin = new Ext.Window({
	    		title : '操作授权',
	    		layout : 'fit',
	    		width : 300,
	    		height : 220,
	    		collapsible : true,
	    		maximizable : true,
	    		modal : true, 
	    		closable : true,
	    		animCollapse : true,
	    		closeAction : 'hide',
	    		border : false,
	    		constrain : true,
	    		pageY : 40,
	    		pageX : document.body.clientWidth / 2 - 380 / 2,
	    		items : [authoriseRoleForm],
	    		buttons : [{
	    			text : '授权',
	    			iconCls : 'keyIcon',
	    			handler : function execute(){
	    				if(authoriseRoleForm.getForm().isValid()){
	    					authoriseRoleForm.form.submit({
	    						url : _contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
	    						success : function(form, action) {
	    							authoriseRoleWin.hide();
	    							var result = Ext.util.JSON.decode(action.response.responseText);
	    							var operateParams = Ext.util.JSON.encode(result.msg);
	    							execObject(operation,operateParams,pType, pDeptid);
	    						},
	    						failure : function(form, action) {
	    							var result = Ext.util.JSON.decode(action.response.responseText);
	    							authoriseRoleWin.hide();
	    							Ext.MessageBox.alert('提示', result.msg);
	    						},
	    						waitTitle:"请稍后",
	            				waitMsg:"授权验证中……"
	    					});
	    				}
	    			}
	    		},{
	    			text : '取消',
	    			iconCls : 'lockIcon',
	    			handler : function cancel(){
	    				authoriseRoleWin.hide();
	    			}
	    		}]
	    	});
	    	
	    	
//	    	authoriseWin.show();
//	    	alert("operation=="+operation+"    operateObject=="+operateObject);
	    	
	    	//查询菜单权限表
	    	 Ext.Ajax.request({
	    		   url:_contextPath_ + '/jsp/conmg/Eaoperaterole_getRole.action',
	    		   params:{operateObject:operateObject},
	    		   callback:function(opts,success,response){
	    			   var jsonobjs=Ext.decode(response.responseText);
	    			   if(jsonobjs.success==true)
	    			   {
	    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
	    				   var params = Ext.util.JSON.decode(operateParams);
//	    				   alert("tableName=="+params.tableName);
	    				   if(params.tableName==operateObject)
	    				   {
	    					   var insertFlag=params.insertFlag;
	    					   var updateFlag=params.updateFlag;
	    					   var deleteFlag=params.deleteFlag;
	    					    if(operation == 'save'){
	    							if(insertFlag==1){
	    								userRoleStore.load({
	    									params : {
	    										tableName : params.tableName,
	    										valueflag:'1',
	    										valueflagvalue:':'
	    									}
	    								});
	    								authoriseRoleWin.show();//需要授权
	    							}
	    							else{
	    								execNoRole(operation,operateObject,pType, pDeptid);//不需要授权
	    							}
	    						}else if(operation == 'update'){
	    							if(updateFlag==1){
	    								userRoleStore.load({
	    									params : {
	    										tableName : params.tableName,
	    										valueflag:'1',
	    										valueflagvalue:':'
	    									}
	    								});
	    								authoriseRoleWin.show();//需要授权
	    							}
	    							else{
	    								execNoRole(operation,operateObject,pType, pDeptid);//不需要授权
	    							}
	    						}
	    						else if(operation == 'delete'){
	    							if(deleteFlag==1){
	    								userRoleStore.load({
	    									params : {
	    										tableName : params.tableName,
	    										valueflag:'1',
	    										valueflagvalue:':'
	    									}
	    								});
	    								authoriseRoleWin.show();//需要授权
	    							}
	    							else{
	    								execNoRole(operation,operateObject,pType, pDeptid);//不需要授权
	    							}
	    						}else
	    						{
	    							execNoRole(operation,operateObject,pType, pDeptid);//不需要授权
	    						}
	    				   }
	    			   }else{
	    				   execNoRole(operation,operateObject,pType, pDeptid);//默认为不需要授权
	    			   }
	    		   }
	    	   });

	    	
	    }


	    //不授权
	    execNoRole=function(operation,operateObject,pType, pDeptid)
	    {
	    	 Ext.Ajax.request({
	    		   url:_contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
	    		   params:{operateType:operation,operateObject:operateObject},
	    		   callback:function(opts,success,response){
	    			   var jsonobjs=Ext.decode(response.responseText);
	    			   if(jsonobjs.success==true)
	    			   {
	    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
	    				   execObject(operation,operateParams,pType, pDeptid);
	    			   }else
	    			   {
	    				   Ext.Msg.alert('提示', jsonobjs.msg);
	    			   }
	    		   }
	    	   });
	    }

	    execObject = function(operation,operateParams,pType, pDeptid){
	    	if(operation == 'save'){
	    		saveDeptItem(operateParams);
	    	}else if(operation == 'update'){
	    		updateDeptItem(operateParams);
	    	}
	    	else if(operation == 'delete'){
	    		deleteDeptItems(pType, pDeptid,operateParams);
	    	}
	    }
	    
	  //记录操作
	    insertOperateRcd = function(operateParams){
	    	Ext.Ajax.request({
	        	url: _contextPath_ + '/jsp/conmg/Eaoperatercd_save.action',
	    		params:{params:operateParams},
	    		success : function(response) {
	    		},
	    		failure : function(form, action) {
	    		}
	        });
	    }
	    /*
	     * 授权模块
	     * */

	});