import java.util.Scanner;
public class Submission {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(args[0]);
        int [][] goldM = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j= 0; j < n; j++){
                goldM[i][j] = sc.nextInt();
            }
        }
        sc.close();
        int [][] dp = new int[n][n];
        for(int i = 0; i < n; i++){
            dp[0][i] = goldM[0][i];
        }

        for(int i =1; i<n;i++){
            for(int j = 0; j<n;j++){
                int leftDown = (j > 0) ? dp[i-1][j-1] : 0;
                int rightDown = (j < n-1) ? dp[i-1][j+1] : 0;
                dp[i][j] = goldM[i][j] + Math.max(leftDown, rightDown);
            }
        }

        int mP = 0;
        for(int a = 0; a < n; a++){
            mP = Math.max(mP, dp[n-1][a]);
        }
        System.out.println(mP);
    }
}
