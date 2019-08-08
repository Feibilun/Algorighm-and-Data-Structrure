package AVL;

public class AVLTreeDemo {
    public static void main(String[] args) {
        //int[] arr={4,3,6,5,7,8};
        //int[] arr={10,12,8,9,7,6};
        int[] arr={10,11,7,6,8,9};
        AVLTree avl=new AVLTree();
        for(int i=0;i<arr.length;i++){
            avl.add(new Node(arr[i]));
        }
        avl.inorder();

        //System.out.println("初始情况");
        System.out.println("树的高度："+avl.getRoot().height());
        System.out.println("树的左子树高度："+avl.getRoot().leftheight());
        System.out.println("树的右子树高度："+avl.getRoot().rightheight());
        System.out.println("当前根节点："+avl.getRoot().val);
    }
}

class AVLTree{
    Node root;
    public Node getRoot(){
        return root;
    }
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

    //返回以该节点为根节点树的高度
    public int height(){
        return 1+Math.max(left==null?0:left.height(),right==null?0:right.height());
    }
    //左子树的高度
    public int leftheight(){
        if(left==null) return 0;
        return left.height();
    }
    //右子树的高度
    public int rightheight(){
        if(right==null) return 0;
        return right.height();
    }

    //左旋转
    public void leftRotate(){
        //创建一个新节点，节点值为当前节点值
        Node newNode=new Node(val);
        //新节点的左子树为当前节点的左子树
        newNode.left=left;
        //新节点的右子树为当前节点右子树的左子树
        newNode.right=right.left;
        //当前节点的值换为右子节点的值
        val=right.val;
        //当前节点的右子树为右子树的右子树
        right=right.right;
        //当前节点的左子树为新节点
        left=newNode;
    }

    //右旋转
    public void rightRotate(){
        Node newNode=new Node(val);
        newNode.right=right;
        newNode.left=left.right;
        val=left.val;
        left=left.left;
        right=newNode;
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

        if(rightheight()-leftheight()>1){
            if(right!=null && right.leftheight()>right.rightheight()){
                right.rightRotate();
            }
            leftRotate();
            return;//必须结束了！
        }

        if(leftheight()-rightheight()>1){
            //如果左子节点的右子树高度大于左子节点的左子树高度，就要先对左子节点进行左旋转
            if(left!=null && left.rightheight()>left.leftheight()){
                left.leftRotate();
            }

            rightRotate();
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