import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

class mdLogin extends JFrame implements ActionListener {
    JTextField userName,title1; JPasswordField passwd;
    private PreparedStatement pstmt;
    private Connection conn;
    private ResultSet rs;
    JButton b1, b2, b3;
    private Font f1,f2;

    mdLogin(String title){
        setTitle("MyDiary");
        Container ct = getContentPane();
        ct.setLayout(null);
        ct.setBackground(new Color(255,204,204));

        ImageIcon ic = new ImageIcon("C:\\Users\\minii\\Desktop\\Pic\\mydairylogo4.png");
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

        b1.addActionListener(this);
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

        if (s == "로그인") {
                JavaSwingCalendar jw = new JavaSwingCalendar("달력");
                jw.setSize(400, 550);
                jw.setLocation(400, 300);
                jw.setVisible(true);
                dispose();
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
        b4.addActionListener(this);

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
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student2?serverTimezone=UTC","root", "tjdals35");
                System.out.println("DB 연결 완료");
                Statement dbSt = con.createStatement();
                System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");
                t_userName = userName.getText(); 	t_passwd = passwd.getText();

                String strSql = "UPDATE user_info SET passwd = '"+t_passwd+"' WHERE userName = '"+t_userName+"';";
                dbSt.executeUpdate(strSql);
                MessageDialog md = new MessageDialog(this,"비밀번호 변경",true,"비밀번호가 변경되었습니다. 다시 로그인해주세요 :) ");
                md.show();
                System.out.println("데이터 수정 완료");
                dbSt.close();
                con.close();
            }catch ( SQLException e) {
                System.out.println( "SQLException : " + e.getMessage()); //e.printStackTrace();
            }
        } else dispose();

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
        b2.addActionListener(this);

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
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student2?serverTimezone=UTC","root", "tjdals35");
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
        else dispose();
    }
}

class JavaSwingCalendar extends JFrame implements ActionListener{

    JPanel p1, p2, p3;
    Container ct;
    JComboBox year, month;
    JButton beforemonth, nextmonth, buttonDday;
    JTextField tfDdayYear, tfDdayMonth, tfDdayDay;
    JLabel labelDdayResult;

    public static void main(String[] args) {
        JavaSwingCalendar frame = new JavaSwingCalendar("달력");
        frame.setVisible(true);
    }

    JavaSwingCalendar(String title) {

        setTitle(title);
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ct = getContentPane();
        ct.setLayout(new BoxLayout(ct, BoxLayout.Y_AXIS));

        p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        String[] years = new String[30];
        for(int i = 0; i < 30; i++) {
            years[i] = Integer.toString(i + 2000);
        }
        year = new JComboBox();
        month = new JComboBox(months);
        year = new JComboBox(years);
        year.setSelectedIndex(21);
        month.setSelectedIndex(11);

        JLabel labelyear = new JLabel("년");
        JLabel labelmonth = new JLabel("월");
        beforemonth = new JButton("<");
        nextmonth = new JButton(">");

        ClickBeforeMonth bm = new ClickBeforeMonth(month, year, months, years);
        beforemonth.addActionListener(bm);
        ClickNextMonth nm = new ClickNextMonth(month, year, months, years);
        nextmonth.addActionListener(nm);

        p2.add(beforemonth);
        p2.add(year);
        p2.add(labelyear);
        p2.add(month);
        p2.add(labelmonth);
        p2.add(nextmonth);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(0, 7));
        p1.setPreferredSize(new Dimension(400, 200));
        ArrayList<JButton> buttonArray = new ArrayList<JButton>();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(month.getSelectedItem().toString()));
        cal.set(Calendar.MONTH, Integer.valueOf(month.getSelectedItem().toString()));
        int lastday = cal.getActualMaximum(Calendar.DATE);
        cal.add(Calendar.DATE, -cal.get(Calendar.DATE) + 1);
        int offset = cal.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < offset; i++)
            buttonArray.add(new JButton(" "));

        for (int i = 1; i <= lastday; i++)
            buttonArray.add(new JButton(String.valueOf(i)));

        Iterator<JButton> ite = buttonArray.iterator();

        while (ite.hasNext()) {
            JButton button = (JButton) ite.next();
            p1.add(button);
        }

        DayClickButton dcb = new DayClickButton(buttonArray, year, month);
        for(JButton buttons : buttonArray) {
            buttons.addActionListener(dcb);
        }

        p3 = new JPanel();
        p3.setLayout(null);
        JLabel labelDdayYear, labelDdayMonth, labelDdayDay, labelDdayDesc;
        labelDdayDesc = new JLabel("D-Day 설정 : ");
        labelDdayYear = new JLabel("년");
        labelDdayMonth = new JLabel("월");
        labelDdayDay = new JLabel("일");
        buttonDday = new JButton("설정");
        labelDdayResult = new JLabel("");

        tfDdayYear = new JTextField();
        tfDdayMonth = new JTextField();
        tfDdayDay = new JTextField();

        labelDdayDesc.setBounds(20, 20, 90, 30); tfDdayYear.setBounds(113, 20, 50, 30);
        labelDdayYear.setBounds(168, 20, 20, 30); tfDdayMonth.setBounds(193, 20, 30, 30);
        labelDdayMonth.setBounds(228, 20, 20, 30); tfDdayDay.setBounds(253, 20, 30, 30);
        labelDdayDay.setBounds(288, 20, 20, 30); buttonDday.setBounds(313, 20, 30, 30);
        labelDdayResult.setBounds(20, 60, 300, 30);

        p3.add(labelDdayDesc); p3.add(tfDdayYear); p3.add(labelDdayYear); p3.add(tfDdayMonth);
        p3.add(labelDdayMonth); p3.add(tfDdayDay); p3.add(labelDdayDay); p3.add(buttonDday);
        p3.add(labelDdayResult);

        buttonDday.addActionListener(this);

        ct.add(p2); ct.add(p1); ct.add(p3);

    }
    public void actionPerformed(ActionEvent ae) {
        String stringbtn = ae.getActionCommand();
        String strresult = tfDdayYear.getText().toString() + "년 " + tfDdayMonth.getText().toString() + "월 " + tfDdayDay.getText().toString() + "일로 D-Day가 설정되었습니다.";
        if(stringbtn.equals("설정")) {
            labelDdayResult.setText(strresult);
        }

    }
}

class ClickBeforeMonth implements ActionListener {
    JComboBox month, year;
    String[] m, y;
    int tm;
    ClickBeforeMonth(JComboBox boxmonth, JComboBox boxyear, String[] months, String[] years) {
        m = months;
        y = years;
        month = boxmonth;
        year = boxyear;
    }
    public void actionPerformed(ActionEvent ae) {
        try {
            int thismonth = Integer.valueOf(month.getSelectedItem().toString());
            tm = thismonth - 1;
            month.setSelectedItem((Object)m[tm - 1]);
        } catch(ArrayIndexOutOfBoundsException e) {
            try {int thisyear = Integer.valueOf(year.getSelectedItem().toString()) - 2000;
                year.setSelectedItem((Object)y[thisyear - 1]);
                month.setSelectedItem((Object)m[11]);
            } catch(ArrayIndexOutOfBoundsException e2) {
                System.out.println("현재는 2000년까지만 지원합니다!");
            }
        }
    }
}

class ClickNextMonth implements ActionListener {
    JComboBox month, year;
    String[] m, y;
    int tm;
    ClickNextMonth(JComboBox boxmonth, JComboBox boxyear, String[] months, String[] years) {
        m = months;
        y = years;
        month = boxmonth;
        year = boxyear;
    }
    public void actionPerformed(ActionEvent ae) {
        try {
            int thismonth = Integer.valueOf(month.getSelectedItem().toString());
            tm= thismonth + 1;
            month.setSelectedItem((Object)m[tm - 1]);
        } catch(ArrayIndexOutOfBoundsException e) {
            try {int thisyear = Integer.valueOf(year.getSelectedItem().toString()) - 2000;
                year.setSelectedItem((Object)y[thisyear + 1]);
                month.setSelectedItem((Object)m[0]);
            } catch(ArrayIndexOutOfBoundsException e2) {
                System.out.println("현재는 2029년까지만 지원합니다!");
            }
        }
    }
}

class DayClickButton implements ActionListener {
    ArrayList<JButton> buttonArray = new ArrayList<JButton>();
    int year, month, day;
    JComboBox comboYear, comboMonth;

    DayClickButton(ArrayList<JButton> buttonArray, JComboBox y, JComboBox m) {
        this.buttonArray = buttonArray;
        comboYear = y;
        comboMonth = m;
    }

    public void actionPerformed(ActionEvent ae) {
        year = Integer.valueOf(comboYear.getSelectedItem().toString());
        month = Integer.valueOf(comboMonth.getSelectedItem().toString());
        day = Integer.valueOf(ae.getActionCommand());
        System.out.println(year + "년 " + month + "월 " + day + "일 선택됨!");
        Show show = new Show("일기", year, month, day);
        show.setSize(500, 800);
        show.setLocation( 400,100);
        show.setVisible(true);
    }
}


class Show extends JFrame implements ActionListener{ //일기 편집전 일기장 상태
    private JFileChooser jfc = new JFileChooser();  //사진 파일 올릴 Filechooser 선언
    Image img;
    String name = "";
    String path = ""; // 불러올 이미지 파일의 경로
    static String txt;  //일기장 내용 텍스트
    static JTextField text;  //일기장 텍스트 필드
    JButton jb, jb2, jb3,jb4, jb5;  // 액션 리스너 벝튼
    JLabel imgLabel, labelYear, labelMonth, labelDay;
    JScrollPane jscp1; // 일기장 스크롤
    int selYear, selMonth, selDay;




    Show(String title, int selectedYear, int selectedMonth, int selectedDay){  //일기 보기 메소드
        setTitle("일기");
        selYear = selectedYear; selMonth = selectedMonth; selDay = selectedDay;

        Container ct = getContentPane();

        Dimension dim = new Dimension(500, 800); // 일기장 크기 설정

        ct.setPreferredSize(dim);
        String txt = new String();

        JTextField text = new JTextField(txt);  // 일기 내용창 선언 및 배치
        JScrollPane jscp1 = new JScrollPane(text);
        text.setText(txt);
        text.setEnabled(false);  //일기를 확인하는 창이기에 편집을 불가하도록 설정


        jscp1.setLocation(40, 200);
        jscp1.setSize(400, 500);

        ct.add(jscp1);

        labelYear = new JLabel(Integer.toString(selYear) + "년");
        labelMonth = new JLabel(Integer.toString(selMonth) + "월");
        labelDay = new JLabel(Integer.toString(selDay) + "일");
        labelYear.setLocation(260, 50);
        labelYear.setSize(80, 50);
        labelYear.setFont(new Font("serif", Font.BOLD, 20));
        labelMonth.setLocation(320, 50);
        labelMonth.setSize(80, 50);
        labelMonth.setFont(new Font("Serif", Font.BOLD, 20));
        labelDay.setLocation(380, 50);
        labelDay.setSize(80, 50);
        labelDay.setFont(new Font("Serif", Font.BOLD, 20));
        ct.add(labelYear); ct.add(labelMonth); ct.add(labelDay);

        Button jb = new Button("save"); //일기의 내용을 저장하는 버튼
        jb.setLocation(120, 720);
        jb.setSize(70,30);
        ct.add(jb);

        Button jb2 = new Button("Edit"); // 일기의 내용을 편집하는 버튼
        jb2.setLocation(220, 720);
        jb2.setSize(70, 30);
        ct.add(jb2);

        Button jb3 = new Button("cancel"); //일기의 내용을 저장 하지않고 종료하는 버튼
        jb3.setLocation(320, 720);
        jb3.setSize(70,30);
        ct.add(jb3);



        Button jb4 = new Button("Todo"); // todo리스트를 불러오는 버튼
        jb4.setLocation(390, 140);
        jb4.setSize(50, 50);
        ct.add(jb4);

        Button jb5 = new Button("img upload"); //파일 탐색기를 열어 이미지 파일을 불러올 버튼
        jb5.setLocation(260, 140);
        jb5.setSize(120 , 50);
        ct.add(jb5);

        ImageIcon img = new ImageIcon(path);
        JLabel imgLabel = new JLabel(img); // 불러온 이미지를 미리 보여주는 라벨
        imgLabel.setLocation(40, 80);
        imgLabel.setSize(80,80);
        ct.add(imgLabel);

        jb.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);
        jb5.addActionListener(this);



    }
    @Override
    public void actionPerformed(ActionEvent ae){ // 일기를 보여주는 창의 버튼 액션 리스너
        String s = ae.getActionCommand();
        if ( s == "save"){ //일기의 내용 저장
            dispose();

        }else if ( s == "Edit"){ // 일기 편집창 불러오기
            Edit edit = new Edit("일기 편집");
            edit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            edit.setSize(400, 600);
            edit.setLocation(400, 100);
            try{

            } catch (NullPointerException e) {}
            edit.show();

        }else if ( s.equals("Todo")){ // todo리스트 불러오기
            ListCourses lc =  new ListCourses();
            lc.setSize(300, 500);
            lc.setLocation(1000,100);
            lc.setVisible(true);
        }else if( s.equals("img upload")){ // 이미지 불러올 파일 탐색기 불러오기
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("파일불러오기");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지파일", "png", "jpg", "gif");
            chooser.setFileFilter(filter);
            chooser.setMultiSelectionEnabled(false);
            int returnVal = chooser.showOpenDialog(this);

            if( returnVal == JFileChooser.APPROVE_OPTION ) { // 파일 탐색기의 저장을 눌럿을때
                try {
                    path = chooser.getSelectedFile().getPath();
                    name = chooser.getSelectedFile().getName();
                    if( path == "") {
                        JOptionPane.showMessageDialog(null, "파일을 선택해주세요", "파일이 없습니다.", JOptionPane.ERROR_MESSAGE);
                        pack();
                    }
                }catch(NullPointerException e) {}
            }

            else { //파일 탐색기의 취소를 눌럿을때
                System.out.println("취소");
                path = "";
            }
        }else dispose(); // 취소누를시에 일기 보기창 종료
    }
}

class Edit extends JFrame implements ActionListener{ // 일기 편집창 클래스
    static JTextField EdText; // 일기 편집창 텍스트 필드
    static String txt1; // 일기 작성한 내용
    JScrollPane Edcp; // 일기 편집창의 스크롤 페인

    Edit(String title){ // 편집창 메소드
        setTitle(title);
        Container ct = getContentPane();

        Dimension Edim = new Dimension(400,600); // 편집창 크기 및 배치 구성
        ct.setPreferredSize(Edim);
        ct.setLayout(null);


        JTextField EdText = new JTextField(txt1);  //일기 편집 텍스트 필드
        JScrollPane Edcp = new JScrollPane(EdText);
        EdText.getText();   // 일기장의 내용 불러오기

        Edcp.setSize(340, 450);
        Edcp.setLocation(20, 40);
        ct.add(Edcp);

        JButton Edsave = new JButton("save");
        Edsave.setBounds(110 , 500,80, 30 );


        JButton Edcancel = new JButton("cancel");
        Edcancel.setBounds(210, 500, 80, 30);

        EdText.addActionListener(this);
        Edsave.addActionListener(this);
        Edcancel.addActionListener(this);

        ct.add(Edsave); ct.add(Edcancel);



    }

    @Override
    public void actionPerformed(ActionEvent ae){  //일기 편집창의 버튼 액션 리스너
        String s = ae.getActionCommand();

        if( s.equals("save") ){    //save를 누를시 일기의 내용을 전달
            try{
                Show.text.setText(EdText.getText()); //Show class의 텍스트를 편집한 내용의 텍스트로 불러옴
                dispose();
            }catch (NullPointerException e){
                JOptionPane.showMessageDialog(null, "내용을 작성해 주세요", "내용이 없습니다.",JOptionPane.ERROR_MESSAGE);
            }
        }else dispose();

    }

}





class ListCourses extends JFrame implements ActionListener,MouseListener {
    Vector<String> columnName;	//표의 각각 제목
    Vector<Vector<String>> rowData;	//표안에 가변크기의 데이터 vector 사용
    JTable table = null;
    TodoTableModel model = null;	//일단 그냥 선언하고 null값을 일단 넣어줌!


    JCheckBox check;
    JTextField todoList;	// 내가 할일을 JTextField에 타이핑
    JButton addB, deleteB;	//추가 버튼, 삭제 버튼

    JScrollPane tableSP;
    int row;

    ListCourses() {
        Container ct = getContentPane();
        ct.setLayout(new BorderLayout());

        JPanel top = new JPanel();		// 리스트 작성하는 상단부분
        JPanel center = new JPanel();	//리스트가 저장되는 센터부분
        JPanel bottom = new JPanel();	//리스트를 저장 또는 작세하는 버튼 저장하는 하단부분

        ct.add(top, BorderLayout.NORTH);
        ct.add(center, BorderLayout.CENTER);
        ct.add(bottom, BorderLayout.SOUTH);

        columnName = new Vector<String>();
        columnName.add("체크");
        columnName.add("할일");



        rowData = new Vector<Vector<String>>();
        model = new TodoTableModel(rowData, columnName);
        table = new JTable(model);
        tableSP = new JScrollPane(table);

        // setting dimensions for tableSP
        tableSP.setPreferredSize(new Dimension(200, 450));
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setWidth(100);

        check = new JCheckBox();
        todoList = new JTextField(15);
        addB = new JButton("추가");
        deleteB = new JButton("삭제");


        addB.addActionListener(this);
        deleteB.addActionListener(this);
        table.addMouseListener(this);


        top.setLayout(new FlowLayout());
        top.add(new JLabel("TO DO LIST"));

        center.setLayout(new FlowLayout());
        center.add(check);	center.add(todoList);
        center.add(tableSP);

        bottom.setLayout(new FlowLayout());
        bottom.add(addB);				bottom.add(deleteB);

    }

    public void actionPerformed(ActionEvent ae) {
        String todo = todoList.getText();
        Vector txt = new Vector();
        txt.add(check.isSelected());
        txt.add(todo);

        if(ae.getActionCommand().equals("추가")) {

            rowData.add(txt);
            todoList.setText("");	//추가버튼 누르면 JTextField 화면 clear
            //table.updateUI();
        }
        else
            rowData.remove(row);
        todoList.setText("");
        table.updateUI();

    }
    public void mouseClicked(MouseEvent ae) {
        row = table.getSelectedRow();
        // check.setText((String)model.getValueAt(row,0));
        todoList.setText((String)model.getValueAt(row,1));
    }
    public void mousePressed (MouseEvent ae) {}
    public void mouseReleased (MouseEvent ae) {}
    public void mouseEntered (MouseEvent ae) {}
    public void mouseExited (MouseEvent ae) {}

}

class ToDoList4 {
    public static void main(String[] args) {
        ListCourses win = new ListCourses();
        win.setTitle("TO DO LIST");
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        win.show();
    }
}

class TodoTableModel extends DefaultTableModel {  //JTable row에 JCheckBox를 추가하려면  DefaultTableModel을 상속받고 Boolean.class를 return해야한다.
    public TodoTableModel(Vector<Vector<String>> rowData, Vector<String> columnName) {
        super(rowData,columnName);
    }


    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Boolean.class : String.class;
    }


    public boolean isCellEditable(int row, int column) {
        return column == 0;
    }
}




class myDiary {
    public static void main(String args[]){
        mdLogin win = new mdLogin("로그인");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setSize(500,500);
        win.setLocation(100, 200);
        win.show();
    }
}