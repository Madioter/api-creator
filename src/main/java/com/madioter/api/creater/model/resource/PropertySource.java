package com.madioter.api.creater.model.resource;

import com.madioter.api.creater.common.ClassUtil;
import com.madioter.api.creater.common.Constants;
import com.madioter.api.creater.parser.ValidatorParser;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
public class PropertySource implements IResource {

    // 属性名
    private String propertyName;

    // 属性注释
    private String propertyComment;

    // 关联枚举类
    private EnumResource enumResource;

    // 关联子类
    private List<ClassResource> childClassResourceList;

    // 属性类型
    private PropertyType propertyType;

    // 属性反射对象
    private Field propertyField;

    // 验证注解信息
    private List<ValidatorSource> validatorSourceList = new ArrayList<ValidatorSource>();

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setPropertyComment(String propertyComment) {
        this.propertyComment = propertyComment;
    }

    public List<ValidatorSource> getValidatorSourceList() {
        return validatorSourceList;
    }

    public void setEnumClass(Class enumClass) {
        this.enumResource = new EnumResource(enumClass);
    }

    public String getEnumComment() {
        return enumResource.getEnumComment();
    }

    public void setValidatorSourceList(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            ValidatorSource validatorSource = ValidatorParser.parse(annotation);
            if (validatorSource != null) {
                validatorSourceList.add(validatorSource);
            }
        }
    }

    public String getValidatorComment() {
        StringBuilder builder = new StringBuilder();
        if (validatorSourceList != null && !validatorSourceList.isEmpty()) {
            for (ValidatorSource validatorSource : validatorSourceList) {
                if (validatorSource.getValidateComment() != null) {
                    builder.append(validatorSource.getValidateComment()).append(Constants.CN_COMMA);
                }
            }
        }
        if (enumResource != null && !enumResource.isEmpty()) {
            builder.append("枚举").append(Constants.CN_COMMA);
        }
        if (builder.toString().endsWith(Constants.CN_COMMA)) {
            return builder.substring(0, builder.length() - 1);
        } else {
            return builder.toString();
        }
    }

    public String getPropertyComment() {
        if (enumResource == null || enumResource.isEmpty()) {
            return propertyComment == null ? "" : propertyComment;
        } else {
            return propertyComment + Constants.CN_COMMA + enumResource.getEnumComment();
        }
    }

    public List<ClassResource> getChildClassResourceList() {
        return childClassResourceList;
    }

    public void setChildClassResourceList(List<ClassResource> childClassResourceList) {
        this.childClassResourceList = childClassResourceList;
    }

    public void setPropertyType(Field field) {
        Class clz = field.getType();
        this.propertyField = field;
        if (ClassUtil.isSimpleType(clz)) {
            this.propertyType = PropertyType.SIMPLE_TYPE;
        } else if (ClassUtil.isCollection(clz)) {
            this.propertyType = PropertyType.COLLECTION_TYPE;
        } else {
            this.propertyType = PropertyType.RELATION_TYPE;
        }
    }

    public boolean isObject() {
        if (childClassResourceList != null && !childClassResourceList.isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean isArray() {
        if (this.propertyType == PropertyType.COLLECTION_TYPE) {
            return true;
        } else {
            return false;
        }
    }

    public void addChildClassResource(ClassResource classResource) {
        if (childClassResourceList == null) {
            childClassResourceList = new ArrayList<ClassResource>();
        }
        childClassResourceList.add(classResource);
    }

    public enum PropertyType {
        // 简单数据类型，集合数据类型和关联数据类型
        SIMPLE_TYPE, COLLECTION_TYPE, RELATION_TYPE;
    }

    public String getType() {
        return ClassUtil.getTypeName(this);
    }

    public Field getPropertyField() {
        return propertyField;
    }

    public String getRequired() {
        if (validatorSourceList.size() > 0) {
            return "Y";
        } else {
            return "N";
        }
    }
}
