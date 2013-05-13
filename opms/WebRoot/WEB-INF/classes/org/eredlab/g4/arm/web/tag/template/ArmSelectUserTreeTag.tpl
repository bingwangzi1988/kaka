<!-- ��<eRedG4:arm.SelectUserTree/>��ǩ���ɵĴ��뿪ʼ -->
<div id="selectUserTreeDiv"></div>
<script type="text/javascript">
Ext.onReady(function() {
#foreach($dept in $deptList)
	var node_${dept.deptid} = new Ext.tree.TreeNode({
		text:'${dept.deptname}',	
		id:'id_node_${dept.deptid}'
	});
#end
#foreach($user in $userList)
	var node_${user.userid} = new Ext.tree.TreeNode({
		text:'${user.username}',
#if(${user.checked} == "true")
	 checked:true,
#else
    checked:false,	
#end
		userid:'${user.userid}',
#if(${user.usertype} == "1")
	iconCls:'user_femaleIcon',
#else
    iconCls:'userIcon',
#end
		id:'id_node_${user.userid}'
	});
#end

#foreach($dept in $deptList)
#if(${dept.isroot}!="true")
	node_${dept.parentid}.appendChild(node_${dept.deptid});
#end
#end
#foreach($user in $userList)
	node_${user.deptid}.appendChild(node_${user.userid});
#end

var selectUserTree = new Ext.tree.TreePanel({
			autoHeight : false,
			autoWidth : false,
			autoScroll : true,
			animate : false,
			rootVisible : true,
			border : false,
			containerScroll : true,
			applyTo : 'selectUserTreeDiv',
			tbar : [{
				text : '����',
				id : 'selectUser_saveBtn',
				iconCls : 'acceptIcon',
				handler : function() {
				         authoriseRole('grant','Cmrole');
				 }
		    }, '-', {
				text : 'չ��',
				iconCls : 'expand-allIcon',
				handler : function() {
					selectUserTree.expandAll();
				}
		    }, '-', {
				text : '����',
				iconCls : 'collapse-allIcon',
				handler : function() {
					selectUserTree.collapseAll();
				}
		    }],
			root : node_${deptid}
  });
  //node_${deptid}.expand();
  selectUserTree.expandAll();

function saveU(operateParams)
{
						if (runMode == '0') {
							Ext.Msg.alert('��ʾ', 'ϵͳ��������ʾģʽ������,���Ĳ�����ȡ��!��ģʽ��ֻ�ܽ��в�ѯ����!');
							return;
						  }				
					     var checkedNodes = selectUserTree.getChecked();
					     var userid = "";
						 for(var i = 0; i < checkedNodes.length; i++) {
						   var checkNode = checkedNodes[i];
					       userid = userid + checkNode.attributes.userid + "," ;  
						 }
						 saveUser(userid,operateParams);
}

//������Ȩ����
function saveUser(pUserid,operateParams){
		showWaitMsg();
		Ext.Ajax.request({
					url : './role.ered?reqCode=saveUser',
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
						userid : pUserid
					}
				});
}
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
		    		saveU(operateParams);
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
<!-- ��<eRedG4:arm.SelectUserTree/>��ǩ���ɵĴ������ -->