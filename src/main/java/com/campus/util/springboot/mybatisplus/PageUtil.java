package com.campus.util.springboot.mybatisplus;

import cn.hutool.core.util.StrUtil;
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
import java.util.Objects;

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
    @SneakyThrows
    public static void setCrypto(String keySeed) {
        // 使用SHA1PRNG来生成固定的密钥
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(keySeed.getBytes());
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

    /**
     * 解密游标id
     *
     * @param qo 游标查询
     * @return 解密后的参数。假如要解密的游标id为null，则返回NullNode，而不是null
     */
    public static JsonNode decode(CursorPageQo qo) {
        if (StrUtil.isEmpty(qo.getCursorId())) {
            return OBJECT_MAPPER.nullNode();
        }
        return decode(qo.getCursorId());
    }

    /**
     * 断言游标分页查询还未到最后
     *
     * @param qo 游标查询
     */
    public static void assertNotAtEnd(CursorPageQo qo) {
        if (Objects.nonNull(qo.getCursorId()) && qo.getCursorId().equals(CursorPageDto.END_CURSOR_ID)) {
            throw new OutOfPaginationException("查询已到最后");
        }
    }
}
