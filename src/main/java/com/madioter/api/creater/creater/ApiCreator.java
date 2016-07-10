package com.madioter.api.creater.creater;

import com.madioter.api.creater.common.FreemarkerUtils;
import com.madioter.api.creater.model.resource.ClassResource;
import com.madioter.api.creater.model.resource.PropertySource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月09日 <br>
 */
public class ApiCreator {

    public void create(Class clz) {
        ClassResource classSource = new ClassResource(clz);
        List<ClassResource> classResourceList = new ArrayList<ClassResource>();
        addAllClassResource(classResourceList, classSource);

        classSource.getClz().getSimpleName();
        HashMap map = new HashMap();
        map.put("classResourceList", classResourceList); // 子项目url链接

        // 生成HTML的完整路径
        String htmlFullPath = "D:/templates/" + clz.getSimpleName() + ".html";
        //路径不存在时，创建路径
        File file  = new File("D:/templates/");
        if (!file.exists()) {
            file.mkdirs();
        }
        FreemarkerUtils.createFile("class_table.ftl", map, htmlFullPath);
    }

    private void addAllClassResource(List<ClassResource> classResourceList, ClassResource classSource) {
        classResourceList.add(classSource);
        for (PropertySource propertySource : classSource.getPropertySourceList()) {
            if (propertySource.isObject()) {
                for (ClassResource childClassSource : propertySource.getChildClassResourceList()) {
                    addAllClassResource(classResourceList, childClassSource);
                }
            }
        }
    }
}
