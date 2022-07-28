package com.example.demo.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class JasyptConfigTest extends JasyptConfig{

    @Test
    void jasypt() {
        String plainText = "";
        String encryptedText = stringEncryptor().encrypt(plainText);
        String decryptedText = stringEncryptor().decrypt(encryptedText);
        System.out.println(plainText);
        System.out.println(encryptedText);
        System.out.println(decryptedText);
        Assertions.assertEquals(plainText,decryptedText);
    }

}