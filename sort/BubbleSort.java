package sort;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {
        int[] arr={89,98,67,67,98,23,0,23,78,999,56,45,45,76};

        for(int i=1;i<arr.length;i++){
            boolean flag=true;
            for(int j=0;j<=arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    flag=false;
                    int tmp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=tmp;
                }
            }
            //System.out.println(Arrays.toString(arr));
            //System.out.println(flag);
            //System.out.println(i);
            if(flag) break;
        }

        System.out.println(Arrays.toString(arr));
    }
}
