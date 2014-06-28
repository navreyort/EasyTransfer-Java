# EasyTransfer-Java

EasyTranser-Java is an implementation of [EasyTransfer](https://github.com/madsci1016) in Java. 

#### Built and Tested with:
[Eclipse](http://download.eclipse.org/eclipse/downloads/)
[Arduino IDE](http://arduino.cc/en/main/software)
 
#### Java Library dependencies:

[Lombok](http://projectlombok.org/)
[jSSC](https://code.google.com/p/java-simple-serial-connector/)
[commons-lang3-3.3.2](https://commons.apache.org/proper/commons-lang/download_lang.cgi)

They are all included in the project and located at lib/jar/.

## Test

The test program included in this project checks if the values going back and forth between the java project and arduino is consistent. The java program initiates communication with an arduino board and the board returns the same exact set of value to the java program. The outgoing and incoming serial values are printed in the console. The values of outgoing and incoming serial values are identical to prove that the program is working. To run the test program:

1. Open arduino/EasyTransferTest/EasyTransferTest.ino in Arduino IDE.
2. Upload the ino program to arduino (I used Arduino UNO for testing).
3. Open src/edu.mit.media.of.test.Constants. 
4. Set the port name for arduino in PORT_NAMES variable. e.g. /dev/tty.usbmodemfd121 for mac.
5. Set the DEFAULT_PORT_NAMES_INDEX corresponding to your OS. 
6. Run TestSerialConnectionManager.java

When you successfully run the program, you should see system prints similar to the following:

	Outgoing ================================
	Byte: 10
	Short: 140
	Int: 1230
	Long: 104030
	Char: ]
	Boolean: true
	Incoming ================================
	Byte: 10
	Short: 140
	Int: 1230
	Long: 104030
	Char: ]
	Boolean: true

## Customize

#### Defining your data structure
EasyTransfer assumes that the data structures on two programs (i.e. An arduino-like microcontroller and an OS side software) are the same. The receiving data structure on the arduino code must correspond to the transmiting data structure on the java program and vice versa. 

For example, in the arduino side test code (arduino/EasyTransferTest/PeripheralsAndProtocols.h), the receiving data structure looks like this: 

	typedef struct SerialReceiveData {    
    	uint16_t c;
    	uint8_t bo;
    	int8_t b;
    	int16_t s;
    	int32_t i;
    	int64_t l;
	} SerialReceiveData;
	
The transmitting data structure on the java program looks like this: 

	public class TxData extends SerialTransmitData {
		private @Getter Character c = ']';
		private @Getter Boolean bo = true;
		private @Getter Byte b = 10;
		private @Getter Short s = 140;
		private @Getter Integer i = 1230;
		private @Getter Long l = 104030L;
	}

If you want to cusomize this program for your own project, modifying these values might be a good starting point. The maximum size of the data structure cannot exceed 256 bytes. Note that the order of each type in the data structure also matters.

#### Adding behavior 
##### Arduino
SerialEventHandler class is responsible for managing serial data coming in and out of arduino. You will need to use serialEventHandler.event for transmitting serial data and serialEventHandler.getIncomingEvent to wait for data to be received.

###### Receiving serial data
In the test code (in arduino/EasyTransferTest/EasyTransferTest.ino), Arduino receives serial messages using: 

	if(serialEventHandler.getIncomingEvent(&receiveData)){}
	
In this if statement, you can add your custom functions, switch statements, and etc... based on how you'd like your arduino to behave.

###### Transmitting serial data
In the test code (arduino/EasyTransferTest/EasyTransferTest.ino), arduino transmits serial data using:

	serialEventHandler.event(sendData);


##### Java
SerialServer and SerialDataReader are responsible for managing serial data coming in and out of java program. SerialServer sends out serial data to arduino using:

	serialServer.sendData
	
The java program receives serial data through SerialDataReader's processInput method:

	public void processInput(ByteBuffer message){}
	
This method decodes incoming serial message and packages up into RxData class. 
 
## TODO
* The project needs to be tested with Windows and Linux machines.
* More example codes

## License 

MIT License applies to this code repository

    Copyright (C) 2014 Akito van Troyer
        
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
    the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
    COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
    IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
    CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.