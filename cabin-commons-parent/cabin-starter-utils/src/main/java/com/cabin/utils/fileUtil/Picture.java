package com.cabin.utils.fileUtil;

import java.io.File;

/**
 * 图片命名修改
 *
 * @author 伍六七<br>
 * UID 477609259
 */
public class Picture {

    /**
     * 统一修改图片命名<br>
     * 目前问题：中英文名可以修改，但是副本好像不能及时修改
     *
     * @param path 路径
     * @author 伍六七<br>
     * @since 0.0.1
     */
    public static void rename(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        int i = 0;
        for (File file1 : files) {
            if (!file1.isDirectory()) {
                file1.renameTo(new File(path + "\\" + i + ".jpg"));
                i++;
            }
            System.out.println(file1.getName() + "执行成功");
        }
    }

}
