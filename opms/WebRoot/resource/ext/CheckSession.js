/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-4-29
 * Time: ����11:28
 * To change this template use File | Settings | File Templates.
 */

Ext.Ajax.on('requestcomplete', checkUserSessionStatus, this);
function checkUserSessionStatus(conn, response, options) {
	var t = response.responseText;
	if(t.charAt(0)=="{"){
       if( Ext.decode(t).xlsFileFileName ){
      		return;
       }
	}else{
	    if (typeof response.getResponseHeader("sessionstatus") != 'undefined') {
	        Ext.MessageBox.alert('��ʾ', '���ڳ�ʱ��δ����,���лỰ�ѳ�ʱ;������ǿ���ض��򵽵�¼ҳ��!', function() {
	            if (parent != null) {
	                top.location.href = _contextPath_;
	            } else
	                window.location.href = _contextPath_;
	        });
	    }
	}
}
