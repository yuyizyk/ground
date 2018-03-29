package cn.yuyizyk.ground.util.apidoc.http;

public enum ContentType {
	/**
	 * 数据被编码为名称/值对
	 */
	application_x_www_form_urlencoded,
	/**
	 * 数据被编码为一条消息
	 */
	multipart_form_data,
	/**
	 * 数据以纯文本形式(text/json/xml/html)进行编码，其中不含任何控件或格式字符
	 */
	text_plain,
	/**
	 * 二进制流
	 */
	application_octet_stream,

	/**
	 * doc
	 */
	application_msword,
	/**
	 * dwg
	 */
	application_x_dwg,
	/**
	 * html
	 */
	text_html,
	/**
	 * json  application/json
	 */
	application_json, image_jpeg, application_x_jpg, application_x_javascript, audio_mp3, video_mpeg4, application_pdf, application_x_png, application_x_ppt, application_vnd_ms_powerpoint,
	/**
	 * application/vnd.android.package-archive
	 */
	application_vnd_android_package_archive,

}
