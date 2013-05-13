package com.common.business.commonaction;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.arm.service.PortalService;
import org.eredlab.g4.arm.web.tag.vo.PortalVo;
import org.eredlab.g4.bmf.util.SpringBeanLoader;
import org.eredlab.g4.ccl.datastructure.Dto;
import org.eredlab.g4.ccl.datastructure.impl.BaseDto;
import org.eredlab.g4.rif.util.SessionContainer;
import org.eredlab.g4.rif.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-5-19
 * Time: ÏÂÎç1:39
 * desc:
 */
public class PortalAction extends ActionSupport {

    PortalService portalService = (PortalService) SpringBeanLoader.getSpringBean("portalService");

    private JSONArray resultArray;

    public JSONArray getResultArray() {
        return resultArray;
    }

    public void setResultArray(JSONArray resultArray) {
        this.resultArray = resultArray;
    }

    public String init() {

        HttpServletRequest request = ServletActionContext.getRequest();
        SessionContainer sessionContainer = WebUtils.getSessionContainer(request);
        String userid = sessionContainer.getUserInfo().getUserid();

        Dto dto = new BaseDto();
        dto.put("userid", userid);

        List<PortalVo> portalVoList = portalService.getPortletList(dto);
        JSONArray jsonArray = JSONArray.fromObject(portalVoList);
        setResultArray(jsonArray);
        return SUCCESS;
    }

    public String add() {

        HttpServletRequest request = ServletActionContext.getRequest();
        SessionContainer sessionContainer = WebUtils.getSessionContainer(request);
        String userid = sessionContainer.getUserInfo().getUserid();

        String menuid = request.getParameter("menuid");
        int sortno = Integer.parseInt(request.getParameter("sortno"));
        Dto dto = new BaseDto();
        dto.put("userid", userid);
        dto.put("menuid", menuid);
        dto.put("sortno", sortno);
        portalService.savePortlet(dto);
        return SUCCESS;
    }

    public String delete() {

        HttpServletRequest request = ServletActionContext.getRequest();
        SessionContainer sessionContainer = WebUtils.getSessionContainer(request);
        String userid = sessionContainer.getUserInfo().getUserid();

        String menuid = request.getParameter("menuid");
        int sortno = Integer.parseInt(request.getParameter("sortno"));
        int column = Integer.parseInt(request.getParameter("column"));
        Dto dto = new BaseDto();
        dto.put("userid", userid);
        dto.put("menuid", menuid);
        dto.put("presortno", sortno);
        dto.put("col", column);
        portalService.deletePortlet(dto);
        return SUCCESS;
    }

    public String resize() {

        HttpServletRequest request = ServletActionContext.getRequest();
        SessionContainer sessionContainer = WebUtils.getSessionContainer(request);
        String userid = sessionContainer.getUserInfo().getUserid();

        String menuid = request.getParameter("menuid");
        String height = request.getParameter("height");
        Dto dto = new BaseDto();
        dto.put("userid", userid);
        dto.put("menuid", menuid);
        dto.put("height", height);
        portalService.updatePortletHeight(dto);
        return SUCCESS;
    }

    public String move() {

        HttpServletRequest request = ServletActionContext.getRequest();
        SessionContainer sessionContainer = WebUtils.getSessionContainer(request);
        String userid = sessionContainer.getUserInfo().getUserid();

        String menuid = request.getParameter("menuid");
        int sortno = Integer.parseInt(request.getParameter("sortno"));
        int presortno = Integer.parseInt(request.getParameter("presortno"));
        int column = Integer.parseInt(request.getParameter("column"));
        Dto dto = new BaseDto();
        dto.put("userid", userid);
        dto.put("menuid", menuid);
        dto.put("sortno", sortno);
        dto.put("presortno", presortno);
        dto.put("col", column);

        portalService.updatePortletMove(dto);
        return SUCCESS;
    }

    public void save() {
        HttpServletRequest request = ServletActionContext.getRequest();
        SessionContainer sessionContainer = WebUtils.getSessionContainer(request);
        String userid = sessionContainer.getUserInfo().getUserid();

        String portal = request.getParameter("portal");
        int column = Integer.parseInt(request.getParameter("column"));

        Dto dto = new BaseDto();
        dto.put("userid", userid);
        dto.put("portal", portal);
        dto.put("col", column);

        portalService.updatePortal(dto);
        sessionContainer.getUserInfo().setPortal(portal);
    }
}
