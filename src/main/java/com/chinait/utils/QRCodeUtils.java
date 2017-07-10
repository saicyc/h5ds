package com.chinait.utils;
/**
 * 二维码生成工具
 * @author yachao
 *
 */
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;


public class QRCodeUtils {
	private static int codeWidth = 200; // 图像宽度  
    private static int codeHeight = 200; // 图像高度 
    private static String format = "png";
    /**
     * 
     * @param fileName(文件名称)
     * @param filePath(文件存放路径)
     * @param url(文件的内容)
     * @return
     */
	public boolean createQRCode(String fileName,String filePath,String content){
		try{
	        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, codeWidth, codeHeight, hints);// 生成矩阵  
	        Path path = FileSystems.getDefault().getPath(filePath, fileName+Constance.QrCodeImg);  
	        MatrixToImageWriter.writeToPath(bitMatrix, format, path);// 输出图像  
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
        return true;
	}
	
	public static int getCodeWidth() {
		return codeWidth;
	}

	public static void setCodeWidth(int codeWidth) {
		QRCodeUtils.codeWidth = codeWidth;
	}

	public static int getCodeHeight() {
		return codeHeight;
	}

	public static void setCodeHeight(int codeHeight) {
		QRCodeUtils.codeHeight = codeHeight;
	}

	public static String getFormat() {
		return format;
	}
	public static void setFormat(String format) {
		QRCodeUtils.format = format;
	}
	
}
