package cn.yuyizyk.ground.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.util.PatternMatcher;

/**
 * {@code PatternMatcher} implementation that uses standard
 * {@link java.util.regex} objects.
 * <p/>
 * class is org.apache.shiro <a href="http://shiro.apache.org/">shiro</a>
 * 
 * @see Pattern
 * @since 1.0
 */
public class RegExPatternMatcher implements PatternMatcher {

	/**
	 * Simple implementation that merely uses the default pattern comparison logic
	 * provided by the JDK.
	 * <p/>
	 * This implementation essentially executes the following:
	 * 
	 * <pre>
	 * Pattern p = Pattern.compile(pattern);
	 * Matcher m = p.matcher(source);
	 * return m.matches();
	 * </pre>
	 * 
	 * @param pattern
	 *            the pattern to match against
	 * @param source
	 *            the source to match
	 * @return {@code true} if the source matches the required pattern,
	 *         {@code false} otherwise.
	 */
	public boolean matches(String pattern, String source) {
		if (pattern == null) {
			throw new IllegalArgumentException("pattern argument cannot be null.");
		}
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.matches();
	}
}
