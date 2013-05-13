/**
 * ����������(�ύ���������)
 * 
 * @author XiongChun
 * @since 2010-08-20
 */
Ext.onReady(function() {
			var btn1 = new Ext.Button({
						text : '��ʾ��1',
						applyTo : 'btn1_div',
						handler : function() {
							Ext.MessageBox.alert('��ʾ', '��򵥵���д��ʽ,<b>�޽�������!</b>');
							// alert('�Ҳ��ᱻ�������ʾ��������Ŷ!');
						}
					});
			var btn2 = new Ext.Button({
						text : '��ʾ��2',
						applyTo : 'btn2_div',
						handler : function() {
							// �Ի���Ļص�����
							Ext.MessageBox.alert('��ʾ', '�н�������!', function() {
										alert('�Ҹղű���������,�ҿ�!');
									});
						}
					});
			var btn3 = new Ext.Button({
						text : 'ȷ�Ͽ�1',
						applyTo : 'btn3_div',
						handler : function() {
							Ext.MessageBox.show({
										title : '��ʾ',
										msg : '���Ǵ�ү����',
										buttons : Ext.MessageBox.YESNO,
										animEl : Ext.getBody(),
										icon : Ext.MessageBox.QUESTION,
										fn : function(btn) {
											// �ص�����
											if (btn == 'yes') {
												// ��
												alert(btn + ' ��');
											} else {
												// ��
												alert(btn + ' ��');
											}
										}
									});

						}
					});
			var btn4 = new Ext.Button({
						text : '�����',
						applyTo : 'btn4_div',
						handler : function() {
							Ext.MessageBox.show({
										title : '��ʾ',
										msg : '���������ĵ�ַ��Ϣ��',
										width : 300,
										buttons : Ext.MessageBox.OKCANCEL,
										multiline : true, // �Ƿ�����������
										// �������뵭���ο���
										animEl : Ext.getBody(),
										fn : function(btn, text) {
											if (btn == 'ok') {
												alert('�������ֵΪ:' + text);
											}
										}
									});
						}
					});

			var btn5 = new Ext.Button({
						text : 'ȷ�Ͽ�2',
						applyTo : 'btn5_div',
						handler : function() {
							// ȷ�Ͽ�ļ�д��
							Ext.MessageBox.confirm('��ȷ��', '���Ǵ�ү����?', function(btn) {
										alert(btn);
									})
						}
					});

			var btn6 = new Ext.Button({
						text : '�ȴ���',
						applyTo : 'btn6_div',
						handler : function() {
							Ext.MessageBox.show({
										title : '��ȴ�',
										msg : '���ڳ�ʼ��...',
										width : 300,
										wait : true,
										waitConfig : {
											interval : 200
										}, // ���������ٶ�
										closable : false
									});
							// �ڴ���ɹ��Ļص������йرյȴ�����
							// Ext.MessageBox.hide();
						}
					});

			var firstWindow = new Ext.Window({
						title : '��Ϣ�Ի���', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 400, // ���ڿ��
						height : 100, // ���ڸ߶�
						// tbar : tb, // ������
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 400 / 2, // ҳ�涨λX����
						constrain : true,
						// ���ô����Ƿ�������������
						items : [new Ext.Panel({
									contentEl : 'btn'
								})]
					});
			firstWindow.show(); // ��ʾ����
		});