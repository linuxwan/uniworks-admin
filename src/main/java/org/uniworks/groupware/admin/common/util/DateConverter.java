/**
 * 박충완이 작성한 소스입니다.
 * 이 소스는 모든 개발자들이 자유롭게 수정/배포할 수 있습니다.
 * 단, 이 소스를 근간으로 애플리케이션을 개발하실 때에는 꼭 출처를 명시해 주세요.
 */
package org.uniworks.groupware.admin.common.util;

import org.apache.commons.beanutils.Converter;

/**
 * @author Chungwan Park
 * DateConverter.java 2014. 6. 28.
 */
public class DateConverter implements Converter {

	@Override
	public Object convert(Class type, Object value) {
		// TODO Auto-generated method stub
		boolean chkFlag = false;
		DateUtil dt = new DateUtil();
		if (value == null) {
			return null;
		} else {
			if (value instanceof String) {				
				chkFlag = dt.parseDate((String)value);
			}
		}
		return chkFlag ? dt.getDate() : null;
	}

}
