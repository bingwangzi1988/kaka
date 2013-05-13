/**
 * ����UI�ۺ�ʾ��(��ͨ��)
 * 
 * @author XiongChun
 * @since 2010-10-27
 */
Ext.onReady(function() {

			var root = new Ext.tree.TreeNode({
						id : '0',
						text : '�й�'
					});
			var node1 = new Ext.tree.TreeNode({
				id : '1',
				text : '����ʡ'
					// href : 'url', // ���ӵ�ַ
					// hrefTarget : 'mainFrame' // ���ӵ�ַ��Ŀ�����
				});
			var node2 = new Ext.tree.TreeNode({
						id : '2',
						iconCls : 'medal_gold_1Icon', // �ڵ�ͼ��
						text : '�������������'
					});
			var node3 = new Ext.tree.TreeNode({
						id : '3',
						text : '�������'
					});
			var node4 = new Ext.tree.TreeNode({
						id : '4',
						iconCls : 'expand-allIcon',
						text : '����������'
					});
			var node5 = new Ext.tree.TreeNode({
						id : '5',
						expanded : true, // �Ƿ�չ���ڵ�
						text : '̨�����'
					});
			var node6 = new Ext.tree.TreeNode({
						id : '6',
						text : '̨����'
					});

			root.appendChild(node1);
			node1.appendChild(node2);
			root.appendChild(node3);
			root.appendChild(node4);
			root.appendChild(node5);
			node5.appendChild(node6);

			// ����һ�������
			var tree = new Ext.tree.TreePanel({
				title : '',
				renderTo : 'treeDiv',
				width : 400,
				// border: false, //���߿�
				useArrows : true, // ��ͷ�ڵ�ͼ��
				root : root,
				autoScroll : true,
				animate : false
					// �Ƿ񶯻���ʾ
				});

			root.expand(false); // trueΪ�ݹ�չ��,falseΪֻչ�����ڵ�

			// �󶨽ڵ㵥���¼�
			tree.on("click", function(node, e) {
						Ext.MessageBox.alert('��ʾ', 'ID:' + node.id + " text:" + node.text);
					});

			// ����һ���Ҽ��˵�
			var contextmenu = new Ext.menu.Menu({
						id : 'theContextMenu',
						items : [{
									iconCls : 'userIcon',
									text : '�����˾͵���',
									handler : function() {
										var selectModel = tree.getSelectionModel(); // ��ȡ��ѡ��ģ��
										var selectNode = selectModel.getSelectedNode(); // ��ȡ��ǰ��ѡ�нڵ����
										Ext.Msg.alert('��ʾ', 'ID:' + selectNode.id + " text:" + selectNode.text);
									}
								}, {
									iconCls : 'user_femaleIcon',
									text : '�ٵ�һ��',
									handler : function() {
										Ext.Msg.alert('��ʾ', '�����ص�!');
									}
								}]
					});
			// ���Ҽ��˵�
			tree.on("contextmenu", function(node, e) {
						e.preventDefault();
						node.select();
						contextmenu.showAt(e.getXY());
					});

			var firstWindow = new Ext.Window({
				title : '������һ(��ͨ��)', // ���ڱ���
				layout : 'fit', // ���ô��ڲ���ģʽ
				width : 250, // ���ڿ��
				height : 300, // ���ڸ߶�
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