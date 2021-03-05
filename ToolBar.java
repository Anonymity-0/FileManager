package com.q.module;

import com.q.ModuleUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author anonymity-0
 * @date 2020/11/7 - 23:56
 */
public class ToolBar extends JPanel {
    private final FileDisplay display;
    private final JButton backBotton =new JButton("←");
    static JLabel path = new JLabel("       ");
    public static void main(String[] args) {

    }
    public ToolBar(FileDisplay fileDisplay) {
        display=fileDisplay;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        add(backBotton);
        add(backBotton);
        path.setSize(30,30);
        path.setOpaque(true);
        path.setBackground(Color.white);
        add(path);
        addListener();
    }

    private void addListener(){
        backBotton.addActionListener(e -> {
            String temp =path.getText();
            StringBuilder sb=new StringBuilder();
            String[] split = temp.split("/");
            for (int i = 0; i < split.length-1; i++) {
                 sb.append(split[i]+"/");
            }
            if (!"".equals(sb.toString())){
                path.setText(sb.toString());
                ModuleUtils.updateList(display,path.getText());
            }
            else{
                path.setText("");
                ModuleUtils.updateDefaultList(display);
            }
        });
    }
}
