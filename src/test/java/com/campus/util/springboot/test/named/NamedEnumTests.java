package com.campus.util.springboot.test.named;

import com.campus.util.springboot.enums.named.EnableEggCampusNamedEnum;
import com.campus.util.springboot.exception.EnableEggCampusExceptionHandler;
import com.eggcampus.util.result.AliErrorCode;
import com.eggcampus.util.result.ReturnResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;

import java.util.Map;

/**
 * @author 黄磊
 */
@SuppressWarnings("DataFlowIssue")
@EnableEggCampusNamedEnum
@EnableEggCampusExceptionHandler
@Import(NamedEnumController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NamedEnumTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("使用param传递命名枚举")
    void test1() {
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/enums/named1?param1=1-value1", ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo("1-value1");
    }

    @Test
    @DisplayName("application/json通过DTO传递命名枚举")
    void test2() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"1-value1\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/enums/named2", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "1-value1"));
    }


    @Test
    @DisplayName("application/x-www-form-urlencoded传递命名枚举")
    void test3() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("param1=1-value1", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/enums/named3", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo("1-value1");
    }


    @Test
    @DisplayName("同时传递多个类型的命名枚举")
    void test4() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"1-value1\",\"param2\":\"2-value1\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/enums/named4", requestEntity, ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "1-value1", "param2", "2-value1"));
    }

    @Test
    @DisplayName("同时传递1个类型的多个命名枚举")
    void test5() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"1-value1\",\"param2\":\"1-value2\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/enums/named5", requestEntity, ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "1-value1", "param2", "1-value2"));
    }

    @Test
    @DisplayName("字符串对应的枚举不存在")
    void test6() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"1-value11\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/enums/named6", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getStatus()).isEqualTo(AliErrorCode.USER_ERROR_A0400);
    }

    @Test
    @DisplayName("相同字符串不同枚举值")
    void test7() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"c-value1\",\"param2\":\"c-value1\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/enums/named7", requestEntity, ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult body = response.getBody();
        System.out.println(body);
        Assertions.assertThat(body.getData()).isEqualTo(Map.of("param1", "c-value1", "param2", "c-value1"));
    }
}
