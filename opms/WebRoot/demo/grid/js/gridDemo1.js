/**
 * ����ۺ�ʾ��
 * 
 * @author XiongChun
 * @since 2010-10-20
 */
Ext.onReady(function() {
			// ��ѡ��
			var sm = new Ext.grid.CheckboxSelectionModel();

			// �����Զ���ǰҳ�к�
			var rownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});

			// ������ģ��
			var cm = new Ext.grid.ColumnModel([rownum, sm, {
						header : '����', // �б���
						dataIndex : 'edit',
						width : 35,
						renderer : iconColumnRender
					}, {
						header : '��ĿID', // �б���
						dataIndex : 'xmid', // ��������:��Storeģ�Ͷ�Ӧ
						sortable : true
						// �Ƿ������
				}	, {
						header : '����',
						dataIndex : 'sfdlbm',
						hidden : true, // ������
						sortable : true,
						width : 50
						// �п�
				}	, {
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
						renderer : colorRender, // ��ʾrender���÷�(�Զ���)
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
					}, {
						header : '����',
						dataIndex : 'cd',
						width : 200
					}, {
						header : 'ҽԺ����',
						dataIndex : 'yybm'
					}, {
						header : '����ʱ��',
						dataIndex : 'ggsj'
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
								}, '-', {
									text : '��ȡѡ����',
									handler : function() {
										getCheckboxValues();
									}
								}]
					});

			// ����Ҽ��˵�
			var contextmenu = new Ext.menu.Menu({
						id : 'theContextMenu',
						items : [{
									text : '�鿴����',
									iconCls : 'previewIcon',
									handler : function() {
										// ��ȡ��ǰѡ���ж���
										var record = grid.getSelectionModel().getSelected();
										var xmmc = record.get('xmmc');
										Ext.MessageBox.alert('��ʾ', xmmc);
									}
								}, {
									text : '�����б�',
									iconCls : 'page_excelIcon',
									handler : function() {
										// ��ȡ��ǰѡ���ж���
										var record = grid.getSelectionModel().getSelected();
										var xmmc = record.get('xmmc');
										Ext.MessageBox.alert('��ʾ', xmmc);
									}
								}]
					});

			// ���ʵ��
			var grid = new Ext.grid.GridPanel({
						// ���������,Ĭ��Ϊ���壬�Ҳ�ϲ�����壬����������ʽ�����ʽΪ��������
						title : '<span style="font-weight:normal">����ۺ���ʾһ</span>',
						renderTo : 'gridDiv', // ��JSPҳ���DIVԪ��ID��Ӧ
						height : 500,
						frame : true,
						autoScroll : true,
						region : 'center', // ��VIEWPORT����ģ�Ͷ�Ӧ���䵱center���򲼾�
						store : store, // ���ݴ洢
						stripeRows : true, // ������
						cm : cm, // ��ģ��
						sm : sm, // ��ѡ��
						tbar : tbar, // ��񹤾���
						bbar : bbar,// ��ҳ������
						viewConfig : {
			// ����������������, �����Զ���չ�Զ�ѹ��, �����������Ƚ��ٵ����
						// forceFit : true
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

			// С���ʵ���¼�
			grid.on("cellclick", function(pGrid, rowIndex, columnIndex, e) {
						var store = pGrid.getStore();
						var record = store.getAt(rowIndex);
						var fieldName = pGrid.getColumnModel().getDataIndex(columnIndex);
						// columnIndexΪС���������е�����,������0��ʼ
						// ����Ҫ�ǳ�ע��!!!!!
						if (fieldName == 'edit' && columnIndex == 2) {
							var xmmc = record.get("xmmc");
							// ������Ϳ��Լ����������κ�������
							Ext.MessageBox.alert('��ʾ', xmmc);
						}
					});

			// ������Ԫ��˫���¼�
			grid.on("celldblclick", function(pGrid, rowIndex, columnIndex, e) {
				var record = pGrid.getStore().getAt(rowIndex);
				var fieldName = pGrid.getColumnModel().getDataIndex(columnIndex);
				var cellData = record.get(fieldName);
					// Ext.MessageBox.alert('��ʾ', cellData);
				});

			// ������˫���¼�
			grid.on('rowdblclick', function(pGrid, rowIndex, event) {
						// ��ȡ�����ݼ�
						var record = pGrid.getStore().getAt(rowIndex);
						// ��ȡ��Ԫ�����ݼ�
						var data = record.get("xmmc");
						Ext.MessageBox.alert('��ʾ', "˫���е�����Ϊ:" + rowIndex);
					});

			// �������Ҽ��˵�
			grid.on("rowcontextmenu", function(grid, rowIndex, e) {
						e.preventDefault(); // ����Ĭ���Ҽ��¼�
						grid.getSelectionModel().selectRow(rowIndex); // ѡ�е�ǰ��
						contextmenu.showAt(e.getXY());
					});

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

			// ��ȡѡ����
			function getCheckboxValues() {
				// ����һ���м���JS����
				var rows = grid.getSelectionModel().getSelections();
				if (Ext.isEmpty(rows)) {
					Ext.MessageBox.alert('��ʾ', '��û��ѡ���κ�����!');
					return;
				}
				// ��JS�����е��м�������������,�ָ����ַ���
				var strChecked = jsArray2JsString(rows, 'xmid');
				Ext.MessageBox.alert('��ʾ', strChecked);
				// ���ѡ�����ݺ�����Դ����̨��������
			}

			// ��ʾrender���÷�
			function colorRender(value,cellMetaData,record) {
				//alert(record.data.xmid); ���Ի�ȡ��Record����Ŷ
				if (value == '��') {
					return "<span style='color:red; font-weight:bold'>" + value + "</span>";
				}
				if (value == 'ƿ') {
					return "<span style='color:green; font-weight:bold'>" + value + "</span>";
				}
				return value;
			}

			// ����һ��ͼ����
			function iconColumnRender(value) {
				return "<a href='javascript:void(0);'><img src='" + webContext + "/resource/image/ext/edit1.png'/></a>";;
			}

		});