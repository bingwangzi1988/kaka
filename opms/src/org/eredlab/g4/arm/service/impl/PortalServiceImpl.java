package org.eredlab.g4.arm.service.impl;

import org.eredlab.g4.arm.service.PortalService;
import org.eredlab.g4.arm.web.tag.vo.PortalVo;
import org.eredlab.g4.bmf.base.BaseServiceImpl;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.id.util.IDHelper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-5-19
 * Time: ÏÂÎç2:21
 * desc:
 */
public class PortalServiceImpl extends BaseServiceImpl implements PortalService {
    public List<PortalVo> getPortletList(Dto pDto) {
        List<PortalVo> portletList = g4Dao.queryForList("queryPortletForUser", pDto);
        return portletList;
    }

    public void savePortlet(Dto pDto) {
        pDto.put("portletid", IDHelper.uuid());
        g4Dao.insert("savePortlet", pDto);
    }

    public void deletePortlet(Dto pDto) {
        g4Dao.delete("deletePortlet", pDto);
        g4Dao.update("updatePortletDelete", pDto);
    }

    public void updatePortletHeight(Dto pDto) {
        g4Dao.update("updatePortlet", pDto);
    }

    public void updatePortletMove(Dto pDto) {
        g4Dao.update("updatePortletDelete", pDto);
        g4Dao.update("updatePortletAdd", pDto);
        g4Dao.update("updatePortlet", pDto);
    }

    public void updatePortal(Dto pDto) {
        g4Dao.update("updatePortal", pDto);
        g4Dao.update("updatePortletLayout", pDto);
    }
}
