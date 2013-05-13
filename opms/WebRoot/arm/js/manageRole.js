/**
 * ��ɫ��������Ȩ
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
					text : '������ɫ',
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
			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer(),
							sm,
							{
								header : 'Ȩ��',
								dataIndex : 'roleid',
								hidden:true,
								renderer : function(value) {
									return '<a href="javascript:void(0);"><img src="./resource/image/ext/edit2.gif" style="cursor: pointer;"/></a>';
								},
								width : 50
							}, {
								header : '��ɫ����',
								dataIndex : 'rolename',
								width : 150
							}, {
								id : 'deptname',
								header : '��������',
								dataIndex : 'deptname',
								width : 150
							}, {
								header : '��ɫ����',
								dataIndex : 'roletype',
								hidden:true,
								width : 80,
								renderer : function(value) {
									if (value == '1')
										return 'ҵ���ɫ';
									else if (value == '2')
										return '�����ɫ';
									else if (value == '3')
										return 'ϵͳ���ý�ɫ';
									else
										return value;
								}
							}, {
								header : '��ɫ״̬',
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
								header : '��ɫ���',
								dataIndex : 'roleid',
								hidden : false,
								width : 80,
								sortable : true
							}, {
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
				title : '<span style="font-weight:normal">��ɫ��Ϣ��</span>',
				iconCls : 'award_star_silver_3Icon',
				renderTo : 'roleGridDiv',
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
//						deleteRoleItems();
						authoriseRole('delete','Cmrole');
					}
				}, '->', new Ext.form.TextField({
					id : 'queryParam',
					name : 'queryParam',
					emptyText : '�������ɫ����',
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
					text : '��ѯ',
					iconCls : 'previewIcon',
					handler : function() {
						queryRoleItem();
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
						title: '����',
						msg: "��ѡ��һ������",
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
			// �����������Ľڵ㵥���¼�
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
				fieldLabel : '��ɫ״̬',
				emptyText : '��ѡ��...',
				allowBlank : false,
				forceSelection : true,
				editable : false,
				readOnly:true,
				typeAhead : true,
				anchor : "99%"
			});

			var roletypeStore = new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 ҵ���ɫ' ], [ '2', '2 �����ɫ' ] ]
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
				fieldLabel : '��ɫ����',
				emptyText : '��ѡ��...',
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
					fieldLabel : '��ɫ����',
					name : 'rolename',
					id : 'rolename',
                    maxLength:50,
					allowBlank : false,
					anchor : '99%'
				}, comboxWithTree, roletypeCombo, lockedCombo, {
					fieldLabel : '��ע',
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
						title : '<span style="font-weight:normal">������ɫ</span>',
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
//											saveRoleItem();
											authoriseRole('save','Cmrole');
										}
										else
//											updateRoleItem();
											authoriseRole('update','Cmrole');
									}
//								}, {
//									text : '����',
//									id : 'btnReset',
//									iconCls : 'tbar_synchronizeIcon',
//									handler : function() {
//										clearForm(addRoleFormPanel.getForm());
//									}
								}, {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										addRoleWindow.hide();
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
			 * ����������ѯ��ɫ
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
			 * ������ɫ��ʼ��
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
						.setTitle('<span style="font-weight:normal">������ɫ</span>');
				Ext.getCmp('windowmode').setValue('add');
				comboxWithTree.setDisabled(false);
				lockedCombo.setValue('0');
				roletypeCombo.setValue('1');
//				Ext.getCmp('btnReset').show();
			}

			/**
			 * �����ɫ����
			 */
			function saveRoleItem(operateParams) {
				if (!addRoleFormPanel.form.isValid()) {
					return;
				}
				addRoleFormPanel.form.submit({
					url : './role.ered?reqCode=saveRoleItem',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						addRoleWindow.hide();
						deptid = Ext.getCmp('deptid').getValue();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('��ʾ', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('��ʾ', '��ɫ���ݱ���ʧ��:<br>' + msg);
					}
				});
			}

			/**
			 * ɾ����ɫ
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
					Ext.Msg.alert('��ʾ', '<b>��ѡ�е���Ŀ�а�������ϵͳ���õ�ֻ����Ŀ</b><br>'
							+ fields + '<font color=red>ϵͳ���ý�ɫ����ɾ��!</font>');
					return;
				}
				if (Ext.isEmpty(rows)) {
					Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
					return;
				}
				var strChecked = jsArray2JsString(rows, 'roleid');
				Ext.Msg
						.confirm(
								'��ȷ��',
								'<span style="color:red"><b>��ʾ:</b>ɾ����ɫ��ͬʱɾ���ͽ�ɫ��ص�Ȩ����Ϣ,������.</span><br>����ɾ����?',
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
													url : './role.ered?reqCode=deleteRoleItems',
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
			 * �޸Ľ�ɫ��ʼ��
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
					Ext.MessageBox.alert('��ʾ', '����ѡ��Ҫ�޸ĵĽ�ɫ!');
				}
				if (record.get('roletype') == '3') {
					Ext.MessageBox.alert('��ʾ', 'ϵͳ���ý�ɫ,�����޸�!');
					return;
				}
				addRoleFormPanel.getForm().loadRecord(record);
				addRoleWindow.show();
				addRoleWindow
						.setTitle('<span style="font-weight:normal">�޸Ľ�ɫ</span>');
				Ext.getCmp('windowmode').setValue('edit');
				Ext.getCmp('deptid_old').setValue(record.get('deptid'));
				Ext.getCmp('roleid').setValue(record.get('roleid'));
//				Ext.getCmp('btnReset').hide();
			}

			/**
			 * �޸Ľ�ɫ����
			 */
			function updateRoleItem(operateParams) {
				if (!addRoleFormPanel.form.isValid()) {
					return;
				}
				var deptid = Ext.getCmp('deptid').getValue();
				var deptid_old = Ext.getCmp('deptid_old').getValue();
				if (deptid != deptid_old) {
					Ext.Msg.confirm('ȷ��', '�޸��������Ž����½�ɫ-��Աӳ���ϵ��ʧ! ����������?',
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
			 * ����
			 */
			function update(operateParams) {
				addRoleFormPanel.form.submit({
					url : './role.ered?reqCode=updateRoleItem',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						addRoleWindow.hide();
						store.reload();
						form.reset();
						Ext.MessageBox.alert('��ʾ', action.result.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('��ʾ', '��ɫ�����޸�ʧ��:<br>' + msg);
					}
				});
			}

			/**
			 * ��ɫ��Ȩ���ڳ�ʼ��
			 */
			function roleGrantInit(roleid, deptid, roletype) {

				var operatorTab = new Ext.Panel(
						{
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> ����Ȩ��',
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
							title : '<img src="./resource/image/ext/group.png" align="top" class="IEPNG"> ѡ����Ա',
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
							title : '<img src="./resource/image/ext/wrench.png" align="top" class="IEPNG"> ��ȨȨ����Ȩ',
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
							title : '<img src="./resource/image/ext/config.png" align="top" class="IEPNG"> ��ȨȨ��',
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
					plain : true,// True��ʾΪ����Ⱦtab��ѡ���ϱ�������ͼƬ
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
					title : '<span style="font-weight:normal">��ɫ��Ȩ</span>',
					// iconCls : 'award_star_silver_3Icon',
					modal : true,
					pageY : 15,
					pageX : document.body.clientWidth / 2 - 420 / 2,
					collapsible : true,
					titleCollapse : true,
					maximizable : false,
					// animateTarget: document.body,
					// //���ʹ��autoLoad,���鲻Ҫʹ�ö���Ч��
					buttonAlign : 'right',
					constrain : true,
					items : [ roleGrantTabPanel ],
					buttons : [ {
						text : '�ر�',
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
		    		saveRoleItem(operateParams);
		    	}else if(operation == 'update'){
		    		updateRoleItem(operateParams);
		    	}
		    	else if(operation == 'delete'){
		    		deleteRoleItems(operateParams);
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