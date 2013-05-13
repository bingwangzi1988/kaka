/**
 * ����UI�ۺ�ʾ��(�������߼������Լ���Form�����ݽ���)
 * 
 * @author XiongChun
 * @since 2010-10-28
 */
Ext.onReady(function() {
	var addRoot = new Ext.tree.AsyncTreeNode({
				text : '������',
				expanded : true,
				id : '001'
			});
	var addDeptTree = new Ext.tree.TreePanel({
				loader : new Ext.tree.TreeLoader({
							baseAttrs : {},
							dataUrl : 'treeDemo.ered?reqCode=departmentTreeInit'
						}),
				root : addRoot,
				autoScroll : true,
				animate : false,
				useArrows : false,
				border : false
			});

	// �����������Ľڵ㵥���¼�
	addDeptTree.on('click', function(node) {
				if (!node.isLeaf())
					return false;
				var form = Ext.getCmp("firstForm");
				comboxWithTree.setValue(node.text);
				form.get('parentid').setValue(node.attributes.id);
				// ��Ԫ�ظ�ֵ
				form.get('sortno').setValue(node.attributes.sortno);
				comboxWithTree.collapse();
			});

	var comboxWithTree = new Ext.form.ComboBox({
		id : 'parentdeptname',
		store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
		editable : false,
		value : ' ',
		emptyText : '��ѡ��...',
		fieldLabel : '�ϼ�����',
		anchor : '100%',
		mode : 'local',
		triggerAction : 'all',
		maxHeight : 390,
		// ���������ʾģ��,addDeptTreeDiv��Ϊ��ʾ������������
		tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn
	});

	// ���������������չ���¼�
	comboxWithTree.on('expand', function() {
				// ��UI���ҵ�treeDiv����
				addDeptTree.render('addDeptTreeDiv');
				// addDeptTree.root.expand(); //ֻ�ǵ�һ���������������
				addDeptTree.root.reload(); // ÿ�����������������

			});

	var firstForm = new Ext.form.FormPanel({
				id : 'firstForm',
				name : 'firstForm',
				labelWidth : 60, // ��ǩ���
				// frame : true, // �Ƿ���Ⱦ����屳��ɫ
				defaultType : 'textfield', // ��Ԫ��Ĭ������
				labelAlign : 'right', // ��ǩ���뷽ʽ
				bodyStyle : 'padding:5 5 5 5', // ��Ԫ�غͱ����ı߾�
				items : [{
							name : 'parentid',
							id : 'parentid',
							fieldLabel : '���ű��',
							readOnly : true,
							anchor : '100%'
						}, {
							id : 'sortno',
							name : 'sortno',
							fieldLabel : '�����', // �ڵ�ĸ�������
							readOnly : true,
							anchor : '100%'
						}, comboxWithTree]
			});

	var firstWindow = new Ext.Window({
				title : '��׼������(ֻ��ѡ��Ҷ�ӽڵ�,�ڵ�����ֵ�ͱ�����)', // ���ڱ���
				layout : 'fit', // ���ô��ڲ���ģʽ
				width : 400, // ���ڿ��
				height : 160, // ���ڸ߶�
				closable : false, // �Ƿ�ɹر�
				collapsible : true, // �Ƿ������
				maximizable : true, // �����Ƿ�������
				border : false, // �߿�������
				constrain : true, // ���ô����Ƿ�������������
				pageY : 20, // ҳ�涨λX����
				pageX : document.body.clientWidth / 2 - 350 / 2, // ҳ�涨λY����
				items : [firstForm]
			});
	firstWindow.show(); // ��ʾ����
});