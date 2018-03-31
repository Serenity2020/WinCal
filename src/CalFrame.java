import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalFrame extends JFrame{
	//显示计算结果的textField
	private JTextField tf = null;
	//用一个数组保存MC，MR，MS，M+等操作符
	private String[] mOp = {"MC","MR","MS","M+"};
	//用一个数组保存结果操作符
	private String[] rOp = {"Back","CE","C"};
	//用一个数组保存数字和运算操作符
	private String[] nOp = {"7","8","9","/","sqrt","4","5","6","*","%","1","2","3","-","1/x","0","+/-",".","+","="};
	
	private JButton button = null;
	private CalService service = new CalService();
	private ActionListener actionListener = null;
	private final int pre_width = 360;
	private final int pre_height = 250;
	
	public CalFrame(){
		super();
		initialize();
	}
	
	private void initialize(){
		this.setTitle("计算器");
		this.setResizable(true);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10,1));
		panel.add(getTextField(), BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(pre_width,pre_height));
		
		//增加左边存储操作键
		JButton[] mButton = getMButton();
		JPanel panel1 = new JPanel();
		
		//设置布局管理器
		panel1.setLayout(new GridLayout(5,1,0,5));
		
		//迭代增加按钮
		for(JButton b:mButton){
			panel1.add(b);
		}
		
		//增加结果操作键
		JButton[] rButton = getRButton();
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BorderLayout(1,5));
		
		//再新建一个panel，用于放置按钮
		JPanel panel21 = new JPanel();
		
		//设置布局管理器
		panel21.setLayout(new GridLayout(1, 3, 3, 3));
		
		for(JButton b:rButton){
			panel21.add(b);
		}
		
		JButton[] nButton = getNButton();//增加数字和其他运算符的按钮
		//再新建一个panel，第二列下方的，用于放置nbutton
		JPanel panel22 = new JPanel();
		panel22.setLayout(new GridLayout(4, 5, 3, 5));
		
		for(JButton b:nButton){
			panel22.add(b);
		}
		
		panel2.add(panel21, BorderLayout.NORTH);
		panel2.add(panel22,BorderLayout.CENTER);
		panel.add(panel1, BorderLayout.WEST);
		panel.add(panel2, BorderLayout.CENTER);
		this.add(panel);
	}
	
	private JTextField getTextField(){
		if(tf == null){
			tf = new JTextField("0");
			tf.setEditable(false);
			tf.setBackground(Color.white);
		}
		return tf;
	}
	
	private JButton[] getMButton(){
		JButton[] result = new JButton[mOp.length + 1];
		result[0] = getButton();
		for(int i = 0;i<this.mOp.length;i++){
			//新建按钮
			JButton b = new JButton(this.mOp[i]);//新建按钮的同时，给按钮上加上文本，文本存储在mOp数组中
			b.addActionListener(getActionListener());
			b.setForeground(Color.red);
			result[i+1] = b;
		}
		return result;
	}
	
	private JButton getButton(){
		if(button == null){
			//设置默认值为0
			button = new JButton();
		}
		return button;//标志存储结果类型的按钮
	}
	
	private JButton[] getRButton(){
		JButton[] result = new JButton[rOp.length];
		for(int i = 0;i<this.rOp.length;i++){
			JButton b = new JButton(this.rOp[i]);
			b.addActionListener(getActionListener());
			b.setForeground(Color.red);
			result[i] = b;
		}
		return result;
	}
	
	private JButton[] getNButton(){
		//这个数组保存需要设置为红色的操作符
		String[] redButton = {"/","*","-","+","="};
		JButton[] result = new JButton[nOp.length];
		for(int i= 0;i<this.nOp.length;i++){
			JButton b = new JButton(this.nOp[i]);
			b.addActionListener(getActionListener());
			Arrays.sort(redButton);
			if(Arrays.binarySearch(redButton, nOp[i]) >= 0){
				b.setForeground(Color.red);
			}else {
				b.setForeground(Color.blue);
			}
			result[i] = b;
		}
		return result;
	} 
	public ActionListener getActionListener(){
		if(actionListener == null){
			actionListener = new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String cmd = e.getActionCommand();
					String result = null;
					try{
						//计算操作结果
						result = service.callMethod(cmd, tf.getText());
					}catch(Exception e1){
						System.out.println(e1.getMessage());
					}
					
					if(cmd.indexOf("MC") == 0){
						button.setText("");
					}else if(cmd.indexOf("M") == 0 && service.getStore()>0){
						button.setText("M");
					}
					
					if(result != null){
						tf.setText(result);
					}
				}
				
			};
		}
		return actionListener;
	}
}
