//ceiling(获取该Set中大于等于指定值的最小元素)、floor(获取该Set中小于等于指定值的最大元素)

/*
leetcode220
Given an array of integers, find out whether there are two distinct indices i
and j in the array such that the absolute difference between nums[i] and nums[j]
is at most t and the absolute difference between i and j is at most k.*/

class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if(nums.length<2 || k==0) return false;

        TreeSet<Long> set=new TreeSet();
        for(int i=0;i<nums.length;i++){
            Long n=(long) nums[i];
            Long floor=set.floor(n); //小于等于n的最大值
            Long ceil=set.ceiling(n);//大于等于n的最小值
            if(floor!=null && n-floor<=t) return true;
            if(ceil!=null && ceil-n<=t) return true;
            set.add(n);
            if(i>=k){
                set.remove((long)nums[i-k]);
            }
        }
        return false;
    }
}


//TreeMap
//floorEntry方法
/*
leetcode1146
*/
class SnapshotArray {
    TreeMap<Integer,Integer>[] arr;  //<snapId,value>
    int id=0;
    public SnapshotArray(int length) {
        arr=new TreeMap[length];
        for(int i=0;i<length;i++){
            arr[i]=new TreeMap();
            arr[i].put(0,0);
        }
    }

    public void set(int index, int val) {
        arr[index].put(id,val);
    }

    public int snap() {
        return id++;
    }

    public int get(int index, int snap_id) {

        return arr[index].floorEntry(snap_id).getValue();
    }
}
