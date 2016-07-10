package com.madioter.api.creater.creater;


/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月06日 <br>
 */
public class Main {

    public static void main(String[] args) {
        Class clz = java.lang.Integer.class;
        ApiCreator creater = new ApiCreator();
        creater.create(clz);
    }
}
