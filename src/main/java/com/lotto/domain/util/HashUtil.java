package com.lotto.domain.util;

import com.lotto.global.exception.CustomException;
import com.lotto.global.exception.ErrorCode;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    private static final String ALGORITHM = "SHA-256";

    public static String sha256(String plainText) {
        if (plainText == null || plainText.isBlank()) {
            throw new CustomException(ErrorCode.HASH_EMPTY);
        }

        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] encodedHash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(ErrorCode.HASH_ALGORITHM_NOT_FOUND);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexStr = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) {
                hexStr.append('0');
            }
            hexStr.append(hex);
        }
        return hexStr.toString();
    }
}
