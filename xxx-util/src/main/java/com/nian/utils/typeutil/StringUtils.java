package com.nian.utils.typeutil;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
	private static final String EMPTY_STRING = "";
	private static final String NULL_STRING = "null";
	private static final String ARRAY_START = "{";
	private static final String ARRAY_END = "}";
	private static final String EMPTY_ARRAY = "{}";
	private static final String ARRAY_ELEMENT_SEPARATOR = ", ";
	public static final int INDEX_NOT_FOUND = -1;
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (isEmpty(arr)) {
			return "";
		}
		if (arr.length == 1) {
			return nullSafeToString(arr[0]);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	public static boolean isEmpty(Object[] array) {
		return (array == null) || (array.length == 0);
	}

	public static boolean isEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}

	public static String nullSafeToString(Object obj) {
		if (obj == null) {
			return "null";
		}
		if ((obj instanceof String)) {
			return (String) obj;
		}
		if ((obj instanceof Object[])) {
			return nullSafeToString((Object[]) obj);
		}
		if ((obj instanceof boolean[])) {
			return nullSafeToString((boolean[]) obj);
		}
		if ((obj instanceof byte[])) {
			return nullSafeToString((byte[]) obj);
		}
		if ((obj instanceof char[])) {
			return nullSafeToString((char[]) obj);
		}
		if ((obj instanceof double[])) {
			return nullSafeToString((double[]) obj);
		}
		if ((obj instanceof float[])) {
			return nullSafeToString((float[]) obj);
		}
		if ((obj instanceof int[])) {
			return nullSafeToString((int[]) obj);
		}
		if ((obj instanceof long[])) {
			return nullSafeToString((long[]) obj);
		}
		if ((obj instanceof short[])) {
			return nullSafeToString((short[]) obj);
		}
		String str = obj.toString();
		return str != null ? str : "";
	}

	public static String nullSafeToString(Object[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}
			sb.append(String.valueOf(array[i]));
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(boolean[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}

			sb.append(array[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(byte[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}
			sb.append(array[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(char[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}
			sb.append("'").append(array[i]).append("'");
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(double[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}

			sb.append(array[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(float[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}

			sb.append(array[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(int[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}
			sb.append(array[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(long[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}
			sb.append(array[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static String nullSafeToString(short[] array) {
		if (array == null) {
			return "null";
		}
		int length = array.length;
		if (length == 0) {
			return "{}";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(", ");
			}
			sb.append(array[i]);
		}
		sb.append("}");
		return sb.toString();
	}

	public static String substringAfter(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (separator == null) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	public static String substringAfterLast(String str, String separator) {
		if (isEmpty(str)) {
			return str;
		}
		if (isEmpty(separator)) {
			return "";
		}
		int pos = str.lastIndexOf(separator);
		if ((pos == -1) || (pos == str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	public static String substringBefore(String str, String separator) {
		if ((isEmpty(str)) || (separator == null)) {
			return str;
		}
		if (separator.length() == 0) {
			return "";
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringBeforeLast(String str, String separator) {
		if ((isEmpty(str)) || (isEmpty(separator))) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	public static String replace(String text, String searchString, String replacement, int max) {
		if ((isEmpty(text)) || (isEmpty(searchString)) || (replacement == null) || (max == 0)) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == -1) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = increase < 0 ? 0 : increase;
		increase *= (max > 64 ? 64 : max < 0 ? 16 : max);
		StringBuilder buf = new StringBuilder(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			max--;
			if (max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	public static String join(Object[] array, String separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = "";
		}

		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static String join(Object[] array, char separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return "";
		}
		StringBuilder buf = new StringBuilder(noOfItems * 16);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int sizePlus1 = 1;
		int i = 0;
		int start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			while (i < len)
				if (Character.isWhitespace(str.charAt(i))) {
					if ((match) || (preserveAllTokens)) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					i++;
					start = i;
				} else {
					lastMatch = false;
					match = true;
					i++;
				}
		}
		if (separatorChars.length() == 1) {
			char sep = separatorChars.charAt(0);
			while (i < len)
				if (str.charAt(i) == sep) {
					if ((match) || (preserveAllTokens)) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					i++;
					start = i;
				} else {
					lastMatch = false;
					match = true;
					i++;
				}
		} else {
			while (i < len)
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if ((match) || (preserveAllTokens)) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					i++;
					start = i;
				} else {
					lastMatch = false;
					match = true;
					i++;
				}
		}
		if ((match) || ((preserveAllTokens) && (lastMatch))) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}
}