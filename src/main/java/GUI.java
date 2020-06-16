import util.ClipboardListener;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    static JFrame gui;
    static JPanel body;
    public static JFrame getGUI(){
        return gui;
    }
    public static void main(String[] args) {
        gui = new JFrame();
        gui.setTitle("划词索引辅助器GUI beta");
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui.setSize(700,300);
        gui.setLocation(100,100);
        gui.setLocationRelativeTo(null);

//        Container body = new Container();
//        body.setLayout((LayoutManager) CardLayout);
        body = new JPanel(new CardLayout());
        final JPanel[] start = {new JPanel()};
        final JButton beginLB = new JButton("开始监听");
        gui.setUndecorated(true);



        start[0].add(beginLB);
        body.add(start[0],"card1");
        gui.add(body);

        beginLB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runtime runtime = Runtime.getRuntime();
                try{
                    gui.dispose();
                    gui.setVisible(false);
                    gui.removeAll();
                    gui = null;
//                    Thread thread = new Thread(new Code());
//                    thread.run();
                }catch (Exception exception){
                    beginLB.setText("执行出错！");
                }
                System.exit(0);
            }
        });
        gui.add(body);
        gui.setVisible(true);
    }

}
