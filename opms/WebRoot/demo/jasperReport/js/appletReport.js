/**
 * Applet�����ӡ
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
						dataIndex : 'yybm'
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
									url : 'jasperReportDemo.ered?reqCode=queryCatalogs4Print'
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

			/**
			 * ��ҳ����ʱ��Ĳ�������
			 */
			store.on('beforeload', function() {
						this.baseParams = {
							xmmc : Ext.getCmp('xmmc').getValue()
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
						displayMsg : '��ʾ{0}����{1}��,��{2}��',
						plugins : new Ext.ux.ProgressBarPager(), // ��ҳ������						emptyMsg : "û�з��������ļ�¼",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

			var grid = new Ext.grid.GridPanel({
						title : '<span style="font-weight:normal">ҽԺ�շ�Ŀ¼<font color=red>[����ȷ�������������������,�����ܽ�ʧЧ]</font></span>',
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
						tbar : [new Ext.form.TextField({
											id : 'xmmc',
											name : 'xmmc',
											emptyText : '��������Ŀ����',
											enableKeyEvents : true,
											listeners : {
												specialkey : function(field, e) {
													if (e.getKey() == Ext.EventObject.ENTER) {
														queryCatalogItem();
													}
												}
											},
											width : 150
										}), {
									text : '��ѯ',
									iconCls : 'page_findIcon',
									handler : function() {
										queryCatalogItem();
									}
								}, '-', {
									text : '��ӡ(Applet)',
									tooltip : '�Ե�������Ƕ��Applet�ķ�ʽʵ�ֱ���չʾ�Ϳͻ��˴�ӡ����',
									iconCls : 'printerIcon',
									handler : function() {
										printCatalog1();
									}
								}, '-', {
									text : '��ӡ(Applet,֧�ֻص�����)',
									tooltip : '����ģʽ�����ڻص��������д��ӡ��־���߼�¼��ӡ����',
									iconCls : 'printerIcon',
									handler : function() {
										printCatalog2();
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
								limit : bbar.pageSize,
								xmmc : Ext.getCmp('xmmc').getValue()
							}
						});
			}

			/**
			 * ��ӡһ
			 */
			function printCatalog1() {
				showWaitMsg('����׼����������,���Ե�...');
				Ext.Ajax.request({
							url : 'jasperReportDemo.ered?reqCode=buildReportDataObject',
							success : function(response) {
								hideWaitMsg();
								doPrint('hisCatalogReport');
							},
							failure : function(response) {
								hideWaitMsg();
								Ext.Msg.alert('��ʾ', "׼���������ݶ���������,����!");
							}
						});
			}

			/**
			 * ��ӡ��(֧�ֻص�����)
			 */
			function printCatalog2() {
				showWaitMsg('����׼����������,���Ե�...');
				Ext.Ajax.request({
							url : 'jasperReportDemo.ered?reqCode=buildReportDataObject',
							success : function(response) {
								hideWaitMsg();
								doPrintWithCallback('hisCatalogReport');
							},
							failure : function(response) {
								hideWaitMsg();
								Ext.Msg.alert('��ʾ', "׼���������ݶ���������,����!");
							}
						});
			}

		});

/**
 * �رմ�ӡ���ڵĻص�����
 */
function fnPrintCallback() {
	// �����ڴ˼�¼��ӡ����
	Ext.Msg.alert('��ʾ', '���ڹر���,�����ڴ��¼��Ļص��������¼��ӡ����');
}