/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-4-14
 * Time: 下午5:35
 * To change this template use File | Settings | File Templates.
 */

/**
 * 在主页面显示信息
 * 第一个参数为eamenu表中的menuid列
 * 第二个参数为标题相当于eamenu种的menuname列
 * 第三个参数为跳转url相当于eamenu种的request列
 * 第四个参数为css图标相当于eamenu种的iconcls列
 * 第五个参数为是否重新加载页面 true 是 false 否
 * 
 * openIframe("010312", "测试菜单", "jsp/conmg/cmcountry.jsp", "", false);
 * 
 * */

Ext.onReady(function() {

    var menuPanelitems = new Array();
    var tempStyle;
    var selectedTreeNode;

    var tools = [
        {
            id:'maximize',
            handler: function(e, target, panel) {
                openIframe(panel.id.replace("_for_portal", ""), panel.title, panel.url, panel.iconCls, false);
            }
        },
        {
            id:'close',
            handler: function(e, target, panel) {
                var column = getColumnLength();
                HttpRequest({
                            url : _contextPath_ + '/PortalActiondelete.action',
                            success:function() {
                                var fn = function(c) {
                                    if (c.sortno > panel.sortno) {
                                        c.sortno = c.sortno - column;
                                    }
                                };
                                panel.ownerCt.items.each(fn);

                                panel.ownerCt.remove(panel, true);

                            },
                            failure : function(response) {
                                Ext.Msg.alert('提示', response.responseText);
                            },
                            params:{menuid:panel.id.replace("_for_portal", ""),sortno:panel.sortno,column:column}
                        });


            }
        }
    ];

    HttpRequest({
                url : _contextPath_ + '/MenuTree.action',
//                async:false,
                success : dipplayMenuTree
            });

    HttpRequest({
                url : _contextPath_ + '/PortalActioninit.action',
//                async:false,
                success : initPortal
            });

    function dipplayMenuTree(o) {
        var resultArray = Ext.util.JSON.decode(o.responseText);
        for (var i = 0; i < resultArray.length; i++) {
            var tree = resultArray[i];
            var menutree = new Ext.tree.TreePanel({
                        id:"panel" + tree.id,
                        split:true,
                        iconCls:tree.iconCls,
                        collapsible: true,
                        title:tree.text ,
                        rootVisible:false,
                        autoScroll:true,
                        border:false,
                        containerScroll: true,
                        root: new Ext.tree.AsyncTreeNode(tree)
                    });
            menutree.on('click', function (node) {
                if (node.leaf) {
                    selectedTreeNode = {id:node.id,title:node.text,iconCls:node.attributes.iconCls,url:node.attributes.url};
                    openIframe(node.id, node.text, node.attributes.url, node.attributes.iconCls, false);
                } else {
                    selectedTreeNode = null
                }
            });

            menuPanel.items.add(menutree);
            menuPanel.doLayout();
        }
    }

    function initPortal(o) {
        tempStyle = style;
        dipplayPortal(o);
    }

    function dipplayPortal(o) {
        var resultArray = Ext.util.JSON.decode(o.responseText);
        Ext.getCmp("disk").removeAll();

        var tempPortlets = getPortalUI(tempStyle);
        Ext.getCmp("disk").add(tempPortlets);
        for (var i = 0; i < resultArray.length; i++) {
            var portlet = resultArray[i];
            var columnIndex = portlet.sortno % tempPortlets.length;
            var item = new Ext.ux.Portlet({
                        id:portlet.menuid + "_for_portal",
                        title: portlet.title,
                        height:portlet.height != 0 ? portlet.height : (document.body.clientHeight-180)/2,
                        sortno:portlet.sortno,
                        iconCls: portlet.iconcls,
                        url:portlet.url,
                        tools: tools,
                        dblclickHandler:function(panel) {
                            openIframe(panel.id.replace("_for_portal", ""), panel.title, panel.url, panel.iconCls, false);
                        },
                        items:[new Ext.ux.IFrameComponent({ id:portlet.menuid + "_for_portal", url: Ext.urlAppend(_contextPath_ + "/" + portlet.url, "isPortal=true") })]
                    });

            Ext.getCmp("disk").items.itemAt(columnIndex).add(item);
        }


        Ext.getCmp("disk").doLayout();
    }

    var treeTool = [
        {id:"gear",qtip:"添加到" + WELCOME_PAGE_TITLE,handler:function() {
            tabPanel.setActiveTab(0);
            addPortalet();
        }},
        {id:"maximize",qtip:"最大化当前窗口",handler:function() {
            toggleCollapse(true)
        }}
    ];

    var isOpen = true;

    function toggleCollapse(flag) {
        isOpen = flag;
        if (flag) {
            menuPanel.collapse();
            hearPanel.collapse();
            bottomPanel.collapse();
        } else {
            menuPanel.expand();
            hearPanel.expand();
            bottomPanel.expand();
        }
        isOpen = !isOpen;
    }

    function isPortaletExist(disk, itemid) {
        var flag = false;
        for (var i = 0; i < getColumnLength(); i++) {
            var thisobj = disk.items.itemAt(i);
            var fn = function(c) {
                if (c.id == itemid) {
                    flag = true;
                    return false;
                }
            };
            thisobj.items.each(fn);

        }
        return flag;
    }

    function getColumnLength() {
        var i = 0;
        for (var j = 0; j < tempStyle.length; j++) {
            var obj = tempStyle.charAt(j);
            if (obj == '_') {
                i++;
            }
        }
        return i + 1;
    }

    function addPortalet() {
        if (selectedTreeNode != undefined && selectedTreeNode != null) {
            var itemid = selectedTreeNode.id + "_for_portal";
            var disk = Ext.getCmp("disk");

            if (isPortaletExist(disk, itemid)) {
                Ext.MessageBox.alert("提示", "该模块已经被添加");
                return;
            }
            var selectColumn = disk.items.itemAt(0);
            var selectLength = selectColumn.items.getCount();

            var selectIndex = 0;
            var columnLength = getColumnLength();
            for (var i = 1; i < columnLength; i++) {
                var thisobj = disk.items.itemAt(i);
                var thisLength = thisobj.items.getCount();
                selectColumn = selectLength <= thisLength ? selectColumn : thisobj;
                if (selectLength > thisLength) {
                    selectColumn = thisobj;
                    selectLength = thisLength;
                    selectIndex = i;
                }
            }

            var sortno = disk.items.itemAt(selectIndex).items.getCount() * columnLength + selectIndex;
            var item = new Ext.ux.Portlet({
                        id:itemid,
                        title: selectedTreeNode.title,
                        iconCls: selectedTreeNode.iconCls,
                        height:(document.body.clientHeight-180)/2,
                        sortno:sortno,
                        url:selectedTreeNode.url,
                        tools: tools,
                        dblclickHandler:function(panel) {
                            openIframe(panel.id.replace("_for_portal", ""), panel.title, panel.url, panel.iconCls, false);
                        },
                        items:[new Ext.ux.IFrameComponent({ id:selectedTreeNode.id + "_for_portal", url: Ext.urlAppend(_contextPath_ + "/" + selectedTreeNode.url, "isPortal=true") })]
                    });

            selectColumn.add(item);
            disk.doLayout();

            Ext.Ajax.request({
                        url : _contextPath_ + '/PortalActionadd.action',
                        failure : function(response) {
                            selectColumn.remove(item);
                            disk.doLayout();
                            Ext.Msg.alert('提示', response.responseText);
                        },
                        params:{menuid:selectedTreeNode.id,sortno:sortno}
                    });

        } else {
            Ext.MessageBox.alert("提示", "请选择一个菜单");
        }
    }

    var menuPanel = new Ext.Panel({
                region:'west',
                id:'west-panel',
//                tools:treeTool,
                title:WEST_NAVIGATE_TITLE,
                split:true,
                width: 200,
                minSize: 200,
                maxSize: 400,
//                collapsible: true,
                collapseMode:'mini',
                layout:'accordion',
                layoutConfig:{
                    activeOnTop :WEST_CARDMENU_ACTIVEONTOP != "0",
                    animate:true
                },
                items:menuPanelitems
            });
    var scrollerMenu = new Ext.ux.TabScrollerMenu({
                maxText  : 15,
                pageSize : 10
            });

    var tabPanel = new Ext.ux.SlidingTabPanel({
                region:'center',
                id:"mainPanel",
                activeTab:0,
//        defaultType: 'iframepanel',
//        defaults: {autoScroll:true},
                enableTabScroll:true,
                plugins: new Ext.ux.TabCloseMenu({dbClickHandler:function() {
                            toggleCollapse(isOpen)
                        }}),
                items:[
                    {
                        id:"disk",
                        title: WELCOME_PAGE_TITLE,
                        tabTitle:WELCOME_PAGE_TITLE,
                        iconCls:"application_homeIcon",
                        closable:false,
                        xtype:'portal'
                        //   margins:'35 5 5 0',
//                        ,items:portlets
//                        ,listeners: {
//                        'drop': function(e) {
//                            Ext.Msg.alert('Portlet Dropped', e.panel.title + '<br />Column: ' +
//                                    e.columnIndex + '<br />Position: ' + e.position);
//                        }
//                    }
                    }
                ]
                ,listeners:{
    				tabchange:function(tp,p){
    					var its=Ext.getCmp(p.id).items;
    					for(var i=0;i<its.length;i++)
    					{
    						its.get(i).focus();
    					}
    				}
    			}
            });

    var store = new Ext.data.ArrayStore({
                proxy   : new Ext.data.MemoryProxy(),
                fields  : ['name', 'columns']
            });

    store.loadData([
        ["33_33_33",3],
        ["50_50",2],
        ["67_33",2],
        ["33_67",2],
        ["25_25_25_25",4]
    ]);

    var dataview = new Ext.DataView({
                store: store,
                tpl  : new Ext.XTemplate(
                        '<ul>',
                        '<tpl for=".">',
                        '<li class="phone" id="{name}">',
                        '<img src="images/portal/columnStyle/colStyle_{values.name}.gif" />',
                        '<strong>{name}</strong>',
                        '</li>',
                        '</tpl>',
                        '</ul>'
                ),
                id: 'phones',
                itemSelector: 'li.phone',
                overClass   : 'phone-hover',
                singleSelect: true,
                autoScroll  : true
            });

    dataview.on("click", function(view, index, node, e) {
        if (node.id != tempStyle) {
            tempStyle = node.id;

            Ext.Ajax.request({
                        url : _contextPath_ + '/PortalActioninit.action',
//                async:false,
                        success : dipplayPortal,
                        failure : function(response) {
                            Ext.Msg.alert('提示', response.responseText);
                        }
                    });
        }
    });
    var contextMenuWin = new Ext.Window({
                layout : 'fit',
                width : 270,
                height : 360,
//                resizable : false,
                draggable : true,
                closeAction : 'hide',
                modal : true,
                title : '布局',
                iconCls : 'layout_contentIcon',
                collapsible : true,
                titleCollapse : true,
//                border : false,
                animCollapse : true,
                animateTarget : Ext.getBody(),
                constrain : true,
                items : [dataview]
            });

    contextMenuWin.on('beforehide', function() {
        Ext.MessageBox.confirm("提示", "确认保存布局", function(btn) {
            if (btn == 'yes') {
                style = tempStyle;
                //布局改变后，前台sortno检查错位，需配合后台sortno同步
                var columns = Ext.getCmp("disk").items;
                var columnLength = getColumnLength();
                var i = 0;
                columns.each(function(column) {
                    var j = 0;
                    column.items.each(function(row) {
                        var sortno = i * columnLength + j;
                        if (row.sortno != sortno) {
                            row.sortno = sortno;
                        }
                        j++;
                    });
                    i++;
                });

                Ext.Ajax.request({
                            url : _contextPath_ + '/PortalActionsave.action',
                            failure : function(response) {
                                Ext.Msg.alert('提示', response.responseText);
                            },
                            params:{portal:style,column:columnLength}
                        });

            } else if (tempStyle != style) {
                tempStyle = style;

                Ext.Ajax.request({
                            url : _contextPath_ + '/PortalActioninit.action',
//                async:false,
                            success : dipplayPortal,
                            failure : function(response) {
                                Ext.Msg.alert('提示', response.responseText);
                            }
                        });
            }
        });
    });

    var hearPanel = new Ext.Panel({
                region: 'north',
                contentEl: 'header',
                collapseMode:'mini',
                split:true,
//                        minSize: 62,
//                        maxSize: 62,
//                        height:62,
                autoHeight:true
            });
    var bottomPanel = new Ext.Panel({
                region:'south',
                contentEl: 'bottomer',
                collapseMode:'mini',
                split:true,
//                height:28
                        autoHeight:true
            });
    var viewport = new Ext.Viewport({
                layout:'border',
                items:[
                    hearPanel,
                    menuPanel,
                    tabPanel,
                    bottomPanel
                ]
            });

    Ext.getCmp("disk").getEl().on('contextmenu', function(e) {
        e.preventDefault();
        contextMenuWin.show();
    });

    function openIframe(id, title, url, iconCls, relaod) {
        if (url == '#' || url == '' || url == undefined) {
            Ext.Msg.alert('提示', '此菜单还没有指定请求地址,无法为您打开页面.');
        } else {
            var index = url.indexOf('.ered');
            if (index != -1)
                url = url + '&menuid4Log=' + id;

            id = id + "_for_tab";

            var pattern = "webtellerHome";
            if (url.substr(0, pattern.length) != pattern) {
                url = _contextPath_ + '/' + url;
            } else {
                url = url.substr(pattern.length, url.length);
                url = WEBTELLER_HOME + url + "&Brc=" + Brc + "&Teller=" + Teller;
            }
            if (Ext.getCmp(id)) {
                if (relaod != undefined && relaod) {
                    Ext.getCmp(id).items.get(0).setUrl(url);
                    Ext.getCmp(id).show();
                } else
                    Ext.getCmp(id).show();
            }
            else {
//            if (node.attributes.tabicon != undefined && node.attributes.tabicon != null) {
//                title = "<img align='top' class='IEPNG' src='./resource/image/ext/" + node.attributes.tabicon + "'/>" + node.text
//            }
                tabPanel.add({
                            id:id,
                            title: title,
                            tabTitle:title,
                            closable:true,
                            iconCls:iconCls,
                            layout:'fit',
                            items:[new Ext.ux.IFrameComponent({ id:id, url:url})]
                        }).show();
            }
        }
    }

    logout = function() {
        Ext.MessageBox.confirm("", "您确定要注销登陆吗？", function(btn) {
            if (btn == 'yes') {
                window.location.href = _contextPath_;
            }
        });
    };

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
                bodyStyle : 'padding:5 5 0',
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
                        id : 'password',
                        enableKeyEvents:true,
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
                width : 300,
                height : 235,
                resizable : false,
                draggable : true,
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
                        text : '保存',
                        iconCls : 'acceptIcon',
                        handler : function() {
                            updateUser();
                        }
                    },
                    {
                        text : '关闭',
                        iconCls : 'deleteIcon',
                        handler : function() {
                            userWindow.hide();
                        }
                    }
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
                                Ext.Msg.alert('提示', '数据读取失败:'
                                        + action.failureType);
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
                        Ext.MessageBox.alert('提示', '登录帐户信息修改成功');
                        userWindow.hide();
                    },
                    failure : function(form, action) {
                        var msg = action.result.msg;
                        Ext.MessageBox.alert('提示', '人员数据保存失败');
                    }
                });
    }

    Ext.get("changePassword").on("click", function() {
        updateUserInit()
    });
    
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

    var winWaring = new Ext.ux.Notification({
                animateTarget: 'bottomer',
                autoDestroy: false,
                //                hideDelay: 10000,
                html:"<span style='color:red;'>您有新的短信息，请注意查收</span>"
                ,title:'短信息'
                ,buttons: [
                    {
                        text: '处理',
                        handler:function() {
//                    openIframe();
                            winWaring.hide();
                        }
                    },
                    {
                        text: '取消',
                        handler: function() {
                            winWaring.hide();
                        }
                    }
                ]
            });
//    winWaring.show();
});

function showTime() {
    var date = new Date();
    var h = date.getHours();
    h = h < 10 ? '0' + h : h;
    var m = date.getMinutes();
    m = m < 10 ? '0' + m : m;
    var s = date.getSeconds();
    s = s < 10 ? '0' + s : s;
    document.getElementById('rTime').innerHTML = h + ":" + m + ":" + s;
}

window.onload = function() {
    setInterval("showTime()", 1000);
}


//tabPanel.on("tabchange", function(combo, record, index) {
//	alert("test");
//});