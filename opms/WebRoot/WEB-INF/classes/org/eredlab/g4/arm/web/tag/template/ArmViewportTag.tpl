<!-- ��<eRedG4:arm.Viewport/>��ǩ���ɵĴ��뿪ʼ -->
#foreach($card in $cardList)
<div id="div.card.${card.menuid}" style="overflow: auto; height: 100%; width: 100%;"></div>
#end
${scriptStart}
var default_theme = '${theme}';
var mainTabs;
Ext.onReady(function(){
            mainTabs = new Ext.TabPanel({
                region:'center', 
                activeTab:0,
                id:'mainTabs',
                enableTabScroll:true,
                height:550,
                border:false,
                frame:true,
                plugins: new Ext.ux.TabCloseMenu(),
                items:[{
                      title:"<img align='top' class='IEPNG' src='./resource/image/ext/user.png'/>${welcomePageTitle}",
                      listeners: {activate: function(){Ext.getCmp('centerPanel').setTitle('${centerTitle} -> ${welcomePageTitle}');}},
                      html:"<iframe name='mainFrame'  src='index.ered?reqCode=preferencesInit' scrolling='auto' frameborder='0' width='100%' height='100%' ></iframe>"
                    }]
             });
			 
             var viewport = new Ext.Viewport({
                 layout:'border',
                 items:[
                 new Ext.Panel({
                     region:'north',
                     contentEl:'north', 
                     iconCls:'application_homeIcon', 
                     height:85,
                     collapsible:true,
					 border:false,
                     layout: 'fit',
                     title:'${northTitle}'}),
			     
                 new Ext.BoxComponent({
                     region:'south',
                     contentEl: 'south',
                     height:17,
                     layout: 'fit',
                     collapsible: true}),
			     
                 {region:'center', 
                     id: 'centerPanel', 
                     iconCls:'',
                     title:'${centerTitle}',
                     autoScroll:false,
                     layout: 'fit',
                     items:[mainTabs]},
                   {region:'west',
                    width: 220,
                    collapsible: true,
                    minSize: 200,
                    maxSize: 350,
                    split: true,
                    //collapseMode:'mini',
					iconCls:'book_previousIcon',
                    title: '${westTitle}',
                    layout:'accordion',
                    layoutConfig:{
                      animate:true,
					  activeOnTop : ${activeOnTop}
					},
                   items: [
#foreach($card in $cardList)
	                {
                    autoScroll:true,
                    border:false,
                    contentEl: 'div.card.${card.menuid}',
#if(${card.iconcls}&&${card.iconcls}!="")
                    iconCls:'${card.iconcls}',
#end
					title:'${card.menuname}'
                   }
#if(${card.isNotLast})
					  ,
#end
#end
				   ]
                   }
                ]}); 
    });
/**
 * ��Ӧ���ڵ㵥���¼�
 */
function addTab(url,name,menuid,pathCh,icon){
  var id = "tab_id_" + menuid;
  if(url == '#' || url == ''){
    Ext.Msg.alert('��ʾ', '�˲˵���û��ָ�������ַ,�޷�Ϊ����ҳ��.');
  }else{
  var index = url.indexOf('.ered');
  if(index != -1)
    url = url + '&menuid4Log=' + menuid;
  var n = mainTabs.getComponent(id);
  if (!n) {
     // �����centerPanel��������,����Գ�����ӰmainTabs
     //Ext.getCmp('centerPanel').getEl().mask('<span style=font-size:12>���ڼ���ҳ��,���Ե�...</span>', 'x-mask-loading');
     // document.getElementById('endIeStatus').click();//���Iframe IE���ز���ȫ������
     // ����IE��FF����.click()����
     var endIeStatus = document.getElementById("endIeStatus");
     if(document.createEvent){
         var ev = document.createEvent('HTMLEvents');
         ev.initEvent('click', false, true);
         endIeStatus.dispatchEvent(ev);
     }
     else endIeStatus.click();
     n = mainTabs.add({
       id:id,
       title:"<img align='top' class='IEPNG' src='./resource/image/ext/" + icon + "'/>" + name,
       closable:true,
       layout:'fit',
       listeners: {activate: function(){Ext.getCmp('centerPanel').setTitle(pathCh)}},
       html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src='+url+'></iframe>'
	   //�������ҳ��ʹ������������Ӱ����ģʽ��ʹ������Ĵ���unmaskCenterPanel();ҳ�������Ϻ�ȡ����Ӱ
	   //html:'<iframe scrolling="auto" frameborder="0" onload="unmaskCenterPanel()" width="100%" height="100%" src='+url+'></iframe>'
         });
       }
  mainTabs.setActiveTab(n);
 }
  }

// ���Iframe IE���ز���ȫ������
function endIeStatus(){}

Ext.EventManager.on(window, 'load', function(){
	 setTimeout(
		 function() {
			Ext.get('loading').remove();
			Ext.get('loading-mask').fadeOut({remove:true});
			}, 200); 
});
  
/**
 * ȡ����Ӱ(����ҳ�������ɺ�ͨ��parent.XXXX������)
 */
function unmaskCenterPanel(){
 // �����centerPanel��������,����Գ�����Ӱ
 Ext.getCmp('centerPanel').getEl().unmask();
}
${scriptEnd}
<style type="text/css">
 #loading-mask {
	Z-INDEX: 20000;
	LEFT: 0px;
	WIDTH: 100%;
	POSITION: absolute;
	TOP: 0px;
	HEIGHT: 100%;
	BACKGROUND-COLOR: white
}
#loading {
	PADDING-RIGHT: 2px;
	PADDING-LEFT: 2px;
	Z-INDEX: 20001;
	LEFT: 45%;
	PADDING-BOTTOM: 2px;
	PADDING-TOP: 2px;
	POSITION: absolute;
	TOP: 40%;
	HEIGHT: auto
}
#loading IMG {
	MARGIN-BOTTOM: 5px
}
#loading .loading-indicator {
	PADDING-RIGHT: 10px;
	PADDING-LEFT: 10px;
	BACKGROUND: white;
	PADDING-BOTTOM: 10px;
	MARGIN: 0px;
	FONT: 12px ����, arial, helvetica;
	COLOR: #555;
	PADDING-TOP: 10px;
	HEIGHT: auto;
	TEXT-ALIGN: center
}

.banner {
	font-family: "����";
	font-size: 12px;
	color:$themeColor;
}
</style>
<!--��ʾloding����-->
<DIV id=loading-mask></DIV>
<DIV id=loading>
<DIV class=loading-indicator><IMG style="MARGIN-RIGHT: 8px"
	height=32
	src="./resource/image/ajax1.gif"
	width=36 align=absMiddle>���ڳ�ʼ��,���Ե�...</DIV>
</DIV>
<div id="north">
<table border="0" cellpadding="0" cellspacing="0" width="100%"  
	height="60" background="./resource/image/banner_background/${theme}.png">
	<tr >
		<td style="padding-left:15px"><img class="IEPNG"
			src="${banner}" />
		</td>
		<td style="padding-right:5px">
		  <table width="100%"   border="0" cellpadding="0" cellspacing="3" class="banner">
		  	<tr align="right">
		      <td>${welcome},${username}! ������:${date} ${week} <span id="rTime"><span></td>
		    </tr>
		    <tr align="right">
		    <td>
		      <table border="0" cellpadding="0" cellspacing="1">
		        <tr>
		          <td><div id = "themeDiv"></td> 
		          <td>&nbsp;</td>
		          <td><div id = "configDiv"></td> 
		          <td>&nbsp;</td>
		          <td><div id = "closeDiv"></td>
		        </tr>
		      </table>
		    </td>
		    </tr>
		  </table>
		</td>
	</tr>
</table>
</div>
<div id="south" align="left">
<table class="banner" width="100%">
<tr>
<td width="65%"><nobr>&nbsp;<img class="IEPNG" src="./resource/image/ext/comments2.png" />&nbsp;��ӭ��,${username}!&nbsp;�ʻ�:${account}&nbsp;��������:${deptname}</nobr></td>
<td width="35%"><div align="right"><nobr>${copyright}</nobr></div></td>
</tr>
</table>
</div>
<a href="#" onclick="javascript:endIeStatus();" id="endIeStatus"
	style="display: none;" />
<!-- ��<eRedG4:arm.Viewport/>��ǩ���ɵĴ������ -->
