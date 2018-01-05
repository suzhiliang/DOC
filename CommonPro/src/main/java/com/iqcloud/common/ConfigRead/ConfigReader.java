package com.iqcloud.common.ConfigRead;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigReader {
	/**
	 * 整个ini的引用
	 */
	private Map<String, Map<String, List<String>>> map = null;

	/**
	 * 当前Section的引用
	 */
	private String currentSection = null;

	/**
	 * 读取
	 * 
	 * @param path
	 */
	public ConfigReader(String path) {
		map = new HashMap<String, Map<String, List<String>>>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			read(reader);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("IO Exception:" + e);
		}
		// System.out.println("map--> " + map);
	}

	/**
	 * 读取文件
	 * 
	 * @param reader
	 * @throws IOException
	 */
	private void read(BufferedReader reader) throws IOException {
		String line = null;
		while ((line = reader.readLine()) != null) {
			parseLine(line);
		}
	}

	/**
	 * 转换
	 * 
	 * @param line
	 */
	private void parseLine(String line) {
		line = line.trim();
		if (line.matches("^\\#\\[\\S+\\]$")) {
			// section
			String section = line.replaceFirst("^\\#\\[(\\S+)\\]$", "$1");
			addSection(map, section);
		} else if (line.matches("^\\#.*$")) {
			return;
		} else if (line.matches(".*=.*")) {
			int i = line.indexOf("=");
			String key = line.substring(0, i).trim();
			String value = line.substring(i + 1).trim();
			// System.out.println(currentSection + ", " + key + ", " + value);
			addKeyValue(map, currentSection, key, value);
		}

		/*
		 * // 此部分为注释 if(line.matches("^\\#.*$")) { return; }else if
		 * (line.matches("^\\[\\S+\\]$")) { // section String section =
		 * line.replaceFirst("^\\[(\\S+)\\]$","$1");
		 * 
		 * addSection(map,section); }else if (line.matches(".*=.*")) { int i =
		 * line.indexOf("="); String key = line.substring(0, i).trim(); String
		 * value =line.substring(i + 1).trim(); //
		 * System.out.println(currentSection + ", " + key + ", " + value);
		 * addKeyValue(map,currentSection,key,value); }
		 */
	}

	/**
	 * 增加新的Key和Value
	 * 
	 * @param map
	 * @param currentSection
	 * @param key
	 * @param value
	 */
	private void addKeyValue(Map<String, Map<String, List<String>>> map, String currentSection, String key,
			String value) {
		if (!map.containsKey(currentSection)) {
			return;
		}

		Map<String, List<String>> childMap = map.get(currentSection);

		if (!childMap.containsKey(key)) {
			List<String> list = new ArrayList<String>();
			list.add(value);
			childMap.put(key, list);
		} else {
			childMap.get(key).add(value);
		}
	}

	/**
	 * 增加Section
	 * 
	 * @param map
	 * @param section
	 */
	private void addSection(Map<String, Map<String, List<String>>> map, String section) {
		if (!map.containsKey(section)) {
			currentSection = section;
			Map<String, List<String>> childMap = new HashMap<String, List<String>>();
			map.put(section, childMap);
		}
	}

	/**
	 * 获取配置文件指定Section和指定子键的值
	 * 
	 * @param section
	 * @param key
	 * @return
	 */
	public List<String> get(String section, String key) {
		if (map.containsKey(section)) {
			return get(section).containsKey(key) ? get(section).get(key) : null;
		}
		return null;
	}

	/**
	 * 获取配置文件指定Section的子键和值
	 * 
	 * @param section
	 * @return
	 */
	public Map<String, List<String>> get(String section) {
		return map.containsKey(section) ? map.get(section) : null;
	}

	/**
	 * 获取这个配置文件的节点和值
	 * 
	 * @return
	 */
	public Map<String, Map<String, List<String>>> get() {
		return map;
	}
}
