package edu.mit.media.of.test;

import lombok.Getter;
import edu.mit.media.of.net.serial.protocol.SerialTransmitData;

public class TxData extends SerialTransmitData {
	private @Getter Byte b = 1;
	private @Getter Short s = 140;
	private @Getter Integer i = 1230;
	private @Getter Long l = 104030L;
	private @Getter Character c = ']';
	private @Getter Boolean bo = false;
}
