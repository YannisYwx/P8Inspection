package com.p8.inspection.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/22  17:08
 * @email : 923080261@qq.com
 * @description :
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
