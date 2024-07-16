package com.campus.util.springboot.test.exception;

import com.campus.util.springboot.exception.EnableEggCampusExceptionHandler;
import com.eggcampus.util.result.ReturnResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;

/**
 * @author 黄磊
 */
@SuppressWarnings("DataFlowIssue")
@EnableEggCampusExceptionHandler
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "eggcampus.exception.print-error-message=true",
})
@Import(ExceptionTestController.class)
public class ExceptionTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("参数类型不匹配")
    void test_methodArgumentTypeMismatchException() throws Exception {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/exception/methodArgumentTypeMismatchException?param1=aaaa", String.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody()).contains("输入的参数有问题");
    }

    @Test
    @DisplayName("缺少参数")
    void test_missingServletRequestParameterException() {
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/exception/missingServletRequestParameterException", ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody().getMessage()).contains("输入的参数有问题");
    }

    @Test
    @DisplayName("路径参数时，类型不匹配")
    void test_methodArgumentTypeMismatchException2() {
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/exception/methodArgumentTypeMismatchException2/aaa", ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody().getMessage()).contains("输入的参数有问题");
    }

    @Test
    @DisplayName("application/x-www-form-urlencoded时，参数类型不匹配")
    void test_methodArgumentTypeMismatchException3() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("param1=aaa", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/exception/methodArgumentTypeMismatchException3", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody().getMessage()).contains("输入的参数有问题");
    }

    @Test
    @DisplayName("application/x-www-form-urlencoded时，缺少参数")
    void test_missingServletRequestParameterException3() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/exception/missingServletRequestParameterException3", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody().getMessage()).contains("输入的参数有问题");
    }

    @Test
    @DisplayName("application/json时，参数类型不匹配")
    void test_httpMessageNotReadableException() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"aaa\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/exception/httpMessageNotReadableException", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody().getMessage()).contains("输入的参数有问题");
    }

    @Test
    @DisplayName("application/json时，校验未通过")
    void test_bindException() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/exception/bindException", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody().getErrorMessage()).contains("param1 : param1不能为空");
    }

    @Test
    @DisplayName("application/json时，请求体缺失")
    void test_httpMessageNotReadableException2() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/exception/httpMessageNotReadableException2", requestEntity, ReturnResult.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        System.out.println(response.getBody());
        Assertions.assertThat(response.getBody().getMessage()).contains("输入的参数有问题");
    }
}
