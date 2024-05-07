package com.campus.util.springboot.test.log;

import com.campus.util.springboot.exception.EnableExceptionHandler;
import com.campus.util.springboot.log.EnableLog;
import com.campus.util.springboot.log.TraceIdUtil;
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

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 黄磊
 */
@SuppressWarnings("DataFlowIssue")
@EnableLog
@EnableExceptionHandler
@SpringBootTest(properties = "logging.pattern.level=%5p [%X{traceId}]",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(LogTestController.class)
public class LogTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final PrintStream out;

    {
        out = System.out;
    }

    private void changeToStandardOutStream() {
        System.setOut(out);
    }

    private ByteArrayOutputStream changeToInnerStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // 重定向标准输出流
        System.setOut(ps);
        return baos;
    }

    private String getLog(ByteArrayOutputStream stream) {
        String log = stream.toString();

        Pattern pattern = Pattern.compile(".*util\\.springboot\\.log\\.LogAOP.*");
        Matcher matcher = pattern.matcher(log);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            changeToStandardOutStream();
            System.out.println(log);
            throw new RuntimeException("未找到输出的日志");
        }
    }

    @Test
    @DisplayName("测试Param参数正常显示")
    void test_log1() {
        ByteArrayOutputStream stream = changeToInnerStream();
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/log/log1?param1=111", ReturnResult.class);
        changeToStandardOutStream();
        String log = getLog(stream);
        System.out.println(log);


        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        Assertions.assertThat(log).contains("{\"param1\":\"111\"}");
    }

    @Test
    @DisplayName("测试application/json的请求体数据正常显示")
    void test_log2() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"111\"}", headers);

        ByteArrayOutputStream stream = changeToInnerStream();
        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/log/log2", requestEntity, ReturnResult.class);
        changeToStandardOutStream();
        String log = getLog(stream);
        System.out.println(log);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        Assertions.assertThat(log).contains("请求体类型：application/json，请求体：{\"param1\":\"111\"}");
    }

    @Test
    @DisplayName("测试暂不支持查看的Content-Type")
    void test_log3() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>("param1=111", headers);

        ByteArrayOutputStream stream = changeToInnerStream();
        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/log/log3", requestEntity, ReturnResult.class);
        changeToStandardOutStream();
        String log = getLog(stream);
        System.out.println(log);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        Assertions.assertThat(log).contains("请求体类型：application/x-www-form-urlencoded，请求体：暂不支持查看");
    }

    @Test
    @DisplayName("测试会抛出异常Content-Type")
    void test_log4() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/aaa"));
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"param1\":\"111\"}", headers);

        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/log/log4", requestEntity, ReturnResult.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult returnResult = response.getBody();
        Assertions.assertThat(returnResult.getStatus()).isEqualTo(AliErrorCode.SYSTEM_ERROR_B0001);
        Assertions.assertThat(returnResult.getErrorMessage()).contains("使用了不支持的contentType<application/aaa>，路径</log/log4>");
    }

    @Test
    @DisplayName("自动生成Trace-Id")
    void test_log5() {
        ByteArrayOutputStream stream = changeToInnerStream();
        ResponseEntity<ReturnResult> response = testRestTemplate.getForEntity("/log/log5", ReturnResult.class);
        changeToStandardOutStream();
        String log = getLog(stream);
        System.out.println(log);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult returnResult = response.getBody();
        Assertions.assertThat(returnResult.getStatus()).isEqualTo(AliErrorCode.SUCCESS);
        Assertions.assertThat(log).containsPattern("^.*INFO \\[.+].*---.*$");
    }

    @Test
    @DisplayName("传递Trace-Id")
    void test_log6() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(TraceIdUtil.HEADER_NAME, "123123123123");
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        ByteArrayOutputStream stream = changeToInnerStream();
        ResponseEntity<ReturnResult> response = testRestTemplate.postForEntity("/log/log6", requestEntity, ReturnResult.class);
        changeToStandardOutStream();
        String log = getLog(stream);
        System.out.println(log);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
        ReturnResult returnResult = response.getBody();
        Assertions.assertThat(returnResult.getStatus()).isEqualTo(AliErrorCode.SUCCESS);
        Assertions.assertThat(log).containsPattern("^.*INFO \\[123123123123].*---.*$");
    }
}
