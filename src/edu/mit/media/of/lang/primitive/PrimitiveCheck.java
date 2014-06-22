package edu.mit.media.of.lang.primitive;

import lombok.NonNull;

public class PrimitiveCheck {

	public static byte getByteSize(@NonNull Object primitive){
		if(primitive instanceof Byte || primitive instanceof Boolean){
			return (byte) PrimitiveCheck.getByteSizeOfByte();
		}
		else if(primitive instanceof Short){
			return (byte) PrimitiveCheck.getByteSizeOfShort();
		}
		else if(primitive instanceof Integer){
			return (byte) PrimitiveCheck.getByteSizeOfInteger();
		}
		else if(primitive instanceof Long){
			return (byte) PrimitiveCheck.getByteSizeOfLong();
		}
		else if(primitive instanceof Character){
			return (byte) PrimitiveCheck.getByteSizeOfCharacter();
		}
		return 0;
	}
	
	public static boolean isNumber(@NonNull Class<?> cls){
		if(!cls.getSuperclass().isInstance(Number.class)){
			return true;
		}
		return false;
	}
	
	public static boolean isCharacter(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Character")){
			return true;
		}
		return false;
	}
	
	public static boolean isBoolean(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Boolean")){
			return true;
		}
		return false;
	}
	
	public static boolean isByte(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Byte")){
			return true;
		}
		return false;		
	}
	
	public static boolean isInteger(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Integer")){
			return true;
		}
		return false;		
	}
	
	public static boolean isShort(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Short")){
			return true;
		}
		return false;		
	}
	
	public static boolean isLong(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Long")){
			return true;
		}
		return false;		
	}
	
	public static boolean isFloat(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Float")){
			return true;
		}
		return false;		
	}
	
	public static boolean isDouble(@NonNull Class<?> cls){
		if(cls.getName().equalsIgnoreCase("java.lang.Double")){
			return true;
		}
		return false;		
	}

	public static int getByteSizeOfByte() {
		return Byte.SIZE / Byte.SIZE;
	}
	
	public static int getByteSizeOfShort() {
		return Short.SIZE / Byte.SIZE;
	}
	
	public static int getByteSizeOfInteger() {
		return Integer.SIZE / Byte.SIZE;
	}
	
	public static int getByteSizeOfLong() {
		return Long.SIZE / Byte.SIZE;
	}
	
	public static int getByteSizeOfFloat() {
		return Float.SIZE / Byte.SIZE;
	}
	
	public static int getByteSizeOfDouble() {
		return Double.SIZE / Byte.SIZE;
	}
	
	public static int getByteSizeOfCharacter() {
		return Character.SIZE / Byte.SIZE;
	}
	
	public static int getByteSizeOfBoolean() {
		return Byte.SIZE / Byte.SIZE;
	}
}
