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
	 * Formats Array of String
	 * @param  strings Raw string
	 * @return Formatted Array of Strings
	 */
	public static String[] formatStringArray(final String[] strings) {
		final int stringsLen = strings.length;
		final String[] formattedStrings = new String[stringsLen];
		for (int i = 0; i < stringsLen; i++) {
			final String[] stringSpaces = strings[i].replaceAll("\\t", " ").split("\\s+");
			final int stringSpacesLen = stringSpaces.length;
			final String[] trimedSpaces = new String[stringSpacesLen];
			for (int ii = 0; ii < stringSpacesLen; ii++) {
				trimedSpaces[ii] = stringSpaces[ii].trim();
			}
			formattedStrings[i] = String.join(" ", trimedSpaces);
		}
		return formattedStrings;
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
