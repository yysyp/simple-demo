package ps.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;


@Slf4j
public class MyRestTemplateUtil {

    private RestTemplate restTemplate;

    private static MyRestTemplateUtil instance;

    private MyRestTemplateUtil() {
        init();
    }

    // Double check...
    public static MyRestTemplateUtil getInstance() {
        if (instance == null) {
            synchronized (MyRestTemplateUtil.class) {
                if (instance == null) {
                    instance = new MyRestTemplateUtil();
                }
            }
        }
        return instance;
    }

    private void init() {
        org.apache.http.conn.ssl.TrustStrategy acceptTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        try {
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptTrustStrategy)
                    .build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslConnectionSocketFactory)
                    .build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            this.restTemplate = new RestTemplate(requestFactory);
        } catch (Exception e) {
           throw new RuntimeException("RestTemplate initialization error!", e);
        }
    }



    public String submitFormForString(String url, MultiValueMap<String, String> formMap) {
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        return submitFormForObject(url, formMap, responseType).getBody();
    }

    public <T> ResponseEntity<T> submitFormForObject(String url, MultiValueMap<String, String> formMap, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        return submitFormForObject(url, headers, formMap, responseType);
    }

    public <T> ResponseEntity<T> submitFormForObject(String url, HttpHeaders headers, MultiValueMap<String, String> formMap, ParameterizedTypeReference<T> responseType) {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formMap, headers);
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, responseType);
            return responseEntity;
        } catch (Exception e) {
            log.info("--->>Rest call submitFormForObject error, url={}, message={}", url, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String postJsonObjectForString(String url, String jsonStr) {
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        return postJsonObjectForObject(url, jsonStr, responseType).getBody();
    }

    public <T> ResponseEntity<T> postJsonObjectForObject(String url, String jsonStr, ParameterizedTypeReference<T> responseType) {
        HttpHeaders headers = new HttpHeaders();
        return postJsonObjectForObject(url, headers, jsonStr, responseType);
    }

    public <T> ResponseEntity<T> postJsonObjectForObject(String url, HttpHeaders headers, String jsonStr, ParameterizedTypeReference<T> responseType) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(jsonStr, headers);
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, responseType);
            return responseEntity;
        } catch (Exception e) {
            log.info("--->>Rest call postJsonObjectForObject error, url={}, message={}", url, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String getForString(String url, Object... uriVariables) {
        ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
        };
        return getForObject(url, responseType, uriVariables).getBody();
    }

    public <T> ResponseEntity<T> getForObject(String url, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        return getForObject(url, headers, responseType, uriVariables);
    }

    /**
     * NOTE!!! Current RestTemplate settings has conflict with this get method, if you use
     * "RestTemplate restTemplate = new RestTemplate();" to set the "restTemplate" attribute, it will work.
     * otherwise it will throw:
     * Caused by: java.lang.UnsupportedOperationException: getBody not supported
     * 	at org.springframework.http.client.HttpComponentsStreamingClientHttpRequest.getBodyInternal(HttpComponentsStreamingClientHttpRequest.java:86)
     * 	at org.springframework.http.client.AbstractClientHttpRequest.getBody(AbstractClientHttpRequest.java:47)
     * 	at org.springframework.http.client.BufferingClientHttpRequestWrapper.executeInternal(BufferingClientHttpRequestWrapper.java:62)
     * 	at org.springframework.http.client.AbstractBufferingClientHttpRequest.executeInternal(AbstractBufferingClientHttpRequest.java:48)
     * 	at org.springframework.http.client.AbstractClientHttpRequest.execute(AbstractClientHttpRequest.java:53)
     * 	at org.springframework.http.client.InterceptingClientHttpRequest$InterceptingRequestExecution.execute(InterceptingClientHttpRequest.java:109)
     * 	at p.y.demo.restconfig.LogClientHttpRequestInterceptor.intercept(LogClientHttpRequestInterceptor.java:28)
     * 	at org.springframework.http.client.InterceptingClientHttpRequest$InterceptingRequestExecution.execute(InterceptingClientHttpRequest.java:93)
     * 	at org.springframework.http.client.InterceptingClientHttpRequest.executeInternal(InterceptingClientHttpRequest.java:77)
     * 	at org.springframework.http.client.AbstractBufferingClientHttpRequest.executeInternal(AbstractBufferingClientHttpRequest.java:48)
     * 	at org.springframework.http.client.AbstractClientHttpRequest.execute(AbstractClientHttpRequest.java:53)
     * 	at org.springframework.web.client.RestTemplate.doExecute(RestTemplate.java:739)
     * 	at org.springframework.web.client.RestTemplate.execute(RestTemplate.java:674)
     * 	at org.springframework.web.client.RestTemplate.exchange(RestTemplate.java:612)
     * 	at p.y.demo.restconfig.RestTemplateUtil.getForObject(RestTemplateUtil.java:85)
     * @param url
     * @param headers
     * @param responseType
     * @param uriVariables
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> getForObject(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType, Object... uriVariables) {
        HttpEntity<String> request = new HttpEntity<>(headers);
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    responseType, uriVariables
            );
            return responseEntity;
        } catch (Exception e) {
            log.info("--->>Rest call getForObject error, url={}, message={}", url, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }



}
