import java.util.Scanner;
public class Submission {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char asc1 = sc.next().charAt(0);
        int ats = 0;
        if(asc1 >= 'a' && asc1 <= 'z'){
            ats = asc1 - 'a' + 1;
            System.out.println(ats);
        }
        if(asc1 >= 'A' && asc1 <= 'Z') {
            ats = asc1 - 'A' + 1;
            System.out.println(ats);
        }
	sc.close();

    }
}
