package com.q.module;

import javax.swing.*;

/**
 * @author anonymity-0
 * @date 2020/11/8 - 22:21
 */
public class Menu extends JMenuBar {
    private final FileChoose fileChoose= new FileChoose();
    public Menu() {
        JMenu file = new JMenu("文件");
        JMenu help = new JMenu("帮助");

        //文件菜单
        this.add(file);
        JMenuItem openFile = new JMenuItem("打开");
        JMenuItem exitCmd = new JMenuItem("退出");
        file.add(openFile);
        file.add(exitCmd);

        //帮助菜单
        this.add(help);
        JMenuItem about = new JMenuItem("关于");
        JMenuItem helpManual = new JMenuItem("打开帮助");
        help.add(about);
        help.add(helpManual);

        //菜单事件响应
        //退出
        exitCmd.addActionListener(e -> System.exit(0));
        //打开文件
        openFile.addActionListener(e -> fileChoose.openFlie());

        about.addActionListener(e -> JOptionPane.showMessageDialog(null,"一个简单的文件管理器"));
    }
}


