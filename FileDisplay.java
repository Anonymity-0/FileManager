package com.q.module;

import com.q.FileOperateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * @author anonymity-0
 * @date 2020/11/8 - 14:39
 */
public class FileDisplay extends JPanel {
    private final ArrayList<String> filename;
    private final StringBuilder generalPath =new StringBuilder();
    private final JList<Object> jList;
    private final FileDisplay fileDisplay;
    private final int doubleClickCount =2;
    public FileDisplay() {
        fileDisplay =this;
        filename= new ArrayList<>();
        jList= new JList<>();
        this.setSize(100, 20);
        this.setBackground(Color.white);
        updateDefaultList();
        addListener();
        this.add(jList);
    }
    public void updateList(String path){
        generalPath.delete(0,generalPath.length());
        generalPath.append(ToolBar.path.getText());
        setFilename(path);
        try {
            jList.setListData(filename.toArray());
        }catch (ArrayIndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }
    public void setFilename(String path){
        File file = new File(path);
        filename.clear();
        File[] files = file.listFiles();
        if (files != null) {
            for (File f:files){
                if (!f.isHidden()) {
                    filename.add(f.getName());
                }
            }
        }
    }
    private final String departStr = ":/";
    private void addListener(){
        //右键监听事件
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource()==jList&&e.getButton()==MouseEvent.BUTTON3){
                    JList tempList = (JList) e.getSource();
                    int index = tempList.getSelectedIndex();    //已选项的下标
                    if (index>=0){
                        String currPath = tempList.getModel().getElementAt(index).toString();
                        JPopupMenu popupMenu =new PopubMenu(generalPath +currPath,fileDisplay);
                        popupMenu.show(e.getComponent(),e.getX(),e.getY());
                    }
                }
            }
        });
        //当文件被双击时打开
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == jList && e.getClickCount() == doubleClickCount){
                    JList tempList = (JList) e.getSource();
                    int index = tempList.getSelectedIndex();    //已选项的下标
                    String currPath = tempList.getModel().getElementAt(index).toString();
                    if (!"".equals(currPath)){
                        if (currPath.contains(departStr)) {
                            generalPath.append(currPath);
                        } else {
                            generalPath.append(currPath).append("/");
                        }
                    }
                    File file = new File(String.valueOf(generalPath));
                     if (!file.isDirectory()){
                        FileOperateUtils.openFile(String.valueOf(generalPath));
                        generalPath.delete(0,generalPath.length());
                        generalPath.append(ToolBar.path.getText());
                        ToolBar.path.setText(String.valueOf(generalPath));
                    }
                    //是文件夹显示文件里面的内容
                    else {
                        try {
                            setFilename(String.valueOf(generalPath));
                            ToolBar.path.setText(String.valueOf(generalPath));
                            jList.setListData(filename.toArray());
                        }catch (ArrayIndexOutOfBoundsException ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void updateDefaultList() {
        File[] file =  File.listRoots();
        DefaultListModel<Object> model = new DefaultListModel<>();
        for (File f:file){
            model.addElement(f.getPath().charAt(0)+":/");
        }
        filename.clear();
        generalPath.delete(0,generalPath.length());
        jList.setModel(model);
    }
}
