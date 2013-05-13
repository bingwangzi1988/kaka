/**
 * ����ۺ�ʾ��
 * 
 * @author XiongChun
 * @since 2010-10-20
 */
Ext.onReady(function() {

			// ����һ���м�չ����(��Ҫ��CM��grid�����м���)
			var expander = new Ext.grid.RowExpander({
						tpl : new Ext.Template('<p style=margin-left:70px;><span style=color:Teal;>��ĿID</span><br><span>{xmid}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>��Ŀ����</span><br><span>{xmmc}</span></p>',
								'<p style=margin-left:70px;><span style=color:Teal;>����</span><br><span>{cd}</span></p>'),
						// ������˫���Ƿ�չ��
						expandOnDblClick : true
					});

			// �����Զ���ǰҳ�к�
			var rownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});

			// ������ģ��
			var cm = new Ext.grid.ColumnModel([rownum, expander, {
				header : '��ĿID', // �б���
				dataIndex : 'xmid', // ��������:��Storeģ�Ͷ�Ӧ
				sortable : true
					// �Ƿ������
				}, {
				header : '����',
				dataIndex : 'sfdlbm',
				hidden : true, // ������
				sortable : true,
				width : 50
					// �п�
				}, {
				header : '��Ŀ����',
				dataIndex : 'xmmc',
				sortable : true,
				width : 200
			}, {
				header : '��Ŀ�ȼ�',
				dataIndex : 'xmrj'
			}, {
				header : '���',
				dataIndex : 'gg'
			}, {
				header : '��λ',
				dataIndex : 'dw',
				width : 60
			}, {
				header : '����״̬',
				dataIndex : 'qybz',
				// ��ʾrender���÷�(����ת��,��render��<eRedG4:ext.codeRender/>��ǩ����)
				renderer : QYBZRender,
				width : 60
			}, {
				header : '����',
				dataIndex : 'jx',
				width : 60
			}]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						// ��ȡ���ݵķ�ʽ
						proxy : new Ext.data.HttpProxy({
									url : 'gridDemo.ered?reqCode=querySfxmDatas'
								}),
						// ���ݶ�ȡ��
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT', // ��¼����
									root : 'ROOT' // Json�е��б����ݸ��ڵ�
								}, [{
											name : 'xmid' // Json�е�����Keyֵ
										}, {
											name : 'sfdlbm'
										}, {
											name : 'xmmc'
										}, {
											name : 'xmrj'
										}, {
											name : 'gg'
										}, {
											name : 'dw'
										}, {
											name : 'qybz'
										}, {
											name : 'jx'
										}, {
											name : 'cd'
										}, {
											name : 'yybm'
										}, {
											name : 'ggsj'
										}])
					});

			/**
			 * ��ҳ����ʱ��Ĳ�������
			 */
			// ��ҳ����ʱ���ϲ�ѯ����
			store.on('beforeload', function() {
						this.baseParams = {
							xmmc : Ext.getCmp('xmmc').getValue()
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
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

			// ��񹤾���
			var tbar = new Ext.Toolbar({
						items : [{
									xtype : 'textfield',
									id : 'xmmc',
									name : 'xmmc',
									emptyText : '��������Ŀ����',
									width : 150,
									enableKeyEvents : true,
									// ��Ӧ�س���
									listeners : {
										specialkey : function(field, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												queryCatalogItem();
											}
										}
									}
								}, {
									text : '��ѯ',
									iconCls : 'page_findIcon',
									handler : function() {
										queryCatalogItem();
									}
								}, {
									text : 'ˢ��',
									iconCls : 'page_refreshIcon',
									handler : function() {
										store.reload();
									}
								}]
					});

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
						plugins : expander, // �м�չ��
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
            //grid.expandRow(2);
			// ҳ���ʼ�Զ���ѯ����
			// store.load({params : {start : 0,limit : bbar.pageSize}});

			// ����ģ��
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [grid]
					});

			// ��ѯ�������
			function queryCatalogItem() {
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize,
								xmmc : Ext.getCmp('xmmc').getValue()
							}
						});
			}

		});