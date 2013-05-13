/**
 * ȫ�ֲ��������
 * 
 * @author XiongChun
 * @since 2010-05-20
 */
Ext
		.onReady(function() {
			var expander = new Ext.grid.RowExpander(
					{
						tpl : new Ext.Template(
								'<p style=margin-left:70px;><span style=color:Teal;>��������</span><br><span>{paramkey}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>������ֵ</span><br><span>{paramvalue}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>��ע</span><br><span>{remark}</span></p>'),
						// ����˫���¼�
						expandOnDblClick : false
					});
			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), sm,
					expander, {
						header : '��������',
						dataIndex : 'paramkey',
						width : 200
					}, {
						id : 'paramvalue',
						header : '������ֵ',
						dataIndex : 'paramvalue',
						width : 250
					}, {
						header : '�������',
						dataIndex : 'paramid',
						hidden : false,
						hidden : true,
						width : 80,
						sortable : true
					}, {
						id : 'remark',
						header : '��ע',
						dataIndex : 'remark'
					} ]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : './param.ered?reqCode=queryParamsForManage'
				}),
				reader : new Ext.data.JsonReader({
					totalProperty : 'TOTALCOUNT',
					root : 'ROOT'
				}, [ {
					name : 'paramid'
				}, {
					name : 'paramkey'
				}, {
					name : 'paramvalue'
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
				plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������
				emptyMsg : "û�з��������ļ�¼",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});

			var grid = new Ext.grid.GridPanel({
				title : '<span style="font-weight:normal">ȫ�ֲ�����</span>',
				iconCls : 'configIcon',
				renderTo : 'paramGridDiv',
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
				plugins : expander,

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
					text : 'ɾ��',
					iconCls : 'page_delIcon',
					handler : function() {
						deleteParamItems();
					}
				}, '-', {
					text : '�ڴ�ͬ��',
					iconCls : 'arrow_refreshIcon',
					handler : function() {
						synMemory('Ҫ�Բ��������ݽ����ڴ�ͬ��������?', '1');
					}
				}, '-', '��ʾ:ά������������ִ���ڴ�ͬ��', '->', new Ext.form.TextField({
					id : 'queryParam',
					name : 'queryParam',
					emptyText : '�������������|������ֵ',
					enableKeyEvents : true,
					listeners : {
						specialkey : function(field, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								queryParamItem();
							}
						}
					},
					width : 150
				}), {
					text : '��ѯ',
					iconCls : 'previewIcon',
					handler : function() {
						queryParamItem();
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
					limit : bbar.pageSize
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

			var addParamFormPanel = new Ext.form.FormPanel({
				id : 'addParamFormPanel',
				name : 'addParamFormPanel',
				defaultType : 'textfield',
				labelAlign : 'right',
				labelWidth : 60,
				frame : false,
				bodyStyle : 'padding:5 5 0',
				items : [ {
					fieldLabel : '��������',
					name : 'paramkey',
					id : 'paramkey',
                    maxLength:50,
					allowBlank : false,
					anchor : '99%'
				}, {
					fieldLabel : '������ֵ',
					name : 'paramvalue',
					id : 'paramvalue',
					allowBlank : false,
                    maxLength:100,
					xtype : 'textarea',
					anchor : '99%'
				}, {
					fieldLabel : '��ע',
					name : 'remark',
					allowBlank : true,
                    maxLength:200,
					anchor : '99%'
				}, {
					id : 'paramid',
					name : 'paramid',
					hidden : true
				}, {
					id : 'windowmode',
					name : 'windowmode',
					hidden : true
				} ]
			});

			var addParamWindow = new Ext.Window(
					{
						layout : 'fit',
						width : 400,
						height : 200,
						resizable : false,
						draggable : true,
						closeAction : 'hide',
						title : '<span styel="font-weight:normal">����ȫ�ֲ���</span>',
						modal : true,
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
						items : [ addParamFormPanel ],
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
										if (mode == 'add')
											saveParamItem();
										else
											updateParamItem();
									}
								}, {
									text : '����',
									id : 'btnReset',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										clearForm(addParamFormPanel.getForm());
									}
								}, {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										addParamWindow.hide();
									}
								} ]
					});

			/**
			 * ����
			 */
			var viewport = new Ext.Viewport({
				layout : 'border',
				items : [ grid ]
			});

			/**
			 * ��ѯ����
			 */
			function queryParamItem() {
				store.load({
					params : {
						start : 0,
						limit : bbar.pageSize,
						queryParam : Ext.getCmp('queryParam').getValue()
					}
				});
			}

			/**
			 * ����������ʼ��
			 */
			function addInit() {
				// clearForm(addParamFormPanel.getForm());
				var flag = Ext.getCmp('windowmode').getValue();
				if (typeof (flag) != 'undefined') {
					addParamFormPanel.form.getEl().dom.reset();
				} else {
					clearForm(addParamFormPanel.getForm());
				}
				addParamWindow.show();
				addParamWindow
						.setTitle('<span style="font-weight:normal">����ȫ�ֲ���</span>');
				Ext.getCmp('windowmode').setValue('add');
				Ext.getCmp('btnReset').show();
			}

			/**
			 * �����������
			 */
			function saveParamItem() {
				if (!addParamFormPanel.form.isValid()) {
					return;
				}
				addParamFormPanel.form.submit({
					url : './param.ered?reqCode=saveParamItem',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						addParamWindow.hide();
						store.reload();
						form.reset();
						synMemory('���������ɹ�,Ҫ�Բ��������ݽ����ڴ�ͬ��������?');
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('��ʾ', '�������ݱ���ʧ��:<br>' + msg);
					}
				});
			}
			
			/**
			 * �ڴ�ͬ��
			 */
			function synMemory(msg,flag) {
				Ext.Msg.confirm('��ȷ��', msg, function(btn, text) {
					if (btn == 'yes') {
						showWaitMsg();
						Ext.Ajax.request( {
							url : 'param.ered?reqCode=synMemory',
							success : function(response) {
								if(flag == '1'){
									store.reload();
								}
								Ext.Msg.alert('��ʾ', '�ڴ�ͬ���ɹ�');
							},
							failure : function(response) {
								Ext.Msg.alert('��ʾ', '�ڴ�ͬ��ʧ��');
							}
						});
					}
				});
			}

			/**
			 * ɾ������
			 */
			function deleteParamItems() {
				var rows = grid.getSelectionModel().getSelections();
				if (Ext.isEmpty(rows)) {
					Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
					return;
				}
				var strChecked = jsArray2JsString(rows, 'paramid');
				Ext.Msg.confirm('��ȷ��', 'ȷ��ɾ��ѡ�е�ȫ�ֲ�����?', function(btn, text) {
					if (btn == 'yes') {
						if (runMode == '0') {
							Ext.Msg.alert('��ʾ',
									'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
							return;
						}
						showWaitMsg();
						Ext.Ajax.request({
							url : './param.ered?reqCode=deleteParamItems',
							success : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								store.reload();
								synMemory('����ɾ���ɹ�,Ҫ�Բ��������ݽ����ڴ�ͬ��������?');
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								Ext.Msg.alert('��ʾ', resultArray.msg);
							},
							params : {
								strChecked : strChecked
							}
						});
					}
				});
			}

			/**
			 * �޸Ĳ�����ʼ��
			 */
			function editInit() {
				var record = grid.getSelectionModel().getSelected();
				if (Ext.isEmpty(record)) {
					Ext.MessageBox.alert('��ʾ', '����ѡ��Ҫ�޸ĵ���Ŀ');
				}
				addParamFormPanel.getForm().loadRecord(record);
				addParamWindow.show();
				addParamWindow
						.setTitle('<span style="font-weight:normal">�޸�ȫ�ֲ���</span>');
				Ext.getCmp('windowmode').setValue('edit');
				Ext.getCmp('paramid').setValue(record.get('paramid'));
				Ext.getCmp('btnReset').hide();
			}

			/**
			 * �޸Ĳ�������
			 */
			function updateParamItem() {
				if (!addParamFormPanel.form.isValid()) {
					return;
				}
				update();
			}

			/**
			 * ����
			 */
			function update() {
				addParamFormPanel.form.submit({
					url : './param.ered?reqCode=updateParamItem',
					waitTitle : '��ʾ',
					method : 'POST',
					waitMsg : '���ڴ�������,���Ժ�...',
					success : function(form, action) {
						addParamWindow.hide();
						store.reload();
						form.reset();
						synMemory('�����޸ĳɹ�,Ҫ�Բ��������ݽ����ڴ�ͬ��������?');
					},
					failure : function(form, action) {
						var msg = action.result.msg;
						Ext.MessageBox.alert('��ʾ', '��ɫ�����޸�ʧ��:<br>' + msg);
					}
				});
			}
		});