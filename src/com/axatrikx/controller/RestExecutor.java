/**
 * 
 */
package com.axatrikx.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class RestExecutor {

	private HttpClient client;
	private String url;

	/**
	 * Constructor for RestExecutor
	 * 
	 * @param url
	 */
	public RestExecutor(String url) {
		client = HttpClientBuilder.create().build();
		this.url = url;
	}

	public RestValidator get(String path) {
		return get(path, null);
	}

	/**
	 * Executes GET req and returns response json.
	 * 
	 * @param path
	 * @param headers
	 * @return
	 */
	public RestValidator get(String path, HashMap<String, String> headers) {
		HttpGet request = new HttpGet(url + path);
		HttpResponse response;
		/*
		 * The response object which holds the details of the response.
		 */
		RestResponse resResponse = new RestResponse();
		StringBuffer responseString = new StringBuffer();
		try {
			/*
			 * Setting the headers for the request
			 */
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					request.addHeader(key, headers.get(key));
				}
			}
			/* 
			 * Executing the GET operation
			 */
			response = client.execute(request);
			
			/*
			 * Obtaining the response body from the response stream.
			 */
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			/*
			 * Setting values for the response object
			 */
			resResponse.setResponseBody(responseString.toString());
			resResponse.setResponseCode(response.getStatusLine().getStatusCode());
			resResponse.setResponseMessage(response.getStatusLine().getReasonPhrase());
			Header[] rheaders = response.getAllHeaders();
			for (Header header : rheaders) {
				resResponse.setHeader(header.getName(), header.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * Returns the RestValidator object providing the response object
		 */
		return new RestValidator(resResponse);
	}

	public RestValidator post(String path, String xmlContent, String contentType) {
		return post(path, null, xmlContent, contentType);
	}

	/**
	 * Executes POST req and returns response json.
	 * 
	 * @param path
	 * @param headers
	 * @return
	 */
	public RestValidator post(String path, HashMap<String, String> headers, String xmlContent, String contentType) {
		HttpPost post = new HttpPost(url + path);
		RestResponse resResponse = new RestResponse();
		StringBuffer responseString = new StringBuffer();
		try {
			if (headers != null)
				post.setEntity(getEntities(headers));

			/*
			 * Setting the xml content and content type.
			 */
			StringEntity input = new StringEntity(xmlContent);
			input.setContentType(contentType);
			post.setEntity(input);

			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			resResponse.setResponseBody(responseString.toString());
			resResponse.setResponseCode(response.getStatusLine().getStatusCode());
			resResponse.setResponseMessage(response.getStatusLine().getReasonPhrase());
			Header[] rheaders = response.getAllHeaders();
			for (Header header : rheaders) {
				resResponse.setHeader(header.getName(), header.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace(); // handle
		}
		return new RestValidator(resResponse);
	}

	public RestValidator delete(String path) {
		return delete(path, null);
	}

	/**
	 * Executes DELETE req and returns response json.
	 * 
	 * @param path
	 * @param headers
	 * @return
	 */
	public RestValidator delete(String path, HashMap<String, String> headers) {
		HttpDelete delete = new HttpDelete(url + path);
		RestResponse resResponse = new RestResponse();
		StringBuffer responseString = new StringBuffer();
		try {
			if (headers != null) {
				Set<String> keys = headers.keySet();
				for (String key : keys) {
					delete.addHeader(key, headers.get(key));
				}
			}
			HttpResponse response = client.execute(delete);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			resResponse.setResponseBody(responseString.toString());
			resResponse.setResponseCode(response.getStatusLine().getStatusCode());
			resResponse.setResponseMessage(response.getStatusLine().getReasonPhrase());
			Header[] rheaders = response.getAllHeaders();
			for (Header header : rheaders) {
				resResponse.setHeader(header.getName(), header.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RestValidator(resResponse);
	}
	
	public RestValidator put(String path, String xmlContent, String contentType) {
		return put(path,null,xmlContent,contentType);
	}

	/**
	 * Executes PUT req and returns response json.
	 * 
	 * @param path
	 * @param headers
	 * @param xmlContent
	 * @param contentType
	 * @return
	 */
	public RestValidator put(String path, HashMap<String, String> headers, String xmlContent, String contentType) {
		HttpPut post = new HttpPut(url + path);
		RestResponse resResponse = new RestResponse();
		StringBuffer responseString = new StringBuffer();
		try {
			if (headers != null)
				post.setEntity(getEntities(headers));

			StringEntity input = new StringEntity(xmlContent);
			input.setContentType(contentType);
			post.setEntity(input);

			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			resResponse.setResponseBody(responseString.toString());
			resResponse.setResponseCode(response.getStatusLine().getStatusCode());
			resResponse.setResponseMessage(response.getStatusLine().getReasonPhrase());
			Header[] rheaders = response.getAllHeaders();
			for (Header header : rheaders) {
				resResponse.setHeader(header.getName(), header.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace(); // handle
		}
		return new RestValidator(resResponse);
	}
	
	public RestValidator patch(String path, String xmlContent, String contentType) {
		return patch(path,null,xmlContent,contentType);
	}

	/**
	 * Executes PATCH req and returns response json.
	 * 
	 * @param path
	 * @param headers
	 * @param xmlContent
	 * @param contentType
	 * @return
	 */
	public RestValidator patch(String path, HashMap<String, String> headers, String xmlContent, String contentType) {
		HttpPatch post = new HttpPatch(url + path);
		RestResponse resResponse = new RestResponse();
		StringBuffer responseString = new StringBuffer();
		try {
			if (headers != null)
				post.setEntity(getEntities(headers));

			StringEntity input = new StringEntity(xmlContent);
			input.setContentType(contentType);
			post.setEntity(input);

			HttpResponse response = client.execute(post);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseString.append(line);
			}
			resResponse.setResponseBody(responseString.toString());
			resResponse.setResponseCode(response.getStatusLine().getStatusCode());
			resResponse.setResponseMessage(response.getStatusLine().getReasonPhrase());
			Header[] rheaders = response.getAllHeaders();
			for (Header header : rheaders) {
				resResponse.setHeader(header.getName(), header.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace(); // handle
		}
		return new RestValidator(resResponse);
	}

	/**
	 * Gets the hashmap turns it in HttpEntity nameValuePair.
	 * 
	 * @param inputEntities
	 * @return
	 */
	private HttpEntity getEntities(HashMap<String, String> inputEntities) {
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(inputEntities.size());
		Set<String> keys = inputEntities.keySet();
		for (String key : keys) {
			nameValuePairs.add(new BasicNameValuePair(key, inputEntities.get(key)));
		}
		try {
			return new UrlEncodedFormEntity(nameValuePairs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
