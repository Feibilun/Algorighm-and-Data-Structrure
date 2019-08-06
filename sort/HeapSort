import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] arr={89,98,67,67,98,23,0,23,78,999,56,45,45,76};
        myHeapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void myHeapSort(int[] arr){
        for(int i=(arr.length-1-1)/2;i>=0;i--){
            maxHeap(arr,i,arr.length);
        }

        for(int i=0;i<=arr.length-2;i++){
            int tmp=arr[0];
            arr[0]=arr[arr.length-1-i];
            arr[arr.length-1-i]=tmp;
            maxHeap(arr,0,arr.length-1-i);

        }

    }

    public static void maxHeap(int[] arr,int index,int size){
        int max=index;
        int left=2*index+1;
        int right=2*index+2;
        if(left<size && arr[left]>arr[max]){
            max=left;
        }
        if(right<size && arr[right]>arr[max]){
            max=right;
        }
        if(index!=max){
            int tmp=arr[index];
            arr[index]=arr[max];
            arr[max]=tmp;
            maxHeap(arr,max,size);
        }
    }
}
