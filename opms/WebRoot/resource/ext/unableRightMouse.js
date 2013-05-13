/**
 * Created by IntelliJ IDEA.
 * User: zly
 * Date: 12-6-11
 * Time: ÉÏÎç10:00
 * To change this template use File | Settings | File Templates.
 */

document.captureEvents(Event.MOUSEUP);

function nocontextmenu()
{
	alert("test1");
	event.cancelBubble=true;
	event.returnValue=false;
	return false;
}

function norightclick(e)
{
	alert("test2");
	if(window.Event)
	{
		if(e.which==2 || e.which==3)
		{
			return false;
		}
		else if(event.button==2 || event.button==3)
		{
			event.cancelBubble=true;
			event.returnValue=false;
			return false;
		}
	}
}
document.oncontextmenu=nocontextmenu; //for IE5
document.onmousedown=norightclick;//for other