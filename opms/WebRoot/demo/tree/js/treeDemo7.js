/**
 * ���Panel ��autoload����
 * 
 * @author XiongChun
 * @since 2010-10-28
 */
Ext.onReady(function() {

			var root = new Ext.tree.AsyncTreeNode({
						text : '������',
						expanded : true,
						id : '001'
					});

			var deptTree = new Ext.ux.tree.TreeGrid({
						loader : new Ext.tree.TreeLoader({
									dataUrl : 'treeDemo.ered?reqCode=departmentTreeInit'
								}),
						title : '',
						root : root,
						animate : false,
						renderTo : 'treeDiv',
						width : 650, // ����ָ��,������ʾ������
						//height : 400,
						columns : [{
									header : '��������',
									dataIndex : 'text',
									width : 200
								}, {
									header : '����ID',
									dataIndex : 'id',
									width : 120
								}, {
									header : '������ID',
									dataIndex : 'parentid',
									width : 120
								}, {
									header : '�Զ���ID',
									dataIndex : 'customid',
									width : 60
								}, {
									header : '�����',
									dataIndex : 'sortno',
									width : 60
								}],
						useArrows : true,
						border : true
					});

			deptTree.on("click", function(node, e) {
						Ext.MessageBox.alert('��ʾ', 'ID:' + node.id + " text:" + node.text);
					});
		});