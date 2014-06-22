#include "SerialEventHandler.h"

SerialEventHandler serialEventHandler;
SerialSendData data;

void setup(){
  serialEventHandler.setup();
  pinMode(13,OUTPUT);
  digitalWrite(13,LOW);
}

void loop(){
  serialEventHandler.update();
  handleIncomingSerial();
  
  //XXX Send Example
/*
  data.b = 10;
  data.s = 100;
  data.i = 1000;
  data.l = 10000;
  data.c = 'j';
  data.bo = true;
  serialEventHandler.event(data);
*/
}

void handleIncomingSerial(){
  
  static SerialReceiveData receiveData;
    
  if(serialEventHandler.getIncomingEvent(&receiveData)){
   //XXX Receive Example
    
    SerialSendData sendData;
    sendData.b = receiveData.b;
    sendData.s = receiveData.s;
    sendData.i = receiveData.i;
    sendData.l = receiveData.l;
    sendData.c = receiveData.c;
    sendData.bo = receiveData.bo;
    serialEventHandler.event(sendData);
    
  }
}
