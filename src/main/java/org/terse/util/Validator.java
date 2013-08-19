package org.terse.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public final class Validator {

	/**
	 * 验证集合是否为空
	 * @param 待验证的字符串
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * 验证数组是否为空
	 * @param 待验证的字符串
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isBlank(Object[] obj) {
		return obj == null ? true : (obj.length == 0 ? true : false);
	}

	/**
	 * 验证字符串是否为空
	 * @param 待验证的字符串
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * 验证字符串长度是否大于指定长度-去除2端空格
	 * @param 待验证的字符串
	 * @param 长度
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isGtLength(String str, int length) {
		if (isBlank(str)) {
			return false;
		}
		return getLength(str.trim()) > length;
	}

	/**
	 * 验证字符串长度是否小于指定长度-去除2端空格
	 * @param 待验证的字符串
	 * @param 长度
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isLtLength(String str, int length) {
		if (isBlank(str)) {
			return false;
		}
		return getLength(str.trim()) < length;
	}

	/**
	 * 验证是否是一个价格
	 * @param 待验证的字符串
	 * @param 长度
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isPrice(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "[0-9]+(.[0-9]+)?";
		return match(regex, str);
	}

	/**
	 * 验证是否是一个大于零的价格
	 * @param 待验证的字符串
	 * @param 长度
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isDeposit(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^(([1-9]+[0-9]*.{1}[0-9]+)|([0].{1}[1-9]+[0-9]*)|([1-9][0-9]*)|([0][.][0-9]+[1-9]*))$";
		return match(regex, str);
	}

	/**
	 * 验证数字输入
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isNumber(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	/**
	 * 验证非零的正整数
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isIntNumber(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^\\+?[1-9][0-9]*$";
		return match(regex, str);
	}

	/**
	 * 验证是否是一个纯数字的QQ号码
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isQq(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^\\d{5,}$";
		return match(regex, str);
	}

	/**
	 * 验证手机号码
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isMobilePhone(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^1[3,5]\\d{9}$";
		return match(regex, str);
	}

	/**
	 * 验证电话号码
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isTelePhone(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^(\\d{3,4}-)?\\d{6,8}$";
		return match(regex, str);
	}

	/**
	 * 验证邮箱
	 * @param 待验证的字符串
	 * @return 如果是符合的字符串,返回true,否则为false
	 */
	public static boolean isEmail(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		return match(regex, str);
	}

	/**
	 * 验证真实姓名
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isName(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "[\\u4E00-\\u9FA5]{2,}";
		return match(regex, str);
	}

	/**
	 * 验证用户名
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isUserName(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^[\\w]{6,20}$";
		return match(regex, str);
	}

	/**
	 * 验证输入密码条件(字符与数据同时出现)
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isPassword(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "[A-Za-z]+[0-9]";
		return match(regex, str);
	}

	/**
	 * 验证输入密码长度 (6-20位)
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isPasswLength(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^\\d{6,20}$";
		return match(regex, str);
	}

	/**
	 * 验证IP地址
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isIP(String str) {
		if (isBlank(str)) {
			return false;
		}
		String num = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
		String regex = "^" + num + "\\." + num + "\\." + num + "\\." + num
				+ "$";
		return match(regex, str);
	}

	/**
	 * 验证网址Url
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isUrl(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return match(regex, str);
	}
	
	/**
	 * 验证域名地址
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isDomainName(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return match(regex, str);
	}

	/**
	 * 验证输入邮政编号
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isPostalcode(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^\\d{6}$";
		return match(regex, str);
	}

	/**
	 * 验证输入两位小数
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isDecimal(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^[0-9]+(.[0-9]{2})?$";
		return match(regex, str);
	}

	/**
	 * 验证输入一年的12个月
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isMonth(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^(0?[[1-9]|1[0-2])$";
		return match(regex, str);
	}

	/**
	 * 验证输入一个月的31天
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isDay(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^((0?[1-9])|((1|2)[0-9])|30|31)$";
		return match(regex, str);
	}

	/**
	 * 验证日期时间
	 * @param 待验证的字符串
	 * @return 如果是符合网址格式的字符串,返回true,否则为false
	 */
	public static boolean isDate(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";
		return match(regex, str);
	}

	/**
	 * 验证大写字母
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isUpChar(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^[A-Z]+$";
		return match(regex, str);
	}

	/**
	 * 验证小写字母
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isLowChar(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^[a-z]+$";
		return match(regex, str);
	}

	/**
	 * 验证输入字母
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isLetter(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}

	/**
	 * 验证汉字
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回true,否则为false
	 */
	public static boolean isChinese(String str) {
		if (isBlank(str)) {
			return false;
		}
		String regex = "^[\u4e00-\u9fa5],{0,}$";
		return match(regex, str);
	}

	/**
	 * 验证是否是图片文件
	 * @param file 需要验证的文件
	 * @return 如果是图片文件,返回true,否则为false
	 */
	public static boolean isImage(File file) {
		byte[] b = new byte[10];
		int l = -1;
		try {
			InputStream imgFile = new FileInputStream(file);
			l = imgFile.read(b);
			imgFile.close();
		} catch (Exception e) {
			return false;
		}
		if (l == 10) {
			byte b0 = b[0];
			byte b1 = b[1];
			byte b2 = b[2];
			byte b3 = b[3];
			byte b6 = b[6];
			byte b7 = b[7];
			byte b8 = b[8];
			byte b9 = b[9];
			if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
				return true;
			} else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
				return true;
			} else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I'
					&& b9 == (byte) 'F') {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 验证是否是图片文件大小是否小于等于指定大小
	 * @param file 需要验证的文件
	 * @param size 指定大小 单位kb
	 * @return 如果是小于指定大小,返回true,否则为false
	 */
	public static boolean isLtImageSize(File file, int size) {
		if (file == null || size <= 0) {
			return false;
		}
		long length = size * 1024;// 将Kb换成字节
		if (file.length() <= length) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 检查是不是图片是否小于指定大小
	 * @param fileList 需要验证的文件集合
	 * @param size 指定大小 单位kb
	 * @return 返回首次匹配到不符合的文件索引 否则返回-1
	 */
	public static int chkImage(List<File> fileList, int size) {
		int i=0;
		if(!isEmpty(fileList)){
			for(File file : fileList){
				if(!isImage(file) || !isLtImageSize(file, size)){
					return i;
				}
				i++;
			}
		}
		return -1;
	}
	
	/**
	 * 检查是不是图片是否小于指定大小
	 * @param fileList 需要验证的文件
	 * @param size 指定大小 单位kb
	 * @return 如果是符合,返回true,否则为false
	 */
	public static boolean chkImage(File file, int size) {
		if(!isImage(file) || !isLtImageSize(file, size)){
			return false;
		}
		return true;
	}

	/**
	 * @param regex 正则表达式字符串
	 * @param str 要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	private static int getLength(String str) {
		return str.replaceAll("[^\\x00-\\xff]", "**").length();
	}
}