package com.proxy.pay.proxy_pay.weixin.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * 公众平台通用接口工具类
 * @author userli
 * @date 2017年9月12日 下午3:09:13  
 */
public class HttpClientUtil {
	
	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";
	public static String CHARTSET = "UTF-8";
	public static String sendGET(String url, String param) {
		String result = "";// 访问返回结果
		BufferedReader read = null;// 读取访问结果
		try {
			// 创建url
			URL realurl = new URL(url + "?" + param);
			// 打开连接
			URLConnection connection = realurl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立连接
			connection.connect();
			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段，获取到cookies等
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }
			// 定义 BufferedReader输入流来读取URL的响应
			read = new BufferedReader(new InputStreamReader(connection.getInputStream(), CHARTSET));
			String line;// 循环读取
			while ((line = read.readLine()) != null) {
				result += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (read != null) {// 关闭流
				try {
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}
	
	/**
	 * get HttpURLConnection
	 * @param strUrlַ
	 * @return HttpURLConnection
	 * @throws IOException
	 */
	public static HttpURLConnection getHttpURLConnection(String strUrl)
			throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		return httpURLConnection;
	}
	
	/**
	 * get HttpsURLConnection
	 * @param strUr ַ
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	public static HttpsURLConnection getHttpsURLConnection(String strUrl)
			throws IOException {
		URL url = new URL(strUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url
				.openConnection();
		return httpsURLConnection;
	}
	
	/**
	 * getURL
	 * @param strUrl
	 * @return String
	 */
	public static String getURL(String strUrl) {

		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(0, indexOf);
			} 
			
			return strUrl;
		}
		
		return strUrl;
		
	}
	
	/**
	 * getQueryString
	 * @param strUrl
	 * @return String
	 */
	public static String getQueryString(String strUrl) {
		
		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(indexOf+1, strUrl.length());
			} 
			
			return "";
		}
		
		return strUrl;
	}
	
	/**
	 * queryString2Map
	 * name1=key1&name2=key2&...
	 * @param queryString
	 * @return
	 */
	public static Map queryString2Map(String queryString) {
		if(null == queryString || "".equals(queryString)) {
			return null;
		}
		
		Map m = new HashMap();
		String[] strArray = queryString.split("&");
		for(int index = 0; index < strArray.length; index++) {
			String pair = strArray[index];
			HttpClientUtil.putMapByPair(pair, m);
		}
		
		return m;
		
	}
	
	/**
	 * putMapByPair
	 * pair:name=value
	 * @param pair name=value
	 * @param m
	 */
	public static void putMapByPair(String pair, Map m) {
		
		if(null == pair || "".equals(pair)) {
			return;
		}
		
		int indexOf = pair.indexOf("=");
		if(-1 != indexOf) {
			String k = pair.substring(0, indexOf);
			String v = pair.substring(indexOf+1, pair.length());
			if(null != k && !"".equals(k)) {
				m.put(k, v);
			}
		} else {
			m.put(pair, "");
		}
	}
	
	/**
	 * bufferedReader2String
	 * ע��:���ر���Ҫ���д���
	 * @param reader
	 * @return String
	 * @throws IOException
	 */
	public static String bufferedReader2String(BufferedReader reader) throws IOException {
		StringBuffer buf = new StringBuffer();
		String line = null;
		while( (line = reader.readLine()) != null) {
			buf.append(line);
			buf.append("\r\n");
		}
				
		return buf.toString();
	}
	
	/**
	 * doOutput
	 * @param out
	 * @param data
	 * @param len
	 * @throws IOException
	 */
	public static void doOutput(OutputStream out, byte[] data, int len)
			throws IOException {
		int dataLen = data.length;
		int off = 0;
		while (off < data.length) {
			if (len >= dataLen) {
				out.write(data, off, dataLen);
				off += dataLen;
			} else {
				out.write(data, off, len);
				off += len;
				dataLen -= len;
			}

			// ˢ�»�����
			out.flush();
		}

	}
	
	/**
	 * getSSLContext
	 * @param trustFile 
	 * @param trustPasswd
	 * @param keyFile
	 * @param keyPasswd
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws UnrecoverableKeyException 
	 * @throws KeyManagementException 
	 */
	public static SSLContext getSSLContext(
			FileInputStream trustFileInputStream, String trustPasswd,
			FileInputStream keyFileInputStream, String keyPasswd)
			throws NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException, UnrecoverableKeyException,
			KeyManagementException {

		// ca
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore trustKeyStore = KeyStore.getInstance(HttpClientUtil.JKS);
		trustKeyStore.load(trustFileInputStream, HttpClientUtil
				.str2CharArray(trustPasswd));
		tmf.init(trustKeyStore);

		final char[] kp = HttpClientUtil.str2CharArray(keyPasswd);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore ks = KeyStore.getInstance(HttpClientUtil.PKCS12);
		ks.load(keyFileInputStream, kp);
		kmf.init(ks, kp);

		SecureRandom rand = new SecureRandom();
		SSLContext ctx = SSLContext.getInstance(HttpClientUtil.TLS);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

		return ctx;
	}
	
	/**
	 * getCertificate
	 * @param cafile 
	 * @return Certificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static Certificate getCertificate(File cafile)
			throws CertificateException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cafile);
		Certificate cert = cf.generateCertificate(in);
		in.close();
		return cert;
	}
	
	/**
	 * str2CharArray
	 * @param str
	 * @return char[]
	 */
	public static char[] str2CharArray(String str) {
		if(null == str) return null;
		
		return str.toCharArray();
	}
	
	/**
	 * storeCACert
	 * @param cert
	 * @param alias
	 * @param password
	 * @param out
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static void storeCACert(Certificate cert, String alias,
			String password, OutputStream out) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance("JKS");

		ks.load(null, null);

		ks.setCertificateEntry(alias, cert);

		// store keystore
		ks.store(out, HttpClientUtil.str2CharArray(password));

	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
	
	/**
	 * InputStreamTOByte
	 * InputStream
	 * @param in
	 * @return byte
	 * @throws Exception
	 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException{  
		
		int BUFFER_SIZE = 4096;  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        byte[] outByte = outStream.toByteArray();
        outStream.close();
        
        return outByte;  
    } 
	
	/**
	 * InputStreamTOString
	 * InputStream
	 * @param in
	 * @param encoding ����
	 * @return String
	 * @throws Exception
	 */
	public static String InputStreamTOString(InputStream in,String encoding) throws IOException{  

        return new String(InputStreamTOByte(in),encoding);
        
    }
    

}
