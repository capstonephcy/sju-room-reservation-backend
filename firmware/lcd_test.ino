#include <Arduino.h>
#include <U8g2lib.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <HttpClient.h>

// for SPI
#include <SPI.h>

char* ssid = "NewCentury";    // wifi 아이디
char* password =  "**-*******";    // wifi 비번
char* serverName = "http://192.168.0.101:3000/rooms/profiles/devices?id=2"; // 웹서버주소 (id 다음에 룸 넘버 설정)

WiFiClient client;

typedef struct RevData {
  int status = 0;
  String building = "";
  String number = "";
  String revOwnerName = "";
  String checkInCode = "";
  bool empty = true;
};

RevData req_data();
void printSerialLog(RevData parsedData);


// for using Korean NanumGothicCoding font
//#include "u8g2_font_unifont_t_korean_NanumGothicCoding_16.h"
U8G2_SH1106_128X64_NONAME_F_4W_HW_SPI u8g2(U8G2_R0, /* cs=*/ 5, /* dc=*/ 14, /* reset=*/ 15);

void setup() {
  Serial.begin(115200);
  WiFi.begin(ssid, password);   // 와이파이 접속
  u8g2.begin();
  u8g2.enableUTF8Print();   // enable UTF8 support for the Arduino print() function
}

void loop() {
  u8g2.firstPage();
  do {
    
    if (WiFi.status() != WL_CONNECTED) { //Check for the connection
      Serial.println("Connecting to WiFi..");
      u8g2.setFont(u8g2_font_squeezed_r6_tr);
      u8g2.setCursor(0, 10);
      u8g2.print("Sejong Conference Room Reservation");
      
      u8g2.setCursor(0, 20);
      u8g2.print("SSID: ");
      u8g2.setCursor(20, 20);
      u8g2.print(ssid);
      
      u8g2.setFont(u8g2_font_unifont_t_korean2);
      u8g2.setCursor(0, 36);
      u8g2.print("WIFI 접속 중...");
    } else {
      Serial.println("Connected to the WiFi network");
      RevData data = req_data();
      if (data.status) {
        u8g2.setFont(u8g2_font_squeezed_r6_tr);
        u8g2.setCursor(0, 10);
        u8g2.print("Sejong Conference Room Reservation");

        u8g2.setFont(u8g2_font_unifont_t_korean2);
        
        u8g2.setCursor(0, 26);
        u8g2.print(data.building + " " + data.number);
        
        if (data.empty) {
          u8g2.setCursor(0, 44);
          u8g2.print("예약자: " + data.revOwnerName);

          u8g2.setCursor(0, 60);
          u8g2.print("인증No: " + data.checkInCode);
        } else {
          u8g2.setCursor(0, 44);
          u8g2.print("예약 정보 없음");

          u8g2.setCursor(0, 60);
          u8g2.print("(공실입니다)");
        }
      } else {
        u8g2.setFont(u8g2_font_squeezed_r6_tr);
        u8g2.setCursor(0, 10);
        u8g2.print("Sejong Conference Room Reservation");

        u8g2.setFont(u8g2_font_unifont_t_korean2);
        
        u8g2.setCursor(0, 26);
        u8g2.print("HTTP 통신 에러");
      }
      
      // u8g2.print(name.c_str());  // write something to the internal memory
    }
  } while (u8g2.nextPage());
  delay(5000);
}

RevData req_data() {
  HTTPClient http;
  http.begin(serverName);
  int httpCode = http.GET();
  String payload = "";
  RevData parsedData;

  if (httpCode > 0) {
    payload = http.getString();

    StaticJsonDocument<5000> jsonDoc; //Memory pool
    DeserializationError err = deserializeJson(jsonDoc, payload);

    if (err) {
      Serial.print(F("deserializeJson() failed with code "));
      Serial.println(err.f_str());
    }
    Serial.println("Receieved:");
    Serial.println(payload);

    int status = jsonDoc["_metadata"]["status"];
    if (status) {
      parsedData.status = jsonDoc["_metadata"]["status"];
      const char* building = jsonDoc["room"]["building"];
      int number = jsonDoc["room"]["number"];
      parsedData.building = building;
      parsedData.number = String(number);
      parsedData.empty = !jsonDoc["currentRev"].isNull();
      if (parsedData.empty) {
        const char* name = jsonDoc["currentRev"]["revOwner"]["name"];
        parsedData.revOwnerName = name;
        parsedData.checkInCode = "000000";
      } else {
        parsedData.revOwnerName = "";
        parsedData.checkInCode = "";
      }
    }
    printSerialLog(parsedData);
  } else {
    Serial.println("Error on HTTP Request");
    Serial.printf("[HTTP] GET... failed, error: %s\n", http.errorToString(httpCode).c_str());
  }
  http.end();
  return parsedData;
}

void printSerialLog(RevData parsedData) {
  Serial.println("Result: ");
  Serial.println("building: " + parsedData.building);
  Serial.println("number: " + parsedData.number);
  Serial.println("empty: " + String(parsedData.empty));
  Serial.println("name: " + parsedData.revOwnerName);
  Serial.println("checkInCode: " + parsedData.checkInCode);
}
