package cn.yuyizyk.ground.constant;

import org.springframework.context.ApplicationContext;

public final class AppConfigInfo {
	/**
	 * WEB应用名
	 */
	private final String applicationName;

	private static AppConfigInfo applicationInfo;

	public static final String getApplicationName() {
		return applicationInfo.applicationName;
	}

	private AppConfigInfo(ApplicationContext context) {
		applicationName = context.getApplicationName();
	}

	static final class AppConfigInfoInit {
		public static void init(ApplicationContext context) {
			applicationInfo = new AppConfigInfo(context);
		}
	}
}
