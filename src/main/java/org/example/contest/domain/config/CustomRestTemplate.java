package org.example.contest.domain.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

public class CustomRestTemplate {

    public static RestTemplate createRestTemplate() {
        try {
            // TLS를 사용하여 SSLContext 생성
            SSLContext sslContext = SSLContextBuilder.create()
                    .setProtocol("TLSv1.2")  // 사용할 TLS 버전 설정
                    .build();

            // SSLConnectionSocketFactory 생성
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext);

            // SSLConnectionSocketFactory를 레지스트리에 등록
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslSocketFactory)  // "https" 프로토콜에 SSL 소켓 팩토리 등록
                    .build();

            // 커넥션 매니저 생성 및 설정
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

            // 커넥션 매니저를 사용하여 HttpClient 생성
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .build();

            // HttpClient를 사용하여 RestTemplate 생성
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
            return new RestTemplate(factory);

        } catch (Exception e) {
            throw new RuntimeException("커스텀 SSL 컨텍스트로 RestTemplate 생성에 실패했습니다.", e);
        }
    }
}
