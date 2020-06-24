package util;

import javax.swing.*;
import java.awt.*;

/**
 * @author Administrator 此工具类用法：实例化出对象，调用 void show("标题","内容") 方法. InfoUtil tool
 *     = new InfoUtil(); tool.show("标题","内容")
 */
public class WindowTip {

    private float transparency = 0.5f;//透明度  0.5f表示窗口50%透明
    private int width = 300 ,height = 180;//窗口大小
    private int margin_bottom = 300;//弹窗出现位置相对于屏幕下方的距离
    private int margin_right = 0;//弹窗出现位置相对于屏幕右方的距离
    private int durationTime = 1500; //弹窗持续出现时间，单位ms


    private TipWindow tw = null; // 提示框
    private JPanel headPan = null;
    private JPanel feaPan = null;
    private JPanel btnPan = null;
    private JLabel head = null; // 蓝色标题
    private JTextArea feature = null; // 内容
    private JScrollPane jfeaPan = null;
    private String titleT = null;
    private String word = null;

    //初始化
    public void init() {
        // 新建弹窗主体
        tw = new TipWindow(width, height,transparency,margin_right,margin_bottom,durationTime);
        headPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        feaPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPan = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        head = new JLabel(titleT);
        feature = new JTextArea(word);
        jfeaPan = new JScrollPane(feature);
        // 设置提示框的边框,宽度和颜色
        tw.getRootPane().setBorder(
                BorderFactory.createMatteBorder(1, 1, 1, 1, Color.white));

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


    public void show(String word) {
        this.word = word;
        init();
        tw.setAlwaysOnTop(true);
        tw.setUndecorated(true);
        tw.setResizable(false);
        tw.setVisible(true);
        tw.run();
    }

    public void close() {
        tw.close();
    }
}

class TipWindow extends JDialog {
    private static final long serialVersionUID = 8541659783234673950L;
    private static Dimension dim;
    private int x, y;
    private int width, height;
    private float transparency;
    private int margin_bottom;
    private int margin_right;
    private int durationTime;
    private static Insets screenInsets;

    public TipWindow(int width, int height,float transparency,int margin_right,int margin_bottom,int durationTime) {
        this.width = width;
        this.height = height;
        this.transparency = transparency;
        this.margin_bottom = margin_bottom;
        this.margin_right = margin_right;
        this.durationTime = durationTime;

        //设置窗口透明度
        this.setUndecorated(true);
        this.setOpacity(transparency);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
                this.getGraphicsConfiguration());
        x = (int) (dim.getWidth() - width - margin_right);
        y = (int) (dim.getHeight() - screenInsets.bottom - margin_bottom);

        initComponents();
    }

    public void run() {
        this.setLocation(x,y);
        //让消息提示框定时消失
        try {
            Thread.sleep(durationTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        close();
    }

    //初始化窗体
    private void initComponents() {
        this.setSize(width, height);
        this.setLocation(x, y);
        this.setBackground(Color.black);
    }
    //关闭窗体
    public void close() {
        dispose();
    }

}