package edu.mit.media.of.net.serial.protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Arrays;

import lombok.NonNull;
import lombok.extern.java.Log;

import org.apache.commons.lang3.ArrayUtils;

import edu.mit.media.of.lang.primitive.ByteArrayConversion;
import edu.mit.media.of.lang.primitive.PrimitiveCheck;

/**
 * The class assumes no float and double but only integer types and character.
 * @author n4v
 *
 */
@Log
public abstract class SerialTransmitData {

	public static final byte SERIAL_INIT1 = 0x06;
	public static final byte SERIAL_INIT2 = 0x15;
	private static final byte ATTACHED_PACKET_SIZE = 4;
	private static final byte DATA_SIZE_INDEX = 2;
	private static final byte DATA_START_INDEX = 3;
	
	/**
	 * Converts fields into byte array representation with packet header and checksum footer.
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public ByteBuffer toByteBuffer() {
		byte size = this.getDataSize();
		byte checksum = size;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

		//Append packet header
		byteStream.write(SerialTransmitData.SERIAL_INIT1);
		byteStream.write(SerialTransmitData.SERIAL_INIT2);
		byteStream.write(size);

		//append data
		for(Field field : this.getClass().getDeclaredFields()){
			field.setAccessible(true);
			if(PrimitiveCheck.isNumber(field.getType()) || PrimitiveCheck.isCharacter(field.getType()) 
					|| PrimitiveCheck.isBoolean(field.getType())){
				try {
					byte[] d = ByteArrayConversion.toByta(field.get(this));
					ArrayUtils.reverse(d);
					byteStream.write(d);
				} catch (Throwable e) {
					log.warning(e.getMessage());
				}
			}
		}

		//Create checksum
		byte[] txData = byteStream.toByteArray();
		for (int i=DATA_START_INDEX; i<txData.length; i++){
			checksum^=txData[i];
		} 

		//Checksum footer
		byteStream.write(checksum);

		return ByteBuffer.wrap(byteStream.toByteArray());
	}

	public void toData(@NonNull ByteBuffer buffer){
		byte[] data = buffer.array();
		
		byte checkSum = data[DATA_SIZE_INDEX];
		for(int i=DATA_START_INDEX;i<data.length-1;i++){
			checkSum ^= data[i];
		}

		if(checkSum == data[data.length-1]){
			int curIndex = DATA_START_INDEX;

			for(Field field : this.getClass().getDeclaredFields()){
				field.setAccessible(true);
				try {
					if(PrimitiveCheck.isBoolean(field.getType())){
						ByteArrayInputStream byte_in = new ByteArrayInputStream (Arrays.copyOfRange(data, curIndex, curIndex+PrimitiveCheck.getByteSizeOfBoolean()));
						DataInputStream data_in = new DataInputStream (byte_in);
						field.set(this, data_in.readBoolean());
						curIndex += PrimitiveCheck.getByteSizeOfBoolean();
					}
					else if(PrimitiveCheck.isByte(field.getType())){
						ByteArrayInputStream byte_in = new ByteArrayInputStream (Arrays.copyOfRange(data, curIndex, curIndex+PrimitiveCheck.getByteSizeOfByte()));
						DataInputStream data_in = new DataInputStream (byte_in);
						field.set(this, data_in.readByte());
						curIndex += PrimitiveCheck.getByteSizeOfByte();
					}
					else if(PrimitiveCheck.isShort(field.getType())){
						byte[] d = Arrays.copyOfRange(data, curIndex, curIndex+PrimitiveCheck.getByteSizeOfShort());
						ArrayUtils.reverse(d);
						ByteArrayInputStream byte_in = new ByteArrayInputStream (d);
						DataInputStream data_in = new DataInputStream (byte_in);
						field.set(this, data_in.readShort());
						curIndex += PrimitiveCheck.getByteSizeOfShort();
					}
					else if(PrimitiveCheck.isInteger(field.getType())){
						byte[] d = Arrays.copyOfRange(data, curIndex, curIndex+PrimitiveCheck.getByteSizeOfInteger());
						ArrayUtils.reverse(d);
						ByteArrayInputStream byte_in = new ByteArrayInputStream (d);
						DataInputStream data_in = new DataInputStream (byte_in);
						field.set(this, data_in.readInt());
						curIndex += PrimitiveCheck.getByteSizeOfInteger();
					}
					else if(PrimitiveCheck.isLong(field.getType())){
						byte[] d = Arrays.copyOfRange(data, curIndex, curIndex+PrimitiveCheck.getByteSizeOfLong());
						ArrayUtils.reverse(d);
						ByteArrayInputStream byte_in = new ByteArrayInputStream (d);
						DataInputStream data_in = new DataInputStream (byte_in);
						field.set(this, data_in.readLong());
						curIndex += PrimitiveCheck.getByteSizeOfLong();
					}
					else if(PrimitiveCheck.isCharacter(field.getType())){
						byte[] d = Arrays.copyOfRange(data, curIndex, curIndex+PrimitiveCheck.getByteSizeOfCharacter());
						ArrayUtils.reverse(d);
						ByteArrayInputStream byte_in = new ByteArrayInputStream (d);
						DataInputStream data_in = new DataInputStream (byte_in);
						field.set(this, data_in.readChar());
						curIndex += PrimitiveCheck.getByteSizeOfCharacter();
					}
				} catch (Throwable e) {
					log.warning(e.getMessage());
				}
			}
		}
	}

	public byte getDataSize(){
		byte size = 0;
		for(Field field : this.getClass().getDeclaredFields()){
			field.setAccessible(true);
			if(PrimitiveCheck.isNumber(field.getType()) || PrimitiveCheck.isCharacter(field.getType()) 
					|| PrimitiveCheck.isBoolean(field.getType())){
				try {
					size += PrimitiveCheck.getByteSize(field.get(this));
				} catch (Throwable e) {
					log.warning(e.getMessage());
				}
			}
		}
		return size;
	}

	/**
	 * Gets packet byte size 
	 * @return
	 */
	public byte getPacketSize(){ 
		return (byte) (this.getDataSize() + SerialTransmitData.ATTACHED_PACKET_SIZE);
	}

	public int getHeaderSize() {
		return SerialTransmitData.ATTACHED_PACKET_SIZE - 1;
	}
}
