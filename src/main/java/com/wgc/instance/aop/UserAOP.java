package com.wgc.instance.aop;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Component
/*
* ElementType.TYPE: 能使用在类上
* METHOD能使用在方法上
* PARAMETER能使用在参数上
* */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
/*
* RUNTIME注解将会被编译到 class 文件中，并能在运行时通过反射获取
* */
@Retention(RetentionPolicy.RUNTIME)
/*
     @Documented 表示是否允许 javadoc 或相关工具为这个注解生成文档
*/
@Documented
public @interface UserAOP {

}
