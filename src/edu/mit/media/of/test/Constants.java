package edu.mit.media.of.test;

public class Constants {
	public static final String PORT_NAMES[] = { 
		"/dev/tty.usbserial-A900ac6q",	// Mac OS X
		"/dev/ttyUSB0", 				// Linux
		"COM4", 						// Windows
	};
	public static final String DEFAULT_PORT_NAME = "COM4";
	public static final int DEFAULT_SERIAL_RATE_INDEX = 2;
	public static final int SERIAL_RATES[] = {
		4800, 9600, 19200, 38400, 57600
	};
	
	public static final String SERIAL_RATES_STR[] = {
		"4800", "9600", "19200", "38400", "57600"
	}; 
}
