package biz.sagal.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utility
 * @author Mark Sagal <mark@sagal.biz>
 * @since  2018-06-14
 */
public interface StringUtil {
	/**
	 * Get lines from string
	 * @param  value Raw string
	 * @return Array of string
	 */
	public static String[] getLines(final String value) {
		return value.split("\\n");
	}

	/**
	 * Remove spaces from string
	 * @param  searchString Raw string
	 * @return              Returns string without spaces
	 */
	public static String removeSpaces(final String searchString) {
		return searchString.replaceAll("\\s+", "");
	}

	/**
	 * Get string matches
	 * @param  searchString String to search
	 * @param  regex        Regular expression
	 * @return Results in ArrayList<String>
	 */
	public static ArrayList<String> getMatch(final String searchString, final String regex) {
		final Matcher match = Pattern.compile(regex).matcher(searchString);
		final ArrayList<String> strings = new ArrayList<>();
		while (match.find()) {
			strings.add(match.group());
		}
		return strings;
	}
}
