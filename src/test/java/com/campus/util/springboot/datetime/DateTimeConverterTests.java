package com.campus.util.springboot.datetime;

import com.eggcampus.util.result.ReturnResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

/**
 * @author 黄磊
 */
@SuppressWarnings("DataFlowIssue")
@EnableDateTimeConverter
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("autoTest")
public class DateTimeConverterTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("使用param传递日期时间")
    void test1() {
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/datetime/converter1?param1=2024-02-08 11:11:11", ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo("2024-02-08 11:11:11");
    }

    @Test
    @DisplayName("使用param传递日期")
    void test2() {
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/datetime/converter2?param1=2024-02-08", ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo("2024-02-08");
    }

    @Test
    @DisplayName("使用param传递时间")
    void test3() {
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/datetime/converter3?param1=11:11:11", ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo("11:11:11");
    }

    @Test
    @DisplayName("application/json通过DTO传递日期时间")
    void test5() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"2024-02-08 11:11:11\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/datetime/converter5", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "2024-02-08 11:11:11"));
    }

    @Test
    @DisplayName("application/json通过DTO传递日期时间")
    void test6() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"2024-02-08\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/datetime/converter6", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "2024-02-08"));
    }

    @Test
    @DisplayName("application/json通过DTO传递时间")
    void test7() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"11:11:11\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/datetime/converter7", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "11:11:11"));
    }


    @Test
    @DisplayName("application/x-www-form-urlencoded传递日期时间")
    void test8() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("param1=2024-02-08 11:11:11", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/datetime/converter8", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "2024-02-08 11:11:11"));
    }

    @Test
    @DisplayName("application/x-www-form-urlencoded传递日期")
    void test9() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("param1=2024-02-08", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/datetime/converter9", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "2024-02-08"));
    }

    @Test
    @DisplayName("application/x-www-form-urlencoded传递时间")
    void test10() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("param1=11:11:11", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/datetime/converter10", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "11:11:11"));
    }
}
