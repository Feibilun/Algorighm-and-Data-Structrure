####LC307  
Given an integer array nums, find the sum of the elements between indices i and j (i ≤ j), inclusive.

The update(i, val) function modifies nums by updating the element at index i to val.  
```java
class NumArray {
    class SegmentTreeNode{
        int start;
        int end;
        int val;
        SegmentTreeNode left;
        SegmentTreeNode right;
    }

    SegmentTreeNode root=null;

    public NumArray(int[] nums) {
        root=buildTree(nums,0,nums.length-1);
    }

    public SegmentTreeNode buildTree(int[] nums,int start,int end){
        if(start>end) return null;
        SegmentTreeNode root=new SegmentTreeNode();
        root.start=start;
        root.end=end;
        if(start==end){
            root.val=nums[start];
            return root;
        }
        int mid=start+(end-start)/2;
        root.left=buildTree(nums,start,mid);
        root.right=buildTree(nums,mid+1,end);
        root.val=root.left.val+root.right.val;
        return root;
    }

    public void update(int i, int val) {
        updateTree(root,i,val);
    }

    public void updateTree(SegmentTreeNode root,int index,int val){
        if(root.start==root.end && root.start==index){
            root.val=val;
            return;
        }

        int mid=root.start+(root.end-root.start)/2;
        if(index<=mid){
            updateTree(root.left,index,val);
        }else{
            updateTree(root.right,index,val);
        }
        root.val=root.left.val+root.right.val;
    }
    public int sumRange(int i, int j) {
        return sumRangeTree(root,i,j);
    }

    public int sumRangeTree(SegmentTreeNode root,int i,int j){
        if(root.start==i && root.end==j){
            return root.val;
        }
        int mid=root.start+(root.end-root.start)/2;
        if(j<=mid){
            return sumRangeTree(root.left,i,j);
        }else if(i>mid){
            return sumRangeTree(root.right,i,j);
        }else{
            return sumRangeTree(root.left,i,mid)+sumRangeTree(root.right,mid+1,j);
        }

    }
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */
 ```
用数组实现，堆的思想  
```java
class NumArray {
    int[] segment;
    int len;
    public NumArray(int[] nums) {
        len=nums.length;
        int i=0;
        while((1<<i)<len){
            i++;
        }
        int n=1<<i;
        segment=new int[2*n-1];
        buildTree(segment,nums,0,nums.length-1,0);
    }
    public void buildTree(int[] segment,int[] nums,int left,int right,int pos){
        if(left>right) return;
        if(left==right){
            segment[pos]=nums[left];
            return;
        }
        int mid=left+(right-left)/2;
        buildTree(segment,nums,left,mid,pos*2+1);
        buildTree(segment,nums,mid+1,right,pos*2+2);
        segment[pos]=segment[pos*2+1]+segment[pos*2+2];
    }

    public void update(int i, int val) {
        update(segment,i,val,0,0,len-1);
    }

    public void update(int[] segment,int index,int val,int pos,int left,int right){
         if(left==right){
             segment[pos]=val;
             return;
         }   
         int mid=left+(right-left)/2;
         if(index<=mid){
             update(segment,index,val,pos*2+1,left,mid);
         }else{
             update(segment,index,val,pos*2+2,mid+1,right);
         }
        segment[pos]=segment[pos*2+1]+segment[pos*2+2];
    }

    public int sumRange(int i, int j) {
        return getSum(i,j,segment,0,0,len-1);
    }

    public int getSum(int qleft,int qright,int[] segment,int pos,int left,int right){
        if(qleft==left && qright==right){
            return segment[pos];
        }
        int mid=left+(right-left)/2;
        if(qright<=mid){
            return getSum(qleft,qright,segment,pos*2+1,left,mid);
        }
        if(qleft>mid){
            return getSum(qleft,qright,segment,pos*2+2,mid+1,right);
        }
        return getSum(qleft,mid,segment,pos*2+1,left,mid)+getSum(mid+1,qright,segment,pos*2+2,mid+1,right);
    }

    //getsum另一种写法：
    public int getSum01(int qleft,int qright,int[] segment,int pos,int left,int right){
    if(qleft>right || qright<left) return 0;
    if(left>=qleft && right<=qright){
        return segment[pos];
    }
    int mid=(left+right)/2;
    return getSum(qleft,qright,segment,pos*2+1,left,mid)+getSum(qleft,qright,segment,pos*2+2,mid+1,right);
}
}

/**
 * Your NumArray object will be instantiated and called as such:
 * NumArray obj = new NumArray(nums);
 * obj.update(i,val);
 * int param_2 = obj.sumRange(i,j);
 */
```

#### LC308  
Given a 2D matrix matrix, find the sum of the elements inside the rectangle defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
```java
class NumMatrix {
    int m;
    int n;
    int[] seg;
    public NumMatrix(int[][] matrix) {
        m=matrix.length;
        if(m==0) return;
        n=matrix[0].length;
        int num=Math.max(m,n)*Math.max(m,n);
        int i=1;
        while(i<num){
            i*=4;
        }
        seg=new int[(4*i-1)/3];
        buildTree(seg,matrix,0,0,m-1,n-1,0);
    }
    public void buildTree(int[] seg,int[][] matrix,int row1,int col1,int row2,int col2,int pos){
        if(row1>row2 || col1>col2) return;
        if(row1==row2 && col1==col2){
            seg[pos]=matrix[row1][col1];
            return;
        }
        int rowMid=(row1+row2)/2;
        int colMid=(col1+col2)/2;
        buildTree(seg,matrix,row1,col1,rowMid,colMid,pos*4+1);
        buildTree(seg,matrix,row1,colMid+1,rowMid,col2,pos*4+2);
        buildTree(seg,matrix,rowMid+1,col1,row2,colMid,pos*4+3);
        buildTree(seg,matrix,rowMid+1,colMid+1,row2,col2,pos*4+4);
        seg[pos]=seg[pos*4+1]+seg[pos*4+2]+seg[pos*4+3]+seg[pos*4+4];
    }

    public void update(int row, int col, int val) {
        update(seg,row,col,val,0,0,m-1,n-1,0);
    }
    public void update(int[] seg,int row,int col,int val,int row1,int col1,int row2,int col2,int pos){
        //if(row1>row2 || col1>col2) return;
        if(row1==row && row2==row && col1==col && col2==col){
            seg[pos]=val;
            return;
        }
        int rowMid=(row1+row2)/2;
        int colMid=(col1+col2)/2;
        if(row<=rowMid && col<=colMid){
            update(seg,row,col,val,row1,col1,rowMid,colMid,pos*4+1);
        }else if(row<=rowMid && col>colMid){
            update(seg,row,col,val,row1,colMid+1,rowMid,col2,pos*4+2);
        }else if(row>rowMid && col<=colMid){
            update(seg,row,col,val,rowMid+1,col1,row2,colMid,pos*4+3);
        }else{
            update(seg,row,col,val,rowMid+1,colMid+1,row2,col2,pos*4+4);
        }
        seg[pos]=seg[pos*4+1]+seg[pos*4+2]+seg[pos*4+3]+seg[pos*4+4];

    }
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return getSum(row1,col1,row2,col2,seg,0,0,m-1,n-1,0);
    }

    public int getSum(int qrow1,int qcol1,int qrow2,int qcol2,int[] seg,int row1,int col1,int row2,int col2,int pos){
        if(qrow1>row2 || qrow2<row1 || qcol1>col2 || qcol2<col1) return 0;
        if(row1>=qrow1 && col1>=qcol1 && row2<=qrow2 && col2<=qcol2){
            return seg[pos];
        }
        int sum=0;
        int rowMid=(row1+row2)/2;
        int colMid=(col1+col2)/2;
        sum+=getSum(qrow1,qcol1,qrow2,qcol2,seg,row1,col1,rowMid,colMid,pos*4+1);
        sum+=getSum(qrow1,qcol1,qrow2,qcol2,seg,row1,colMid+1,rowMid,col2,pos*4+2);
        sum+=getSum(qrow1,qcol1,qrow2,qcol2,seg,rowMid+1,col1,row2,colMid,pos*4+3);
        sum+=getSum(qrow1,qcol1,qrow2,qcol2,seg,rowMid+1,colMid+1,row2,col2,pos*4+4);
        return sum;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */
```
二维写法  
```java
class NumMatrix {
    int[][] seg;
    int m;
    int n;
    public NumMatrix(int[][] matrix) {
        m=matrix.length;
        if(m==0) return;
        n=matrix[0].length;
        int num=Math.max(m,n);
        seg=new int[4*num][4*num];
        buildTree(seg,matrix,0,m-1,0,n-1,0,0);
    }
    public void buildTree(int[][] seg,int[][] matrix,int rowLeft,int rowRight,int colLeft,int colRight,int idx1,int idx2){
        if(rowLeft>rowRight || colLeft>colRight){
            return;
        }
        if(rowLeft==rowRight && colLeft==colRight){
            seg[idx1][idx2]=matrix[rowLeft][colLeft];
            return;
        }
        int rowMid=rowLeft+(rowRight-rowLeft)/2;
        int colMid=colLeft+(colRight-colLeft)/2;
        buildTree(seg,matrix,rowLeft,rowMid,colLeft,colMid,2*idx1+1,2*idx2+1);
        buildTree(seg,matrix,rowLeft,rowMid,colMid+1,colRight,2*idx1+1,2*idx2+2);
        buildTree(seg,matrix,rowMid+1,rowRight,colLeft,colMid,2*idx1+2,2*idx2+1);
        buildTree(seg,matrix,rowMid+1,rowRight,colMid+1,colRight,2*idx1+2,2*idx2+2);
        seg[idx1][idx2]=seg[2*idx1+1][2*idx2+1]+seg[2*idx1+2][2*idx2+1]+seg[2*idx1+1][2*idx2+2]+seg[2*idx1+2][2*idx2+2];
    }
    public void update(int row, int col, int val) {
        update(row,col,val,0,m-1,0,n-1,0,0);
    }

    public void update(int row,int col,int val,int rowLeft,int rowRight,int colLeft,int colRight,int idx1,int idx2){
        if(rowLeft>rowRight || colLeft>colRight) return;
        if(rowLeft==rowRight && colLeft==colRight){
            seg[idx1][idx2]=val;
            return;
        }
        int rowMid=rowLeft+(rowRight-rowLeft)/2;
        int colMid=colLeft+(colRight-colLeft)/2;
        if(row<=rowMid && col<=colMid){
            update(row,col,val,rowLeft,rowMid,colLeft,colMid,2*idx1+1,2*idx2+1);
        }else if(row<=rowMid && col>colMid){
            update(row,col,val,rowLeft,rowMid,colMid+1,colRight,2*idx1+1,2*idx2+2);
        }else if(row>rowMid && col<=colMid){
            update(row,col,val,rowMid+1,rowRight,colLeft,colMid,2*idx1+2,2*idx2+1);
        }else{
            update(row,col,val,rowMid+1,rowRight,colMid+1,colRight,2*idx1+2,2*idx2+2);
        }
        seg[idx1][idx2]=seg[2*idx1+1][2*idx2+1]+seg[2*idx1+2][2*idx2+1]+seg[2*idx1+1][2*idx2+2]+seg[2*idx1+2][2*idx2+2];
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        return getSum(row1,col1,row2,col2,seg,0,m-1,0,n-1,0,0);
    }

    public int getSum(int row1,int col1,int row2,int col2,int[][] seg,int rowLeft,int rowRight,int colLeft,int colRight,int idx1,int idx2){
        if(row2<rowLeft || row1>rowRight || col2<colLeft || col1>colRight || rowLeft>rowRight || colLeft>colRight) return 0;
        if(row1<=rowLeft && row2>=rowRight && col1<=colLeft && col2>=colRight){
            return seg[idx1][idx2];
        }
        int rowMid=rowLeft+(rowRight-rowLeft)/2;
        int colMid=colLeft+(colRight-colLeft)/2;
        return getSum(row1,col1,row2,col2,seg,rowLeft,rowMid,colLeft,colMid,2*idx1+1,2*idx2+1)+
            getSum(row1,col1,row2,col2,seg,rowLeft,rowMid,colMid+1,colRight,2*idx1+1,2*idx2+2)+
            getSum(row1,col1,row2,col2,seg,rowMid+1,rowRight,colLeft,colMid,2*idx1+2,2*idx2+1)+
            getSum(row1,col1,row2,col2,seg,rowMid+1,rowRight,colMid+1,colRight,2*idx1+2,2*idx2+2);
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */
```
