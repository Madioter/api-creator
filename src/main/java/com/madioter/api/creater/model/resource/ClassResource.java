package com.madioter.api.creater.model.resource;

import com.madioter.api.creater.common.BaseFileConfig;
import com.madioter.api.creater.parser.ClassParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月06日 <br>
 */
public class ClassResource implements IResource {

    public ClassResource (Class clz) {
        // TODO 类循环嵌套应用问题需要处理
        this.clz = clz;
        this.resource = BaseFileConfig.getClassFile(clz);
        ClassParser.parse(this);
        ClassParser.getProperties(this);
    }

    public ClassResource (Class clz, String name) {
        // TODO 类循环嵌套应用问题需要处理
        this.clz = clz;
        this.resource = BaseFileConfig.getClassFile(clz);
        this.name = name;
        ClassParser.parse(this);
        ClassParser.getProperties(this);
    }

    private Class clz;

    private File resource;

    private List<PropertySource> propertySourceList = new ArrayList<PropertySource>();

    private List<String> fileContent;

    private String name = "参数说明";

    public List<PropertySource> getPropertySourceList() {
        return propertySourceList;
    }

    public void setPropertySourceList(List<PropertySource> propertySourceList) {
        this.propertySourceList = propertySourceList;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }

    public File getResource() {
        return resource;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }

    public List<String> getFileContent() {
        return fileContent;
    }

    public void setFileContent(List<String> fileContent) {
        this.fileContent = fileContent;
    }

    public void addProperty(PropertySource propertySource) {
        propertySourceList.add(propertySource);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return this.clz.getName();
    }
}
