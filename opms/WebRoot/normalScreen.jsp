<%@ page contentType="text/html; charset=GBK"%>
<html>
<head>
  <title>�����������IC��ϵͳ</title>
  <style type="text/css">
  .waitMsg{
    font-family: "����";
    font-size: 13px;
  }
  </style>
</head>
<body onload="init();">
<span class="waitMsg">������ת,��ȴ�...</span>
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