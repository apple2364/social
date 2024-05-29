import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
 
import com.sun.awt.AWTUtilities;
 
import cn.qq.listener.FrameMoveListener;
import cn.qq.listener.WindowCloseListener;
import cn.qq.listener.WindowMinListener;
import cn.qq.view.register.GetbackPswFrame;
import cn.qq.view.register.RegisterFrame;
import cn.qq.view.util.JButtonUtil;
import cn.qq.view.util.JFrameUtil;
 
/**
 * 登录界面
 *
 * @author ViKi_zc
 *
 */
 
public class LoginFrame extends JFrame {
     
    public JPanel mainPane = null; // 主面板
    public JPanel downPane = null; // 最下面的登录面板
    private JLabel lblLogin = null; // 登录文字标签
    private JButton btnLogin = null;// 登录按钮
     
    public ErrorTipPane errorTipPane = null;//登录失败错误提示面板
     
     
    private JButton btnMin = null; // 最小化按钮
    private JButton btnClose = null; // 关闭按钮
    private JButton btnSet = null; // 设置按钮
 
    private JButton btnOsk = null; // 软键盘
    private JButton btnQuickLogin = null; // 闪登按钮
    private JButton btnMoreAccountLogin = null; // 添加登录账户按钮
 
    private JLabel lblLoginHeadbkg = null; // 登录头像背景
    private JLabel lblLoginHeadimg = null; // 登录头像
 
    private JTextField jtfAccount = null; // 账户输入框
    private JPasswordField pwdfPassword = null;// 密码输入框
    private JButton btnRegister = null; // 注册按钮
    private JButton btnFindBackPwd = null;// 找回密码按钮
    private JCheckBox chkAutoLogin = null;// 自动登录复选框
    private JCheckBox chkRememberPwd = null;// 记住密码复选框
    private JLabel lblRememberPwd = null;// 记住密码标签
    private JLabel lblAutoLogin = null;// 自动登录标签
 
    private JMenuBar mBar = new JMenuBar(); // 登录状态菜单条
    private JMenu menuStatus = new JMenu();// 登录状态菜单
    private JMenuItem imonline = new JMenuItem();
    private JMenuItem invisible = new JMenuItem();
    private JMenuItem busy = new JMenuItem();
    private JMenuItem away = new JMenuItem();
    private JMenuItem qme = new JMenuItem();
    private JMenuItem mute = new JMenuItem();
 
    PopupMenu pupup = null;
    public SystemTray sysTray = null; // 系统托盘
    public TrayIcon trayIcon = null; // 托盘图标
    Font font = new Font("微软雅黑", Font.PLAIN, 12);
 
    public void launchFrame() {
        this.setSize(380, 292);
        this.setLocation(JFrameUtil.getScreenCenterPoint(this));
        this.setIconImage(new ImageIcon("image/Login/qqTitle.png").getImage());
        this.setUndecorated(true);
 
        this.initComponent();
        this.addComponent();
        this.initSystemTray();
        this.setContentPane(mainPane);
        AWTUtilities.setWindowOpacity(this, 0.90f);
         /** 设置圆角 */ 
        AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double( 
                0.0D, 0.0D, this.getWidth(), this.getHeight(), 20.0D,  20.0D)); 
         
        // 添加窗口移动监听
        FrameMoveListener listener = new FrameMoveListener(this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
         
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
 
    /**
     * 设置系统托盘
     */
    private void initSystemTray() {
        sysTray = SystemTray.getSystemTray();
        trayIcon = new TrayIcon(new ImageIcon(
                "image/Login/status/offline16.png").getImage(), "QQ", pupup);
        try {
            if (SystemTray.isSupported() && sysTray != null) {
                sysTray.add(trayIcon);
            }
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        trayIcon.addActionListener(new ActionListener() {
             
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame.this.setExtendedState(JFrame.NORMAL);
                setVisible(true);
            }
        });
    }
 
    /**
     * 初始化组件
     */
    private void initComponent() {
        //提示面板
        errorTipPane = new ErrorTipPane();
        // 主面板
        mainPane = new MainPane();
        // 底部登录按钮面板
        downPane = new DownPane();
         
        // 登陆头像及背景
        lblLoginHeadimg = new JLabel(new ImageIcon("image/Login/qqhead.png"));
        lblLoginHeadbkg = new JLabel(new ImageIcon(
                "image/Login/login_head_white.png"));
        // 用户名文本框
        jtfAccount = new JTextField("100000");
        jtfAccount.setBorder(new LineBorder(Color.GRAY, 1));
        jtfAccount.setFont(new Font("Verdana", Font.PLAIN, 12));
        jtfAccount.setForeground(Color.black);
 
        // 密码输入框
        pwdfPassword = new JPasswordField("1234567890  ");
//      pwdfPassword = new JPasswordField("123");
        pwdfPassword.requestFocus(true);
        pwdfPassword.setBorder(new LineBorder(Color.GRAY, 1));
        pwdfPassword.setFont(font);
        pwdfPassword.setForeground(Color.black);
        pwdfPassword.setEchoChar('●');
 
        // 自动登陆复选框
        chkAutoLogin = JButtonUtil.getIconCheckBox(
                "image/Login/checkbox_normal.png",
                "image/Login/checkbox_hover.png",
                "image/Login/checkbox_press.png",
                "image/Login/checkbox_selected_hover.png");
        // 记住密码复选框
        chkRememberPwd = JButtonUtil.getIconCheckBox(
                "image/Login/checkbox_normal.png",
                "image/Login/checkbox_hover.png",
                "image/Login/checkbox_press.png",
                "image/Login/checkbox_selected_hover.png");
        // 自动登陆标签
        lblAutoLogin = new JLabel("自动登录");
        lblAutoLogin.setFont(font);
        lblAutoLogin.setForeground(Color.black);
        // 记住密码标签
        lblRememberPwd = new JLabel("记住密码");
        lblRememberPwd.setFont(font);
        lblRememberPwd.setForeground(Color.black);
 
        this.initStatus();
        this.initButton();
        this.initPupupMenu();
         
        jtfAccount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1||e.getClickCount()==2){
                    mainPane.remove(errorTipPane);
                    mainPane.add(downPane);
                    downPane.setBounds(0, 241, 380, 51);
                    mainPane.updateUI();
                    mainPane.validate();
                }
            }
        });
         
        pwdfPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1||e.getClickCount()==2){
                    mainPane.remove(errorTipPane);
                    mainPane.add(downPane);
                    downPane.setBounds(0, 241, 380, 51);
                    mainPane.updateUI();
                    mainPane.validate();
                }
            }
        });
    }
 
    /**
     * 初始化右键菜单
     */
    private void initPupupMenu() {
        pupup = new PopupMenu();
        MenuItem itemShowMainFrame = new MenuItem("打开主面板");
        itemShowMainFrame.setFont(font);
        MenuItem itemExit = new MenuItem("退出");
        itemExit.setActionCommand("exit");
        itemExit.setFont(font);
        pupup.add(itemShowMainFrame);
        pupup.addSeparator();
        pupup.add(itemExit);
 
        itemExit.addActionListener(new WindowCloseListener(this));
        itemShowMainFrame.addActionListener(new ActionListener() {
             
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame.this.setExtendedState(JFrame.NORMAL);
                setVisible(true);
            }
        });
    }
 
    /**
     * 初始化状态菜单
     */
    private void initStatus() {
        menuStatus.setIcon(new ImageIcon("image/Login/status/imonline.png"));
        menuStatus.setBorder(null);
        menuStatus.setFocusPainted(false);
        menuStatus.setContentAreaFilled(false);
        menuStatus.setName("imonline");
        mBar.setBorder(null);
        menuStatus.setBorder(null);
 
        imonline.setIcon(new ImageIcon("image/Login/status/imonline.png"));
        invisible.setIcon(new ImageIcon("image/Login/status/invisible.png"));
        busy.setIcon(new ImageIcon("image/Login/status/busy.png"));
        away.setIcon(new ImageIcon("image/Login/status/away.png"));
        qme.setIcon(new ImageIcon("image/Login/status/Qme.png"));
        mute.setIcon(new ImageIcon("image/Login/status/mute.png"));
 
        imonline.setText("我在线上");
        imonline.setFont(font);
        qme.setText("Q我吧");
        qme.setFont(font);
        away.setText("离开");
        away.setFont(font);
        busy.setText("忙碌");
        busy.setFont(font);
        mute.setText("请勿打扰");
        mute.setFont(font);
        invisible.setText("隐身");
        invisible.setFont(font);
 
        menuStatus.add(imonline);
        menuStatus.addSeparator();
        menuStatus.add(qme);
        menuStatus.add(away);
        menuStatus.add(busy);
        menuStatus.add(mute);
        menuStatus.addSeparator();
        menuStatus.add(invisible);
        mBar.add(menuStatus);
        mBar.setOpaque(false);// 如果为true，则该组件绘制其边界内的所有像素
        menuStatus.setOpaque(false);
 
        imonline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuStatus.setIcon(imonline.getIcon());
                menuStatus.setName("imonline");
            }
        });
        invisible.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuStatus.setIcon(invisible.getIcon());
                menuStatus.setName("invisible");
            }
        });
        busy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuStatus.setIcon(busy.getIcon());
                menuStatus.setName("busy");
            }
        });
        away.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuStatus.setIcon(away.getIcon());
                menuStatus.setName("away");
            }
        });
        qme.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuStatus.setIcon(qme.getIcon());
                menuStatus.setName("qme");
            }
        });
        mute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuStatus.setIcon(mute.getIcon());
                menuStatus.setName("mute");
            }
        });
 
    }
 
    /**
     * 初始化主面板上的按钮
     */
    private void initButton() {
        // 软键盘按钮
        btnOsk = JButtonUtil.getIconButton("image/Login/keyboard.png");
 
        // 注册按钮
        btnRegister = JButtonUtil.getIconButton("image/Login/zhuce_normal.png",
                "image/Login/zhuce_press.png", "image/Login/zhuce_hover.png");
        // 密码找回按钮
        btnFindBackPwd = JButtonUtil.getIconButton(
                "image/Login/mima_normal.png", "image/Login/mima_press.png",
                "image/Login/mima_hover.png");
        btnClose = JButtonUtil.getBtnClose();
        btnClose.setActionCommand("exit");
        btnMin = JButtonUtil.getBtnMin();
        btnSet = JButtonUtil.getBtnSet();
 
        // 给登录按钮添加活动事件
        btnLogin.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                sysTray.remove(trayIcon);
                LoadingFrame loadingFrame = new LoadingFrame(LoginFrame.this);
                loadingFrame.setLocation(LoginFrame.this.getX(),
                        LoginFrame.this.getY());
                new Thread(loadingFrame).start();
            }
        });
 
        // 给关闭按钮添加活动事件
        btnClose.addActionListener(new WindowCloseListener(this));
 
        // 给最小化按钮添加活动事件
        btnMin.addActionListener(new WindowMinListener(this));
 
        // 给设置按钮添加活动事件
        btnSet.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                LoginSetFrame loginSetFrame = new LoginSetFrame(LoginFrame.this);
                loginSetFrame.setLocation(LoginFrame.this.getX(),
                        LoginFrame.this.getY());
                loginSetFrame.launchFrame();
            }
        });
 
        // 给软键盘按钮添加活动事件
        btnOsk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runtime rt = Runtime.getRuntime();
                try {
                    rt.exec("osk");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
 
        // 给右下角闪登按钮添加活动事件
        btnQuickLogin.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                QuickLoginFrame quickLoginFrame = new QuickLoginFrame(
                        LoginFrame.this);
                quickLoginFrame.setLocation(LoginFrame.this.getX(),
                        LoginFrame.this.getY());
                quickLoginFrame.launchFrame();
            }
        });
 
        // 给左下角多帐户登录按钮添加活动事件
        btnMoreAccountLogin.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                AddLoginAccountFrame addLoginAccountFrame = new AddLoginAccountFrame(
                        LoginFrame.this);
                addLoginAccountFrame.setLocation(LoginFrame.this.getX(),
                        LoginFrame.this.getY());
                addLoginAccountFrame.launchFrame();
            }
        });
        // 给注册按钮添加活动事件
        btnRegister.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();
            }
        });
        // 给找回密码按钮添加活动事件
        btnFindBackPwd.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                new GetbackPswFrame();
            }
        });
    }
 
    /**
     * 添加组件
     */
    private void addComponent() {
 
        mainPane.setLayout(null);
 
        mainPane.add(btnClose);
        btnClose.setBounds(342, -2, 39, 20);
        mainPane.add(btnMin);
        btnMin.setBounds(315, -2, 28, 20);
        mainPane.add(btnSet);
        btnSet.setBounds(288, -2, 28, 20);
        mainPane.add(btnOsk);
        btnOsk.setBounds(275, 180, 20, 20);
 
        mainPane.add(mBar);
        mBar.setBounds(85, 205, 32, 22);
        mainPane.add(lblLoginHeadimg);
        lblLoginHeadimg.setBounds(22, 147, 80, 79);
        mainPane.add(lblLoginHeadbkg);
        lblLoginHeadbkg.setBounds(21, 143, 84, 84);
 
        mainPane.add(jtfAccount);
        jtfAccount.setBounds(113, 145, 185, 26);
 
        mainPane.add(pwdfPassword);
        pwdfPassword.setBounds(113, 178, 185, 26);
        mainPane.add(btnRegister);
        btnRegister.setBounds(310, 150, 51, 16);
        mainPane.add(btnFindBackPwd);
        btnFindBackPwd.setBounds(310, 183, 51, 16);
 
        mainPane.add(chkAutoLogin);
        chkRememberPwd.setBounds(113, 211, 15, 15);
        mainPane.add(lblRememberPwd);
        lblRememberPwd.setBounds(129, 211, 48, 15);
        mainPane.add(chkRememberPwd);
        chkAutoLogin.setBounds(189, 211, 15, 15);
        mainPane.add(lblAutoLogin);
        lblAutoLogin.setBounds(206, 211, 48, 15);
 
        mainPane.add(downPane);
        downPane.setBounds(0, 241, 380, 51);
 
    }
 
    /**
     * 底部按钮面板
     */
    class DownPane extends JPanel {
 
        public DownPane() {
            this.initComponent();
            this.setLayout(null);
            this.add(btnMoreAccountLogin);
            btnMoreAccountLogin.setBounds(0, 11, 40, 40);
            this.add(btnQuickLogin);
            btnQuickLogin.setBounds(340, 11, 40, 40);
            this.add(lblLogin);
            lblLogin.setBounds(163, 16, 100, 25);
            this.add(btnLogin);
            btnLogin.setBounds(72, 5, 237, 48);
        }
 
        // 初始化底部面板组件
        private void initComponent() {
            // 登录按钮上的标签
            lblLogin = new JLabel("登        录");
            lblLogin.setFont(font);
            lblLogin.setForeground(Color.BLACK);
            // 登录按钮
            btnLogin = JButtonUtil.getIconButton(
                    "image/Login/button_blue_normal.png",
                    "image/Login/button_blue_press.png",
                    "image/Login/button_blue_hover.png");
            // 多帐户登录按钮
            btnMoreAccountLogin = JButtonUtil.getIconButton(
                    "image/Login/corner_back_normal.png",
                    "image/Login/corner_back_press.png",
                    "image/Login/corner_back_hover.png");
            btnMoreAccountLogin.setToolTipText("多帐号登录");
            // 闪登按钮
            btnQuickLogin = JButtonUtil.getIconButton(
                    "image/Login/corner_right_normal.png",
                    "image/Login/corner_right_press.png",
                    "image/Login/corner_right_hover.png");
            btnQuickLogin.setToolTipText("QQ闪登");
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(new ImageIcon("image/Login/loginbutton_background.jpg")
                    .getImage(), 0, 0, 378, 50, this);
 
        }
    }
 
    /**
     * 主面板类
     *
     * @author _zc
     */
    class MainPane extends JPanel {
 
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(
                    new ImageIcon("image/Login/background/noon.jpg").getImage(),
                    0, 0, 380, 292, this);
        }
    }
     
    /**
     * 登录超时提示面板
     * @author ViKi_zc
     *
     */
    public class ErrorTipPane extends JPanel{
        public JLabel lblTip = null;
        private JButton btnClo = null;
        private JButton btnTip = null;
         
        public ErrorTipPane() {
            this.setSize(380, 51);
            init();
            this.setLayout(null);
            this.add(lblTip);
            lblTip.setBounds(80, 15, 300, 20);
            this.add(btnTip);
            btnTip.setBounds(45, 18, 16, 16);
            this.add(btnClo);
            btnClo.setBounds(358, 6, 12, 12);
        }
 
        private void init() {
            lblTip = new JLabel("对不起，此账号已登录，请不要重复登录。");
            lblTip.setFont(font);
            lblTip.setForeground(Color.BLACK);
             
            btnTip = JButtonUtil.getIconButton("image/Login/Tip/i.png");
            btnClo = JButtonUtil.getIconButton("image/Login/Tip/close.png",
                    "image/Login/Tip/close_press.png",
                    "image/Login/Tip/close_hover.png");
            btnClo.addActionListener(new ActionListener() {
                 
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainPane.remove(errorTipPane);
                    mainPane.add(downPane);
                    downPane.setBounds(0, 241, 380, 51);
                    mainPane.updateUI();
                    mainPane.validate();
                }
            });
             
        }
    }
     
     
    /**
     * 启动登录界面
     *
     * @param args
     */
    public static void main(String[] args) {
        new LoginFrame().launchFrame();
    }
 
    public JTextField getJtfAccount() {
        return jtfAccount;
    }
 
    public JPasswordField getPwdfPassword() {
        return pwdfPassword;
    }
 
    public JMenu getMenuStatus() {
        return menuStatus;
    }
 
}
