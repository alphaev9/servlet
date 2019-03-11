package com.alpha.filters;


import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

/**
 * @author alpha
 * @date 2019/3/6
 */
@WebFilter(filterName = "CryptoFilter")
public class CryptoFilter implements Filter {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String servletPath = request.getServletPath();
        if ("/index.jsp".equals(servletPath)) {
            KeyPairGenerator generator = null;
            try {
                generator = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            generator.initialize(1024);
            KeyPair keyPair = generator.generateKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            byte[] encode = Base64.getEncoder().encode(publicKey.getEncoded());

            String s = new String(encode);
            request.setAttribute("publicKey", s);
        } else {
            Cipher rsa;
            try {
                rsa = Cipher.getInstance("RSA");
                rsa.init(Cipher.DECRYPT_MODE, privateKey);
                String nickName = request.getParameter("nickName");
                System.out.println("nickName: "+nickName);
                byte[] encryptedData = Base64.getDecoder().decode(nickName);

                String s1 = new String(encryptedData, "iso8859-1");

                System.out.println("result:  "+s1);

                int inputLen = encryptedData.length;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int offSet = 0;
                byte[] cache;
                int i = 0;
                // 对数据分段解密
                while (inputLen - offSet > 0) {
                    if (inputLen - offSet > 128) {
                        cache = rsa.doFinal(encryptedData, offSet, 128);
                    } else {
                        cache = rsa.doFinal(encryptedData, offSet, inputLen - offSet);
                    }
                    out.write(cache, 0, cache.length);
                    i++;
                    offSet = i * 128;
                }
                byte[] decryptedData = out.toByteArray();
                out.close();

                String s = new String(decryptedData);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}
