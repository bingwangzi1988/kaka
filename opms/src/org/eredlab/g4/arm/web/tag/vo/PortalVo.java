package org.eredlab.g4.arm.web.tag.vo;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-5-19
 * Time: ÏÂÎç12:17
 * desc:
 */
public class PortalVo {
    private String portletid;
    private String userid;
    private String menuid;
    private String title;
    private String iconcls;
    private String url;
    private Integer height;
    private Integer sortno;

    public String getPortletid() {
        return portletid;
    }

    public void setPortletid(String portletid) {
        this.portletid = portletid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconcls() {
        return iconcls;
    }

    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
