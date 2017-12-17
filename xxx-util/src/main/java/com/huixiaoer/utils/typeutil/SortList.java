package com.huixiaoer.utils.typeutil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortList<E> {
	@SuppressWarnings("all")
	public void Sort(List<E> list, final String method, final String sort,
			final String type) {
		Collections.sort(list, new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			public int compare(Object a, Object b) {
				int ret = 0;
				try {
					Method m1 = ((E) a).getClass().getMethod(method, null);
					Method m2 = ((E) b).getClass().getMethod(method, null);
					Object obja = m1.invoke(((E) a), null);
					Object objb = m2.invoke(((E) b), null);
					if (type.equals("Integer")) {
						Integer ib = (Integer) objb;
						Integer ia = (Integer) obja;
						if (sort != null && "desc".equals(sort)) {
							ret =ib.compareTo(ia);
						}
						else{
						ret = ia.compareTo(ib);
						}
					}
					if (type.equals("Double")) {
						Double db = (Double) objb;
						Double da = (Double) obja;
						if (sort != null && "desc".equals(sort)) {
							ret = db.compareTo(da);
						}else{
						ret = da.compareTo(db);
						}
					}
					if (type.equals("Float")) {
						Float fb = (Float) objb;
						Float fa = (Float) obja;
						if (sort != null && "desc".equals(sort)) {
							ret = fb.compareTo(fa);
						}else{
						ret = fa.compareTo(fb);
						}
						
					}
					if (type.equals("BigDecimal")) {
						BigDecimal bigb = (BigDecimal) objb;
						BigDecimal biga = (BigDecimal) obja;
						if (sort != null && "desc".equals(sort)) {
							ret = bigb.compareTo(biga);
						}else{
						ret = biga.compareTo(bigb);
						}
					}
				} catch (NoSuchMethodException ne) {
					System.out.println(ne);
				} catch (IllegalAccessException ie) {
					System.out.println(ie);
				} catch (InvocationTargetException it) {
					System.out.println(it);
				}
				return ret;
			}
		});
	}
}
