/**
 * Tab��ǩ������
 * 
 * @author XiongChun
 * @since 2010-10-27
 */
Ext.onReady(function() {
			var tabs = new Ext.TabPanel({
						region:'center',
						enableTabScroll : true,
						//autoWidth : true,
						height : 200,
						buttonAlign : 'center',
						buttons : [{
									text : '�л�1',
									iconCls : 'acceptIcon',
									handler : function() {
										tabs.activate(1);
									}
								}, {
									text : '����',
									iconCls : 'deleteIcon',
									handler : function() {
										Ext.getCmp('tab2').disable();
									}
								}, {
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										Ext.getCmp('tab2').enable();
									}
								}]
					});
			// ÿһ��Tab�����Կ���Ϊһ��Panel
			tabs.add({
						title : '������Ϣ',
						html : '������Ϣ',
						
						// tbar:tb, //������
						// items:[],
						iconCls : 'book_previousIcon', // ͼ��
						closable : true
					});
			tabs.add({
						id : 'tab2',
						title : '��ϸ��Ϣ',
						html : '��ϸ��Ϣ'
					});
			tabs.activate(0);
			
			// ����ģ��
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [tabs]
					});

		});