package com.madioter.api.creater.common;

import com.madioter.api.creater.model.resource.PropertySource;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月09日 <br>
 */
public class ClassUtil {

    private static Class[] SIMPLE_CLASS_TYPE = new Class[]{
            int.class, short.class, long.class, float.class, double.class, byte.class, boolean.class, char.class,
            Integer.class, Long.class, Short.class, Float.class, Double.class, String.class, Byte.class, Boolean.class,
            BigDecimal.class, Date.class, java.sql.Date.class
    };

    private static Map<String, List<Class>> API_DOC_TYPE_NAME;

    static {
        API_DOC_TYPE_NAME = new HashMap<String, List<Class>>();
        API_DOC_TYPE_NAME.put("int", Arrays.asList(new Class[]{int.class, short.class, long.class, Integer.class, Long.class, Short.class}));
        API_DOC_TYPE_NAME.put("decimal", Arrays.asList(new Class[]{float.class, double.class, Float.class, Double.class, BigDecimal.class}));
        API_DOC_TYPE_NAME.put("string", Arrays.asList(new Class[]{char.class, byte.class, Byte.class, String.class}));
        API_DOC_TYPE_NAME.put("bool", Arrays.asList(new Class[]{Boolean.class, boolean.class}));
        API_DOC_TYPE_NAME.put("date", Arrays.asList(new Class[]{Date.class, java.sql.Date.class}));
    }

    public static boolean isSimpleType(Class clz) {
        for (Class simpleClz : SIMPLE_CLASS_TYPE) {
            if (simpleClz == clz) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCollection(Class clz) {
        return clz.isArray() || clz == List.class || clz == Set.class;
    }

    public static Class getGenericType(Field field) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            return getActualType((ParameterizedType) genericType);
        } else if (genericType instanceof Class) {
            if (((Class) genericType).isArray()) {
                return ((Class) genericType).getComponentType();
            }
        }
        return null;
    }

    private static Class getActualType(ParameterizedType genericType) {
        Type[] types = genericType.getActualTypeArguments();
        if (types.length > 0) {
            if (types[0] instanceof Class) {
                return (Class) types[0];
            } else if (types[0] instanceof ParameterizedType) {
                Type innerType = ((ParameterizedType) types[0]).getRawType();
                if (innerType instanceof Class) {
                    return (Class) innerType;
                } else {
                    System.out.println("无法解析的对象类型：" + genericType.toString());
                }
            } else {
                System.out.println("无法解析的对象类型：" + genericType.toString());
            }
        }
        return null;
    }

    public static boolean isInterface(Class clz) {
        return clz.isInterface();
    }

    public static List<Class> getRealizeClassList(Class clz) {
        try {
            List returnClassList = new ArrayList<Class>();
            //判断是不是接口,不是接口不作处理
            if (clz.isInterface()) {
                String packageName = clz.getPackage().getName();  //获得当前包名
                List<Class> allClass = getClasses(packageName); //获得当前包以及子包下的所有类

                //判断是否是一个接口
                for (int i = 0; i < allClass.size(); i++) {
                    if (clz.isAssignableFrom(allClass.get(i))) {
                        if (!clz.equals(allClass.get(i))) {
                            returnClassList.add(allClass.get(i));
                        }
                    }
                }
            }
            return returnClassList;
        } catch (Exception e) {
            return new ArrayList<Class>();
        }
    }

    /**
     *
     * @Description: 根据包名获得该包以及子包下的所有类不查找jar包中的
     * @param packageName 包名
     * @return List<Class>    包下所有类
     * @throws ClassNotFoundException 异常
     * @throws IOException 异常
     */
    private static List<Class> getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace(".", "/");
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClass(directory, packageName));
        }
        return classes;
    }

    /**
     * 获取文件路径下的所有符合条件的类
     * @param directory 文件路径
     * @param packageName 报名
     * @return List<Class>
     * @throws ClassNotFoundException 异常
     */
    private static List<Class> findClass(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (directory == null || !directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClass(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + "." + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    /**
     * api文档专用类型名称
     * @param propertySource
     * @return
     */
    public static String getTypeName(PropertySource propertySource) {
        // 默认普通数据类型： int，decimal，date，string
        Class fieldType = propertySource.getPropertyField().getType();
        if (isSimpleType(fieldType)) {
            for (String key : API_DOC_TYPE_NAME.keySet()) {
                List<Class> classList = API_DOC_TYPE_NAME.get(key);
                if (classList.contains(fieldType)) {
                    return key;
                }
            }
        }
        // 数组类型 array[类名]
        else if (propertySource.isArray()) {
            Class clz = getGenericType(propertySource.getPropertyField());
            return "array[" + clz.getSimpleName() + "]";
        }
        // 对象 object[类名]
        else if (propertySource.isObject()) {
            return "object[" + fieldType.getSimpleName() + "]";
        }
        return null;
    }
}
