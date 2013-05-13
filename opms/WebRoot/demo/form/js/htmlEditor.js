/**
 * �������ı�����
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {

			var panel = new Ext.form.FormPanel({
						layout : 'fit',
						frame : true,
						items : [{
									id : 'htmleditor',
									name : 'htmleditor',
									xtype : 'htmleditor'
								}]
					});

			var firstWindow = new Ext.Window({
						title : '<span style="font-weight:normal">���ı�����</span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 600, // ���ڿ��
						height : 380, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						animateTarget : Ext.getBody(),
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 600 / 2, // ҳ�涨λX����
						items : [panel], // Ƕ��ı����
						buttons : [{ // ���ڵײ���ť����
							text : '����', // ��ť�ı�
							iconCls : 'acceptIcon', // ��ťͼ��
							handler : function() { // ��ť��Ӧ����
								var value = Ext.getCmp('htmleditor').getValue();
								// Ext.MessageBox.alert('��ʾ', value);
								alert(value);
							}
						}, {	// ���ڵײ���ť����
									text : '����', // ��ť�ı�
									iconCls : 'tbar_synchronizeIcon', // ��ťͼ��
									handler : function() { // ��ť��Ӧ����
										panel.form.reset();
									}
								}]
					});
			firstWindow.show(); // ��ʾ����
			
			Ext.getCmp('htmleditor').on('initialize',function(){
			  Ext.getCmp('htmleditor').focus();
			})
		});