package com.gogwt.apps.tracking.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Use Secure Hash Algorithm (SHA-256)
 * 
 * @author michael.wang
 * 
 */
public class PasswordEncoder {
	private static final Logger log = Logger.getLogger(PasswordEncoder.class);

	private PasswordEncoder() {
	}

	private static PasswordEncoder instance;

	/**
     * Count for the number of time to hash,
     * more you hash more difficult it would be for the attacker
     */
    private final static int ITERATION_COUNT = 2;
    
	public static synchronized PasswordEncoder getInstance() {

		if (instance == null) {
			instance = new PasswordEncoder();
		}

		return instance;
	}

	public synchronized String encode(String password, String saltKey)
			throws NoSuchAlgorithmException, IOException {
		if (log.isDebugEnabled()) {
			log.debug("encode(String, String) - start");
		}

		String encodedPassword = null;
		byte[] salt = base64ToByte(saltKey);

		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);

		byte[] btPass = digest.digest(password.getBytes("UTF-8"));
		for (int i = 0; i < ITERATION_COUNT; i++) {
			digest.reset();
			btPass = digest.digest(btPass);
		}

		encodedPassword = byteToBase64(btPass);

		if (log.isDebugEnabled()) {
			log.debug("encode(String, String) - end");
		}
		return encodedPassword;
	}

	public synchronized String encode(String password) {
		try {
			return encode(password, "mysecret");
		}
		catch (Throwable e) {
			
		}
		return password;
	}
	/**
	 * @param str
	 * @return byte[]
	 * @throws IOException
	 */
	private byte[] base64ToByte(String str) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("base64ToByte(String) - start");
		}

		BASE64Decoder decoder = new BASE64Decoder();
		byte[] returnbyteArray = decoder.decodeBuffer(str);
		if (log.isDebugEnabled()) {
			log.debug("base64ToByte(String) - end");
		}
		return returnbyteArray;
	}

	/**
	 * @param bt
	 * @return String
	 * @throws IOException
	 */
	private String byteToBase64(byte[] bt) {
		if (log.isDebugEnabled()) {
			log.debug("byteToBase64(byte[]) - start");
		}

		BASE64Encoder endecoder = new BASE64Encoder();
		String returnString = endecoder.encode(bt);
		if (log.isDebugEnabled()) {
			log.debug("byteToBase64(byte[]) - end");
		}
		return returnString;
	}
}
