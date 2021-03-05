package com.q;

import org.apache.commons.io.FileUtils;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;

/**
 * @author anonymity-0
 * @date 2020/11/12 - 14:38
 */
public class FileOperateUtils {
    /**
     * 删除文件
     * @param path 文件路径
     * @return  删除结果
     */
    public static boolean deleteFile(String path){
        File file = new File(path);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 删除文件夹和文件
     * @param dir 文件夹路径
     * @return  删除结果
     */
    public static boolean deleteDirectory(String dir) {
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                // 删除子文件
                if (file.isFile()) {
                    flag = FileOperateUtils.deleteFile(file.getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
                // 删除子目录
                else if (file.isDirectory()) {
                    flag = FileOperateUtils.deleteDirectory(file.getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除目录
        return dirFile.delete();
    }

    /**
     * 复制文件
     * @param path 文件路径
     */
    public static void copyFile(String path){
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        File file = new File(path);
            // 封装文本内容
        if (file.exists()) {
            Transferable trans = new StringSelection(path);
            // 把文本内容设置到系统剪贴板
            clipboard.setContents(trans, null);
        }
    }

    /**
     * 粘贴文件
     * @param path 粘贴到的目录
     */
    public static void pasteFile(String path) {
        // 获取系统剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        // 获取剪贴板中的内容
        Transferable trans = clipboard.getContents(null);
        if (trans != null) {
            // 判断剪贴板中的内容是否支持文本
            if (trans.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    // 获取剪贴板中的文本内容
                    String text = (String) trans.getTransferData(DataFlavor.stringFlavor);
                    File file = new File(text);
                    File file1 = new File(path);
                    if (file.exists()){
                        if (!file.isDirectory()){
                            FileUtils.copyFileToDirectory(file,file1);
                        }
                        else {
                            FileUtils.copyDirectoryToDirectory(file,file1);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public static void openFile(String path) {
        File file = new File(path);
        if (file.exists()&&!file.isDirectory()){
            try {
                // 启动已在本机桌面上注册的关联应用程序，打开文件
                Desktop.getDesktop().open(file);

            } catch (IOException | NullPointerException exception) { // 异常处理
                System.err.println(exception);
            }
        }
    }


    /**
     * 获取文件上一级目录
     * @param s 文件路径
     * @return  上一级目录
     */
    public static String getParentPath(String s){
        StringBuilder sb=new StringBuilder();
        String[] split = s.split("/");
        for (int i = 0; i < split.length-1; i++) {
            sb.append(split[i]).append("/");
        }
        return String.valueOf(sb);
    }
    public static String getFileName(String path){
        String filename = path.substring(path.lastIndexOf("/")+1);
        File file = new File(path);
        if (file.exists()&&!file.isDirectory()) {
            filename = filename.substring(0,filename.lastIndexOf("."));
        }
        return filename;
    }

    /**
     * 压缩文件
     * @param sourceFile 压缩文件地址
     * @param targetZip 压缩后文件地址
     */
    public static void zip(String sourceFile,String targetZip){
        File file = new File(sourceFile);
        Project prj = new Project();
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);
        //设置将要进行压缩的源文件File对象
        // 判断是目录还是文件
        if (file.isDirectory()) {
            fileSet.setDir(file);
        } else {
            fileSet.setFile(file);
        }
        Zip zip = new Zip();
        zip.setProject(prj);
        //设置生成的目标zip文件File对象
        zip.setDestFile(new File(targetZip));
        zip.setEncoding("gbk");
        zip.addFileset(fileSet);
        zip.execute();
    }

    /**
     * 解压zip
     * @param sourceZip zip路径
     * @param destDir   解压到的路径
     */
    public static void unzip(String sourceZip,String destDir){
        File file =new File(destDir);
        if (!file.exists()) {
            file.mkdir();
        }
        Project p = new Project();
        Expand e = new Expand();
        e.setProject(p);
        e.setSrc(new File(sourceZip));
        e.setOverwrite(false);
        e.setDest(new File(destDir));
        e.setEncoding("gbk");
        e.execute();
    }


    /**
     * 创建文件夹
     * @param destDirName 文件夹路径
     */
    public static void createDir(String destDirName) {
        File file = new File(destDirName);
        if (!file.isDirectory()){
            JOptionPane.showMessageDialog(null,"创建失败");
        }
        else {
            File dir =new File(destDirName+"/新建文件夹");
            if (dir.exists()) {
                JOptionPane.showMessageDialog(null,"创建失败");
            }
            else {
                dir.mkdir();
            }
        }
    }


    /**
     * 加密文件
     * @param src 源文件路径
     * @param enc   加密后文件路径
     * @throws Exception 抛出异常
     */
    public static void encFile(String src, String enc) throws Exception {
        File srcFile =new File(src);
        File encFile = new File(enc+"/加密"+srcFile.getName());
        if (!srcFile.exists()) {
            return;
        }
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos = new BufferedOutputStream(new
                FileOutputStream(encFile));

        int n;
        while((n=bis.read())!=-1){
            bos.write(n+1);
        }
        bis.close();
        bos.close();
    }

    /**
     * 解密文件
     * @param enc 要解密的文件路径
     * @param dec 解密后放置的文件路径
     * @throws Exception 抛出异常
     */
    public static void decFile(String enc, String dec) throws Exception {
        File encFile = new File(enc);
        File decFile = new File(dec+"/解密"+encFile.getName());
        if (!encFile.exists()) {
            return;
        }

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(encFile));
        BufferedOutputStream bos = new BufferedOutputStream(new
                FileOutputStream(decFile));
        int n;
        while((n=bis.read())!=-1){
            bos.write(n-1);
        }
        bis.close();
        bos.close();

    }
}
