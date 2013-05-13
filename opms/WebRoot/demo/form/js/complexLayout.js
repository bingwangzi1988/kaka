/**
 * �������ϲ���
 * 
 * @author XiongChun
 * @since 2010-11-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						// frame : true, // �Ƿ���Ⱦ����屳��ɫ
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						labelWidth : 40,
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .5,
												layout : 'form',
												labelWidth : 40, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '����1',
															name : 'name1',
															anchor : '95%'
														}, {
															fieldLabel : '����2',
															name : 'name2',
															anchor : '95%'
														}]
											}, {
												columnWidth : .5,
												layout : 'form',
												labelWidth : 65, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '���֤��1',
															name : 'pid1',
															anchor : '100%'
														}, {
															fieldLabel : '���֤��2',
															name : 'pid2',
															anchor : '100%'
														}]
											}]
								}, {
									fieldLabel : '����',
									labelWidth : 40,
									name : 'jg',
									xtype : 'textfield',
									anchor : '100%'
								}, {
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .5,
												layout : 'form',
												labelWidth : 40, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '����1',
															name : 'age1',
															anchor : '95%'
														}, {
															fieldLabel : '����2',
															name : 'age2',
															anchor : '95%'
														}]
											}, {
												columnWidth : .5,
												layout : 'form',
												labelWidth : 65, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '��ѧרҵ1',
															name : 'field1',
															anchor : '100%'
														}, {
															fieldLabel : '��ѧרҵ2',
															name : 'field2',
															anchor : '100%'
														}]
											}]
								}, {
									fieldLabel : '��ע',
									labelWidth : 40,
									name : 'remark',
									xtype : 'textarea',
									anchor : '100%'
								}]
					});

			var firstWindow = new Ext.Window({
						title : '<span style="font-weight:normal">�ۺϲ���1</span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 430, // ���ڿ��
						height : 280, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						animateTarget : Ext.getBody(),
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 450 / 2, // ҳ�涨λX����
						items : [firstForm], // Ƕ��ı����
						buttons : [{ // ���ڵײ���ť����
							text : '����', // ��ť�ı�
							iconCls : 'tbar_synchronizeIcon', // ��ťͼ��
							handler : function() { // ��ť��Ӧ����
								firstForm.form.reset();
							}
						}]
					});
			firstWindow.show(); // ��ʾ����

		});