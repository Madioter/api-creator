package com.madioter.api.creater.parser;

import com.madioter.api.creater.common.ClassUtil;
import com.madioter.api.creater.model.doc.AnnotationNode;
import com.madioter.api.creater.model.doc.CommentNode;
import com.madioter.api.creater.model.doc.DocNode;
import com.madioter.api.creater.model.resource.ClassResource;
import com.madioter.api.creater.model.resource.PropertySource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月06日 <br>
 */
public class PropertyParser implements IParser {

    public static PropertySource parse(List<DocNode> contents, Field field) {
        PropertySource propertySource = new PropertySource();
        propertySource.setPropertyName(field.getName());
        propertySource.setPropertyType(field);
        if (!ClassUtil.isSimpleType(field.getType())) {
            Class genericType = null;
            if (ClassUtil.isCollection(field.getType())) {
                if (!ClassUtil.isSimpleType(ClassUtil.getGenericType(field))) {
                    genericType = ClassUtil.getGenericType(field);
                }
            } else {
                genericType = field.getType();
            }
            if (genericType != null) {
                if (ClassUtil.isInterface(genericType)) {
                    List<Class> realizeClassList = ClassUtil.getRealizeClassList(genericType);
                    for (Class clz : realizeClassList) {
                        propertySource.addChildClassResource(new ClassResource(clz, field.getName()));
                    }
                } else {
                    propertySource.addChildClassResource(new ClassResource(genericType, field.getName()));
                }
            }
        }
        boolean havaAnnotations = false;
        for (DocNode docNode : contents) {
            if (docNode instanceof CommentNode) {
                propertySource.setPropertyComment(((CommentNode) docNode).getComment());
                propertySource.setEnumClass(((CommentNode) docNode).containsEnum());
            } else if (docNode instanceof AnnotationNode) {
                havaAnnotations = true;
            }
        }
        if (havaAnnotations) {
            Annotation[] annotations = field.getAnnotations();
            propertySource.setValidatorSourceList(annotations);
        }
        return propertySource;
    }
}
