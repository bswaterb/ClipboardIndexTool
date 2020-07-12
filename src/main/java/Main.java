import util.ClipboardListener;

import java.io.*;

public class Main {
    public static void main(String[] args){
        try{
            ClipboardListener.Run();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("出现严重错误");
        }
    }

    //public String readConfig(){
//        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
//        System.out.println(path);
//        try{
//            InputStream inputStream = new FileInputStream(new File(path+"config.txt"));
//            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//            String line="";
//            while((line=br.readLine())!=null){
//                System.out.println("getClassLoader: "+line);
//            }
//            return line;
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("读取配置文件时出错！");
//            return null;
//        }
//    }

}
