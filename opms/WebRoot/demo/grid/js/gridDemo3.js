/**
 * ����ۺ�ʾ��
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
				header : '��ĿID', // �б���
				dataIndex : 'xmid', // ��������:��Storeģ�Ͷ�Ӧ
				sortable : true
					// �Ƿ������
				}, {
				header : '��Ŀ����',
				dataIndex : 'xmmc',
				sortable : true,
				editor : new Ext.grid.GridEditor(new Ext.form.TextField({
							// ֻ��ԭ�����ݱ༭��Ч,������һ�еĳ�����Ч
							allowBlank : false
						})),
				width : 200
			}, {
				header : '��Ŀ�ȼ�',
				dataIndex : 'xmrj',
				editor : new Ext.grid.GridEditor(new Ext.form.TextField({}))
			}, {
				header : '���',
				dataIndex : 'gg',
				editor : new Ext.grid.GridEditor(new Ext.form.TextField({}))
			}, {
				header : '��λ',
				dataIndex : 'dw',
				editor : new Ext.grid.GridEditor(new Ext.form.TextField({})),
				width : 60
			}, {
				header : '����״̬',
				dataIndex : 'qybz',
				// ��ʾrender���÷�(����ת��,��render��<eRedG4:ext.codeRender/>��ǩ����)
				renderer : QYBZRender,
				editor : new Ext.grid.GridEditor(new Ext.form.ComboBox({
							store : QYBZStore,
							mode : 'local',
							triggerAction : 'all',
							valueField : 'value',
							displayField : 'text',
							allowBlank : false,
							forceSelection : true,
							typeAhead : true
						})),
				width : 80
			}, {
				header : '����ʱ��',
				dataIndex : 'ggsj',
				renderer : Ext.util.Format.dateRenderer('Y-m-d'),
				editor : new Ext.grid.GridEditor(new Ext.form.DateField({
							format : 'Y-m-d'
						})),
				width : 140
			}]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						// true to clear all modified record information each
						// time the store is loaded
						pruneModifiedRecords : true,
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
											name : 'ggsj',
											type : 'date',
											dateFormat : 'Y-m-d'
										}])
					});

			// ����һ��Record
			var MyRecord = Ext.data.Record.create([{
						name : 'xmid',
						type : 'string'
					}, {
						name : 'sfdlbm',
						type : 'string'
					}, {
						name : 'xmmc',
						type : 'string'
					}, {
						name : 'xmrj',
						type : 'string'
					}, {
						name : 'gg',
						type : 'string'
					}, {
						name : 'dw',
						type : 'string'
					}, {
						name : 'qybz',
						type : 'string'
					}, {
						name : 'ggsj',
						type : 'data'
					}]);

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
									text : '����һ��',
									iconCls : 'addIcon',
									handler : function() {
										var row = new MyRecord({});
										row.set('qybz', '1'); // ����ֵ
										grid.stopEditing();
										store.insert(0, row);
										grid.startEditing(0, 2);
									}
								}, {
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										saveRow();
									}
								}, {
									text : 'ɾ��һ��',
									iconCls : 'deleteIcon',
									handler : function() {
										var sm = grid.getSelectionModel();
										var cell = sm.getSelectedCell();
										if (Ext.isEmpty(cell)) {
											Ext.Msg.alert('��ʾ', '��û��ѡ����');
										}
										var record = store.getAt(cell[0]);
										Ext.MessageBox.alert('��ʾ', '��ĿID:' + record.get('xmid'))
										store.remove(record);
									}
								}]
					});

			// ���ʵ��
			var grid = new Ext.grid.EditorGridPanel({
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
						clicksToEdit : 1, // ������˫������༭״̬
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
			function queryCatalogItem() {
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize,
								xmmc : Ext.getCmp('xmmc').getValue()
							}
						});
			}

			// ����
			function saveRow() {
				var m = store.modified.slice(0); // ��ȡ�޸Ĺ���record�������
				if (Ext.isEmpty(m)) {
					Ext.MessageBox.alert('��ʾ', 'û��������Ҫ����!');
					return;
				}
				if (!validateEditGrid(m, 'xmmc')) {
					Ext.Msg.alert('��ʾ', '��Ŀ�����ֶ�����У�鲻�Ϸ�,����������!', function() {
								grid.startEditing(0, 2);
							});
					return;
				}
				var jsonArray = [];
				// ��record�������ת��Ϊ��Json�������
				Ext.each(m, function(item) {
							jsonArray.push(item.data);
						});
				// �ύ����̨����
				Ext.Ajax.request({
							url : 'gridDemo.ered?reqCode=saveDirtyDatas',
							success : function(response) { // �ص�������1������
								var resultArray = Ext.util.JSON.decode(response.responseText);
								Ext.Msg.alert('��ʾ', resultArray.msg);
							},
							failure : function(response) {
								Ext.MessageBox.alert('��ʾ', '���ݱ���ʧ��');
							},
							params : {
								// ϵ�л�ΪJson���ϸ�ʽ�����̨����
								dirtydata : Ext.encode(jsonArray)
							}
						});
			}

			// ��������еĿɱ༭��Ԫ�����ݺϷ���
			function validateEditGrid(m, colName) {
				for (var i = 0; i < m.length; i++) {
					var record = m[i];
					var rowIndex = store.indexOfId(record.id);
					var value = record.get(colName);
					if (Ext.isEmpty(value)) {
						// Ext.Msg.alert('��ʾ', '����У�鲻�Ϸ�');
						return false;
					}
					var colIndex = cm.findColumnIndex(colName);
					var editor = cm.getCellEditor(colIndex).field;
					if (!editor.validateValue(value)) {
						// Ext.Msg.alert('��ʾ', '����У�鲻�Ϸ�');
						return false;
					}
				}
				return true;
			}

		});