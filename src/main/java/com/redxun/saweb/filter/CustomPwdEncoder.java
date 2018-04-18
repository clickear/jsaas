package com.redxun.saweb.filter;

import org.apache.commons.codec.DecoderException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.redxun.core.util.EncryptUtil;

/**
 * 自定义密码加密配置。
 * <pre>
 * 配置在spring-security.xml
 * 
 * &lt;security:authentication-manager alias="authenticationManager">
        &lt;security:authentication-provider user-service-ref="userDetailsProvider" >
        	&lt;security:password-encoder ref="customPwdEncoder">&lt;/security:password-encoder>
        &lt;/security:authentication-provider>
    &lt;/security:authentication-manager>
 * </pre>
 * @author redxun
 *
 */
public class CustomPwdEncoder implements PasswordEncoder {
	
	private static ThreadLocal<Boolean> ingorePwd=new ThreadLocal<Boolean>();
	
	public static void setIngore(boolean ingore){
		ingorePwd.set(ingore);
	}
	
	/**
	 * 密码加密类型  SHA256,MD5,NONE
	 */
	private String encType="SHA256";
	

	public String getEncType() {
		return encType;
	}

	public void setEncType(String encType) {
		this.encType = encType;
	}

	/**
     * Encode the raw password.
     * Generally, a good encoding algorithm applies a SHA-1 or greater hash combined with an 8-byte or greater randomly
     * generated salt.
     */
    public String encode(CharSequence rawPassword){
    	String pwd="";
		try {
			if("SHA256".equals(encType)){
				pwd = EncryptUtil.hexToBase64((String)rawPassword);
			}else if("MD5".equals(encType)){
				pwd = EncryptUtil.encryptMd5((String)rawPassword);
			}else if("MD5HEX".equals(encType)){
				pwd = EncryptUtil.encryptMd5Hex((String)rawPassword);
			}else{
				pwd=(String)rawPassword;
			}
		} catch (DecoderException e) {
			e.printStackTrace();
		}
    	return pwd;
	}

    /**
     * Verify the encoded password obtained from storage matches the submitted raw password after it too is encoded.
     * Returns true if the passwords match, false if they do not.
     * The stored password itself is never decoded.
     *
     * @param rawPassword the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from storage
     */
    public  boolean matches(CharSequence rawPassword, String encodedPassword){
    	if(ingorePwd.get()==null || ingorePwd.get()==false){
    		String enc=this.encode(rawPassword);
        	return enc.equals(encodedPassword);
    	}
    	return true;
    }

}
