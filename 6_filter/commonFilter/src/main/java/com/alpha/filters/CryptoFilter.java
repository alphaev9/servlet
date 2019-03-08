package com.alpha.filters;

import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author alpha
 * @date 2019/3/6
 */
@WebFilter(filterName = "CryptoFilter")
public class CryptoFilter implements Filter {
    private final String privateKey = "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEA8LDlubYoYFA3akUf\n" +
            "ju78AxjATzUyGWVOr6/7Zhsn9dbzAL0EK0kC0i5DCzqwVPk8l9G5Aik7jpPN7Xf1\n" +
            "dCe3nwIDAQABAkEAgS/xncRP16I2nCuNzMIpMeBPGDSNN2RQ36utXBinUiOKwiNY\n" +
            "mF5Yr5ZD+s0qKgQnm6bAZZ80lyfM1xUE/ucvwQIhAPkXfFLP0EJrTnddvEA6y0Xz\n" +
            "5T5pqp48domsu6Shf9DLAiEA913Ei22WFmj6UZRVeNdpZVIia7elX7v8v/tScHeC\n" +
            "Pf0CIQCBClCmme/aqLpyjgW7BHpP1+TXq2fvJ4WFFw3yYJPVdwIhAKOu0m2IeDYg\n" +
            "BPOSefRaZg17ifdJMA7nL9pBjU2KZw5pAiEA5TA+tu+7aUMMFCFwOkGkTgXGXzr3\n" +
            "u+7FfUvogiscf34=";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            Cipher rsa = Cipher.getInstance("RSA");
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buffer = decoder.decodeBuffer(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory rsa1 = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = rsa1.generatePrivate(keySpec);
            rsa.init(Cipher.DECRYPT_MODE, privateKey);
            String nickName = request.getParameter("nickName");
            byte[] bytes = rsa.doFinal(nickName.getBytes());
            String s = new String(bytes);
            System.out.println(s);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
