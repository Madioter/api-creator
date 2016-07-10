package com.madioter.api.creater.model.doc;

import com.madioter.api.creater.common.ClassContents;
import com.madioter.api.creater.common.Constants;
import com.madioter.api.creater.common.StringUtil;
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
public class DocNodeFactory {

    private List<String> tempContent = new ArrayList<String>();

    private DocType type = null;

    int countBlanket = 0;

    public DocNode getDocNode(String item) {
        item = item.trim();
        if (type == DocType.COMMENT) {
            tempContent.add(item);
            if (item.endsWith(Constants.END_ASTERISK)) {
                DocNode node = new CommentNode(tempContent);
                clear();
                return node;
            }
        } else if (type == DocType.BLOCK) {
            int leftBlanketCount = StringUtil.leftBlanketCount(item);
            int rightBlanketCount = StringUtil.rightBlanketCount(item);
            countBlanket = countBlanket + leftBlanketCount - rightBlanketCount;
            tempContent.add(item);
            if (countBlanket == 0) {
                DocNode node = getBlockNode(tempContent);
                clear();
                return node;
            }
        } else if (type == DocType.ANNOTATION) {
            int leftCurves = StringUtil.leftCurvesCount(item);
            int rightCurves = StringUtil.rightCurvesCount(item);
            countBlanket = countBlanket + leftCurves - rightCurves;
            tempContent.add(item);
            if (countBlanket == 0) {
                DocNode node = new AnnotationNode(tempContent);
                clear();
                return node;
            }
        } else if (type == DocType.SENTENCE) {
            tempContent.add(item);
            if (item.endsWith(Constants.SEMICOLON)) {
                DocNode node = new SentenceNode(item);
                return node;
            }
        } else if (type == null){
            if (item.startsWith(Constants.START_ASTERISK) && item.endsWith(Constants.END_ASTERISK)
                    || type == null && item.startsWith(Constants.DOUBLE_SLASH)) {
                DocNode node = new CommentNode(item);
                clear();
                return node;
            } else if (item.startsWith(Constants.START_ASTERISK)) {
                tempContent.add(item);
                type = DocType.COMMENT;
            } else if (item.startsWith(Constants.AT_TAG)) {
                int leftCurves = StringUtil.leftCurvesCount(item);
                int rightCurves = StringUtil.rightCurvesCount(item);
                countBlanket = countBlanket + leftCurves - rightCurves;
                tempContent.add(item);
                if (countBlanket == 0) {
                    type = DocType.ANNOTATION;
                    DocNode node = new AnnotationNode(tempContent);
                    clear();
                    return node;
                }
            } else {
                int leftBlanketCount = StringUtil.leftBlanketCount(item);
                int rightBlanketCount = StringUtil.rightBlanketCount(item);
                countBlanket = leftBlanketCount - rightBlanketCount;
                if (countBlanket > 0) {
                    tempContent.add(item);
                    type = DocType.BLOCK;
                } else if (leftBlanketCount > 0 && countBlanket == 0) {
                    tempContent.add(item);
                    DocNode node = getBlockNode(tempContent);
                    clear();
                    return node;
                } else if (leftBlanketCount == 0){
                    if (item.endsWith(Constants.SEMICOLON)) {
                        DocNode node = new SentenceNode(item);
                        return node;
                    } else {
                        type = DocType.SENTENCE;
                        tempContent.add(item);
                    }
                }
            }
        }
        return null;
    }

    private BlockNode getBlockNode(List<String> tempContent) {
        StringBuilder builder = new StringBuilder();
        for (String item : tempContent) {
            builder.append(item);
            if (builder.indexOf(Constants.LEFT_BLANKET) != -1) {
                if (StringUtil.containsTag(builder.toString(), ClassContents.CLASS_TAG)) {
                    return new ClassBlockNode(tempContent);
                } else {
                    return new BlockNode(tempContent);
                }
            }
        }
        return null;
    }

    private void clear(){
        type = null;
        tempContent.clear();
        countBlanket = 0;
    }

    enum DocType {
        COMMENT, SENTENCE, BLOCK, ANNOTATION;
    }
}
