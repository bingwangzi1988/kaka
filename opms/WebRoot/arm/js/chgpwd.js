/**
 * 登陆页面
 * 
 * @author zly
 * @since 2012-07-30
 */
Ext.onReady(function() {
	
	 var sexStore = new Ext.data.SimpleStore({
         fields : [ 'value', 'text' ],
         data : [
             [ '1', '1 男' ],
             [ '2', '2 女' ],
             [ '0', '0 未知' ]
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
         fieldLabel : '性别',
         emptyText : '请选择...',
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
                fieldLabel : '登录帐户',
                name : 'account',
                id : 'account',
                allowBlank : false,
                readOnly : true,
                fieldClass : 'x-custom-field-disabled',
                anchor : '99%'
            },
            {
                fieldLabel : '姓名',
                name : 'username',
                id : 'username',
                readOnly : true,
                allowBlank : false,
                anchor : '99%'
            },
            sexCombo,
            {
                fieldLabel : '密码',
                name : 'password',
                enableKeyEvents:true,
                id : 'password',
                inputType : 'password',
                allowBlank : false,
                anchor : '99%'
            },
            label,
            {
                fieldLabel : '确认密码',
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
         title : '修改帐户信息',
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
                 text : '修改',
                 iconCls : 'edit',
                 handler : function() {
                     updateUser();
                 }
             }
//             ,
//             {
//                 text : '关闭',
//                 iconCls : 'deleteIcon',
//                 handler : function() {
//                     userWindow.hide();
//                 }
//             }
         ]
     });
	 
	 /**
	     * 加载当前登录用户信息
	     */
	    function updateUserInit() {
	        userFormPanel.form.reset();
	        userWindow.show();
	        userWindow.on('show', function() {
	            setTimeout(function() {
	                userFormPanel.form.load({
	                            waitTitle : '提示',
	                            waitMsg : '正在读取用户信息,请稍候...',
	                            url : 'index.ered?reqCode=loadUserInfo',
	                            success : function(form, action) {
	                            },
	                            failure : function(form, action) {
	                                if(action.failureType=='connect')
	                                {
	                                	Ext.MessageBox.alert('提示', '由于长时间未操作,空闲会话已超时;您将被强制重定向到登录页面!', function() {
	                        	            if (parent != null) {
	                        	                top.location.href = _contextPath_;
	                        	            } else
	                        	                window.location.href = _contextPath_;
	                        	        });
	                                }else{
	                                	 Ext.Msg.alert('提示', '数据读取失败:'+ action.failureType);
	                                }
	                            }
	                        });
	            }, 5);
	        });
	    }
	    
	    var pwdflag='0';
	    /**
	     * 修改用户信息
	     */
	    function updateUser() {
	    	var pwd=userFormPanel.getForm().findField('password').getValue();
	    	var pwd1=userFormPanel.getForm().findField('password1').getValue();
	    	if(pwd.length < 6){
	    		Ext.Msg.alert("提示","密码长度不能少于6位");
	    		return;
	    	}
	    	if(pwd!=pwd1){
	    		Ext.Msg.alert("提示","两次密码不一致");
	    		return;
	    	}
	    	if(pwdflag!='3')
	    	{
	    		Ext.Msg.alert("提示","密码强度必须为高");
	    		return;
	    	}
	        if (!userFormPanel.form.isValid()) {
	            return;
	        }
	        userFormPanel.form.submit({
	                    url : 'index.ered?reqCode=updateUserInfo',
	                    waitTitle : '提示',
	                    method : 'POST',
	                    waitMsg : '正在处理数据,请稍候...',
	                    success : function(form, action) {
	                        Ext.MessageBox.alert('提示', '登录帐户信息修改成功', function() {
		                        userWindow.hide();
		                        window.location.href = 'main.jsp';
	                        });
	                    },
	                    failure : function(form, action) {
	                        var msg = action.result.msg;
	                        Ext.MessageBox.alert('提示', '人员数据保存失败');
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
	    		label.setVisible(false);//显示label标签
	    	}
	    });
	    
	    function chkpwd(pwd){
	    	label.setVisible(true);//显示label标签
	    	
	    	var id=getResult(pwd);
	    	var msg=new Array(4);
	    	msg[0]='密码过短不能少于6位';
	    	msg[1]='密码安全性差';
	    	msg[2]='密码强度良好';
	    	msg[3]='密码强度高';
	    	
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