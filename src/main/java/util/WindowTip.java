package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Administrator 此工具类用法：实例化出对象，调用 void show("标题","内容") 方法. InfoUtil tool
 *     = new InfoUtil(); tool.show("标题","内容")
 */
public class WindowTip implements Runnable{
    private String answer;
    private TipWindow tw = null; // 提示框
    private JPanel headPan = null;
    private JPanel feaPan = null;
    private JPanel btnPan = null;
    private JLabel title = null; // 栏目名称
    private JLabel head = null; // 蓝色标题
    private JLabel close = null; // 关闭按钮
    private JTextArea feature = null; // 内容
    private JScrollPane jfeaPan = null;
    private JButton sure = null;
    private String titleT = null;
    private String word = null;
    private Desktop desktop = null;


    public void init() {
// 新建300x180的消息提示框  
        tw = new TipWindow(300, 300);
        headPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        feaPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        title = new JLabel("欢迎使用本系统");
        head = new JLabel(titleT);
        close = new JLabel(" x");
        word = answer;
        feature = new JTextArea(word);
        jfeaPan = new JScrollPane(feature);
        sure = new JButton("确认");
        sure.setHorizontalAlignment(SwingConstants.CENTER);

// 设置提示框的边框,宽度和颜色  
        tw.getRootPane().setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));
        title.setPreferredSize(new Dimension(200, 26));
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setFont(new Font("宋体", Font.PLAIN, 12));
        title.setForeground(Color.white);

        close.setFont(new Font("Arial", Font.BOLD, 15));
        close.setPreferredSize(new Dimension(20, 20));
        close.setVerticalTextPosition(JLabel.CENTER);
        close.setHorizontalTextPosition(JLabel.CENTER);
        close.setCursor(new Cursor(12));
        close.setToolTipText("关闭");

        head.setPreferredSize(new Dimension(250, 35));
        head.setVerticalTextPosition(JLabel.CENTER);
        head.setHorizontalTextPosition(JLabel.CENTER);
        head.setFont(new Font("宋体", Font.PLAIN, 14));
        head.setForeground(Color.black);

        feature.setEditable(false);
        feature.setForeground(Color.BLACK);
        feature.setFont(new Font("宋体", Font.PLAIN, 13));
        feature.setBackground(new Color(255, 255, 255));
// 设置文本域自动换行  
        feature.setLineWrap(true);

        jfeaPan.setPreferredSize(new Dimension(260, 100));
        jfeaPan.setBorder(null);
        jfeaPan.setBackground(Color.black);
        tw.setBackground(Color.white);

// 为了隐藏文本域，加个空的JLabel将他挤到下面去  
        JLabel jsp = new JLabel();
        jsp.setPreferredSize(new Dimension(300, 15));

        sure.setPreferredSize(new Dimension(60, 30));
// 设置标签鼠标手形
        sure.setCursor(new Cursor(12));
// 设置button外观
        sure.setContentAreaFilled(false);
        sure.setBorder(BorderFactory.createRaisedBevelBorder());
        sure.setBackground(Color.gray);
        headPan.add(head);

        feaPan.add(jsp);
        feaPan.add(jfeaPan);


        headPan.setBackground(new Color(255,255,255));
        feaPan.setBackground(Color.white);
        btnPan.setBackground(Color.white);

        tw.add(headPan, BorderLayout.NORTH);
        tw.add(feaPan, BorderLayout.CENTER);
        tw.add(btnPan, BorderLayout.SOUTH);
    }

    public void handle() {
// 为更新按钮增加相应的事件  
        desktop = Desktop.getDesktop();
        sure.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tw.close();
            }

            public void mouseEntered(MouseEvent e) {
                sure.setBorder(BorderFactory.createLineBorder(Color.gray));
            }

            public void mouseExited(MouseEvent e) {
                sure.setBorder(null);
            }
        });
// 右上角关闭按钮事件  
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tw.close();
            }

            public void mouseEntered(MouseEvent e) {
                close.setBorder(BorderFactory.createLineBorder(Color.gray));
            }

            public void mouseExited(MouseEvent e) {
                close.setBorder(null);
            }
        });
    }

    public void show(String titleT, String word) {
        this.titleT = titleT;
        this.word = word;
// time = sdf.format(new Date());  
        init();
        handle();
        tw.setAlwaysOnTop(true);
        tw.setUndecorated(true);
        tw.setResizable(false);
        tw.setVisible(true);
        tw.run();
    }

    public void close() {
        tw.close();
    }

    @Override
    public void run() {
        show("",answer);

    }
    public WindowTip(String answer){
        this.answer = answer;
    }
}

class TipWindow extends JDialog {
    private static final long serialVersionUID = 8541659783234673950L;
    private static Dimension dim;
    private int x, y;
    private int width, height;
    private static Insets screenInsets;

    public TipWindow(int width, int height) {
        this.width = width;
        this.height = height;
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                this.getGraphicsConfiguration());
        x = (int) (dim.getWidth() - width - 3);
        y = (int) (dim.getHeight() - screenInsets.bottom - 3);
        initComponents();
    }

    public void run() {
        for (int i = 0; i <= height; i += 10) {
            try {
                this.setLocation(x, y-400);
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
        }
// 此处代码用来实现让消息提示框1秒后自动消失  
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        close();
    }

    private void initComponents() {
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setBackground(Color.black);
    }

    public void close() {
        dispose();
    }

}