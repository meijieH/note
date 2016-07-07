import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MyLookAndFeel {
	//在界面内直接掉就可以实现皮肤效果
	//系统自带皮肤,5中都能用
	public static String sys_Metal = "javax.swing.plaf.metal.MetalLookAndFeel"; //默认风
	public static String sys_Nimbus = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";//经典蓝
	public static String sys_CDE_Motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";//骨感风
	public static String sys_Windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";//清新白
	public static String sys_Windows_Classic = "com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel";//经典风
	//JIattoo jar包资源com.jtattoo.plafacryl.AcrylLookAndFeel
	public static String jtattoo_acryl = "com.jtattoo.plaf.acryl.AcrylLookAndFeel";//黑白搭
	public static String jtattoo_aero = "com.jtattoo.plaf.aero.AeroLookAndFeel";//清新风
	public static String jtattoo_aluminum = "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel";//深灰风
	public static String jtattoo_bernstein = "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel";//中国黄
	public static String jtattoo_fast = "com.jtattoo.plaf.fast.FastLookAndFeel";//雅典灰
	public static String jtattoo_hifi = "com.jtattoo.plaf.hifi.HiFiLookAndFeel";//暗黑风
	public static String jtattoo_luna = "com.jtattoo.plaf.luna.LunaLookAndFeel";//纯XP风
	public static String jtattoo_mcwin = "com.jtattoo.plaf.mcwin.McWinLookAndFeel";//低调绿
	public static String jtattoo_mint = "com.jtattoo.plaf.mint.MintLookAndFeel";//清新绿
	public static String jtattoo_noire = "com.jtattoo.plaf.noire.NoireLookAndFeel";//有问题
	public static String jtattoo_smart = "com.jtattoo.plaf.smart.SmartLookAndFeel";//古木质
	//liquidlnf.jar包资源
	static final String Liquidlnf = "com.birosoft.liquid.LiquidLookAndFeel";//萌宠蓝
	//SwingSet2_zip.jar包资源  苹果皮肤 咋不太好看
	public static String SwingSet_borland= "com.borland.plaf.borland.BorlandLookAndFeel";//有问题
	public static String SwingSet_quaqua1= "ch.randelshofer.quaqua.Quaqua13PantherLookAndFeel";//有问题
    public static String SwingSet_quaqua2= "ch.randelshofer.quaqua.QuaquaLookAndFeel";//苹果风 有问题
    public static String SwingSet_Office2003= "org.fife.plaf.Office2003.Office2003LookAndFeel";
	public static String SwingSet_OfficeXP= "org.fife.plaf.OfficeXP.OfficeXPLookAndFeel";
	//包资源
	static final String office2003 = "org.fife.plaf.Office2003.Office2003LookAndFeel"; //office风
	static final String Minrodlf = "com.nilo.plaf.nimrod.NimRODLookAndFeel";//高贵棕
	static final String A03Laf = "a03.swing.plaf.A03LookAndFeel";//炫酷灰 
	static final String PgsLookAndFeel = "com.pagosoft.plaf.PgsLookAndFeel";//灰土黄
	

	public MyLookAndFeel(){
		
	}
	
	public static void setLookAndFeel(String skin){
		try {
			UIManager.setLookAndFeel(skin);
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
}

