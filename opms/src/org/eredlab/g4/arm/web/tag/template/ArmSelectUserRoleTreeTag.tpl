<!-- ��<eRedG4:arm.ArmSelectUserRoleTreeTag/>��ǩ���ɵĴ��뿪ʼ -->
<div id="selectUserRoleTreeDiv"></div>
<script type="text/javascript">
Ext.onReady(function() {
#foreach($menu in $menuList)
	var node_${menu.menuid} = new Ext.tree.TreeNode({
		text:'${menu.menuname}',	
#if(${menu.checked} == "false")
	checked:false,
#else
    checked:true,
#end
        menuid:'${menu.menuid}',
		id:'id_node_${menu.menuid}'
	});
#end

#foreach($menu in $menuList)
#if(${menu.isRoot}!="true")
	node_${menu.parentid}.appendChild(node_${menu.menuid});
#end
#end

var selectMenuTree = new Ext.tree.TreePanel({
			autoHeight : false,
			autoWidth : false,
			autoScroll : true,
			animate : false,
			rootVisible : true,
			border : false,
			containerScroll : true,
			applyTo : 'selectUserRoleTreeDiv',
			tbar : [{
				text : '����',
				id : 'selectMenu_saveBtn',
				iconCls : 'acceptIcon',
				handler : function() {
				         if (runMode == '0') {
							Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
							return;
						  }				
					     var checkedNodes = selectMenuTree.getChecked();
					     var menuid = "";
						 for(var i = 0; i < checkedNodes.length; i++) {
						   var checkNode = checkedNodes[i];
					       menuid = menuid + checkNode.attributes.menuid + "," ;  
						 }
						 saveSelectedMenu(menuid);
				 }
		    }, '-', {
				text : 'չ��',
				iconCls : 'expand-allIcon',
				handler : function() {
					selectMenuTree.expandAll();
				}
		    }, '-', {
				text : '����',
				iconCls : 'collapse-allIcon',
				handler : function() {
					selectMenuTree.collapseAll();
				}
		    }],
			root : node_01
  });
//��������Ϊ���ⲻ��ѡ�н����ӽڵ�����⴦��
  selectMenuTree.expandAll();
  selectMenuTree.collapseAll();
#foreach($menu in $menuList)
#if(${menu.expanded}=="true")
	node_${menu.menuid}.expand();
#end
#end

//������Ȩ����
function saveSelectedMenu(pMenuid){
		showWaitMsg();
		Ext.Ajax.request({
					url : './user.ered?reqCode=saveSelectedUserRoleMenu',
					success : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('��ʾ', resultArray.msg);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('��ʾ', resultArray.msg);
					},
					params : {
						menuid : pMenuid		
						}
				});
}

/** ***************** ����ѡ��֧�ֿ�ʼ ******************** */
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
			onCheckboxClick : Ext.tree.TreeEventModel.prototype.onCheckboxClick
					.createSequence(function(e, node) {
								node.cascadeParent();
								node.cascadeChildren();
							})
		});
/** ***************** ����ѡ��֧�ֽ��� ******************** */
	
})
</script>
<!-- ��<eRedG4:arm.SelectMenuTreeTag/>��ǩ���ɵĴ������ -->