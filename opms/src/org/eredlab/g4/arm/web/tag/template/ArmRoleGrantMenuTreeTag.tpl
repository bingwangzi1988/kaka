<!-- ��<eRedG4:arm.RoleGrantMenuTree/>��ǩ���ɵĴ��뿪ʼ -->
<div id="${key}TreeDiv"></div>
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

var ${key}Tree = new Ext.tree.TreePanel({
			autoHeight : false,
			autoWidth : false,
			autoScroll : true,
			animate : false,
			rootVisible : true,
			border : false,
			containerScroll : true,
			applyTo : '${key}TreeDiv',
			tbar : [{
				text : '����',
				id : '${key}_saveBtn',
				iconCls : 'acceptIcon',
				handler : function() {
				        authoriseRole('grant','Cmrole');
				 }
		    }, '-', {
				text : 'չ��',
				iconCls : 'expand-allIcon',
				handler : function() {
					${key}Tree.expandAll();
				}
		    }, '-', {
				text : '����',
				iconCls : 'collapse-allIcon',
				handler : function() {
					${key}Tree.collapseAll();
				}
		    }],
			root : node_01
  });
//��������Ϊ���ⲻ��ѡ�н����ӽڵ�����⴦��
  ${key}Tree.expandAll();
  ${key}Tree.collapseAll();
#foreach($menu in $menuList)
#if(${menu.expanded}=="true")
	node_${menu.menuid}.expand();
#end
#end


function saveG(operateParams)
{
	 if (runMode == '0') {
			Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
			return;
		}	
		var checkedNodes = ${key}Tree.getChecked();
		var menuid = "";
		for(var i = 0; i < checkedNodes.length; i++) {
			   var checkNode = checkedNodes[i];
		       menuid = menuid + checkNode.attributes.menuid + "," ;  
		 }
		saveGrant(menuid,operateParams);
}

//������Ȩ����
function saveGrant(pMenuid,operateParams){
		showWaitMsg();
		Ext.Ajax.request({
					url : './role.ered?reqCode=saveGrant',
					success : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('��ʾ', resultArray.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('��ʾ', resultArray.msg);
					},
					params : {
						menuid : pMenuid,
						key: '${authorizelevel}'
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
	/*
		     * ��Ȩģ��
		     * */
		    
		    var userRoleStore = new Ext.data.Store({
				 baseParams:{valueflag:'1',valueflagvalue:':'},
		      proxy:new Ext.data.HttpProxy({url:_contextPath_ + '/common/codeSelect_getUserRole.action'}),
		      reader: new Ext.data.JsonReader({}, [
		        {name: "code"},
		        {name: "codedesc"}
		      ]),
		      remoteSort:false
		      });
//			userRoleStore.load();
			
			var authoriseId=new Ext.form.ComboBox({
//				id:'authoriseId',
				name : 'authoriseId',
				hiddenName : 'authoriseId',
				fieldLabel : '��ȨԱ��',
				emptyText : '��ѡ��',
				triggerAction : 'all',
				store:userRoleStore,
				displayField : 'codedesc',
				valueField : 'code',
				mode : 'local',
				forceSelection : false, // ѡ�����ݱ���Ϊ�����б������
				editable : false,
				typeAhead : true,
				resizable : true,
				allowBlank : false,
				anchor : '100%'
			});

		    function authoriseRole(operation,operateObject){
		    	var authoriseRoleForm = new Ext.form.FormPanel({	
		    		height:200,
		    		labelWidth : 90,
		    		frame : true,
		    		defaultType : 'textfield',
		    		labelAlign : 'right',
		    		bodyStyle : 'padding:5 5 5 5',
		    		items : [
//		    		         {
//		    			fieldLabel : '��ȨԱ��',
//		    			name : 'authoriseId',
////		    			minLength:7,
////		    			store:userRoleStore,
////		    			"xtype":"dcombo",
//		    			allowBlank : false,
//		    			anchor : '100%'
//		    		}, 
		    		authoriseId
		    		,
		    		{
		    			fieldLabel : '��Ȩ����',
		    			name : 'authorisePwd',
		    			inputType : 'password',
		    			allowBlank : false,
		    			anchor : '100%'
		    		}, {
		    			fieldLabel : '��������',
		    			name : 'operateType',
		    			value:operation,
		    			hidden:true,
		    			anchor : '100%'
		    		}, {
		    			fieldLabel : '��������',
		    			name : 'operateObject',
		    			value:operateObject,
		    			hidden:true,
		    			anchor : '100%'
		    		}/*, {
		    			fieldLabel : '������Ϣ',
		    			name : 'operateInfo',
//		    			hidden:true,
		    			anchor : '100%'
		    		}*/]	
		    	});
		    	
		    	var authoriseRoleWin = new Ext.Window({
		    		title : '������Ȩ',
		    		layout : 'fit',
		    		width : 300,
		    		height : 220,
		    		collapsible : true,
		    		maximizable : true,
		    		modal : true, 
		    		closable : true,
		    		animCollapse : true,
		    		closeAction : 'hide',
		    		border : false,
		    		constrain : true,
		    		pageY : 40,
		    		pageX : document.body.clientWidth / 2 - 380 / 2,
		    		items : [authoriseRoleForm],
		    		buttons : [{
		    			text : '��Ȩ',
		    			iconCls : 'keyIcon',
		    			handler : function execute(){
		    				if(authoriseRoleForm.getForm().isValid()){
		    					authoriseRoleForm.form.submit({
		    						url : _contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
		    						success : function(form, action) {
		    							authoriseRoleWin.hide();
		    							var result = Ext.util.JSON.decode(action.response.responseText);
		    							var operateParams = Ext.util.JSON.encode(result.msg);
		    							execObject(operation,operateParams);
		    						},
		    						failure : function(form, action) {
		    							var result = Ext.util.JSON.decode(action.response.responseText);
		    							authoriseRoleWin.hide();
		    							Ext.MessageBox.alert('��ʾ', result.msg);
		    						},
		    						waitTitle:"���Ժ�",
		            				waitMsg:"��Ȩ��֤�С���"
		    					});
		    				}
		    			}
		    		},{
		    			text : 'ȡ��',
		    			iconCls : 'lockIcon',
		    			handler : function cancel(){
		    				authoriseRoleWin.hide();
		    			}
		    		}]
		    	});
		    	
		    	
//		    	authoriseWin.show();
//		    	alert("operation=="+operation+"    operateObject=="+operateObject);
		    	
		    	//��ѯ�˵�Ȩ�ޱ�
		    	 Ext.Ajax.request({
		    		   url:_contextPath_ + '/jsp/conmg/Eaoperaterole_getRole.action',
		    		   params:{operateObject:operateObject},
		    		   callback:function(opts,success,response){
		    			   var jsonobjs=Ext.decode(response.responseText);
		    			   if(jsonobjs.success==true)
		    			   {
		    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
		    				   var params = Ext.util.JSON.decode(operateParams);
//		    				   alert("tableName=="+params.tableName);
		    				   if(params.tableName==operateObject)
		    				   {
		    					   var updateFlag=params.updateFlag;
		    					     if(operation == 'grant'){
		    							if(updateFlag==1){
		    								userRoleStore.load({
		    									params : {
		    										tableName : params.tableName,
		    										valueflag:'1',
		    										valueflagvalue:':'
		    									}
		    								});
		    								authoriseRoleWin.show();//��Ҫ��Ȩ
		    							}
		    							else{
		    								execNoRole(operation,operateObject);//����Ҫ��Ȩ
		    							}
		    						}else
		    						{
		    							execNoRole(operation,operateObject);//����Ҫ��Ȩ
		    						}
		    				   }
		    			   }else{
		    				   execNoRole(operation,operateObject);//Ĭ��Ϊ����Ҫ��Ȩ
		    			   }
		    		   }
		    	   });

		    	
		    }


		    //����Ȩ
		    function execNoRole(operation,operateObject)
		    {
		    	 Ext.Ajax.request({
		    		   url:_contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
		    		   params:{operateType:operation,operateObject:operateObject},
		    		   callback:function(opts,success,response){
		    			   var jsonobjs=Ext.decode(response.responseText);
		    			   if(jsonobjs.success==true)
		    			   {
		    				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
		    				   execObject(operation,operateParams);
		    			   }else
		    			   {
		    				   Ext.Msg.alert('��ʾ', jsonobjs.msg);
		    			   }
		    		   }
		    	   });
		    }

		     function execObject(operation,operateParams){
		    	 if(operation == 'grant'){
		    		saveG(operateParams);
		    	}
		    }
		    
		  //��¼����
		     function insertOperateRcd(operateParams){
		    	Ext.Ajax.request({
		        	url: _contextPath_ + '/jsp/conmg/Eaoperatercd_save.action',
		    		params:{params:operateParams},
		    		success : function(response) {
		    		},
		    		failure : function(form, action) {
		    		}
		        });
		    }
		    /*
		     * ��Ȩģ��
		     * */
})
</script>
<!-- ��<eRedG4:arm.RoleGrantMenuTree/>��ǩ���ɵĴ������ -->