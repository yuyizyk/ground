package cn.yuyizyk.ground.core.bean;

/**
 * 系统内部信息
 * @author yuyi
 *
 */
public class ApplicationInfo {

	private static final ApplicationInfo applicationInfo = new ApplicationInfo();

	private ApplicationInfo() {
	}

	public ApplicationInfo newInstance() {
		return applicationInfo;
	}

	static {
		applicationInfo.init();
	}

	/**
	 * 初始化
	 */
	private void init() {
	}
}
