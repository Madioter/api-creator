package com.madioter.api.creater.model.doc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月06日 <br>
 */
public class JavaDocTree {

    private List<DocNode> docNodeList;

    public JavaDocTree(List<String> items) {
        docNodeList = new ArrayList<DocNode>();
        DocNodeFactory factory = new DocNodeFactory();
        for (String item : items) {
            DocNode node = factory.getDocNode(item);
            if (node == null) {
                continue;
            }
            docNodeList.add(node);
        }
    }

    public List<DocNode> getNode(String fieldName) {
        LinkedList<DocNode> docNodes = new LinkedList<DocNode>();
        int index = 0;
        for (int i = 0; i < docNodeList.size(); i++) {
            if (docNodeList.get(i) instanceof SentenceNode && ((SentenceNode) docNodeList.get(i)).contains(fieldName)) {
                docNodes.addFirst(docNodeList.get(i));
                index = i;
                break;
            } else if (docNodeList.get(i) instanceof ClassBlockNode) {
                List<DocNode> nodes = ((ClassBlockNode) docNodeList.get(i)).getNode(fieldName);
                if (nodes != null) {
                    return nodes;
                }
            }
        }
        for (int i = index - 1; i >= 0; i--) {
            if (docNodeList.get(i) instanceof AnnotationNode || docNodeList.get(i) instanceof CommentNode) {
                docNodes.addFirst(docNodeList.get(i));
            } else {
                break;
            }
        }
        return docNodes;
    }
}
