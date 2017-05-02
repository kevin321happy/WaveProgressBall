package com.fips.huashun.modle.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Data {
	public static List<Map<String, Object>> groups = new ArrayList<Map<String, Object>>();
	public static Map<String, List<Map<String, Object>>> childs = new HashMap<String, List<Map<String, Object>>>();
	public static List<Integer> gcategory = new ArrayList<Integer>();

	public List<Map<String, Object>> initGroups() {
		groups.clear();
		Map<String, Object> group = new HashMap<String, Object>();
		group.put("name", "搜索资源");
		group.put("id", 1);

		groups.add(group);
		group = new HashMap<String, Object>();
		group.put("name", "热门课程");
		group.put("id", 2);

		groups.add(group);
		return groups;
	}

	public Map<String, List<Map<String, Object>>> initChilds() {
		childs.clear();
		List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", 11);
		map.put("name", "人力资源");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 12);
		map.put("name", "人力资源");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 13);
		map.put("name", "人力资源");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 14);
		map.put("name", "面试宝典");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 15);
		map.put("name", "面试宝典");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 16);
		map.put("name", "面试宝典");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 17);
		map.put("name", "面试宝典");


		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 20);
		map.put("name", "司法考试");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 21);
		map.put("name", "司法考试");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 22);
		map.put("name", "司法考试");

		child.add(map);
		childs.put("1", child);
///////////////////////////////////////////////////////////////
		child = new ArrayList<Map<String, Object>>();
		map = new HashMap<String, Object>();
		map.put("id", 23);
		map.put("name", "产品设计");

		child.add(map);
		map = new HashMap<String, Object>();
		map.put("id", 24);
		map.put("name", "动漫设计");

		child.add(map);
		childs.put("2", child);
		return childs;
	}

	public List<Integer> initGcategory() {
		gcategory.clear();
		gcategory.add(14);
		gcategory.add(15);
		return gcategory;
	}
}
