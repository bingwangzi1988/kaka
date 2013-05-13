/**
 * JDBCִ�м��
 * 
 * @author XiongChun
 * @since 2010-05-20
 */
Ext.onReady(function() {
			var expander = new Ext.grid.RowExpander({
						tpl : new Ext.Template('<p style=margin-left:70px;><span style=color:Teal;>ִ��SQL</span><br><span>{sqltext}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>��ʼִ��ʱ��</span><br><span>{starttime}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>ִ�к�ʱ</span><br><span>{costtime}����</span></p>'),
						// ����˫���¼�
						expandOnDblClick : true
					});
			var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer({
								width : 28
							}), expander,{
								header : '����ʱ��',
								dataIndex : 'starttime',
								width : 140,
								sortable : true
							},{
						header : 'ִ��SQL',
						dataIndex : 'sqltext',
						id : 'sqltext',
						width : 100
					}, {
						header : 'ִ�к�ʱ',
						dataIndex : 'costtime',
						width : 60,
						sortable : true
					}, {
						header : 'Ӱ������',
						dataIndex : 'rowcount',
						width : 60,
						sortable : true
					}]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'jdbcMonitor.ered?reqCode=queryMonitorData'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{
											name : 'monitorid'
										}, {
											name : 'sqltext'
										}, {
											name : 'starttime'
										}, {
											name : 'costtime'
										}, {
											name : 'rowcount'
										}, {
											name : 'type'
										}])
					});

			// ��ҳ����ʱ���ϲ�ѯ����
			store.on('beforeload', function() {
						var starttime = Ext.getCmp('starttime').getValue();
						if (!Ext.isEmpty(starttime)) {
							starttime = starttime.format('Y-m-d').toString();
						}
						this.baseParams = {
							type : Ext.getCmp('type_id').getValue(),
							costtime : Ext.getCmp('costtime').getValue(),
							rowcount : Ext.getCmp('rowcount').getValue(),
							sql : Ext.getCmp('sql').getValue(),
							starttime : starttime
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
									fields : ['value', 'text'],
									data : [[10, '10��/ҳ'], [20, '20��/ҳ'], [50, '50��/ҳ'], [100, '100��/ҳ'], [250, '250��/ҳ'], [500, '500��/ҳ']]
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
						// emptyMsg
						// :
						// "û�з��������ļ�¼",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

			var grid = new Ext.grid.GridPanel({
						title : '<span style="font-weight:normal">JDBCִ�м��</span>',
						iconCls: 'database_refreshIcon',
						renderTo : 'jdbcGridDiv',
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
						autoExpandColumn : 'sqltext',
						cm : cm,
						plugins : expander,
						tbar : [{
									text : '����',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										if (runMode == '0') {
											Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
											return;
										}
										reset();
									}
								}, '->', new Ext.form.ComboBox({
											id : 'type_id',
											hiddenName : 'type',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({
														fields : ['value', 'text'],
														data : [[1, 'INSERT'], [2, 'UPDATE'], [3, 'DELETE'], [4, 'SELECT'], [5, 'CALLPRC']]
													}),
											displayField : 'text',
											valueField : 'value',
											mode : 'local',
											listWidth : 120, // �����б�Ŀ��,Ĭ��Ϊ����ѡ���Ŀ��
											forceSelection : true,
											typeAhead : true,
											emptyText : '��������',
											// editable : false,
											resizable : true,
											width : 100
										}), '-', {
									id : 'starttime',
									name : 'starttime',
									xtype : 'datefield',
									emptyText : 'ִ��ʱ�����',
									format : 'Y-m-d',
									width : 100
								}, '-', {
									id : 'costtime',
									name : 'costtime',
									xtype : 'numberfield',
									emptyText : 'ִ�к�ʱ����',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												query();
											}
										}
									},
									width : 100
								}, '-', {
									id : 'rowcount',
									name : 'rowcount',
									xtype : 'numberfield',
									emptyText : 'Ӱ����������',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												query();
											}
										}
									},
									width : 100
								}, '-', {
									id : 'sql',
									name : 'sql',
									xtype : 'textfield',
									emptyText : 'SQL�ؼ���',
									enableKeyEvents : true,
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												query();
											}
										}
									},
									width : 100
								}, '-', {
									text : '��ѯ',
									iconCls : 'previewIcon',
									handler : function() {
										query();
									}
								}, '-', {
									text : 'ˢ��',
									iconCls : 'arrow_refreshIcon',
									handler : function() {
										store.reload();
									}
								}],
						bbar : bbar
					});
			store.load({
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
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [grid]
					});

			/**
			 * ��ѯ
			 */
			function query() {
				var starttime = Ext.getCmp('starttime').getValue();
				if (!Ext.isEmpty(starttime)) {
					starttime = starttime.format('Y-m-d').toString();
				}
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize,
								type : Ext.getCmp('type_id').getValue(),
								costtime : Ext.getCmp('costtime').getValue(),
								rowcount : Ext.getCmp('rowcount').getValue(),
								sql : Ext.getCmp('sql').getValue(),
								starttime : starttime
							}
						});
			}

			/**
			 * ����
			 */
			function reset() {
				Ext.Msg.confirm('��ȷ��', 'ȷ��Ҫ����JDBC��ؼ�¼��?', function(btn, text) {
							if (btn == 'yes') {
								showWaitMsg();
								Ext.Ajax.request({
											url : 'jdbcMonitor.ered?reqCode=resetMonitorData',
											success : function(response) {
												var resultArray = Ext.util.JSON.decode(response.responseText);
												store.reload();
												Ext.Msg.alert('��ʾ', resultArray.msg);
											},
											failure : function(response) {
												Ext.Msg.alert('��ʾ', '����ʧ��!');
											}
										});
							}
						});
			}

			if (runMode == '0') {
				Ext.Msg.alert('��ʾ', 'Ϊ������ʾϵͳ��������,��ع����ѹر�.����������������ʷ�������!');
			}

		});