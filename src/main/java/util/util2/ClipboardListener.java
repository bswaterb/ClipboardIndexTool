package util.util2;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import po.Exam;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.InputStream;

public class ClipboardListener{
    public static void Run() throws Exception{
        //读取mybatis配置并创建工厂
        InputStream config = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
        SqlSession sqlSession = factory.openSession();
        while (true) {
            System.out.println("监听中");
            try {
                //如果剪切板中内容变化了
                if (isContentChanged()) {
                    String text = getClipboardString();
                    String answer;
                    //从数据库中查找内容
                    if(sqlSession.selectOne("mapper.ExamMapper.selectAnswerByQuestion", '%' + text + '%')==null){
                        answer = "查询无果";
                    }else{
                        answer = sqlSession.selectOne("mapper.ExamMapper.selectAnswerByQuestion", '%' + text + '%');
                    }
                    //输出复制的内容和查找到的内容
                    System.out.println("Your Copy:"+text);
                    System.out.println("Copy Index Result:"+answer);
                    WindowTip test = new WindowTip();
                    test.show("", answer);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Exam getAnswer() throws Exception {
        InputStream config = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
        SqlSession sqlSession = factory.openSession();
        //如果剪切板中内容变化了，就输出当前的字符串
        if (isContentChanged()) {
            String text = getClipboardString();
            String answer = sqlSession.selectOne("mapper.ExamMapper.selectAnswerByQuestion", '%' + text + '%');
            System.out.println(text);
            System.out.println(answer);
            Exam exam = new Exam(text, answer);
            return exam;
        }
        return null;
    }

    public static void setSysClipboardText(String writeMe) {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable tText = new StringSelection(writeMe);
            clipboard.setContents(tText, null);
        } catch (Exception e) {
            System.out.println("输入内容异常！");
        }

    }

        public static boolean isContentChanged() throws InterruptedException {
            Clipboard clipboard;
            try{
                System.out.println("正在获取剪切板对象...");
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            }catch (Exception e){
                System.out.println("获取剪切板对象异常");
                setSysClipboardText("");
                return false;
            }

            Transferable trans = clipboard.getContents(null);
            try{
                trans = clipboard.getContents(null);
            }catch (Exception e){
                System.out.println("获取剪切板异常，重试中");
            }
            String text = "";
            if (trans != null) {
                //获取当前文本框
                System.out.println("正在获取剪切板内容...");
                try{
                    text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                }catch (Exception e){
                    System.out.println("获取文本异常");
                    setSysClipboardText("");
                    return false;
                }

            }
            while(true){

                try{
                    Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
                    trans = clipboard2.getContents(null);
                    String text2 = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    if(!text2.equals(text)) {
                        return true;
                    }
                }catch (Exception e){
                    System.out.println("捕获内容异常");
                    return false;
                }
                Thread.sleep(500);
            }
        }

        public static String getClipboardString() {
            Clipboard clipboard = null;
            // 获取系统剪贴板
            try{
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            }catch (Exception e){
                System.out.println("获取系统剪切板异常!");
            }
            // 获取剪贴板中的内容
            Transferable trans = clipboard.getContents(null);
            if (trans != null) {
                // 判断剪贴板中的内容是否支持文本
                if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                    try {
                        // 获取剪贴板中的文本内容
                        String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                        return text;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }


}
