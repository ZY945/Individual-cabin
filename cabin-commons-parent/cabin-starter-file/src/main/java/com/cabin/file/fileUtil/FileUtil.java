package com.cabin.file.fileUtil;


import com.cabin.file.fileUtil.empty.FileAbsolutePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 伍六七
 * @date 2023/5/16 12:45
 */
public class FileUtil {

    public static final String JPEG = "FFD8FF";
    public static final String PNG = "89504E47";
    public static final String GIF = "47494638";


    public static String getMagicNumber(String filePath) throws IOException {
        File file = new File(filePath);
        return getMagicNumber(file);
    }

    public static String getMagicNumber(File file) throws IOException {
        try {
            byte[] bytes = new byte[20];
            FileInputStream input = new FileInputStream(file);
            input.read(bytes, 0, 20);
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                String hexString = Integer.toHexString(b & 0xFF);
                builder.append(hexString);
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取后缀
     *
     * @param url 文件路径
     * @return 后缀(类似.java)
     */
    public static String getFileSuffix(String url) {
        return url.contains(".") ? url.substring(url.lastIndexOf(".") + 1) : null;
    }

    /**
     * 获取文件名
     *
     * @param url 文件路径
     * @return 后缀(类似.java)
     */
    public static String getFileNameByWindows(String url) {
        return url.contains(".") ? url.substring(url.lastIndexOf("\\") + 1, url.indexOf(".")) : null;
    }

    /**
     * 获取后缀
     *
     * @param url 文件路径
     * @return 后缀(类似.java)
     */
    public static String getFileNameByLinux(String url) {
        return url.contains(".") ? url.substring(url.lastIndexOf("/"), url.indexOf(".")) : null;
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
