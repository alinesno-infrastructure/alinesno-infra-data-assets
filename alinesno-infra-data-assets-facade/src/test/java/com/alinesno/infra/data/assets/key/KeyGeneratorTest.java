package com.alinesno.infra.data.assets.key;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorTest {

    @Test
    void testGenerateAndParseKey() {
        String userId = "12345"; // 示例用户ID
        String orgId = "67890"; // 示例组织ID

        // 生成密钥
        String key = KeyGenerator.generateKey(userId, orgId);
        assertNotNull(key, "生成的密钥不能为空");
        assertTrue(key.startsWith(KeyGenerator.KEY_PREFIX), "生成的密钥应以 'sk-' 开头");

        // 解析密钥
        String[] ids = KeyGenerator.parseIdsFromKey(key);
        assertNotNull(ids, "解析的ID数组不能为空");
        assertEquals(2, ids.length, "解析的ID数组应包含两个元素");
        assertEquals(userId, ids[0], "解析的用户ID应与生成时的用户ID相同");
        assertEquals(orgId, ids[1], "解析的组织ID应与生成时的组织ID相同");
    }

    @Test
    void testInvalidKeyFormat() {
        String invalidKey = "invalid-key"; // 无效的密钥

        // 尝试解析无效的密钥
        assertThrows(IllegalArgumentException.class, () -> {
            KeyGenerator.parseIdsFromKey(invalidKey);
        }, "解析无效密钥时应抛出IllegalArgumentException");
    }

    @Test
    void testMissingPrefix() {
        String keyWithoutPrefix = "base64encodedkey"; // 缺少前缀的密钥

        // 尝试解析缺少前缀的密钥
        assertThrows(IllegalArgumentException.class, () -> {
            KeyGenerator.parseIdsFromKey(keyWithoutPrefix);
        }, "解析缺少前缀的密钥时应抛出IllegalArgumentException");
    }

    @Test
    void testEmptyKey() {
        String emptyKey = ""; // 空密钥

        // 尝试解析空密钥
        assertThrows(IllegalArgumentException.class, () -> {
            KeyGenerator.parseIdsFromKey(emptyKey);
        }, "解析空密钥时应抛出IllegalArgumentException");
    }

    @Test
    void testNullKey() {
        String nullKey = null; // null密钥

        // 尝试解析null密钥
        assertThrows(NullPointerException.class, () -> {
            KeyGenerator.parseIdsFromKey(nullKey);
        }, "解析null密钥时应抛出NullPointerException");
    }
}