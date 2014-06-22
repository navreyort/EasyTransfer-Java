#ifndef SERIALEVENTHANDLER_H
#define SERIALEVENTHANDLER_H

#include "PeripheralsAndProtocols.h"
#include "EventQueue.h"
#include "EasyTransfer.h"

class SerialEventHandler {
  public:
    
    void event(SerialSendData data);
    void incomingMessage();
    void sendMessage();
    boolean getIncomingEvent(SerialReceiveData *data) ;
    void run() ;
    void setup() ;
    void update() ;
    
  private:
     static boolean isSerialInitialized;
     boolean isIncomingComplete;
     EasyTransfer ETIn;
     EasyTransfer ETOut;
     SerialSendData txData;
     SerialReceiveData rxData;
     EventQueue<SerialReceiveData> inQueue;
     EventQueue<SerialSendData> outQueue;
};

boolean SerialEventHandler::isSerialInitialized = false;

inline void SerialEventHandler::setup() {
 if(!SerialEventHandler::isSerialInitialized){
    Serial.begin(kSerialRate);
    ETIn.begin(details(rxData),&Serial);
    ETOut.begin(details(txData),&Serial);
    SerialEventHandler::isSerialInitialized = true;
  }
  
  this->inQueue.setQueueID(0);
  this->outQueue.setQueueID(1);
}

inline void SerialEventHandler::update() {
  this->incomingMessage();
  this->sendMessage();
}

inline void SerialEventHandler::event(SerialSendData data)  {
  this->outQueue.enqueueEvent(data);
}

inline void SerialEventHandler::incomingMessage() {
  if(ETIn.receiveData()){
    this->inQueue.enqueueEvent(rxData);
  }
}

inline void SerialEventHandler::sendMessage() {
  if(!this->outQueue.isEmpty()){
    outQueue.dequeueEvent(&txData);
    ETOut.sendData();
  }
}

inline boolean SerialEventHandler::getIncomingEvent(SerialReceiveData *data) {
  if(!this->inQueue.isEmpty() || this->inQueue.getNumEvents() > 0){
    this->inQueue.dequeueEvent(data);
    return true;
  }
  return false;
}

#endif //SERIALEVENTHANDLER_H
