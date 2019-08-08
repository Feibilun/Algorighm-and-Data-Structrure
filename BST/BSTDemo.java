package BST;

public class BSTDemo {
    public static void main(String[] args) {
        int[] arr={7,3,10,12,5,1,9,2};
        BSTTree bst=new BSTTree();
        for(int n:arr){
            bst.add(new Node(n));
        }
        bst.inorder();

        bst.delNode(7);
        System.out.println("删除后");
        bst.inorder();
    }

}

class BSTTree{
    Node root;
    public void add(Node node){
        if(root==null){
            root=node;
        }else{
            root.add(node);
        }
    }

    public void inorder(){
        if(root!=null){
            root.inorder();
        }
    }

    //查找要删除节点
    public Node search(int value){
        if(root==null) return null;
        return root.search(value);
    }

    //查找父节点
    public Node searchParent(int value){
        if(root==null) return null;
        return root.searchParent(value);
    }

    //删除节点
    public void delNode(int value){
        if(root==null) return;
        Node targetNode=this.search(value);
        if(targetNode==null) return;
        //如果只有一个节点
        if(root.left==null && root.right==null){
            root=null;
            return;
        }

        Node parent=searchParent(value);
        //如果要删除的节点是叶子节点
        if(targetNode.left==null && targetNode.right==null){
            if(parent.left!=null && parent.left.val==value){
                parent.left=null;
            }else if(parent.right!=null && parent.right.val==value){
                parent.right=null;
            }
        }else if(targetNode.left!=null && targetNode.right!=null){ //删除的节点有两个子树
            int minValue=delRightTreeMin(targetNode.right);
            targetNode.val=minValue;
        }else{ //删除只有一颗子树的节点
            if(targetNode.left!=null){
                if(parent!=null){
                    if(parent.left.val==value){
                        parent.left=targetNode.left;
                    }else{
                        parent.right=targetNode.left;
                    }
                }else{
                    root=targetNode.left;
                }

            }else{
                if(parent!=null){
                    if(parent.left.val==value){
                        parent.left=targetNode.right;
                    }else{
                        parent.right=targetNode.right;
                    }
                }else{
                    root=targetNode.right;
                }
            }
        }
    }
    //返回以node为根节点的BST的最小节点的值并删除该节点
    public int delRightTreeMin(Node node){
        Node target=node;
        while(target.left!=null){
            target=target.left;
        }
        delNode(target.val);
        return target.val;
    }
}

class Node{
    int val;
    Node left;
    Node right;

    public Node(int val) {
        this.val = val;
    }

    //添加节点
    public void add(Node node){
        if(node==null) return;
        if(node.val<this.val){
            if(this.left==null){
                this.left=node;
                return;
            }
            this.left.add(node);
        }else{
            if(this.right==null){
                this.right=node;
                return;
            }
            this.right.add(node);
        }
    }

    public void inorder(){
        if(this.left!=null){
            this.left.inorder();
        }
        System.out.println(this.val);
        if(this.right!=null){
            this.right.inorder();
        }
    }

    //查找要删除的节点
    public Node search(int value){
        if(this.val==value){
            return this;
        }else if(value<this.val){
            if(this.left==null) return null;
            return this.left.search(value);
        }else{
            if(this.right==null) return null;
            return this.right.search(value);
        }
    }

    //查找要删除节点的父节点
    public Node searchParent(int value){
        if((this.left!=null && this.left.val==value) || (this.right!=null && this.right.val==value)){
            return this;
        }else{
            if(value<this.val && this.left!=null){
                return this.left.searchParent(value);
            }else if(value>=this.val && this.right!=null){
                return this.right.searchParent(value);
            }else{
                return null;
            }
        }
    }

}
