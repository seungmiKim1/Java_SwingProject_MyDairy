import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class mdLogin extends JFrame implements ActionListener {
	JTextField userName,title1; JPasswordField passwd;
	JButton b1, b2, b3;
	private Font f1,f2; 

	mdLogin(String title){
		setTitle("MyDiary");
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(255,204,204));

		ImageIcon ic = new ImageIcon("C:\\Users\\Win10\\Desktop\\학교\\Pic\\mydairyLogo4.png");
		JLabel lblmage1 = new JLabel(ic);
		ct.add(lblmage1);
		lblmage1.setBounds(135,230,200,200);
		lblmage1.setVisible(true);
		
	
		JLabel l1 = new JLabel("사용자 이름");
		userName = new JTextField(10);
		l1.setBounds(110, 60, 70, 30);
		f1= new Font("바탕",Font.BOLD,12);
		l1.setFont(f1); 
		userName.setBounds(200, 60, 140, 30);
		ct.add(l1); ct.add(userName);

		JLabel l2 = new JLabel("비밀번호");
		passwd = new JPasswordField(10);
		l2.setBounds(110, 95, 70, 30);
		f2 = new Font("바탕",Font.BOLD,12);
		l2.setFont(f2);
		passwd.setBounds(200, 100, 140, 30);
		ct.add(l2); ct.add(passwd);
	
		b1 = new JButton("로그인");
		b2 = new JButton("재설정");
		b3 = new JButton("이용하기");

		b2.addActionListener(this);
		b3.addActionListener(this);

		b1.setBounds(5, 170, 150, 30);
		b1.setBackground(new Color(204,255,000));
	
		b2.setBounds(165, 170, 150, 30);
		b2.setBackground(new Color(204,255,000));
	

		b3.setBounds(325, 170, 150, 30);
		b3.setBackground(new Color(204,255,000));
		ct.add(b1);	ct.add(b2);	ct.add(b3);
	}
	public void actionPerformed(ActionEvent ae){
		String s = ae.getActionCommand();
		if ( s == "로그인" ){
			// 메인 캘린더 페이지로 이동 (try~catch로 try > 메인 캘린더 이동, catch : 잘못된 사용자입니다 메시지 출력)
		}
		else if ( s == "이용하기" ){
			NewMember my = new NewMember("MyDiary 이용하기");
			my.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			my.setSize(360, 300);
			my.setLocation(400, 300);
			my.show();
		}
		else {

			setPW sp = new setPW("PW 재설정하기");
			sp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			sp.setSize(360,300);
			sp.setLocation(400,300);
			sp.show();
	}

	
}
}

class setPW extends JFrame implements ActionListener { 
	private JTextField userName; private JPasswordField passwd;
	private JButton b3,b4;

	setPW(String title){ 
		setTitle("PW 재설정하기");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout(0,20)); 

		JPanel top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel l1 = new JLabel("사용자 이름");
		userName = new JTextField(10);
		
		p1.add(l1);	p1.add(userName);	
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel ll2 = new JLabel("새로운 비밀번호");
		passwd = new JPasswordField(10);
		p2.add(ll2);	 p2.add(passwd);
		
		
		top.add(p1);	top.add(p2);
		ct.add(top, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		b3 = new JButton("설정완료");
		b3.addActionListener(this);
		b4 = new JButton("취소");
		//b2.addActionListener(this);
		
		bottom.add(b3); bottom.add(b4);		
		ct.add(bottom, BorderLayout.SOUTH);
		}

	public void actionPerformed(ActionEvent ae){
		String s = ae.getActionCommand();
		String t_userName = "", t_passwd = "";
	
		if(s.equals("설정완료")) {
			
			try {
            			Class.forName("com.mysql.cj.jdbc.Driver");
         				System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
    		     } catch (ClassNotFoundException e) {
          				  System.err.println("드라이버 로드에 실패했습니다."); }
        			 try {
           				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?serverTimezone=UTC","root", "a12345");
            			System.out.println("DB 연결 완료");
				Statement dbSt = con.createStatement();
				System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
				t_userName = userName.getText(); 	t_passwd = passwd.getText();
				
				String strSql = "UPDATE user_info SET passwd = '"+t_passwd+"' WHERE userName = '"+t_userName+"';";
				dbSt.executeUpdate(strSql);
				MessageDialog md = new MessageDialog(this,"비밀번호 변경",true,"비밀번호가 변경되었습니다. 다시 로그인해주세요 :) ");
				md.show();
				System.out.println("데이터 수정 완료");
				//dbSt.close();
				//con.close();
			}catch ( SQLException e) {
				System.out.println( "SQLException : " + e.getMessage()); //e.printStackTrace(); 
			}
		}
	}


} 

class MessageDialog extends JDialog implements ActionListener { 
	JButton ok;

	MessageDialog(JFrame parent,String title,boolean mode,String msg){ 
		super(parent,title,mode);
		JPanel jp = new JPanel();
		JLabel label = new JLabel(msg);
		jp.add(label);
		add(jp,BorderLayout.CENTER); 

		JPanel jp2 = new JPanel();
		ok = new JButton("확인");
		ok.addActionListener(this);
		jp2.add(ok);
		add(jp2, BorderLayout.SOUTH);
		pack();
	} 
	public void actionPerformed(ActionEvent ae) { 
		dispose();
		}
} 

class NewMember extends JFrame implements ActionListener{
	JTextField userName; JPasswordField passwd;
	JButton b1, b2;

	NewMember(String title){
		setTitle("MyDiary 이용하기");
		Container ct = getContentPane();
		ct.setLayout(new BorderLayout(0, 20));
		
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel l1 = new JLabel("사용자 이름");
		userName = new JTextField(10);
		
		p1.add(l1);	p1.add(userName);	
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel l2 = new JLabel("비밀번호");
		passwd = new JPasswordField(10);
		p2.add(l2);	 p2.add(passwd);
		
		
		top.add(p1);	top.add(p2);
		ct.add(top, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel();
		b1 = new JButton("입력완료");
		b1.addActionListener(this);
		b2 = new JButton("취소");
		//b2.addActionListener(this);
		
		bottom.add(b1); bottom.add(b2);		
		ct.add(bottom, BorderLayout.SOUTH);
		}


	public void actionPerformed(ActionEvent ae){
		String s = ae.getActionCommand();
		
		String t_userName = "", t_passwd = "";
		
		if(s.equals("입력완료")) {
			
		    try {
            		Class.forName("com.mysql.cj.jdbc.Driver");
         			System.err.println("JDBC-ODBC 드라이버를 정상적으로 로드함");
    		     } catch (ClassNotFoundException e) {
          			System.err.println("드라이버 로드에 실패했습니다."); }
        			
 		        try {
                   		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?serverTimezone=UTC","root", "a12345");
                     		    System.out.println("DB 연결 완료");
            		    Statement dbSt = con.createStatement();
           		 	    System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
            		    t_userName = userName.getText();    t_passwd = passwd.getText();
            
            		    String strSql = "INSERT INTO user_info(userName,passwd) VALUES ("+"'"+t_userName+"','"+t_passwd+"')";
           
            		    dbSt.executeUpdate(strSql);
            		    System.out.println("데이터 삽입 완료");
            		    dbSt.close();
            		    con.close();
        			 } catch ( SQLException e) {
            	                System.out.println( "SQLException : " + e.getMessage()); //e.printStackTrace(); 
         }
      }
   }
}

class MyDiaryLogin_2 {
	public static void main(String args[]){
		mdLogin win = new mdLogin("로그인");
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.setSize(500,500);
		win.setLocation(100, 200);
		win.show();

		
		
		}
}