package com.q.module;

import javax.swing.*;
import java.awt.*;

/**
 * @author anonymity-0
 * @date 2020/11/3 - 20:28
 */
public class MainManager extends JFrame  {
    private static Menu topMenu;
    private static FileTree fileTree;
    private static ToolBar ToolBar;
    private FileDisplay fileDisplay;
    public static FileTree getFileTree() {
        return fileTree;
    }
    public static void main(String[] args) throws Exception {
        //系统风格
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new MainManager();
    }

    public MainManager(){
        init();
    }

    private void init(){
        this.setTitle("文件管理器");
        this.setBounds(0,0,600,600);
        EventQueue.invokeLater(()->{
            fileDisplay =new FileDisplay();
            JScrollPane pane = new JScrollPane(fileDisplay);
            this.add(pane);
        });

        //初始化右侧显示框

        EventQueue.invokeLater(()->{
            fileTree = new FileTree(fileDisplay);
            fileTree.setPreferredSize(new Dimension(150, 70));
            this.add(fileTree,BorderLayout.WEST);
        });
        EventQueue.invokeLater(()->{
            topMenu=new Menu();
            this.setJMenuBar(topMenu);
            ToolBar = new ToolBar(fileDisplay);
            this.add(ToolBar,BorderLayout.NORTH);
            JScrollPane scrollPane =new JScrollPane(fileDisplay);
            this.add(scrollPane,BorderLayout.CENTER);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            this.setVisible(true);
        });

    }

}
