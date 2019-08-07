import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr={1,3,2,4,2,1,34,43,34,34,56,34,56,34,1};
        mergesort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergesort(int[] arr,int left,int right){
        if(left<right){
            int mid=left+(right-left)/2;
            mergesort(arr,left,mid);
            mergesort(arr,mid+1,right);
            merge(arr,left,mid,right);
        }
    }

    public static void merge(int[] arr,int left,int mid,int right){
        int[] res=new int[right-left+1];
        int t=0;
        int i=left;
        int j=mid+1;
        while(i<=mid && j<=right){
            if(arr[i]<=arr[j]){
                res[t]=arr[i];
                i++;
            }else{
                res[t]=arr[j];
                j++;
            }
            t++;
        }
        while(i<=mid){
            res[t]=arr[i];
            t++;
            i++;
        }
        while(j<=right){
            res[t]=arr[j];
            t++;
            j++;
        }

        t=0;
        for(int p=left;p<=right;p++){
            arr[p]=res[t];
            t++;
        }
    }
}
