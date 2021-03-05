package com.q.module;

import com.q.ModuleUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

/**
 * @author anonymity-0
 * @date 2020/11/7 - 19:29
 */
public class FileTree extends JPanel {
    private  JTree jTree;
    private final FileDisplay fileDisplay;
    private final int doubleClickCount =2;
    public FileTree(FileDisplay fileDisplay)  {
        this.fileDisplay=fileDisplay;
        initTree();
        JScrollPane treeView = new JScrollPane(jTree);
        treeView.setPreferredSize(new Dimension(140,450));
        treeView.getViewport().getView().setPreferredSize(treeView.getPreferredSize());
        add(treeView);
    }
    /** 初始化JTree */
    private void initTree(){
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("");
        jTree =new JTree(top);
        addListener();
        //获取系统的所有盘符
        jTree.setRootVisible(false);
        //setTreeIcon(jTree);
        File[] file =  File.listRoots();
          for(int i = 3; i < file.length; i++) {
              DefaultMutableTreeNode tempnode = new DefaultMutableTreeNode(file[i].getPath().substring(0,2));
              top.add(tempnode);
              addDir(tempnode,file[i]);
          }
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.setRootVisible(true);


    }
    private void addListener(){
        jTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 鼠标双击事件
                if (e.getSource() == jTree && e.getClickCount() == doubleClickCount) {
                    // 按照鼠标点击的坐标点获取路径
                    TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
                    if (selPath != null)// 双击空白处空指针异常
                    {
                        DefaultMutableTreeNode temp = (DefaultMutableTreeNode) jTree.getPathForLocation(e.getX(), e.getY()).getLastPathComponent();
                        String tempPath;
                        File file;
                        if (temp.isLeaf()){
                            tempPath =String.valueOf(getFilePath((DefaultMutableTreeNode) temp.getParent()));
                            file =new File(String.valueOf(getFilePath(temp)));
                        }
                        else {
                            tempPath=String.valueOf(getFilePath(temp));
                            file=new File(tempPath);
                        }
                        if (file.exists()){
                            ToolBar.path.setText(tempPath);
                            if (!file.isDirectory()){
                                try {
                                    Desktop.getDesktop().open(file);
                                } catch (IOException | NullPointerException exception) {
                                    System.err.println(exception);
                                }
                            }
                            else {
                                ModuleUtils.updateList(fileDisplay,tempPath);
                            }
                        }

                    }
                }
            }
        });


    }
    private StringBuilder getFilePath(DefaultMutableTreeNode node){
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jTree.getModel().getRoot();
        StringBuilder sb =new StringBuilder();
        while (root!=node){
            if (node.equals(jTree.getModel().getRoot())){
                continue;
            }
            else if (!node.isLeaf()){
                sb.insert(0,node.toString()+"/");
            }
            else {
                sb.insert(0,node.toString());
            }
            node= (DefaultMutableTreeNode) node.getParent();
        }
        return sb;
        }
    private DefaultMutableTreeNode addDir(DefaultMutableTreeNode top, File file){
        File[] file1 =file.listFiles();
            if(file.isDirectory()&&file1!=null){
               for(File f:file1){
                    DefaultMutableTreeNode t = new DefaultMutableTreeNode(f.getName());
                    if(!f.isHidden()&&f.isDirectory()) {
                        top.add(addDir(t,f));
                    } else {
                        top.add(t);
                    }
                }
            }else{
                top.add(new DefaultMutableTreeNode(file.getName()));
            }
        return top;
    }
}
