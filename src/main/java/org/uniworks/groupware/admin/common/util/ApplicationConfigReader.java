/**
 * 박충완이 작성한 소스입니다.
 * 이 소스는 모든 개발자들이 자유롭게 수정/배포할 수 있습니다.
 * 단, 이소스를 근간으로 애플리케이션을 개발하실 때에는 꼭 출처를 명시해 주세요.
 */
package org.uniworks.groupware.admin.common.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Chungwan Park
 * ApplicationConfigReader.java 2011. 4. 13.
 */
public class ApplicationConfigReader {
	static Properties props;

	static {
		props = new Properties();

		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}

	public static int getInt(String key) {
		return Integer.parseInt(props.getProperty(key));
	}

	public static boolean getBoolen(String key) {
		return Boolean.valueOf(props.getProperty(key)).booleanValue();
	}
}
