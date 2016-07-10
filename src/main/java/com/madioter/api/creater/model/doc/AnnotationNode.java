package com.madioter.api.creater.model.doc;

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
public class AnnotationNode extends DocNode {

    private List<String> contents = new ArrayList<String>();

    public AnnotationNode(String item) {
        super(item);
        contents.add(item);
    }

    public AnnotationNode(List<String> items) {
        super(items);
        contents.addAll(items);
    }
}
