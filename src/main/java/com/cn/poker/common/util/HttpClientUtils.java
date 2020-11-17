package com.cn.poker.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Map.Entry;

/**
 * ClassName: HttpClientUtils <br/>
 * Function: httpClient工具类. <br/>
 * date: 2018年10月25日 上午10:35:46 <br/>
 *
 * @author fate
 * @version
 * @since JDK 1.7
 */
@Slf4j
public class HttpClientUtils {

	// 编码格式。发送编码格式统一用UTF-8
	private static final String	ENCODING		= "UTF-8";

	// 设置连接超时时间，单位毫秒。
	private static final int	CONNECT_TIMEOUT	= 6000;

	// 请求获取数据的超时时间(即响应时间)，单位毫秒。
	private static final int	SOCKET_TIMEOUT	= 6000;

	/**
	 * 发送get请求；不带请求头和请求参数
	 *
	 * @param url
	 *            请求地址
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url) throws Exception {
		return doGet( url, null, null );
	}

	/**
	 * 发送get请求；带请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, String> params) throws Exception {
		return doGet( url, null, params );
	}

	/**
	 * 发送get请求；带请求头和请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头集合
	 * @param params
	 *            请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建访问的地址
		URIBuilder uriBuilder = new URIBuilder( url );
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				uriBuilder.setParameter( entry.getKey(), entry.getValue() );
			}
		}

		// 创建http对象
		HttpGet httpGet = new HttpGet( uriBuilder.build() );
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。
		 * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
		 * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。
		 * 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout( CONNECT_TIMEOUT ).setSocketTimeout( SOCKET_TIMEOUT ).build();
		httpGet.setConfig( requestConfig );

		// 设置请求头
		packageHeader( headers, httpGet );

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult( httpResponse, httpClient, httpGet );
		} finally {
			// 释放资源
			release( httpResponse, httpClient );
		}
	}

	/**
	 * 发送post请求；不带请求头和请求参数
	 *
	 * @param url
	 *            请求地址
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url) throws Exception {
		return doPost( url, null, null );
	}

	/**
	 * 发送post请求；带请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, String> params) throws Exception {
		return doPost( url, null, params );
	}
	public static String doPost1(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		//httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			try {
				response = httpclient.execute(httpPost);
			} catch (HttpHostConnectException e) {
//                response = httpclient.execute(httpPost);
				e.printStackTrace();
			}
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return jsonString;
			}
			else{
				log.error("请求返回:"+state+"("+url+")");
			}
		}
		finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	/*
	 * 请求PHP接口
	 * @param pathUrl 接口地址
	 * @param params 请求参数
	 * @return httpUrlConnection
	 */
	public static String httpUrlConnection(String pathUrl, String params) {
		try {
			// 建立连接
			URL url = new URL(pathUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();


			// //设置连接属性
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod("POST");// 设置URL请求方法

			// 设置请求属性
			// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
			byte[] requestStringBytes = params.getBytes("UTF-8");
			httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpConn.setRequestProperty("Charset", "UTF-8");
			//
			// 建立输出流，并写入数据
			OutputStream outputStream = httpConn.getOutputStream();
			outputStream.write(requestStringBytes);
			outputStream.close();
			// 获得响应状态
			int responseCode = httpConn.getResponseCode();


			if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
				// 当正确响应时处理数据
				StringBuffer sb = new StringBuffer();
				String readLine;
				BufferedReader responseReader;
				// 处理响应流，必须与服务器响应流输出的编码一致
				responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				System.out.println(sb.toString());
				return sb.toString();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/*public static String sendxwwwform(String url, Map<String,String> map,String encoding) throws Exception, IOException{
		String body = "";
		//采用绕过验证的方式处理https请求
		SSLContext sslcontext = createIgnoreVerifySSL();

		// 设置协议http和https对应的处理socket链接工厂的对象
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext))
				.build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		HttpClients.custom().setConnectionManager(connManager);

		//创建自定义的httpclient对象
		CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();

		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		//装填参数
		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		if(map!=null){
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		//设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		System.out.println("请求地址："+url);
		System.out.println("请求参数："+nvps.toString());

		//设置header信息
		//指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		// httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}
*/


	/**
	 * 发送post请求；带请求头和请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头集合
	 * @param params
	 *            请求参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		// 创建httpClient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 创建http对象
		HttpPost httpPost = new HttpPost( url );
		/**
		 * setConnectTimeout：设置连接超时时间，单位毫秒。
		 * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
		 * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
		 * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。
		 * 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
		 */
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout( CONNECT_TIMEOUT ).setSocketTimeout( SOCKET_TIMEOUT ).build();
		httpPost.setConfig( requestConfig );
		// 设置请求头
		/*
		 * httpPost.setHeader("Cookie", ""); httpPost.setHeader("Connection",
		 * "keep-alive"); httpPost.setHeader("Accept", "application/json");
		 * httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
		 * httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
		 * httpPost.setHeader("User-Agent",
		 * "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
		 * );
		 */
		packageHeader( headers, httpPost );

		// 封装请求参数
		packageParam( params, httpPost );

		// 创建httpResponse对象
		CloseableHttpResponse httpResponse = null;

		try {
			// 执行请求并获得响应结果
			return getHttpClientResult( httpResponse, httpClient, httpPost );
		} finally {
			// 释放资源
			release( httpResponse, httpClient );
		}
	}

	/**
	 * 发送put请求；不带请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url) throws Exception {
		return doPut( url );
	}

	/**
	 * 发送put请求；带请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doPut(String url, Map<String, String> params) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut( url );
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout( CONNECT_TIMEOUT ).setSocketTimeout( SOCKET_TIMEOUT ).build();
		httpPut.setConfig( requestConfig );

		packageParam( params, httpPut );

		CloseableHttpResponse httpResponse = null;

		try {
			return getHttpClientResult( httpResponse, httpClient, httpPut );
		} finally {
			release( httpResponse, httpClient );
		}
	}

	/**
	 * 发送delete请求；不带请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete( url );
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout( CONNECT_TIMEOUT ).setSocketTimeout( SOCKET_TIMEOUT ).build();
		httpDelete.setConfig( requestConfig );

		CloseableHttpResponse httpResponse = null;
		try {
			return getHttpClientResult( httpResponse, httpClient, httpDelete );
		} finally {
			release( httpResponse, httpClient );
		}
	}

	/**
	 * 发送delete请求；带请求参数
	 *
	 * @param url
	 *            请求地址
	 * @param params
	 *            参数集合
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult doDelete(String url, Map<String, String> params) throws Exception {
		if (params == null) {
			params = new HashMap<String, String>();
		}

		params.put( "_method", "delete" );
		return doPost( url, params );
	}

	/**
	 * Description: 封装请求头
	 *
	 * @param params
	 * @param httpMethod
	 */
	public static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
		// 封装请求头
		if (params != null) {
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				// 设置到请求头到HttpRequestBase对象中
				httpMethod.setHeader( entry.getKey(), entry.getValue() );
			}
		}
	}

	/**
	 * Description: 封装请求参数
	 *
	 * @param params
	 * @param httpMethod
	 * @throws UnsupportedEncodingException
	 */
	public static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod) throws UnsupportedEncodingException {
		// 封装请求参数
		if (params != null) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Set<Entry<String, String>> entrySet = params.entrySet();
			for (Entry<String, String> entry : entrySet) {
				nvps.add( new BasicNameValuePair( entry.getKey(), entry.getValue() ) );
			}

			// 设置到请求的http对象中
			httpMethod.setEntity( new UrlEncodedFormEntity( nvps, ENCODING ) );
		}
	}

	/**
	 * Description: 获得响应结果
	 *
	 * @param httpResponse
	 * @param httpClient
	 * @param httpMethod
	 * @return
	 * @throws Exception
	 */
	public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient, HttpRequestBase httpMethod)
			throws Exception {
		// 执行请求
		httpResponse = httpClient.execute( httpMethod );

		// 获取返回结果
		if (httpResponse != null && httpResponse.getStatusLine() != null) {
			String content = "";
			if (httpResponse.getEntity() != null) {
				content = EntityUtils.toString( httpResponse.getEntity(), ENCODING );
			}
			return new HttpClientResult( httpResponse.getStatusLine().getStatusCode(), content );
		}
		return new HttpClientResult( HttpStatus.SC_INTERNAL_SERVER_ERROR );
	}

	/**
	 * Description: 释放资源
	 *
	 * @param httpResponse
	 * @param httpClient
	 * @throws IOException
	 */
	public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws IOException {
		// 释放资源
		if (httpResponse != null) {
			httpResponse.close();
		}
		if (httpClient != null) {
			httpClient.close();
		}
	}
	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) throws Exception {
		String result = "";
		BufferedReader in = null;

		String urlNameString = url + "?" + param;
		URL realUrl = new URL(urlNameString);
		// 打开和URL之间的连接
		URLConnection connection = realUrl.openConnection();

		// 设置通用的请求属性
		connection.setRequestProperty("accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent",
				"Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

		// 建立实际的连接
		connection.connect();
		// 获取所有响应头字段
		Map<String, List<String>> map = connection.getHeaderFields();
		// 遍历所有的响应头字段
		for (String key : map.keySet()) {
			// log.warn(key + "--->" + map.get(key));
		}
		// 定义 BufferedReader输入流来读取URL的响应
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}

		try {
			if (in != null) {
				in.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public static String Connect(String address){
		HttpURLConnection conn = null;
		URL url = null;
		InputStream in = null;
		BufferedReader reader = null;
		StringBuffer stringBuffer = null;
		try {
			url = new URL(address);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setDoInput(true);
			conn.connect();
			in = conn.getInputStream();
			// reader = new BufferedReader(new InputStreamReader(in),"utf-8");
			reader = new BufferedReader(new InputStreamReader(in,"utf-8"));


			stringBuffer = new StringBuffer();
			String line = null;
			while((line = reader.readLine()) != null){
				stringBuffer.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			conn.disconnect();
			try {
				in.close();
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(stringBuffer != null){
			return stringBuffer.toString();
		}else{
			return "";
		}


	}

	/**
	 * 发送get请求
	 * @param url 路径
	 * @return
	 */
	public static JSONArray get(String url){
		// get请求返回结果
		JSONArray jsonResult = null;
		CloseableHttpClient client = HttpClients.createDefault();
		// 发送get请求
		HttpGet request = new HttpGet(url);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout( CONNECT_TIMEOUT ).setSocketTimeout( SOCKET_TIMEOUT ).build();
		request.setConfig(requestConfig);

		try{
			CloseableHttpResponse response = client.execute(request);

			// 请求发送成功，并得到响应
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				// 读取服务器返回过来的json字符串数据
				HttpEntity entity = response.getEntity();
				String strResult = EntityUtils.toString(entity, "utf-8");
				// 把json字符串转换成json对象
				jsonResult = JSONArray.parseArray(strResult);
			}else{
				get( url);
			}
		}
		catch (IOException e){
			get( url);
			e.printStackTrace();
		}finally{
			request.releaseConnection();
		}
		return jsonResult;
	}

	public static boolean doPost(String url, String params) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost
		httpPost.setHeader("Accept", "application/x-www-form-urlencoded");
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);
		CloseableHttpResponse response = null;
		try {
			try {
				response = httpclient.execute(httpPost);
			} catch (HttpHostConnectException e) {
//                response = httpclient.execute(httpPost);
				e.printStackTrace();
			}
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			if (state == HttpStatus.SC_MOVED_TEMPORARILY) {
				HttpEntity responseEntity = response.getEntity();
				String jsonString = EntityUtils.toString(responseEntity);
				return true;
			}
			else{
				log.error("请求返回:"+state+"("+url+")");
			}
		}
		finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static String doPost2(String url) throws IOException {
		HttpPost httpPost = new HttpPost(url);
//2.1 额外设置Content-Type请求头
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		//4、如果有参数添加参数
		CloseableHttpClient client = HttpClients.createDefault();
		httpPost.setEntity(new StringEntity("log=zxl&pwd=123456", "UTF-8"));
		//5、点击发送按钮，发送请求  6、获取响应报文
		CloseableHttpResponse response = client.execute(httpPost);
		//7、格式化响应报文
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity);
//8、在控制台输出报文
		System.out.println(result);
		return result;
	}
}