/**
 * ������ѡ����ѡ��
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var firstForm = new Ext.form.FormPanel({
						id : 'firstForm',
						name : 'firstForm',
						labelWidth : 40, // ��ǩ���
						// frame : true, //�Ƿ���Ⱦ����屳��ɫ
						defaultType : 'textfield', // ��Ԫ��Ĭ������
						labelAlign : 'right', // ��ǩ���뷽ʽ
						bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
						items : [new Ext.form.Radio({
											name : 'sex',// ������ͬ�ĵ�ѡ�����Ϊһ��
											fieldLabel : '�Ա�',
											boxLabel : '��'
										}), new Ext.form.Radio({
											name : 'sex',// ������ͬ�ĵ�ѡ�����Ϊһ��
											boxLabel : 'Ů'
										}), new Ext.form.Checkbox({
											name : 'swim',
											fieldLabel : '����',
											boxLabel : '��Ӿ'
										}), new Ext.form.Checkbox({
											name : 'walk',
											boxLabel : 'ɢ��'
										}), new Ext.form.Checkbox({
											name : 'basketball',
											checked : true,
											boxLabel : '������'
										})]
					});

			var firstWindow = new Ext.Window({
						title : '������ѡ����ѡ��', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 200, // ���ڿ��
						height : 300, // ���ڸ߶�
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						constrain : true, // ���ô����Ƿ�������������
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 200 / 2, // ҳ�涨λX����
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