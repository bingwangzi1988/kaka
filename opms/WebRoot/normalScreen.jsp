<%@ page contentType="text/html; charset=GBK"%>
<html>
<head>
  <title>海辉软件金融IC卡系统</title>
  <style type="text/css">
  .waitMsg{
    font-family: "宋体";
    font-size: 13px;
  }
  </style>
</head>
<body onload="init();">
<span class="waitMsg">正在跳转,请等待...</span>
</body>
<script language="javascript">
   function init(){
     window.opener = null;
     //window.close();
     //self.close();
     var win = window.open('login.ered?reqCode=init', '_blank',
       'top=0,left=0,location=yes,status=yes,menubar=yes,toolbar=yes,scrollbars=yes,resizable=yes,' 
       + 'height=' + (screen.availHeight-30) + ', width=' + screen.availWidth);
     self.close();
	 }
</script>
</html>