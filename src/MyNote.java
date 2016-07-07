import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.SQLException;
import javax.swing.*;

///////////////////MyNote主窗体类,MyNote是顶层容器//////////////////////////////////////
public class MyNote extends JFrame implements ActionListener{
    static MyNote fr;
	JMenuBar mb; //菜单栏
	String path="C:\\Users\\梅洁\\Desktop\\haha";
	String str,strnext,fname; //部分变量的声明，fname是初始文件名
	JPanel mainpane;//面板	
	JFileChooser choose = new JFileChooser(); //文件对话框
	Dialog find,replace; //find为查找对话框，replace对话框
	JTextField findtxt,repltxt; //findtxt为查找对话框的输入文本区，repltxt对话框的输入文本区
	Font newfont;
	Font normalfont=new Font("微软雅黑",Font.PLAIN,12);
	JButton findenter,replb,timebutton; //find查找对话框，replace对话框的确定按钮，timebutton是时间的按钮
	JLabel state=new JLabel(" JAVA记事本------未命名文档"); //状态栏
	//主输入文本区
	JTextArea txt1; 	
	File newfiles;
	JPopupMenu popm; //弹出菜单声明	
	JMenu m1, m2, m3,m4,m5,m6; //各菜单项
	JMenuItem m26,m271,m34,p_copy,p_cut,p_paste,p_del;
	JInternalFrame jifShow;//警告框
	//JMenuItem m26, m271, p_copy, p_cut, p_paste, p_del;
	int startp,endp,nexttemp,newstartp,newendp; //查找替换时所用的临时变量
	JToolBar toolbar = new JToolBar(); //工具条
    //工具条设置图标按钮
	JButton newf=new JButton(new ImageIcon(MyNote.class.getResource("/pic/new.png"))); //图标在PIC下
	JButton open=new JButton(new ImageIcon(MyNote.class.getResource("/pic/open.png")));
	JButton save=new JButton(new ImageIcon(MyNote.class.getResource("/pic/save.png")));
	JButton copy=new JButton(new ImageIcon(MyNote.class.getResource("/pic/copy.png")));
	JButton cut=new JButton(new ImageIcon(MyNote.class.getResource("/pic/cut.png")));
	JButton pp=new JButton(new ImageIcon(MyNote.class.getResource("/pic/pp.png")));
	JButton del=new JButton(new ImageIcon(MyNote.class.getResource("/pic/delete.png")));
	JButton findc=new JButton(new ImageIcon(MyNote.class.getResource("/pic/find.png")));
	JButton count = new JButton(new ImageIcon(MyNote.class.getResource("/pic/count.png")));	
	MyNote(String sss) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException /////构造函数开始
	{
		//sss是顶层容器或主窗体的title
		super(sss);
		//菜单
		mb=new JMenuBar();	
		//初始文件名为空
		fname=null; 
		findenter=new JButton("确定"); //find为查找对话框，replace对话框的确定按钮
		findenter.addActionListener(this); //声明对话框中上确定按钮，并注册事件
		replb=new JButton("确定"); //replace对话框的确定按钮
		replb.addActionListener(this);
		//返回此对话框的 contentPane 对象（内容窗格）
		mainpane=(JPanel)this.getContentPane();
		//mainpane.setBackground(new Color(0,0,0));
		
		//BorderLayout这是一个布置容器的边框布局，它可以对容器组件进行安排，并调整其大小，使其符合下列五个区域：
		//北、南、东、西、中。每个区域最多只能包含一个组件，并通过相应的常量进行标识：NORTH、SOUTH、
		//EAST、WEST、CENTER。当使用边框布局将一个组件添加到容器中时，要使用这五个常量之一，例如： 
	    /*Panel p = new Panel();
	    p.setLayout(new BorderLayout());
	    p.add(new Button("Okay"), BorderLayout.SOUTH);*/
		mainpane.setLayout(new BorderLayout());
		//构造具有指定文本、行数和列数的新的 TextArea
		txt1=new JTextArea("",13,61);//主输入文本区
		txt1.addMouseListener(new handlemouse(this));//注册鼠标右击事件
		txt1.setFont(new Font("宋体",Font.PLAIN,14));
		mainpane.add(txt1, BorderLayout.CENTER);
		mainpane.add("North",toolbar);
		mainpane.add("South",state);
		JScrollPane sll = new JScrollPane(); //创建滚动条
		mainpane.add("Center", sll); ;
		//Viewport用于查看底层信息的“视口”或“观察孔”。在滚动时，移动部分称为视口。
		//这好像通过照相机的取景器进行查看。将取景器向上移动会将图片顶部的内部移入视野，而底部的内容将消失。 
		sll.getViewport().add(txt1); //将滚动条装入文本区
	
		popm=new JPopupMenu(); ////POPMeun 开始
		
		p_copy=new JMenuItem("复制 ");
		p_copy.setFont(normalfont);
		p_copy.addActionListener(this);
		KeyStroke keycopyp=KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK);
		//设置快捷键：设置修改键，它能直接调用菜单项的操作侦听器而不必显示菜单的层次结构。
		//UI 负责安装正确的操作。注意，当键入键盘加速器时，不管目前是否显示菜单，它都会运行。 
		p_copy.setAccelerator(keycopyp);
		
		p_cut=new JMenuItem("剪切 ");
		p_cut.setFont(normalfont);
		p_cut.addActionListener(this);
		KeyStroke keycutp=KeyStroke.getKeyStroke(KeyEvent.VK_X,Event.CTRL_MASK);
		p_cut.setAccelerator(keycutp);
		
		p_paste=new JMenuItem("粘贴 ");
		p_paste.setFont(normalfont);
		p_paste.addActionListener(this);
		KeyStroke keypp=KeyStroke.getKeyStroke(KeyEvent.VK_V,Event.CTRL_MASK);
		p_paste.setAccelerator(keypp);
		
		p_del=new JMenuItem("删除 ");
		p_del.setFont(normalfont);
		p_del.addActionListener(this);
		KeyStroke keydelp=KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0);
		p_del.setAccelerator(keydelp);
		
		popm.add(p_copy);
		popm.add(p_cut);
		popm.add(p_paste);
		popm.add(p_del);
		txt1.add(popm); ////POPMenu 结束
	
		//----------第一个菜单：文件---------
		m1=new JMenu("文件(F)");
		m1.setFont(new Font("微软雅黑",Font.PLAIN,12));
		m1.setMnemonic('F'); //定义快捷方式
		JMenuItem m10=new JMenuItem("新建 ");
		m10.setFont(normalfont);
		m10.addActionListener(this); //注册事件监听器
		KeyStroke keynew=KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK); //定义快捷键
		m10.setAccelerator(keynew);
	
		JMenuItem m11=new JMenuItem("打开 ");
		m11.setFont(normalfont);
		m11.addActionListener(this);
		KeyStroke keyopen=KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK);
		m11.setAccelerator(keyopen);
		
		JMenuItem m12=new JMenuItem("保存 ");
		m12.setFont(normalfont);
		m12.addActionListener(this);
		KeyStroke keysave=KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK);
		m12.setAccelerator(keysave);
		
		JMenuItem m13=new JMenuItem("另保存为 ");
		m13.setFont(normalfont);
		m13.addActionListener(this);
		KeyStroke keysaveO=KeyStroke.getKeyStroke(KeyEvent.VK_W,Event.CTRL_MASK);
		m13.setAccelerator(keysaveO);
		
		JMenuItem m14=new JMenuItem("退出 ");
		m14.setFont(normalfont);	
		m14.addActionListener(this);
		KeyStroke keyexit=KeyStroke.getKeyStroke(KeyEvent.VK_F4,Event.ALT_MASK);
		m14.setAccelerator(keyexit);		
		////////////////////////////////////////////
		m2=new JMenu("编辑(E)");
		m2.setFont(normalfont);
		m2.setFont(new Font("微软雅黑",Font.PLAIN,12));
		m2.setMnemonic('E');//设置助记符
		JMenuItem m21=new JMenuItem("复制 ");
		m21.setFont(normalfont);
		m21.addActionListener(this);
		KeyStroke keycopy=KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK);
		m21.setAccelerator(keycopy);
		
		JMenuItem m22=new JMenuItem("剪切 ");
		m22.setFont(normalfont);
		m22.addActionListener(this);
		KeyStroke keycut=KeyStroke.getKeyStroke(KeyEvent.VK_X,Event.CTRL_MASK);
		m22.setAccelerator(keycut);
		
		JMenuItem m23=new JMenuItem("粘贴 ");
		m23.setFont(normalfont);
		m23.addActionListener(this);
		KeyStroke keyp=KeyStroke.getKeyStroke(KeyEvent.VK_V,Event.CTRL_MASK);
		m23.setAccelerator(keyp);
		
		JMenuItem m24=new JMenuItem("删除 ");
		m24.setFont(normalfont);
		m24.addActionListener(this);
		KeyStroke keydel=KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0);
		m24.setAccelerator(keydel);
		
		JMenuItem m25=new JMenuItem("查找 ");
		m25.setFont(normalfont);
		m25.addActionListener(this);
		KeyStroke keyfind=KeyStroke.getKeyStroke(KeyEvent.VK_F,Event.CTRL_MASK);
		m25.setAccelerator(keyfind);
		
		m26=new JMenuItem("查找下一个 ");
		m26.setFont(normalfont);
		m26.addActionListener(this);
		KeyStroke keyfn=KeyStroke.getKeyStroke(KeyEvent.VK_F3,0);
		m26.setAccelerator(keyfn);
		m26.setEnabled(false);
		
		JMenuItem m27=new JMenuItem("替换 ");
		m27.setFont(normalfont);
		m27.addActionListener(this);
		KeyStroke keyrepl=KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK);
		m27.setAccelerator(keyrepl);
		
		m271=new JMenuItem("替换下一个");
		m271.setFont(normalfont);
		m271.setEnabled(false);
		m271.addActionListener(this);
		KeyStroke keyrepn=KeyStroke.getKeyStroke(KeyEvent.VK_F6,0);
		m271.setAccelerator(keyrepn);
		
		JMenuItem m28=new JMenuItem("全选 ");
		m28.setFont(normalfont);
		m28.addActionListener(this);
		KeyStroke keyall=KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK);
		m28.setAccelerator(keyall);
		
		JMenuItem m29 = new JMenuItem("统计 ");
		m29.setFont(normalfont);
		m29.addActionListener(this);
		KeyStroke keycount = KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0);
		m29.setAccelerator(keycount);

		//----------第二个菜单：格式---------
		m3=new JMenu("格式(O)");
		m3.setFont(new Font("微软雅黑",Font.PLAIN,12));
		m3.setMnemonic('O');
		JMenu m31=new JMenu("字体样式 ");
		m31.setFont(normalfont);
		JMenuItem m311=new JMenuItem("正常 ");
		m311.addActionListener(this);
		m311.setFont(normalfont);
		JMenuItem m312=new JMenuItem("粗体 ");
		m312.addActionListener(this);
		m312.setFont(normalfont);
		JMenuItem m313=new JMenuItem("斜体 ");
		m313.addActionListener(this);
		m313.setFont(normalfont);
		
		JMenu m32=new JMenu("字体大小 ");
		m32.setFont(normalfont);
		JMenuItem m321=new JMenuItem("最大 ");
		m321.addActionListener(this);
		m321.setFont(normalfont);
		JMenuItem m322=new JMenuItem("较大 ");
		m322.addActionListener(this);
		m322.setFont(normalfont);
		JMenuItem m323=new JMenuItem("适中 ");
		m323.addActionListener(this);
		m323.setFont(normalfont);
		JMenuItem m324=new JMenuItem("较小 ");
		m324.addActionListener(this);
		m324.setFont(normalfont);
		JMenuItem m325=new JMenuItem("最小 ");
		m325.addActionListener(this);
		m325.setFont(normalfont);
		
		JMenuItem m33=new JMenuItem("字体颜色 ");
		m33.addActionListener(this);
		m33.setFont(normalfont);
		
		m34=new JMenuItem("自动换行 ");
		m34.addActionListener(this);
		m34.setFont(normalfont);
		
		//------更换皮肤---
		m4=new JMenu("皮肤(I)");
		m4.setFont(new Font("微软雅黑",Font.PLAIN,12));
		m4.setMnemonic('I'); //定义快捷方式

		JMenuItem m41=new JMenuItem("默认风");
		m41.addActionListener(this);
		JMenuItem m42=new JMenuItem("经典蓝");
		m42.addActionListener(this);
		JMenuItem m43=new JMenuItem("骨感风");
		m43.addActionListener(this);
		JMenuItem m44=new JMenuItem("清新白");
		m44.addActionListener(this);
		JMenuItem m45=new JMenuItem("经典风");
		m45.addActionListener(this);
		JMenuItem m46=new JMenuItem("黑白搭");
		m46.addActionListener(this);
		JMenuItem m47=new JMenuItem("清新风");
		m47.addActionListener(this);
		JMenuItem m48=new JMenuItem("深灰色");
		m48.addActionListener(this);
		JMenuItem m49=new JMenuItem("中国黄");
		m49.addActionListener(this);
		JMenuItem m410=new JMenuItem("雅典灰");
		m410.addActionListener(this);
		JMenuItem m411=new JMenuItem("暗黑风");
		m411.addActionListener(this);
		JMenuItem m412=new JMenuItem("清新绿");
		m412.addActionListener(this);
		JMenuItem m413=new JMenuItem("古木质");
		m413.addActionListener(this);		
		JMenuItem m414=new JMenuItem("萌宠蓝");
		m414.addActionListener(this);
		JMenuItem m415=new JMenuItem("高贵棕");
		m415.addActionListener(this);
		//----异构数据库
		m5=new JMenu("数据库");
		m5.setFont(new Font("微软雅黑",Font.PLAIN,12));
		m5.setMnemonic('D'); //定义快捷方式
		JMenuItem m51=new JMenuItem("mysql");
		m51.addActionListener(this);
		m51.setFont(normalfont);
		
		JMenuItem m52=new JMenuItem("access");
		m52.addActionListener(this);
		m52.setFont(normalfont);
		
		//-------日历控件--------
		m6=new JMenu("时间(T)");
		m6.setFont(new Font("微软雅黑",Font.PLAIN,12));
		m6.setMnemonic('T'); //定义快捷方式
		
		JMenuItem m61=new JMenuItem("添加时间");
		m61.addActionListener(this);
		m61.setFont(normalfont);
		
		
		
		
		
		m1.add(m10); 
		m1.add(m11); 
		m1.add(m12); 
		m1.add(m13); 
		m1.addSeparator(); //添加分隔符
		m1.add(m14);
		
		m2.add(m21); 
		m2.add(m22); 
		m2.add(m23); 
		m2.add(m24); 
		m2.addSeparator(); 
		m2.add(m25);
		m2.add(m26); //查找下一个，替换下一个
		m2.add(m27); 
		m2.add(m271); 
		m2.addSeparator(); 
		m2.add(m28);
		m2.addSeparator();
		m2.add(m29);
		
		m3.add(m31); m31.add(m311); m31.add(m312); m31.add(m313);
		m3.add(m32); m32.add(m321); m32.add(m322); m32.add(m323); m32.add(m324); m32.add(m325); 
		m3.add(m33); 
		m3.addSeparator(); 
		m3.add(m34);
	
		m4.add(m41);m4.add(m42);m4.add(m43);m4.add(m44);m4.add(m45);m4.add(m46);m4.add(m47);m4.add(m48);
		m4.add(m49);m4.add(m410);m4.add(m411);m4.add(m412);m4.add(m413);m4.add(m414);m4.add(m415);
		m5.add(m51);m5.add(m52);
		m6.add(m61);
		
				
		mb.add(m1); 
		mb.add(m2);
		mb.add(m3);
		mb.add(m4);
		mb.add(m5);
		mb.add(m6);
				
		this.setJMenuBar(mb); //设置菜单栏
		
		toolbar.add(newf); //工具栏各按钮
		newf.setToolTipText("新建");//鼠标落在图标上显示的字
		newf.addActionListener(this);
		toolbar.add(open);
		open.setToolTipText("打开");
		open.addActionListener(this);
		toolbar.add(save);
		save.setToolTipText("保存");
		save.addActionListener(this);
		
		toolbar.addSeparator();
		
		toolbar.add(copy);
		copy.setToolTipText("复制");
		copy.addActionListener(this);
		toolbar.add(cut);
		cut.setToolTipText("剪切");
		cut.addActionListener(this);
		toolbar.add(pp);
		pp.setToolTipText("粘贴");
		pp.addActionListener(this);
		toolbar.add(del);
		del.setToolTipText("删除");
		del.addActionListener(this);
		
		toolbar.addSeparator();
		
		toolbar.add(findc);
		findc.setToolTipText("查找");
		findc.addActionListener(this);
		toolbar.add(count);
		count.setToolTipText("统计");
		count.addActionListener(this);
		
		setVisible(true);
		
		pack();//用于调整此窗口的大小，以适合其子组件的首选大小和布局。 
		show();
		//添加指定的窗口侦听器，以从此窗口接收窗口事件
		this.addWindowListener(new closeWindow(this));
		toolbar.setBackground(new Color(245,246,247));
		//设定日历
		//JPanel cp=new JPanel();
		timebutton=new DateChooserJButton ();
		timebutton.setBackground(new Color(245,246,247));
		mb.add(timebutton);

	}///构造函数结束///

	////////////////事件处理/////////////////////////////////////
	
	public void actionPerformed(ActionEvent p)
	{
		if(p.getActionCommand()=="新建 "||p.getSource()==newf) //响应菜单及工具栏事件
		{
			fname=null; //置文件名为空，便于判断文件是否保存过
			txt1.setText("");
			state.setText("JAVA记事本------未命名文档");
		}	
		//getSource()是获得最初发生 Event 的对象
		if(p.getActionCommand()=="打开 "||p.getSource()==open)
		{
			try 
			{    
				//choose是选择对话框，OPEN_DIALOG指示 JFileChooser 支持 "Open" 文件操作的类型值。
				//IFileChooser的showOpenDialog（）弹出一个 "Open File" 文件选择器对话框
				//this代表myNote主窗体
				if(this.choose.OPEN_DIALOG==this.choose.showOpenDialog(this))
				{
					//path是选中的文件的路径，getPath（）将此抽象路径名转换为一个路径名字符串
					path=this.choose.getSelectedFile().getPath();					
					fname=this.choose.getSelectedFile().getName();
					File file=new File(path);
					//返回由此抽象路径名表示的文件的长度。如果此路径名表示一个目录，则返回值是不确定的。 
					int flength=(int)file.length();
					//用来读取字符文件的便捷类，FileReader 用于读取字符流
					FileReader fReader=new FileReader(file);
					char[] data=new char[flength];
					//将字符读入数组的某一部分。在某个输入可用、发生 I/O 错误或者到达流的末尾前，此方法一直阻塞。 
					//目标缓冲区，开始存储字符处的偏移量，要读取的最多字符数 
					fReader.read(data,0,flength);
					txt1.setText(new String(data));
					state.setText("JAVA记事本------"+path+" 共"+flength+"字节");//状态栏统计文件字节数
					//设置 TextComponent 的文本插入符的位置。注意，插入符可跟踪更改，
					//所以如果组件的底层文本被更改，则此位置可能会移动。如果文档为 null，
					//则不执行任何操作。位置必须在 0 和组件的文本长度之间，否则将抛出异常。 
					txt1.setCaretPosition(0);
					//txt1.show(true);
					//show();
				}
			}
			catch(IOException e)
			{}
		}
	
	
		if(p.getActionCommand()=="保存 "||p.getSource()==save)
		{
			if(fname==null) //如果文件名为空，说明文件未被创建，弹出另存为对话框
			{ 
				othersave(); 
			}
			try 
			{				
				File savefile=new File(path);
				savefile.createNewFile();
				FileWriter fw=new FileWriter(savefile);
				fw.write(txt1.getText());
				fw.close();
			}
			catch(IOException e)
			{
				JOptionPane.showMessageDialog(jifShow,"请选择正确路径","警告", 2);
			}
		}

		if(p.getActionCommand()=="另保存为 ")
		{
			othersave();
		}
		
		if(p.getActionCommand()=="退出 ")
		{
			exit();
		}
	//////////////////编辑////////////////////
	
		if(p.getActionCommand()=="复制 "||p.getSource()==copy)
		{ 
			txt1.copy(); 	
		}
		if(p.getActionCommand()=="剪切 "||p.getSource()==cut)
		{ 
			txt1.cut(); 
		}
		if(p.getActionCommand()=="粘贴 "||p.getSource()==pp)
		{ 
			txt1.paste(); 
		}
		if(p.getActionCommand()=="删除 "||p.getSource()==del)
		{
			txt1.replaceSelection("");
		}
	///////////////////////////////////////////////////////
		if(p.getActionCommand()=="查找 "||p.getSource()==findc) // 创建查找对话框;
		{
			find=new Dialog(this,"查找");
			JPanel p1=new JPanel();
			JPanel p2=new JPanel();
			findtxt=new JTextField(7);
			p1.add(new JLabel("输入要查找字符"));
			p1.add(findtxt);
			p2.add(findenter); //查找的按钮
			find.add("Center",p1);
			find.add("South",p2);
			find.setSize(200,100);
			find.setLocation(520, 240);
			find.show();
		}
		if(p.getSource()==findenter) ///点击查找对话框的确定按钮后的事件处理
		{
			
			if(findtxt.getText().equals(""))
			{
				//释放由此 Window、其子组件及其拥有的所有子组件所使用的所有本机屏幕资源。
				//即这些 Component 的资源将被破坏，它们使用的所有内存都将返回到操作系统，并将它们标记为不可显示。 
				find.dispose();	
			}
			else if(!findtxt.getText().equals(""))
			{
				find.dispose();
				str=txt1.getText();
				//txt1.setCaretPosition(0);
				startp=str.indexOf(findtxt.getText());
				endp=startp+findtxt.getText().length();
				txt1.select(startp,endp);
				//启用或禁用菜单项.true启用
				m26.setEnabled(true);
				newendp=endp;//获取这次查找的终点
				notfindmethod();
			}
		}
	//////////////////////////////////////////////////////////
		if(p.getActionCommand()=="查找下一个 ")
		{
			nexttemp=newendp; /////获取上次查找的终点做为未查找字符串的起点
			String strall=txt1.getText();
			txt1.select(nexttemp,strall.length()); /////选中所有未查找的字符串
			strnext=txt1.getSelectedText();
			newstartp=strnext.indexOf(findtxt.getText())+nexttemp;/////在未查找的字符串里搜索对应字符的在TXT1中的位置
			newendp=newstartp+findtxt.getText().length();
			txt1.select(newstartp,newendp); ////找到相应文本，并选择
			notfindmethod();
		}
		if(p.getActionCommand()=="替换 ")
		{
			m271.setEnabled(true);
			replace=new Dialog(this,"替换"); // 创建替换对话框;
			JPanel p1=new JPanel();
			JPanel p2=new JPanel();
			JPanel p3=new JPanel();
			findtxt=new JTextField(7);
			repltxt=new JTextField(7);
			p1.add(new JLabel("输入原始字符:"));
			p1.add(findtxt);
			p2.add(new JLabel("输入替换字符:"));
			p2.add(repltxt);
			p3.add(replb);
			replace.add("North",p1);
			replace.add("Center",p2);
			replace.add("South",p3);
			replace.setSize(200,150);
			replace.setLocation(520, 240);
			replace.show();
		}
		if(p.getSource()==replb) ///点击替换对话框的确定按钮后的事件处理
		{
			replace.dispose();
			str=txt1.getText();
			startp=str.indexOf(findtxt.getText());
			endp=startp+findtxt.getText().length();
			txt1.select(startp, endp);
			txt1.replaceRange(repltxt.getText(),startp,endp);
			newendp=endp; ///获取这次替换的终点
		}
		
		if(p.getActionCommand()=="替换下一个")
		{
			nexttemp=newendp; //获取上次查找的终点做为未查找字符串的起点
			String strall=txt1.getText();
			txt1.select(nexttemp,strall.length()); //选中所有未查找的字符串
			strnext=txt1.getSelectedText();
			newstartp=strnext.indexOf(findtxt.getText())+nexttemp;//在未查找的字符串里搜索对应字符的在TXT1中的位置
			newendp=newstartp+findtxt.getText().length();
			txt1.select(newstartp,newendp);
			notfindmethod();
			txt1.replaceRange(repltxt.getText(),newstartp,newendp);//替换字符
		}		
		if(p.getActionCommand()=="全选 ")
		{
			txt1.selectAll(); 
		}
		if(p.getActionCommand()=="统计 " || p.getSource()==count)
		{
			TextStats();
		}
	
		///------------------显示格式------------------
		if(p.getActionCommand()=="正常 ")
		{ 
			newfont=txt1.getFont(); //得到当前应用字体，获得字体大小，创建相应样式
			int size=newfont.getSize();
			txt1.setFont(new Font("宋体",Font.PLAIN,size));
		}
		if(p.getActionCommand()=="粗体 ")
		{ 
			newfont=txt1.getFont();
			int size=newfont.getSize();
			txt1.setFont(new Font("宋体",Font.BOLD,size)); 
		}
		
		if(p.getActionCommand()=="斜体 ")
		{ 
			newfont=txt1.getFont();
			int size=newfont.getSize();
			txt1.setFont(new Font("宋体",Font.ITALIC,size)); 
		}

	////////////字体大小/////////////////
		if(p.getActionCommand()=="最大 ")
		{ 
			newfont=txt1.getFont(); ////得到当前应用字体，获得字体样式，创建相应字体的大小
			int sty=newfont.getStyle() ;
			txt1.setFont(new Font("宋体",sty,35)); 
		}
		if(p.getActionCommand()=="较大 ")
		{ 
			newfont=txt1.getFont();
			int sty=newfont.getStyle() ;
			txt1.setFont(new Font("宋体",sty,30)); 
		}
		if(p.getActionCommand()=="适中 ")
		{ 
			newfont=txt1.getFont();
			int sty=newfont.getStyle() ;
			txt1.setFont(new Font("宋体",sty,18)); 
		}
		if(p.getActionCommand()=="较小 ")
		{ 
			newfont=txt1.getFont();
			int sty=newfont.getStyle() ;
			txt1.setFont(new Font("宋体",sty,16)); 
		}
		if(p.getActionCommand()=="最小 ")
		{ 
			newfont=txt1.getFont();
			int sty=newfont.getStyle() ;
			txt1.setFont(new Font("宋体",sty,14)); 
		}

		if(p.getActionCommand()=="字体颜色 ") //字体颜色
		{
			JColorChooser jColor=new JColorChooser(); //调用颜色面板，设置前景就可更改字体颜色
			Color fcolor=txt1.getForeground();
			txt1.setForeground( jColor.showDialog(txt1,"选择字体颜色",fcolor));
		}
		if(p.getActionCommand()=="自动换行 ")
		{ 
			//设置文本区的换行策略。如果设置为 true，则当行的长度大于所分配的宽度时，将换行。
			//如果设置为 false，则始终不换行。当策略更改时，将激发 PropertyChange 事件（"lineWrap"）。此属性默认为 false。
			txt1.setLineWrap(true);
			//启用或禁用菜单项.true启用
			m34.setEnabled(false);
		}
		
		//-----换皮肤----
		if(p.getActionCommand()=="默认风"|p.getActionCommand()=="经典蓝"|p.getActionCommand()=="骨感风"|p.getActionCommand()=="清新白"
				|p.getActionCommand()=="经典风"|p.getActionCommand()=="黑白搭"|p.getActionCommand()=="清新风"|p.getActionCommand()=="深灰色"
				|p.getActionCommand()=="中国黄"|p.getActionCommand()=="雅典灰"|p.getActionCommand()=="暗黑风"
				|p.getActionCommand()=="清新绿"|p.getActionCommand()=="古木质"|p.getActionCommand()=="萌宠蓝"
				|p.getActionCommand()=="高贵棕"){				        
        	  try {
        		  if(p.getActionCommand()=="默认风")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_mcwin);	
        		  if(p.getActionCommand()=="经典蓝")
        			  UIManager.setLookAndFeel(MyLookAndFeel.sys_Nimbus);
        		  if(p.getActionCommand()=="骨感风")
        			  UIManager.setLookAndFeel(MyLookAndFeel.sys_CDE_Motif);
        		  if(p.getActionCommand()=="清新白")
        			  UIManager.setLookAndFeel(MyLookAndFeel.sys_Windows);
        		  if(p.getActionCommand()=="经典风")
        			  UIManager.setLookAndFeel(MyLookAndFeel.sys_Windows_Classic);
        		  if(p.getActionCommand()=="黑白搭")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_acryl);
        		  if(p.getActionCommand()=="清新风")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_aero);
        		  if(p.getActionCommand()=="深灰色")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_aluminum);
        		  if(p.getActionCommand()=="中国黄")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_bernstein);
        		  if(p.getActionCommand()=="雅典灰")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_fast);
        		  if(p.getActionCommand()=="暗黑风")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_hifi);
        		  if(p.getActionCommand()=="清新绿")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_mint);
        		  if(p.getActionCommand()=="古木质")
        			  UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_smart);
        		  if(p.getActionCommand()=="萌宠蓝")
        			  UIManager.setLookAndFeel(MyLookAndFeel.Liquidlnf);
        		  if(p.getActionCommand()=="高贵棕")
        			  UIManager.setLookAndFeel(MyLookAndFeel.Minrodlf);		         		  
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
        	  JFrame.setDefaultLookAndFeelDecorated(true);  
	    	  JDialog.setDefaultLookAndFeelDecorated(true); 
        	  SwingUtilities.updateComponentTreeUI(fr);	   
		}
		
		//--------异构存储
		if(p.getActionCommand()=="mysql"){
			String s=txt1.getText();
			int id=0;
			String sql="insert into "+"notes(id,name,text,time,filepath)"+"values("+id+",'"+fname+"',"+"'"+s+"',"+"'"+timebutton.getText()+"','"+path+"')";
			System.out.println(sql);
			try {
				SaveToDB std=new SaveToDB("mysql","root","961216hmj");
				std.stmt.execute(sql);
				System.out.println("成功保存到数据库");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(p.getActionCommand()=="access"){
			String s=txt1.getText();
			//int id=0;
			String sql="insert into "+"notes(notename,notetext,time,filepath)"+"values('"+fname+"',"+"'"+s+"',"+"'"+timebutton.getText()+"','"+path+"')";
			System.out.println(sql);
			try {
				SaveToDB std = null;
				try {
					std = new SaveToDB("access","","");
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				std.stmt.execute(sql);
				System.out.println("成功保存到access数据库");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//----------时间控件----------
		if(p.getActionCommand()=="添加时间")
		{
			//想要回车加时间 
			txt1.setText(txt1.getText()+timebutton.getText());	
		
		}		
	}//-------------事件处理结束---------

	public void othersave() //另存为方法
	{
		if(choose.APPROVE_OPTION==choose.showSaveDialog(this))
		{
			path=choose.getSelectedFile().getPath();
			System.out.println(path);
			newfiles=new File(path);
			state.setText("JAVA记事本------"+path);
			fname=choose.getSelectedFile().getName();;
			try 
			{
				newfiles=new File(path);
				newfiles.createNewFile();
				FileWriter fw=new FileWriter(newfiles);
				fw.write(txt1.getText());
				fw.close();
			}
			catch(IOException e)
			{}
		}
	}

	public void notfindmethod() //提示查找不到的方法notfindmethod
	{
		if(!txt1.getSelectedText().equals(findtxt.getText()))
		{ 
			txt1.setCaretPosition(0); //光标返回文件头部
			JOptionPane.showMessageDialog(null,"查找不到对应的字符！","查找错误",JOptionPane.ERROR_MESSAGE);
			m26.setEnabled(false); 
		}
	}

	public void exit()
	{ 
		int value;
		String[] qq={"返回", "退出"};
		value=JOptionPane.showOptionDialog(null, "你确定退出吗？请注意保存文件！", "退出程序？",
										JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
										null, qq, qq[0]);
		if(value==JOptionPane.YES_OPTION)
		{
			return;
		}	
		else if(value==JOptionPane.NO_OPTION)
		{ 
			System.exit(0); 
			
		}
	}
	
	public void TextStats() 
    {
		
		final TextArea stats = new TextArea(15, 23);
		stats.setBackground(new Color(255,255,255));
		
		/*String[] words = txt1.getText().toLowerCase().split("[\\W&&[^'-]]+");
		Map<String ,Integer> count = new TreeMap<String, Integer>();  //接口
		for(String word:words)
		{
			if(word.length() > 0)
				count.put(word, count.containsKey(word) ? count.get(word)+1 : 1);
		}
		stats.setText(count.toString().replaceAll("[{}]|, ", "\n").trim());*/
		stats.setText(""+txt1.getText().length());
        //add(text);
        add(stats, BorderLayout.EAST);
        stats.setEditable(false);

        setTitle("单词排序与统计");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{	
	   //必须要启动这个线程，不然无法达到换肤效果，具体原因我也没深究  //多线程换肤
	    SwingUtilities.invokeLater(new Runnable() {  
	      public void run() {  
	        try {  
	        	UIManager.setLookAndFeel(MyLookAndFeel.jtattoo_mint);
	        } catch (Exception e) {  
	            JOptionPane.showMessageDialog(null, "Substance Graphite failed to initialize");  
	        } 
			try {
				//使标题栏的风格也跟着一起改变... 
			 	JFrame.setDefaultLookAndFeelDecorated(true);  
	    		JDialog.setDefaultLookAndFeelDecorated(true); 
				fr = new MyNote("JAVA记事本");
				fr.setSize(600,400);
				fr.setLocation(300, 100);  
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}	   			
	      }  
	    }); 	
	}
}

class closeWindow extends WindowAdapter //关闭窗口closeWindow类////////////
{
	MyNote fx;
	closeWindow(MyNote fxx)
	{fx=fxx;}
	public void windowClosing(WindowEvent e)
	{ 
		JOptionPane.showMessageDialog(null,"欢迎使用本软件！","退出",JOptionPane.INFORMATION_MESSAGE);
		fx.dispose();
		System.exit(0);
	}
}
class CloseCom extends Component{
	Component c;
	CloseCom(Component c){
		this.c=c;
	}
	
}

class handlemouse extends MouseAdapter //处理右键弹出菜单类
{
	MyNote fx;
	handlemouse(MyNote fxx)
	{
		fx=fxx;
	}
	public void mouseReleased(MouseEvent n)
	{
		if(n.isPopupTrigger())
			fx.popm.show((Component)n.getSource(),100,100);
			//fx.popm.show((Component)n.getSource(),n.getX(),n.getY());
		
	}
}