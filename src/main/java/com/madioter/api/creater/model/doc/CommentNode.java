package com.madioter.api.creater.model.doc;

import com.madioter.api.creater.common.Constants;
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
public class CommentNode extends DocNode {

    private List<String> comments = new ArrayList<String>();

    public CommentNode(String str) {
        super(str);
        comments.add(str);
    }

    public CommentNode(List<String> strs) {
        super(strs);
        comments.addAll(strs);
    }

    public Class containsEnum() {
        for (String comment : comments) {
            comment = comment.replace(Constants.START_ASTERISK, "");
            comment = comment.replace(Constants.END_ASTERISK, "");
            comment = comment.replace("*", "");
            comment = comment.replace(Constants.DOUBLE_SLASH, "");
            if (comment.contains("@see")) {
                comment = comment.replace("@see", "");
                comment = comment.replace(" ", "").trim();
                try {
                    Class clz = Class.forName(comment);
                    return clz;
                } catch (ClassNotFoundException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public String getComment() {
        StringBuilder builder = new StringBuilder();
        for (String comment : comments) {
            comment = comment.replace(Constants.START_ASTERISK, "");
            comment = comment.replace(Constants.END_ASTERISK, "");
            comment = comment.replace("*", "");
            comment = comment.replace(Constants.DOUBLE_SLASH, "");
            if (comment.contains("@see")) {
                continue;
            }
            builder.append(comment.trim());
        }
        return builder.toString();
    }
}
