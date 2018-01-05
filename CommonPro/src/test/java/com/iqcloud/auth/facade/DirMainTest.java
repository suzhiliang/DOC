//package com.iqcloud.auth.facade;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.internal.InexactComparisonCriteria;
//
//public class DirMainTest {
//	private static List<String> filelist = new ArrayList<String>();
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		
////        refreshFileList("C:\\Users\\anan\\Desktop\\1,知识点");
//        
//        refreshFileList("C:\\Users\\anan\\Desktop\\人教版新.bak\\人教版新");
//        System.out.println(filelist.size());
//        for (int i = 0; i < filelist.size(); i++) {
//        	String dirPath = filelist.get(i);
//        	File file = new File(dirPath);
//        	String dirName = file.getName();
//        	
//        	int index = dirName.indexOf(",");
//        	if (index > 0){
//        		String numStr = dirName.substring(0, index);
//        		String newName = dirName.substring(index+1, dirName.length());
//        		String newDirPath = file.getParentFile().getAbsolutePath() + "\\" + newName;
//        		
//        		File newFile = new File(newDirPath);
//        		file.renameTo(newFile);
//        		
//        		System.out.println(dirPath);
//        		System.out.println(dirName);
//            	System.out.println(numStr);
//            	System.out.println(newName);
//            	System.out.println(newDirPath);
//        	}
//        	
//        	
////        	System.out.println(index);
////        	
////        	System.out.println(dirName);
////        	System.out.println(file.getName());
////        	System.out.println(file.getParentFile().getAbsolutePath());
//        	
//        	System.out.println("===========================================");
//        	
////			System.out.println(filelist.get(i));
//		}
//        
//	}
//
//	/*
//	 * 删除目录
//	 */
//	public static boolean deleteDir(File dir) {
//        if (dir.isDirectory()) {
//            String[] children = dir.list();
//            // 递归删除目录中的子目录下
//            for (int i=0; i<children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        // 目录此时为空，可以删除
//        return dir.delete();
//    }
//	
//	/*
//	 * 遍历文件目录列表
//	 */
//	public static void refreshFileList(String strPath) { 
//        File dir = new File(strPath); 
//        File[] files = dir.listFiles(); 
//        
//        if (files == null) 
//            return; 
//        for (int i = 0; i < files.length; i++) { 
//            if (files[i].isDirectory()) {
//            	String dirName = files[i].getName();
//            	int index = dirName.indexOf("-Del");
//            	if (index > 0){
//            		System.out.println(files[i].getAbsolutePath());
//            		File file = new File(files[i].getAbsolutePath());
//            		deleteDir(file);
//            	}else{
//            		filelist.add(0, files[i].getAbsolutePath());
//            		refreshFileList(files[i].getAbsolutePath());
//            	} 
//            }
//        } 
//    }
//}
