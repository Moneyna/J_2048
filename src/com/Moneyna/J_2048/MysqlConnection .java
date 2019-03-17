package src.com.Moneyna.J_2048;

//import com.mysql.cj.jdbc.Driver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Map;

class MysqlConnection {

		private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		private static final String DB_URL = "jdbc:mysql://localhost:3306/users";
		static final String   USERNAME = "root";
		static final String PASSWORD = "0Qtmmxhhbqdnlst";

		public void InsertUser(String userName, String pwd) {
			Connection con = null;
			Statement sta = null;

			try {

				Class.forName(JDBC_DRIVER);
				System.out.println("Connecting to database...");
				con = DriverManager.getConnection(DB_URL, USERNAME,PASSWORD);
				System.out.println("Creating statement...");

				pwd = Encryption(pwd);

				boolean flag=isExist(userName);
				if(flag)
				{
					JOptionPane.showMessageDialog(null,"用户名已存在！请尝试登录！");
					return;
				}

				sta = con.createStatement();
				String sql = "INSERT INTO users(user_name, user_pwd) VALUES(\'" + userName + "\',\'" + pwd + "\'); ";
				sta.executeUpdate(sql);

				sta.close();
				con.close();

			} catch (SQLException | ClassNotFoundException se) {//异常处理
				se.printStackTrace();
			} finally {
				try {
					if (sta != null) {
						sta.close();
					}
				} catch (SQLException se2) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException se) {
				}
			}
			JOptionPane.showMessageDialog(null,"注册成功！\n自动登录！");
			//new Client(Server.getNum()+1,BeginGame.getServer());  //TODO : 改逻辑
			battleRoom.setClient(new Client());
		}

		private boolean isExist(String userName)
		{
			Connection con = null;
			Statement sta = null;
			try {
				Class.forName(JDBC_DRIVER);
				System.out.println("Connecting to database...");
				con = DriverManager.getConnection(DB_URL, USERNAME,PASSWORD);
				System.out.println("Creating statement...");

				System.out.println("Creating statement...");
				sta = con.createStatement();
				String sql = "SELECT user_name , user_pwd FROM users WHERE user_name = \'" + userName+"\';";
				ResultSet rs = sta.executeQuery(sql);//使用statement对象执行简单的查询语句

				if(!rs.next())
				{
					return false;
				}
				else
				{
					return true;
				}

			} catch (SQLException | ClassNotFoundException se) {
				se.printStackTrace();
			} finally {
				try {
					if (sta != null) {
						sta.close();
					}
				} catch (SQLException se2) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException se) {
				}
			}
			return false;
		}

		public  void SelectUser(String userName, String pwd) {
			pwd = Encryption(pwd);
			Connection con = null;
			Statement sta = null;
			try {
				Class.forName(JDBC_DRIVER);
				System.out.println("Connecting to database...");
				con = DriverManager.getConnection(DB_URL, USERNAME,PASSWORD);
				System.out.println("Creating statement...");

				System.out.println("Creating statement...");
				sta = con.createStatement();
				String sql = "SELECT user_name , user_pwd FROM users WHERE user_name = \'" + userName+"\';";
				ResultSet rs = sta.executeQuery(sql);//使用statement对象执行简单的查询语句

				if(!rs.next())
				{
					JOptionPane.showMessageDialog(null,"用户名不存在！\n请先注册！");
				}
				else {
					String password = rs.getString("user_pwd");

					if (!pwd.equals(password)) {
						JOptionPane.showMessageDialog(null, "密码不正确！请重新输入！");
					}else
					{
						JOptionPane.showMessageDialog(null, "登录成功！");
						battleRoom.getSign_in().setVisible(false);
//						new Client(Server.getNum()+1,BeginGame.getServer());
						battleRoom.setClient(new Client());
					}

				}

				rs.close();
				sta.close();
				con.close();

			} catch (SQLException | ClassNotFoundException se) {//异常处理
				se.printStackTrace();
			} finally {
				try {
					if (sta != null) {
						sta.close();
					}
				} catch (SQLException se2) {
				}
				try {
					if (con != null) {
						con.close();
					}
				} catch (SQLException se) {
				}
			}
		}

		private String Encryption(String ss) {
			StringBuffer sss = new StringBuffer();
			for (int i = 0; i < ss.length(); i++) {
				char c = (char) ((int) ss.charAt(0) + 7);
				sss.insert(i, c);
			}
			ss = sss.toString();
			return ss;
		}

		private String Decryption(String ss) {
			StringBuffer sss = new StringBuffer();
			for (int i = 0; i < ss.length(); i++) {
				char c = (char) ((int) ss.charAt(0) - 7);
				sss.insert(i, c);
			}
			ss = sss.toString();
			return ss;
		}
	}
