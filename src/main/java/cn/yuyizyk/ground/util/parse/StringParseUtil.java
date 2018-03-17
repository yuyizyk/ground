package cn.yuyizyk.ground.util.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringParseUtil {
	/**
	 * 日期型正则文本格式（YYYY-MM-DD）
	 */
	public static final String DATE_FORMAT_REGULARITY = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

	/**
	 * 时间型正则文本格式（YYYY-MM-DD HH:MM:SS）
	 */
	public static final String TIME_FORMAT_REGULARITY = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8])))))) ((0[0-9])|(1[0-9])|(2[0-3])):[0-5][0-9]:[0-5][0-9]{1}";

	/**
	 * 
	 * @Description: 替换空格 ，半角 、全角
	 */
	public static String replaceBlank(String str) {
		if (str == null)
			return str;
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		str = m.replaceAll("");
		str = StringUtils.remove(str, "　");
		return str;
	}

	/**
	 * 判断是否为日期型数据
	 * 
	 * @Description: 判断是否为日期型数据(YYYY-MM-DD)
	 */
	public static boolean isDate(String date) {
		return date.matches(DATE_FORMAT_REGULARITY);
	}

	/**
	 * 判断是否为时间型数据
	 * 
	 * @Description: 判断是否为时间型数据(YYYY-MM-DD HH:MM:SS)
	 */
	public static boolean isTime(String dateString) {
		return dateString.matches(TIME_FORMAT_REGULARITY);
	}

	/**
	 * 根据字符的Ascii来获得具体的长度
	 * 
	 * @param s
	 * @return
	 */
	public static int getAsciiLength(String str) {
		int length = 0;
		for (int i = 0; i < str.length(); i++) {
			int ascii = Character.codePointAt(str, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	/**
	 * 获得指定长度的省略字符串
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getOmitStr(String str, int length) {
		if (length >= str.length() * 2) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		int len = 0;
		for (int i = 0; i < str.length(); i++) {
			int ascii = Character.codePointAt(str, i);
			if (ascii >= 0 && ascii <= 255) {
				if (len >= length - 3) {
					break;
				}
				len++;
			} else {
				if (len >= length - 4) {
					break;
				}
				len += 2;
			}
			sb.append(str.charAt(i));
		}
		if (length >= len + 3) {
			sb.append("...");
		}
		return sb.toString();
	}

	/**
	 * 获得字符串编码
	 * 
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GB2312
				String s = encode;
				return s; // 是的话，返回“GB2312“，以下代码同理
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是UTF-8
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GBK
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return ""; // 如果都不是，说明输入的内容不属于常见的编码格式。
	}

}
