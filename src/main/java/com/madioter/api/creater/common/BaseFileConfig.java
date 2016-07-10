package com.madioter.api.creater.common;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月06日 <br>
 */
public class BaseFileConfig {

    private static Set<File> BASE_FILES = new HashSet<File>();

    private static final String[] BASE_PATHS = new String[]{
            "D:\\project\\test_79\\ako\\src\\main\\java",
            "D:\\project\\test_79\\supply-nested-api\\src\\main\\java",
            "D:\\project\\test_79\\cfm\\confirmation-api\\src\\main\\java",
            "D:\\project\\test_79\\cfm\\confirmation-main\\src\\main\\java",
            "D:\\project\\test_79\\ord-nested-api\\src\\main\\java",
            "D:\\project\\test_79\\scm\\src\\main\\java"
    };

    static {
        for (String path : BASE_PATHS) {
            addBasePath(path);
        }
    }

    public static synchronized boolean addBasePath(String path) {
        File baseFile = new File(path);
        if (baseFile.exists()) {
            BASE_FILES.add(baseFile);
            return true;
        }
        return false;
    }

    public static File getClassFile(Class clz) {
        String className = clz.getName();
        String classPath = className.replace(Constants.POINT, Constants.BACK_SLASH) + ".java";
        for (File file : BASE_FILES) {
            File clzFile = new File(file, classPath);
            if (clzFile.exists()) {
                return clzFile;
            }
        }
        return null;
    }
}
