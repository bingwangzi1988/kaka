/**
 * 用户管理与授权
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
					dataUrl : './user.ered?reqCode=departmentTreeInit'
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
					text : '新增人员',
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
			
			// 机构代码
			var brcStore = new Ext.data.Store({
				baseParams : {
					valueflag : '1',
					valueflagvalue : ':',
					orderby:'branch',
					ordertype:'asc'
				},
		        proxy:new Ext.data.HttpProxy({url:_contextPath_ + '/jsp/conmg/Cmbank_getBrchCode.action'}),
		        reader: new Ext.data.JsonReader({}, [
		          {name: "code"},
		          {name: "codedesc"}
		        ]),
		        remoteStore:false
		        });
			brcStore.load();

			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer(),
							sm,
							{
								header : '权限',
								hidden:true,
								dataIndex : 'userid',
								renderer : function(value) {
									return '<a k="javascript:void(0);"><img src="./resource/image/ext/edit2.gif" style="cursor: pointer;"/></a>';
								},
								width : 50
							}, {
								header : '姓名',
								dataIndex : 'username',
								width : 80
							}, {
								header : '登录帐户',
								dataIndex : 'account',
								width : 130
							}, {
								id : 'deptname',
								header : '所属部门',
								dataIndex : 'deptname',
								width : 130
							}, {
								header : '性别',
								dataIndex : 'sex',
								width : 60,
								renderer : function(value) {
									if (value == '1')
										return '男';
									else if (value == '2')
										return '女';
									else if (value == '0')
										return '未知';
									else
										return value;
								}
							}, {
								header : '人员状态',
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
								id : 'usertype',
								header : '人员类型',
								hidden:true,
								dataIndex : 'usertype',
								width : 60,
								renderer : function(value) {
									if (value == '1')
										return '经办员';
									else if (value == '2')
										return '管理员';
									else if (value == '3')
										return '系统人员';
									else if (value == '4')
										return '项目主页注册用户';
									else
										return value;
								}
							}, {
								header : '人员编号',
								dataIndex : 'userid',
								hidden : false,
								width : 80,
								sortable : true
							}, 
							{
								header : '机构',
								dataIndex : 'branch',
								hidden : false,
								width : 80,
								sortable : true,
								renderer : function(value) {
									var returnValue;
									brcStore.each(function(record) {
										if (record.get('code') != value) {
										} else {
											returnValue = record.get('codedesc');
											return false;
										}
									});
									return returnValue;
								}
							},{
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
					url : './user.ered?reqCode=queryUsersForManage'
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'TOTALCOUNT',
					root : 'ROOT'
				}, [ {
					name : 'userid'
				}, {
					name : 'username'
				}, {
					name : 'sex'
				}, {
					name : 'account'
				}, {
					name : 'locked'
				}, {
					name : 'deptid'
				}, {
					name : 'deptname'
				}, {
					name : 'branch'
				}, {
					name : 'remark'
				}, {
					name : 'usertype'
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
				title : '<span style="font-weight:normal">人员信息表</span>',
				iconCls : 'groupIcon',
				renderTo : 'userGridDiv',
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
//						deleteUserItems();
						var rows = grid.getSelectionModel().getSelections();
						var fields = '';
						for ( var i = 0; i < rows.length; i++) {
							if (rows[i].get('usertype') == '2' && login_account != parent.DEFAULT_DEVELOP_ACCOUNT) {
								fields = fields + rows[i].get('username') + '<br>';
							}
						}
						if (fields != '') {
							Ext.Msg.alert('提示', '<b>您选中的项目中包含如下系统内置的只读项目</b><br>'
									+ fields + '<font color=red>系统内置人员不能删除!</font>');
							return;
						}
						if (Ext.isEmpty(rows)) {
							Ext.Msg.alert('提示', '请先选中要删除的项目!');
							return;
						}
						authoriseRole('delete','Eauser');
					}
				}, '->', new Ext.form.TextField({
					id : 'queryParam',
					name : 'queryParam',
					emptyText : '请输入人员名称',
					enableKeyEvents : true,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								queryUserItem();
							}
						}
					},
					width : 130
				}), {
					text : '查询',
					iconCls : 'previewIcon',
					handler : function() {
						queryUserItem();
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
			grid.on("cellclick",
					function(grid, rowIndex, columnIndex, e) {
						var store = grid.getStore();
						var record = store.getAt(rowIndex);
						var fieldName = grid.getColumnModel().getDataIndex(
								columnIndex);
						if (fieldName == 'userid' && columnIndex == 2) {
							var userid = record.get('userid');
							var deptid = record.get('deptid');
							var usertype = record.get('usertype');
							userGrantInit(userid, deptid, usertype);
						}
					});
			function grantBtn()
			{
				var c = grid.getSelectionModel().getSelections();
				if(c.length == 1){
					var record = grid.getSelectionModel().getSelected();
					var usertype = record.get('usertype');
					var account=record.get('account');
					if (usertype == '2' && login_account != parent.DEFAULT_DEVELOP_ACCOUNT) {
						Ext.Msg.alert("警告","系统内置人员不能修改");
					}else{
						var userid = record.get('userid');
						var deptid = record.get('deptid');
						userGrantInit(userid, deptid, usertype);
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

			bbar.on("change", function() {
				// grid.getSelectionModel().selectFirstRow();
			});

			var addRoot = new Ext.tree.AsyncTreeNode({
				text : root_deptname,
				expanded : true,
				id : root_deptid
			});
			var addDeptTree = new Ext.tree.TreePanel({
				loader : new Ext.tree.TreeLoader({
					baseAttrs : {},
					dataUrl : './user.ered?reqCode=departmentTreeInit'
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
				Ext.getCmp("addUserFormPanel").findById('deptid').setValue(
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

			var sexStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 男' ], [ '2', '2 女' ], [ '0', '0 未知' ] ]
			});
			var sexCombo = new Ext.form.ComboBox({
				name : 'sex',
				hiddenName : 'sex',
				store : sexStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				value : '0',
				fieldLabel : '性别',
				emptyText : '请选择...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				anchor : "99%"
			});

			var usertypeStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 经办员' ], [ '2', '2 管理员' ] ]
			});
			var usertypeCombo = new Ext.form.ComboBox({
				name : 'usertype',
				hiddenName : 'usertype',
				store : usertypeStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				value : '1',
				fieldLabel : '人员类型',
				emptyText : '请选择...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				readOnly : true,
				anchor : "99%"
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
				fieldLabel : '人员状态',
				emptyText : '请选择...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				anchor : "99%"
			});
			
			var branchCombo = new Ext.form.ComboBox({
				name : 'branch',
				hiddenName : 'branch',
				store : brcStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'code',
				displayField : 'codedesc',
				value : '0',
				fieldLabel : '机构',
				emptyText : '请选择...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				anchor : "99%"
			});

			var addUserFormPanel = new Ext.form.FormPanel({
				id : 'addUserFormPanel',
				name : 'addUserFormPanel',
				defaultType : 'textfield',
				labelAlign : 'right',
				labelWidth : 65,
				frame : false,
				bodyStyle : 'padding:5 5 0',
				items : [ {
					fieldLabel : '人员名称',
					name : 'username',
					id : 'username',
                    maxLength:20,
					allowBlank : false,
					anchor : '99%'
				}, comboxWithTree, {
					fieldLabel : '登录帐户',
					name : 'account',
					id : 'account',
                    maxLength:30,
					allowBlank : false,
					anchor : '99%'
				}, {
					fieldLabel : '密码',
					name : 'password',
					id : 'password',
					inputType : 'password',
					allowBlank : false,
                    maxLength:50,
					anchor : '99%'
				}, {
					fieldLabel : '确认密码',
					name : 'password1',
					id : 'password1',
					inputType : 'password',
                    maxLength:50,
					allowBlank : false,
					anchor : '99%'
				}, usertypeCombo, lockedCombo, sexCombo,branchCombo, {
					fieldLabel : '备注',
					name : 'remark',
					allowBlank : true,
                    maxLength:50,
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
					id : 'userid',
					name : 'userid',
					hidden : true
				}, {
					id : 'updatemode',
					name : 'updatemode',
					hidden : true
				} ]
			});

			var addUserWindow = new Ext.Window(
					{
						layout : 'fit',
						width : 400,
						height : 350,
						resizable : false,
						draggable : true,
						closeAction : 'hide',
						modal : true,
						title : '<span style="font-weight:normal">新增人员</span>',
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
						items : [ addUserFormPanel ],
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
//											saveUserItem();
											authoriseRole('save','Eauser');
										}
										else{
//											updateUserItem();
											authoriseRole('update','Eauser');
										}
									}
//								}, {
//									text : '重置',
//									id : 'btnReset',
//									iconCls : 'tbar_synchronizeIcon',
//									handler : function() {
//										clearForm(addUserFormPanel.getForm());
//									}
								}, {
									text : '关闭',
									iconCls : 'deleteIcon',
									handler : function() {
										addUserWindow.hide();
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
			 * 根据条件查询人员
			 */
			function queryUserItem() {
				store.load({
					params : {
						start : 0,
						limit : bbar.pageSize,
						queryParam : Ext.getCmp('queryParam').getValue()
					}
				});
			}

			/**
			 * 新增人员初始化
			 */
			function addInit() {
				if (login_account == parent.DEFAULT_DEVELOP_ACCOUNT) {
					usertypeCombo.setReadOnly(false);
				}
				if (login_account == parent.DEFAULT_ADMIN_ACCOUNT) {
					usertypeCombo.setReadOnly(false);
				}
				// clearForm(addUserFormPanel.getForm());
				var flag = Ext.getCmp('windowmode').getValue();
				if (typeof (flag) != 'undefined') {
					addUserFormPanel.form.getEl().dom.reset();
				} else {
					clearForm(addUserFormPanel.getForm());
				}
				var selectModel = deptTree.getSelectionModel();
				var selectNode = selectModel.getSelectedNode();
				Ext.getCmp('deptname').setValue(selectNode.attributes.text);
				Ext.getCmp('deptid').setValue(selectNode.attributes.id);
				addUserWindow.show();
				addUserWindow
						.setTitle('<span style="font-weight:normal">新增人员</span>');
				Ext.getCmp('windowmode').setValue('add');
				Ext.getCmp('account').setReadOnly(false);
				comboxWithTree.setDisabled(false);
//				Ext.getCmp('btnReset').show();
				lockedCombo.setValue('0');
				usertypeCombo.setValue('1');
				sexCombo.setValue('0');
			}

			/**
			 * 保存人员数据
			 */
			function saveUserItem(operateParams) {
				if (!addUserFormPanel.form.isValid()) {
					return;
				}
				password1 = Ext.getCmp('password1').getValue();
				password = Ext.getCmp('password').getValue();
				if (password1 != password) {
					Ext.Msg.alert('提示', '两次输入的密码不匹配,请重新输入!');
					Ext.getCmp('password').setValue('');
					Ext.getCmp('password1').setValue('');
					return;
				}
				addUserFormPanel.form.submit({
					url : './user.ered?reqCode=saveUserItem',
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						addUserWindow.hide();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('提示', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('提示', '人员数据保存失败:<br>' + msg);
					}
				});
			}

			/**
			 * 删除人员
			 */
			function deleteUserItems(operateParams) {
				var rows = grid.getSelectionModel().getSelections();
				var fields = '';
				for ( var i = 0; i < rows.length; i++) {
					if (rows[i].get('usertype') == '3') {
						fields = fields + rows[i].get('username') + '<br>';
					}
				}
				if (fields != '') {
					Ext.Msg.alert('提示', '<b>您选中的项目中包含如下系统内置的只读项目</b><br>'
							+ fields + '<font color=red>系统内置人员不能删除!</font>');
					return;
				}
				if (Ext.isEmpty(rows)) {
					Ext.Msg.alert('提示', '请先选中要删除的项目!');
					return;
				}
				var strChecked = jsArray2JsString(rows, 'userid');
				Ext.Msg
						.confirm(
								'请确认',
								'<span style="color:red"><b>提示:</b>删除人员将同时删除和人员相关的权限信息,请慎重.</span><br>继续删除吗?',
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
													url : './user.ered?reqCode=deleteUserItems',
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
			 * 修改人员初始化
			 */
			function editInit() {
				if (login_account == parent.DEFAULT_DEVELOP_ACCOUNT) {
					usertypeCombo.setReadOnly(false);
				}
				if (login_account == parent.DEFAULT_ADMIN_ACCOUNT) {
					usertypeCombo.setReadOnly(false);
				}
				var record = grid.getSelectionModel().getSelected();
				if (Ext.isEmpty(record)) {
					Ext.MessageBox.alert('提示', '请先选择要修改的项目!');
				}
				if (record.get('usertype') == '3') {
					Ext.MessageBox.alert('提示', '系统内置人员,不能修改!');
					return;
				}
				var isReadOnly=false;
				if (record.get('usertype') == '2' && login_account != parent.DEFAULT_DEVELOP_ACCOUNT) {
					isReadOnly=true;
				}
				Ext.getCmp('username').setReadOnly(isReadOnly);
				comboxWithTree.setDisabled(isReadOnly);
				lockedCombo.setDisabled(isReadOnly);
				sexCombo.setDisabled(isReadOnly);
				branchCombo.setDisabled(isReadOnly);
				
				
				addUserFormPanel.getForm().loadRecord(record);
				addUserWindow.show();
				addUserWindow
						.setTitle('<span style="font-weight:normal">修改人员</span>');
				Ext.getCmp('windowmode').setValue('edit');
				Ext.getCmp('deptid_old').setValue(record.get('deptid'));
				Ext.getCmp('password').setValue('@@@@@@');
				Ext.getCmp('password1').setValue('@@@@@@');
				Ext.getCmp('userid').setValue(record.get('userid'));
				Ext.getCmp('account').setReadOnly(true);
//				Ext.getCmp('btnReset').hide();
			}

			/**
			 * 修改人员数据
			 */
			function updateUserItem(operateParams) {
				if (!addUserFormPanel.form.isValid()) {
					return;
				}
				password1 = Ext.getCmp('password1').getValue();
				password = Ext.getCmp('password').getValue();
				if (password == '@@@@@@' && password1 == '@@@@@@') {
					Ext.getCmp('updatemode').setValue(null);
				} else {
					Ext.getCmp('updatemode').setValue('2');
					if (password1 != password) {
						Ext.Msg.alert('提示', '两次输入的密码不匹配,请重新输入!');
						Ext.getCmp('password').setValue('');
						Ext.getCmp('password1').setValue('');
						return;
					}
				}
				var deptid = Ext.getCmp('deptid').getValue();
				var deptid_old = Ext.getCmp('deptid_old').getValue();
				if (deptid != deptid_old) {
					Ext.Msg.confirm('确认',
							'修改所属部门将导致人员/角色映射、人员/菜单映射数据丢失! 继续保存吗?', function(
									btn, text) {
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
				var password = Ext.getCmp('password').getValue();
				var pwdFlag='0';
				if (password != '@@@@@@') {
					pwdFlag='1';
				}
				addUserFormPanel.form.submit({
					url : './user.ered?reqCode=updateUserItem&pwdFlag='+pwdFlag,
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						addUserWindow.hide();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('提示', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('提示', '人员数据修改失败:<br>' + msg);
					}
				});
			}

			/**
			 * 人员授权窗口初始化
			 */
			function userGrantInit(userid, deptid, usertype) {

				var selectRoleTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/award_star_silver_3.png" align="top" class="IEPNG"> 选择角色',
							// iconCls: 'user_femaleIcon',
							autoLoad : {
								url : './user.ered?reqCode=userGrantInit',
								scripts : true,
								text : parent.PAGE_LOAD_MSG,
								params : {
									userid : userid,
									deptid : deptid,
									usertype : usertype
								}
							}
						});

				var selectMenuTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> 选择菜单',
							// iconCls: 'user_femaleIcon',
							autoLoad : {
								url : './user.ered?reqCode=selectMenuInit',
								scripts : true,
								text : parent.PAGE_LOAD_MSG,
								params : {
									userid : userid,
									deptid : deptid,
									usertype : usertype
								}
							}
						});
				
				var selectUserRoleTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> 选择授权菜单',
							// iconCls: 'user_femaleIcon',
							autoLoad : {
								url : './user.ered?reqCode=selectUserRoleInit',
								scripts : true,
								text : parent.PAGE_LOAD_MSG,
								params : {
									userid : userid,
									deptid : deptid,
									usertype : usertype
								}
							}
						});

				var userGrantTabPanel = new Ext.TabPanel({
					activeTab : 0,
					width : 600,
					height : 250,
					plain : true,// True表示为不渲染tab候选栏上背景容器图片
					defaults : {
						autoScroll : true
					},
					items : [ selectRoleTab, selectMenuTab ]
//					items : [ selectRoleTab, selectMenuTab,selectUserRoleTab ]
				});

				var userGrantWindow = new Ext.Window({
					layout : 'fit',
					width : 400,
					height : document.body.clientHeight,
					resizable : true,
					draggable : true,
					closeAction : 'close',
					title : '人员授权',
					iconCls : 'group_linkIcon',
					modal : true,
					pageY : 15,
					pageX : document.body.clientWidth / 2 - 420 / 2,
					collapsible : true,
					maximizable : false,
					// animateTarget: document.body,
					// //如果使用autoLoad,建议不要使用动画效果
					buttonAlign : 'right',
					constrain : true,
					items : [ userGrantTabPanel ],
					buttons : [ {
						text : '关闭',
						iconCls : 'deleteIcon',
						handler : function() {
							userGrantWindow.close();
						}
					} ]
				});
				userGrantWindow.show();
				if (login_account != parent.DEFAULT_DEVELOP_ACCOUNT) {
					selectMenuTab.disable();
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
		    		saveUserItem(operateParams);
		    	}else if(operation == 'update'){
		    		updateUserItem(operateParams);
		    	}
		    	else if(operation == 'delete'){
		    		deleteUserItems(operateParams);
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