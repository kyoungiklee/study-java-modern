public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println();
        //this.getClass().getEnclosingMethod().getName();
        String name = Thread.currentThread().getStackTrace()[1].getMethodName();

    }
}