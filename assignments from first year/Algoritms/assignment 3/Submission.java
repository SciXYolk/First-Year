import java.util.Scanner;
public class Submission {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] a = sc.nextLine().split(" ");
        int[] arr = new int[a.length];
        int bScore = -1;
        int bCount = 0;
        for(int i = 0; i < a.length; i++){
            arr[i] = Integer.parseInt(a[i]);
        }


        for(int i=0; i < a.length-1; i++ ){
            for(int j = 0; j < a.length-i-1; j++){
                if(arr[j] < arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

        for (int i = 0; i < a.length; i++) {
            if (arr[i] < bScore || bScore == -1) {
                bScore = i > 0 ? arr[i-1] : arr[i];
                bCount++;
            }
            if (bCount == 3) {
		if(i == 2){
			bScore = arr[i];
		}
                break;
            }
        }
        System.out.println(bScore);
    }
}
