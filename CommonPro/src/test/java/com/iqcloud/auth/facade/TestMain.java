//package com.iqcloud.auth.facade;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.web.client.RestTemplate;
//
//import scala.Array;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.iqcloud.auth.dto.IQCloudInfoDto;
//import com.iqcloud.auth.dto.MyIQCloudInfoDto;
//
//class TestDto implements Serializable{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -5564436423325628404L;
//	
//	public String name1;
//	public String name2;
//	
//	public List<String> myList;
//
//	public String getName1() {
//		return name1;
//	}
//
//	public void setName1(String name1) {
//		this.name1 = name1;
//	}
//
//	public String getName2() {
//		return name2;
//	}
//
//	public void setName2(String name2) {
//		this.name2 = name2;
//	}
//
//	public List<String> getMyList() {
//		return myList;
//	}
//
//	public void setMyList(List<String> myList) {
//		this.myList = myList;
//	}
//
//
//
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}
//}
//
//public class TestMain {
//	/*
//	 * 字符串与运算
//	 */
//	public static String StrAnd(String strValue1, String strValue2){
//		String resultStr = "";
//		for (int i = 0; i < strValue1.length(); i++) {
//			String s1 = strValue1.substring(i, i + 1);
//			String s2 = strValue2.substring(i, i + 1);
//			
//			if ("1".equals(s1) && "1".equals(s2)){
//				resultStr = resultStr + "1";
//			}else{
//				resultStr = resultStr + "0";
//			}
//		}
//		
//		return resultStr;
//	}
//	
//	/*
//	 * 字符串或运算
//	 */
//	public static String StrOr(String strValue1, String strValue2){
//		String resultStr = "";
//		for (int i = 0; i < strValue1.length(); i++) {
//			String s1 = strValue1.substring(i, i + 1);
//			String s2 = strValue2.substring(i, i + 1);
//			
//			if ("1".equals(s1) || "1".equals(s2)){
//				resultStr = resultStr + "1";
//			}else{
//				resultStr = resultStr + "0";
//			}
//		}
//		
//		return resultStr;
//	}
//	
//	/*
//	 * 字符串非运算
//	 */
//	public static String StrNot(String strValue){
//		String resultStr = "";
//		for (int i = 0; i < strValue.length(); i++) {
//			String s = strValue.substring(i, i + 1);
//			if ("1".equals(s)){
//				resultStr = resultStr + "0";
//			}else{
//				resultStr = resultStr + "1";
//			}
//		}
//		
//		return resultStr;
//	}
//	
//	/*
//	 * 设置值
//	 */
//	public static String setStrValue(String strValue, int i, String itemValue){
//		String resultValue = strValue.substring(0, i) + itemValue + strValue.substring(i + 1, strValue.length());
//		return resultValue;
//	}
//	
//	/*
//	 * 获取字符串值
//	 */
//	public static String getStrValue(String strValue, int i){
//		String resultValue = strValue.substring(i, i + 1);
//		return resultValue;
//	}
//	
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
//		// 产生uuid
////		for (int i = 0; i < 20; i++) {
////			UUID uuid = UUID.randomUUID();
////			String sUuid = uuid.toString();
////			String sNewUuid = sUuid.replaceAll("-", "");
////			sNewUuid = sNewUuid.toUpperCase();
////			System.out.println(sNewUuid);
////		}
//		
//		String valueStr1 = "1001";
//		String valueStr2 = "1100";
//		
//		System.out.println("valueStr1--> " + valueStr1);
//		System.out.println("valueStr2--> " + valueStr2);
//		
//		String andStr = StrAnd(valueStr1, valueStr2);
//		System.out.println("StrAnd--> " + andStr);
//		
//		String orStr = StrOr(valueStr1, valueStr2);
//		System.out.println("StrOr--> " + orStr);
//		
//		String notStr = StrNot(valueStr1);
//		System.out.println("notStr--> " + notStr);
//		
//		String newStr = setStrValue(valueStr1, 2, "1");
//		System.err.println("newStr--> " + newStr);
//		
//		String getStr = getStrValue(valueStr1, 3);
//		System.out.println("getStr--> " + getStr);
//		
//		/*
//		RestTemplate restTemplate = new RestTemplate();
//		MyIQCloudInfoDto myIQCloudInfoDto = new MyIQCloudInfoDto();
//		myIQCloudInfoDto.setMsgBody("msgBody");
//		myIQCloudInfoDto.setMsgHead("msgHead");
//		
//		TestDto testDto = new TestDto();
//		testDto.setName1("myName1");
//		testDto.setName2("myName2");
//		List<String> stringsList = new ArrayList<String>();
//		stringsList.add("listName1");
//		stringsList.add("listName2");
//		testDto.setMyList(stringsList);
//		
//		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(testDto));
//		myIQCloudInfoDto.setJsonBody(jsonObject);
//		
//		MyIQCloudInfoDto myIQCloudInfoDto2 = restTemplate.postForObject("http://127.0.0.1:8080/IQAuthDubboService/myTestController/mytest", myIQCloudInfoDto, MyIQCloudInfoDto.class);
//		System.out.println("myIQCloudInfoDto2--->" + myIQCloudInfoDto2);
//		System.out.println(myIQCloudInfoDto2.getJsonBody().toString());
//		
//		String jsonStr = JSON.toJSONString(myIQCloudInfoDto2.getJsonBody());
//		JSONObject jsonObject2 = JSONObject.parseObject(jsonStr);
//		System.out.println("jsonObject2---> " + jsonObject2);
//		*/
//	}
//
//}
