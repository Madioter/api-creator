package com.madioter.api.creater.model.resource;

import com.madioter.api.creater.common.Constants;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月09日 <br>
 */
public class EnumResource {

    private Class enumClass;

    public EnumResource(Class enumClass) {
        this.enumClass = enumClass;
    }

    public boolean isEmpty() {
        if (enumClass != null) {
            return false;
        } else {
            return true;
        }
    }

    public String getEnumComment() {
        if (enumClass != null && enumClass.isEnum()) {
            Field[] fields = enumClass.getDeclaredFields();
            List<?> list = Arrays.asList(enumClass.getEnumConstants());
            EnumStructure enumStructure = new EnumStructure();
            for(Field field : fields){
                field.setAccessible(true);
                if (field.getGenericType() == enumClass) {
                    continue;
                }
                enumStructure.setField(field);
            }
            StringBuilder builder = new StringBuilder();
            for (Object enu : list) {
                String item = enumStructure.readEnum(enu);
                if (item != null) {
                    builder.append(item).append(Constants.CN_COMMA);
                }
            }
            return builder.toString();
        }
        return "";
    }

    private class EnumStructure {
        private Field id;
        private Field name;


        public void setId(Field id) {
            this.id = id;
        }

        public void setName(Field name) {
            this.name = name;
        }

        public void setField(Field field) {
            if (field.getType() == int.class || field.getType() == Integer.class) {
                this.id = field;
            }
            if (field.getType() == String.class) {
                this.name = field;
            }
        }

        public String readEnum(Object obj) {
            int key = -999;
            String value = null;
            try {
                key = this.id.getInt(obj);
                value = this.name.get(obj).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (key != -999 && value != null) {
                return key + "=" + value;
            } else {
                return null;
            }
        }
    }
}
