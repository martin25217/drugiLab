package analizator;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String data;
    private ArrayList<TreeNode> children;

    public TreeNode(){
        this.children = new ArrayList<TreeNode>();
    }

    public TreeNode(String data) {
        this.data = data;
        this.children = new ArrayList<TreeNode>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void traverseTree() {//ne valja nadi bolje
        System.out.println(getData());

        for (TreeNode child : getChildren()) {
            traverseTree();
        }
    }
}