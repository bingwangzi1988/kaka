/**
 * ����ۺ�ʾ����(�ϼƱ��)
 * 
 * @author XiongChun
 * @since 2010-10-20
 */
Ext.onReady(function() {

			// �����Զ���ǰҳ�к�
			var rownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});

			// ������ģ��
			var cm = new Ext.grid.ColumnModel([rownum, {
				header : '����˳���', // �б���
				dataIndex : 'sxh', // ��������:��Storeģ�Ͷ�Ӧ
				width : 200,
				sortable : true
					// �Ƿ������
				}, {
				header : '���˱���',
				dataIndex : 'grbm',
				hidden : true,
				sortable : true
			}, {
				header : '����',
				dataIndex : 'xm'
			}, {
				header : '�Ա�',
				dataIndex : 'xb'
			}, {
				header : '������',
				dataIndex : 'xnl'
			}, {
				header : '�����ܶ�',
				dataIndex : 'fyze'
			}, {
				header : 'ҽ������',
				dataIndex : 'ybbx'
			}, {
				header : '�Ը����',
				dataIndex : 'zfje'
			}, {
				header : '����ʱ��',
				dataIndex : 'jssj'
			}, {
				header : '����ҽԺ',
				dataIndex : 'yymc'
			}]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						// ��ȡ���ݵķ�ʽ
						proxy : new Ext.data.HttpProxy({
									url : 'gridDemo.ered?reqCode=queryBalanceInfo'
								}),
						// ���ݶ�ȡ��
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT', // ��¼����
									root : 'ROOT' // Json�е��б����ݸ��ڵ�
								}, [{
											name : 'sxh' // Json�е�����Keyֵ
										}, {
											name : 'grbm'
										}, {
											name : 'xm'
										}, {
											name : 'xb'
										}, {
											name : 'xnl'
										}, {
											name : 'fyze'
										}, {
											name : 'ybbx'
										}, {
											name : 'zfje'
										}, {
											name : 'jssj'
										}, {
											name : 'yymc'
										}])
					});

			/**
			 * ��ҳ����ʱ��Ĳ�������
			 */
			// ��ҳ����ʱ���ϲ�ѯ����
			store.on('beforeload', function() {
						this.baseParams = {
							xm : Ext.getCmp('xm').getValue()
						};
					});
			// ÿҳ��ʾ��������ѡ���
			var pagesize_combo = new Ext.form.ComboBox({
						name : 'pagesize',
						triggerAction : 'all',
						mode : 'local',
						store : new Ext.data.ArrayStore({
									fields : ['value', 'text'],
									data : [[10, '10��/ҳ'], [20, '20��/ҳ'], [50, '50��/ҳ'], [100, '100��/ҳ'], [250, '250��/ҳ'], [500, '500��/ҳ']]
								}),
						valueField : 'value',
						displayField : 'text',
						value : '20',
						editable : false,
						width : 85
					});
			var number = parseInt(pagesize_combo.getValue());
			// �ı�ÿҳ��ʾ����reload����
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

			// ��ҳ������
			var bbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '��ʾ{0}����{1}��,��{2}��',
						plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������
						emptyMsg : "û�з��������ļ�¼",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo, '-', {
									text : '�ϼ�',
									iconCls : 'addIcon',
									handler : function() {
										summary.toggleSummary();
									}
								}]
					});

			// ��񹤾���
			var tbar = new Ext.Toolbar({
						items : [{
									xtype : 'textfield',
									id : 'xm',
									name : 'xm',
									emptyText : '����������',
									width : 150,
									enableKeyEvents : true,
									// ��Ӧ�س���
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												queryBalanceInfo();
											}
										}
									}
								}, {
									text : '��ѯ',
									iconCls : 'page_findIcon',
									handler : function() {
										queryBalanceInfo();
									}
								}, {
									text : 'ˢ��',
									iconCls : 'page_refreshIcon',
									handler : function() {
										store.reload();
									}
								}]
					});

			// �ϼ�
			var summary = new Ext.ux.grid.GridSummary();

			// ���ʵ��
			var grid = new Ext.grid.GridPanel({
						// ���������,Ĭ��Ϊ���壬�Ҳ�ϲ�����壬����������ʽ�����ʽΪ��������
						title : '<span style="font-weight:normal">����ۺ���ʾ��</span>',
						renderTo : 'gridDiv', // ��JSPҳ���DIVԪ��ID��Ӧ
						height : 500,
						autoScroll : true,
						frame : true,
						region : 'center', // ��VIEWPORT����ģ�Ͷ�Ӧ���䵱center���򲼾�
						store : store, // ���ݴ洢
						stripeRows : true, // ������
						cm : cm, // ��ģ��
						tbar : tbar, // ��񹤾���
						bbar : bbar,// ��ҳ������
						plugins : [summary], // �ϼ�
						viewConfig : {
							// ����������������, �����Զ���չ�Զ�ѹ��, �����������Ƚ��ٵ����
							forceFit : true
						},
						loadMask : {
							msg : '���ڼ��ر������,���Ե�...'
						}
					});

			// �Ƿ�Ĭ��ѡ�е�һ������
			bbar.on("change", function() {
						// grid.getSelectionModel().selectFirstRow();

					});

			// ҳ���ʼ�Զ���ѯ����
			// store.load({params : {start : 0,limit : bbar.pageSize}});

			// ����ģ��
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [grid]
					});

			// ��ѯ�������
			function queryBalanceInfo() {
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize,
								xm : Ext.getCmp('xm').getValue()
							},
							callback :fnSumInfo
						});
			}

			/**
			 * ���ܱ��
			 */
			function fnSumInfo() {
				Ext.Ajax.request({
							url : 'gridDemo.ered?reqCode=sumBalanceInfo',
							success : function(response) { // �ص�������1������
								summary.toggleSummary(true);
								summary.setSumValue(Ext.decode(response.responseText));
							},
							failure : function(response) {
								Ext.MessageBox.alert('��ʾ', '��������ʧ��');
							}
						});
			}

		});