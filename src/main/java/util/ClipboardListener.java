package util;

import com.mysql.cj.util.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClipboardListener {
    public static void Run() throws Exception {
        String answer = null;
        List<String> answerList = null;
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
                    //从数据库中查找内容
                    //尝试给text加入通配符
                    String[] list = text.split("");
                    String[] list2 = new String[list.length*2+1];
                    for(int i=0,j=0;i<list.length*2;i+=2){
                        list2[i]="%";
                        list2[i+1]=list[j];
                        j++;
                    }
                    list2[list2.length-1] = "%";
                    StringBuffer stringBuffer = new StringBuffer();
                    for(int i = 0; i < list2.length; i++){
                        stringBuffer. append(list2[i]);
                    }
                    String text2 = stringBuffer.toString();
                    //可查找多个结果
                    try{
                        if((answer = sqlSession.selectOne("mapper.ExamMapper.selectAnswerByQuestion",  text2 )) == null) {
                            answer = "查询无果";
                        }

                        //输出复制的内容和查找到的内容
                        System.out.println("Your Copy:" + text);
                        System.out.println("Copy Index Result:" + answer);
                        if(!answer.equals("查询无果")){
                            setSysClipboardText(answer);
                        }
                        WindowTip test = new WindowTip();
                        if(answer.length()>255){
                            test.show("结果过长，请直接粘贴");
                        }else{
                            test.show(answer);
                        }
                        answer = null;
                    }catch (Exception e){
                        e.printStackTrace();
                        System.out.println("结果过多或查找错误");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //主动替换剪切板中的内容
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
        Transferable trans = null;
        String text = "";
        try {
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        } catch (Exception e) {
            System.out.println("获取剪切板对象异常，重试中...");
            setSysClipboardText("");
            return false;
        }
        try {
            trans = clipboard.getContents(null);
        } catch (Exception e) {
            System.out.println("获取剪切板异常，重试中...");
            return false;
        }
        if (trans != null) {
            //获取当前文本框
           // System.out.println("正在获取剪切板内容...");
            try {
                text = (String) trans.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception e) {
                System.out.println("获取文本异常");
                setSysClipboardText("");
                return false;
            }

        }
        while (true) {

            try {
                Clipboard clipboard2 = Toolkit.getDefaultToolkit().getSystemClipboard();
                trans = clipboard2.getContents(null);
                String text2 = (String) trans.getTransferData(DataFlavor.stringFlavor);
                if (!text2.equals(text)) {
                    return true;
                }
            } catch (Exception e) {
                System.out.println("捕获内容异常");
                return false;
            }
            Thread.sleep(500);
        }
    }

    //获取剪切板的内容
    public static String getClipboardString() {
        Clipboard clipboard = null;
        Transferable trans = null;
        // 获取系统剪贴板
        try {
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        } catch (Exception e) {
            System.out.println("获取系统剪切板异常!");
        }
        // 获取剪贴板中的内容
        try{
            trans = clipboard.getContents(null);
        }catch (Exception e){
            System.out.println("获取剪切板内容失败！");
        }

        if (trans != null) {
            Boolean flag = false;
            try{
                flag = trans.isDataFlavorSupported(DataFlavor.stringFlavor);
            }catch (Exception e){
                System.out.println("获取文本状态异常！");
            }
            // 判断剪贴板中的内容是否支持文本
            if (flag) {
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
