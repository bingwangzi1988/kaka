/**
 * ���ʵ��
 * 
 * @author XiongChun
 * @since 2010-10-27
 */
Ext.onReady(function() {
			// ���幤����
			var tb = new Ext.Toolbar();
			tb.add({
						text : '����',
						iconCls : 'page_delIcon',
						handler : function(item) {
							panel.disable(true);
						}
					}, '-', {
						text : '����',
						iconCls : 'tbar_synchronizeIcon',
						handler : function(item) {
							Ext.Msg.alert('��ʾ', item.text);
						}
					}, '-', {
						text : 'ˢ��',
						iconCls : 'page_refreshIcon',
						// pressed:true, //��ť����
						handler : function(item) {
							Ext.Msg.alert('��ʾ', item.text);
						}
					});

			var panel = new Ext.Panel({
						region : 'north',
						title : '������', // ����
						iconCls : 'book_previousIcon', // ͼ��
						collapsible : true, // �Ƿ������۵�
						contentEl : 'contentDiv', // ����DIV
						// width : 400,
						//frame : true,
						//bodyStyle : 'background-color:#FFFFFF',
						height : 100,
						buttonAlign : 'center',
						buttons : [{
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										// TODO
									}
								}, {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										// TODO
									}
								}]
					});

			var panel1 = new Ext.Panel({
						region : 'south',
						title : '������', // ����
						iconCls : 'book_previousIcon', // ͼ��
						collapsible : true, // �Ƿ������۵�
						tbar : tb, // ������
						contentEl : 'contentDiv2', // ����DIV
						// width : 400,
						frame : false,
						height : 150,
						buttonAlign : 'right',
						buttons : [{
									text : '����',
									iconCls : 'acceptIcon',
									handler : function() {
										// TODO
									}
								}, {
									text : '�ر�',
									iconCls : 'deleteIcon',
									handler : function() {
										// TODO
									}
								}]
					});

			var panel2 = new Ext.Panel({
						region : 'center',
						title : '������(�����ⲿURL)', // ����
						iconCls : 'book_previousIcon', // ͼ��
						collapsible : false, // �Ƿ������۵�
						width : 670,
						autoScroll : true,
						frame : false,
						height : 200,
						autoLoad : {
							url : webContext + '/demo/treeDemo.ered?reqCode=treeDemo7Init',
							scripts : true, // �ⲿ�ļ��Ƿ�����ű�֧��
							text : '���Խ���ģ��������������ҳ��,��ȴ�...'
						}
					});

			// ����ģ��
			var viewport = new Ext.Viewport({
						layout : 'border',
						items : [panel, panel1, panel2]
					});
		});