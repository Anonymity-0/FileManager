package com.q.module;

import com.q.FileOperateUtils;
import com.q.ModuleUtils;

import javax.swing.*;
import java.io.File;

/**
 * @author anonymity-0
 * @date 2020/11/12 - 15:17
 */
public class PopubMenu extends JPopupMenu{
    private final String filepath;
    private final FileDisplay fileDisplay;

    public PopubMenu(String path,FileDisplay display) {
        filepath =path;
        fileDisplay=display;
        //实现打开功能
        JMenuItem openFile = new JMenuItem("打开");
        this.add(openFile);
        addOpenListener(openFile);

        //实现刷新功能
        JMenuItem updateItem =new JMenuItem("刷新");
        this.add(updateItem);
        addUpdateListener(updateItem);

        //实现删除功能
        JMenuItem deleteItem = new JMenuItem("删除");
        this.add(deleteItem);
        addDeleteListener(deleteItem);

        //实现压缩功能
        JMenuItem compressItem =new JMenuItem("压缩");
        add(compressItem);
        addZipListener(compressItem);
        //实现解压功能
        String zipStr = ".zip";
        if (path.contains(zipStr)){
            JMenuItem unzipItem =new JMenuItem("解压");
            this.add(unzipItem);
            addUnZipListener(unzipItem);
        }
        //实现复制功能
        JMenuItem copyItem = new JMenuItem("复制");
        this.add(copyItem);
        addCopyListener(copyItem);
        //实现粘贴功能
        JMenuItem pasteItem = new JMenuItem("粘贴");
        this.add(pasteItem);
        addPasteListener(pasteItem);

       //加密
        JMenuItem encFileItem =new JMenuItem("加密");
        add(encFileItem);
        addEncFileListener(encFileItem);
        //解密
        JMenuItem decFileItem =new JMenuItem("解密");
        this.add(decFileItem);
        addDecFileListener(decFileItem);

        //新建文件夹
        JMenuItem creDirItem = new JMenuItem("新建文件夹");
        this.add(creDirItem);
        addCreatDirListener(creDirItem);

    }

    private void addEncFileListener(JMenuItem encFileItem) {
        encFileItem.addActionListener(e -> {
            try {
                FileOperateUtils.encFile(filepath,FileOperateUtils.getParentPath(filepath));
                ModuleUtils.updateList(fileDisplay,FileOperateUtils.getParentPath(filepath));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void addDecFileListener(JMenuItem decFileItem) {
        decFileItem.addActionListener(e->{
            try {
                FileOperateUtils.decFile(filepath,FileOperateUtils.getParentPath(filepath));
                ModuleUtils.updateList(fileDisplay,FileOperateUtils.getParentPath(filepath));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void addCreatDirListener(JMenuItem creatDirItem) {
        creatDirItem.addActionListener(e->{
            FileOperateUtils.createDir(filepath);
        });

    }

    /**
     * 为粘贴功能 添加监听器
     * @param pasteItem 粘贴按键
     */
    private void addPasteListener(JMenuItem pasteItem) {
        pasteItem.addActionListener(e -> {
            FileOperateUtils.pasteFile(filepath);
            ModuleUtils.updateList(fileDisplay, FileOperateUtils.getParentPath(filepath));
        });
    }

    /**
     * 为删除按钮添加监听器
     * @param deleteItem 删除按键
     */
    private void addDeleteListener(JMenuItem deleteItem){
        deleteItem.addActionListener(e -> {
            File f =new File(filepath);
            if (!f.isHidden()&&f.exists()&&f.isDirectory()) {
                FileOperateUtils.deleteDirectory(filepath);
            }
            else if (!f.isHidden()&&f.exists()){
                FileOperateUtils.deleteFile(filepath);
            }
            ModuleUtils.updateList(fileDisplay, FileOperateUtils.getParentPath(filepath));
        });
    }

    /**
     * 打开按键监听器
     * @param openItem 打开按键
     */
    private void addOpenListener(JMenuItem openItem){
        openItem.addActionListener(e -> FileOperateUtils.openFile(filepath));
    }

    /**
     * 添加刷新监听器
     * @param updateItem 刷新按键
     */
        private void addUpdateListener(JMenuItem updateItem){
        updateItem.addActionListener(e -> {
            File file = new File(filepath);
            if (file.exists()){
                ModuleUtils.updateList(fileDisplay, FileOperateUtils.getParentPath(filepath));
            }
        });
    }

    /**
     * 为压缩按钮添加监听事件
     * @param compressItem 压缩按键
     */
    private void addZipListener(JMenuItem compressItem){
        compressItem.addActionListener(e -> {
            File file =new File(filepath);
            if (file.exists()){
                FileOperateUtils.zip(filepath, FileOperateUtils.getParentPath(filepath)+ FileOperateUtils.getFileName(filepath)+".zip");
                ModuleUtils.updateList(fileDisplay, FileOperateUtils.getParentPath(filepath));
            }
        });
    }

    /**
     * 为解压按钮添加监听器
     * @param unzipItem 解压按键
     */
    private void addUnZipListener(JMenuItem unzipItem){
        unzipItem.addActionListener(e -> {
            File file =new File(filepath);
            if (file.exists()){
                FileOperateUtils.unzip(filepath,file.getParent()+"\\"+ FileOperateUtils.getFileName(filepath));
                ModuleUtils.updateList(fileDisplay, FileOperateUtils.getParentPath(filepath));
            }
        });
    }

    /**
     * 为复制功能添加监听器
     * @param copyItem 复制按键
     */
    private void addCopyListener(JMenuItem copyItem){
        copyItem.addActionListener(e -> FileOperateUtils.copyFile(filepath));
    }
}
