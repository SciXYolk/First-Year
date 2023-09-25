import java.util.Scanner;
public class Submission {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a = 2;
        double numberInput = sc.nextDouble();
        while(Math.abs(a-numberInput/a) >= 1) {
            a = (a + numberInput / a)/2;
        }
	System.out.println((int)a);
	sc.close();
    }
}
