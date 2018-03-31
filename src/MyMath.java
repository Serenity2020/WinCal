import java.math.BigDecimal;

public class MyMath {
	public static final int DEFAULT_SCALE = 20;//保留小数点位数
	
	public static double add(double num1,double num2){
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.add(second).doubleValue();
	}
	
	private static BigDecimal getBigDecimal(double number){
		return new BigDecimal(number);
	}
	
	public static double substract(double num1,double num2){
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.subtract(second).doubleValue();
	}
	
	public static double multiply(double num1,double num2){
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.multiply(second).doubleValue();
	}
	
	public static double divide(double num1,double num2){
		BigDecimal first = getBigDecimal(num1);
		BigDecimal second = getBigDecimal(num2);
		return first.divide(second, DEFAULT_SCALE,BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
