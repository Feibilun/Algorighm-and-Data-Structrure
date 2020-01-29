## 遍历stack实现  
1.前序
```java
public List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode p = root;
    while(!stack.isEmpty() || p != null) {
        if(p != null) {
            stack.push(p);
            result.add(p.val);  // Add before going to children
            p = p.left;
        } else {
            TreeNode node = stack.pop();
            p = node.right;   
        }
    }
    return result;
}
```  
2. 中序  
```java
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode p = root;
    while(!stack.isEmpty() || p != null) {
        if(p != null) {
            stack.push(p);
            p = p.left;
        } else {
            TreeNode node = stack.pop();
            result.add(node.val);  // Add after all left children
            p = node.right;   
        }
    }
    return result;
}
```  

3. 后序  
```java
class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res=new ArrayList();
        Stack<TreeNode> stack=new Stack();
        TreeNode cur=root;
        while(cur!=null || !stack.isEmpty()){
            if(cur!=null){
                stack.push(cur);
                cur=cur.left;
            }else{
                if(stack.peek().right==null){
                    TreeNode tmp=stack.pop();
                    res.add(tmp.val);
                    while(!stack.isEmpty() && stack.peek().right==tmp){
                        tmp=stack.pop();
                        res.add(tmp.val);
                    }
                }else{
                    cur=stack.peek().right;
                }
            }
        }
        return res;
    }
}
```  

## Morris traversal  

1. Morris Traversal inorder  
    LC99
```c++
class Solution {
public:
    void recoverTree(TreeNode* root) {
        TreeNode* pre=new TreeNode(INT_MIN);
        TreeNode* first=nullptr;
        TreeNode* second=nullptr;

        TreeNode* cur=root;
        while(cur!=nullptr){
            if(cur->left==nullptr){
                if(cur->val<pre->val){
                    if(first==nullptr){
                        first=pre;
                        second=cur;
                    }else{
                        second=cur;
                    }
                }
                pre=cur;
                cur=cur->right;
            }else{
                TreeNode* tmp=cur->left;
                while(tmp->right!=nullptr && tmp->right!=cur){
                    tmp=tmp->right;
                }
                if(tmp->right==cur){
                    if(cur->val<pre->val){
                        if(first==nullptr){
                            first=pre;
                            second=cur;
                        }else{
                            second=cur;
                        }
                    }
                    pre=cur;
                    tmp->right=nullptr;
                    cur=cur->right;
                }else{
                    tmp->right=cur;
                    cur=cur->left;
                }
            }
        }
        int t=first->val;
        first->val=second->val;
        second->val=t;
        return;
    }

};
```    
<a href="https://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html">detailed explanation</a>    
```c++
void inorderMorrisTraversal(TreeNode *root) {
 2     TreeNode *cur = root, *prev = NULL;
 3     while (cur != NULL)
 4     {
 5         if (cur->left == NULL)          // 1.
 6         {
 7             printf("%d ", cur->val);
 8             cur = cur->right;
 9         }
10         else
11         {
12             // find predecessor
13             prev = cur->left;
14             while (prev->right != NULL && prev->right != cur)
15                 prev = prev->right;
16
17             if (prev->right == NULL)   // 2.a)
18             {
19                 prev->right = cur;
20                 cur = cur->left;
21             }
22             else                       // 2.b)
23             {
24                 prev->right = NULL;
25                 printf("%d ", cur->val);
26                 cur = cur->right;
27             }
28         }
29     }
30 }
```
2. preorder  
```c++
void preorderMorrisTraversal(TreeNode *root) {
    TreeNode *cur = root, *prev = NULL;
    while (cur != NULL)
    {
        if (cur->left == NULL)
        {
            printf("%d ", cur->val);
            cur = cur->right;
        }
        else
        {
            prev = cur->left;
            while (prev->right != NULL && prev->right != cur)
                prev = prev->right;

            if (prev->right == NULL)
            {
                printf("%d ", cur->val);  // the only difference with inorder-traversal
                prev->right = cur;
                cur = cur->left;
            }
            else
            {
                prev->right = NULL;
                cur = cur->right;
            }
        }
    }
}
```


## Tree Diameter  
LC1245
Given an undirected tree, return its diameter: the number of edges in a longest path in that tree.

The tree is given as an array of edges where edges[i] = [u, v] is a bidirectional edge between nodes u and v.  Each node has labels in the set {0, 1, ..., edges.length}.  

1. 2 BFS  
If we start BFS from any node 'N' and find a node with the longest distance from 'N',
it must be an end point of the longest path.
It can be proved using contradiction.
So the algorithm reduces to simple two BFSs.
First BFS to find an end point of the longest path and second BFS from this end point to find the actual longest path.  
<a href="https://stackoverflow.com/questions/20010472/proof-of-correctness-algorithm-for-diameter-of-a-tree-in-graph-theory">proof</a>  
```Java
class Solution {
    public int treeDiameter(int[][] edges) {
        int n=edges.length+1;
        List<Integer>[] adj=new ArrayList[n];
        for(int i=0;i<n;i++){
            adj[i]=new ArrayList();
        }
        for(int[] edge:edges){
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        Queue<Integer> queue=new LinkedList();
        queue.offer(0);
        int[] parent=new int[n];
        Arrays.fill(parent,-1);
        int level=0;
        int end=-1;
        int maxLevel=0;
        while(!queue.isEmpty()){
            int size=queue.size();
            for(int i=0;i<size;i++){
                int cur=queue.poll();
                if(level>maxLevel){
                    maxLevel=level;
                    end=cur;
                }

                List<Integer> children=adj[cur];
                for(int child:children){
                    if(child==parent[cur]) continue;
                    parent[child]=cur;
                    queue.offer(child);
                }
            }
            level++;
        }

        queue.offer(end);
        level=0;
        Arrays.fill(parent,-1);

        while(!queue.isEmpty()){
            int size=queue.size();
            for(int i=0;i<size;i++){
                int cur=queue.poll();
                List<Integer> children=adj[cur];
                for(int child:children){
                    if(child==parent[cur]) continue;
                    parent[child]=cur;
                    queue.offer(child);
                }
            }
            if(queue.isEmpty()) break;
            level++;
        }
        return level;
    }

}
```  

2. depth of tree  
Travese all the nodes of the tree. The diameter of the tree is maximum of the longest path through each node.
Longest path through a node is sum of top 2 depths of children's tree.  
```Java
class Solution {
    public int treeDiameter(int[][] edges) {
        int n=edges.length+1;
        List<Integer>[] adj=new ArrayList[n];
        for(int i=0;i<n;i++){
            adj[i]=new ArrayList();
        }
        for(int[] edge:edges){
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }

        depth(0,-1,adj);
        return res;
    }
    int res=0;
    public int depth(int cur,int parent,List<Integer>[] adj){
        int max1=0;  //最大
        int max2=0;  //次大

        List<Integer> children=adj[cur];
        for(int child:children){
            if(child==parent) continue;
            int candi=depth(child,cur,adj);
            if(candi>max1){
                max2=max1;
                max1=candi;
            }else if(candi>max2){
                max2=candi;
            }
        }
        res=Math.max(res,max1+max2);
        return 1+max1;
    }
}
```  

3. dfs+memo  
```Java
class Solution {
    public int treeDiameter(int[][] edges) {
        int n=edges.length+1;
        List<Integer>[] adj=new ArrayList[n];
        for(int i=0;i<n;i++){
            adj[i]=new ArrayList();
        }
        for(int[] edge:edges){
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        Map<String,Integer> memo=new HashMap();
        int res=0;
        for(int i=0;i<n;i++){
            res=Math.max(res,dfs(i,-1,adj,memo));
        }
        return res;
    }
    public int dfs(int cur,int parent,List<Integer>[] adj,Map<String,Integer> memo){
        String s=parent+"*"+cur;
        if(memo.containsKey(s)) return memo.get(s);
        int count=0;
        List<Integer> nexts=adj[cur];
        for(int next:nexts){
            if(next==parent) continue;
            count=Math.max(count,1+dfs(next,cur,adj,memo));
        }
        memo.put(s,count);
        return count;
    }
}
```
