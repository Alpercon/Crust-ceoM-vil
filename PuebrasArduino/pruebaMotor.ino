#include <SoftwareSerial.h>

//SoftwareSerial blue(2,3); //(Rx, Tx) ***PRUEBA***
char nombre[21] = "CrustaceoMovil";
char BPS = '4'; // 1 -> 1200, 2 -> 2400, n -> 1200*(2^(n-1))
char PWD[5] = "0000";
char state = '0';
int en1M1 = 6;
int en2M1 = 7;
int en1M2 = 8;
int en2M2 = 9;
int velM1 = 11;
int velM2 = 10;

int izquierdo = A1;
int derecho = A0;

int luzIzquierda = 0; //Resistencia inversamente proporcional a la luz
int luzDerecha = 0; //Resistencia directamente proporcional a la luz

long tiempo;
int trigger = 4;
int echo = 5;
float distancia;

int autoSpeed = 140;
int maxSpeed = 200;

void setup() {
  Serial1.begin(9600);
  Serial1.begin(9600);

  pinMode(trigger, OUTPUT);
  pinMode(echo, INPUT);
  pinMode(13, OUTPUT);
  digitalWrite(13,HIGH);
  delay(4000);

  digitalWrite(13,LOW);
  Serial1.print("AT");
  delay(1000);

  Serial1.print("AT+BAUD");
  Serial1.print(BPS);
  delay(1000);

  Serial1.print("AT+NAME");
  Serial1.print(nombre);
  delay(1000);

  Serial1.print("AT+PIN");
  Serial1.print(PWD);
  delay(1000);

  pinMode(en1M1, OUTPUT);
  pinMode(en2M1, OUTPUT);
  pinMode(en1M2, OUTPUT);
  pinMode(en2M2, OUTPUT);
  pinMode(velM1, OUTPUT);
  pinMode(velM2, OUTPUT);

  analogWrite(velM1, 0);
  analogWrite(velM2, 0);

  state = '0';
}

void loop() {
  state = '0';
  while(Serial1.available()){
    state = Serial1.read();
    switch(state){
      case '0':
        Serial1.println("Alto");
        detener();
      break;
      case '1':
        Serial1.println("Avanzando");
        avanzar(maxSpeed);
      break;
      case'2':
        Serial1.println("Retrocediendo");
        retroceder(maxSpeed);
      break;
      case '3':
        Serial1.println("Girando a la derecha"); 
        derecha(maxSpeed);  
      break;
      case '4':
        Serial1.println("Girando a la izquierda");
        izquierda(maxSpeed);
      break;
      case '5':
        Serial1.println("Me estoy quemando :'v");
        rotar(maxSpeed);
      break;
      case 'a':
        automatico();
      break;
      case 'l':
        avanzar(autoSpeed);
        detener();
        seguidorLuz();
      break;
    }
  }
}

void detener(){
  analogWrite(velM1, 0);
  analogWrite(velM2, 0);  
}

void avanzar(int velocidad){
  analogWrite(velM1, velocidad);
  analogWrite(velM2, velocidad);
  digitalWrite(en1M1, HIGH);
  digitalWrite(en2M1, LOW);
  digitalWrite(en1M2, HIGH);
  digitalWrite(en2M2, LOW);  
}

void retroceder(int velocidad){
  analogWrite(velM1, velocidad);
  analogWrite(velM2, velocidad);
  digitalWrite(en2M1, HIGH);
  digitalWrite(en1M1, LOW);
  digitalWrite(en2M2, HIGH);
  digitalWrite(en1M2, LOW);
}

void derecha(int velocidad){
  analogWrite(velM1, velocidad);
  analogWrite(velM2, 3/4*velocidad); 
}


void izquierda(int velocidad){
  analogWrite(velM1, 3/4*velocidad);
  analogWrite(velM2, velocidad);
}

void rotar(int velocidad){
  analogWrite(velM1, velocidad);
  analogWrite(velM2, velocidad);
  digitalWrite(en1M1, HIGH);
  digitalWrite(en2M1, LOW);
  digitalWrite(en1M2, LOW);
  digitalWrite(en2M2, HIGH); 
}

void automatico(){
  analogWrite(velM1, 0);
  analogWrite(velM2, 0);
  Serial1.println("Activando piloto automático");
  Serial1.println("Para volver a modo manual presiona x");
  //puntitos(300); 
  distancia = medirDistancia();
  Serial1.println("Piloto automático ACTIVADO");
  while((Serial1.read()) != 'x'){
    digitalWrite(trigger, HIGH);
    distancia = medirDistancia();
    if(distancia > 50){
      avanzar(autoSpeed); 
    }
    else{
      detener();
      while(distancia < 50 && ((Serial.read()) != 'x')){
        rotar(autoSpeed);
        delay(150);
        detener();
        distancia = medirDistancia();
      }
    }
  }
  detener();
  //puntitos(300);
  Serial1.println("Modo manual ACTIVADO");
}

float medirDistancia(){
  digitalWrite(trigger, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigger, LOW);
  tiempo = (pulseIn(echo, HIGH)/2);
  return float(tiempo * 0.0343);
}

void seguidorLuz(){
  while((Serial1.read()) != 'x'){
    luzDerecha = analogRead(derecho);
    luzIzquierda = analogRead(izquierdo);
    if(luzDerecha > 700){
      digitalWrite(velM1, 0.5*autoSpeed);
    }
    else{
      digitalWrite(velM1, 0);
    }
    if(luzIzquierda < 100){
      digitalWrite(velM2, 0.5*autoSpeed);
    }
    else{
      digitalWrite(velM2, 0);
    }
    Serial1.print("Luz izquierda, luz derecha: ");
    Serial1.print(luzIzquierda);
    Serial1.print(", ");
    Serial1.println(luzDerecha);
    delay(3000);
  }
  detener();
}

void puntitos(int timer){
  for(int i = 0; i < 3; i ++){
    for(int j = 0; j < 3; j++){
      Serial1.print(".");
      delay(timer); 
    }
    Serial1.println();
    delay(timer);
  } 
}

