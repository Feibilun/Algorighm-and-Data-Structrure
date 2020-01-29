### 状态压缩  
LCP 4  
你有一块棋盘，棋盘上有一些格子已经坏掉了。你还有无穷块大小为1 * 2的多米诺骨牌，你想把这些骨牌不重叠地覆盖在完好的格子上，请找出你最多能在棋盘上放多少块骨牌？这些骨牌可以横着或者竖着放。



输入：n, m代表棋盘的大小；broken是一个b * 2的二维数组，其中每个元素代表棋盘上每一个坏掉的格子的位置。

输出：一个整数，代表最多能在棋盘上放的骨牌数。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/broken-board-dominoes
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。  
```java
class Solution {
    public int domino(int n, int m, int[][] broken) {
        int[][] dp=new int[10][256];
        int[] b=new int[10];
        for(int[] bro:broken){
            b[bro[0]]|=1<<bro[1];
        }
        b[n]=(1<<m)-1;
        for(int i=0;i<n;i++){
            for(int j=0;j<(1<<m);j++){
                if((j&b[i])!=b[i]) continue;
                for(int c=0;c<m-1;c++){
                    if( ((j&(1<<c))==0) && ((j&(1<<(c+1)))==0) ){
                        dp[i][j|1<<c|1<<c+1]=Math.max(dp[i][j|1<<c|1<<c+1],1+dp[i][j]);
                    }
                }
            }

            for(int j=0;j<(1<<m);j++){
                if((j&b[i])!=b[i]) continue;
                for(int k=0;k<(1<<m);k++){
                    if((k&b[i+1])!=0) continue;
                    if((k&j)!=0) continue;
                    dp[i+1][k|b[i+1]]=Math.max(dp[i+1][k|b[i+1]],dp[i][j]+getBitCount(k));
                }
            }
        }

        int res=0;
        for(int i=0;i<(1<<m);i++){
            res=Math.max(res,dp[n-1][i]);
        }
        return res;
    }

    public int getBitCount(int n){
        int count=0;
        for(int i=0;i<8;i++){
            if((n&(1<<i))!=0) count++;
        }
        return count;
    }
}
```

### Longest Increasing Subsequence  
LC300  
Given an unsorted array of integers, find the length of longest increasing subsequence.  
```Java
class Solution {
    public int lengthOfLIS(int[] nums) {
        int n=nums.length;
        if(n==0) return 0;
        int[] tail=new int[n+1]; //tails is an array storing the smallest tail of all increasing subsequences with length i in tails[i].
        tail[1]=nums[0];
        int size=1;
        for(int num:nums){
            int left=1;
            int right=size;
            while(left<=right){
                int m=left+(right-left)/2;
                if(tail[m]>=num){
                    right=m-1;
                }else {
                    left=m+1;
                }
            }
            tail[left]=num;
            if(left==size+1){
                size++;
            }
        }
        return size;
    }
}
```  

LC354  
You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit into another if and only if both the width and height of one envelope is greater than the width and height of the other envelope.

What is the maximum number of envelopes can you Russian doll? (put one inside other)  
```Java
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        int n=envelopes.length;
        if(n==0) return 0;
        Arrays.sort(envelopes,new Comparator<int[]>(){
           public int compare(int[] a,int[] b){
               if(a[0]==b[0]){
                   return b[1]-a[1];
               }else{
                   return a[0]-b[0];
               }
           }
        });

        int size=1;
        int[] tail=new int[n+1];
        tail[1]=envelopes[0][1];
        for(int[] e:envelopes){
            int left=1;
            int right=size;
            while(left<=right){
                int mid=left+(right-left)/2;
                if(tail[mid]>=e[1]){
                    right=mid-1;
                }else{
                    left=mid+1;
                }
            }
            tail[left]=e[1];
            if(left==size+1) size++;
        }
        return size;
    }
}
```
