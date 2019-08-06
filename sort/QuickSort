import java.util.Arrays;

public class QuickSortDemo {
    public static void main(String[] args) {
        int[] arr={1,3,2,4,2,1,34,43,34,34,56,34,56,34,1};
        quicksort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }


    public static void quicksort(int[] nums,int left,int right){
        if(left<right){
            int q=partition2(nums,left,right);
            quicksort(nums,left,q-1);
            quicksort(nums,q+1,right);
        }
    }

    public static int partition(int[] nums,int left,int right){
        int key=nums[left];
        int i=left+1;
        int j=right;
        while(true){
            while(i<=right && nums[i]<=key){
                i++;
            }
            while(j>left && nums[j]>key){
                j--;
            }
            if(j<i){
                break;
            }
            int tmp=nums[i];
            nums[i]=nums[j];
            nums[j]=tmp;
            i++;
            j--;
        }
        int tmp=nums[left];
        nums[left]=nums[j];
        nums[j]=tmp;
        return j;
    }

    public static int partition2(int[] nums,int left,int right){
        int key=nums[left];
        int p=left;
        for(int i=left+1;i<=right;i++){
            if(nums[i]<=key){
                p++;
                int tmp=nums[i];
                nums[i]=nums[p];
                nums[p]=tmp;
            }
        }
        int tmp=nums[left];
        nums[left]=nums[p];
        nums[p]=tmp;
        return p;
    }
}
