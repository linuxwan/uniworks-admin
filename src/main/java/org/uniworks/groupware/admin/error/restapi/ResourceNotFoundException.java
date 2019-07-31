/**
 * 박충완(Park Chungwan)이 작성한 코드 입니다.
 * Uniworks라는 개인적 프로젝트를 완성하기 위해서 작성 중 입니다.
 * 이 소스의 코드를 사용하실 경우에는 꼭 출처를 명시해 주시기 바랍니다.
 */
package org.uniworks.groupware.admin.error.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Park Chung Wan
 *
 */
public class ResourceNotFoundException extends RuntimeException {
	private static final Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);
	
	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public ResourceNotFoundException(final String message) {
		super(message);
	}
	
	public ResourceNotFoundException(final Throwable cause) {
		super(cause);
	}
}
