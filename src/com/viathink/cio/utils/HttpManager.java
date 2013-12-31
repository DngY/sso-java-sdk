package com.viathink.cio.utils;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;


public class HttpManager {

	private static Log logger = LogFactory.getLog(HttpManager.class);
	
	public static final int CONNECTION_TIMEOUT = 5000;
    public static final int CON_TIME_OUT_MS = 5000;
    public static final int SO_TIME_OUT_MS = 5000;
    public static final int MAX_CONNECTIONS_PER_HOST = 20;
    public static final int MAX_TOTAL_CONNECTIONS = 200;
    
    private HttpClient httpClient;

    public HttpManager() {
    	this(MAX_CONNECTIONS_PER_HOST, MAX_TOTAL_CONNECTIONS, CON_TIME_OUT_MS, SO_TIME_OUT_MS,null,null);
    }

    /**
     * 个性化配置连接管理器
     * @param maxConnectionsPerHost 设置默认的连接到每个主机的最大连接数
     * @param maxTotalConnections 设置整个管理连接器的最大连接数
     * @param conTimeOutMs  连接超时
     * @param soTimeOutMs socket超时
     * @param routeCfgList 特殊路由配置列表，若无请填null
     * @param proxy 代理设置，若无请填null
     */
	public HttpManager(int maxConnectionsPerHost, int maxTotalConnections,
			int conTimeOutMs, int soTimeOutMs, Object object, Object object2) {
		
		 // 使用默认的 socket factories 注册 "http" & "https" protocol scheme
        SchemeRegistry supportedSchemes = new SchemeRegistry();
        supportedSchemes.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        supportedSchemes.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(supportedSchemes);

        // 参数设置
        HttpParams httpParams = new SyncBasicHttpParams();
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);

        httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, conTimeOutMs);
        httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeOutMs);
          
        HttpProtocolParams.setUseExpectContinue(httpParams, false);

        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerHost);
        connectionManager.setMaxTotal(maxTotalConnections);

        HttpClientParams.setCookiePolicy(httpParams, CookiePolicy.IGNORE_COOKIES);
        
       
        httpClient = new DefaultHttpClient(connectionManager, httpParams);
		
	}
	
	
	 /**
     * Get方法传送消息
     * 
     * @param url  连接的URL
     * @param queryString  请求参数串
     * @return 服务器返回的信息
     * @throws Exception
     */
    public String httpGet(String url, String queryString) throws Exception {

        String responseData = null;
        if (queryString != null && !queryString.equals("")) {
            url += "?" + queryString;
        }
        logger.info("HttpClient httpGet [1] url = " + url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));
        
        HttpResponse response;
        response = httpClient.execute(httpGet);

        try {
        	logger.info("HttpClient httpGet [2] StatusLine : " + response.getStatusLine());
            responseData = EntityUtils.toString(response.getEntity());
            logger.info("HttpClient httpGet [3] Response = " + responseData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.abort();
        }

        return responseData;
    }

    /**
     * Post方法传送消息
     * 
     * @param url  连接的URL
     * @param queryString 请求参数串
     * @return 服务器返回的信息
     * @throws Exception
     */
    public String httpPost(String url, String queryString) throws Exception {
        String responseData = null;
        URI tmpUri = new URI(url);
        URI uri = URIUtils.createURI(tmpUri.getScheme(), tmpUri.getHost(), tmpUri.getPort(), tmpUri.getPath(),
                queryString, null);
        
        logger.info("HttpManager httpPost [1] url = " + uri.toURL());

        HttpPost httpPost = new HttpPost(uri);
        httpPost.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));
        if (queryString != null && !queryString.equals("")) {
            StringEntity reqEntity = new StringEntity(queryString);
            // 设置类型
            reqEntity.setContentType("application/x-www-form-urlencoded");
            // 设置请求的数据
            httpPost.setEntity(reqEntity);
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            logger.info("HttpManager httpPost [2] StatusLine = " + response.getStatusLine());
            responseData = EntityUtils.toString(response.getEntity());
            logger.info("HttpManager httpPost [3] responseData = " + responseData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }

        return responseData;
    }

    public String httpRequest(String url, String accessToken) throws Exception {
    	String responseData = null;
    	HttpGet httpGet = new HttpGet(url);
        httpGet.getParams().setParameter("http.socket.timeout", new Integer(CONNECTION_TIMEOUT));
        httpGet.setHeader("Authorization", "Bearer " + accessToken);
        
        HttpResponse response = null;
        try {
        	response = httpClient.execute(httpGet);
        	logger.info("httpRequest StatusLine : " + response.getStatusLine());
            responseData = EntityUtils.toString(response.getEntity());
            logger.info("httpRequest Response = " + responseData);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpGet.abort();
        }

        return responseData;
    	
    }
	
}
