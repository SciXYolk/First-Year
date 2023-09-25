import java.util.Scanner;

public class Submission {
    static BST tree;
    static Scanner sc;

    public static class Node {
        int data;
        Node left;
        Node right;
        int count;

        public Node(int data) {
            this.data = data;
            this.count = 1;
            left = null;
            right = null;
        }
    }

    public static class BST {
        Node root;

        public BST() {
            this.root = null;
        }

        public void insert(Node node) {
            this.root = insertHelper(this.root, node);
        }

        private Node insertHelper(Node root, Node node) {
            if (root == null) {
                root = node;
                return root;
            }
            if (node.data == root.data) {
                root.count++;
            } else if (node.data < root.data) {
                root.left = insertHelper(root.left, node);
            } else {
                root.right = insertHelper(root.right, node);
            }
            return root;
        }

        public Node search(int data) {
            return searchHelp(root, data);
        }

        private Node searchHelp(Node root, int data) {
            if (root == null) {
                return null;
            } else if (root.data == data) {
                return root;
            } else if (root.data > data) {
                return searchHelp(root.left, data);
            } else {
                return searchHelp(root.right, data);
            }
        }

        public void remove(int data) {
            root = removeHelp(root, data);
        }

        private Node removeHelp(Node root, int data) {
            if (root == null) {
                return null;
            }
            if (data < root.data) {
                root.left = removeHelp(root.left, data);
            } else if (data > root.data) {
                root.right = removeHelp(root.right, data);
            } else {
                if (root.count > 1) {
                    root.count--;
                } else {
                    if (root.left != null) {
                        Node largestLeft = findMax(root.left);
                        root.data = largestLeft.data;
                        root.count = largestLeft.count;
                        largestLeft.count = 1; // Reset the count of the largest left node before removal
                        root.left = removeHelp(root.left, largestLeft.data);
                    } else if (root.right != null) {
                        Node smallestRight = findMin(root.right);
                        root.data = smallestRight.data;
                        root.count = smallestRight.count;
                        smallestRight.count = 1; // Reset the count of the smallest right node before removal
                        root.right = removeHelp(root.right, smallestRight.data);
                    } else {
                        root = null;
                    }
                }
            }
            return root;
        }

        public Node findMin() {
            return findMin(root);
        }

        private Node findMin(Node root) {
            if (root == null) {
                return null;
            }
            while (root.left != null) {
                root = root.left;
            }
            return root;
        }

        public Node findMax() {
            return findMax(root);
        }

        private Node findMax(Node root) {
            if (root == null) {
                return null;
            }
            while (root.right != null) {
                root = root.right;
            }
            return root;
        }

        public void printInOrder() {
            printInOrder(this.root);
        }

        private void printInOrder(Node root) {
            if (root != null) {
                printInOrder(root.left);
                System.out.println(root.data + "(" + root.count + ")");
                printInOrder(root.right);
            }
        }

        public void printPreOrder() {
            printPreOrder(root);
        }

        private void printPreOrder(Node root) {
            if (root != null) {
                System.out.println(root.data + "(" + root.count + ")");
                printPreOrder(root.left);
                printPreOrder(root.right);
            }
        }

        public void printPostOrder() {
            printPostOrder(root);
        }

        private void printPostOrder(Node root) {
            if (root != null) {
                printPostOrder(root.left);
                printPostOrder(root.right);
                System.out.println(root.data + "(" + root.count + ")");
            }
        }

    }
    public Submission(){
        tree = new BST();
        sc = new Scanner(System.in);
    }
    public static void main(String[] args) {
        new Submission();
        int choice = -1;
        while(choice != 0){
            choice = sc.nextInt();
            switch(choice){
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    int elementValue = sc.nextInt();
                    Node element = new Node(elementValue);
                    tree.insert(element);
                    break;
                case 2:
                    elementValue =sc.nextInt();
                    Node node = tree.search(elementValue);
                    if(node != null){
                        // this means we have found it
                        System.out.println(node.data + "(" + node.count + ")");
                    } else{
                        // we have not found it
                        System.out.println(elementValue + "(0)");
                    }
                    break;
                case 3:
                    Node max = tree.findMax();
                    if (max != null) {
                        System.out.println(max.data + "(" + max.count + ")");
                    } else {
                        System.out.println("0(0)");
                    }
                    break;
                case 4:
                    Node min = tree.findMin();
                    if (min != null) {
                        System.out.println(min.data + "(" + min.count + ")");
                    } else {
                        System.out.println("0(0)");
                    }
                    
                    break;
                case 5:
                    tree.printPreOrder();
                    break;
                case 6:
                    tree.printPostOrder();
                    break;
                case 7:
                    tree.printInOrder();
                    break;
                case 8:
                    elementValue = sc.nextInt();
                    tree.remove(elementValue);
                    break;
                default:
                    break;
            }
        }
        sc.close();
    }
}
