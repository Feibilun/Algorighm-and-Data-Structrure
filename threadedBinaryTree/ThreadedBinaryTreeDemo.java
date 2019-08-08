package threadedBinaryTree;

public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root=new HeroNode(1,"Tom");
        HeroNode node2=new HeroNode(3,"Jack");
        HeroNode node3=new HeroNode(6,"Smith");
        HeroNode node4=new HeroNode(8,"Mary");
        HeroNode node5=new HeroNode(10,"King");
        HeroNode node6=new HeroNode(14,"Dim");

        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        ThreadedBinaryTree threadedBinaryTree=new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        threadedBinaryTree.threadedNodes(root);

        HeroNode leftNode=node5.getLeft();
        //System.out.println(leftNode);

        HeroNode rightNode=node5.getRight();
        //System.out.println(rightNode);

        threadedBinaryTree.threadedList();
    }
}


class ThreadedBinaryTree{
    private HeroNode root;

    private HeroNode pre=null;//进行线索化时始终保持为前一个节点

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    //进行中序线索化
    public void threadedNodes(HeroNode node){
        if(node==null) return;

        threadedNodes(node.getLeft());
        if(node.getLeft()==null){
            node.setLeft(pre);
            node.setLeftType(1);
        }
        if(pre!=null && pre.getRight()==null){
            pre.setRight(node);
            pre.setRightType(1);
        }
        pre=node;//!!!!!!!!!!!
        threadedNodes((node.getRight()));
    }


    //遍历线索化二叉树
    public void threadedList(){
        HeroNode node=root;
        while(node!=null){
            while(node.getLeftType()==0){
                node=node.getLeft();
            }
            System.out.println(node);
            while(node.getRightType()==1){
                node=node.getRight();
                System.out.println(node);
            }
            node=node.getRight();
        }
    }
    public void preOrder(){
        if(this.root!=null){
            this.root.preOrder();
        }
    }

    public void infixOrder(){
        if(this.root!=null){
            this.root.infixOrder();
        }
    }

    public void postOrder(){
        if(this.root!=null){
            this.root.postOrder();
        }
    }

    //前序遍历查找
    public HeroNode preOrderSearch(int no){
        if(root!=null){
            return root.preOrderSearch(no);
        }else{
            return null;
        }
    }

    //中序遍历查找
    public HeroNode infixOrderSearch(int no){
        if(root!=null){
            return root.infixOrderSearch(no);
        }else{
            return null;
        }
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no){
        if(root!=null){
            return root.postOrderSearch(no);
        }else{
            return null;
        }
    }

    //删除节点
    public void delNode(int no){
        if(root!=null){
            if(root.getNo()==no){
                root=null;
            }else{
                root.delNode(no);
            }
        }else{
            System.out.println("空树，无法删除");
        }
    }
}

class HeroNode{
    private int no;
    private String name;
    private HeroNode left;
    private HeroNode right;

    private int leftType;//=0表示指向左子树，=1表示指向前驱节点
    private int rightType;//=0表示指向右子树，=1表示指向后继节点

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    public String getName() {
        return name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public HeroNode getRight() {
        return right;
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left!=null){
            this.left.preOrder();
        }
        if(this.right!=null){
            this.right.preOrder();
        }
    }

    //中序遍历
    public void infixOrder(){
        if(this.left!=null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if(this.right!=null){
            this.right.infixOrder();
        }
    }

    //后序遍历
    public void postOrder(){
        if(this.left!=null){
            this.left.postOrder();
        }
        if(this.right!=null){
            this.right.postOrder();
        }
        System.out.println(this);
    }

    //前序遍历查找
    public HeroNode preOrderSearch(int no){
        System.out.println("进入前序遍历");
        if(this.no==no){
            return this;
        }
        HeroNode resNode=null;
        if(this.left!=null){
            resNode=this.left.preOrderSearch(no);
        }
        if(resNode!=null) return resNode;
        if(this.right!=null){
            resNode=this.right.preOrderSearch(no);
        }
        return resNode;
    }

    //中序遍历查找
    public HeroNode infixOrderSearch(int no){
        HeroNode resNode=null;
        if(this.left!=null){
            resNode=this.left.infixOrderSearch(no);
        }
        if(resNode!=null){
            return resNode;
        }
        System.out.println("进入中序遍历查找");
        if(this.no==no){
            return this;
        }
        if(this.right!=null){
            resNode=this.right.infixOrderSearch(no);
        }
        return resNode;
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no){
        HeroNode resNode=null;
        if(this.left!=null){
            resNode=this.left.postOrderSearch(no);
        }
        if(resNode!=null){
            return resNode;
        }
        if(this.right!=null){
            resNode=this.right.postOrderSearch(no);
        }
        if(resNode!=null){
            return resNode;
        }
        System.out.println("后序遍历查找开始");
        if(this.no==no){
            return this;
        }
        return null;
    }

    //递归删除节点
    public void delNode(int no){
        if(this.left!=null && this.left.no==no){
            this.left=null;
            return;
        }
        if(this.right!=null && this.right.no==no){
            this.right=null;
            return;
        }
        if(this.left!=null){
            this.left.delNode(no);
        }
        if(this.right!=null){
            this.right.delNode(no);
        }

    }
}