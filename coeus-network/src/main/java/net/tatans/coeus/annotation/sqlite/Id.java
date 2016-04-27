
package net.tatans.coeus.annotation.sqlite;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @title Id��������
 * @description �����õ�ʱ��Ĭ�������id��_id�ֶ���Ϊ������column�����õ���Ĭ��Ϊ�ֶ���
 * @author michael Young (www.YangFuhai.com)
 * @version 1.0
 * @created 2012-10-31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME) 
public @interface Id {
	 public String column() default "";
}
