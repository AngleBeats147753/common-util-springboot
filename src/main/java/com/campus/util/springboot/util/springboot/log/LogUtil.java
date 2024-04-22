package com.campus.util.springboot.util.springboot.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * 日志处理工具
 *
 * @author 黄磊
 */
public class LogUtil {
    private static final List<String> PRIVACY_DATA_KEYS = List.of("password", "oldPassword", "newPassword");
    private static final String MASK = "***";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String maskPrivacyData(String json) {
        try {
            JsonNode root = OBJECT_MAPPER.readTree(json);
            doMaskPrivacyData(root);
            return OBJECT_MAPPER.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void doMaskPrivacyData(JsonNode node) {
        if (node.isObject()) {
            // 遍历对象节点的所有属性
            node.fields().forEachRemaining(entry -> {
                String key = entry.getKey();
                JsonNode valueNode = entry.getValue();
                if (PRIVACY_DATA_KEYS.contains(key) && valueNode.isTextual()) {
                    ((ObjectNode) node).put(key, MASK);
                } else if (valueNode.isObject() || valueNode.isArray()) {
                    // 递归处理对象节点或数组节点
                    doMaskPrivacyData(valueNode);
                }
            });
        } else if (node.isArray()) {
            // 遍历数组节点中的每个元素
            node.elements().forEachRemaining(element -> {
                if (element.isObject() || element.isArray()) {
                    // 递归处理对象节点或数组节点
                    doMaskPrivacyData(element);
                }
            });
        }
    }
}
