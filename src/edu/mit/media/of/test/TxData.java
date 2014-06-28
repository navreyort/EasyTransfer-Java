package edu.mit.media.of.test;

import lombok.Getter;
import edu.mit.media.of.net.serial.protocol.SerialTransmitData;

/**
 * The order of types must be exactly the same as the struct in arduino
 * @author n4v
 *
 */
public class TxData extends SerialTransmitData {
	private @Getter Character c = ']';
	private @Getter Boolean bo = true;
	private @Getter Byte b = 10;
	private @Getter Short s = 140;
	private @Getter Integer i = 1230;
	private @Getter Long l = 104030L;
}
