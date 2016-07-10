package com.madioter.api.creater.model.doc;

import com.madioter.api.creater.common.Constants;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016Äê07ÔÂ07ÈÕ <br>
 */
public class ClassBlockNode extends BlockNode {

    private JavaDocTree javaDocTree;

    public ClassBlockNode(List<String> sentences) {
        super(sentences);
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < sentences.size(); i++) {
            if (sentences.get(i).contains(Constants.LEFT_BLANKET)) {
                if (startIndex  == 0) {
                    startIndex = i + 1;
                }
            }
            if (sentences.get(sentences.size() - i - 1).contains(Constants.RIGHT_BLANKET)) {
                if (endIndex == 0) {
                    endIndex = sentences.size() - i - 1;
                }
            }
            if (startIndex != 0 && endIndex != 0) {
                break;
            }
        }
        javaDocTree = new JavaDocTree(sentences.subList(startIndex, endIndex));
    }

    public ClassBlockNode(String sentence) {
        super(sentence);
    }

    @Override
    public List<DocNode> getNode(String fieldName) {
        return javaDocTree.getNode(fieldName);
    }
}
