import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.swing.*;

public class JavaSwingCalendar extends JFrame implements ActionListener{

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

	JavaSwingCalendar (String title) {

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
	}
}
