#include <SoftwareSerial.h>

SoftwareSerial blue(2,3); //(Rx, Tx)
char nombre[21] = "CrustaceoMovil";
char BPS = '4'; // 1 -> 1200, 2 -> 2400, n -> 1200*(2^(n-1))
char PWD[5] = "0000";
char state;
int en1M1 = 6;
int en2M1 = 7;
int en1M2 = 8;
int en2M2 = 9;
int velM1 = 10;
int velM2 = 11;

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

  pinMode(en1M1, OUTPUT);
  pinMode(en2M1, OUTPUT);
  pinMode(en1M2, OUTPUT);
  pinMode(en2M2, OUTPUT);
  pinMode(velM1, OUTPUT);
  pinMode(velM2, OUTPUT);

  analogWrite(velM1, 254);
  analogWrite(velM2, 254);
}

void loop() {
  while(blue.available()){
    state = blue.read();
    switch(state){
      case '0':
        blue.println("Avanzando");
        analogWrite(velM1, 254);
        analogWrite(velM2, 254);
        digitalWrite(en1M1, HIGH);
        digitalWrite(en2M1, LOW);
        digitalWrite(en1M2, HIGH);
        digitalWrite(en2M2, LOW);
      break;
      case '1':
        blue.println("Retrocediendo");
        analogWrite(velM1, 254);
        analogWrite(velM2, 254);
        digitalWrite(en2M1, HIGH);
        digitalWrite(en1M1, LOW);
        digitalWrite(en2M2, HIGH);
        digitalWrite(en1M2, LOW);
      break;
      case'2':
        blue.println("Girando a la derecha");
        analogWrite(velM1, 254);
        analogWrite(velM2, 157);
      break;
      case '3':
        blue.println("Girando a la izquierda");
        analogWrite(velM1, 157);
        analogWrite(velM2, 254);  
      break;
      case '4':
        blue.println("Me estoy quemando!! :'v");
        analogWrite(velM1, 254);
        analogWrite(velM2, 254);
        digitalWrite(en1M1, HIGH);
        digitalWrite(en2M1, LOW);
        digitalWrite(en1M2, LOW);
        digitalWrite(en2M2, HIGH);
      break;
    }
    
  }
}
