/**
 * ���ڷ���
 * 
 * @author XiongChun
 * @since 2010-10-27
 */
Ext.onReady(function() {
			// ���幤����
			var tb = new Ext.Toolbar();
			tb.add({
						text : '����һ',
						iconCls : 'imageIcon',
						handler : function(item) {
							initWindow1();
						}
					}, '-', {
						text : '���ڶ�',
						iconCls : 'imageIcon',
						handler : function(item) {
							initWindow2();
						}
					}, '-', {
						text : '������',
						iconCls : 'imageIcon',
						handler : function(item) {
							initWindow3();
						}
					});
			tb.render(Ext.getBody());

			function initWindow1() {
				var firstWindow = new Ext.Window({
							title : '����һ', // ���ڱ���
							iconCls : 'imageIcon',
							layout : 'fit', // ���ô��ڲ���ģʽ
							width : 400, // ���ڿ��
							height : 200, // ���ڸ߶�
							// tbar : tb, // ������
							closable : true, // �Ƿ�ɹر�
							closeAction : 'hide', // �رղ���
							bodyStyle : 'background-color:#FFFFFF',
							collapsible : true, // �Ƿ������
							maximizable : true, // �����Ƿ�������
							animateTarget : Ext.getBody(),
							border : true, // �߿�������
							pageY : 30, // ҳ�涨λY����
							pageX : document.body.clientWidth / 2 - 400 / 2, // ҳ�涨λX����
							constrain : true,
							// ���ô����Ƿ�������������
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
											firstWindow.hide();
										}
									}]
						});
				firstWindow.show(); // ��ʾ����
			}

			function initWindow2() {
				var secondWindow = new Ext.Window({
							title : '���ڶ�', // ���ڱ���
							iconCls : 'imageIcon',
							layout : 'fit', // ���ô��ڲ���ģʽ
							width : 400, // ���ڿ��
							height : 200, // ���ڸ߶�
							// tbar : tb, // ������
							animateTarget : Ext.getBody(),
							closable : false, // �Ƿ�ɹر�
							closeAction : 'hide', // �رղ���
							collapsible : false, // �Ƿ������
							maximizable : false, // �����Ƿ�������
							border : true, // �߿�������
							pageY : 80, // ҳ�涨λY����
							pageX : document.body.clientWidth / 2 - 400 / 2, // ҳ�涨λX����
							constrain : true,
							// ���ô����Ƿ�������������
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
											secondWindow.hide();
										}
									}]
						});
				secondWindow.show(); // ��ʾ����
			}
			var panel = new Ext.Panel({
						// renderTo : 'panelDiv1', // ��Ⱦ����DomԪ��
						title : '������', // ����
						iconCls : 'book_previousIcon', // ͼ��
						collapsible : true, // �Ƿ������۵�
						animateTarget : Ext.getBody(),
						// contentEl : 'contentDiv', // ����DIV
						// width : 400,
						frame : false,
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
			function initWindow3() {
				var thirdWindow = new Ext.Window({
							title : '������', // ���ڱ���
							iconCls : 'imageIcon',
							layout : 'fit', // ���ô��ڲ���ģʽ
							width : 400, // ���ڿ��
							height : 200, // ���ڸ߶�
							// tbar : tb, // ������
							closable : false, // �Ƿ�ɹر�
							closeAction : 'hide', // �رղ���
							collapsible : true, // �Ƿ������
							maximizable : false, // �����Ƿ�������
							modal : true,
							border : false, // �߿�������
							pageY : 120, // ҳ�涨λY����
							pageX : document.body.clientWidth / 2 - 400 / 2, // ҳ�涨λX����
							constrain : true,
							items : [panel],
							// ���ô����Ƿ�������������
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
											thirdWindow.hide();
										}
									}]
						});
				thirdWindow.show(); // ��ʾ����
			}
		});