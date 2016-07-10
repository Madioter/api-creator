package com.madioter.api.creater.parser;

import com.madioter.api.creater.model.resource.ValidatorSource;
import java.lang.annotation.Annotation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <Description> <br>
 *
 * @author wangyi8<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2016年07月06日 <br>
 */
public class ValidatorParser implements IParser {

    public static ValidatorSource parse(Annotation annotation) {
        ValidatorSource validatorSource = new ValidatorSource();
        if (annotation instanceof Min) {
            validatorSource.setValidateComment("大于等于" + ((Min) annotation).value());
        } else if (annotation instanceof NotEmpty) {
            validatorSource.setValidateComment("不能为空");
        } else if (annotation instanceof NotNull) {
            validatorSource.setValidateComment("不能为null");
        } else {
            return null;
        }
        return validatorSource;
    }
}
