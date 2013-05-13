package org.eredlab.g4.arm.service;

import org.eredlab.g4.arm.web.tag.vo.PortalVo;
import org.eredlab.g4.bmf.base.BaseService;
import org.eredlab.g4.ccl.datastructure.Dto;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-5-19
 * Time: обнГ2:03
 * desc:
 */
public interface PortalService extends BaseService {

    List<PortalVo> getPortletList(Dto pDto);

    void savePortlet(Dto pDto);

    void deletePortlet(Dto pDto);

    void updatePortletHeight(Dto pDto);

    void updatePortletMove(Dto pDto);

    void updatePortal(Dto pDto);
}
