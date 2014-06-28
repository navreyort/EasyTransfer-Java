package edu.mit.media.of.test;

import lombok.Getter;
import edu.mit.media.of.net.serial.protocol.SerialTransmitData;

public class RxData extends SerialTransmitData {
	private @Getter Character c = 0x41;
	private @Getter Boolean bo = false;
	private @Getter Byte b = 0x00;
	private @Getter Short s = 0x00;
	private @Getter Integer i = 0x00;
	private @Getter Long l = 0L;
}
