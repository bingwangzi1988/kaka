/**
 * ����UI���(������)
 * 
 * @author XiongChun
 * @since 2010-10-27
 */
Ext.onReady(function() {
			// һ���˵�
			var contextmenu = new Ext.menu.Menu({
						items : [{
									text : '�鿴����',
									iconCls : 'previewIcon',
									handler : function(item) {
										Ext.Msg.alert('��ʾ', item.text);
									}
								}, {
									text : '�����б�',
									iconCls : 'page_excelIcon',
									handler : function(item) {
										Ext.Msg.alert('��ʾ', item.text);
									}
								}, {
									text : '�Ƿ�����',
									checked : true,
									checkHandler : function(item) {
										alert(item.checked);
										// Ext.Msg.alert('��ʾ', item.checked);
									}
								}, {
									text : '�����˵�',
									iconCls : 'cupIcon',
									menu : new Ext.menu.Menu({
												items : [{
															text : '����',
															iconCls : 'medal_gold_1Icon',
															handler : function(item) {
																Ext.Msg.alert('��ʾ', item.text);
															}
														}, {
															text : '����',
															iconCls : 'medal_silver_3Icon',
															handler : function(item) {
																Ext.Msg.alert('��ʾ', item.text);
															}
														}]
											})
								}]
					});

			// ���幤����
			var tb = new Ext.Toolbar();
			tb.add({
				text : '�����˵�',
				iconCls : 'window_caise_listIcon',
				menu : contextmenu
					// �����˵�
				}, '-', {
				text : 'ɾ��',
				iconCls : 'page_delIcon',
				handler : function(item) {
					Ext.Msg.alert('��ʾ', item.text);
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
			}, '-', {
				id : 'remark',
				name : 'remark',
				xtype : 'tbtext',
				text : '<span style=font-size:12px>��ͨ�ı�</span>',
				width : 100
			}, '->', {
				id : 'ksrq',
				name : 'ksrq',
				xtype : 'datefield',
				emptyText : 'ѡ������',
				format : 'Y-m-d',
				width : 100
			}, '-', {
				id : 'keyword',
				name : 'keyword',
				emptyText : '�ؼ��ֹ���',
				xtype : 'textfield',
				enableKeyEvents : true,
				// ��Ӧ�س���
				listeners : {
					specialkey : function(field, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
						}
					}
				},
				width : 100
			}, {
				text : '��ѯ',
				iconCls : 'page_findIcon',
				handler : function(item) {
					Ext.Msg.alert('��ʾ', item.text);
				}
			});
			var firstWindow = new Ext.Window({
						title : '����UI���(���������˵���)', // ���ڱ���
						layout : 'fit', // ���ô��ڲ���ģʽ
						width : 700, // ���ڿ��
						height : 200, // ���ڸ߶�
						//tbar : tb, // ������
						closable : false, // �Ƿ�ɹر�
						collapsible : true, // �Ƿ������
						maximizable : true, // �����Ƿ�������
						border : false, // �߿�������
						pageY : 20, // ҳ�涨λY����
						pageX : document.body.clientWidth / 2 - 700 / 2, // ҳ�涨λX����
						constrain : true,
						// ���ô����Ƿ�������������
						items : [new Ext.form.FormPanel({
									tbar : tb
								})]
					});
			firstWindow.show(); // ��ʾ����

		});