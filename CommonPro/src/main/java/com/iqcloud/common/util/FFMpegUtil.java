package com.iqcloud.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;  

/** 
 * FFMpegUntil 
 * <p>Title: FFMpeg工具类</p> 
 * <p>Description: 本类封装FFMpeg对视频的操作</p> 
 * <p>Date: 2010-7-14</p> 
 * <p>Copyright: Copyright (c) 2010</p> 
 * <p>Company: novel-supertv.com</p> 
 * @author chenggong 
 * @version 1.0 
 */  
public class FFMpegUtil implements IStringGetter{  
      
    @SuppressWarnings("unused")
	private int runtime = 0;  
    private String ffmpegUri;//ffmpeg地址  
    private String qtFaststartUri;//qtFaststartUri地址  
    private String originFileUri; //视频源文件地址  
    private enum FFMpegUtilStatus { Empty, CheckingFile, GettingRuntime, GettingVCodeC};  
    private FFMpegUtilStatus status = FFMpegUtilStatus.Empty;  
    private List<String> cmd = new ArrayList<String>();  
    private boolean isSupported;  


    private String timeString;
    
    private String vCodeC;
      
    /** 
     * 构造函数 
     * @param ffmpegUri ffmpeg的全路径  
     *      如e:/ffmpeg/ffmpeg.exe 或 /root/ffmpeg/bin/ffmpeg 
     * @param originFileUri 所操作视频文件的全路径  
     *      如e:/upload/temp/test.wmv 
     */  
    public FFMpegUtil( String ffmpegUri,String qtFaststartUri, String originFileUri ){  
        this.ffmpegUri = ffmpegUri;  
        this.qtFaststartUri = qtFaststartUri; 
        this.originFileUri = originFileUri;  
    }  
  
    /** 
     * 获取视频编码
     * @return 
     */  
    public String getVCodeC(){
        runtime = 0;  
        status = FFMpegUtilStatus.GettingVCodeC;  
        cmd.clear();  
        cmd.add(ffmpegUri);  
        cmd.add("-i");  
        cmd.add(originFileUri);  
        CmdExecuter.exec(cmd, this);  
        return vCodeC;   
    }  
    
    /** 
     * 获取视频时长 00:00:00
     * @return 
     */  
    public String getRuntime(){  
        runtime = 0;  
        status = FFMpegUtilStatus.GettingRuntime;  
        cmd.clear();  
        cmd.add(ffmpegUri);  
        cmd.add("-i");  
        cmd.add(originFileUri);  
        CmdExecuter.exec(cmd, this);  
        return timeString;   
    }  
      
    /** 
     * 检测文件是否是支持的格式 
     * 将检测视频文件本身，而不是扩展名 
     * @return 
     */  
    public boolean isSupported(){  
        isSupported = true;  
        status = FFMpegUtilStatus.CheckingFile;  
        cmd.clear();  
        cmd.add(ffmpegUri);  
        cmd.add("-i");  
        cmd.add(originFileUri);  
        CmdExecuter.exec(cmd, this);  
        return isSupported;  
    }  
            
    /** 
     * 生成视频截图 
     * @param imageSavePath 截图文件保存全路径 
     * @param screenSize 截图大小 如640x480 
     */  
    public void makeScreenCut( String imageSavePath , String screenSize ){  
        cmd.clear();  
        cmd.add(ffmpegUri);  
        cmd.add("-i");  
        cmd.add(originFileUri);  
        cmd.add("-y");  
        cmd.add("-f");  
        cmd.add("image2");  
        cmd.add("-ss");  
        cmd.add("8");  
        cmd.add("-t");  
        cmd.add("0.001");  
        cmd.add("-s");  
        cmd.add(screenSize);  
        cmd.add(imageSavePath);  
        CmdExecuter.exec(cmd, null);  
    }  
      
    /** 
     * 视频转换 
     * @param fileSavePath 文件保存全路径（包括扩展名）如 e:/abc/test.flv 
     */  
    public void videoTransfer(   
            String fileSavePath){  
    	String temFileUrl = originFileUri;
    	String code = getVCodeC();
    	String mp4Path = fileSavePath.substring(0, fileSavePath.lastIndexOf(".")) + "_temp.mp4";
    	if(code!=null){
    		if(code.startsWith("cscd")) { //白板软件录制视频格式，要先转为flv再转为mp4
        		String aviPath = fileSavePath.substring(0, fileSavePath.lastIndexOf(".")) + ".flv";    		
        		temFileUrl = aviPath;
        		videoTransferFLV(aviPath);
        	}
    	}
    	
        cmd.clear();        
        cmd.add(ffmpegUri);  
        cmd.add("-i");  
        cmd.add(temFileUrl);  
//        cmd.add("-y");  
//        cmd.add("-ab");  
//        cmd.add( "56");  
//        cmd.add("-ar");  
//        cmd.add("22050");  
//        cmd.add("-qscale");  
//        cmd.add( "8");  
//        cmd.add("-r");  
//        cmd.add( "15" );  
//        cmd.add("-vcodec");
//        cmd.add("h264");        
        cmd.add( "-vf" );  
        cmd.add("scale=1280:-2");
        cmd.add( mp4Path );  
        CmdExecuter.exec(cmd, null);  
        if(!temFileUrl.equals(originFileUri)) {
        	File file = new File(temFileUrl);
        	file.delete();
        }
        /*
         * FFMpeg转码由此得到的mp4文件中, meta信息是在文件尾部的, 
         * 而 videoview 在没有得到meta信息前不会播放文件,
         *  因此只有等到文件完全下载完视频才会播放. 
         *  因此可以使用qt-faststart来把meta信息移到文件头部
         *  
         * 把Mp4的meta信息放到文件头
         */
        cmd.clear(); 
        cmd.add(qtFaststartUri);  
        cmd.add( mp4Path );  
        cmd.add( fileSavePath );  
        CmdExecuter.exec(cmd, null);  
//        File file2 =  new File(mp4Path);
//        file2.delete();
        
    }  
    
    private void videoTransferFLV(String fileSavePath) {
    	System.out.println("------------------先转为flv--------------");
    	cmd.clear();        
        cmd.add(ffmpegUri);  
        cmd.add("-i");  
        cmd.add(originFileUri);  
        cmd.add("-y");  
        cmd.add("-ab");  
        cmd.add( "56");  
        cmd.add("-ar");  
        cmd.add("22050");  
        cmd.add("-qscale");  
        cmd.add( "8");  
        cmd.add("-r");  
        cmd.add( "15" );  
        cmd.add( fileSavePath );  
        CmdExecuter.exec(cmd, null);  
    }
    
    /** 
     * 视频转换 
     * @param fileSavePath 文件保存全路径（包括扩展名）如 e:/abc/test.flv 
     * @param screenSize 视频分辨率 如640x480 
     * @param audioByte 音频比特率 
     * @param audioCollection 音频采样率 
     * @param quality 视频质量(0.01-255)越低质量越好 
     * @param fps 每秒帧数（15或29.97） 
     */  
    public void videoTransfer(   
            String fileSavePath,  
            String screenSize,  
            int audioByte,  
            int audioCollection,  
            double quality,  
            double fps){  
        cmd.clear();  
        cmd.add(ffmpegUri);  
        cmd.add("-i");  
        cmd.add(originFileUri);  
        cmd.add("-y");  
        cmd.add("-ab");  
        cmd.add( Integer.toString(audioByte) );  
        cmd.add("-ar");  
        cmd.add( Integer.toString(audioCollection) );  
        cmd.add("-qscale");  
        cmd.add( Double.toString(quality) );  
        cmd.add("-r");  
        cmd.add( Double.toString(fps) );  
        cmd.add("-s");  
        cmd.add(screenSize);              
        cmd.add( fileSavePath );  

        CmdExecuter.exec(cmd, null);  
    }  
    


      
//    String regexDuration ="Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s"; // 总时间，开始时间，bitrate 码率 单位 kb
//    String regexVideo ="Video: (.*?), (.*?), (.*?)[,\\s]";  //编码格式 ,视频格式， 分辨率
//    String regexAudio ="Audio: (\\w*), (\\d*) Hz";    //音频编码, 音频采样频率
    @Override  
    public void dealString( String str ){  
          
        switch( status )  
        {  
            case Empty:  
                break;  
            case CheckingFile:{  
                Matcher m = Pattern.compile("Unknown format").matcher(str);  
                if( m.find() )  
                    this.isSupported = false;  
                break;  
            }  
            case GettingRuntime:{  
                Matcher m = Pattern.compile("Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s").matcher(str);
                while (m.find())   
                {  
                     String msg = m.group(1);  
                     System.out.println("msg:" + msg);
                     timeString = msg;
                }  
                
                break;  
            }  
            case GettingVCodeC:{  
            	Matcher m = Pattern.compile("Video: (.*?), (.*?), (.*?)[,\\s]").matcher(str);
                while (m.find())   
                {  
                     String msg = m.group(1);  
                     System.out.println("msg:" + msg);
                     vCodeC = msg;
                }  
                
                break;  
            }  
        }//switch  
    }  
}  
