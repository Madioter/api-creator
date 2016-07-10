package com.madioter.api.creater.parser;

import com.madioter.api.creater.common.StringUtil;
import com.madioter.api.creater.model.doc.DocNode;
import com.madioter.api.creater.model.doc.JavaDocTree;
import com.madioter.api.creater.model.resource.ClassResource;
import com.madioter.api.creater.model.resource.PropertySource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class ClassParser implements IParser {

    public static void parse(ClassResource resource) {
        File clzResource = resource.getResource();
        BufferedReader reader = null;
        List<String> fileContent = new ArrayList<String>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(clzResource),"UTF-8"));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                if (!StringUtil.isBlank(tempString.trim())) {
                    fileContent.add(tempString);
                }
            }
            resource.setFileContent(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void getProperties(ClassResource resource) {
        Class clz = resource.getClz();
        Field[] fields = clz.getDeclaredFields();
        JavaDocTree docTree = new JavaDocTree(resource.getFileContent());
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();

            List<DocNode> contents = docTree.getNode(fieldName);
            PropertySource propertySource = PropertyParser.parse(contents, field);
            resource.addProperty(propertySource);
        }
    }
}
