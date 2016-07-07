import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.LineBorder;

class DateChooserJButton extends JButton {	
	private static final long serialVersionUID = 1L;
	private DateChooser dateChooser = null;
	private String preLabel = "";
    //创建一个带文本的按钮，针对刚打开的日期
	public DateChooserJButton() {
		this(getNowDate());
	}
	//创建带文本的按钮，针对于修改后的日期
	public DateChooserJButton(SimpleDateFormat df, String dateString) {
		this();
		setText(df, dateString);
	}
	public DateChooserJButton(Date date) {
		this("", date);
	}
	public DateChooserJButton(String preLabel, Date date) {
		if (preLabel != null)
			this.preLabel = preLabel;
		setDate(date);
		setBorder(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		super.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (dateChooser == null)
					dateChooser = new DateChooser();
				Point p = getLocationOnScreen();
				p.y = p.y + 30;
				dateChooser.showDateChooser(p);
			}
		});
	}
    //得到现在的日历
	private static Date getNowDate() {
		return Calendar.getInstance().getTime();
	}
	// 按钮显示的日期格式
	private static SimpleDateFormat getDefaultDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"); 
	}
	//按照制定格式把S解析成日期
	public void setText(String s) {
		Date date;
		try {
			date = getDefaultDateFormat().parse(s);
		} catch (ParseException e) {
			date = getNowDate();
		}
		setDate(date);
	}
    //把s解析成指定格式df的日期，如果df==null使date=getNowDate()
	public void setText(SimpleDateFormat df, String s) {
		Date date;
		try {
			date = df.parse(s);
		} catch (ParseException e) {
			date = getNowDate();
		}
		setDate(date);
	}
    //设置按钮里的文本
	public void setDate(Date date) {
		super.setText(preLabel + getDefaultDateFormat().format(date));
	}

	public Date getDate() {
		String dateString = getText().substring(preLabel.length());
		try {
			return getDefaultDateFormat().parse(dateString);
		} catch (ParseException e) {
			return getNowDate();
		}
	}

	// 覆盖父类的方法使之无效
	public void addActionListener(ActionListener listener) {
	}

	private class DateChooser extends JPanel implements ActionListener, ChangeListener {
		int startYear = 1980; // 默认【最小】显示年份
		int lastYear = 2050; // 默认【最大】显示年份
		int width = 200; // 界面宽度
		int height = 200; // 界面高度
		Color backGroundColor = Color.gray; // 底色
		// 月历表格配色----------------//
		Color palletTableColor = Color.white; // 日历表底色
		Color todayBackColor = Color.orange; // 今天背景色
		Color weekFontColor = Color.blue; // 星期文字色
		Color dateFontColor = Color.black; // 日期文字色
		Color weekendFontColor = Color.red; // 周末文字色
		// 控制条配色------------------//
		Color controlLineColor = Color.pink; // 控制条底色
		Color controlTextColor = Color.white; // 控制条标签文字色
		Color rbFontColor = Color.white; // RoundBox文字色
		Color rbBorderColor = Color.red; // RoundBox边框色
		Color rbButtonColor = Color.pink; // RoundBox按钮色
		Color rbBtFontColor = Color.red; // RoundBox按钮文字色
		JDialog dialog;
		JSpinner yearSpin;
		JSpinner monthSpin;
		JSpinner hourSpin;
		JButton[][] daysButton = new JButton[6][7];

		DateChooser() {
			setLayout(new BorderLayout());
			setBorder(new LineBorder(backGroundColor, 2));
			setBackground(backGroundColor);
			JPanel topYearAndMonth = createYearAndMonthPanal();
			add(topYearAndMonth, BorderLayout.NORTH);
			JPanel centerWeekAndDay = createWeekAndDayPanal();
			add(centerWeekAndDay, BorderLayout.CENTER);
		}

		private JPanel createYearAndMonthPanal() {
			Calendar c = getCalendar();
			int currentYear = c.get(Calendar.YEAR);
			int currentMonth = c.get(Calendar.MONTH) + 1;
			int currentHour = c.get(Calendar.HOUR_OF_DAY);
			JPanel result = new JPanel();
			result.setLayout(new FlowLayout());
			result.setBackground(controlLineColor);
			yearSpin = new JSpinner(new SpinnerNumberModel(currentYear, startYear, lastYear, 1));
			//这是在设置年轮换框
			yearSpin.setPreferredSize(new Dimension(48, 20));
			yearSpin.setName("Year");
			yearSpin.setEditor(new JSpinner.NumberEditor(yearSpin, "####"));
			yearSpin.addChangeListener(this);
			result.add(yearSpin);
			JLabel yearLabel = new JLabel("年");
			yearLabel.setForeground(controlTextColor);
			result.add(yearLabel);
			monthSpin = new JSpinner(new SpinnerNumberModel(currentMonth, 1, 12, 1));
			monthSpin.setPreferredSize(new Dimension(35, 20));
			monthSpin.setName("Month");
			monthSpin.addChangeListener(this);
			result.add(monthSpin);
			JLabel monthLabel = new JLabel("月");
			monthLabel.setForeground(controlTextColor);
			result.add(monthLabel);
			hourSpin = new JSpinner(new SpinnerNumberModel(currentHour, 0, 23, 1));
			hourSpin.setPreferredSize(new Dimension(35, 20));
			hourSpin.setName("Hour");
			hourSpin.addChangeListener(this);
			result.add(hourSpin);
			JLabel hourLabel = new JLabel("时");
			hourLabel.setForeground(controlTextColor);
			result.add(hourLabel);
			return result;
		}

		private JPanel createWeekAndDayPanal() {
			String colname[] = { "日", "一", "二", "三", "四", "五", "六" };
			JPanel result = new JPanel();
			// 设置固定字体，以免调用环境改变影响界面美观
			result.setFont(new Font("宋体", Font.PLAIN, 12));
			result.setLayout(new GridLayout(7, 7));
			result.setBackground(Color.white);
			JLabel cell;
			for (int i = 0; i < 7; i++) {
				cell = new JLabel(colname[i]);
				cell.setHorizontalAlignment(JLabel.RIGHT);
				if (i == 0 || i == 6)
					cell.setForeground(weekendFontColor);
				else
					cell.setForeground(weekFontColor);
				result.add(cell);
			}
			int actionCommandId = 0;
			for (int i = 0; i < 6; i++)
				for (int j = 0; j < 7; j++) {
					JButton numberButton = new JButton();
					numberButton.setBorder(null);
					//设置数字按钮右对齐
					numberButton.setHorizontalAlignment(SwingConstants.RIGHT);
					//设置此按钮的动作命令，给每一天设置一个按钮，按钮命令就是对应的日子
					numberButton.setActionCommand(String.valueOf(actionCommandId));
					numberButton.addActionListener(this);
					numberButton.setBackground(palletTableColor);
					numberButton.setForeground(dateFontColor);
					if (j == 0 || j == 6)
						numberButton.setForeground(weekendFontColor);
					else
						numberButton.setForeground(dateFontColor);
					daysButton[i][j] = numberButton;
					result.add(numberButton);
					actionCommandId++;
				}
			return result;
		}
        //创建对话框
		private JDialog createDialog(Frame owner) {
			JDialog result = new JDialog(owner, "日期时间选择", true);
			//设置当用户在此对话框上启动 "close" 时默认执行的操作
			result.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
			//返回此对话框的 contentPane 对象（内容窗格）
			result.getContentPane().add(this, BorderLayout.CENTER);
			//pack（）：调整此窗口的大小，以适合其子组件的首选大小和布局。如果该窗口和/或其所有者还不可显示，
			//则在计算首选大小之前都将变得可显示。在计算首选大小之后，将会验证该窗口
			result.pack();
			result.setSize(width, height);
			return result;
		}
       //设置按钮的坐标
		void showDateChooser(Point position) {
			//返回 组件的第一个 Window 祖先；如果 组件未包含在 Window 内，则返回 null。
			Frame owner = (Frame) SwingUtilities.getWindowAncestor(DateChooserJButton.this);
			if (dialog == null || dialog.getOwner() != owner)
				dialog = createDialog(owner);
			dialog.setLocation(getAppropriateLocation(owner, position));
			flushWeekAndDay();
			dialog.show();
		}

		//针对于拖动窗口的
		Point getAppropriateLocation(Frame owner, Point position) {
			Point result = new Point(position);
			Point p = owner.getLocation();
			int offsetX = (position.x + width) - (p.x + owner.getWidth());
			int offsetY = (position.y + height) - (p.y + owner.getHeight());
			if (offsetX > 0) {
				result.x -= offsetX;
			}
			if (offsetY > 0) {
				result.y -= offsetY;
			}
			return result;
		}
       //使用默认时区和语言环境获得一个日历。
		private Calendar getCalendar() {
			Calendar result = Calendar.getInstance();
			//设置时间为今天
			result.setTime(getDate());
			return result;
		}
        //设置年为选中的年
		private int getSelectedYear() {
			return ((Integer) yearSpin.getValue()).intValue();
		}
        //设置月为选中的月
		private int getSelectedMonth() {
			return ((Integer) monthSpin.getValue()).intValue();
		}
        //设置小时为选中的小时
		private int getSelectedHour() {
			return ((Integer) hourSpin.getValue()).intValue();
		}

		private void dayColorUpdate(boolean isOldDay) {
			Calendar c = getCalendar();
			//得到一个月中的某天
			int day = c.get(Calendar.DAY_OF_MONTH);
			//将给定的日历字段设置为给定值
			c.set(Calendar.DAY_OF_MONTH, 1);
			//计算某月某天的下标？？？？问题一算法
			int actionCommandId = day - 2 + c.get(Calendar.DAY_OF_WEEK);
			int i = actionCommandId / 7;
			int j = actionCommandId % 7;
			//设置前一个点击的日子为黑色，新点击的为彩色
			if (isOldDay)
				daysButton[i][j].setForeground(dateFontColor);
			else
				daysButton[i][j].setForeground(todayBackColor);
		}
        //给每个按钮设定text？？？？？问题二
		private void flushWeekAndDay() {
			Calendar c = getCalendar();
			//将给定的日历字段设置为给定值
			c.set(Calendar.DAY_OF_MONTH, 1);
			//给定此 Calendar 的时间值，返回指定日历字段可能拥有的最大值。例如，在某些年份中，MONTH 字段的实际最大值是 12
			int maxDayNo = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dayNo = 2 - c.get(Calendar.DAY_OF_WEEK);
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					String s = "";
					if (dayNo >= 1 && dayNo <= maxDayNo)
						s = String.valueOf(dayNo);
					daysButton[i][j].setText(s);
					dayNo++;
				}
			}
			dayColorUpdate(false);
		}
        //将年月时轮换后的时间写到日历中，然后返回这个日期
		public void stateChanged(ChangeEvent e) {			
			//获得最初发生 Event 的对象。
			JSpinner source = (JSpinner) e.getSource();
			Calendar c = getCalendar();
			if (source.getName().equals("Hour")) {
				//将给定的日历字段设置为给定值
				c.set(Calendar.HOUR_OF_DAY, getSelectedHour());
				setDate(c.getTime());
				return;
			}
			dayColorUpdate(true);
			if (source.getName().equals("Year"))
				c.set(Calendar.YEAR, getSelectedYear());
			else
				// (source.getName().equals("Month"))
				c.set(Calendar.MONTH, getSelectedMonth() - 1);
			setDate(c.getTime());
			flushWeekAndDay();
		}

		public void actionPerformed(ActionEvent e) {
			//获得最初发生 Event 的对象
			JButton source = (JButton) e.getSource();
			//如果点击这个时间没获得按钮的内容，return
			if (source.getText().length() == 0)
				return;
			//否则设置点击的日子为彩色
			dayColorUpdate(true);
			source.setForeground(todayBackColor);
			//获得这个点击的日子的内容---只有第几天
			int newDay = Integer.parseInt(source.getText());
			Calendar c = getCalendar();
			//将给定的日历字段设置为给定值
			c.set(Calendar.DAY_OF_MONTH, newDay);
			//getTime（）返回一个表示此 Calendar 时间值（从历元至现在的毫秒偏移量）的 Date 对象。 
			setDate(c.getTime());
		}
		//jtattoo 
	}// end class DateChooser

	/*public static void main(String args[]) {
		JFrame frame=new JFrame("dee");
		JPanel cp=new JPanel();
		DateChooserJButton button=new DateChooserJButton ();
		cp.add(button);
		frame.setContentPane(cp);
		frame.setSize(200,200);
		frame.setVisible(true);
	}*/
}
