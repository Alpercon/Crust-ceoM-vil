#include <SoftwareSerial.h>

SoftwareSerial blue(2,3); //(Rx, Tx)
char nombre[21] = "CrustaceoMovil";
char BPS = '4'; // 1 -> 1200, 2 -> 2400, n -> 1200*(2^(n-1))
char PWD[5] = "0000";

void setup() {
  blue.begin(9600);
  
  pinMode(13, OUTPUT);
  digitalWrite(13,HIGH);
  delay(4000);

  digitalWrite(13,LOW);
  blue.print("AT");
  delay(1000);

  blue.print("AT+BAUD");
  blue.print(BPS);
  delay(1000);

  blue.print("AT+NAME");
  blue.print(nombre);
  delay(1000);

  blue.print("AT+PIN");
  blue.print(PWD);
  delay(1000);
}

void loop() {
  digitalWrite(13,HIGH);
  delay(100);
  digitalWrite(13, LOW);
  delay(100);
}
