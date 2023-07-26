package com.cabin.file.fileUtil;


import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Size;

import java.io.File;

/**
 * 图片命名修改
 *
 * @author 伍六七<br>
 * UID 477609259
 */
public class PictureUtil {

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

    public static Mat getMat(String inputImgPath) {
        //加载图片
        return opencv_imgcodecs.imread(inputImgPath);
    }

    public static void changeMat(String inputImgPath,
                                 String outputImgPath,
                                 Integer color,
                                 Boolean gaussianBlur,
                                 Boolean Canny) {
        //加载图片
        Mat image = getMat(inputImgPath);
        if (color != null) {
            image = changeColor(image, color);
        }
        if (gaussianBlur) {
            image = toBeGaussianBlur(image);
        }
        if (Canny) {
            image = toCanny(image);
        }
        opencv_imgcodecs.imwrite(outputImgPath, image);
    }

    public static Mat changeColor(Mat image, int color) {
        //加载图片

        Mat grayImage = new Mat();
        opencv_imgproc.cvtColor(image, grayImage, color);
        return grayImage;
    }

    public static Mat toBeGaussianBlur(Mat image) {
        Mat blurredImage = new Mat();
        opencv_imgproc.GaussianBlur(image, blurredImage, new Size(5, 5), 0);
        return blurredImage;
    }

    public static Mat toCanny(Mat image) {

        Mat cannyImage = new Mat();
        opencv_imgproc.Canny(image, cannyImage, 50, 150);
        return cannyImage;
    }
}
