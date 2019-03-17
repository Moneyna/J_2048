package src.com.Moneyna.J_2048;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class sign_up extends JFrame {

	private MysqlConnection  mysql_c;

	private JPanel jp;
	private JTextField typeName;
	private JPasswordField typePwd;
	private JPasswordField sureTypePwd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					sign_up frame = new sign_up();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public sign_up() {
        mysql_c=new MysqlConnection();
		setTitle("J_2048 \u8D26\u53F7\u6CE8\u518C");  //J_2048注册
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO：更改逻辑
		setBounds(100, 100, 333, 228);
		jp = new JPanel();
		jp.setForeground(new Color(0, 0, 0));
		jp.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jp);
		jp.setLayout(null);
		
		setSize(350, 230);
        setLocation(500, 252);
        jp.setOpaque(true);
        jp.setBackground(new Color(0, 206, 209));
        jp.setLayout(null);
        
        JLabel name = new JLabel("\u8D26\u53F7");  //账号
        name.setOpaque(true);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setForeground(Color.BLACK);
        name.setFont(new Font("账号", Font.PLAIN, 15));
        name.setBackground(new Color(152, 251, 152));
        name.setBounds(41, 24, 46, 22);
        jp.add(name);
        
        typeName = new JTextField();
        typeName.setHorizontalAlignment(SwingConstants.RIGHT);
        typeName.setColumns(10);
        typeName.setBounds(102, 26, 127, 21);
        jp.add(typeName);
        
        JLabel tip1 = new JLabel("(四位)");  
        tip1.setOpaque(true);
        tip1.setHorizontalAlignment(SwingConstants.CENTER);
        tip1.setForeground(new Color(240, 248, 255));
        tip1.setBackground(new Color(95, 158, 160));
        tip1.setBounds(240, 29, 54, 15);
        jp.add(tip1);
        
        JLabel pwd = new JLabel("\u5BC6\u7801");  //密码
        pwd.setOpaque(true);
        pwd.setHorizontalAlignment(SwingConstants.CENTER);
        pwd.setForeground(Color.BLACK);
        pwd.setFont(new Font("密码", Font.PLAIN, 15));
        pwd.setBackground(new Color(152, 251, 152));
        pwd.setBounds(41, 59, 46, 22);
        jp.add(pwd);
        
        typePwd = new JPasswordField(5);
        typePwd.setEchoChar('*');
        typePwd.setHorizontalAlignment(SwingConstants.RIGHT);
        typePwd.setColumns(10);
        typePwd.setBounds(102, 63, 127, 21);
        jp.add(typePwd);
        
        JLabel tip2 = new JLabel("(五位)");  //
        tip2.setOpaque(true);
        tip2.setHorizontalAlignment(SwingConstants.CENTER);
        tip2.setForeground(new Color(240, 248, 255));
        tip2.setBackground(new Color(95, 158, 160));
        tip2.setBounds(238, 64, 54, 15);
        jp.add(tip2);
        
        JLabel surePwd = new JLabel("\u786E\u8BA4\u5BC6\u7801");  //确认密码
        surePwd.setOpaque(true);
        surePwd.setHorizontalAlignment(SwingConstants.CENTER);
        surePwd.setForeground(Color.BLACK);
        surePwd.setFont(new Font("确认密码", Font.PLAIN, 11));
        surePwd.setBackground(new Color(152, 251, 152));
        surePwd.setBounds(41, 97, 46, 22);
        jp.add(surePwd);
        
        JLabel tip3 = new JLabel("(五位)");
        tip3.setOpaque(true);
        tip3.setHorizontalAlignment(SwingConstants.CENTER);
        tip3.setForeground(new Color(240, 248, 255));
        tip3.setBackground(new Color(95, 158, 160));
        tip3.setBounds(238, 100, 54, 15);
        jp.add(tip3);
        
        sureTypePwd = new JPasswordField(5);
        sureTypePwd.setEchoChar('*');
        sureTypePwd.setHorizontalAlignment(SwingConstants.RIGHT);
        sureTypePwd.setColumns(10);
        sureTypePwd.setBounds(102, 96, 127, 21);
        jp.add(sureTypePwd);	
	
        JButton signIn = new JButton("\u767B\u5F55");  //登录
 	    signIn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                         battleRoom.getSign_up().setVisible(false);
                         battleRoom.setSign_in(new Sign_in());
                    }
                });
        signIn.setBounds(190, 145, 93, 23);
        jp.add(signIn);
        
        JButton signUp = new JButton("\u6CE8\u518C");  //注册
	    signUp.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String name= typeName.getText();
                        String pwd1= String.valueOf(typePwd.getPassword());
                        String pwd2= String.valueOf(sureTypePwd.getPassword());

                        if(name.isEmpty()||pwd1.isEmpty()||pwd2.isEmpty())
                        {
                            JOptionPane.showMessageDialog(null,"用户名或密码不能为空！");
                        }else {
				if(name.length()!=4||pwd1.length()!=5||pwd2.length()!=5)
				{
	 			    JOptionPane.showMessageDialog(null, "账号或密码长度不符合要求！");
				}else{
                            		if (!pwd1.equals(pwd2)) {
                                            JOptionPane.showMessageDialog(null, "两次输入密码不匹配！\n请重新输入！");
                            } else {
                                mysql_c.InsertUser(name, pwd1);
                                battleRoom.getSign_up().setVisible(false);
			
                            }
                        }
                    }
}
                });
        signUp.setBounds(57, 145, 93, 23);
        jp.add(signUp);

        setVisible(true);
	}

}
