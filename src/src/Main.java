public class Main {
    public static void main(String[] args) {

        File_Loader fl = new File_Loader();
        char[] out = fl.Load();
        for (Character c: out){
            System.out.println(c);
        }
        System.exit(0);
    }
}