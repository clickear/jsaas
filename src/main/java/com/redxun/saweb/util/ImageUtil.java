package com.redxun.saweb.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片工具类，对图片进行缩略处理及剪切处理
 * @author csx
 *@Email chshxuan@163.com
 * @Copyright (c) 2014-2016 广州红迅软件有限公司（http://www.redxun.cn）
 * 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class ImageUtil {

    private static Logger log = LoggerFactory.getLogger(ImageUtil.class);
    
    private static String DEFAULT_THUMB_PREVFIX = "thumb_";
    private static String DEFAULT_CUT_PREVFIX = "cut_";
    private static Boolean DEFAULT_FORCE = false;
    
    
    /**
     * 根据原图与裁切size截取局部图片
     * @param srcImg    源图片
     * @param output    图片输出流
     * @param rect        需要截取部分的坐标和大小
     */
    public static void cutImage(File srcImg, OutputStream output, java.awt.Rectangle rect){
        if(srcImg.exists()){
            java.io.FileInputStream fis = null;
            ImageInputStream iis = null;
            try {
                fis = new FileInputStream(srcImg);
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if(srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
                // 将FileInputStream 转换为ImageInputStream
                iis = ImageIO.createImageInputStream(fis);
                // 根据图片类型获取该种类型的ImageReader
                ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
                reader.setInput(iis,true);
                ImageReadParam param = reader.getDefaultReadParam();
                param.setSourceRegion(rect);
                BufferedImage bi = reader.read(0, param);
                ImageIO.write(bi, suffix, output);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fis != null) fis.close();
                    if(iis != null) iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else {
            log.warn("the src image is not exist.");
        }
    }
    
    public static void cutImage(File srcImg, OutputStream output, int x, int y, int width, int height){
        cutImage(srcImg, output, new java.awt.Rectangle(x, y, width, height));
    }
    
    public static void cutImage(File srcImg, String destImgPath, java.awt.Rectangle rect){
        File destImg = new File(destImgPath);
        if(destImg.exists()){
            String p = destImg.getPath();
            try {
                if(!destImg.isDirectory()) p = destImg.getParent();
                if(!p.endsWith(File.separator)) p = p + File.separator;
                cutImage(srcImg, new java.io.FileOutputStream(p + DEFAULT_CUT_PREVFIX + "_" + new java.util.Date().getTime() + "_" + srcImg.getName()), rect);
            } catch (FileNotFoundException e) {
                log.warn("the dest image is not exist.");
            }
        }else log.warn("the dest image folder is not exist.");
    }
    
    public static void cutImage(File srcImg, String destImg, int x, int y, int width, int height){
        cutImage(srcImg, destImg, new java.awt.Rectangle(x, y, width, height));
    }
    
    public static void cutImage(String srcImg, String destImg, int x, int y, int width, int height){
        cutImage(new File(srcImg), destImg, new java.awt.Rectangle(x, y, width, height));
    }
    
    public static void thumbnailImage(InputStream srcImg,String suffix, OutputStream output, int maxWidth,  boolean force){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
               
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
           
                
                BufferedImage img = ImageIO.read(srcImg);
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                int width = img.getWidth(null);
                int height = img.getHeight(null);
                
                if(!force){
                    if(width>maxWidth){
                    	height=(int)(height*((float)maxWidth /width));
                    	width=maxWidth;
                    }
                    else{
                    	ImageIO.write(img, suffix, output);
                    	return;
                    }
                }
                
                if(suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"))){
                    BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
                    Graphics2D g2d = to.createGraphics(); 
                    to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
                    g2d.dispose(); 
                    
                    g2d = to.createGraphics(); 
                    Image from = img.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
                    g2d.drawImage(from, 0, 0, null);
                    g2d.dispose(); 
                    
                  
                    
                    ImageIO.write(to, suffix, output);
                }else{
                    
                    BufferedImage newImage = new BufferedImage(width, height, img.getType());
                    Graphics g = newImage.getGraphics();
                    g.drawImage(img, 0, 0, width, height, null);
                    g.dispose();
                   
                    ImageIO.write(newImage, suffix, output);
                }
           
                
               
            } catch (IOException e) {
               log.error("generate thumbnail image failed.",e);
            }finally{
            	 try {
            		 srcImg.close();
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
      
    }
    
    
    /**
     * <p>Title: thumbnailImage</p>
     * <p>Description: 根据图片路径生成缩略图 </p>
     * @param imagePath    原图片路径
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     */
    public static void thumbnailImage(File srcImg, OutputStream output, int w, int h, String prevfix, boolean force){
        if(srcImg.exists()){
            try {
                // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
                String types = Arrays.toString(ImageIO.getReaderFormatNames()).replace("]", ",");
                String suffix = null;
                // 获取图片后缀
                if(srcImg.getName().indexOf(".") > -1) {
                    suffix = srcImg.getName().substring(srcImg.getName().lastIndexOf(".") + 1);
                }// 类型和图片后缀全部小写，然后判断后缀是否合法
                if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase()+",") < 0){
                    log.error("Sorry, the image suffix is illegal. the standard image suffix is {}." + types);
                    return ;
                }
                log.debug("target image's size, width:{}, height:{}.",w,h);
                
                Image img = ImageIO.read(srcImg);
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                if(!force){
                    int width = img.getWidth(null);
                    int height = img.getHeight(null);
                    if((width*1.0)/w < (height*1.0)/h){
                        if(width > w){
                            h = Integer.parseInt(new java.text.DecimalFormat("0").format(height * w/(width*1.0)));
                            log.debug("change image's height, width:{}, height:{}.",w,h);
                        }
                    } else {
                        if(height > h){
                            w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * h/(height*1.0)));
                            log.debug("change image's width, width:{}, height:{}.",w,h);
                        }
                    }
                }
                BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics g = bi.getGraphics();
                g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
                g.dispose();
                // 将图片保存在原目录并加上前缀
                ImageIO.write(bi, suffix, output);
               
            } catch (IOException e) {
               log.error("generate thumbnail image failed.",e);
            }finally{
            	 try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }else{
            log.warn("the src image is not exist.");
        }
    }
    /**
     * 生成文件缩略图
     * @param srcImg
     * @param w
     * @param h
     * @param prevfix
     * @param force
     * @return 返回缩略图的文件名
     */
    public static String thumbnailImage(File srcImg, int w, int h, String prevfix, boolean force){
        String p = srcImg.getAbsolutePath();
        //文件缩略图路径
        String fileName=null;
        String thumbName=null;
        try {
            if(!srcImg.isDirectory()) p = srcImg.getParent();
            if(!p.endsWith(File.separator)) p = p + File.separator;
            
            fileName=srcImg.getName();
            int index=fileName.indexOf(".");
            
            if(index!=-1){
            	String orgName=fileName.substring(0,index);
            	String orgExt=fileName.substring(index);
            	thumbName=orgName+"_"+prevfix+orgExt;
            	fileName=p+thumbName;
            }
            thumbnailImage(srcImg, new java.io.FileOutputStream(fileName), w, h, prevfix, force);
        } catch (FileNotFoundException e) {
            log.error("the dest image is not exist.",e);
        }
        return thumbName;
    }
    
    /**
     * 生成文件缩略图
     * @param imagePath
     * @param w
     * @param h
     * @param prevfix
     * @param force
     */
    public static String thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force){
        File srcImg = new File(imagePath);
        return thumbnailImage(srcImg, w, h, prevfix, force);
    }
    
    public static String thumbnailImage(String imagePath, int w, int h, boolean force){
        return thumbnailImage(imagePath, w, h, DEFAULT_THUMB_PREVFIX, DEFAULT_FORCE);
    }
    
    public static String thumbnailImage(String imagePath, int w, int h){
        return thumbnailImage(imagePath, w, h, DEFAULT_FORCE);
    }
    
    
    /**
     * 给图片增加水印。
     * @param pressText
     * @param is
     * @param fontName
     * @param fontStyle
     * @param color
     * @param fontSize
     * @param position
     * @param type
     * @param output
     */
    public static void pressText(String pressText, InputStream is,
    		   String fontName, int color, int fontSize, String position,String type,OutputStream output) {
    	try {
		   Image src = ImageIO.read(is);
		   int wideth = src.getWidth(null);
		   int height = src.getHeight(null);
		   BufferedImage image = new BufferedImage(wideth, height,
		     BufferedImage.TYPE_INT_RGB);
		   Graphics g = image.createGraphics();
		   g.drawImage(src, 0, 0, wideth, height, null);
		     
		   g.setColor(Color.lightGray);
		   g.setFont(new Font(fontName, Font.BOLD, fontSize));
		   
		   int[] point=getPostion(pressText,height,fontSize,position);
		  
		   g.drawString(pressText, point[0], point[1]);
		   g.dispose();
		   
		   ImageIO.write(image, type, output);
    		  
    	} catch (Exception e) {
    		   e.printStackTrace();
    	}
    	
    }
    
    private static int[] getPostion(String pressText,int height, int fontSize,String position){
    	if("top".equals(position)){
    		return new int[]{10,0};
    	}
    	else if("right".equals(position)){
    		int x=pressText.length()*fontSize;
    		return new int[]{x,10};
    	}
    	else if("bottom".equals(position)){
    		int x=pressText.length()*fontSize;
    		int y=height-10-fontSize;
    		return new int[]{x,y};
    	}
    	else if("left".equals(position)){
    		int x=10;
    		int y=height-10-fontSize;
    		return new int[]{x,y};
    	}
    	return new int[]{0,0};
    }
    
    
    public static void main(String[] args) {
        String thumbnailImg=thumbnailImage("d:/mspaint.png", 250, 250);
        System.out.println("img:" + thumbnailImg);
        //cutImage("imgs/Tulips.jpg","imgs", 250, 70, 300, 400);
    }

}