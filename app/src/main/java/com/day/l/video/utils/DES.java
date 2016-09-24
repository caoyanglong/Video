package com.day.l.video.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {

    public static String key = "7C26642A4F204DF48791F8C2B776D046";
 
//    public final static String DES_KEY_STRING = "ABSujsuu";
     /**
      * 
      * @param key  ���ܵ�key 
      * @param message   ��Ҫ���ܵ�����
      * @return
      * @throws Exception
      */
    public static String encrypt(String key, String message) throws Exception {
    	//��key ���м򵥴���   ���key �ĳ��Ȳ������в���
    	String content = key + Constants.keyConstants;
    	key = content.substring(0, 8);
    	String lv = content.substring(8,16);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(lv.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
    }
    /**
     * ͨ����̬�ļ���key   
     * ����Ϣ���м���  
     * �쳣ͬһ����
     * @param msg
     * @return
     */
    public static String encryptMsg(String msg){
    	String content = "";
    	try {
    		content = encrypt(msg);
		} catch (Exception e) {
		}
    	return content;
    }
    /**
     * ˽�м��ܷ���
     * @param message
     * @return
     * @throws Exception
     */
    public static String privateEncrypt(String message) throws Exception {
    	return encrypt(Constants.PRIVATEKEY, message);
    }
    /**
     * ˽�н��ܷ���
     * @param message
     * @return
     * @throws Exception
     */
    public static String privateDecrypt(String message) throws Exception {
    	return decrypt(Constants.PRIVATEKEY,message);
    }
    public static String encrypt(String message) throws Exception {
    	if(message == null){
    		message = "";
    	}
    	return encrypt(key, message);
    }
    /**
     * 
     * @param key         ���ܵ�key    
     * @param message     ��Ҫ���ܵ�����
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String message) throws Exception {
    	//��key ���в�λ����
    	String content = key + Constants.keyConstants;
    	key = content.substring(0, 8);
    	String lv = content.substring(8,16);
    	
        byte[] bytesrc = decodeBase64(message);//convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(lv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
 
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }
    /**
     * 
     * @param message     ��Ҫ���ܵ�����
     * @return
     * @throws Exception
     */
    public static String decrypt(String message) throws Exception {
    	String key = DES.key;
    	//��key ���в�λ����
    	String content = key + Constants.keyConstants;
    	key = content.substring(0, 8);
    	String lv = content.substring(8,16);
    	
        byte[] bytesrc = decodeBase64(message);//convertHexString(message);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(lv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }
    /**
     * �����쳣�Ľ��ܷ���
     * @param msg
     * @return
     */
    public static String decryptMsg(String msg){
    	try {
    		msg = decrypt(msg);
		} catch (Exception e) {
		}
		return msg;
    }
    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
 
        return digest;
    }
 
    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
 
        return hexString.toString();
    }
 
     
    public static String encodeBase64(byte[] b) {
        return Base64.encodeToString(b, Base64.DEFAULT);
    }
     
    public static byte[] decodeBase64(String base64String) {
        return Base64.decode(base64String, Base64.DEFAULT);
    }
}