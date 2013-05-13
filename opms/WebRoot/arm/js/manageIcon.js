/**
 * ϵͳԤ��ͼ��
 * 
 * @author XiongChun
 * @since 2010-05-20
 */
Ext.onReady(function() {
			var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
						header : 'ͼ����',
						dataIndex : 'iconid',
						hidden : false,
						width : 80,
						sortable : true
					}, {
						header : 'Ԥ��',
						dataIndex : 'previewpath',
						renderer : function(value) {
							return '<img src=' + value + ' />';
						},
						width : 50
					}, {
						id : 'cssname',
						header : 'CSS����',
						dataIndex : 'cssname',
						width : 150
					}, {
						id : 'filename',
						header : '�ļ���',
						dataIndex : 'filename',
						width : 150
					}, {
						id : 'accesspath',
						header : '����·��',
						dataIndex : 'accesspath'
					}]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : './resource.ered?reqCode=queryIconItems'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{
											name : 'iconid'
										}, {
											name : 'previewpath'
										}, {
											name : 'cssname'
										}, {
											name : 'filename'
										}, {
											name : 'accesspath'
										}])
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
						displayMsg : '�� {2} ������ǰҳ��ʾ�� {0}���� {1}��',
						emptyMsg : "û�з��������ļ�¼",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

			var grid = new Ext.grid.GridPanel({
						title : '<span style="font-weight:normal">ϵͳԤ��ͼ��</span>',
						iconCls:'imagesIcon',
						renderTo : 'iconGridDiv',
						height : 500,
						autoScroll : true,
						region : 'center',
						store : store,
						loadMask : {
							msg : '���ڼ��ر������,���Ե�...'
						},
						stripeRows : true,
						frame : true,
						autoExpandColumn : 'accesspath',
						cm : cm,
						tbar : [{
									text : 'ˢ��',
									iconCls : 'page_refreshIcon',
									handler : function() {
										store.reload();
									}
								}, '->', new Ext.form.TextField({
											id : 'queryParam',
											name : 'queryParam',
											emptyText : '�������ļ���|CSS����',
											enableKeyEvents : true,
											listeners : {
												specialkey : function(field, e) {
													if (e.getKey() == Ext.EventObject.ENTER) {
														queryIconItem();
													}
												}
											},
											width : 150
										}), {
									text : '��ѯ',
									iconCls : 'page_findIcon',
									handler : function() {
										queryIconItem();
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
			grid.on("celldblclick", function(grid, rowIndex, columnIndex, e) {
						var store = grid.getStore();
						var record = store.getAt(rowIndex);
						var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
						var celldata = record.get(fieldName);
						// copyToClipboard(celldata);
						iconPanel.getForm().loadRecord(record);
						iconWindow.show();
					});

			var iconPanel = new Ext.form.FormPanel({
						id : 'iconPanel',
						name : 'iconPanel',
						defaultType : 'textfield',
						labelAlign : 'right',
						labelWidth : 65,
						frame : true,
						items : [{
									fieldLabel : 'CSS����',
									name : 'cssname',
                                    maxLength:50,
									allowBlank : true,
									anchor : '99%'
								}, {
									fieldLabel : '�ļ���',
									name : 'filename',
                                    maxLength:50,
									allowBlank : true,
									anchor : '99%'
								}, {
									fieldLabel : '����·��',
									name : 'accesspath',
									allowBlank : true,
									anchor : '99%'
								}]
					});

			var iconWindow = new Ext.Window({
						layout : 'fit',
						width : 400,
						height : 120,
						resizable : false,
						draggable : true,
						closeAction : 'hide',
						title : 'ϵͳͼ��',
						// iconCls : 'page_addIcon',
						modal : false,
						collapsible : true,
						titleCollapse : true,
						maximizable : false,
						buttonAlign : 'right',
						border : false,
						animCollapse : true,
						// pageY : 20,
						// pageX : document.body.clientWidth / 2 - 400 / 2,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [iconPanel]
					});

			/**
			 * ����
			 */
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [grid]
					});

			/**
			 * ��ѯ����
			 */
			function queryIconItem() {
				var queryParam = Ext.getCmp('queryParam').getValue();
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize,
								queryParam : queryParam
							}
						});
			}

			/**
			 * ��ʾ����
			 */
			function showWindowInit() {
				var record = grid.getSelectionModel().getSelected();
				if (Ext.isEmpty(record)) {
					grid.getSelectionModel().selectFirstRow();
				}
				iconPanel.getForm().loadRecord(record);
				iconWindow.show();
			}
		});