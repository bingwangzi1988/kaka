<!-- 由<eRedG4:arm.SelectRoleTree/>标签生成的代码开始 -->
<div id="selectRoleTreeDiv"></div>
<script type="text/javascript">
Ext.onReady(function() {
#foreach($dept in $deptList)
	var node_${dept.deptid} = new Ext.tree.TreeNode({
		text:'${dept.deptname}',	
		id:'id_node_${dept.deptid}'
	});
#end
#foreach($role in $roleList)
	var node_${role.roleid} = new Ext.tree.TreeNode({
		text:'${role.rolename}',
#if(${role.checked} == "true")
	 checked:true,
#else
    checked:false,	
#end
		roleid:'${role.roleid}',
#if(${role.roletype} == "1")
	iconCls:'medal_silver_3Icon',
#else
    iconCls:'medal_gold_1Icon',
#end
		id:'id_node_${role.roleid}'
	});
#end

#foreach($dept in $deptList)
#if(${dept.isroot}!="true")
	node_${dept.parentid}.appendChild(node_${dept.deptid});
#end
#end
#foreach($role in $roleList)
	node_${role.deptid}.appendChild(node_${role.roleid});
#end

var selectRoleTree = new Ext.tree.TreePanel({
			autoHeight : false,
			autoWidth : false,
			autoScroll : true,
			animate : false,
			rootVisible : true,
			border : false,
			containerScroll : true,
			applyTo : 'selectRoleTreeDiv',
			tbar : [{
				text : '保存',
				id : 'selectRole_saveBtn',
				iconCls : 'acceptIcon',
				handler : function() {
				        authoriseRole('grant','Eauser');
				 }
		    }, '-', {
				text : '展开',
				iconCls : 'expand-allIcon',
				handler : function() {
					selectRoleTree.expandAll();
				}
		    }, '-', {
				text : '收缩',
				iconCls : 'collapse-allIcon',
				handler : function() {
					selectRoleTree.collapseAll();
				}
		    }],
			root : node_${deptid}
  });
  //node_${deptid}.expand();
  selectRoleTree.expandAll();

function saveM(operateParams)
{
	 		if (runMode == '0') {
							Ext.Msg.alert('提示', '系统正处于演示模式下运行,您的操作被取消!该模式下只能进行查询操作!');
							return;
						  }				
					     var checkedNodes = selectRoleTree.getChecked();
					     var roleid = "";
						 for(var i = 0; i < checkedNodes.length; i++) {
						   var checkNode = checkedNodes[i];
					       roleid = roleid + checkNode.attributes.roleid + "," ;  
						 }
						 saveSelectedRole(roleid,operateParams);
}

//保存授权数据
function saveSelectedRole(pRoleid,operateParams){
		showWaitMsg();
		Ext.Ajax.request({
					url : './user.ered?reqCode=saveSelectedRole',
					success : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('提示', resultArray.msg);
						insertOperateRcd(operateParams);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('提示', resultArray.msg);
					},
					params : {
						roleid : pRoleid
					}
				});
}
	/*
		     * 授权模块
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
				fieldLabel : '授权员工',
				emptyText : '请选择',
				triggerAction : 'all',
				store:userRoleStore,
				displayField : 'codedesc',
				valueField : 'code',
				mode : 'local',
				forceSelection : false, // 选中内容必须为下拉列表的子项
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
//		    			fieldLabel : '授权员工',
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
		    			fieldLabel : '授权密码',
		    			name : 'authorisePwd',
		    			inputType : 'password',
		    			allowBlank : false,
		    			anchor : '100%'
		    		}, {
		    			fieldLabel : '操作类型',
		    			name : 'operateType',
		    			value:operation,
		    			hidden:true,
		    			anchor : '100%'
		    		}, {
		    			fieldLabel : '操作对象',
		    			name : 'operateObject',
		    			value:operateObject,
		    			hidden:true,
		    			anchor : '100%'
		    		}/*, {
		    			fieldLabel : '操作信息',
		    			name : 'operateInfo',
//		    			hidden:true,
		    			anchor : '100%'
		    		}*/]	
		    	});
		    	
		    	var authoriseRoleWin = new Ext.Window({
		    		title : '操作授权',
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
		    			text : '授权',
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
		    							Ext.MessageBox.alert('提示', result.msg);
		    						},
		    						waitTitle:"请稍后",
		            				waitMsg:"授权验证中……"
		    					});
		    				}
		    			}
		    		},{
		    			text : '取消',
		    			iconCls : 'lockIcon',
		    			handler : function cancel(){
		    				authoriseRoleWin.hide();
		    			}
		    		}]
		    	});
		    	
		    	
//		    	authoriseWin.show();
//		    	alert("operation=="+operation+"    operateObject=="+operateObject);
		    	
		    	//查询菜单权限表
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
		    								authoriseRoleWin.show();//需要授权
		    							}
		    							else{
		    								execNoRole(operation,operateObject);//不需要授权
		    							}
		    						}else
		    						{
		    							execNoRole(operation,operateObject);//不需要授权
		    						}
		    				   }
		    			   }else{
		    				   execNoRole(operation,operateObject);//默认为不需要授权
		    			   }
		    		   }
		    	   });

		    	
		    }


		    //不授权
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
		    				   Ext.Msg.alert('提示', jsonobjs.msg);
		    			   }
		    		   }
		    	   });
		    }

		     function execObject(operation,operateParams){
		    	 if(operation == 'grant'){
		    		saveM(operateParams);
		    	}
		    }
		    
		  //记录操作
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
		     * 授权模块
		     * */
})
</script>
<!-- 由<eRedG4:arm.SelectRoleTree/>标签生成的代码结束 -->