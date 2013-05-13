package org.rapid_framework.generator.util;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

/**
 * Created by IntelliJ IDEA.
 * User: ritchrs
 * Date: 11-6-3
 * Time: ?¦¶1:14
 * desc:
 */
public class StaticMethodHelper {

    public static TemplateHashModel useStaticPackage(String packageName) {
        try {
            BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
            TemplateHashModel staticModels = wrapper.getStaticModels();
            TemplateHashModel fileStatics = (TemplateHashModel) staticModels.get(packageName);
            return fileStatics;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
