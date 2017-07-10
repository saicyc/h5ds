package com.chinait.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64Utils{
	public static String encodeCreateFile(File file) throws IOException{
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		try {
			inputFile.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			inputFile.close();
		}
		return Base64.getEncoder().encodeToString(buffer).toString();
	}
	public static void decoderCreateFile(String str,String fileUrl) throws IOException{
		byte[] bytes = Base64.getDecoder().decode(str);
		FileOutputStream out = new FileOutputStream(fileUrl);
		try {
			out.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
}
