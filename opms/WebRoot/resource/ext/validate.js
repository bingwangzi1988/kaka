/**
 * Created by IntelliJ IDEA.
 * User: zly
 * Date: 12-6-6
 * Time: ����10:00
 * To change this template use File | Settings | File Templates.
 */

/**
 * ��֤�Ƿ�����
 * */
   function valNum(val)
    {
 	  return /^[0-9]*$/.test(val);
    }
   
   
   /**
    * ��֤�Ƿ��������Ӣ�Ļ�������
    * */
      function valNumAndStr(val)
      {
    	  return /^[0-9a-zA-z]+$/.test(val);
      }
      
      /**
       * ȥ��ո�
       **/
       function ltrim(s)
       {
    	   return s.replace(/(^\s*)/,"");
       }
       /**
        * ȥ�ҿո�
        **/
       function rtrim(s)
       {
    	   return s.replace(/(\s*$)/,"");
       }
       
       /**
        * ȥ���ҿո�
        **/
       function trim(s)
       {
    	   return rtrim(ltrim(s));
       }
       
       
       /**
        * ��֤�Ƿ��ַ�
        * */
       function valStr(str){
    	   var valStr="��`����^\\!@/|/;?'\"�����ۣݣ�����������������<>�������������������������������������������������������������������������������������������";
    	   if(valStr.indexOf(str)!=-1)
			 {
		 	    return false;
			 }
    	   return true;
       }
       
       /**
        * 
        * ��֤�ַ������Ƿ��������ַ�������еĻ���ɾ�������ַ�
        * */
       function validateStrAndGetStr(str)
       {
    	   var arr=str.split("");
		   var tmp="";
		   for(var i=0;i<arr.length;i++)
		   {
			    var temp="";
		 	    if(valStr(arr[i]))
				{
		 	    	temp=arr[i];
				}
				 tmp+=temp;
			}
			return tmp;
       }
       
       /**
        * ֻ����������+��ĸ
        * ��֤�ַ������Ƿ��������ַ�������еĻ���ɾ�������ַ�
        * */
       function validateStr(str)
       {
    	   var arr=str.split("");
		   var tmp="";
		   for(var i=0;i<arr.length;i++)
		   {
			    var temp="";
		 	    if(valStr(arr[i]) && valNumAndStr(arr[i]))
				{
		 	    	temp=arr[i];
				}
				 tmp+=temp;
			}
			return tmp;
       }
       
       
       