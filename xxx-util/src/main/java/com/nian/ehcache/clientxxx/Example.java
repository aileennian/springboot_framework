package com.nian.ehcache.clientxxx;

import java.util.ArrayList;
import java.util.List;

import com.nian.ehcache.CacheElement;
import com.nian.ehcache.EhcacheUtil;

public class Example {

	
	public List<InfoPublishDTO> operationExampleInfoPublishe() {
		CacheElement cacheElement = EhcacheKeyConstant.getElementForInfoPublish();
		/**
		 * 得到公告信息
		 */
		List<InfoPublishDTO> listPublish = EhcacheUtil.get(cacheElement);

		if (listPublish == null) {
			RequestInfoPublishDTO req = new RequestInfoPublishDTO();
			//req.setPublishType(1); //明明就没有此方法，这代码写的，是没测过吗？
			//req.setLimitedCount(3);;// 限制3条记录
			// listPublish = pageManageService.getInfoPublish(req).getResult();
			// 从中台取数
			listPublish = new ArrayList<InfoPublishDTO>();
			EhcacheUtil.put(cacheElement, listPublish);
		}
		return listPublish;
	}
}
