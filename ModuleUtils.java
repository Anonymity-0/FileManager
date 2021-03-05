package com.q;

import com.q.module.FileDisplay;

/**
 * @author anonymity-0
 * @date 2020/11/12 - 14:54
 */
public class ModuleUtils {
    public static void updateList(FileDisplay display,String path){
        display.updateList(path);
    }
    public static void updateDefaultList(FileDisplay display){
        display.updateDefaultList();
    }
}
