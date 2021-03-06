/**
 * 角色管理与授权
 * 
 * @author XiongChun
 * @since 2010-04-20
 */
Ext
		.onReady(function() {
			var root = new Ext.tree.AsyncTreeNode({
				text : root_deptname,
				expanded : true,
				id : root_deptid
			});
			var deptTree = new Ext.tree.TreePanel({
				loader : new Ext.tree.TreeLoader({
					baseAttrs : {},
					dataUrl : './role.ered?reqCode=departmentTreeInit'
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
				store.load({
					params : {
						start : 0,
						limit : bbar.pageSize,
						deptid : deptid
					}
				});
			});

			var contextMenu = new Ext.menu.Menu({
				id : 'deptTreeContextMenu',
				items : [ {
					text : '新增角色',
					iconCls : 'page_addIcon',
					handler : function() {
						addInit();
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
				store.load({
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
			var cm = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer(),
							sm,
							{
								header : '权限',
								dataIndex : 'roleid',
								hidden:true,
								renderer : function(value) {
									return '<a href="javascript:void(0);"><img src="./resource/image/ext/edit2.gif" style="cursor: pointer;"/></a>';
								},
								width : 50
							}, {
								header : '角色名称',
								dataIndex : 'rolename',
								width : 150
							}, {
								id : 'deptname',
								header : '所属部门',
								dataIndex : 'deptname',
								width : 150
							}, {
								header : '角色类型',
								dataIndex : 'roletype',
								hidden:true,
								width : 80,
								renderer : function(value) {
									if (value == '1')
										return '业务角色';
									else if (value == '2')
										return '管理角色';
									else if (value == '3')
										return '系统内置角色';
									else
										return value;
								}
							}, {
								header : '角色状态',
								dataIndex : 'locked',
								width : 60,
								renderer : function(value) {
									if (value == '1')
										return '锁定';
									else if (value == '0')
										return '正常';
									else
										return value;
								}
							}, {
								header : '角色编号',
								dataIndex : 'roleid',
								hidden : false,
								width : 80,
								sortable : true
							}, {
								id : 'remark',
								header : '备注',
								dataIndex : 'remark'
							}, {
								id : 'deptid',
								header : '所属部门编号',
								dataIndex : 'deptid',
								hidden : true
							} ]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : './role.ered?reqCode=queryRolesForManage'
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'TOTALCOUNT',
					root : 'ROOT'
				}, [ {
					name : 'roleid'
				}, {
					name : 'rolename'
				}, {
					name : 'locked'
				}, {
					name : 'roletype'
				}, {
					name : 'deptid'
				}, {
					name : 'deptname'
				}, {
					name : 'remark'
				} ])
			});

			// 翻页排序时带上查询条件
			store.on('beforeload', function() {
				this.baseParams = {
					queryParam : Ext.getCmp('queryParam').getValue()
				};
			});

			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				hiddenName : 'pagesize',
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 500, '500条/页' ] ]
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
				store.reload({
					params : {
						start : 0,
						limit : bbar.pageSize
					}
				});
			});

			var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});

			var grid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">角色信息表</span>',
				iconCls : 'award_star_silver_3Icon',
				renderTo : 'roleGridDiv',
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
					text : '权限授权',
					iconCls : 'page_edit_1Icon',
					handler : function() {
						grantBtn();
					}
				}, '-', {
					text : '删除',
					iconCls : 'page_delIcon',
					handler : function() {
//						deleteRoleItems();
						authoriseRole('delete','Cmrole');
					}
				}, '->', new Ext.form.TextField({
					id : 'queryParam',
					name : 'queryParam',
					emptyText : '请输入角色名称',
					enableKeyEvents : true,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								queryRoleItem();
							}
						}
					},
					width : 130
				}), {
					text : '查询',
					iconCls : 'previewIcon',
					handler : function() {
						queryRoleItem();
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
			store.load({
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
			grid.on("cellclick",
					function(grid, rowIndex, columnIndex, e) {
						var store = grid.getStore();
						var record = store.getAt(rowIndex);
						var fieldName = grid.getColumnModel().getDataIndex(
								columnIndex);
						if (fieldName == 'roleid' && columnIndex == 2) {
							var roleid = record.get(fieldName);
							var deptid = record.get('deptid');
							var roletype = record.get('roletype');
							roleGrantInit(roleid, deptid, roletype);
						}
					});
			
			function grantBtn()
			{
				var c = grid.getSelectionModel().getSelections();
				if(c.length == 1){
					var record = grid.getSelectionModel().getSelected();
					var roleid = record.get('roleid');
					var deptid = record.get('deptid');
					var roletype = record.get('roletype');
					roleGrantInit(roleid, deptid, roletype);
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
			var addRoot = new Ext.tree.AsyncTreeNode({
				text : root_deptname,
				expanded : true,
				id : root_deptid
			});
			var addDeptTree = new Ext.tree.TreePanel({
				loader : new Ext.tree.TreeLoader({
					baseAttrs : {},
					dataUrl : './role.ered?reqCode=departmentTreeInit'
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
				Ext.getCmp("addRoleFormPanel").findById('deptid').setValue(
						node.attributes.id);
				comboxWithTree.collapse();
			});
			var comboxWithTree = new Ext.form.ComboBox(
					{
						id : 'deptname',
						store : new Ext.data.SimpleStore({
							fields : [],
							data : [ [] ]
						}),
						editable : false,
						value : ' ',
						emptyText : '请选择...',
						fieldLabel : '所属部门',
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
			var lockedStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '0', '0 正常' ], [ '1', '1 锁定' ] ]
			});
			var lockedCombo = new Ext.form.ComboBox({
				name : 'locked',
				hiddenName : 'locked',
				store : lockedStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				value : '0',
				fieldLabel : '角色状态',
				emptyText : '请选择...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				readOnly:true,
				typeAhead : true,
				anchor : "99%"
			});

			var roletypeStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 业务角色' ], [ '2', '2 管理角色' ] ]
			});
			var roletypeCombo = new Ext.form.ComboBox({
				name : 'roletype',
				hiddenName : 'roletype',
				store : roletypeStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				readOnly : true,
				value : '1',
				fieldLabel : '角色类型',
				emptyText : '请选择...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				anchor : "99%"
			});
			var addRoleFormPanel = new Ext.form.FormPanel({
				id : 'addRoleFormPanel',
				name : 'addRoleFormPanel',
				defaultType : 'textfield',
				labelAlign : 'right',
				labelWidth : 58,
				frame : false,
				bodyStyle : 'padding:5 5 0',
				items : [ {
					fieldLabel : '角色名称',
					name : 'rolename',
					id : 'rolename',
                    maxLength:50,
					allowBlank : false,
					anchor : '99%'
				}, comboxWithTree, roletypeCombo, lockedCombo, {
					fieldLabel : '备注',
					name : 'remark',
                    maxLength:200,
					allowBlank : true,
					anchor : '99%'
				}, {
					id : 'windowmode',
					name : 'windowmode',
					hidden : true
				}, {
					id : 'deptid',
					name : 'deptid',
					hidden : true
				}, {
					id : 'deptid_old',
					name : 'deptid_old',
					hidden : true
				}, {
					id : 'roleid',
					name : 'roleid',
					hidden : true
				} ]
			});

			var addRoleWindow = new Ext.Window(
					{
						layout : 'fit',
						width : 400,
						height : 203,
						resizable : false,
						draggable : true,
						closable : true,
						modal : true,
						closeAction : 'hide',
						title : '<span style="font-weight:normal">新增角色</span>',
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
						items : [ addRoleFormPanel ],
						buttons : [
								{
									text : '保存',
									iconCls : 'acceptIcon',
									handler : function() {
										if (runMode == '0') {
											Ext.Msg
													.alert('提示',
															'系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
											return;
										}
										var mode = Ext.getCmp('windowmode')
												.getValue();
										if (mode == 'add'){
//											saveRoleItem();
											authoriseRole('save','Cmrole');
										}
										else
//											updateRoleItem();
											authoriseRole('update','Cmrole');
									}
//								}, {
//									text : '重置',
//									id : 'btnReset',
//									iconCls : 'tbar_synchronizeIcon',
//									handler : function() {
//										clearForm(addRoleFormPanel.getForm());
//									}
								}, {
									text : '关闭',
									iconCls : 'deleteIcon',
									handler : function() {
										addRoleWindow.hide();
									}
								} ]
					});

			/**
			 * 布局
			 */
			var viewport = new Ext.Viewport({
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
			 * 根据条件查询角色
			 */
			function queryRoleItem() {
				store.load({
					params : {
						start : 0,
						limit : bbar.pageSize,
						queryParam : Ext.getCmp('queryParam').getValue()
					}
				});
			}

			/**
			 * 新增角色初始化
			 */
			function addInit() {
				if (login_account == parent.DEFAULT_DEVELOP_ACCOUNT) {
					roletypeCombo.setReadOnly(false);
				}
				if (login_account == parent.DEFAULT_ADMIN_ACCOUNT) {
					roletypeCombo.setReadOnly(false);
				}
				// clearForm(addRoleFormPanel.getForm());
				var flag = Ext.getCmp('windowmode').getValue();
				if (typeof (flag) != 'undefined') {
					addRoleFormPanel.form.getEl().dom.reset();
				} else {
					clearForm(addRoleFormPanel.getForm());
				}
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
				Ext.getCmp('deptname').setValue(selectNode.attributes.text);
				Ext.getCmp('deptid').setValue(selectNode.attributes.id);
				addRoleWindow.show();
				addRoleWindow
						.setTitle('<span style="font-weight:normal">新增角色</span>');
				Ext.getCmp('windowmode').setValue('add');
				comboxWithTree.setDisabled(false);
				lockedCombo.setValue('0');
				roletypeCombo.setValue('1');
//				Ext.getCmp('btnReset').show();
			}

			/**
			 * 保存角色数据
			 */
			function saveRoleItem(operateParams) {
				if (!addRoleFormPanel.form.isValid()) {
					return;
				}
				addRoleFormPanel.form.submit({
					url : './role.ered?reqCode=saveRoleItem',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						addRoleWindow.hide();
						deptid = Ext.getCmp('deptid').getValue();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('提示', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('提示', '角色数据保存失败:<br>' + msg);
					}
				});
			}

			/**
			 * 删除角色
			 */
			function deleteRoleItems(operateParams) {
				var rows = grid.getSelectionModel().getSelections();
				var fields = '';
				for ( var i = 0; i < rows.length; i++) {
					if (rows[i].get('roletype') == '3') {
						fields = fields + rows[i].get('rolename') + '<br>';
					}
				}
				if (fields != '') {
					Ext.Msg.alert('提示', '<b>您选中的项目中包含如下系统内置的只读项目</b><br>'
							+ fields + '<font color=red>系统内置角色不能删除!</font>');
					return;
				}
				if (Ext.isEmpty(rows)) {
					Ext.Msg.alert('提示', '请先选中要删除的项目!');
					return;
				}
				var strChecked = jsArray2JsString(rows, 'roleid');
				Ext.Msg
						.confirm(
								'请确认',
								'<span style="color:red"><b>提示:</b>删除角色将同时删除和角色相关的权限信息,请慎重.</span><br>继续删除吗?',
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
												.request({
													url : './role.ered?reqCode=deleteRoleItems',
													success : function(response) {
														var resultArray = Ext.util.JSON
																.decode(response.responseText);
														store.reload();
														Ext.Msg
																.alert(
																		'提示',
																		resultArray.msg);
														insertOperateRcd(operateParams);
													},
													failure : function(response) {
														var resultArray = Ext.util.JSON
																.decode(response.responseText);
														Ext.Msg
																.alert(
																		'提示',
																		resultArray.msg);
													},
													params : {
														strChecked : strChecked
													}
												});
									}
								});
			}

			/**
			 * 修改角色初始化
			 */
			function editInit() {
				if (login_account == parent.DEFAULT_DEVELOP_ACCOUNT) {
					roletypeCombo.setReadOnly(false);
				}
				if (login_account == parent.DEFAULT_ADMIN_ACCOUNT) {
					roletypeCombo.setReadOnly(false);
				}
				var record = grid.getSelectionModel().getSelected();
				if (Ext.isEmpty(record)) {
					Ext.MessageBox.alert('提示', '请先选择要修改的角色!');
				}
				if (record.get('roletype') == '3') {
					Ext.MessageBox.alert('提示', '系统内置角色,不能修改!');
					return;
				}
				addRoleFormPanel.getForm().loadRecord(record);
				addRoleWindow.show();
				addRoleWindow
						.setTitle('<span style="font-weight:normal">修改角色</span>');
				Ext.getCmp('windowmode').setValue('edit');
				Ext.getCmp('deptid_old').setValue(record.get('deptid'));
				Ext.getCmp('roleid').setValue(record.get('roleid'));
//				Ext.getCmp('btnReset').hide();
			}

			/**
			 * 修改角色数据
			 */
			function updateRoleItem(operateParams) {
				if (!addRoleFormPanel.form.isValid()) {
					return;
				}
				var deptid = Ext.getCmp('deptid').getValue();
				var deptid_old = Ext.getCmp('deptid_old').getValue();
				if (deptid != deptid_old) {
					Ext.Msg.confirm('确认', '修改所属部门将导致角色-人员映射关系丢失! 继续保存吗?',
							function(btn, text) {
								if (btn == 'yes') {
									update(operateParams);
								} else {
									return;
								}
							});
					return;
				} else {
					update(operateParams);
				}
			}

			/**
			 * 更新
			 */
			function update(operateParams) {
				addRoleFormPanel.form.submit({
					url : './role.ered?reqCode=updateRoleItem',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						addRoleWindow.hide();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('提示', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('提示', '角色数据修改失败:<br>' + msg);
					}
				});
			}

			/**
			 * 角色授权窗口初始化
			 */
			function roleGrantInit(roleid, deptid, roletype) {

				var operatorTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> 经办权限',
							// iconCls: 'user_femaleIcon',
							autoLoad : {
								url : './role.ered?reqCode=operatorTabInit',
								scripts : true,
								text : parent.PAGE_LOAD_MSG,
								params : {
									roleid : roleid,
									deptid : deptid,
									roletype : roletype
								}
							}
						});

				var selectUserTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/group.png" align="top" class="IEPNG"> 选择人员',
							// iconCls:'chart_organisationIcon',
							autoLoad : {
								url : './role.ered?reqCode=selectUserTabInit',
								scripts : true,
								text : parent.PAGE_LOAD_MSG,
								params : {
									roleid : roleid,
									deptid : deptid,
									roletype : roletype
								}
							}
						});

				var managerTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/wrench.png" align="top" class="IEPNG"> 授权权限授权',
							// iconCls: 'status_onlineIcon',
							autoLoad : {
								url : './role.ered?reqCode=managerTabInit',
								scripts : true,
								text : parent.PAGE_LOAD_MSG,
								params : {
									roleid : roleid,
									deptid : deptid,
									roletype : roletype
								}
							}
						});
				
				var useRoleManagerTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> 授权权限',
							// iconCls: 'status_onlineIcon',
							autoLoad : {
								url : './role.ered?reqCode=managerRoleTabInit',
								scripts : true,
								text : parent.PAGE_LOAD_MSG,
								params : {
									roleid : roleid,
									deptid : deptid,
									roletype : roletype
								}
							}
						});

				var roleGrantTabPanel = new Ext.TabPanel({
					activeTab : 0,
					width : 600,
					height : 250,
					plain : true,// True表示为不渲染tab候选栏上背景容器图片
					defaults : {
						autoScroll : true
					}
				});
				
				roleGrantTabPanel.add(operatorTab);
				if (login_account == parent.DEFAULT_DEVELOP_ACCOUNT) {
					roleGrantTabPanel.add(managerTab);
				}
				else if (login_account == parent.DEFAULT_ADMIN_ACCOUNT) {
					roleGrantTabPanel.add(managerTab);
				}
				roleGrantTabPanel.add(useRoleManagerTab);
				roleGrantTabPanel.add(selectUserTab);
				var roleGrantWindow = new Ext.Window({
					layout : 'fit',
					width : 500,
					height : document.body.clientHeight,
					resizable : true,
					draggable : true,
					closeAction : 'close',
					closable : true,
					title : '<span style="font-weight:normal">角色授权</span>',
					// iconCls : 'award_star_silver_3Icon',
					modal : true,
					pageY : 15,
					pageX : document.body.clientWidth / 2 - 420 / 2,
					collapsible : true,
					titleCollapse : true,
					maximizable : false,
					// animateTarget: document.body,
					// //如果使用autoLoad,建议不要使用动画效果
					buttonAlign : 'right',
					constrain : true,
					items : [ roleGrantTabPanel ],
					buttons : [ {
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							roleGrantWindow.close();
						}
					} ]
				});
				roleGrantWindow.show();
				if (roletype == '2') {
					// operatorTab.disable();
				} else if (roletype == '1') {
					managerTab.disable();
				}
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
//			userRoleStore.load();
			
			var authoriseId=new Ext.form.ComboBox({
//				id:'authoriseId',
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

		    function authoriseRole(operation,operateObject){
		    	var authoriseRoleForm = new Ext.form.FormPanel({	
		    		height:200,
		    		labelWidth : 90,
		    		frame : true,
		    		defaultType : 'textfield',
		    		labelAlign : 'right',
		    		bodyStyle : 'padding:5 5 5 5',
		    		items : [
//		    		         {
//		    			fieldLabel : '授权员工',
//		    			name : 'authoriseId',
////		    			minLength:7,
////		    			store:userRoleStore,
////		    			"xtype":"dcombo",
//		    			allowBlank : false,
//		    			anchor : '100%'
//		    		}, 
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
//		    			hidden:true,
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
		    							execObject(operation,operateParams);
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
		    	
		    	
//		    	authoriseWin.show();
//		    	alert("operation=="+operation+"    operateObject=="+operateObject);
		    	
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
//		    				   alert("tableName=="+params.tableName);
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
		    								execNoRole(operation,operateObject);//不需要授权
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
		    								execNoRole(operation,operateObject);//不需要授权
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
		    								execNoRole(operation,operateObject);//不需要授权
		    							}
		    						}
		    						else
		    						{
		    							execNoRole(operation,operateObject);//不需要授权
		    						}
		    				   }
		    			   }else{
		    				   execNoRole(operation,operateObject);//默认为不需要授权
		    			   }
		    		   }
		    	   });

		    	
		    }


		    //不授权
		    execNoRole=function(operation,operateObject)
		    {
		    	 Ext.Ajax.request({
		    		   url:_contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
		    		   params:{operateType:operation,operateObject:operateObject},
		    		   callback:function(opts,success,response){
		    			   var jsonobjs=Ext.decode(response.responseText);
		    			   if(jsonobjs.success==true)
		    			   {
		    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
		    				   execObject(operation,operateParams);
		    			   }else
		    			   {
		    				   Ext.Msg.alert('提示', jsonobjs.msg);
		    			   }
		    		   }
		    	   });
		    }

		    execObject = function(operation,operateParams){
		    	if(operation == 'save'){
		    		saveRoleItem(operateParams);
		    	}else if(operation == 'update'){
		    		updateRoleItem(operateParams);
		    	}
		    	else if(operation == 'delete'){
		    		deleteRoleItems(operateParams);
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