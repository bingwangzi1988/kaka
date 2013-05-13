package org.eredlab.g4.arm.util.idgenerator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eredlab.g4.bmf.base.IDao;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.ccl.id.SequenceStorer;
import org.eredlab.g4.ccl.id.fomater.DefaultSequenceFormater;
import org.eredlab.g4.ccl.id.generator.DefaultIDGenerator;
import org.eredlab.g4.ccl.id.prefix.DefaultPrefixGenerator;
import org.eredlab.g4.ccl.id.sequence.DefaultSequenceGenerator;
import org.eredlab.g4.ccl.util.G4Utils;
import org.eredlab.g4.ccl.util.GlobalConstants;

/**
 * ID������
 *
 * @author XiongChun
 * @since 2009-07-13
 */
public class IdGenerator {
    private static Log log = LogFactory.getLog(IdGenerator.class);
    private static int catche = 1;
    private static IDao g4Dao = (IDao) SpringBeanLoader.getSpringBean("g4Dao");

    /**
     * �ֶ�����
     */
    private String fieldname;

    public IdGenerator(String pFieldName) {
        setFieldname(pFieldName);
    }

    public IdGenerator() {
    }

    /**
     * ��ȡID������ʵ��
     *
     * @return DefaultIDGenerator
     */
    public DefaultIDGenerator getDefaultIDGenerator() {
        Dto dto = new BaseDto();
        dto.put("fieldname", getFieldname());
        dto = (BaseDto) g4Dao.queryForObject("getEaSequenceByFieldName", dto);

        DefaultIDGenerator idGenerator = new DefaultIDGenerator();

        DefaultSequenceFormater sequenceFormater = new DefaultSequenceFormater();
        sequenceFormater.setPattern(dto.getAsString("pattern"));
        idGenerator.setSequenceFormater(sequenceFormater);
        DefaultSequenceGenerator sequenceGenerator = new DefaultSequenceGenerator(getFieldname());

        SequenceStorer sequenceStorer = new DBSequenceStorer();
        sequenceGenerator.setSequenceStorer(sequenceStorer);
        sequenceGenerator.setCache(catche);
        idGenerator.setSequenceGenerator(sequenceGenerator);

        DefaultPrefixGenerator prefixGenerator = new DefaultPrefixGenerator();
        prefixGenerator.setPrefix(dto.getAsString("prefix"));
        idGenerator.setPrefixGenerator(prefixGenerator);

        return idGenerator;
    }

    /**
     * �˵����ID������(�Զ���)
     *
     * @param pParentid �˵���ŵĲο����
     * @return
     */
    public static String getMenuIdGenerator(String pParentid) {
        String maxSubMenuId = (String) g4Dao.queryForObject("getMaxSubMenuId", pParentid);
        String menuId = null;
        if (G4Utils.isEmpty(maxSubMenuId)) {
            menuId = "01";
        } else {
            int length = maxSubMenuId.length();
            String temp = maxSubMenuId.substring(length - 2, length);
            int intMenuId = Integer.valueOf(temp).intValue() + 1;
            if (intMenuId > 0 && intMenuId < 10) {
                menuId = "0" + String.valueOf(intMenuId);
            } else if (10 <= intMenuId && intMenuId <= 99) {
                menuId = String.valueOf(intMenuId);
            } else if (intMenuId > 99) {
                log.error(GlobalConstants.Exception_Head + "���ɲ˵����Խ����.ͬ���ֵܽڵ���Ϊ[01-99]\n�������ϵͳ����Ա��ϵ!");
            } else {
                log.error(GlobalConstants.Exception_Head + "���ɲ˵���ŷ���δ֪����,��Ϳ�����Ա��ϵ!");
            }
        }
        return pParentid + menuId;
    }

    /**
     * ���ű��ID������(�Զ���)
     *
     * @param pParentid �˵���ŵĲο����
     * @return
     */
    public static String getDeptIdGenerator(String pParentid) {
        String maxSubDeptId = (String) g4Dao.queryForObject("getMaxSubDeptId", pParentid);
        String deptid = null;
        if (G4Utils.isEmpty(maxSubDeptId)) {
            deptid = "001";
        } else {
            int length = maxSubDeptId.length();
            String temp = maxSubDeptId.substring(length - 3, length);
            int intDeptId = Integer.valueOf(temp).intValue() + 1;
            if (intDeptId > 0 && intDeptId < 10) {
                deptid = "00" + String.valueOf(intDeptId);
            } else if (10 <= intDeptId && intDeptId <= 99) {
                deptid = "0" + String.valueOf(intDeptId);
            } else if (100 <= intDeptId && intDeptId <= 999) {
                deptid = String.valueOf(intDeptId);
            } else if (intDeptId > 999) {
                log.error(GlobalConstants.Exception_Head + "���ɲ��ű��Խ����.ͬ���ֵܽڵ���Ϊ[001-999]\n�������ϵͳ����Ա��ϵ!");
            } else {
                log.error(GlobalConstants.Exception_Head + "���ɲ��ű�ŷ���δ֪����,��Ϳ�����Ա��ϵ!");
            }
        }
        return pParentid + deptid;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }
}
