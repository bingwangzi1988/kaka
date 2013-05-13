/**
 * ��½ҳ��
 * 
 * @author zly
 * @since 2012-07-30
 */
Ext.onReady(function() {
	
	 var sexStore = new Ext.data.SimpleStore({
         fields : [ 'value', 'text' ],
         data : [
             [ '1', '1 ��' ],
             [ '2', '2 Ů' ],
             [ '0', '0 δ֪' ]
         ]
     });
	 
	 var sexCombo = new Ext.form.ComboBox({
         name : 'sex',
         hiddenName : 'sex',
         store : sexStore,
         mode : 'local',
         triggerAction : 'all',
         valueField : 'value',
         displayField : 'text',
         value : '0',
         fieldLabel : '�Ա�',
         emptyText : '��ѡ��...',
         allowBlank : false,
         forceSelection : true,
         editable : false,
         typeAhead : true,
         readOnly : true,
         anchor : "99%"
     });
	 
	 var label=new Ext.form.Label({
		 id:'valpwd',
		 name:'valpwd',
		 height:100,
		 width:100,
		 autoShow:true,
		 autoWidth:true,
		 autoHeight:true,
		 hidden:true,
		 hideMode:'offsets',
		 disabled:false,
		 style:'padding-left:74px;font-size:12px;color: red;',
		 text:'',
		 renderTo:''
	 });
	 
	var userFormPanel = new Ext.form.FormPanel({
        defaultType : 'textfield',
        labelAlign : 'right',
        labelWidth : 65,
        frame : false,
        bodyStyle : 'padding:15 5 0',
        items : [
            {
                fieldLabel : '��¼�ʻ�',
                name : 'account',
                id : 'account',
                allowBlank : false,
                readOnly : true,
                fieldClass : 'x-custom-field-disabled',
                anchor : '99%'
            },
            {
                fieldLabel : '����',
                name : 'username',
                id : 'username',
                readOnly : true,
                allowBlank : false,
                anchor : '99%'
            },
            sexCombo,
            {
                fieldLabel : '����',
                name : 'password',
                enableKeyEvents:true,
                id : 'password',
                inputType : 'password',
                allowBlank : false,
                anchor : '99%'
            },
            label,
            {
                fieldLabel : 'ȷ������',
                name : 'password1',
                id : 'password1',
                inputType : 'password',
                vtype: 'password',
                initialPassField: 'password',
                allowBlank : false,
                anchor : '99%'
            },
            {
                id : 'userid',
                name : 'userid',
                hidden : true
            }
        ]
    });
	
	 var userWindow = new Ext.Window({
         layout : 'fit',
         width : 500,
         height : 265,
         resizable : false,
         draggable : true,
         closable : false,
         closeAction : 'hide',
         modal : true,
         title : '�޸��ʻ���Ϣ',
         iconCls : 'configIcon',
         collapsible : true,
         titleCollapse : true,
         maximizable : false,
         buttonAlign : 'right',
         border : false,
         animCollapse : true,
         animateTarget : Ext.getBody(),
         constrain : true,
         items : [ userFormPanel ],
         buttons : [
             {
                 text : '�޸�',
                 iconCls : 'edit',
                 handler : function() {
                     updateUser();
                 }
             }
//             ,
//             {
//                 text : '�ر�',
//                 iconCls : 'deleteIcon',
//                 handler : function() {
//                     userWindow.hide();
//                 }
//             }
         ]
     });
	 
	 /**
	     * ���ص�ǰ��¼�û���Ϣ
	     */
	    function updateUserInit() {
	        userFormPanel.form.reset();
	        userWindow.show();
	        userWindow.on('show', function() {
	            setTimeout(function() {
	                userFormPanel.form.load({
	                            waitTitle : '��ʾ',
	                            waitMsg : '���ڶ�ȡ�û���Ϣ,���Ժ�...',
	                            url : 'index.ered?reqCode=loadUserInfo',
	                            success : function(form, action) {
	                            },
	                            failure : function(form, action) {
	                                if(action.failureType=='connect')
	                                {
	                                	Ext.MessageBox.alert('��ʾ', '���ڳ�ʱ��δ����,���лỰ�ѳ�ʱ;������ǿ���ض��򵽵�¼ҳ��!', function() {
	                        	            if (parent != null) {
	                        	                top.location.href = _contextPath_;
	                        	            } else
	                        	                window.location.href = _contextPath_;
	                        	        });
	                                }else{
	                                	 Ext.Msg.alert('��ʾ', '���ݶ�ȡʧ��:'+ action.failureType);
	                                }
	                            }
	                        });
	            }, 5);
	        });
	    }
	    
	    var pwdflag='0';
	    /**
	     * �޸��û���Ϣ
	     */
	    function updateUser() {
	    	var pwd=userFormPanel.getForm().findField('password').getValue();
	    	var pwd1=userFormPanel.getForm().findField('password1').getValue();
	    	if(pwd.length < 6){
	    		Ext.Msg.alert("��ʾ","���볤�Ȳ�������6λ");
	    		return;
	    	}
	    	if(pwd!=pwd1){
	    		Ext.Msg.alert("��ʾ","�������벻һ��");
	    		return;
	    	}
	    	if(pwdflag!='3')
	    	{
	    		Ext.Msg.alert("��ʾ","����ǿ�ȱ���Ϊ��");
	    		return;
	    	}
	        if (!userFormPanel.form.isValid()) {
	            return;
	        }
	        userFormPanel.form.submit({
	                    url : 'index.ered?reqCode=updateUserInfo',
	                    waitTitle : '��ʾ',
	                    method : 'POST',
	                    waitMsg : '���ڴ�������,���Ժ�...',
	                    success : function(form, action) {
	                        Ext.MessageBox.alert('��ʾ', '��¼�ʻ���Ϣ�޸ĳɹ�', function() {
		                        userWindow.hide();
		                        window.location.href = 'main.jsp';
	                        });
	                    },
	                    failure : function(form, action) {
	                        var msg = action.result.msg;
	                        Ext.MessageBox.alert('��ʾ', '��Ա���ݱ���ʧ��');
	                    }
	                });
	    }
	    
	    updateUserInit();
	    
	    userFormPanel.getForm().findField('password').on("keyup",function(){
	    	var pwd=userFormPanel.getForm().findField('password').getValue();
	    	if(pwd.length>0){
	    		chkpwd(pwd);
	    	}else
	    	{
	    		label.setVisible(false);//��ʾlabel��ǩ
	    	}
	    });
	    
	    function chkpwd(pwd){
	    	label.setVisible(true);//��ʾlabel��ǩ
	    	
	    	var id=getResult(pwd);
	    	var msg=new Array(4);
	    	msg[0]='������̲�������6λ';
	    	msg[1]='���밲ȫ�Բ�';
	    	msg[2]='����ǿ������';
	    	msg[3]='����ǿ�ȸ�';
	    	
	    	var col=new Array(4);
	    	col[0]='gray';
	    	col[1]='red';
	    	col[2]='#ff6600';
	    	col[3]='green';
	    	
	    	pwdflag=id;
	    	
	    	var obj=document.getElementById('valpwd');
	    	obj.innerHTML=msg[id];
	    	obj.style.color=col[id];
	    }
	    
	    function getResult(pwd)
	    {
	    	if(pwd.length < 6)
	    	{
	    		return 0;
	    	}
	    	var ls=0;
	    	if(pwd.match(/[a-z]/ig))
	    	{
	    		ls++;
	    	}
	    	if(pwd.match(/[0-9]/ig))
	    	{
	    		ls++;
	    	}
	    	if(pwd.match(/(.[^a-z0-9])/ig))
	    	{
	    		ls++;
	    	}
	    	if(pwd.length < 6 && ls > 0)
	    	{
	    		ls--;
	    	}
	    	return ls;
	    }
	
});