
function authorise(object,operation,operateObject){
	
	var userRoleStore = new Ext.data.Store({
		 baseParams:{valueflag:'1',valueflagvalue:':'},
       proxy:new Ext.data.HttpProxy({url:_contextPath_ + '/common/codeSelect_getUserRole.action'}),
       reader: new Ext.data.JsonReader({}, [
         {name: "code"},
         {name: "codedesc"}
       ]),
       remoteSort:false
       });
//	userRoleStore.load();
	
	var authoriseForm = new Ext.form.FormPanel({	
		height:200,
		labelWidth : 90,
		frame : true,
		defaultType : 'textfield',
		labelAlign : 'right',
		bodyStyle : 'padding:5 5 5 5',
		items : [{
			fieldLabel : '��ȨԱ��',
			name : 'authoriseId',
//			minLength:7,
			store:userRoleStore,
			"xtype":"dcombo",
			allowBlank : false,
			anchor : '100%'
		}, {
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
//			hidden:true,
			anchor : '100%'
		}*/]	
	});
	
	var authoriseWin = new Ext.Window({
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
		items : [authoriseForm],
		buttons : [{
			text : '��Ȩ',
			iconCls : 'keyIcon',
			handler : function execute(){
				if(authoriseForm.getForm().isValid()){
					authoriseForm.form.submit({
						url : _contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
						success : function(form, action) {
							authoriseWin.hide();
							var result = Ext.util.JSON.decode(action.response.responseText);
							var operateParams = Ext.util.JSON.encode(result.msg);
							executeObject(object,operation,operateParams);
						},
						failure : function(form, action) {
							var result = Ext.util.JSON.decode(action.response.responseText);
							authoriseWin.hide();
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
				authoriseWin.hide();
			}
		}]
	});
	
	
//	authoriseWin.show();
//	alert("object===="+object+"operation=="+operation+"    operateObject=="+operateObject);
	
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
//				   alert("tableName=="+params.tableName);
				   if(params.tableName==operateObject)
				   {
					   var insertFlag=params.insertFlag;
					   var updateFlag=params.updateFlag;
					   var deleteFlag=params.deleteFlag;
					    if(operation == 'save'){
							if(insertFlag==1){
								userRoleStore.load({
									params : {
										tableName : params.tableName,
										valueflag:'1',
										valueflagvalue:':'
									}
								});
								authoriseWin.show();//��Ҫ��Ȩ
							}
							else{
								executeNoRole(object,operation,operateObject);//����Ҫ��Ȩ
							}
						}else if(operation == 'update'){
							if(updateFlag==1){
								userRoleStore.load({
									params : {
										tableName : params.tableName,
										valueflag:'1',
										valueflagvalue:':'
									}
								});
								authoriseWin.show();//��Ҫ��Ȩ
							}
							else{
								executeNoRole(object,operation,operateObject);//����Ҫ��Ȩ
							}
						}else if(operation == 'delete')
						{
							if(deleteFlag==1){
								userRoleStore.load({
									params : {
										tableName : params.tableName,
										valueflag:'1',
										valueflagvalue:':'
									}
								});
								authoriseWin.show();//��Ҫ��Ȩ
							}
							else{
								executeNoRole(object,operation,operateObject);//����Ҫ��Ȩ
							}
						}else
						{
							executeNoRole(object,operation,operateObject);//����Ҫ��Ȩ
						}
				   }
			   }else{
				   executeNoRole(object,operation,operateObject);//Ĭ��Ϊ����Ҫ��Ȩ
			   }
		   }
	   });

	
}


//����Ȩ
executeNoRole=function(object,operation,operateObject)
{
	 Ext.Ajax.request({
		   url:_contextPath_ + '/jsp/common/Authorization_executeAuthority.action',
		   params:{operateType:operation,operateObject:operateObject},
		   callback:function(opts,success,response){
			   var jsonobjs=Ext.decode(response.responseText);
			   if(jsonobjs.success==true)
			   {
				   var operateParams = Ext.util.JSON.encode(jsonobjs.msg);
				   executeObject(object,operation,operateParams);
			   }else
			   {
				   Ext.Msg.alert('��ʾ', jsonobjs.msg);
			   }
		   }
	   });
}

executeObject = function(object,operation,operateParams){
	if(operation == 'save' || operation == 'update'){
		saveOrUpdateWay(object,operateParams);
	}else if(operation == 'delete'){
		deleteWay(object,operateParams);
	}else{
		specialWay(object,operation,operateParams);
	}
}

//���ӻ����޸�
saveOrUpdateWay = function(object,operateParams){
    object.win1.items.get(0).form.submit({
        success: function(form, action) {
            object.win1.hide();
            object.store.reload();
            insertOperateRcd(operateParams);
        },
        failure: function(form, action) {
            Ext.MessageBox.show({
                title: '��ʾ',
                msg: action.result.msg,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.WARNING
            });
        },
        scope:object,
        waitTitle:"���Ժ�",
        waitMsg:"���ݱ����С���"
    });
}

//ɾ��
deleteWay = function(object,operateParams){
	Ext.Ajax.request({
        url : _contextPath_ + '/' + object.deleteAction + '.action',
        success : function onSuccessDel(o) {
            this.store.reload();
            var jsonobjs=Ext.decode(o.responseText);
        	var msg="ɾ������ɹ�";
        	if(jsonobjs.msg!=null && jsonobjs.msg!='')
        	{
        		msg=jsonobjs.msg;	
        	}
            Ext.MessageBox.show({
                title: '��Ϣ',
                msg: msg,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.INFO
            });
            insertOperateRcd(operateParams);
        },
        failure : function(response) {
            var errorWindow = new Ext.Window({
                title:"��������",
                width:200,
                height:200,
                maximizable:true,
                autoScroll:true,
                html:response.responseText
            });
            errorWindow.show();
            errorWindow.maximize();
        }
        ,params:object.makeDelParams(object.getSelectionModel().getSelections())
        ,scope:object
    });
}

//��¼����
insertOperateRcd = function(operateParams){
	Ext.Ajax.request({
    	url: _contextPath_ + '/jsp/conmg/Eaoperatercd_save.action',
		params:{params:operateParams},
		success : function(response) {
		},
		failure : function(form, action) {
		}
    });
}

