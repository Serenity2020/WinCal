import java.math.BigDecimal;
public class CalService {
	//存储器，默认设为零，用于暂时存储计算结果
	private double storation = 0;
	//第一个操作数
	private String firstNum = null;
	//定义上一次操作的字符串变量
	private String lastOp = null;
	//第二个操作数
	private String secondNum = null;
	private boolean isSecondNum = false;//此行表示当前调入运算的是否为第二个操作数。如果是，则在文本框重新输入
	
	private String numString = "0123456789.";
	//四则运算
	private String opString = "+-*/";
	
	public CalService(){
		super();
	}
	
	public String callMethod(String cmd,String text) throws Exception{
		if (cmd.equals("C")) {
			return clearAll();
		} else if (cmd.equals("CE")) {
			return clear(text);
		} else if (cmd.equals("Back")) {
			return backSpace(text);
		} else if (numString.indexOf(cmd) != -1) {
			return catNum(cmd, text);
		} else if (opString.indexOf(cmd) != -1) {
			return setOp(cmd, text);
		} else if (cmd.equals("=")) {
			return cal(text, false);
		} else if (cmd.equals("+/-")) {
			return setNegative(text);
		} else if (cmd.equals("1/x")) {
			return setReciprocal(text);
		} else if (cmd.equals("sqrt")) {
			return sqrt(text);
		} else if (cmd.equals("%")) {
			return cal(text, true);
		} else {
			return mCmd(cmd, text);
		}
	}
	
	public String cal(String text,boolean isPercent) throws Exception{
		double secondResult = secondNum == null?Double.valueOf(text).doubleValue():Double.valueOf(secondNum).doubleValue();
		
		//如果除数为0，不处理
		if(secondResult == 0 && this.lastOp.equals("/")){
			return "0";
		}
		
		if(isPercent){
			secondResult = MyMath.multiply(Double.valueOf(firstNum),MyMath.divide(secondResult, 100));
			
		}
		
		//接下来是四则运算，返回结果赋值给第一个操作数
		if(this.lastOp.equals("+")){
			firstNum = String.valueOf(MyMath.add(Double.valueOf(firstNum), secondResult));
		}else if(this.lastOp.equals("-")){
			firstNum = String.valueOf(MyMath.substract(Double.valueOf(firstNum), secondResult));
		}else if(this.lastOp.equals("*")){
			firstNum = String.valueOf(MyMath.multiply(Double.valueOf(firstNum),secondResult));
		}else if(this.lastOp.equals("/")){
			firstNum = String.valueOf(MyMath.divide(Double.valueOf(firstNum), secondResult));
		}
		
		//给第二个操作数重新赋值
		secondNum = secondNum == null? text:secondNum;
		
		//把isSecondNum标志为true
		this.isSecondNum = true;
		return firstNum;
	}
	
	public String setReciprocal(String text){
		//如果text为0，则，则不求倒数
		if(text.equals("0")){
			return text;
		}else{
			//将isSecondNum设为true
			this.isSecondNum = true;
			return String.valueOf(MyMath.divide(1, Double.valueOf(text)));
		}
	}
	
	public String sqrt(String text){
		this.isSecondNum = true;
		return String.valueOf(Math.sqrt(Double.valueOf(text)));
	}
	
	public String setOp(String cmd,String text){
		//将此操作符号设置为上次的操作
		this.lastOp = cmd;
		//设置第一个操作数的值
		this.firstNum = text;
		//将第二个操作数赋值为空
		this.secondNum = null;
		this.isSecondNum = true;
		//返回空值
		return null;
	}
	
	public String setNegative(String text){
		//如果text是负数，则将它变为正数
		if(text.indexOf("-") == 0){
			return text.substring(1,text.length());
		}
		//否则，将正数变成 负数
		return text.equals("0")? text :"-"+text;
	}
	
	public String catNum(String cmd,String text){
		String result = cmd;
		if(!text.equals("0")){
			if(isSecondNum){
				//将isSecondNum设为true
				isSecondNum = false;
			}else{
				//返回的结果是目前的text加上新点的数字
				result = text + cmd;
			}
		}
		
		if(result.indexOf(".") == 0){
			result = "0"+result;
		}
		return result;
	}
	
	public String backSpace(String text){
		return text.equals("0") || text.equals("")? "0":text.substring(0,text.length()-1);
	}
	
	/*实现存储操作命令
	 * cmd
	 * --String的操作符号
	 * --text
	 * --String现在文本框的结果
	 * return String*/
	public String mCmd(String cmd,String text){
		if(cmd.equals("M+")){
			//如果是“M+”操作，把计算结果加入到storation中
			storation = MyMath.add(storation, Double.valueOf(text));
		}else if(cmd.equals("MC")){
			//如果是“MC”操作，则清楚storation
			storation = 0;
		}else if(cmd.equals("MR")){
			//如果是“MR”操作，则把storation的值读取出来
			isSecondNum = true;
			return String.valueOf(storation);
		}else if(cmd.equals("MS")){
			storation = Double.valueOf(text).doubleValue();
		}
		return null;
	}
	
	public String clearAll(){
		//将第一个第二个操作数恢复为默认值
		this.firstNum = "0";
		this.secondNum = null;
		return this.firstNum;
	}
	
	public String clear(String text){
		return "0";//清除上一次的计算结果
	}
	
	public double getStore(){
		return this.storation;
	}
}
