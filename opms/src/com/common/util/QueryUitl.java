package com.common.util;

import cn.org.rapid_framework.page.PageRequest;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-23
 * Time: 上午10:43
 * desc:
 */
public class QueryUitl {
    public static final int MAX_PAGE_SIZE = 1000;
    static BeanUtilsBean beanUtils = new BeanUtilsBean();

    static {
        //用于注册日期类型的转换
        String[] datePatterns = new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "HH:mm:ss"};
        ConvertRegisterHelper.registerConverters(beanUtils.getConvertUtils(), datePatterns);

        System.out.println("QueryUitl.MAX_PAGE_SIZE=" + MAX_PAGE_SIZE);
    }

    public static <T> T newQuery(Class<T> queryClazz) {
        Object query = bindQueryVo(org.springframework.beans.BeanUtils.instantiateClass(queryClazz), ServletActionContext.getRequest());
        return (T) query;
    }

    public static Object bindQueryVo(Object vo, HttpServletRequest request) {
        try {
            Map params = WebUtils.getParametersStartingWith(request, "");
            if(params!=null && params.size()>0)
            {
            	 for(Object key:params.keySet())
                 {
                 	if(null!=params.get(key) && params.get(key) instanceof String) {
                 		String val=(String) params.get(key);
                 		params.put(key, val.trim());
                 	}
                 }
            }
            beanUtils.copyProperties(vo, params);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("beanUtils.copyProperties() error", e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("beanUtils.copyProperties() error", e.getTargetException());
        }
        return vo;
    }
}
