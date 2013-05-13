/**
 * ����Ա�¼�����
 * 
 * @author XiongChun
 * @since 2010-05-20
 */
Ext.onReady(function() {
			var expander = new Ext.grid.RowExpander(
					{
						tpl : new Ext.Template(
								'<p style=margin-left:70px;><span style=color:Teal;>������Ϣ</span><br><span>{description}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>����·��</span><br><span>{requestpath}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>���󷽷�</span><br><span>{methodname}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>��ʱ</span><br><span>{costtime}</span></p>'),
						// ����˫���¼�
						expandOnDblClick : true
					});
			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel( [ new Ext.grid.RowNumberer(),
					sm, expander, {
						header : '����ʱ��',
						dataIndex : 'activetime',
						sortable : true,
						width : 130
					}, {
						header : '������Ϣ',
						dataIndex : 'description',
						width : 350,
						sortable : true
					}, {
						header : '��ʱ',
						dataIndex : 'costtime',
						width : 80,
						sortable : true
					}, {
						header : '����·��',
						dataIndex : 'requestpath',
						sortable : true,
						width : 200
					}, {
						header : '���󷽷�',
						dataIndex : 'methodname',
						sortable : true,
						width : 150
					}, {
						header : '�û���',
						dataIndex : 'username',
						width : 80,
						sortable : true
					}, {
						header : '�¼����',
						dataIndex : 'eventid',
						hidden : true,
						width : 120,
						sortable : true
					}, {
						header : '�û�ID',
						dataIndex : 'userid',
						hidden : true
					}, {
						header : '�ʻ�',
						dataIndex : 'account',
						width : 80,
						sortable : true
					}, {
						id : '_blank',
						dataIndex : '_blank'
					} ]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store( {
				proxy : new Ext.data.HttpProxy( {
					url : 'eventTrack.ered?reqCode=queryEvents'
				}),
				reader : new Ext.data.JsonReader( {
					totalProperty : 'TOTALCOUNT',
					root : 'ROOT'
				}, [ {
					name : 'eventid'
				}, {
					name : 'userid'
				}, {
					name : 'account'
				}, {
					name : 'username'
				}, {
					name : 'activetime'
				}, {
					name : 'description'
				}, {
					name : 'requestpath'
				}, {
					name : 'methodname'
				}, {
					name : 'costtime'
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
					jsrq : jsrq,
					ksrq : ksrq,
					account : Ext.getCmp('account').getValue(),
					username : Ext.getCmp('username').getValue(),
					keyword : Ext.getCmp('keyword').getValue()
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
				// emptyMsg
				// :
				// "û�з��������ļ�¼",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});

			var grid = new Ext.grid.GridPanel(
					{
						title : '<span style="font-weight:normal">Rquest�������</span>',
						iconCls: 'server_connectIcon',
						renderTo : 'eventGridDiv',
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
										deleteEvents('del');
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
										deleteEvents('reset');
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
								new Ext.form.TextField(
										{
											id : 'account',
											name : 'account',
											emptyText : '�ʻ�',
											enableKeyEvents : true,
											listeners : {
												specialkey : function(field, e) {
													if (e.getKey() == Ext.EventObject.ENTER) {
														queryEvents();
													}
												}
											},
											width : 80
										}),
								'-',
								new Ext.form.TextField(
										{
											id : 'username',
											name : 'username',
											emptyText : '�û���',
											enableKeyEvents : true,
											listeners : {
												specialkey : function(field, e) {
													if (e.getKey() == Ext.EventObject.ENTER) {
														queryEvents();
													}
												}
											},
											width : 80
										}),
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
														queryEvents();
													}
												}
											},
											width : 120
										}), {
									text : '��ѯ',
									iconCls : 'previewIcon',
									handler : function() {
										queryEvents();
									}
								},
								'-',
								{
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
				//grid.getSelectionModel().selectFirstRow();
			});

			bbar.on("change", function() {
				//grid.getSelectionModel().selectFirstRow();
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
			function queryEvents() {
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
						account : Ext.getCmp('account').getValue(),
						username : Ext.getCmp('username').getValue(),
						keyword : Ext.getCmp('keyword').getValue(),
						jsrq : jsrq,
						ksrq : ksrq
					}
				});
			}

			/**
			 * ɾ���¼�
			 */
			function deleteEvents(type) {
				var rows = grid.getSelectionModel().getSelections();
				if(type == 'del'){
					if (Ext.isEmpty(rows)) {
						Ext.Msg.alert('��ʾ', '����ѡ��Ҫɾ������Ŀ!');
						return;
					}
				}
				var strChecked = jsArray2JsString(rows, 'eventid');
				var msg = "ȷ��ɾ��ѡ�еļ�¼��?";
				if(type == 'reset') msg = "ȷ���������еļ�¼��,��¼��Ϣ�������?";
				Ext.Msg.confirm('��ȷ��', msg, function(btn, text) {
					if (btn == 'yes') {
						showWaitMsg();
						Ext.Ajax.request( {
							url : 'eventTrack.ered?reqCode=deleteEvents',
							success : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								store.reload();
								Ext.Msg.alert('��ʾ', resultArray.msg);
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.responseText);
								Ext.Msg.alert('��ʾ', resultArray.msg);
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
				Ext.Msg.alert('��ʾ', 'Ϊ������ʾϵͳ��������,���ٹ����ѹر�.����������������ʷ��������!');
			}

		});