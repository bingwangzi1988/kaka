package com.common.action;

import com.common.base.BaseAction;
import com.common.util.PorpertiesUtil;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-7-9
 * Time: 下午5:21
 * desc:
 */
public class FileUploadAction extends BaseAction{
    private static final int BUFFER_SIZE = 16 * 1024;
    // 文件标题
    private String title;
    // 上传文件域对象
    private File upload;
    // 上传文件名
    private String uploadFileName;
    // 上传文件类型
    private String uploadContentType;
    // 保存文件的目录路径(通过依赖注入)
    private String savePath;

    private JSON resultJson;

	public JSON getResultJson() {
		return resultJson;
	}

	public void setResultJson(JSON resultJson) {
		this.resultJson = resultJson;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    //自己封装的一个把源文件对象复制成目标文件对象
    private static void copy(File src, File dst) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public String doUpload(){
    	JSONObject jsonObject = new JSONObject();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	Properties props = PorpertiesUtil.getProperties("fileDownload.properties");
    	String fileNm = "";
    	try {
    		fileNm = URLDecoder.decode(request.getParameter("fileNm"),"utf-8");
    		fileNm = fileNm.substring(fileNm.lastIndexOf(File.separator)+1);
    		System.out.println(fileNm);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        String dstPath = props.get("uploadPath") + fileNm;
        File dstFile = new File(dstPath);
        try {
			copy(this.upload, dstFile);
			jsonObject.put("success", "true");
			jsonObject.put("dstPath", dstPath);
		} catch (Exception e) {
			jsonObject.put("success", "false");
			e.printStackTrace();
		}
        
        setResultJson(jsonObject);
        
        return SUCCESS;
    }

}