package com.q.module;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

/**
 * @author anonymity-0
 * @date 2020/11/14 - 19:52
 */
public class FileChoose extends JFileChooser {
    public void openFlie(){
        //设置默认显示为D盘
        setCurrentDirectory(new File("D:/"));
        //设置选择模式（只选文件、只选文件夹、文件和文件均可选）
        setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //是否允许多选
        setMultiSelectionEnabled(true);
        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        int value=showOpenDialog(this);
        if (value==APPROVE_OPTION){
            File f = this.getSelectedFile();
            try {
                Desktop.getDesktop().open(f);
            } catch (IOException | NullPointerException e) {
                System.err.println(e);
            }
        }
    }
}
