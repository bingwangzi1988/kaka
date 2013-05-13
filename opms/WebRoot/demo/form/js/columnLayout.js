/**
 * �������в���
 * 
 * @author XiongChun
 * @since 2010-11-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						// frame : true, // �Ƿ���Ⱦ����屳��ɫ
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [{
									layout : 'column',
									border : false,
									items : [{
												columnWidth : .4,
												layout : 'form',
												labelWidth : 40, // ��ǩ���
												defaultType : 'textfield',
												border : false,
												items : [{
															fieldLabel : '����1',
															name : 'name1',
															anchor : '90%'
														}, {
															fieldLabel : '����2',
															name : 'name2',
															anchor : '90%'
														}, {
															fieldLabel : '����3',
															name : 'name3',
															anchor : '90%'
														}, {
															fieldLabel : '����4',
															name : 'name4',
															anchor : '90%'
														}, {
															fieldLabel : '����5',
															name : 'name5',
															anchor : '90%'
														}]
											}, {
												columnWidth : .6,
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
														}, {
															fieldLabel : '���֤��3',
															name : 'pid3',
															anchor : '100%'
														}, {
															fieldLabel : '���֤��4',
															name : 'pid4',
															anchor : '100%'
														}]
											}]
								}]
					});

			var firstWindow = new Ext.Window({
						title : '<span style="font-weight:normal">Column����</span>', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 430, // ���ڿ��
						height : 230, // ���ڸ߶�
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