package util;

import com.sun.org.apache.bcel.internal.classfile.Code;
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

/**
 * Created by chaohui on 2020/6/14
 */
public class ClipboardListener{
    private static String answer;
    public static String Run() throws Exception{
        //setSysClipboardText(""); //清空当前剪切板
        //读取mybatis配置并创建工厂
        InputStream config = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(config);
        SqlSession sqlSession = factory.openSession();
        System.out.println("监听中");
            //如果剪切板中内容变化了
            try {
                if (isContentChanged()) {
                    String text = getClipboardString();
                    //从数据库中查找内容
                    answer = sqlSession.selectOne("mapper.ExamMapper.selectAnswerByQuestion", '%' + text + '%');
                    //输出复制的内容和查找到的内容
                    System.out.println(text);
                    System.out.println(answer);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return answer;
    }

        public static void setSysClipboardText(String writeMe){
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable tText = new StringSelection(writeMe);
            clipboard.setContents(tText,null);
        }

        public static boolean isContentChanged() throws InterruptedException {
            Clipboard clipboard = null;
            try{
                System.out.println("正在获取剪切板对象...");
                clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            }catch (Exception e){
                System.out.println("获取剪切板对象异常");
                return false;
            }

            // 获取剪贴板中的内容
            Transferable trans = clipboard.getContents(null);
            String text = "";
            if (trans != null) {
                //获取当前文本框
                System.out.println("正在获取剪切板内容...");
                try{
                    text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    System.out.println(text);
                }catch (Exception e){
                    System.out.println("获取文本异常");
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
            // 获取系统剪贴板
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

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
