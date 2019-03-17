package src.com.Moneyna.J_2048;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Sign_in extends JFrame {

	private MysqlConnection mysql_c;
	private JPanel jp;
	private JTextField typeName;
	private JPasswordField typePwd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Sign_in frame = new Sign_in();
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
	public Sign_in() {
	    mysql_c=new MysqlConnection();
		setTitle("J_2048 \u623F\u95F4\u767B\u5F55");  //J_2048登录
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO：更换逻辑
		setBounds(100, 100, 450, 300);
		jp = new JPanel();
		jp.setForeground(new Color(0, 0, 0));
		jp.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(jp);
		
		 setSize(350, 230);
         setLocation(500, 252);
         jp.setOpaque(true);
         jp.setBackground(new Color(0, 206, 209));
         jp.setLayout(null);
         
         JLabel name = new JLabel("\u8D26\u53F7"); //账号
         name.setOpaque(true);
         name.setFont(new Font("账号", Font.PLAIN, 15));
         name.setHorizontalAlignment(SwingConstants.CENTER);
         name.setForeground(new Color(0, 0, 0));
         name.setBounds(44, 30, 46, 22);
         name.setBackground(new Color(152, 251, 152));
         jp.add(name);
         
         typeName = new JTextField();
         typeName.setHorizontalAlignment(SwingConstants.RIGHT);
         typeName.setBounds(100, 31, 127, 21);
         jp.add(typeName);
         typeName.setColumns(10);
         
         JLabel tip1 = new JLabel("(四位)");  
         tip1.setForeground(new Color(240, 248, 255));
         tip1.setBackground(new Color(95, 158, 160));
         tip1.setOpaque(true);
         tip1.setHorizontalAlignment(SwingConstants.CENTER);
         tip1.setBounds(237, 34, 54, 15);
         jp.add(tip1);
         
         JLabel pwd = new JLabel("\u5BC6\u7801");  //密码
         pwd.setOpaque(true);
         pwd.setHorizontalAlignment(SwingConstants.CENTER);
         pwd.setForeground(Color.BLACK);
         pwd.setFont(new Font("密码", Font.PLAIN, 15));
         pwd.setBackground(new Color(152, 251, 152));
         pwd.setBounds(44, 75, 46, 22);
         jp.add(pwd);
         
         typePwd = new JPasswordField();
         typePwd.setHorizontalAlignment(SwingConstants.RIGHT);
         typePwd.setColumns(10);
         typePwd.setBounds(100, 75, 127, 21);
         jp.add(typePwd);
         
         JLabel tip2 = new JLabel("(五位)"); 
         tip2.setOpaque(true);
         tip2.setHorizontalAlignment(SwingConstants.CENTER);
         tip2.setForeground(new Color(240, 248, 255));
         tip2.setBackground(new Color(95, 158, 160));
         tip2.setBounds(237, 77, 54, 15);
         jp.add(tip2);
         
         JButton signIn = new JButton("\u767B\u5F55");   //登录
       	 signIn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        String name= typeName.getText();
                        String pwd= String.valueOf(typePwd.getPassword());

                        System.out.println(name);
                        System.out.println(pwd);

                        if(name.isEmpty()|pwd.isEmpty())
                        {
                            JOptionPane.showMessageDialog(null,"用户名或密码不能为空！");
                        }else{

			                if(name.length()!=4)
			                {
	 			               JOptionPane.showMessageDialog(null,"账户名称只支持四位长！");
			                }else{

				                if(pwd.length()!=5)
			                    {
				                    JOptionPane.showMessageDialog(null,"密码只支持五位长！");
				                }else{

				                    mysql_c.SelectUser(name,pwd);
                                    //battleRoom.getSign_in().setVisible(false);
                        	    }
			                }
                        
			            }
                    }
                });
         signIn.setBounds(65, 125, 93, 23);
         jp.add(signIn);
         
         JButton signUp = new JButton("\u6CE8\u518C");   //注册
	     signUp.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        battleRoom.getSign_in().setVisible(false);
			            battleRoom.setSign_up(new sign_up());
                   }
                });
         signUp.setBounds(172, 125, 93, 23);
         jp.add(signUp);

         setVisible(true);
	}
}
