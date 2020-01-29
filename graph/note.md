### Kosaraju's algorithm to find strongly connected component  
<a href="https://www.geeksforgeeks.org/strongly-connected-components/">introduction</a>
```Java
// Java implementation of Kosaraju's algorithm to print all SCCs
import java.io.*;
import java.util.*;
import java.util.LinkedList;

// This class represents a directed graph using adjacency list
// representation
class Graph
{
	private int V; // No. of vertices
	private LinkedList<Integer> adj[]; //Adjacency List

	//Constructor
	Graph(int v)
	{
		V = v;
		adj = new LinkedList[v];
		for (int i=0; i<v; ++i)
			adj[i] = new LinkedList();
	}

	//Function to add an edge into the graph
	void addEdge(int v, int w) { adj[v].add(w); }

	// A recursive function to print DFS starting from v
	void DFSUtil(int v,boolean visited[])
	{
		// Mark the current node as visited and print it
		visited[v] = true;
		System.out.print(v + " ");

		int n;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i =adj[v].iterator();
		while (i.hasNext())
		{
			n = i.next();
			if (!visited[n])
				DFSUtil(n,visited);
		}
	}

	// Function that returns reverse (or transpose) of this graph
	Graph getTranspose()
	{
		Graph g = new Graph(V);
		for (int v = 0; v < V; v++)
		{
			// Recur for all the vertices adjacent to this vertex
			Iterator<Integer> i =adj[v].listIterator();
			while(i.hasNext())
				g.adj[i.next()].add(v);
		}
		return g;
	}

	void fillOrder(int v, boolean visited[], Stack stack)
	{
		// Mark the current node as visited and print it
		visited[v] = true;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> i = adj[v].iterator();
		while (i.hasNext())
		{
			int n = i.next();
			if(!visited[n])
				fillOrder(n, visited, stack);
		}

		// All vertices reachable from v are processed by now,
		// push v to Stack
		stack.push(new Integer(v));
	}

	// The main function that finds and prints all strongly
	// connected components
	void printSCCs()
	{
		Stack stack = new Stack();

		// Mark all the vertices as not visited (For first DFS)
		boolean visited[] = new boolean[V];
		for(int i = 0; i < V; i++)
			visited[i] = false;

		// Fill vertices in stack according to their finishing
		// times
		for (int i = 0; i < V; i++)
			if (visited[i] == false)
				fillOrder(i, visited, stack);

		// Create a reversed graph
		Graph gr = getTranspose();

		// Mark all the vertices as not visited (For second DFS)
		for (int i = 0; i < V; i++)
			visited[i] = false;

		// Now process all vertices in order defined by Stack
		while (stack.empty() == false)
		{
			// Pop a vertex from stack
			int v = (int)stack.pop();

			// Print Strongly connected component of the popped vertex
			if (visited[v] == false)
			{
				gr.DFSUtil(v, visited);
				System.out.println();
			}
		}
	}

	// Driver method
	public static void main(String args[])
	{
		// Create a graph given in the above diagram
		Graph g = new Graph(5);
		g.addEdge(1, 0);
		g.addEdge(0, 2);
		g.addEdge(2, 1);
		g.addEdge(0, 3);
		g.addEdge(3, 4);

		System.out.println("Following are strongly connected components "+
						"in given graph ");
		g.printSCCs();
	}
}
// This code is contributed by Aakash Hasija
```

hdu1269  
为了训练小希的方向感，Gardon建立了一座大城堡，里面有N个房间(N<=10000)和M条通道(M<=100000)，每个通道都是单向的，就是说若称某通道连通了A房间和B房间，只说明可以通过这个通道由A房间到达B房间，但并不说明通过它可以由B房间到达A房间。Gardon需要请你写个程序确认一下是否任意两个房间都是相互连通的，即：对于任意的i和j，至少存在一条路径可以从房间i到房间j，也存在一条路径可以从房间j到房间i。  
```c++
#include <bits/stdc++.h>

using namespace std;


const int N=10005;
int n, m;
vector<int> adj[N];
vector<int> anadj[N];
int scc_num[N];
int scc_count;
bool visited[N];
stack<int> stk;


void dfs1(int u){
    visited[u]=true;
    for(int v:adj[u]){
        if(!visited[v]) dfs1(v);
    }
    stk.push(u);
}

void dfs2(int u){
    visited[u]=true;
    scc_num[u]=scc_count;
    for(int v:anadj[u]){
        if(!visited[v]) dfs2(v);
    }
}

int main(){

     int a, b;
     while(scanf("%d %d",&n, &m)!=EOF && (m+n)>0){
         for(int i=1;i<=n;++i){
             adj[i].clear();
             anadj[i].clear();
         }

         memset(scc_num, -1, sizeof(scc_num));
         memset(visited,false,sizeof(visited));
         scc_count=0;
         while(!stk.empty()) stk.pop();


         for(int i=0;i<m;++i){
             scanf("%d %d", &a, &b);
             adj[a].push_back(b);
             anadj[b].push_back(a);
         }

         for(int i=1;i<=n;++i){
             if(!visited[i]){
                 dfs1(i);
             }
         }

         memset(visited,false,sizeof(visited));
         while(!stk.empty()){
             int u=stk.top();
             stk.pop();
             if(!visited[u]){
                 ++scc_count;
                 dfs2(u);
             }
         }

         int t=scc_num[1];
         int i=2;
         for(;i<=n;++i){
             if(scc_num[i]!=t){
                 printf("No\n");
                 break;
             }
         }
         if(i>n) printf("Yes\n");
     }
}  
```


### Tarjan算法  
LC1192  
There are n servers numbered from 0 to n-1 connected by undirected server-to-server connections forming a network where connections[i] = [a, b] represents a connection between servers a and b. Any server can reach any other server directly or indirectly through the network.

A critical connection is a connection that, if removed, will make some server unable to reach some other server.

Return all critical connections in the network in any order.  
```java
class Solution {
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<Integer>[] adj=new ArrayList[n];
        for(int i=0;i<n;i++){
            adj[i]=new ArrayList();
        }
        for(List<Integer> list:connections){
            int start=list.get(0);
            int end=list.get(1);
            adj[start].add(end);
            adj[end].add(start);
        }

        int[] d=new int[n];
        int[] min=new int[n];

        List<List<Integer>> res=new ArrayList();
        dfs(adj,n,0,0,res,d,min);
        return res;
    }
    int count=1;
    public void dfs(List<Integer>[] adj,int n,int cur,int pre,List<List<Integer>> res,int[] d,int[] min){
        d[cur]=count;
        min[cur]=count;
        count++;
        List<Integer> next=adj[cur];
        for(int i:next){
            if(i==pre) continue;
            if(d[i]!=0){
                min[cur]=Math.min(min[cur],d[i]);
            }else{
                dfs(adj,n,i,cur,res,d,min);
                min[cur]=Math.min(min[cur],min[i]);
                if(d[cur]<min[i]){   //也可以写为  if(min[i]==d[i])
                    List<Integer> tmp=new ArrayList();
                    tmp.add(cur);
                    tmp.add(i);
                    res.add(tmp);
                }
            }
        }
    }
}
```
### Articulation Points  
```Java
// A Java program to find articulation points in an undirected graph
import java.io.*;
import java.util.*;
import java.util.LinkedList;

// This class represents an undirected graph using adjacency list
// representation
class Graph
{
	private int V; // No. of vertices

	// Array of lists for Adjacency List Representation
	private LinkedList<Integer> adj[];
	int time = 0;
	static final int NIL = -1;

	// Constructor
	Graph(int v)
	{
		V = v;
		adj = new LinkedList[v];
		for (int i=0; i<v; ++i)
			adj[i] = new LinkedList();
	}

	//Function to add an edge into the graph
	void addEdge(int v, int w)
	{
		adj[v].add(w); // Add w to v's list.
		adj[w].add(v); //Add v to w's list
	}

	// A recursive function that find articulation points using DFS
	// u --> The vertex to be visited next
	// visited[] --> keeps tract of visited vertices
	// disc[] --> Stores discovery times of visited vertices
	// parent[] --> Stores parent vertices in DFS tree
	// ap[] --> Store articulation points
	void APUtil(int u, boolean visited[], int disc[],
				int low[], int parent[], boolean ap[])
	{

		// Count of children in DFS Tree
		int children = 0;

		// Mark the current node as visited
		visited[u] = true;

		// Initialize discovery time and low value
		disc[u] = low[u] = ++time;

		// Go through all vertices aadjacent to this
		Iterator<Integer> i = adj[u].iterator();
		while (i.hasNext())
		{
			int v = i.next(); // v is current adjacent of u

			// If v is not visited yet, then make it a child of u
			// in DFS tree and recur for it
			if (!visited[v])
			{
				children++;
				parent[v] = u;
				APUtil(v, visited, disc, low, parent, ap);

				// Check if the subtree rooted with v has a connection to
				// one of the ancestors of u
				low[u] = Math.min(low[u], low[v]);

				// u is an articulation point in following cases

				// (1) u is root of DFS tree and has two or more chilren.
				if (parent[u] == NIL && children > 1)
					ap[u] = true;

				// (2) If u is not root and low value of one of its child
				// is more than discovery value of u.
				if (parent[u] != NIL && low[v] >= disc[u])
					ap[u] = true;
			}

			// Update low value of u for parent function calls.
			else if (v != parent[u])
				low[u] = Math.min(low[u], disc[v]);
		}
	}

	// The function to do DFS traversal. It uses recursive function APUtil()
	void AP()
	{
		// Mark all the vertices as not visited
		boolean visited[] = new boolean[V];
		int disc[] = new int[V];
		int low[] = new int[V];
		int parent[] = new int[V];
		boolean ap[] = new boolean[V]; // To store articulation points

		// Initialize parent and visited, and ap(articulation point)
		// arrays
		for (int i = 0; i < V; i++)
		{
			parent[i] = NIL;
			visited[i] = false;
			ap[i] = false;
		}

		// Call the recursive helper function to find articulation
		// points in DFS tree rooted with vertex 'i'
		for (int i = 0; i < V; i++)
			if (visited[i] == false)
				APUtil(i, visited, disc, low, parent, ap);

		// Now ap[] contains articulation points, print them
		for (int i = 0; i < V; i++)
			if (ap[i] == true)
				System.out.print(i+" ");
	}

	// Driver method
	public static void main(String args[])
	{
		// Create graphs given in above diagrams
		System.out.println("Articulation points in first graph ");
		Graph g1 = new Graph(5);
		g1.addEdge(1, 0);
		g1.addEdge(0, 2);
		g1.addEdge(2, 1);
		g1.addEdge(0, 3);
		g1.addEdge(3, 4);
		g1.AP();
		System.out.println();

		System.out.println("Articulation points in Second graph");
		Graph g2 = new Graph(4);
		g2.addEdge(0, 1);
		g2.addEdge(1, 2);
		g2.addEdge(2, 3);
		g2.AP();
		System.out.println();

		System.out.println("Articulation points in Third graph ");
		Graph g3 = new Graph(7);
		g3.addEdge(0, 1);
		g3.addEdge(1, 2);
		g3.addEdge(2, 0);
		g3.addEdge(1, 3);
		g3.addEdge(1, 4);
		g3.addEdge(1, 6);
		g3.addEdge(3, 5);
		g3.addEdge(4, 5);
		g3.AP();
	}
}
// This code is contributed by Aakash Hasija
```  

### Tarjan 求强连通分量  
```c++
// A C++ program to find strongly connected components in a given
// directed graph using Tarjan's algorithm (single DFS)
#include<iostream>
#include <list>
#include <stack>
#define NIL -1
using namespace std;

// A class that represents an directed graph
class Graph
{
	int V; // No. of vertices
	list<int> *adj; // A dynamic array of adjacency lists

	// A Recursive DFS based function used by SCC()
	void SCCUtil(int u, int disc[], int low[],
				stack<int> *st, bool stackMember[]);
public:
	Graph(int V); // Constructor
	void addEdge(int v, int w); // function to add an edge to graph
	void SCC(); // prints strongly connected components
};

Graph::Graph(int V)
{
	this->V = V;
	adj = new list<int>[V];
}

void Graph::addEdge(int v, int w)
{
	adj[v].push_back(w);
}

// A recursive function that finds and prints strongly connected
// components using DFS traversal
// u --> The vertex to be visited next
// disc[] --> Stores discovery times of visited vertices
// low[] -- >> earliest visited vertex (the vertex with minimum
//			 discovery time) that can be reached from subtree
//			 rooted with current vertex
// *st -- >> To store all the connected ancestors (could be part
//		 of SCC)
// stackMember[] --> bit/index array for faster check whether
//				 a node is in stack
void Graph::SCCUtil(int u, int disc[], int low[], stack<int> *st,
					bool stackMember[])
{
	// A static variable is used for simplicity, we can avoid use
	// of static variable by passing a pointer.
	static int time = 0;

	// Initialize discovery time and low value
	disc[u] = low[u] = ++time;
	st->push(u);
	stackMember[u] = true;

	// Go through all vertices adjacent to this
	list<int>::iterator i;
	for (i = adj[u].begin(); i != adj[u].end(); ++i)
	{
		int v = *i; // v is current adjacent of 'u'

		// If v is not visited yet, then recur for it
		if (disc[v] == -1)
		{
			SCCUtil(v, disc, low, st, stackMember);

			// Check if the subtree rooted with 'v' has a
			// connection to one of the ancestors of 'u'
			// Case 1 (per above discussion on Disc and Low value)
			low[u] = min(low[u], low[v]);
		}

		// Update low value of 'u' only of 'v' is still in stack
		// (i.e. it's a back edge, not cross edge).
		// Case 2 (per above discussion on Disc and Low value)
		else if (stackMember[v] == true)
			low[u] = min(low[u], disc[v]);
	}

	// head node found, pop the stack and print an SCC
	int w = 0; // To store stack extracted vertices
	if (low[u] == disc[u])
	{
		while (st->top() != u)
		{
			w = (int) st->top();
			cout << w << " ";
			stackMember[w] = false;
			st->pop();
		}
		w = (int) st->top();
		cout << w << "\n";
		stackMember[w] = false;
		st->pop();
	}
}

// The function to do DFS traversal. It uses SCCUtil()
void Graph::SCC()
{
	int *disc = new int[V];
	int *low = new int[V];
	bool *stackMember = new bool[V];
	stack<int> *st = new stack<int>();

	// Initialize disc and low, and stackMember arrays
	for (int i = 0; i < V; i++)
	{
		disc[i] = NIL;
		low[i] = NIL;
		stackMember[i] = false;
	}

	// Call the recursive helper function to find strongly
	// connected components in DFS tree with vertex 'i'
	for (int i = 0; i < V; i++)
		if (disc[i] == NIL)
			SCCUtil(i, disc, low, st, stackMember);
}

// Driver program to test above function
int main()
{
	cout << "\nSCCs in first graph \n";
	Graph g1(5);
	g1.addEdge(1, 0);
	g1.addEdge(0, 2);
	g1.addEdge(2, 1);
	g1.addEdge(0, 3);
	g1.addEdge(3, 4);
	g1.SCC();

	cout << "\nSCCs in second graph \n";
	Graph g2(4);
	g2.addEdge(0, 1);
	g2.addEdge(1, 2);
	g2.addEdge(2, 3);
	g2.SCC();

	cout << "\nSCCs in third graph \n";
	Graph g3(7);
	g3.addEdge(0, 1);
	g3.addEdge(1, 2);
	g3.addEdge(2, 0);
	g3.addEdge(1, 3);
	g3.addEdge(1, 4);
	g3.addEdge(1, 6);
	g3.addEdge(3, 5);
	g3.addEdge(4, 5);
	g3.SCC();

	cout << "\nSCCs in fourth graph \n";
	Graph g4(11);
	g4.addEdge(0,1);g4.addEdge(0,3);
	g4.addEdge(1,2);g4.addEdge(1,4);
	g4.addEdge(2,0);g4.addEdge(2,6);
	g4.addEdge(3,2);
	g4.addEdge(4,5);g4.addEdge(4,6);
	g4.addEdge(5,6);g4.addEdge(5,7);g4.addEdge(5,8);g4.addEdge(5,9);
	g4.addEdge(6,4);
	g4.addEdge(7,9);
	g4.addEdge(8,9);
	g4.addEdge(9,8);
	g4.SCC();

	cout << "\nSCCs in fifth graph \n";
	Graph g5(5);
	g5.addEdge(0,1);
	g5.addEdge(1,2);
	g5.addEdge(2,3);
	g5.addEdge(2,4);
	g5.addEdge(3,0);
	g5.addEdge(4,2);
	g5.SCC();

	return 0;
}
```  
hdu1269  
为了训练小希的方向感，Gardon建立了一座大城堡，里面有N个房间(N<=10000)和M条通道(M<=100000)，每个通道都是单向的，就是说若称某通道连通了A房间和B房间，只说明可以通过这个通道由A房间到达B房间，但并不说明通过它可以由B房间到达A房间。Gardon需要请你写个程序确认一下是否任意两个房间都是相互连通的，即：对于任意的i和j，至少存在一条路径可以从房间i到房间j，也存在一条路径可以从房间j到房间i。  
```c++
#include <bits/stdc++.h>

using namespace std;


const int N=10005;
int n, m;
vector<int> adj[N];
int dict[N], low[N];
int scc_num[N];
int scc_count;
int timer;
stack<int> stk;
bool instack[N];

void tarjan(int u){
    ++timer;
    dict[u]=timer;
    low[u]=timer;
    stk.push(u);
    instack[u]=true;
    for(int v:adj[u]){
        if(dict[v]==-1){
            tarjan(v);
            low[u]=min(low[u],low[v]);
        }else{
            if(instack[v]){
                low[u]=min(low[u],dict[v]);
            }
        }
    }

    if(dict[u]==low[u]){
        ++scc_count;
        while(stk.top()!=u){
            int t=stk.top();
            stk.pop();
            instack[t]=false;
            scc_num[t]=scc_count;
        }
        stk.pop();
        instack[u]=false;
        scc_num[u]=scc_count;
    }
}

int main(){

     int a, b;
     while(scanf("%d %d",&n, &m)!=EOF && (m+n)>0){
         for(int i=1;i<=n;++i){
             adj[i].clear();
         }
         memset(dict,-1,sizeof(dict));
         memset(low,-1,sizeof(low));
         memset(scc_num, -1, sizeof(scc_num));
         memset(instack,false,sizeof(instack));
         scc_count=0;
         timer=0;
         while(!stk.empty()) stk.pop();


         for(int i=0;i<m;++i){
             scanf("%d %d", &a, &b);
             adj[a].push_back(b);
         }

         for(int i=1;i<=n;++i){
             if(dict[i]==-1){
                 tarjan(i);
             }
         }

         int t=scc_num[1];
         int i=2;
         for(;i<=n;++i){
             if(scc_num[i]!=t){
                 printf("No\n");
                 break;
             }
         }
         if(i>n) printf("Yes\n");
     }
}  
```

<a href="https://www.luogu.com.cn/problem/P1073">P1073</a>  


### Union-Find    

1. LC1202
You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.<br>
You can swap the characters at any pair of indices in the given pairs any number of times.<br>
Return the lexicographically smallest string that s can be changed to after using the swaps.  
```Java
class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int n=s.length();
        UnionFind u=new UnionFind(n);
        for(List<Integer> pair:pairs){
            u.union(pair.get(0),pair.get(1));
        }
        Map<Integer,PriorityQueue<Character>> map=new HashMap();
        for(int i=0;i<n;i++){
            int root=u.find(i);
            if(!map.containsKey(root)){
                map.put(root,new PriorityQueue<>());
            }
            map.get(root).offer(s.charAt(i));
        }

        StringBuilder sb=new StringBuilder();
        for(int i=0;i<n;i++){
            int root=u.find(i);
            PriorityQueue<Character> queue=map.get(root);
            sb.append(queue.poll());
        }
        return sb.toString();
    }
}

class UnionFind{
    public int[] size;
    public int[] parent;
    public UnionFind(int n){
        size=new int[n];
        parent=new int[n];
        for(int i=0;i<n;i++){
            size[i]=1;
            parent[i]=i;
        }
    }

    public int find(int p){
        while(p!=parent[p]){
            parent[p]=parent[parent[p]];
            p=parent[p];
        }
        return p;
    }

    public int union(int p, int q){
        int proot=find(p);
        int qroot=find(q);
        if(proot==qroot) return size[proot];
        if(size[proot]>size[qroot]){
            parent[qroot]=proot;
            size[proot]+=size[qroot];
            return size[proot];
        }else {
            parent[proot]=qroot;
            size[qroot]+=size[proot];
            return size[qroot];
        }
    }
}
```  
<a href="https://blog.csdn.net/dm_vincent/article/details/7655764">并查集算法介绍</a>  

2. LC547  
There are N students in a class. Some of them are friends, while some are not. Their friendship is transitive in nature. For example, if A is a direct friend of B, and B is a direct friend of C, then A is an indirect friend of C. And we defined a friend circle is a group of students who are direct or indirect friends.<br>
Given a N*N matrix M representing the friend relationship between students in the class. If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise not. And you have to output the total number of friend circles among all the students.  
```Java
class Solution {
    public int findCircleNum(int[][] M) {
        int n=M.length;
        UnionFind uf=new UnionFind(n);
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(M[i][j]==1){
                    uf.union(i,j);
                }
            }
        }
        return uf.count;
    }
}

class UnionFind{
    int count;
    int[] size;
    int[] parent;
    public UnionFind(int n){
        count=n;
        size=new int[n];
        parent=new int[n];
        for(int i=0;i<n;i++){
            size[i]=1;
            parent[i]=i;
        }
    }

    public int find(int p){
        while(p!=parent[p]){
            parent[p]=parent[parent[p]];
            p=parent[p];
        }
        return p;
    }

    public void union(int p,int q){
        int proot=find(p);
        int qroot=find(q);
        if(proot==qroot){
            return;
        }
        if(size[proot]>size[qroot]){
            parent[qroot]=proot;
            size[proot]+=size[qroot];
            count--;
        }else{
            parent[proot]=qroot;
            size[qroot]+=size[proot];
            count--;
        }
    }
}
```


### 匈牙利算法
<a href="https://www.renfei.org/blog/bipartite-matching.html">二分图的最大匹配、完美匹配和匈牙利算法</a>    


LC785 判断图是否是二分图  

```Java
public class Hungary {
    int[][] graph;  //需要计算的图的邻接矩阵，注意每个顶点和它自己的连接被设置成了0。另外graph需要是n*n的矩阵
    int[] match;    //记录每个顶点的匹配顶点。假如match[0]=1，就是说顶点0和顶点1已经匹配
    int len;        //图的顶点的个数
    boolean[] used; //在从每个顶点搜索其增广路径的循环中，记录每个顶点是否已经被访问过

    public Hungary(int[][] graph) {
        this.graph = graph;
        len = graph.length;
        used = new boolean[len];

        match = new int[len];
        for (int i = 0; i < len; i++) {
            match[i] = -1;
            used[i] = false;
        }
    }

    //寻找顶点x的增广路径。如果能够寻找到则返回true，否则返回false。
    //匈牙利算法一个重要的定理：如果从一个顶点A出发，没有找到增广路径，那么无论再从别的点出发找到多少增广路径来改变现在的匹配，从A出发都永远找不到增广路径
    boolean findAugmentPath(int x) {
        for (int i = 0; i < len; i++) {
            if (graph[x][i] == 1) { //顶点x和顶点i之间有连接。需要注意的一点是我们在输入需要计算的图的邻接矩阵的时候把对角线上的元素设置为0
                if (!used[i]) {     //如果顶点i还未访问
                    used[i] = true;
                    //如果顶点i还未匹配，或者与顶点i匹配的那个顶点可以换个顶点匹配（也就是说可以把顶点i“让给”当前顶点x），则把顶点x和顶点i为对方的匹配顶点
                    //由于上一步已经将顶点i设置成used，所以findAugmentPath(match[i])不会再考虑顶点i了
                    if (match[i] == -1 || findAugmentPath(match[i])) {
                        match[x] = i;
                        match[i] = x;
                        System.out.println(x + "------>" + i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    void search() {
        //对于每个顶点都循环处理
        for (int i = 0; i < len; i++) {
            if (match[i] == -1) {   //如果当前顶点已经有匹配的顶点了，就略过此顶点
                clearUsed();    //新的一轮搜索，把used数组设置成false
                System.out.println("开始匹配顶点" + i);
                findAugmentPath(i);
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();

        for (int i = 0; i < len; i++) {
            System.out.println(i + "------>" + match[i]);
        }
    }

    void clearUsed() {
        for (int i = 0; i < len; i++) {
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        int[][] graph = {{0, 0, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 1},
                {1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0}};
        new Hungary(graph).search();
    }
}

```
LCP4  
你有一块棋盘，棋盘上有一些格子已经坏掉了。你还有无穷块大小为1 * 2的多米诺骨牌，你想把这些骨牌不重叠地覆盖在完好的格子上，请找出你最多能在棋盘上放多少块骨牌？这些骨牌可以横着或者竖着放。



输入：n, m代表棋盘的大小；broken是一个b * 2的二维数组，其中每个元素代表棋盘上每一个坏掉的格子的位置。

输出：一个整数，代表最多能在棋盘上放的骨牌数。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/broken-board-dominoes
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。  

```Java
class Solution {
    public int domino(int n, int m, int[][] broken) {
        int[] mat=new int[n*m];
        Arrays.fill(mat,-1);
        int[] b=new int[m*n];
        for(int i=0;i<broken.length;i++){
            int index=broken[i][0]*m+broken[i][1];
            b[index]=1;
        }
        int ans=0;
        for(int i=0;i<m*n;i++){
            if(mat[i]==-1 && b[i]!=1){
                boolean[] visited=new boolean[m*n];
                if(dfs(i,n,m,b,visited,mat)){
                    ans++;
                }
            }
        }

        return ans;
    }

    public boolean dfs(int index,int n,int m,int[] broken,boolean[] visited,int[] mat){
        int row=index/m;
        int col=index%m;
        int index1=index+1;
        if(index1<m*n && col<m-1 && !visited[index1] && broken[index1]!=1){
            visited[index1]=true;
            if(mat[index1]==-1 || dfs(mat[index1],n,m,broken,visited,mat)){
                mat[index]=index1;
                mat[index1]=index;
                return true;
            }
        }

        index1=index+m;
        if(index1<m*n && row<n-1 && !visited[index1] && broken[index1]!=1){
            visited[index1]=true;
            if(mat[index1]==-1 || dfs(mat[index1],n,m,broken,visited,mat)){
                mat[index]=index1;
                mat[index1]=index;
                return true;
            }
        }

        index1=index-1;
        if(index1>=0 && col>=1 && !visited[index1] && broken[index1]!=1){
            visited[index1]=true;
            if(mat[index1]==-1 || dfs(mat[index1],n,m,broken,visited,mat)){
                mat[index]=index1;
                mat[index1]=index;
                return true;
            }
        }

        index1=index-m;
        if(index1>=0 && row>=1 && !visited[index1] && broken[index1]!=1){
            visited[index1]=true;
            if(mat[index1]==-1 || dfs(mat[index1],n,m,broken,visited,mat)){
                mat[index]=index1;
                mat[index1]=index;
                return true;
            }
        }

        return false;
    }
}
```

### KM算法  
leetcode 1066  
On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.

We assign one unique bike to each worker so that the sum of the Manhattan distances between each worker and their assigned bike is minimized.

The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.

Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/campus-bikes-ii
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。  

```c++  
class Solution {
public:
    int assignBikes(vector<vector<int>>& workers, vector<vector<int>>& bikes) {
        int N=workers.size();
        int M=bikes.size();
        vector<vector<int>> dis(N,vector<int>(M,0));
        for(int i=0;i<N;++i){
            for(int j=0;j<M;++j){
                dis[i][j]=abs(workers[i][0]-bikes[j][0])+abs(workers[i][1]-bikes[j][1]);
            }
        }

        vector<int> expw(N,0);
        vector<int> expb(M,0);
        vector<bool> wvisit(N,false);
        vector<bool> bvisit(M,false);
        vector<int> bmat(M,-1);
        vector<int> needed;


        for(int i=0;i<N;++i){
            needed=vector<int>(M,INT_MIN);
            while(true){
                wvisit=vector<bool>(N,false);
                bvisit=vector<bool>(M,false);
                if(dfs(i,needed,wvisit,bvisit,dis,expw,expb,bmat)){
                    break;
                }
                int d=INT_MIN;
                for(int j=0;j<M;++j){
                    if(!bvisit[j]){
                        d=max(d,needed[j]);
                    }
                }
                for(int j=0;j<N;++j){
                    if(wvisit[j]){
                        expw[j]-=d;
                    }
                }
                for(int j=0;j<M;++j){
                    if(bvisit[j]){
                        expb[j]+=d;
                    }else{
                        needed[j]-=d;
                    }
                }
            }
        }

        int res=0;
        for(int i=0;i<M;++i){
            if(bmat[i]!=-1){
                res+=dis[bmat[i]][i];
            }
        }
        return res;
    }

    bool dfs(int w, vector<int> &needed, vector<bool> &wvisit, vector<bool> &bvisit,
        vector<vector<int>> &dis, vector<int> &expw, vector<int> &expb, vector<int> &bmat){
            wvisit[w]=true;
            for(int i=0;i<dis[0].size();++i){
                if(bvisit[i]) continue;
                int diff=expw[w]+expb[i]-dis[w][i];
                if(diff==0){
                    bvisit[i]=true;
                    if(bmat[i]==-1 || dfs(bmat[i],needed,wvisit,bvisit,dis,expw,expb,bmat)){
                        bmat[i]=w;
                        return true;
                    }
                }else{
                    needed[i]=max(needed[i],diff);
                }
            }
            return false;
        }
};
```

<a href="http://acm.hdu.edu.cn/showproblem.php?pid=2255">hdu Problem : 2255 ( 奔小康赚大钱 )   </a>  
```c++
#include <bits/stdc++.h>

using namespace std;


const int N=301;
int n;
int grid[N][N];
int expx[N];
int expy[N];
int matH[N];
int matP[N];
int visitedH[N];
int visitedP[N];
int sub;

bool dfs(int u){
    visitedP[u]=1;
    for(int i=0;i<n;++i){
        if(visitedH[i]) continue;
        int diff=expx[u]+expy[i]-grid[u][i];
        if(diff==0){
            visitedH[i]=1;
            if(matP[i]==-1 || dfs(matP[i])){
                matP[i]=u;
                matH[u]=i;
                return true;
            }
        }else{
            sub=min(sub,diff);
        }
    }
    return false;
}

int KM(){
    for(int i=0;i<n;++i){
        while(true){
            memset(visitedH,0,sizeof(visitedH));
            memset(visitedP,0,sizeof(visitedP));
            sub=INT_MAX;
            if(dfs(i)) break;

            for(int p=0;p<n;++p){
                if(visitedP[p]) expx[p]-=sub;
                if(visitedH[p]) expy[p]+=sub;
            }
        }
    }
    int res=0;
    for(int i=0;i<n;++i){
        int j=matH[i];
        res+=grid[i][j];
    }
    return res;
}

int main(){
    while(scanf("%d",&n)!=EOF){
        memset(matH,-1,sizeof(matH));
        memset(matP,-1,sizeof(matP));
        memset(visitedH,0,sizeof(visitedH));
        memset(visitedP,0,sizeof(visitedP));
        for(int i=0;i<n;++i){
            expx[i]=0;
            expy[i]=0;
            for(int j=0;j<n;++j){
                scanf("%d", &grid[i][j]);
                expx[i]=max(expx[i],grid[i][j]);
            }
        }
        int res=KM();
        printf("%d\n",res);
    }
}



```



### Ford fulkerson method Edmonds Karp algorithm for finding max flow    
<a href="https://www.geeksforgeeks.org/max-flow-problem-introduction/">introduction</a>  
<a href="https://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/">Ford-Fulkerson Algorithm for Maximum Flow Problem</a>  
```Java
import java.util.*;

/**
 * Date 04/14/2014
 * @author Tushar Roy
 *
 * Ford fulkerson method Edmonds Karp algorithm for finding max flow
 *
 * Capacity - Capacity of an edge to carry units from source to destination vertex
 * Flow - Actual flow of units from source to destination vertex of an edge
 * Residual capacity - Remaining capacity on this edge i.e capacity - flow
 * AugmentedPath - Path from source to sink which has residual capacity greater than 0
 *
 * Time complexity is O(VE^2)
 *
 * References:
 * http://www.geeksforgeeks.org/ford-fulkerson-algorithm-for-maximum-flow-problem/
 * https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
 */
public class FordFulkerson {

    public int maxFlow(int capacity[][], int source, int sink){

        //declare and initialize residual capacity as total avaiable capacity initially.
        int residualCapacity[][] = new int[capacity.length][capacity[0].length];
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity[0].length; j++) {
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        //this is parent map for storing BFS parent
        Map<Integer,Integer> parent = new HashMap<>();

        //stores all the augmented paths
        List<List<Integer>> augmentedPaths = new ArrayList<>();

        //max flow we can get in this network
        int maxFlow = 0;

        //see if augmented path can be found from source to sink.
        while(BFS(residualCapacity, parent, source, sink)){
            List<Integer> augmentedPath = new ArrayList<>();
            int flow = Integer.MAX_VALUE;
            //find minimum residual capacity in augmented path
            //also add vertices to augmented path list
            int v = sink;
            while(v != source){
                augmentedPath.add(v);
                int u = parent.get(v);
                if (flow > residualCapacity[u][v]) {
                    flow = residualCapacity[u][v];
                }
                v = u;
            }
            augmentedPath.add(source);
            Collections.reverse(augmentedPath);
            augmentedPaths.add(augmentedPath);

            //add min capacity to max flow
            maxFlow += flow;

            //decrease residual capacity by min capacity from u to v in augmented path
            // and increase residual capacity by min capacity from v to u
            v = sink;
            while(v != source){
                int u = parent.get(v);
                residualCapacity[u][v] -= flow;
                residualCapacity[v][u] += flow;
                v = u;
            }
        }
        printAugmentedPaths(augmentedPaths);
        return maxFlow;
    }

    /**
     * Prints all the augmented path which contribute to max flow
     */
    private void printAugmentedPaths(List<List<Integer>> augmentedPaths) {
        System.out.println("Augmented paths");
        augmentedPaths.forEach(path -> {
            path.forEach(i -> System.out.print(i + " "));
            System.out.println();
        });
    }

    /**
     * Breadth first search to find augmented path
     */
    private boolean BFS(int[][] residualCapacity, Map<Integer,Integer> parent,
            int source, int sink){
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        boolean foundAugmentedPath = false;
        //see if we can find augmented path from source to sink
        while(!queue.isEmpty()){
            int u = queue.poll();
            for(int v = 0; v < residualCapacity.length; v++){
                //explore the vertex only if it is not visited and its residual capacity is
                //greater than 0
                if(!visited.contains(v) &&  residualCapacity[u][v] > 0){
                    //add in parent map saying v got explored by u
                    parent.put(v, u);
                    //add v to visited
                    visited.add(v);
                    //add v to queue for BFS
                    queue.add(v);
                    //if sink is found then augmented path is found
                    if ( v == sink) {
                        foundAugmentedPath = true;
                        break;
                    }
                }
            }
        }
        //returns if augmented path is found from source to sink or not
        return foundAugmentedPath;
    }

    public static void main(String args[]){
        FordFulkerson ff = new FordFulkerson();
        int[][] capacity = {{0, 3, 0, 3, 0, 0, 0},
                            {0, 0, 4, 0, 0, 0, 0},
                            {3, 0, 0, 1, 2, 0, 0},
                            {0, 0, 0, 0, 2, 6, 0},
                            {0, 1, 0, 0, 0, 0, 1},
                            {0, 0, 0, 0, 0, 0, 9},
                            {0, 0, 0, 0, 0, 0, 0}};

        System.out.println("\nMaximum capacity " + ff.maxFlow(capacity, 0, 6));
    }
}
```

## 最小费用最大流  
结合最大流算法和SPFA  
如题，给出一个网络图，以及其源点和汇点，每条边已知其最大流量和单位流量费用，求出其网络最大流和在最大流情况下的最小费用。  <a href="https://www.luogu.com.cn/problem/P3381">链接</a>
第一行包含四个正整数N、M、S、T，分别表示点的个数、有向边的个数、源点序号、汇点序号。

接下来M行每行包含四个正整数ui、vi、wi、fi，表示第i条有向边从ui出发，到达vi，边权为wi（即该边最大流量为wi），单位流量的费用为fi。

```c++
#include <bits/stdc++.h>


using namespace std;
const int INF=0x3f3f3f3f;

int N, M, S, T;


const int MAXN=5001;
const int MAXM=50001;
int dis[MAXN];
int preEdge[MAXN];
int preNode[MAXN];
int inquee[MAXN];
int edge_num=1;
int head[MAXN];

struct Edge{
    int next;
    int from;
    int to;
    int flow;
    int cost;
}edge[MAXM<<1];

void addEdge(int from, int to, int flow, int cost){
    ++edge_num;
    edge[edge_num].next=head[from];
    edge[edge_num].from=from;
    edge[edge_num].to=to;
    edge[edge_num].flow=flow;
    edge[edge_num].cost=cost;
    head[from]=edge_num;
}

void spfa(){
    memset(dis, INF, sizeof(dis));
    memset(preNode,-1,sizeof(preNode));
    memset(preEdge,-1,sizeof(preEdge));
    memset(inquee,0,sizeof(inquee));
    dis[S]=0;
    queue<int> q;
    q.push(S);
    inquee[S]=1;
    while(!q.empty()){
        int cur=q.front();
        q.pop();
        inquee[cur]=0;
        for(int i=head[cur];i!=-1;i=edge[i].next){
            int v=edge[i].to;
            int f=edge[i].flow;
            int c=edge[i].cost;
            if(f>0 && dis[cur]+c<dis[v]){
                dis[v]=dis[cur]+c;
                preEdge[v]=i;
                preNode[v]=cur;
                if(!inquee[v]){
                    inquee[v]=1;
                    q.push(v);
                }
            }
        }
    }

}

int main(){
    memset(head,-1,sizeof(head));
    scanf("%d %d %d %d", &N, &M, &S, &T);
    for(int i=1;i<=M;++i){
        int ui, vi, wi, fi;
        scanf("%d %d %d %d", &ui, &vi, &wi, &fi);
        addEdge(ui,vi,wi,fi);
        addEdge(vi,ui,0,-fi);
    }
    int flow_res=0;
    int cost_res=0;
    while(true){
        spfa();

        if(preNode[T]==-1) break;
        int low=INF;
        int e=preEdge[T];
        while(true){
            low=min(low,edge[e].flow);
            int u=edge[e].from;
            if(u==S) break;
            e=preEdge[u];
        }


        flow_res+=low;
        cost_res+=dis[T]*low;

        e=preEdge[T];
        while(true){
            edge[e].flow-=low;
            edge[e^1].flow+=low;
            int u=edge[e].from;
            if(u==S) break;
            e=preEdge[u];
        }

    }

    printf("%d %d", flow_res, cost_res);

    return 0;
}

```

## 拓扑排序  
LC210  
There are a total of n courses you have to take, labeled from 0 to n-1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.  

BFS  
```java
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] adj=new ArrayList[numCourses];
        int[] indegree=new int[numCourses];
        for(int i=0;i<numCourses;i++){
            adj[i]=new ArrayList();
        }
        for(int[] pre:prerequisites){
            int u=pre[1];
            int v=pre[0];
            adj[u].add(v);
            indegree[v]++;
        }

        int[] res=new int[numCourses];
        int t=0;
        Queue<Integer> queue=new LinkedList();

        for(int i=0;i<numCourses;i++){
            if(indegree[i]==0){
                queue.offer(i);
            }
        }

        int edges=0;
        while(!queue.isEmpty()){
            int u=queue.poll();
            res[t++]=u;
            List<Integer> nexts=adj[u];
            for(int next:nexts){
                indegree[next]--;
                edges++;
                if(indegree[next]==0){
                    queue.offer(next);
                }
            }
        }

        if(edges==prerequisites.length){
            return res;
        }
        return new int[0];

    }
}
```  

DFS  
```java
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer>[] adj=new ArrayList[numCourses];

        for(int i=0;i<numCourses;i++){
            adj[i]=new ArrayList();
        }
        for(int[] pre:prerequisites){
            int u=pre[1];
            int v=pre[0];
            adj[u].add(v);

        }

        int[] res=new int[numCourses];
        int[] memo=new int[numCourses];
        boolean[] visited=new boolean[numCourses];
        Stack<Integer> stack=new Stack();
        for(int i=0;i<numCourses;i++){
            if(isCircle(i,visited,stack,adj,memo)){
                return new int[0];
            }
        }
        for(int t=0;t<numCourses;t++){
            res[t]=stack.pop();
        }
        return res;

    }

    public boolean isCircle(int cur,boolean[] visited,Stack<Integer> stack,List<Integer>[] adj,int[] memo){
        if(visited[cur]){
            return true;
        }
        if(memo[cur]==-1) return false;
        visited[cur]=true;
        List<Integer> nexts=adj[cur];
        for(int next:nexts){
            if(isCircle(next,visited,stack,adj,memo)){
                return true;
            }
        }
        stack.push(cur);
        memo[cur]=-1;
        visited[cur]=false;
        return false;
    }

}
```  

## Dijkstra's Algorithm Single Source Shortest Path Graph Algorithm  
```java
/**
 * Date 10/11/2014
 * @author Tushar Roy
 *
 * Find single source shortest path using Dijkstra's algorithm
 *
 * Space complexity - O(E + V)
 * Time complexity - O(ElogV)
 *
 * References
 * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 * CLRS book*/
public class DijkstraShortestPath {

    public Map<Vertex<Integer>,Integer> shortestPath(Graph<Integer> graph, Vertex<Integer> sourceVertex){

        //heap + map data structure
        BinaryMinHeap<Vertex<Integer>> minHeap = new BinaryMinHeap<>();

        //stores shortest distance from root to every vertex
        Map<Vertex<Integer>,Integer> distance = new HashMap<>();

        //stores parent of every vertex in shortest distance
        Map<Vertex<Integer>, Vertex<Integer>> parent = new HashMap<>();

        //initialize all vertex with infinite distance from source vertex
        for(Vertex<Integer> vertex : graph.getAllVertex()){
            minHeap.add(Integer.MAX_VALUE, vertex);
        }

        //set distance of source vertex to 0
        minHeap.decrease(sourceVertex, 0);

        //put it in map
        distance.put(sourceVertex, 0);

        //source vertex parent is null
        parent.put(sourceVertex, null);

        //iterate till heap is not empty
        while(!minHeap.empty()){
            //get the min value from heap node which has vertex and distance of that vertex from source vertex.
            BinaryMinHeap<Vertex<Integer>>.Node heapNode = minHeap.extractMinNode();
            Vertex<Integer> current = heapNode.key;

            //update shortest distance of current vertex from source vertex
            distance.put(current, heapNode.weight);

            //iterate through all edges of current vertex
            for(Edge<Integer> edge : current.getEdges()){

                //get the adjacent vertex
                Vertex<Integer> adjacent = getVertexForEdge(current, edge);

                //if heap does not contain adjacent vertex means adjacent vertex already has shortest distance from source vertex
                if(!minHeap.containsData(adjacent)){
                    continue;
                }

                //add distance of current vertex to edge weight to get distance of adjacent vertex from source vertex
                //when it goes through current vertex
                int newDistance = distance.get(current) + edge.getWeight();

                //see if this above calculated distance is less than current distance stored for adjacent vertex from source vertex
                if(minHeap.getWeight(adjacent) > newDistance) {
                    minHeap.decrease(adjacent, newDistance);
                    parent.put(adjacent, current);
                }
            }
        }
        return distance;
    }

    private Vertex<Integer> getVertexForEdge(Vertex<Integer> v, Edge<Integer> e){
        return e.getVertex1().equals(v) ? e.getVertex2() : e.getVertex1();
    }

    public static void main(String args[]){
        Graph<Integer> graph = new Graph<>(false);
        /*graph.addEdge(0, 1, 4);
        graph.addEdge(1, 2, 8);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 4, 9);
        graph.addEdge(4, 5, 10);
        graph.addEdge(2, 5, 4);
        graph.addEdge(1, 7, 11);
        graph.addEdge(0, 7, 8);
        graph.addEdge(2, 8, 2);
        graph.addEdge(3, 5, 14);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 8, 6);
        graph.addEdge(6, 7, 1);
        graph.addEdge(7, 8, 7);*/

        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 3, 2);
        graph.addEdge(1, 4, 9);
        graph.addEdge(1, 5, 3);
        graph.addEdge(5, 6, 2);
        graph.addEdge(6, 4, 2);
        graph.addEdge(3, 4, 3);

        DijkstraShortestPath dsp = new DijkstraShortestPath();
        Vertex<Integer> sourceVertex = graph.getVertex(1);
        Map<Vertex<Integer>,Integer> distance = dsp.shortestPath(graph, sourceVertex);
        System.out.print(distance);
    }
}
```  
LC743  
There are N network nodes, labelled 1 to N.

Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.  

```java
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer,Map<Integer,Integer>> map=new HashMap();
        for(int[] t:times){
            int u=t[0];
            int v=t[1];
            int w=t[2];
            if(!map.containsKey(u)){
                map.put(u,new HashMap());
            }
            map.get(u).put(v,w);
        }

        PriorityQueue<int[]> pq=new PriorityQueue<>(new Comparator<int[]>(){
            public int compare(int[] a,int[] b){
                return a[1]-b[1];
            }
        });

        pq.offer(new int[]{K,0});

        int[] cost=new int[N+1];
        Arrays.fill(cost,Integer.MAX_VALUE);
        cost[K]=0;

        boolean[] visited=new boolean[N+1];

        while(!pq.isEmpty()){
            int[] cur=pq.poll();
            if(!visited[cur[0]]){
                visited[cur[0]]=true;
                if(!map.containsKey(cur[0])) continue;
                Map<Integer,Integer> nexts=map.get(cur[0]);
                for(int next:nexts.keySet()){
                    if(visited[next]) continue;
                    int tmp=cur[1]+nexts.get(next);
                    if(tmp<cost[next]){
                        cost[next]=tmp;
                        pq.offer(new int[]{next,cost[next]});
                    }
                }
            }

        }

        int ans=0;
        for(int i=1;i<=N;i++){
            ans=Math.max(ans,cost[i]);
        }

        return ans==Integer.MAX_VALUE?-1:ans;
    }
}
```

LC787  
There are n cities connected by m flights. Each fight starts from city u and arrives at v with a price w.

Now given all the cities and flights, together with starting city src and the destination dst, your task is to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.  
```java
class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        int[][] graph=new int[n][n];
        for(int[] f:flights){
            int from=f[0];
            int to=f[1];
            int cost=f[2];
            graph[from][to]=cost;
        }

        PriorityQueue<City> pq=new PriorityQueue();
        pq.offer(new City(src,0,0));

        int[] cost=new int[n];
        int[] stop=new int[n];
        Arrays.fill(cost,Integer.MAX_VALUE);
        Arrays.fill(stop,Integer.MAX_VALUE);
        cost[src]=0;
        stop[src]=0;

        while(!pq.isEmpty()){
            City cur=pq.poll();
            if(cur.stop>K){
              if(cur.id==dst) return cur.cost;
                continue;
            }
            if(cur.id==dst) return cur.cost;
            for(int i=0;i<n;i++){
                if(graph[cur.id][i]>0){
                    int newcost=cur.cost+graph[cur.id][i];
                    int newstop=cur.stop+1;
                    if(newcost<cost[i]){
                        cost[i]=newcost;
                    }else{
                        if(newstop<stop[i]){
                            stop[i]=newstop;
                        }
                    }
                    pq.offer(new City(i,newcost,newstop));
                }
            }
        }

        return -1;
    }

}

class City implements Comparable<City> {
    int id;
    int cost;
    int stop;

    public City(int id,int cost,int stop){
        this.id=id;
        this.cost=cost;
        this.stop=stop;
    }

    public int compareTo(City c){
        return this.cost-c.cost;
    }
}
```


## Bellman Ford algorithm to find single source shortest path in directed graph  
Bellman ford works with negative edges as well unlike Dijksra's algorithm. If there is negative weight cycle it detects it.


```java
/**
 * Date 11/05/2015
 * @author Tushar Roy
 *
 * Write program for Bellman Ford algorithm to find single source shortest path in directed graph.
 * Bellman ford works with negative edges as well unlike Dijksra's algorithm. If there is negative
 * weight cycle it detects it.
 *
 * Time complexity - O(EV)
 * Space complexity - O(V)
 *
 * References
 * https://en.wikipedia.org/wiki/Bellman%E2%80%93Ford_algorithm
 * http://www.geeksforgeeks.org/dynamic-programming-set-23-bellman-ford-algorithm/
 */

public class BellmanFordShortestPath {

    //some random big number is treated as infinity. I m not taking INTEGER_MAX as infinity because
    //doing any addition on that causes integer overflow
    private static int INFINITY = 10000000;

    class NegativeWeightCycleException extends RuntimeException {
    }

    public Map<Vertex<Integer>, Integer> getShortestPath(Graph<Integer> graph,
            Vertex<Integer> sourceVertex) {

        Map<Vertex<Integer>, Integer> distance = new HashMap<>();
        Map<Vertex<Integer>, Vertex<Integer>> parent = new HashMap<>();

        //set distance of every vertex to be infinity initially
        for(Vertex<Integer> v : graph.getAllVertex()) {
            distance.put(v, INFINITY);
            parent.put(v, null);
        }

        //set distance of source vertex to be 0
        distance.put(sourceVertex, 0);

        int V = graph.getAllVertex().size();

        //relax edges repeatedly V - 1 times
        for (int i = 0; i < V - 1 ; i++) {
            for (Edge<Integer> edge : graph.getAllEdges()) {
                Vertex<Integer> u = edge.getVertex1();
                Vertex<Integer> v = edge.getVertex2();
                //relax the edge
                //if we get better distance to v via u then use this distance
                //and set u as parent of v.
                if (distance.get(u) + edge.getWeight() < distance.get(v)) {
                    distance.put(v, distance.get(u) + edge.getWeight());
                    parent.put(v, u);
                }
            }
        }

        //relax all edges again. If we still get lesser distance it means
        //there is negative weight cycle in the graph. Throw exception in that
        //case
        for (Edge<Integer> edge : graph.getAllEdges()) {
            Vertex<Integer> u = edge.getVertex1();
            Vertex<Integer> v = edge.getVertex2();
            if (distance.get(u) + edge.getWeight() < distance.get(v)) {
                throw new NegativeWeightCycleException();
            }
        }
        return distance;
    }

    public static void main(String args[]){

        Graph<Integer> graph = new Graph<>(false);
        graph.addEdge(0, 3, 8);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 5);
        graph.addEdge(1, 2, -3);
        graph.addEdge(2, 4, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 3, 1);

        BellmanFordShortestPath shortestPath = new BellmanFordShortestPath();
        Vertex<Integer> startVertex = graph.getAllVertex().iterator().next();
        Map<Vertex<Integer>,Integer> distance = shortestPath.getShortestPath(graph, startVertex);
        System.out.println(distance);
    }

}
```  
LC743  
There are N network nodes, labelled 1 to N.

Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.  

```java
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        int[] cost=new int[N+1];
        for(int i=1;i<=N;i++){
            cost[i]=Integer.MAX_VALUE;
        }
        cost[K]=0;
        for(int i=1;i<=N-1;i++){
            boolean flag=true;
            for(int[] t:times){
                int u=t[0];
                int v=t[1];
                int c=t[2];
                if(cost[u]!= Integer.MAX_VALUE && cost[u]+c<cost[v]){
                    flag=false;
                    cost[v]=cost[u]+c;
                }
            }
            if(flag) break;
        }

        int res=0;
        for(int i=1;i<=N;i++){
            res=Math.max(res,cost[i]);
        }
        return res==Integer.MAX_VALUE?-1:res;
    }
}
```

## Shortest Path Faster Algorithm (SPFA)   
lc743  
```c++
class Solution {
public:
    int networkDelayTime(vector<vector<int>>& times, int N, int K) {
        vector<unordered_map<int,int>> adj(N+1);
        for(auto t:times){
            adj[t[0]][t[1]]=t[2];
        }

        vector<int> dis(N+1,INT_MAX);
        dis[K]=0;
        queue<int> q;
        q.push(K);
        vector<int> inque(N+1,0);
        inque[K]=1;
        while(!q.empty()){
            int cur=q.front();
            inque[cur]=0;
            q.pop();
            for(auto it=adj[cur].begin();it!=adj[cur].end();++it){
                int v=it->first, cost=it->second;
                if(dis[cur]+cost<dis[v]){
                    dis[v]=dis[cur]+cost;
                    if(!inque[v]){
                        q.push(v);
                        inque[v]=1;
                    }
                }
            }
        }
        int res=0;
        for(int i=1;i<=N;++i) res=max(res,dis[i]);
        return res==INT_MAX?-1:res;
    }
};
```

## Floyd-Warshall Algorithm for finding all pair shortest path  
```java
/**
 * Date 11/02/2015
 * @author Tushar Roy
 *
 * Floyd-Warshall Algorithm for finding all pair shortest path.
 *
 * Time complexity - O(V^3)
 * Space complexity - O(V^2)
 *
 * References
 * https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
 */
public class FloydWarshallAllPairShortestPath {

    class NegativeWeightCycleException extends RuntimeException {

    }

    private static final int INF = 1000000;

    public int[][] allPairShortestPath(int[][] distanceMatrix) {

        int distance[][] = new int[distanceMatrix.length][distanceMatrix.length];
        int path[][] = new int[distanceMatrix.length][distanceMatrix.length];

        for (int i=0; i < distanceMatrix.length; i++) {
            for (int j=0; j< distanceMatrix[i].length; j++){
                distance[i][j] = distanceMatrix[i][j];
                if (distanceMatrix[i][j] != INF && i != j) {
                    path[i][j] = i;
                } else {
                    path[i][j] = -1;
                }
            }
        }

        for(int k=0; k < distanceMatrix.length; k++){
            for(int i=0; i < distanceMatrix.length; i++){
                for(int j=0; j < distanceMatrix.length; j++){
                    if(distance[i][k] == INF || distance[k][j] == INF) {
                        continue;
                    }
                    if(distance[i][j] > distance[i][k] + distance[k][j]){
                        distance[i][j] = distance[i][k] + distance[k][j];
                        path[i][j] = path[k][j];
                    }
                }
            }
        }

        //look for negative weight cycle in the graph
        //if values on diagonal of distance matrix is negative
        //then there is negative weight cycle in the graph.
        for(int i = 0; i < distance.length; i++) {
            if(distance[i][i] < 0) {
                throw new NegativeWeightCycleException();
            }
        }

        printPath(path, 3, 2);
        return distance;
    }

    public void printPath(int[][] path, int start, int end) {
        if(start < 0 || end < 0 || start >= path.length || end >= path.length) {
            throw new IllegalArgumentException();
        }

        System.out.println("Actual path - between " + start + " " + end);
        Deque<Integer> stack = new LinkedList<>();
        stack.addFirst(end);
        while (true) {
            end = path[start][end];
            if(end == -1) {
                return;
            }
            stack.addFirst(end);
            if(end == start) {
                break;
            }
        }

        while (!stack.isEmpty()) {
            System.out.print(stack.pollFirst() + " ");
        }

        System.out.println();
    }

    public static void main(String args[]){
        int[][] graph = {
                {0,   3,   6,   15},
                {INF, 0,  -2,   INF},
                {INF, INF, 0,   2},
                {1,   INF, INF, 0}
        };

        FloydWarshallAllPairShortestPath shortestPath = new FloydWarshallAllPairShortestPath();
        int[][] distance = shortestPath.allPairShortestPath(graph);
        System.out.println("Minimum Distance matrix");
        for(int i=0; i < distance.length; i++){
            for(int j=0; j < distance.length; j++){
                System.out.print(distance[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
```
LC743  
There are N network nodes, labelled 1 to N.

Given times, a list of travel times as directed edges times[i] = (u, v, w), where u is the source node, v is the target node, and w is the time it takes for a signal to travel from source to target.

Now, we send a signal from a certain node K. How long will it take for all nodes to receive the signal? If it is impossible, return -1.   
```java
class Solution {
    public int networkDelayTime(int[][] times, int N, int K) {
        int inf=Integer.MAX_VALUE;
        int[][] mat=new int[N+1][N+1];
        for(int[] m:mat){
            Arrays.fill(m,inf);
        }
        for(int[] t:times){
            int u=t[0];
            int v=t[1];
            int c=t[2];
            mat[u][v]=c;
        }
        for(int i=1;i<=N;i++){
            mat[i][i]=0;
        }

        for(int k=1;k<=N;k++){
            for(int i=1;i<=N;i++){
                for(int j=1;j<=N;j++){
                    if(mat[i][k]==inf || mat[k][j]==inf){
                        continue;
                    }
                    mat[i][j]=Math.min(mat[i][j],mat[i][k]+mat[k][j]);
                }
            }
        }

        int res=0;
        for(int i=1;i<=N;i++){
            if(mat[K][i]==inf) return -1;
            res=Math.max(res,mat[K][i]);
        }
        return res;
    }
}
```
## Find minimum spanning tree usinig Prim’s algorithm  
```Java
// A Java program for Prim's Minimum Spanning Tree (MST) algorithm.
// The program is for adjacency matrix representation of the graph

import java.util.*;
import java.lang.*;
import java.io.*;

class MST {
	// Number of vertices in the graph
	private static final int V = 5;

	// A utility function to find the vertex with minimum key
	// value, from the set of vertices not yet included in MST
	int minKey(int key[], Boolean mstSet[])
	{
		// Initialize min value
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < V; v++)
			if (mstSet[v] == false && key[v] < min) {
				min = key[v];
				min_index = v;
			}

		return min_index;
	}

	// A utility function to print the constructed MST stored in
	// parent[]
	void printMST(int parent[], int graph[][])
	{
		System.out.println("Edge \tWeight");
		for (int i = 1; i < V; i++)
			System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
	}

	// Function to construct and print MST for a graph represented
	// using adjacency matrix representation
	void primMST(int graph[][])
	{
		// Array to store constructed MST
		int parent[] = new int[V];

		// Key values used to pick minimum weight edge in cut
		int key[] = new int[V];

		// To represent set of vertices not yet included in MST
		Boolean mstSet[] = new Boolean[V];

		// Initialize all keys as INFINITE
		for (int i = 0; i < V; i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}

		// Always include first 1st vertex in MST.
		key[0] = 0; // Make key 0 so that this vertex is
		// picked as first vertex
		parent[0] = -1; // First node is always root of MST

		// The MST will have V vertices
		for (int count = 0; count < V - 1; count++) {
			// Pick thd minimum key vertex from the set of vertices
			// not yet included in MST
			int u = minKey(key, mstSet);

			// Add the picked vertex to the MST Set
			mstSet[u] = true;

			// Update key value and parent index of the adjacent
			// vertices of the picked vertex. Consider only those
			// vertices which are not yet included in MST
			for (int v = 0; v < V; v++)

				// graph[u][v] is non zero only for adjacent vertices of m
				// mstSet[v] is false for vertices not yet included in MST
				// Update the key only if graph[u][v] is smaller than key[v]
				if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
					parent[v] = u;
					key[v] = graph[u][v];
				}
		}

		// print the constructed MST
		printMST(parent, graph);
	}

	public static void main(String[] args)
	{
		/* Let us create the following graph
		2 3
		(0)--(1)--(2)
		| / \ |
		6| 8/ \5 |7
		| /	 \ |
		(3)-------(4)
			9		 */
		MST t = new MST();
		int graph[][] = new int[][] { { 0, 2, 0, 6, 0 },
									{ 2, 0, 3, 8, 5 },
									{ 0, 3, 0, 0, 7 },
									{ 6, 8, 0, 0, 9 },
									{ 0, 5, 7, 9, 0 } };

		// Print the solution
		t.primMST(graph);
	}
}
// This code is contributed by Aakash Hasija

```
## Find minimum spanning tree usinig Kruskals algorithm   
```java
/**
 * Date 09/25/2014
 *
 * @author Tushar Roy
 *         <p>
 *         Find minimum spanning tree usinig Kruskals algorithm
 *         <p>
 *         Time complexity - O(ElogE)
 *         Space complexity - O(E + V)
 *         <p>
 *         References
 *         https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
 */

public class KruskalMST {
    /**
     * Comparator to sort edges by weight in non decreasing order
     */
    public class EdgeComparator implements Comparator<Edge<Integer>> {
        @Override
        public int compare(Edge<Integer> edge1, Edge<Integer> edge2) {
            if (edge1.getWeight() <= edge2.getWeight()) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public List<Edge<Integer>> getMST(Graph<Integer> graph) {
        List<Edge<Integer>> allEdges = graph.getAllEdges();
        EdgeComparator edgeComparator = new EdgeComparator();

        //sort all edges in non decreasing order
        Collections.sort(allEdges, edgeComparator);
        DisjointSet disjointSet = new DisjointSet();

        //create as many disjoint sets as the total vertices
        for (Vertex<Integer> vertex : graph.getAllVertex()) {
            disjointSet.makeSet(vertex.getId());
        }

        List<Edge<Integer>> resultEdge = new ArrayList<Edge<Integer>>();

        for (Edge<Integer> edge : allEdges) {
            //get the sets of two vertices of the edge
            long root1 = disjointSet.findSet(edge.getVertex1().getId());
            long root2 = disjointSet.findSet(edge.getVertex2().getId());

            //check if the vertices are in same set or different set
            //if verties are in same set then ignore the edge
            if (root1 == root2) {
                continue;
            } else {
                //if vertices are in different set then add the edge to result and union these two sets into one
                resultEdge.add(edge);
                disjointSet.union(edge.getVertex1().getId(), edge.getVertex2().getId());
            }

        }
        return resultEdge;
    }

    public static void main(String args[]) {
        Graph<Integer> graph = new Graph<Integer>(false);
        graph.addEdge(1, 2, 4);
        graph.addEdge(1, 3, 1);
        graph.addEdge(2, 5, 1);
        graph.addEdge(2, 6, 3);
        graph.addEdge(2, 4, 2);
        graph.addEdge(6, 5, 2);
        graph.addEdge(6, 4, 3);
        graph.addEdge(4, 7, 2);
        graph.addEdge(3, 4, 5);
        graph.addEdge(3, 7, 8);
        KruskalMST mst = new KruskalMST();
        List<Edge<Integer>> result = mst.getMST(graph);
        for (Edge<Integer> edge : result) {
            System.out.println(edge.getVertex1() + " " + edge.getVertex2());
        }
    }
}
```
## Hamiltonian Cycle   
```Java
/* Java program for solution of Hamiltonian Cycle problem
using backtracking */
class HamiltonianCycle
{
	final int V = 5;
	int path[];

	/* A utility function to check if the vertex v can be
	added at index 'pos'in the Hamiltonian Cycle
	constructed so far (stored in 'path[]') */
	boolean isSafe(int v, int graph[][], int path[], int pos)
	{
		/* Check if this vertex is an adjacent vertex of
		the previously added vertex. */
		if (graph[path[pos - 1]][v] == 0)
			return false;

		/* Check if the vertex has already been included.
		This step can be optimized by creating an array
		of size V */
		for (int i = 0; i < pos; i++)
			if (path[i] == v)
				return false;

		return true;
	}

	/* A recursive utility function to solve hamiltonian
	cycle problem */
	boolean hamCycleUtil(int graph[][], int path[], int pos)
	{
		/* base case: If all vertices are included in
		Hamiltonian Cycle */
		if (pos == V)
		{
			// And if there is an edge from the last included
			// vertex to the first vertex
			if (graph[path[pos - 1]][path[0]] == 1)
				return true;
			else
				return false;
		}

		// Try different vertices as a next candidate in
		// Hamiltonian Cycle. We don't try for 0 as we
		// included 0 as starting point in hamCycle()
		for (int v = 1; v < V; v++)
		{
			/* Check if this vertex can be added to Hamiltonian
			Cycle */
			if (isSafe(v, graph, path, pos))
			{
				path[pos] = v;

				/* recur to construct rest of the path */
				if (hamCycleUtil(graph, path, pos + 1) == true)
					return true;

				/* If adding vertex v doesn't lead to a solution,
				then remove it */
				path[pos] = -1;
			}
		}

		/* If no vertex can be added to Hamiltonian Cycle
		constructed so far, then return false */
		return false;
	}

	/* This function solves the Hamiltonian Cycle problem using
	Backtracking. It mainly uses hamCycleUtil() to solve the
	problem. It returns false if there is no Hamiltonian Cycle
	possible, otherwise return true and prints the path.
	Please note that there may be more than one solutions,
	this function prints one of the feasible solutions. */
	int hamCycle(int graph[][])
	{
		path = new int[V];
		for (int i = 0; i < V; i++)
			path[i] = -1;

		/* Let us put vertex 0 as the first vertex in the path.
		If there is a Hamiltonian Cycle, then the path can be
		started from any point of the cycle as the graph is
		undirected */
		path[0] = 0;
		if (hamCycleUtil(graph, path, 1) == false)
		{
			System.out.println("\nSolution does not exist");
			return 0;
		}

		printSolution(path);
		return 1;
	}

	/* A utility function to print solution */
	void printSolution(int path[])
	{
		System.out.println("Solution Exists: Following" +
						" is one Hamiltonian Cycle");
		for (int i = 0; i < V; i++)
			System.out.print(" " + path[i] + " ");

		// Let us print the first vertex again to show the
		// complete cycle
		System.out.println(" " + path[0] + " ");
	}

	// driver program to test above function
	public static void main(String args[])
	{
		HamiltonianCycle hamiltonian =
								new HamiltonianCycle();
		/* Let us create the following graph
		(0)--(1)--(2)
			| / \ |
			| / \ |
			| /	 \ |
		(3)-------(4) */
		int graph1[][] = {{0, 1, 0, 1, 0},
			{1, 0, 1, 1, 1},
			{0, 1, 0, 0, 1},
			{1, 1, 0, 0, 1},
			{0, 1, 1, 1, 0},
		};

		// Print the solution
		hamiltonian.hamCycle(graph1);

		/* Let us create the following graph
		(0)--(1)--(2)
			| / \ |
			| / \ |
			| /	 \ |
		(3)	 (4) */
		int graph2[][] = {{0, 1, 0, 1, 0},
			{1, 0, 1, 1, 1},
			{0, 1, 0, 0, 1},
			{1, 1, 0, 0, 0},
			{0, 1, 1, 0, 0},
		};

		// Print the solution
		hamiltonian.hamCycle(graph2);
	}
}
// This code is contributed by Abhishek Shankhadhar
```

## Traveling Salesman Problem Dynamic Programming Held-Karp  
LC943  
Given an array A of strings, find any smallest string that contains each string in A as a substring.

We may assume that no string in A is substring of another string in A.  
```c++
class Solution {
public:
    string shortestSuperstring(vector<string>& A) {
        int N=A.size();
        vector<vector<string>> dp(1<<N,vector<string>(N));
        vector<vector<int>> overlap(N,vector<int>(N,0));
        for(int i=0;i<N;++i){
            for(int j=0;j<N;++j){
                if(i!=j){
                    for(int k=min(A[i].size(),A[j].size());k>=0;--k){
                        if(A[i].substr(A[i].size()-k)==A[j].substr(0,k)){
                            overlap[i][j]=k;
                            break;
                        }
                    }
                }
            }
        }

        for(int i=0;i<N;++i){
            dp[1<<i][i]+=A[i];
        }
        for(int mask=1;mask<(1<<N);++mask){
            for(int i=0;i<N;++i){
                if((mask&(1<<i))!=0){
                    int pre=mask^(1<<i);
                    for(int j=0;j<N;++j){
                        if((pre&(1<<j))!=0){
                            string tmp=dp[pre][j]+A[i].substr(overlap[j][i]);
                            if(dp[mask][i].empty() || tmp.size()<dp[mask][i].size()){
                                dp[mask][i]=tmp;
                            }
                        }
                    }
                }
            }
        }

        string res=dp[(1<<N)-1][0];
        for(int i=1;i<N;++i){
            if(dp[(1<<N)-1][i].size()<res.size()){
                res=dp[(1<<N)-1][i];
            }
        }
        return res;
    }
};
```
<a href="https://www.youtube.com/watch?v=-JjA4BLQyqE">视频详解</a>
<a href="https://www.youtube.com/watch?v=Q4zHb-Swzro">视频详解2</a>
<a href="https://en.wikipedia.org/wiki/Held%E2%80%93Karp_algorithm">wiki</a>
