package com.qtec.sm2sm3sm4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blueflybee.sm2sm3sm4.library.SM4Utils;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String plainText = "abcd";

    SM4Utils sm4 = new SM4Utils();
    sm4.setSecretKey("JeF8U9wHFOMfs2Y8");
    sm4.setHexString(false);

    System.out.println("ECB模式");
    String cipherText = sm4.encryptData_ECB(plainText);
    System.out.println("密文: " + cipherText);
    System.out.println("");

    plainText = sm4.decryptData_ECB(cipherText);
    System.out.println("明文: " + plainText);
    System.out.println("");


    System.out.println("CBC模式");
    sm4.setIv("UISwD9fW6cFh9SNS");
    cipherText = sm4.encryptData_CBC(plainText);
    System.out.println("密文: " + cipherText);
    System.out.println("");

    plainText = sm4.decryptData_CBC(cipherText);
    System.out.println("明文: " + plainText);
  }
}
