import util.ClipboardListener;

public class Main {
    public static void main(String[] args){
        try{
            ClipboardListener.Run();
        }catch (Exception e){
            System.out.println("出现严重错误");
        }
    }
}
