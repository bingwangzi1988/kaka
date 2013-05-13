/**
 * �û���������Ȩ
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
					text : '������Ա',
					iconCls : 'page_addIcon',
					handler : function() {
						addInit();
					}
				}, {
					text : 'ˢ�½ڵ�',
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
			
			// ��������
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
								header : 'Ȩ��',
								hidden:true,
								dataIndex : 'userid',
								renderer : function(value) {
									return '<a k="javascript:void(0);"><img src="./resource/image/ext/edit2.gif" style="cursor: pointer;"/></a>';
								},
								width : 50
							}, {
								header : '����',
								dataIndex : 'username',
								width : 80
							}, {
								header : '��¼�ʻ�',
								dataIndex : 'account',
								width : 130
							}, {
								id : 'deptname',
								header : '��������',
								dataIndex : 'deptname',
								width : 130
							}, {
								header : '�Ա�',
								dataIndex : 'sex',
								width : 60,
								renderer : function(value) {
									if (value == '1')
										return '��';
									else if (value == '2')
										return 'Ů';
									else if (value == '0')
										return 'δ֪';
									else
										return value;
								}
							}, {
								header : '��Ա״̬',
								dataIndex : 'locked',
								width : 60,
								renderer : function(value) {
									if (value == '1')
										return '����';
									else if (value == '0')
										return '����';
									else
										return value;
								}
							}, {
								id : 'usertype',
								header : '��Ա����',
								hidden:true,
								dataIndex : 'usertype',
								width : 60,
								renderer : function(value) {
									if (value == '1')
										return '����Ա';
									else if (value == '2')
										return '����Ա';
									else if (value == '3')
										return 'ϵͳ��Ա';
									else if (value == '4')
										return '��Ŀ��ҳע���û�';
									else
										return value;
								}
							}, {
								header : '��Ա���',
								dataIndex : 'userid',
								hidden : false,
								width : 80,
								sortable : true
							}, 
							{
								header : '����',
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
								header : '��ע',
								dataIndex : 'remark'
							}, {
								id : 'deptid',
								header : '�������ű��',
								dataIndex : 'deptid',
								hidden : true
							} ]);

			/**
			 * ���ݴ洢
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

			// ��ҳ����ʱ���ϲ�ѯ����
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
					data : [ [ 10, '10��/ҳ' ], [ 20, '20��/ҳ' ], [ 50, '50��/ҳ' ],
							[ 100, '100��/ҳ' ], [ 250, '250��/ҳ' ],
							[ 500, '500��/ҳ' ] ]
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
				displayMsg : '��ʾ{0}����{1}��,��{2}��',
				emptyMsg : "û�з��������ļ�¼",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var grid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">��Ա��Ϣ��</span>',
				iconCls : 'groupIcon',
				renderTo : 'userGridDiv',
				height : 500,
				// width:600,
				autoScroll : true,
				region : 'center',
				store : store,
				loadMask : {
					msg : '���ڼ��ر������,���Ե�...'
				},
				stripeRows : true,
				frame : true,
				autoExpandColumn : 'remark',
				cm : cm,
				sm : sm,
				tbar : [ {
					text : '����',
					iconCls : 'page_addIcon',
					handler : function() {
						addInit();
					}
				}, '-', {
					text : '�޸�',
					iconCls : 'page_edit_1Icon',
					handler : function() {
						editInit();
					}
				}, '-', {
					text : 'Ȩ����Ȩ',
					iconCls : 'page_edit_1Icon',
					handler : function() {
						grantBtn();
						
					}
				}, '-', {
					text : 'ɾ��',
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
							Ext.Msg.alert('��ʾ', '<b>��ѡ�е���Ŀ�а�������ϵͳ���õ�ֻ����Ŀ</b><br>'
									+ fields + '<font color=red>ϵͳ������Ա����ɾ��!</font>');
							return;
						}
						if (Ext.isEmpty(rows)) {
							Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
							return;
						}
						authoriseRole('delete','Eauser');
					}
				}, '->', new Ext.form.TextField({
					id : 'queryParam',
					name : 'queryParam',
					emptyText : '��������Ա����',
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
					text : '��ѯ',
					iconCls : 'previewIcon',
					handler : function() {
						queryUserItem();
					}
				}, '-', {
					text : 'ˢ��',
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
						Ext.Msg.alert("����","ϵͳ������Ա�����޸�");
					}else{
						var userid = record.get('userid');
						var deptid = record.get('deptid');
						userGrantInit(userid, deptid, usertype);
					}
				}
				else
				{
					Ext.MessageBox.show({
						title: '����',
						msg: "��ѡ��һ������",
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
			// �����������Ľڵ㵥���¼�
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
						emptyText : '��ѡ��...',
						fieldLabel : '��������',
						anchor : '100%',
						mode : 'local',
						triggerAction : 'all',
						maxHeight : 390,
						// ���������ʾģ��,addDeptTreeDiv��Ϊ��ʾ������������
						tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv'></div></div></tpl>",
						allowBlank : false,
						onSelect : Ext.emptyFn
					});
			// ���������������չ���¼�
			comboxWithTree.on('expand', function() {
				// ��UI���ҵ�treeDiv����
				addDeptTree.render('addDeptTreeDiv');
				// addDeptTree.root.expand(); //ֻ�ǵ�һ���������������
				addDeptTree.root.reload(); // ÿ�����������������

			});

			var sexStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 ��' ], [ '2', '2 Ů' ], [ '0', '0 δ֪' ] ]
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
				fieldLabel : '�Ա�',
				emptyText : '��ѡ��...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				anchor : "99%"
			});

			var usertypeStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 ����Ա' ], [ '2', '2 ����Ա' ] ]
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
				fieldLabel : '��Ա����',
				emptyText : '��ѡ��...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				typeAhead : true,
				readOnly : true,
				anchor : "99%"
			});

			var lockedStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '0', '0 ����' ], [ '1', '1 ����' ] ]
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
				fieldLabel : '��Ա״̬',
				emptyText : '��ѡ��...',
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
				fieldLabel : '����',
				emptyText : '��ѡ��...',
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
					fieldLabel : '��Ա����',
					name : 'username',
					id : 'username',
                    maxLength:20,
					allowBlank : false,
					anchor : '99%'
				}, comboxWithTree, {
					fieldLabel : '��¼�ʻ�',
					name : 'account',
					id : 'account',
                    maxLength:30,
					allowBlank : false,
					anchor : '99%'
				}, {
					fieldLabel : '����',
					name : 'password',
					id : 'password',
					inputType : 'password',
					allowBlank : false,
                    maxLength:50,
					anchor : '99%'
				}, {
					fieldLabel : 'ȷ������',
					name : 'password1',
					id : 'password1',
					inputType : 'password',
                    maxLength:50,
					allowBlank : false,
					anchor : '99%'
				}, usertypeCombo, lockedCombo, sexCombo,branchCombo, {
					fieldLabel : '��ע',
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
						title : '<span style="font-weight:normal">������Ա</span>',
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
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										if (runMode == '0') {
											Ext.Msg
													.alert('��ʾ',
															'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
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
//									text : '����',
//									id : 'btnReset',
//									iconCls : 'tbar_synchronizeIcon',
//									handler : function() {
//										clearForm(addUserFormPanel.getForm());
//									}
								}, {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										addUserWindow.hide();
									}
								} ]
					});

			/**
			 * ����
			 */
			var viewport = new Ext.Viewport({
				layout : 'border',
				items : [ {
					title : '<span style="font-weight:normal">��֯����</span>',
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
			 * ����������ѯ��Ա
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
			 * ������Ա��ʼ��
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
						.setTitle('<span style="font-weight:normal">������Ա</span>');
				Ext.getCmp('windowmode').setValue('add');
				Ext.getCmp('account').setReadOnly(false);
				comboxWithTree.setDisabled(false);
//				Ext.getCmp('btnReset').show();
				lockedCombo.setValue('0');
				usertypeCombo.setValue('1');
				sexCombo.setValue('0');
			}

			/**
			 * ������Ա����
			 */
			function saveUserItem(operateParams) {
				if (!addUserFormPanel.form.isValid()) {
					return;
				}
				password1 = Ext.getCmp('password1').getValue();
				password = Ext.getCmp('password').getValue();
				if (password1 != password) {
					Ext.Msg.alert('��ʾ', '������������벻ƥ��,����������!');
					Ext.getCmp('password').setValue('');
					Ext.getCmp('password1').setValue('');
					return;
				}
				addUserFormPanel.form.submit({
					url : './user.ered?reqCode=saveUserItem',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						addUserWindow.hide();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('��ʾ', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('��ʾ', '��Ա���ݱ���ʧ��:<br>' + msg);
					}
				});
			}

			/**
			 * ɾ����Ա
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
					Ext.Msg.alert('��ʾ', '<b>��ѡ�е���Ŀ�а�������ϵͳ���õ�ֻ����Ŀ</b><br>'
							+ fields + '<font color=red>ϵͳ������Ա����ɾ��!</font>');
					return;
				}
				if (Ext.isEmpty(rows)) {
					Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
					return;
				}
				var strChecked = jsArray2JsString(rows, 'userid');
				Ext.Msg
						.confirm(
								'��ȷ��',
								'<span style="color:red"><b>��ʾ:</b>ɾ����Ա��ͬʱɾ������Ա��ص�Ȩ����Ϣ,������.</span><br>����ɾ����?',
								function(btn, text) {
									if (btn == 'yes') {
										if (runMode == '0') {
											Ext.Msg
													.alert('��ʾ',
															'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
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
																		'��ʾ',
																		resultArray.msg);
														insertOperateRcd(operateParams);
													},
													failure : function(response) {
														var resultArray = Ext.util.JSON
																.decode(response.responseText);
														Ext.Msg
																.alert(
																		'��ʾ',
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
			 * �޸���Ա��ʼ��
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
					Ext.MessageBox.alert('��ʾ', '����ѡ��Ҫ�޸ĵ���Ŀ!');
				}
				if (record.get('usertype') == '3') {
					Ext.MessageBox.alert('��ʾ', 'ϵͳ������Ա,�����޸�!');
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
						.setTitle('<span style="font-weight:normal">�޸���Ա</span>');
				Ext.getCmp('windowmode').setValue('edit');
				Ext.getCmp('deptid_old').setValue(record.get('deptid'));
				Ext.getCmp('password').setValue('@@@@@@');
				Ext.getCmp('password1').setValue('@@@@@@');
				Ext.getCmp('userid').setValue(record.get('userid'));
				Ext.getCmp('account').setReadOnly(true);
//				Ext.getCmp('btnReset').hide();
			}

			/**
			 * �޸���Ա����
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
						Ext.Msg.alert('��ʾ', '������������벻ƥ��,����������!');
						Ext.getCmp('password').setValue('');
						Ext.getCmp('password1').setValue('');
						return;
					}
				}
				var deptid = Ext.getCmp('deptid').getValue();
				var deptid_old = Ext.getCmp('deptid_old').getValue();
				if (deptid != deptid_old) {
					Ext.Msg.confirm('ȷ��',
							'�޸��������Ž�������Ա/��ɫӳ�䡢��Ա/�˵�ӳ�����ݶ�ʧ! ����������?', function(
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
			 * ����
			 */
			function update(operateParams) {
				var password = Ext.getCmp('password').getValue();
				var pwdFlag='0';
				if (password != '@@@@@@') {
					pwdFlag='1';
				}
				addUserFormPanel.form.submit({
					url : './user.ered?reqCode=updateUserItem&pwdFlag='+pwdFlag,
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						addUserWindow.hide();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('��ʾ', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('��ʾ', '��Ա�����޸�ʧ��:<br>' + msg);
					}
				});
			}

			/**
			 * ��Ա��Ȩ���ڳ�ʼ��
			 */
			function userGrantInit(userid, deptid, usertype) {

				var selectRoleTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/award_star_silver_3.png" align="top" class="IEPNG"> ѡ���ɫ',
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
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> ѡ��˵�',
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
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> ѡ����Ȩ�˵�',
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
					plain : true,// True��ʾΪ����Ⱦtab��ѡ���ϱ�������ͼƬ
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
					title : '��Ա��Ȩ',
					iconCls : 'group_linkIcon',
					modal : true,
					pageY : 15,
					pageX : document.body.clientWidth / 2 - 420 / 2,
					collapsible : true,
					maximizable : false,
					// animateTarget: document.body,
					// //���ʹ��autoLoad,���鲻Ҫʹ�ö���Ч��
					buttonAlign : 'right',
					constrain : true,
					items : [ userGrantTabPanel ],
					buttons : [ {
						text : '�ر�',
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
		     * ��Ȩģ��
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
				fieldLabel : '��ȨԱ��',
				emptyText : '��ѡ��',
				triggerAction : 'all',
				store:userRoleStore,
				displayField : 'codedesc',
				valueField : 'code',
				mode : 'local',
				forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
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
//		    			fieldLabel : '��ȨԱ��',
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
		    			fieldLabel : '��Ȩ����',
		    			name : 'authorisePwd',
		    			inputType : 'password',
		    			allowBlank : false,
		    			anchor : '100%'
		    		}, {
		    			fieldLabel : '��������',
		    			name : 'operateType',
		    			value:operation,
		    			hidden:true,
		    			anchor : '100%'
		    		}, {
		    			fieldLabel : '��������',
		    			name : 'operateObject',
		    			value:operateObject,
		    			hidden:true,
		    			anchor : '100%'
		    		}/*, {
		    			fieldLabel : '������Ϣ',
		    			name : 'operateInfo',
//		    			hidden:true,
		    			anchor : '100%'
		    		}*/]	
		    	});
		    	
		    	var authoriseRoleWin = new Ext.Window({
		    		title : '������Ȩ',
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
		    			text : '��Ȩ',
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
		    							Ext.MessageBox.alert('��ʾ', result.msg);
		    						},
		    						waitTitle:"���Ժ�",
		            				waitMsg:"��Ȩ��֤�С���"
		    					});
		    				}
		    			}
		    		},{
		    			text : 'ȡ��',
		    			iconCls : 'lockIcon',
		    			handler : function cancel(){
		    				authoriseRoleWin.hide();
		    			}
		    		}]
		    	});
		    	
		    	
//		    	authoriseWin.show();
//		    	alert("operation=="+operation+"    operateObject=="+operateObject);
		    	
		    	//��ѯ�˵�Ȩ�ޱ�
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
		    								authoriseRoleWin.show();//��Ҫ��Ȩ
		    							}
		    							else{
		    								execNoRole(operation,operateObject);//����Ҫ��Ȩ
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
		    								authoriseRoleWin.show();//��Ҫ��Ȩ
		    							}
		    							else{
		    								execNoRole(operation,operateObject);//����Ҫ��Ȩ
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
		    								authoriseRoleWin.show();//��Ҫ��Ȩ
		    							}
		    							else{
		    								execNoRole(operation,operateObject);//����Ҫ��Ȩ
		    							}
		    						}
		    						else
		    						{
		    							execNoRole(operation,operateObject);//����Ҫ��Ȩ
		    						}
		    				   }
		    			   }else{
		    				   execNoRole(operation,operateObject);//Ĭ��Ϊ����Ҫ��Ȩ
		    			   }
		    		   }
		    	   });

		    	
		    }


		    //����Ȩ
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
		    				   Ext.Msg.alert('��ʾ', jsonobjs.msg);
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
		    
		  //��¼����
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
		     * ��Ȩģ��
		     * */

		});