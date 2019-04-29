package com.qtec.sm2sm3sm4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blueflybee.sm2sm3sm4.library.SM4Utils;
import com.qtec.sm2sm3sm4.utils.FileIOUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;

import static com.blueflybee.sm2sm3sm4.library.SM4Utils.UTF_8;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String plainText = "你好，我是abcdksajdfl37645)*(^^%$#，很高兴见到你！";

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


    System.out.println("/////////////////////以下是对byte数组加密解密///////////////////");
    sm4 = new SM4Utils();
    sm4.setSecretKey("JeF8U9wHFOMfs2Y8");
    sm4.setHexString(false);

    System.out.println("ECB模式");

    byte[] encryptBytes = null;

    try {
      encryptBytes = sm4.encryptBytes_ECB(plainText.getBytes(UTF_8));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    for (byte encryptByte : encryptBytes)
      System.out.print(Integer.toHexString(encryptByte & 0xff) + "\t");

    System.out.println();

    byte[] result = sm4.decryptBytes_ECB(encryptBytes);
    try {
      System.out.println("明文: " + new String(result, UTF_8));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }


    testSm4File();
  }

  private void testSm4File() {
    File filesDir = getFilesDir();
    //test.txt
    File file = new File(filesDir, "pic2.jpg");
    File encryptFile = new File(filesDir, "en_pic2.jpg");
    File decryptFile = new File(filesDir, "de_pic2.jpg");
    System.out.println("file.exists() = " + file.exists());

    System.out.println("/////////////////////以下是对文件进行加密解密///////////////////");
    SM4Utils sm4 = new SM4Utils();
    sm4.setSecretKey("JeF8U9wHFOMfs2Y8");
    sm4.setHexString(false);

    System.out.println("ECB模式");

    byte[] bytesEncrypt = sm4.encryptBytes_ECB(FileIOUtils.readFile2BytesByStream(file.getPath()));
//    for (byte encryptByte : bytesEncrypt)
//      System.out.print(Integer.toHexString(encryptByte & 0xff) + "\t");
//
    FileIOUtils.writeFileFromBytesByStream(encryptFile, bytesEncrypt);
//
    byte[] encryptBytesFromFile = FileIOUtils.readFile2BytesByStream(encryptFile.getPath());

    System.out.println();

    byte[] result = sm4.decryptBytes_ECB(encryptBytesFromFile);
//    System.out.println("明文: " + new String(result));

    FileIOUtils.writeFileFromBytesByStream(decryptFile, result);

  }
}
