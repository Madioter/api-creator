package com.madioter.api.creater.model.doc;

import com.madioter.api.creater.common.ClassContents;
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
public class BlockNode extends DocNode {

    private List<String> sentences = new ArrayList<String>();

    public BlockNode(List<String> sentences) {
        super(sentences);
        this.sentences.addAll(sentences);
    }

    public BlockNode(String sentence) {
        super(sentence);
        sentences.add(sentence);
    }

    public List<DocNode> getNode(String fieldName) {
        return null;
    }
}
