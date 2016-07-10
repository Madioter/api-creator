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
public class SentenceNode extends DocNode {

    private List<String> sentences = new ArrayList<String>();

    public SentenceNode(List<String> sentences) {
        super(sentences);
        this.sentences.addAll(sentences);
    }

    public boolean contains(String fieldName) {
        for(String sentence: sentences) {
            if (sentence.contains(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public SentenceNode(String sentence) {
        super(sentence);
        sentences = new ArrayList<String>();
        sentences.add(sentence);
    }
}
