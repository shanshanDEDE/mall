package com.willy.malltest.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    // 定義一個靜態方法，輸入參數為JWT字符串和要提取的負載資料的鍵名
    public static String payloadJWTExtraction(String token, String extraction) {

        // 移除JWT字符串前的"Bearer "前綴
        token.replace("Bearer ", "");

        // 將JWT按"."分割成三部分：頭部（header）、負載（payload）和簽名（signature）
        String[] chunks = token.split("\\.");
        // 獲取URL安全的Base64解碼器
        Base64.Decoder decoder = Base64.getUrlDecoder();

        // 解碼JWT的第二部分（負載）並轉換成字符串
        String payload = new String(decoder.decode(chunks[1]));

        // 將負載按","分割成多個鍵值對
        String[] entries = payload.split(",");
        // 創建一個HashMap來儲存解析出的鍵值對
        Map<String, String> map = new HashMap<String, String>();

        // 遍歷所有鍵值對
        for (String entry : entries) {
            // 將每個鍵值對按":"分割
            String[] keyValue = entry.split(":");
            // 如果當前鍵與要提取的鍵名相同
            if (keyValue[0].equals(extraction)) {

                // 根據值的格式進行處理，去除多餘的字符
                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }
                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                // 將處理後的鍵值對儲入HashMap
                map.put(keyValue[0], keyValue[1]);
            }
        }
        // 如果HashMap中包含要提取的鍵，則返回對應的值
        if (map.containsKey(extraction)) {
            return map.get(extraction);
        }
        // 如果沒有找到對應的鍵，返回null
        return null;
    }
}
