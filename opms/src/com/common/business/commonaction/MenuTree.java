package com.common.business.commonaction;

import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.eredlab.g4.arm.service.ArmTagSupportService;
import org.eredlab.g4.arm.util.ArmConstants;
import org.eredlab.g4.arm.web.tag.vo.MenuVo;
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
 * Date: 11-4-14
 * Time: ÏÂÎç4:49
 * desc:
 */
public class MenuTree extends ActionSupport {

    ArmTagSupportService armTagSupportService = (ArmTagSupportService) SpringBeanLoader.getSpringBean("armTagSupportService");

    private JSONArray resultArray;

    public JSONArray getResultArray() {
        return resultArray;
    }

    public void setResultArray(JSONArray resultArray) {
        this.resultArray = resultArray;
    }

    private JSONArray CreatTreeNode(List list, String parentId) {

        JSONArray treeNodeArray = new JSONArray();
        for (Object o : list) {
            MenuVo menuVo = (MenuVo) o;
            String pId = menuVo.getParentid();
            if (pId.equals(parentId)) {
                JSONObject treeObject = new JSONObject();
                String nodeId = menuVo.getMenuid();
                String nodeText = menuVo.getMenuname();
                treeObject.put("id", nodeId);

                JSONArray jsonArray = CreatTreeNode(list, nodeId);
                if (!jsonArray.isEmpty()) {
                    treeObject.element("text", nodeText);
                    treeObject.element("children", jsonArray);
                    if (menuVo.getIconcls() != null)
                        treeObject.element("iconCls", menuVo.getIconcls());
                } else {
                    treeObject.element("text", nodeText);
                    treeObject.element("leaf", true);
                    treeObject.element("url", menuVo.getRequest());
                    if (menuVo.getIconcls() != null)
                        treeObject.element("iconCls", menuVo.getIconcls());
                    if (menuVo.getIcon() != null)
                        treeObject.element("tabicon", menuVo.getIcon());


                }
                treeNodeArray.add(treeObject);
            }
        }

        return treeNodeArray;
    }

    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        SessionContainer sessionContainer = WebUtils.getSessionContainer(request);
        String userid = sessionContainer.getUserInfo().getUserid();

        Dto dto = new BaseDto();
        String account = sessionContainer.getUserInfo().getAccount();
        account = account == null ? "" : account;
        String accountType = ArmConstants.ACCOUNTTYPE_NORMAL;
        if (account.equalsIgnoreCase(WebUtils.getParamValue("DEFAULT_ADMIN_ACCOUNT", request))) {
            accountType = ArmConstants.ACCOUNTTYPE_SUPER;
        } else if (account.equalsIgnoreCase(WebUtils.getParamValue("DEFAULT_DEVELOP_ACCOUNT", request))) {
            accountType = ArmConstants.ACCOUNTTYPE_DEVELOPER;
        }

        dto.put("userid", userid);
        dto.put("accountType", accountType);

        List cardList = armTagSupportService.getCardList(dto).getDefaultAList();

        Dto deptDto = new BaseDto();
        deptDto.put("deptid", sessionContainer.getUserInfo().getDeptid());
        dto.put("deptname", armTagSupportService.getDepartmentInfo(deptDto).getAsString("deptname"));
        dto.put("username", sessionContainer.getUserInfo().getUsername());

        Dto qDto = new BaseDto();
        qDto.put("userid", userid);

        JSONArray panelArray = new JSONArray();

        for (Object o : cardList) {
            MenuVo cardVo = (MenuVo) o;
            qDto.put("menuid", cardVo.getMenuid());
            qDto.put("accountType", accountType);

            JSONObject treeObject = new JSONObject();
            treeObject.element("id", cardVo.getMenuid());
            treeObject.element("text", cardVo.getMenuname());
            if (cardVo.getIconcls() != null)
                treeObject.element("iconCls", cardVo.getIconcls());

            List menuList = armTagSupportService.getCardTreeList(qDto).getDefaultAList();
            JSONArray jsonArray = CreatTreeNode(menuList, cardVo.getMenuid());

            treeObject.element("children", jsonArray);
            panelArray.element(treeObject);
        }
        setResultArray(panelArray);

        return SUCCESS;
    }

}
