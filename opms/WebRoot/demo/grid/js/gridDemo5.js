/**
 * ����ۺ�ʾ��(������)
 * 
 * @author XiongChun
 * @since 2010-10-20
 */
Ext.onReady(function() {

			// �����Զ���ǰҳ�к�
			var rownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 35
					});

			// ������ģ��
			var cm = new Ext.grid.ColumnModel([rownum, {
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
									data : [[100, '100��/ҳ'], [1000, '1000��/ҳ'], [2000, '2000��/ҳ'], [3000, '3000��/ҳ'], [4000, '4000��/ҳ'], [5000, '5000��/ҳ']]
								}),
						valueField : 'value',
						displayField : 'text',
						value : '1000',
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
						// ���������pagesize�Ƚϴ�����ʹ��
						view : new Ext.ux.grid.BufferView({
									// rowHeight : 34, //�Զ����и�
									// �Ƿ���ʱ��Ⱦ����Dom�ڵ�
									scrollDelay : true
								}),
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
			 store.load({params : {start : 0,limit : bbar.pageSize}});

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