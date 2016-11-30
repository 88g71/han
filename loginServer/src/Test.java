
public class Test {
	public static void main(String[] args) {
		byte a = -127;
		byte b = -128;
		byte c = (byte) ((a&b));
		System.out.println(c);
		
		byte d = (byte) (c>>7);
		System.out.println(d);
		System.out.println(c);
		System.out.println(127>>2);
	}
}
