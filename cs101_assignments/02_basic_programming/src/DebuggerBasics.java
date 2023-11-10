public class DebuggerBasics {
    public static void main(String[] args) {
    System.out.println("Hello Debugger!");
    int a = 1;
    a = 5;
    a = a + a * a;
    a /= 5;
    a = a * a / a;
    a = a++ * a++;
    a = (a * 1428571) / 10000000 + 1; 
    a = ++a * ++a;
    int b = a - a * a;
    a = b;
    a = (a < 0) ? 1 : 0; 
    a = a << 4;
    }
}
