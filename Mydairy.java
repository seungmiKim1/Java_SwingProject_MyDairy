import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;


class Show extends JFrame implements ActionListener{ //일기 편집전 일기장 상태
    private JFileChooser jfc = new JFileChooser();  //사진 파일 올릴 Filechooser 선언
    Image img;
    String name = "";
    String path = ""; // 불러올 이미지 파일의 경로
    static String txt;  //일기장 내용 텍스트
    static JTextField text;  //일기장 텍스트 필드
    JButton jb, jb2, jb3,jb4, jb5;  // 액션 리스너 벝튼
    JLabel imgLabel;
    JScrollPane jscp1; // 일기장 스크롤




    Show(String title){  //일기 보기 메소드
        setTitle("일기");

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

        Button jb5 = new Button("emg upload"); //파일 탐색기를 열어 이미지 파일을 불러올 버튼
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

        }else if( s.equals("emg upload")){ // 이미지 불러올 파일 탐색기 불러오기
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
                dispose();
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




class Mydairy {
    public static void main(String[] args) {
        Show win = new Show("일기 편집");

        win.setSize(500, 800);
        win.setLocation( 400,100);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.show();

    }
}
