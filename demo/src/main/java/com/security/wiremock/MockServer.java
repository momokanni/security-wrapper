package com.security.wiremock;

import java.io.IOException;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

/**
 * 伪造rest服务
 * @author sca
 *
 */
public class MockServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		WireMock.configureFor("http://wm.limit-tech.com", 9090);
		// 清空之前配置
		WireMock.removeAllMappings();
		
		mock("order/1","order_01");
		mock("order/2","order_02");
	}

	private static void mock(String url, String filePath) throws IOException {
		
		ClassPathResource cpr = new ClassPathResource("wireMock/response/" + filePath + ".txt");
		String content = StringUtils.join(FileUtils.readLines(cpr.getFile(),"UTF-8").toArray(),"\n");
		WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/resource")).willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
	}

}
