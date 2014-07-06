package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

public class ResqUtils {
	private static String defaultCharset="utf-8";
	
	public static String getPostString(HttpServletRequest res){
		return getPostString(res,defaultCharset);
	}
	public static String getPostString(HttpServletRequest res,String charset){
		BufferedReader br=null;
		StringBuilder sb=new StringBuilder();
		try {
			br=new BufferedReader(new InputStreamReader(res.getInputStream(), charset));
			String temp="";
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null){
					br.close();
				}
			} catch (Exception e2) {
				e2.toString();
			}
		}
		return sb.toString();
	}
}
