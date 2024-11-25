package com.alinesno.infra.data.assets.key;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    public static final String API_KEY = "apiKey";

    public static final int SALT_LENGTH = 16; // 盐的长度为16字节
    public static final String KEY_PREFIX = "sk-";

    public static String generateKey(String userId, String orgId) {
        try {
            // 创建安全随机数生成器
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt); // 生成随机盐

            // 将用户ID、组织ID与盐连接起来
            String combined = userId + ":" + orgId + ":" + Base64.getEncoder().encodeToString(salt);

            // 将组合后的字符串进行Base64编码
            String encodedKey = Base64.getEncoder().encodeToString(combined.getBytes());

            // 添加前缀
            return KEY_PREFIX + encodedKey;
        } catch (Exception e) {
            throw new RuntimeException("Error generating key", e);
        }
    }

    public static String[] parseIdsFromKey(String key) {
        try {
            // 检查前缀
            if (!key.startsWith(KEY_PREFIX)) {
                throw new IllegalArgumentException("Invalid key format");
            }

            // 去掉前缀
            String encodedKey = key.substring(KEY_PREFIX.length());

            // 对密钥进行Base64解码
            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
            String combined = new String(decodedKey);

            // 分割字符串，获取用户ID和组织ID
            String[] parts = combined.split(":", 3);
            if (parts.length == 3) {
                return new String[]{parts[0], parts[1]}; // 用户ID和组织ID
            } else {
                throw new IllegalArgumentException("Invalid key format");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing IDs from key", e);
        }
    }

}