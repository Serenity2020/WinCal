import java.math.BigDecimal;
public class CalService {
	//�洢����Ĭ����Ϊ�㣬������ʱ�洢������
	private double storation = 0;
	//��һ��������
	private String firstNum = null;
	//������һ�β������ַ�������
	private String lastOp = null;
	//�ڶ���������
	private String secondNum = null;
	private boolean isSecondNum = false;//���б�ʾ��ǰ����������Ƿ�Ϊ�ڶ���������������ǣ������ı�����������
	
	private String numString = "0123456789.";
	//��������
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
		
		//�������Ϊ0��������
		if(secondResult == 0 && this.lastOp.equals("/")){
			return "0";
		}
		
		if(isPercent){
			secondResult = MyMath.multiply(Double.valueOf(firstNum),MyMath.divide(secondResult, 100));
			
		}
		
		//���������������㣬���ؽ����ֵ����һ��������
		if(this.lastOp.equals("+")){
			firstNum = String.valueOf(MyMath.add(Double.valueOf(firstNum), secondResult));
		}else if(this.lastOp.equals("-")){
			firstNum = String.valueOf(MyMath.substract(Double.valueOf(firstNum), secondResult));
		}else if(this.lastOp.equals("*")){
			firstNum = String.valueOf(MyMath.multiply(Double.valueOf(firstNum),secondResult));
		}else if(this.lastOp.equals("/")){
			firstNum = String.valueOf(MyMath.divide(Double.valueOf(firstNum), secondResult));
		}
		
		//���ڶ������������¸�ֵ
		secondNum = secondNum == null? text:secondNum;
		
		//��isSecondNum��־Ϊtrue
		this.isSecondNum = true;
		return firstNum;
	}
	
	public String setReciprocal(String text){
		//���textΪ0����������
		if(text.equals("0")){
			return text;
		}else{
			//��isSecondNum��Ϊtrue
			this.isSecondNum = true;
			return String.valueOf(MyMath.divide(1, Double.valueOf(text)));
		}
	}
	
	public String sqrt(String text){
		this.isSecondNum = true;
		return String.valueOf(Math.sqrt(Double.valueOf(text)));
	}
	
	public String setOp(String cmd,String text){
		//���˲�����������Ϊ�ϴεĲ���
		this.lastOp = cmd;
		//���õ�һ����������ֵ
		this.firstNum = text;
		//���ڶ�����������ֵΪ��
		this.secondNum = null;
		this.isSecondNum = true;
		//���ؿ�ֵ
		return null;
	}
	
	public String setNegative(String text){
		//���text�Ǹ�����������Ϊ����
		if(text.indexOf("-") == 0){
			return text.substring(1,text.length());
		}
		//���򣬽�������� ����
		return text.equals("0")? text :"-"+text;
	}
	
	public String catNum(String cmd,String text){
		String result = cmd;
		if(!text.equals("0")){
			if(isSecondNum){
				//��isSecondNum��Ϊtrue
				isSecondNum = false;
			}else{
				//���صĽ����Ŀǰ��text�����µ������
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
	
	/*ʵ�ִ洢��������
	 * cmd
	 * --String�Ĳ�������
	 * --text
	 * --String�����ı���Ľ��
	 * return String*/
	public String mCmd(String cmd,String text){
		if(cmd.equals("M+")){
			//����ǡ�M+���������Ѽ��������뵽storation��
			storation = MyMath.add(storation, Double.valueOf(text));
		}else if(cmd.equals("MC")){
			//����ǡ�MC�������������storation
			storation = 0;
		}else if(cmd.equals("MR")){
			//����ǡ�MR�����������storation��ֵ��ȡ����
			isSecondNum = true;
			return String.valueOf(storation);
		}else if(cmd.equals("MS")){
			storation = Double.valueOf(text).doubleValue();
		}
		return null;
	}
	
	public String clearAll(){
		//����һ���ڶ����������ָ�ΪĬ��ֵ
		this.firstNum = "0";
		this.secondNum = null;
		return this.firstNum;
	}
	
	public String clear(String text){
		return "0";//�����һ�εļ�����
	}
	
	public double getStore(){
		return this.storation;
	}
}
