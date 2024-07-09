package com.campus.util.springboot.mybatisplus;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

/**
 * @author 黄磊
 */
@Slf4j
public class PageUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static SymmetricCrypto CRYPTO_AGENT;

    /**
     * 从旧的PageDTO中提取分页信息，将records替换为新的records
     *
     * @param oldPageDTO 旧的PageDTO
     * @param records    新的records
     * @param <T>        records的类型
     * @return 新的PageDTO
     */
    public static <T> OffsetPageDto<T> changeRecord(OffsetPageDto<?> oldPageDTO, List<T> records) {
        return new OffsetPageDto<>(oldPageDTO.getCurrent(), oldPageDTO.getSize(), oldPageDTO.getTotal(), records);
    }

    /**
     * 从旧的CursorPageDTO中提取分页信息，将records替换为新的records
     *
     * @param oldPageDTO 旧的CursorPageDTO
     * @param records    新的records
     * @param <T>        records的类型
     * @return 新的CursorPageDTO
     */
    public static <T> CursorPageDto<T> changeRecord(CursorPageDto<?> oldPageDTO, List<T> records) {
        return new CursorPageDto<>(records, oldPageDTO.getNext());
    }

    /**
     * 设置加密解密器
     *
     * @param keySeed 密钥种子
     */
    public static void setCrypto(String keySeed) {
        SecureRandom secureRandom = new SecureRandom(keySeed.getBytes());
        byte[] key = KeyUtil.generateKey(SymmetricAlgorithm.AES.getValue(), -1, secureRandom).getEncoded();
        CRYPTO_AGENT = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
        log.debug("成功设置加密解密器");
    }

    /**
     * 加密游标查询的参数为游标id
     *
     * @param param 参数
     * @return 加密后的游标id
     */
    @SneakyThrows
    public static String encode(Map<String, Object> param) {
        String context = OBJECT_MAPPER.writeValueAsString(param);
        return CRYPTO_AGENT.encryptHex(context);
    }

    /**
     * 解密游标id
     *
     * @param cursorId 游标id
     * @return 解密后的参数
     */
    @SneakyThrows
    public static JsonNode decode(String cursorId) {
        String content = CRYPTO_AGENT.decryptStr(cursorId);
        return OBJECT_MAPPER.readTree(content);
    }
}
