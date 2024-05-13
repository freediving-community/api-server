package com.freediving.buddyservice.adapter.out.externalservice.opendata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.xml.transform.StringSource;

import jakarta.annotation.PostConstruct;

@Service
public class HolidayService {

	private final RestTemplate restTemplate = new RestTemplate();
	private final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	private String cachedXml = "";

	@Value("${openapi.api.url.base}")
	private String apiUrlBase;

	@Value("${openapi.api.key}")
	private String apiKey;

	public HolidayService() {
		marshaller.setClassesToBeBound(Response.class);
	}

	@PostConstruct
	public void init() throws IOException {
		fetchHolidayData(); // 애플리케이션 시작 시 데이터 요청 및 캐싱
	}

	@Scheduled(cron = "0 0 * * * *") // 매시간 0분에 실행
	public void fetchHolidayData() throws IOException {
		LocalDate today = LocalDate.now();
		String year = today.format(DateTimeFormatter.ofPattern("yyyy"));
		String[] months = {
			today.format(DateTimeFormatter.ofPattern("MM")),
			today.plusMonths(1).format(DateTimeFormatter.ofPattern("MM")),
			today.plusMonths(2).format(DateTimeFormatter.ofPattern("MM"))
		};

		List<Response.Body.Item> allItems = new ArrayList<>();

		for (String month : months) {
			try {
				StringBuilder urlBuilder = new StringBuilder(
					"http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo"); /*URL*/
				urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKey); /*Service Key*/
				urlBuilder.append(
					"&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
				urlBuilder.append(
					"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("30",
						"UTF-8")); /*한 페이지 결과 수*/
				urlBuilder.append(
					"&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8")); /*연*/
				urlBuilder.append(
					"&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8")); /*월*/
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/json");
				System.out.println("Response code: " + conn.getResponseCode());
				BufferedReader rd;
				if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
					rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				conn.disconnect();
				String response = sb.toString();

				StringSource source = new StringSource(response);
				Response responseObject = (Response)marshaller.unmarshal(source);
				allItems.addAll(responseObject.getBody().getItems());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Response mergedResponse = new Response();
		Response.Body body = new Response.Body();
		body.setItems(allItems);
		mergedResponse.setBody(body);

		try {
			StringWriter writer = new StringWriter();
			marshaller.marshal(mergedResponse, new StreamResult(writer));
			cachedXml = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCachedXml() {
		return cachedXml;
	}
}
