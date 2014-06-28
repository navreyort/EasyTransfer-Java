package edu.mit.media.of.test;

public class Constants {
	public static final String PORT_NAMES[] = { 
		"/dev/tty.usbmodemfd121",	// Mac OS X
		"/dev/ttyUSB0", 				// Linux
		"COM4", 						// Windows
	};
	public static final int DEFAULT_PORT_NAMES_INDEX = 0;
	public static final int DEFAULT_SERIAL_RATE_INDEX = 2;
	public static final int SERIAL_RATES[] = {
		4800, 9600, 19200, 38400, 57600
	};
}
