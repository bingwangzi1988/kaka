/**
 * ��ʾ����ȴ�����������
 * 
 * @param {}
 *            msg
 */
function showWaitMsg(msg) {
	Ext.MessageBox.show({
				title : 'ϵͳ��ʾ',
				msg : msg == null ? '���ڴ�������,���Ժ�...' : msg,
				progressText : 'processing now,please wait...',
				width : 300,
				wait : true,
				waitConfig : {
					interval : 150
				}
			});
}

/**
 * ��������ȴ�����������
 */
function hideWaitMsg() {
	Ext.MessageBox.hide();
}

/**
 * ��JS����ת��ΪJS�ַ��� ���ѡ��ר��
 */
function jsArray2JsString(arrayChecked, id) {
	var strChecked = "";
	for (var i = 0; i < arrayChecked.length; i++) {
		strChecked = strChecked + arrayChecked[i].get(id) + ',';
	}
	return strChecked.substring(0, strChecked.length - 1)
}

/**
 * ���Ext.Form��Ԫ��
 */
function clearForm(pForm) {
//	var formItems = pForm.items['items'];
//	for (i = 0; i < formItems.length; i++) {
//		element = formItems[i];
//		element.setValue('');
//		element.reset(); // ������ֺ�ɫ������
//	};
    pForm.reset();
    pForm.clearInvalid();

}

/**
 * ���Ƶ�������
 */
function copyToClipboard(txt) {
	if (window.clipboardData) {
		window.clipboardData.clearData();
		window.clipboardData.setData("Text", txt);
	} else if (navigator.userAgent.indexOf("Opera") != -1) {
		window.location = txt;
	} else if (window.netscape) {
		try {
			netscape.security.PrivilegeManager
					.enablePrivilege("UniversalXPConnect");
		} catch (e) {
			Ext.Msg
					.alert(
							'��ʾ',
							"���Ƶ�Ԫ�������������ܾ���\n�����������ַ������'about:config'���س�\nȻ��'signed.applets.codebase_principal_support'����Ϊ'true'")
		}
		var clip = Components.classes['@mozilla.org/widget/clipboard;1']
				.createInstance(Components.interfaces.nsIClipboard);
		if (!clip)
			return;
		var trans = Components.classes['@mozilla.org/widget/transferable;1']
				.createInstance(Components.interfaces.nsITransferable);
		if (!trans)
			return;
		trans.addDataFlavor('text/unicode');
		var str = new Object();
		var len = new Object();
		var str = Components.classes["@mozilla.org/supports-string;1"]
				.createInstance(Components.interfaces.nsISupportsString);
		var copytext = txt;
		str.data = copytext;
		trans.setTransferData("text/unicode", str, copytext.length * 2);
		var clipid = Components.interfaces.nsIClipboard;
		if (!clip)
			return false;
		clip.setData(trans, null, clipid.kGlobalClipboard);
		// Ext.Msg.alert('��ʾ','��Ԫ�������ѳɹ����Ƶ�������!')
	}
}

/**
 * ��ʼ�������ӡ����
 */
function doPrint(pFlag, pWidth, pHeight) {
	var initUrl = '/report.ered?reqCode=initAppletPage';
	if (!Ext.isEmpty(pFlag))
		initUrl = initUrl + '&flag=' + pFlag;
	if (Ext.isEmpty(pWidth))
		pWidth = 800;
	if (Ext.isEmpty(pHeight))
		pHeight = 600;
	var left = (screen.width - pWidth) / 2;
	var top = (screen.height - pHeight) / 2;
	var str = 'width='
			+ pWidth
			+ ',height='
			+ pHeight
			+ ',top='
			+ top
			+ ",left="
			+ left
			+ ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
	window.open(webContext + initUrl, '', str);
}

/**
 * ��ʼ�������ӡ���ڣ����ڹرպ�ִ�лص�����
 */
function doPrintWithCallback(pFlag, pWidth, pHeight) {
	var initUrl = '/report.ered?reqCode=initAppletPage';
	if (!Ext.isEmpty(pFlag))
		initUrl = initUrl + '&flag=' + pFlag;
	if (Ext.isEmpty(pWidth))
		pWidth = 800;
	if (Ext.isEmpty(pHeight))
		pHeight = 600;
	var timer, popwin;
	var left = (screen.width - pWidth) / 2;
	var top = (screen.height - pHeight) / 2;
	var str = 'width='
			+ pWidth
			+ ',height='
			+ pHeight
			+ ',top='
			+ top
			+ ",left="
			+ left
			+ ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
	popwin = window.open(webContext + initUrl, '', str);
	timer = window.setInterval(function() {
				if (popwin.closed == true) {
					window.clearInterval(timer);
					Ext.MessageBox.confirm('��ȷ��', '��ӡ�Ƿ�ɹ�?',
							function(btn, text) {
								if (btn == 'yes') {
									// ������ص�������ʵ�ִ�ӡ������¼����,�˺�������д��Ext��������
									fnPrintCallback();
								} else {
									return;
								}
							});
				}
			}, 500);
}

/**
 * ��ʼ��PDF��������
 */
function doExport(pFlag, pWidth, pHeight) {
	var initUrl = '/report.ered?reqCode=initPdfPage';
	if (!Ext.isEmpty(pFlag))
		initUrl = initUrl + '&flag=' + pFlag;
	if (Ext.isEmpty(pWidth))
		pWidth = 800;
	if (Ext.isEmpty(pHeight))
		pHeight = 600;
	var left = (screen.width - pWidth) / 2;
	var top = (screen.height - pHeight) / 2;
	var str = 'width='
			+ pWidth
			+ ',height='
			+ pHeight
			+ ',top='
			+ top
			+ ",left="
			+ left
			+ ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
	window.open(webContext + initUrl, '', str);
}

/**
 * ��ʼ����PDF�������ڣ����ڹرպ�ִ�лص�����
 */
function doExportWithCallback(pFlag, pWidth, pHeight) {
	var initUrl = '/report.ered?reqCode=initPdfPage';
	if (!Ext.isEmpty(pFlag))
		initUrl = initUrl + '&flag=' + pFlag;
	if (Ext.isEmpty(pWidth))
		pWidth = 800;
	if (Ext.isEmpty(pHeight))
		pHeight = 600;
	var timer, popwin;
	var left = (screen.width - pWidth) / 2;
	var top = (screen.height - pHeight) / 2;
	var str = 'width='
			+ pWidth
			+ ',height='
			+ pHeight
			+ ',top='
			+ top
			+ ",left="
			+ left
			+ ',toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no';
	popwin = window.open(webContext + initUrl, '', str);
	timer = window.setInterval(function() {
				if (popwin.closed == true) {
					window.clearInterval(timer);
					Ext.MessageBox.confirm('��ȷ��', '��ӡ/�����Ƿ�ɹ�?', function(btn,
									text) {
								if (btn == 'yes') {
									// ������ص�������ʵ�ִ�ӡ������¼����,�˺�������д��Ext��������
									fnExportCallback();
								} else {
									return;
								}
							});
				}
	}, 500);
}

/**
 * ͨ��iFrameʵ����ajax�ļ�����
 */
function exportExcel(url) {
	var exportIframe = document.createElement('iframe');
	exportIframe.src = url;
	exportIframe.style.display = "none";
	document.body.appendChild(exportIframe);
}

// ���������֤15λ��18λ�����֤�����Ұ������պ�У��λ����֤��
function isIdCardNo(num) {
	if (Ext.isEmpty(num))
		return false;
	num = num.toUpperCase();
	// ���֤����Ϊ15λ����18λ��15λʱȫΪ���֣�18λǰ17λΪ���֣����һλ��У��λ������Ϊ���ֻ��ַ�X��
	if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {
		Ext.MessageBox.alert('��ʾ',
				'��������֤�ų��Ȳ��ԣ����ߺ��벻���Ϲ涨��\n15λ����ӦȫΪ���֣�18λ����ĩλ����Ϊ���ֻ�X��');
		return false;
	}
	// У��λ����ISO 7064:1983.MOD 11-2�Ĺ涨���ɣ�X������Ϊ������10��
	// ����ֱ�����������ں�У��λ
	var len, re;
	len = num.length;
	if (len == 15) {
		re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
		var arrSplit = num.match(re);
		// ������������Ƿ���ȷ
		var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/'
				+ arrSplit[4]);
		var bGoodDay;
		bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
				&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
				&& (dtmBirth.getDate() == Number(arrSplit[4]));
		if (!bGoodDay) {
			Ext.MessageBox.alert('��ʾ', '��������֤����������ڲ��ԣ�');
			return false;
		} else {
			// ��15λ���֤ת��18λ
			// У��λ����ISO 7064:1983.MOD 11-2�Ĺ涨���ɣ�X������Ϊ������10��
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
					'3', '2');
			var nTemp = 0, i;
			num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
			for (i = 0; i < 17; i++) {
				nTemp += num.substr(i, 1) * arrInt[i];
			}
			num += arrCh[nTemp % 11];
			return num;
		}
	}
	if (len == 18) {
		re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
		var arrSplit = num.match(re);
		// ������������Ƿ���ȷ
		var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"
				+ arrSplit[4]);
		var bGoodDay;
		bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
				&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
				&& (dtmBirth.getDate() == Number(arrSplit[4]));
		if (!bGoodDay) {
			// alert(dtmBirth.getYear());
			// alert(arrSplit[2]);
			Ext.MessageBox.alert('��ʾ', '��������֤����������ڲ��ԣ�');
			return false;
		} else {
			// ����18λ���֤��У�����Ƿ���ȷ��
			// У��λ����ISO 7064:1983.MOD 11-2�Ĺ涨���ɣ�X������Ϊ������10��
			var valnum;
			var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
					8, 4, 2);
			var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4',
					'3', '2');
			var nTemp = 0, i;
			for (i = 0; i < 17; i++) {
				nTemp += num.substr(i, 1) * arrInt[i];
			}
			valnum = arrCh[nTemp % 11];
			if (valnum != num.substr(17, 1)) {
				Ext.MessageBox.alert('��ʾ', '18λ���֤��У���벻��ȷ��Ӧ��Ϊ:' + valnum);
				return false;
			}
			return num;
		}
	}
	return false;
}

/**
 * ����Cookie
 * 
 * @param {} name
 * @param {} value
 */
function setCookie(name, value) {
	var argv = setCookie.arguments;
	var argc = setCookie.arguments.length;
	var expires = (argc > 2) ? argv[2] : null;
	if (expires != null) {
		var LargeExpDate = new Date();
		LargeExpDate.setTime(LargeExpDate.getTime()
				+ (expires * 1000 * 3600 * 24));
	}
	document.cookie = name
			+ "="
			+ escape(value)
			+ ((expires == null) ? "" : ("; expires=" + LargeExpDate
					.toGMTString()));
}

/**
 * ��ȡCookie
 * 
 * @param {} Name
 * @return {}
 */
function getCookie(Name) {
	var search = Name + "="
	if (document.cookie.length > 0) {
		offset = document.cookie.indexOf(search)
		if (offset != -1) {
			offset += search.length
			end = document.cookie.indexOf(";", offset)
			if (end == -1)
				end = document.cookie.length
			return unescape(document.cookie.substring(offset, end))
		} else
			return ""
	}
}

/**
 * �ӻ��������Cookie
 * 
 * @param {} name
 */
function clearCookie(name) {
	var expdate = new Date();
	expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
	setCookie(name, "", expdate);
}
