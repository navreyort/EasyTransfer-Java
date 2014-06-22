#ifndef PERIPHERALS_H
#define PERIPHERALS_H
//--Treat this file as a config file
#include "Arduino.h"
//#define kDebug 1 //Uncomment this for debugging stuff

#define kSDA 20
#define kSDL 21

//--Serial setup
#define kSerialRate 9600

//Define data structures for the transfer
typedef struct SerialSendData {    
    int8_t b;
    int16_t s;
    int32_t i;
    int64_t l;
    uint16_t c;
    uint8_t bo;
} SerialSendData;

typedef struct SerialReceiveData {    
    int8_t b;
    int16_t s;
    int32_t i;
    int64_t l;
    uint16_t c;
    uint8_t bo;
} SerialReceiveData;

//--Low Level AVR access------------------------------------
#define cbi(port,pin)  port &= ~_BV(pin)
#define sbi(port,pin)  port |= _BV(pin)
#define toggle(port,pin) port ^= _BV(pin)
#define bit_test(byte,bit) (byte & (1 << bit)) // test for bit set
#define pin_test(pins,pin) (pins & (1<<pin))

#endif //PERIPHERALS_H
