/**
 * SpringBean������
 * 
 * @author XiongChun
 * @since 2010-09-20
 */
Ext
		.onReady(function() {
			var expander = new Ext.grid.RowExpander(
					{
						tpl : new Ext.Template(
								'<p style=margin-left:70px;><span style=color:Teal;>����</span><br><span>{methodname}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>����</span><br><span>{clazz}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>�ʱ��</span><br><span>{activetime}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>��ʱ</span><br><span>{costtime}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>�쳣��Ϣ</span><br><span style=color:red;>{exception}</span></p>'),
						// ����˫���¼�
						expandOnDblClick : true
					});
			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel( [ new Ext.grid.RowNumberer(),
					sm, expander, {
						header : '��ر��',
						dataIndex : 'monitorid',
						hidden : true,
						width : 120,
						sortable : true
					}, {
						header : '�����',
						dataIndex : 'pointcuttype',
						sortable : true,
						width : 60,
						hidden : true,
						renderer : function(value) {
							if (value == '1')
								return 'BPO';
							else if (value == '2')
								return 'DAO';
							else
								return value;
						}
					}, {
						header : '����ʱ��',
						dataIndex : 'activetime',
						sortable : true,
						width : 130
					}, {
						header : '����',
						dataIndex : 'methodname',
						width : 160,
						sortable : true
					}, {
						header : '��ʱ',
						dataIndex : 'costtime',
						width : 80,
						sortable : true
					}, {
						header : '����',
						dataIndex : 'clazz',
						width : 350,
						sortable : true
					}, {
						header : '֪ͨ����',
						dataIndex : 'advisetype',
						width : 80,
						sortable : true,
						renderer : function(value) {
							if (value == '1')
								return '��������';
							else if (value == '2')
								return '�쳣����';
							else
								return value;
						}
					}, {
						header : '�쳣��Ϣ',
						dataIndex : 'exception',
						sortable : true,
						width : 200
					}, {
						id : '_blank',
						dataIndex : '_blank'
					} ]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store( {
				proxy : new Ext.data.HttpProxy( {
					url : 'beanMonitor.ered?reqCode=queryMonitorDatas'
				}),
				reader : new Ext.data.JsonReader( {
					totalProperty : 'TOTALCOUNT',
					root : 'ROOT'
				}, [ {
					name : 'monitorid'
				}, {
					name : 'pointcuttype'
				}, {
					name : 'advisetype'
				}, {
					name : 'methodname'
				}, {
					name : 'activetime'
				}, {
					name : 'costtime'
				}, {
					name : 'clazz'
				}, {
					name : 'exception'
				} ])
			});

			// ��ҳ����ʱ���ϲ�ѯ����
			store.on('beforeload', function() {
				var ksrq = Ext.getCmp('ksrq').getValue();
				if (!Ext.isEmpty(ksrq)) {
					ksrq = ksrq.format('Y-m-d').toString();
				}
				var jsrq = Ext.getCmp('jsrq').getValue();
				if (!Ext.isEmpty(jsrq)) {
					jsrq = jsrq.format('Y-m-d').toString();
				}
				this.baseParams = {
					keyword : Ext.getCmp('keyword').getValue(),
					jsrq : jsrq,
					ksrq : ksrq,
					advisetype : advisetypeCombo.getValue(),
					pointcuttype : pointcuttypeCombo.getValue()
				};
			});

			var pagesize_combo = new Ext.form.ComboBox( {
				name : 'pagesize',
				hiddenName : 'pagesize',
				typeAhead : true,
				triggerAction : 'all',
				lazyRender : true,
				mode : 'local',
				store : new Ext.data.ArrayStore( {
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
				displayMsg : '��ʾ{0}����{1}��,��{2}��',
				plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������
				emptyMsg : "û�з��������ļ�¼",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});

			var pointcuttypeStore = new Ext.data.SimpleStore( {
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 BPO�����' ], [ '2', '2 DAO�����' ] ]
			});
			var pointcuttypeCombo = new Ext.form.ComboBox( {
				name : 'pointcuttype',
				hiddenName : 'pointcuttype',
				store : pointcuttypeStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				// value : '0',
				fieldLabel : 'ѡ�������',
				emptyText : 'ѡ�������',
				allowBlank : true,
				forceSelection : true,
				editable : true,
				typeAhead : true,
				width : 100
			});
			var advisetypeStore = new Ext.data.SimpleStore( {
				fields : [ 'value', 'text' ],
				data : [ [ '1', '1 ��������' ], [ '2', '2 �쳣����' ] ]
			});
			var advisetypeCombo = new Ext.form.ComboBox( {
				name : 'advisetype',
				hiddenName : 'advisetype',
				store : advisetypeStore,
				mode : 'local',
				triggerAction : 'all',
				valueField : 'value',
				displayField : 'text',
				// value : '0',
				fieldLabel : 'ѡ��֪ͨ����',
				emptyText : 'ѡ��֪ͨ����',
				allowBlank : true,
				forceSelection : true,
				editable : true,
				typeAhead : true,
				width : 100
			});
			var grid = new Ext.grid.GridPanel(
					{
						title : '<span style="font-weight:normal">Service���</span>',
						renderTo : 'beanMonitorGridDiv',
						height : 500,
						// width:600,
						iconCls : 'pluginIcon',
						autoScroll : true,
						region : 'center',
						store : store,
						loadMask : {
							msg : '���ڼ��ر������,���Ե�...'
						},
						stripeRows : true,
						frame : true,
						autoExpandColumn : '_blank',
						cm : cm,
						sm : sm,
						plugins : expander,
						tbar : [
								{
									text : 'ɾ��',
									iconCls : 'page_delIcon',
									handler : function() {
										if (runMode == '0') {
											Ext.Msg
													.alert('��ʾ',
															'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
											return;
										}
										deleteMonitorDatas('del');
									}
								},
								'-',
								{
									text : '����',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										if (runMode == '0') {
											Ext.Msg
													.alert('��ʾ',
															'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
											return;
										}
										deleteMonitorDatas('reset');
									}
								},
								'->',
								{
									id : 'ksrq',
									name : 'ksrq',
									xtype : 'datefield',
									emptyText : '��ʼ����',
									format : 'Y-m-d',
									width : 100
								},
								'-',
								{
									id : 'jsrq',
									name : 'jsrq',
									xtype : 'datefield',
									emptyText : '��������',
									format : 'Y-m-d',
									width : 100
								},
								'-',
								advisetypeCombo,
								'-',
								new Ext.form.TextField(
										{
											id : 'keyword',
											name : 'keyword',
											emptyText : '�ؼ��ֹ���',
											enableKeyEvents : true,
											listeners : {
												specialkey : function(field, e) {
													if (e.getKey() == Ext.EventObject.ENTER) {
														queryMonitorDatas();
													}
												}
											},
											width : 100
										}), {
									text : '��ѯ',
									iconCls : 'previewIcon',
									handler : function() {
										queryMonitorDatas();
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
			store.load( {
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
			grid.on('sortchange', function() {
				grid.getSelectionModel().selectFirstRow();
			});

			bbar.on("change", function() {
				grid.getSelectionModel().selectFirstRow();
			});

			/**
			 * ����
			 */
			var viewport = new Ext.Viewport( {
				layout : 'border',
				items : [ grid ]
			});

			/**
			 * ��ѯ�¼�
			 */
			function queryMonitorDatas() {
				var ksrq = Ext.getCmp('ksrq').getValue();
				if (!Ext.isEmpty(ksrq)) {
					ksrq = ksrq.format('Y-m-d').toString();
				}
				var jsrq = Ext.getCmp('jsrq').getValue();
				if (!Ext.isEmpty(jsrq)) {
					jsrq = jsrq.format('Y-m-d').toString();
				}
				store.load( {
					params : {
						start : 0,
						limit : bbar.pageSize,
						keyword : Ext.getCmp('keyword').getValue(),
						jsrq : jsrq,
						ksrq : ksrq,
						advisetype : advisetypeCombo.getValue(),
						pointcuttype : pointcuttypeCombo.getValue()
					}
				});
			}

			/**
			 * ɾ���¼�
			 */
			function deleteMonitorDatas(type) {
				var rows = grid.getSelectionModel().getSelections();
				if (Ext.isEmpty(rows)) {
					Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
					return;
				}
				var strChecked = jsArray2JsString(rows, 'monitorid');
				var msg = 'ȷ��ɾ��ѡ�еļ�¼��?';
				if (type == 'reset')
					msg = 'ȷ��������м�ؼ�¼��?';
				Ext.Msg
						.confirm(
								'��ȷ��',
								msg,
								function(btn, text) {
									if (btn == 'yes') {
										showWaitMsg();
										Ext.Ajax
												.request( {
													url : 'beanMonitor.ered?reqCode=deleteMonitorDatas',
													success : function(response) {
														var resultArray = Ext.util.JSON
																.decode(response.responseText);
														store.reload();
														Ext.Msg
																.alert(
																		'��ʾ',
																		resultArray.msg);
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
														strChecked : strChecked,
														type : type
													}
												});
									}
								});
			}

			if (runMode == '0') {
				Ext.Msg.alert('��ʾ', 'Ϊ������ʾϵͳ��������,��ع����ѹر�.����������������ʷ�������!');
			}

		});