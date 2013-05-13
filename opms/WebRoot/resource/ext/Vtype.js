/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-5-10
 * Time: ����10:00
 * To change this template use File | Settings | File Templates.
 */
Ext.apply(Ext.form.VTypes, {
            //�����������֤
            password: function(val, field) {
                if (field.initialPassField) {
                    var pwd = Ext.getCmp(field.initialPassField);
                    return (val == pwd.getValue());
                }
                return true;
            },

            passwordText: '�������벻һ��',

            IPAddress:  function(v) {
                return /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(v);
            },
            IPAddressText: '�����Ǹ���Чip��ַ',
            IPAddressMask: /[\d\.]/i,

            phone:  function(v) {
                return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test(v);
            },
            phoneText: '�����ǵ绰����',
            phoneMask: /[\d\-\+]/i,

            mobile:  function(v) {
                return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/.test(v);
            },
            mobileText: '�������ֻ�����',
            mobileMask: /[\d\-]/i,

            postCode:  function(v) {
                return /^[a-zA-Z0-9 ]{3,12}$/.test(v);
            },
            postCodeText: '��������������',
            postCodeMask: /[\d]/i,

            idcard: function checkIdcard(idcard) {
                var area = {11:"����",12:"���",13:"�ӱ�",14:"ɽ��",15:"���ɹ�",21:"����",22:"����",23:"������",31:"�Ϻ�",32:"����",33:"�㽭",34:"����",35:"����",36:"����",37:"ɽ��",41:"����",42:"����",43:"����",44:"�㶫",45:"����",46:"����",50:"����",51:"�Ĵ�",52:"����",53:"����",54:"����",61:"����",62:"����",63:"�ຣ",64:"����",65:"�½�",71:"̨��",81:"���",82:"����",91:"����"}
                var Y,JYM;
                var S,M;
                var idcard_array = new Array();
                idcard_array = idcard.split("");
                //��������
                if (area[parseInt(idcard.substr(0, 2))] == null)
                    //return 4;
                    return false;
                //��ݺ���λ������ʽ����
                switch (idcard.length) {
                    case 15:
                        if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 )) {
                            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//���Գ������ڵĺϷ���
                        } else {
                            ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//���Գ������ڵĺϷ���
                        }
                        if (ereg.test(idcard))
                           // return 0;
                           return true;
                        else
//                            return 2;
                        return false;
                        break;
                    case 18:
                        //18λ��ݺ�����
                        //�������ڵĺϷ��Լ��
                        //��������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
                        //ƽ������:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
                        if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0 )) {
                            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//����������ڵĺϷ���������ʽ
                        } else {
                            ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//ƽ��������ڵĺϷ���������ʽ
                        }
                        if (ereg.test(idcard)) {//���Գ������ڵĺϷ���
                            //����У��λ
                            S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
                                    + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
                                    + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
                                    + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
                                    + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
                                    + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
                                    + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
                                    + parseInt(idcard_array[7]) * 1
                                    + parseInt(idcard_array[8]) * 6
                                    + parseInt(idcard_array[9]) * 3;
                            Y = S % 11;
                            if (idcard_array[17] == "x") {
                                idcard_array[17] = "X";
                            }
                            //alert(Y);
                            //M = "F";
                            JYM = "10X98765432";
                            M = JYM.substr(Y, 1);//�ж�У��λ
                            if (M == idcard_array[17])
              //                  return 0; //���ID��У��λ
                                return true;
                            else
               //                 return 3;
                            return false;
                        }
                        else
                            //return 2;
                        return false;
                        break;
                    default:
                        //return 1;
                          return false
                        break;
                }
            },
            idcardText: '���֤�������',
            idcardMask: /[\d\X]/i

//             var idcardErrors=new Array(
//                    "��֤ͨ��!",
//                    "���֤����λ������!",
//                    "���֤����������ڳ�����Χ���зǷ��ַ�!",
//                    "���֤����У�����!",
//                    "���֤�����Ƿ�!"
//                    );
        });