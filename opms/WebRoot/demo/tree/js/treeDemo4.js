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
				checked : false
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
						}]
			});

	// ����һ�������
	var tree = new Ext.tree.TreePanel({
		loader : new Ext.tree.TreeLoader({
					baseAttrs : {},
					dataUrl : 'treeDemo.ered?reqCode=queryAreas4CheckTree2'
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
	// �첽���ص������Ҫ֧�ּ���ѡ��,�����һ����ȫ��չ��
	tree.expandAll();

	// �󶨽ڵ㵥���¼�
	tree.on("click", function(node, e) {
				// Ext.MessageBox.alert('��ʾ', 'ID:' + node.id + " text:"
				// + node.text);
			});
    
	//��ѡ��״̬����¼�,���ӻ����ߵ�Ч��
	tree.on('checkchange', function(node, checked) {
				if (checked) {
					node.getUI().addClass('node_checked');
				} else {
					node.getUI().removeClass('node_checked');
				}
			});

	var firstWindow = new Ext.Window({
		title : '��������(������ѡ��)', // ���ڱ���
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

	/** ***************** ����ѡ��֧�ֿ�ʼ ******************** */
	/**
	 * ���ڼ���ѡ�е�˵��������ѡ�����ʹ�÷��첽�������ڵ�ķ�ʽ, ��Ϊ�첽���صķ�ʽ���ӽڵ��ǰ��蹹��ģ�
	 * ��������ѡ�нڵ��ʱ��������ӽڵ㻹û�м���չ���������ӽڵ��ǲ��ܱ�ѡ�еġ���ˣ�������Ҫ����ѡ��
	 * �����󣬽���ʹ�÷��첽�����������������Ҫʹ���첽���أ���ô������ڼ��غ����нڵ��Զ�չ�������нڵ�
	 * �����������������Ҳ�Ѿ�ʧȥ���첽���ص������ˡ������÷���������
	 */
	function cascadeParent() {
		var pn = this.parentNode;
		if (!pn || !Ext.isBoolean(this.attributes.checked))
			return;
		if (this.attributes.checked) {// ����ѡ��
			pn.getUI().toggleCheck(true);
		} else {// ����δѡ��
			var b = true;
			Ext.each(pn.childNodes, function(n) {
						if (n.getUI().isChecked()) {
							return b = false;
						}
						return true;
					});
			if (b)
				pn.getUI().toggleCheck(false);
		}
		pn.cascadeParent();
	}
	function cascadeChildren() {
		var ch = this.attributes.checked;
		if (!Ext.isBoolean(ch))
			return;
		Ext.each(this.childNodes, function(n) {

					n.getUI().toggleCheck(ch);
					n.cascadeChildren();
				});
	}
	// ΪTreeNode������Ӽ������ڵ���ӽڵ�ķ���
	Ext.apply(Ext.tree.TreeNode.prototype, {
				cascadeParent : cascadeParent,
				cascadeChildren : cascadeChildren
			});
	// Checkbox������������ڵ���ӽڵ�
	Ext.override(Ext.tree.TreeEventModel, {
				onCheckboxClick : Ext.tree.TreeEventModel.prototype.onCheckboxClick.createSequence(function(e, node) {
							node.cascadeParent();
							node.cascadeChildren();
						})
			});
		/** ***************** ����ѡ��֧�ֽ��� ******************** */
	});