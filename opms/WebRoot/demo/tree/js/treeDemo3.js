/**
 * ����UI�ۺ�ʾ��(��ͨ��)
 * 
 * @author XiongChun
 * @since 2010-10-27
 */
Ext.onReady(function() {

			var root = new Ext.tree.AsyncTreeNode({
						id : '00',
						text : '�й�',
						// ��ʾһ��δѡ�еĸ�ѡ��
						checked : false,
						expanded : true

					});
			// ��񹤾���
			var tbar = new Ext.Toolbar({
						items : [{
									text : '��ȡѡ�нڵ�ֵ',
									iconCls : 'arrow_refreshIcon',
									handler : function() {
										// ��ȡѡ�нڵ�����
										var checkedNodes = tree.getChecked();
										if (Ext.isEmpty(checkedNodes)) {
											Ext.Msg.alert('��ʾ', 'û��ѡ���κνڵ�');
											return;
										}
										var strID = '';
										Ext.each(checkedNodes, function(node) {
													strID = strID + node.id + ',';
												});
										// ���Խ����ַ���������̨����
										Ext.Msg.alert('��ʾ', strID);

									}
								}, {
									text : 'ˢ��ѡ�нڵ�',
									iconCls : 'tbar_synchronizeIcon',
									handler : function() {
										var selectModel = tree.getSelectionModel();
										var selectNode = selectModel.getSelectedNode();
										if (Ext.isEmpty(selectNode)) {
											Ext.Msg.alert('��ʾ', 'û��ѡ���κνڵ�!');
										} else {
											selectNode.reload();
										}
									}
								}]
					});

			// ����һ�������
			var tree = new Ext.tree.TreePanel({
				loader : new Ext.tree.TreeLoader({
							baseAttrs : {},
							dataUrl : 'treeDemo.ered?reqCode=queryAreas4CheckTree'
						}),
				title : '',
				tbar : tbar,
				renderTo : 'treeDiv',
				width : 400,
				// border: false, //���߿�
				useArrows : true, // ��ͷ�ڵ�ͼ��
				root : root, // ���ڵ�
				height : 500,
				autoScroll : true, // �������ʱ����������
				animate : false
					// �Ƿ񶯻���ʾ
				});
			// ѡ�и��ڵ�
			tree.getRootNode().select();

			// �󶨽ڵ㵥���¼�
			tree.on("click", function(node, e) {
						// Ext.MessageBox.alert('��ʾ', 'ID:' + node.id + " text:"
						// + node.text);
					});

			var firstWindow = new Ext.Window({
				title : '��������(��ѡ��)', // ���ڱ���
				layout : 'fit', // ���ô��ڲ���ģʽ
				width : 300, // ���ڿ��
				height : 400, // ���ڸ߶�
				closable : false, // �Ƿ�ɹر�
				collapsible : true, // �Ƿ������
				maximizable : true, // �����Ƿ�������
				border : false, // �߿�������
				constrain : true, // ���ô����Ƿ�������������
				pageY : 20, // ҳ�涨λY����
				pageX : document.body.clientWidth / 2 - 300 / 2, // ҳ�涨λX����
				items : [tree]
					// Ƕ��ı����
				});
			firstWindow.show(); // ��ʾ����

		});