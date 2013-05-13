/**
 * �Ự���
 * 
 * @author XiongChun
 * @since 2010-09-03
 */
Ext.onReady(function() {
			var sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), sm, {
						header : '�û����',
						dataIndex : 'userid',
						width : 75,
						hidden:true,
						sortable : true
					}, {
						header : '�Ự����ʱ��',
						dataIndex : 'sessionCreatedTime',
						width : 140
					}, {
						header : '��¼�˻�',
						dataIndex : 'account',
						width : 150
					}, {
						header : '����',
						dataIndex : 'username',
						width : 90
					}, {
						header : '�ͻ���IP',
						dataIndex : 'loginIP',
						width : 100
					}, {
						header : '�ͻ��������',
						dataIndex : 'explorer',
						width : 120
					}, {
						header : '�ỰID',
						dataIndex : 'sessionID',
						width : 250
					},{
						dataIndex : '_blank',
						id : '_blank'
					}]);

			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'sessionMonitor.ered?reqCode=getSessionList'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{
											name : 'sessionID'
										}, {
											name : 'userid'
										}, {
											name : 'username'
										}, {
											name : 'account'
										}, {
											name : 'loginIP'
										}, {
											name : 'explorer'
										}, {
											name : 'sessionCreatedTime'
										}])
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
									data : [[10, '10��/ҳ'], [20, '20��/ҳ'], [50, '50��/ҳ'], [100, '100��/ҳ'], [250, '250��/ҳ'], [500, '500��/ҳ'], [1000, '1000��/ҳ']]
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
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					})

			var grid = new Ext.grid.GridPanel({
						title : '<span style="font-weight:normal">�Ự���</span>',
						iconCls: 'user_commentIcon',
						renderTo : 'sessionGrid',
						height : 510,
						store : store,
						region : 'center',
						loadMask : {
							msg : '���ڼ�������,���Ե�...'
						},
						stripeRows : true,
						frame : true,
						autoExpandColumn : '_blank',
						cm : cm,
						sm : sm,
						tbar : [{
									text : 'ɱ���Ự',
									iconCls : 'deleteIcon',
									handler : function() {
										if (runMode == '0') {
											Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
											return;
										}
										killSession();
									}
								}, '-', {
									text : 'ˢ��',
									iconCls : 'arrow_refreshIcon',
									handler : function() {
										refreshSessionTable();
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
						//grid.getSelectionModel().selectFirstRow();
					});

			bbar.on("change", function() {
						//grid.getSelectionModel().selectFirstRow();
					});
			/**
			 * ����
			 */
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [grid]
					});

			/**
			 * ɱ���Ự
			 */
			function killSession() {
				var rows = grid.getSelectionModel().getSelections();
				if (Ext.isEmpty(rows)) {
					Ext.Msg.alert('��ʾ', '����ѡ��ɱ���ĻỰ!');
					return;
				}
				var strChecked = jsArray2JsString(rows, 'sessionID');
				showWaitMsg('����ɱ���Ự,��ȴ�...');
				Ext.Ajax.request({
							url : 'sessionMonitor.ered?reqCode=killSession',
							success : function(response) {
								var resultArray = Ext.util.JSON.decode(response.responseText);
								refreshSessionTable();
								Ext.Msg.alert('��ʾ', resultArray.msg);
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert('��ʾ', resultArray.msg);
							},
							params : {
								strChecked : strChecked
							}
						});
			}

			/**
			 * ˢ�»Ự��ر��
			 */
			function refreshSessionTable() {
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			}
		});