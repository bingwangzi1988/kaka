/**
 * Excel����
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
						header : '��ĿID',
						dataIndex : 'xmid',
						sortable : true
					}, {
						header : '����',
						dataIndex : 'sfdlbm',
						hidden : true,
						width : 50
					}, {
						header : '��Ŀ����',
						dataIndex : 'xmmc',
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
						hidden : true,
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
						dataIndex : 'yybm',
						hidden : true
					}, {
						header : '����ʱ��',
						dataIndex : 'ggsj'
					}, {
						id : '_blank',
						header : '',
						dataIndex : '_blank'
					}]);

			/**
			 * ���ݴ洢
			 */
			var store = new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
									url : 'excelReportDemo.ered?reqCode=queryCatalogs4Import'
								}),
						reader : new Ext.data.JsonReader({
									totalProperty : 'TOTALCOUNT',
									root : 'ROOT'
								}, [{
											name : 'xmid'
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
						displayMsg : '��ʾ{0}����{1}��,��{2}��',
						plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������						emptyMsg : "û�з��������ļ�¼",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

			var grid = new Ext.grid.GridPanel({
						title : '<span style="font-weight:normal">ҽԺ�շ�Ŀ¼</span>',
						renderTo : 'catalogGridDiv',
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
						tbar : [{
									text : 'ѡ�����ļ�',
									iconCls : 'page_excelIcon',
									handler : function() {
										window.show();
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
			bbar.on("change", function() {
						grid.getSelectionModel().selectFirstRow();
					});
			var formpanel = new Ext.form.FormPanel({
						id : 'formpanel',
						name : 'formpanel',
						defaultType : 'textfield',
						labelAlign : 'right',
						labelWidth : 99,
						frame : true,
						fileUpload : true,
						items : [{
									fieldLabel : '��ѡ�����ļ�',
									name : 'theFile',
									id : 'theFile',
									inputType : 'file',
									allowBlank : true,
									anchor : '99%'
								}]
					});

			var window = new Ext.Window({
						layout : 'fit',
						width : 380,
						height : 100,
						resizable : false,
						draggable : true,
						closeAction : 'hide',
						title : '����Excel',
						modal : false,
						collapsible : true,
						titleCollapse : true,
						maximizable : false,
						buttonAlign : 'right',
						border : false,
						animCollapse : true,
						animateTarget : Ext.getBody(),
						constrain : true,
						items : [formpanel],
						buttons : [{
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										var theFile = Ext.getCmp('theFile').getValue();
										if (Ext.isEmpty(theFile)) {
											Ext.Msg.alert('��ʾ', '����ѡ����Ҫ�����xls�ļ�...');
											return;
										}
										if (theFile.substring(theFile.length - 4, theFile.length) != ".xls") {
											Ext.Msg.alert('��ʾ', '��ѡ����ļ���ʽ����,ֻ�ܵ���.xls�ļ�!');
											return;
										}
										formpanel.form.submit({
													url : 'excelReportDemo.ered?reqCode=importExcel',
													waitTitle : '��ʾ',
													method : 'POST',
													waitMsg : '���ڴ�������,���Ժ�...',
													success : function(form, action) {
														store.load({
																	params : {
																		start : 0,
																		limit : bbar.pageSize
																	}
																});
														window.hide();
														// Ext.MessageBox.alert('��ʾ',
														// action.result.msg);

													},
													failure : function(form, action) {
														var msg = action.result.msg;
														Ext.MessageBox.alert('��ʾ', '�������ݱ���ʧ��:<br>' + msg);
													}
												});

									}
								}, {
									text : '�ر�',
									id : 'btnReset',
									iconCls : 'deleteIcon',
									handler : function() {
										window.hide();
									}
								}]
					});

			window.show();
			/**
			 * ����
			 */
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [grid]
					});

			/**
			 * ��ѯҽԺ�շ�Ŀ¼
			 */
			function queryCatalogItem() {
				store.load({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			}

		});