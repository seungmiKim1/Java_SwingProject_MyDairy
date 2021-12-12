import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;

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
    win.setSize(300, 500);
    win.setLocation(1000,100);
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
    

