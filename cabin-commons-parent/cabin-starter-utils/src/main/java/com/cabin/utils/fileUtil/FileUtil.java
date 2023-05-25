package com.cabin.utils.fileUtil;


import com.cabin.utils.fileUtil.empty.FileAbsolutePath;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/16 12:45
 */
public class FileUtil {

    /**
     * 获取后缀
     *
     * @param url 文件路径
     * @return 后缀(类似.java)
     */
    public static String getFileSuffix(String url) {
        return url.contains(".") ? url.substring(url.indexOf(".")) : null;
    }

    /**
     * @param url    文件路径
     * @param suffix 后缀
     * @return 是否符合
     */
    public static Boolean isFileBySuffix(String url, String suffix) {
        return url.contains("." + suffix);
    }


    /**
     * 扫描目录下的文件
     *
     * @param basePath 文件路径
     * @return 扫描到的文件路径-绝对路径
     * @throws IOException
     */
    public static FileAbsolutePath scanFiles(String basePath) {
        Path dirPath = FileSystems.getDefault().getPath(basePath);
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.*");

        FileAbsolutePath fileAbsolutePath = new FileAbsolutePath();
        List<String> usingPath = new ArrayList<>();
        List<String> ignorePath = new ArrayList<>();
        final long[] usingTotal = {0L};
        final long[] ignoreTotal = {0L};
        //遍历文件树
        try {
            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (matcher.matches(path.getFileName())) {
                        usingPath.add(path.toFile().getPath());
                        usingTotal[0]++;
                    } else {
                        ignorePath.add(path.toFile().getPath());
                        ignoreTotal[0]++;
                    }
                    return super.visitFile(path, attrs);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("FileUtil.scanFiles(String basePath)文件分析出错\n" + e);
        }
        fileAbsolutePath.setBasePath(basePath);
        fileAbsolutePath.setIgnorePath(ignorePath);
        fileAbsolutePath.setUsingPath(usingPath);
        fileAbsolutePath.setUsingTotal(usingTotal[0]);
        fileAbsolutePath.setTotal(usingTotal[0] + ignoreTotal[0]);
        return fileAbsolutePath;
    }


    /**
     * 扫描目录下的指定后缀的文件
     *
     * @param basePath 文件路径
     * @param suffix   文件后缀
     * @return 扫描到的文件路径-绝对路径
     * @throws IOException
     */
    public static FileAbsolutePath scanFiles(String basePath, String suffix) {
        Path dirPath = FileSystems.getDefault().getPath(basePath);
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*." + suffix);

        FileAbsolutePath fileAbsolutePath = new FileAbsolutePath();
        List<String> usingPath = new ArrayList<>();
        List<String> ignorePath = new ArrayList<>();
        final long[] usingTotal = {0L};
        final long[] ignoreTotal = {0L};
        //遍历文件树
        try {
            Files.walkFileTree(dirPath, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (matcher.matches(path.getFileName())) {
                        usingPath.add(path.toFile().getPath());
                        usingTotal[0]++;
                    } else {
                        ignorePath.add(path.toFile().getPath());
                        ignoreTotal[0]++;
                    }
                    return super.visitFile(path, attrs);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("FileUtil.scanFiles(String basePath,String suffix)文件分析出错\n" + e);
        }
        fileAbsolutePath.setBasePath(basePath);
        fileAbsolutePath.setIgnorePath(ignorePath);
        fileAbsolutePath.setUsingPath(usingPath);
        fileAbsolutePath.setUsingTotal(usingTotal[0]);
        fileAbsolutePath.setTotal(usingTotal[0] + ignoreTotal[0]);
        return fileAbsolutePath;
    }


}
