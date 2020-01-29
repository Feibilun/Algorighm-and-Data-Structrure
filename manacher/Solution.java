package manacher;

import java.util.Arrays;
//求最长回文字串  lc#5
public class Solution {
    public static String longestPalindrome(String s){

        int center=0;
        int rightbound=0;
        char[] cori=s.toCharArray();
        char[] c=new char[2*cori.length+1];
        c[0]='#';
        for(int i=0;i<cori.length;i++){
            c[2*i+1]=cori[i];
            c[2*i+2]='#';
        }
        int[] T=new int[c.length];
        for(int i=1;i<c.length;i++){
            int mirror=2*center-i;
            if(i<rightbound){
                T[i]=Math.min(T[mirror],rightbound-i);
            }

            while(i+T[i]+1<c.length && i-T[i]-1>=0 && c[i+T[i]+1]==c[i-(T[i]+1)]){
                T[i]++;
            }

            if(i+T[i]>rightbound){
                center=i;
                rightbound=i+T[i];
            }
        }
        System.out.println(Arrays.toString(T));
        int max=0;
        int index=0;
        for(int i=0;i<T.length;i++){
            if(T[i]>max){
                max=T[i];
                index=i;
            }
        }
        max--;
        int left=(index-max)/2;
        int right=(index+max)/2;
        return s.substring(left,right+1);

    }

    public static void main(String[] args) {
        String s="babad";
        String res=longestPalindrome(s);
        System.out.println(res);
    }

}
