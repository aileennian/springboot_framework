package com.nian.redis.base;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseKeyConstant {	
	protected final static Logger logger = LogManager.getLogger(BaseKeyConstant.class);
	public static enum KeyType{
		PO, 			//单个实体对像[对象对应个表记录]
		LIST,			//LIST集合
		HASH,			//哈希集合
		SET,			//类型
	}
	
	public static final long COUNT_EXPIRE =  60;	
	
    //------------------------------------过期时间设置----------------------------
	//public static final int FIVE_SEC	=5; 		//5秒
	public static final int FIVE_MIN	=300; 	//5分钟  
    //永不过期
    public static final int NEVER_EXPIRE =0;
	
	

	/**
	 * 得到集合等非实体对象的Key key中的过期时间不能去掉。
	 * 
	 * @param <T>
	 * @param abstractKey
	 *            :含有规则的名称
	 * @param bys
	 *            :条件：key为字段名，value为具体的值
	 * @param orders
	 *            ：排序：格式:orders:key为字段名，value为asc或desc
	 * @return
	 */
	public static <T> String getRedisKey(String abstractKey, Map<String, T> bys, Map<String, String> orders) {
		String factKey = getKeyPrefix(abstractKey);
		String where = getConditions(abstractKey, bys);
		String order = getOrders(abstractKey, orders);

		if (where != null && !where.equals("")) {
			factKey += "{by:" + where + "}";
		}

		if (order != null && !order.equals("")) {
			factKey += "{order:" + order + "}";
		}

		String expireRule = getExpireRule(abstractKey);
		factKey += expireRule;

		return factKey.toLowerCase();
	}

	/**
	 * 含有条件的key的取法
	 * 
	 * @param abstractKey
	 * @param bys
	 * @return
	 */
	public static <T> String getRedisKey(String abstractKey, Map<String, T> bys) {
		if (abstractKey.toLowerCase().indexOf("{order") > -1) {
			throw new RuntimeException("规则不符！原定key含有排序要求:" + abstractKey);
		}
		return getRedisKey(abstractKey, bys, null);
	}

	/**
	 * 不含任何条件的key的取法
	 * 
	 * @param abstractKey
	 * @param bys
	 * @return
	 */
	public static <T> String getRedisKey(String abstractKey) {
		if (abstractKey.toLowerCase().indexOf("{by") > -1 || abstractKey.toLowerCase().indexOf("{order") > -1) {
			throw new RuntimeException("规则不符！原定key含有条件或排序要求:" + abstractKey);
		}
		return getRedisKey(abstractKey, null, null);
	}

	/**
	 * 得到实际key的某个条件的值
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月31日
	 * @param key
	 *            :实际的redis的eky
	 * @param whereKey
	 *            :条件参数
	 * @return
	 */
	public static String getConditionValue(String key, String whereKey) {
		String tmp = key.substring(key.indexOf("{by:") + 4);
		String where = tmp.substring(0, tmp.indexOf("}"));

		if (where != null && where != "") {
			String[] ary = where.split(",");
			for (String v : ary) {
				String by = v.substring(0, v.indexOf("=")).trim();
				String value = v.substring(v.indexOf("=") + 1);
				if (by.equalsIgnoreCase(whereKey)) {
					return value;
				}
			}
		}
		return null;
	}

	/**
	 * 生成条件，以','号分隔，前面无"by"
	 * 
	 * @param <T>
	 * @param abstractKey
	 * @param bys
	 * @return
	 */
	public static <T> String getConditions(String abstractKey, Map<String, T> bys) {
		String whererule = getConditionRule(abstractKey);
		if (bys == null || whererule == null || bys.size() == 0)
			return null;

		StringBuffer where = new StringBuffer("");
		whererule = whererule.replace(" ", "").toLowerCase();
		for (String field : bys.keySet()) {
			if (field != null && whererule.indexOf(field.toLowerCase() + "=") > -1) {
				if (!where.toString().equals("")) {
					where.append(",");
				}
				String value = bys.get(field) == null ? "" : String.valueOf(bys.get(field)).trim();
				where.append(field + "=" + value);
			} else {
				throw new RuntimeException("条件不符！原定条件:" + whererule + ",传入参数:" + bys.toString());
			}
		}

		int whereruleCount = getConditionCount(whererule);
		int wherecount = getConditionCount(where.toString());

		if (whereruleCount != wherecount) {
			// throw new
			// RuntimeException("条件不符！原定条件:"+whererule+",传入参数:"+bys.toString());
		}

		return where.toString();
	}

	/**
	 * 得到条件个数
	 * 
	 * @param keyRule
	 * @return
	 */
	public static int getConditionCount(String where) {
		if (where == null)
			return 0;
		String[] rules = where.split(",");
		return rules.length;
	}

	/**
	 * 生在排序
	 * 
	 * @param abstractKey
	 * @param orders
	 * @return
	 */
	public static <T> String getOrders(String abstractKey, Map<String, T> orders) {
		String orderrule = getOrderRule(abstractKey);
		StringBuffer order = new StringBuffer("");
		if (orders != null && orders.size() > 0 && orderrule != null) {
			orderrule = orderrule.replace(" ", "").toLowerCase();

			for (String field : orders.keySet()) {
				if (!order.toString().equals("")) {
					order.append(",");
				}
				order.append(field + "_" + orders.get(field));
			}
		}
		return order.toString();
	}

	public static String getExpireRule(String key) {
		if (key == null)
			return "{exp=" + FIVE_MIN + "}";
		key = key.toLowerCase().replaceAll(" ", "");

		String tag1 = "{exp=";
		String tag2 = "{exp:";
		try {
			String expireRule = "";
			if (key.indexOf(tag1) > -1 || key.indexOf(tag2) > -1) {
				if (key.indexOf(tag1) > -1) {
					expireRule = key.substring(key.indexOf(tag1));
				} else if (key.indexOf(tag2) > -1) {
					expireRule = key.substring(key.indexOf(tag2));
				}
				expireRule = expireRule.substring(0, expireRule.indexOf("}") + 1);
				return expireRule.toLowerCase();
			} else {
				return "{exp=" + FIVE_MIN + "}";// 默认都5分钟过期
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{exp=" + FIVE_MIN + "}";
		}
	}

	/**
	 * 
	 * 得到过期时间,如果出现异常，默认五分钟过期
	 * 
	 * @param key
	 * @throws ParseException
	 */
	public static int getExpireCustom(String key) {
		if (key == null)
			return FIVE_MIN; // key不存在，那么5秒钟过期。
		try {
			key = getExpireRule(key);

			String tag1 = "{exp=";
			String tag2 = "{exp:";
			if (key.indexOf(tag1) > -1 || key.indexOf(tag2) > -1) {
				if (key.indexOf(tag1) > -1) {
					key = key.substring(key.indexOf(tag1) + tag1.length());
				}
				if (key.indexOf(tag2) > -1) {
					key = key.substring(key.indexOf(tag2) + tag2.length());
				}

				key = key.substring(0, key.indexOf("}"));
				if (key.startsWith("to:")) {
					int toDay = Integer.parseInt(key.substring(3, key.indexOf("/")));
					String time = key.substring(key.indexOf("/") + 1);
					if (time.indexOf(":") == time.lastIndexOf(":")) {
						time += ":00";
					}
					SimpleDateFormat onlyDate = new SimpleDateFormat("yyyy-MM-dd");
					String strToDate = onlyDate.format(DateUtils.addDays(new Date(), toDay)) + " " + time;

					Timestamp toDate = Timestamp.valueOf(strToDate);

					long toTime = toDate.getTime();
					int time_sec = Math.round((toTime - System.currentTimeMillis()) / 1000);

					if (time_sec <= 0) {
						time_sec = FIVE_MIN;
					}
					return time_sec;
					// 到整点小时过期，加上随机数防止并发压力，时间紧简单写，以后整理
				} else if (key.startsWith("every:")) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.HOUR_OF_DAY, 1);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, new Random().nextInt(30));
					int time_sec = Math.round((cal.getTimeInMillis() - System.currentTimeMillis()) / 1000);
					if (time_sec <= 0) {
						time_sec = FIVE_MIN;
					}
					return time_sec;
				} else {
					return Integer.parseInt(key);
				}
			} else {
				return FIVE_MIN;
			}
		} catch (Exception e) {
			// 出异常默认为5分钟过期
			logger.error(e.getMessage(), e);
			return FIVE_MIN;
		}
	}

	/**
	 * 得到条件
	 * 
	 * @author nianxiaoling[14110310]
	 * @date 2015年3月30日
	 * @param key
	 * @param paramKey
	 * @return
	 */
	public static String getWhere(String key, String paramKey) {
		return "";
	}

	/**
	 * 得到查询条件，全部小写,中间用逗 号隔开
	 * 
	 * @param key
	 */
	public static String getConditionRule(String abstractKey) {
		if (abstractKey == null)
			return null;
		abstractKey = abstractKey.toLowerCase().replaceAll(" ", "");
		String tag = "{by:";
		if (abstractKey != null && abstractKey.indexOf(tag) > -1) {
			abstractKey = abstractKey.substring(abstractKey.indexOf(tag));
			abstractKey = abstractKey.substring(0, abstractKey.indexOf("}") + 1);
			abstractKey = abstractKey.replaceAll("by:", "");
			String c = abstractKey.substring(1, abstractKey.length() - 1);
			return c;
		} else if (abstractKey.toLowerCase().startsWith(KeyType.PO.name().toLowerCase())) {
			abstractKey = abstractKey.substring(abstractKey.indexOf("{"));
			abstractKey = abstractKey.substring(0, abstractKey.indexOf("}") + 1);
			String c = abstractKey.substring(1, abstractKey.length() - 1);
			return c;
		}
		return null;
	}

	/**
	 * 得到排序条件，全部小写
	 * 
	 * @param key
	 */
	public static String getOrderRule(String abstractKey) {
		if (abstractKey == null)
			return null;
		abstractKey = abstractKey.toLowerCase().replaceAll(" ", "");
		String tag = "{order:";
		if (abstractKey.indexOf(tag) > -1) {
			abstractKey = abstractKey.substring(abstractKey.indexOf(tag));
			abstractKey = abstractKey.substring(0, abstractKey.indexOf("}") + 1);
			abstractKey = abstractKey.replace("order:", "");
			return abstractKey.toLowerCase();
		}
		return null;
	}

	/**
	 * 返回key的前缀
	 * 
	 * @param key
	 */
	public static String getKeyPrefix(String abstractKey) {
		if (abstractKey == null)
			return "";
		abstractKey = abstractKey.toLowerCase().replaceAll(" ", "");
		String tag = "{";
		if (abstractKey.indexOf("{") > -1) {
			abstractKey = abstractKey.substring(0, abstractKey.indexOf(tag));
		}
		return abstractKey.toLowerCase();
	}
}
