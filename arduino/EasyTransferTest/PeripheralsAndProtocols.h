#ifndef PERIPHERALS_H
#define PERIPHERALS_H
//--Treat this file as a config file
#include "Arduino.h"
//#define kDebug 1 //Uncomment this for debugging stuff

//--Serial setup
#define kSerialRate 19200

#define kHeaderPacket1 0x06
#define kHeaderPacket2 0x15

#define kEventQueueSize 20

//Define data structures for the transfer
typedef struct SerialSendData {    
    uint16_t c;
    uint8_t bo;
    int8_t b;
    int16_t s;
    int32_t i;
    int64_t l;
} SerialSendData;

typedef struct SerialReceiveData {
    uint16_t c;
    uint8_t bo;
    int8_t b;
    int16_t s;
    int32_t i;
    int64_t l;
} SerialReceiveData;

#endif //PERIPHERALS_H
