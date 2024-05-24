package com.cabin.controller;

import com.cabin.common.util.file.UploadUtil;
import com.cabin.entity.Upload;
import com.cabin.entity.bo.Chunk;
import com.cabin.file.fileUtil.FileUtil;
import com.cabin.mapper.jpa.UploadMapper;
import com.cabin.utils.response.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("/file")
@Slf4j
public class UploadController {
    @Resource
    private UploadMapper uploadMapper;
    @Value("${upload.path}")
    private String saveFilePath;

    /**
     * 分片上传
     * 将一次请求分成多块,
     * 然后在服务器进行依次写入内容
     *
     * @param chunk 文件块
     *              每一个上传块都会包含如下分块信息：
     *              chunkNumber: 当前块的次序，第一个块是 1，注意不是从 0 开始的。
     *              totalChunks: 文件被分成块的总数。
     *              chunkSize: 分块大小，根据 totalSize 和这个值你就可以计算出总共的块数。注意最后一块的大小可能会比这个要大。
     *              currentChunkSize: 当前块的大小，实际大小。
     *              totalSize: 文件总大小。
     *              identifier: 这个就是每个文件的唯一标示(可以是md5)。
     *              filename: 文件名。
     *              relativePath: 文件夹上传的时候文件的相对路径属性。
     *              一个分块可以被上传多次，当然这肯定不是标准行为，但是在实际上传过程中是可能发生这种事情的，需要考虑
     */
    @PostMapping("/chunkUpload")
    public Result<String> uploadPost(Chunk chunk) {
        // 获取文件名
        String resourceName = chunk.getFilename();
        // 获取md5值
        String md5 = chunk.getIdentifier();

        // 第一步:判断是否已存在该文件
        Upload uploadByMd5 = uploadMapper.getUploadByMd5(md5);
        if (uploadByMd5 != null) {
            return Result.uploadFileExist("文件id" + uploadByMd5.getId());
        }
        // 获取文件后缀
        String fileExt = FileUtil.getFileSuffix(resourceName);
        // 先把原文件保存,命名是原文件,最后一块传输完在改名
        Path absolutePath = Paths.get("").toAbsolutePath().resolve(saveFilePath.substring(2) + "/" + fileExt);

        String savePath = absolutePath.toString();
        File file = new File(savePath, chunk.getFilename());
        if (!file.getParentFile().exists()) {
            //如果不存在，就创建一个这个路径的文件夹。
            file.getParentFile().mkdirs();
        }
        // 第二步:判断是否是第一块---新建文件还是写入数据
        if (chunk.getChunkNumber() == 1 && !file.exists()) {
            log.info("文件块信息");
            log.info(chunk.toString());
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return Result.uploadFileCancel(e.getMessage());
            }
        }
        log.info("第" + chunk.getChunkNumber() + "次传输,总共" + chunk.getTotalChunks() + "次");
        //进行写文件操作
        try (
                //将块文件写入文件中
                InputStream fos = chunk.getFile().getInputStream();
                RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            int len = -1;
            byte[] buffer = new byte[1024];
            raf.seek((chunk.getChunkNumber() - 1) * chunk.getChunkSize());
            while ((len = fos.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (chunk.getChunkNumber() == 1) {
                file.delete();
            }
            return Result.uploadFileFail(e.getMessage());
        }

        // 第二步:判断是否是最后一块,向数据库中保存记录
        if (chunk.getChunkNumber().equals(chunk.getTotalChunks())) {
            //TODO 保存文件名命名----时间+用户唯一标识
            Date date = new Date();
            log.info("文件原名:" + resourceName);
            long time = date.getTime();
            String newFileName = time + resourceName;
            log.info("重构后文件名" + newFileName);

            // 把已保存的原文件名文件改成新文件名
            String tempPath = savePath + "/" + resourceName;
            String finalPath = savePath + "/" + newFileName;
            try {
                Files.move(Paths.get(tempPath), Paths.get(finalPath));
            } catch (IOException e) {
                return Result.uploadFileCancel(e.getMessage());
            }

            //保存视频url路径地址
            //调用数据库接口插入数据库方法save，把视频原名，视频路径，视频的唯一标识码传进去存到数据库内
            Upload upload = new Upload();
            upload.setPath(finalPath);
            upload.setResourceName(resourceName);
            upload.setFileName(newFileName);
            upload.setCreatTime(new Date());
            upload.setMd5(chunk.getIdentifier());
            upload.setResourceType(fileExt);
            upload.setLastVisitTime(new Date());
            uploadMapper.save(upload);
            return Result.uploadFileSuccess("最后一片上传成功");
        } else {
            return Result.uploadFileInProgress("正在上传");
        }
    }

    /**
     * 不分片上传
     *
     * @param file
     * @param SavePath
     * @return
     * @throws IllegalStateException
     */
    //二级地址
    @PostMapping(value = "/uploadVideo3")
    @ResponseBody
    public Result<String> savaVideoTest(@RequestParam("file") MultipartFile file, @RequestParam String SavePath)
    //throws IllegalStateException写在方法的前面是可以抛出异常状态的，如果有错误会把错误信息发出来对应下面的try和catch
            throws Exception {
        Path absolutePath = Paths.get("").toAbsolutePath().resolve(saveFilePath.substring(2) + "/videos");
        SavePath = absolutePath.toString();
        if (file == null) {
            throw new FileNotFoundException();
        }
        try {
            log.info("保存开始！");
            log.info("文件保存路径" + SavePath);
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                throw new FileUploadException();
            }
            String md5 = UploadUtil.getMd5(file);
            Upload existsed = uploadMapper.getUploadByMd5(md5);
            if (existsed != null) {
                throw new FileAlreadyExistsException(originalFilename + "已经存在,id为:" + existsed.getId());
            }
            //获取文件后缀
            String fileExt = FileUtil.getFileSuffix(originalFilename);
            //TODO 保存文件名命名----时间+用户唯一标识
            Date date = new Date();
            long time = date.getTime();
            String newFileName = time + originalFilename + "." + fileExt;
            log.info("重构后文件名" + newFileName);
            //保存视频的原始名字
            String videoNameText = file.getOriginalFilename();
            log.info("视频原名:" + videoNameText);
            //保存视频url路径地址
            String videoUrl = SavePath + "/" + newFileName;
            //调用数据库接口插入数据库方法save，把视频原名，视频路径，视频的唯一标识码传进去存到数据库内
            //判断SavePath这个路径也就是需要保存视频的文件夹是否存在
            File filepath = new File(SavePath, file.getOriginalFilename());
            if (!filepath.getParentFile().exists()) {
                //如果不存在，就创建一个这个路径的文件夹。
                filepath.getParentFile().mkdirs();
            }
            //保存视频：把视频按照前端传来的地址保存进去，还有视频的名字用唯一标识符显示，需要其他的名字可改这
            File fileSave = new File(SavePath, newFileName);
            //下载视频到文件夹中
            file.transferTo(fileSave);
            //到这里全部保存好了，把整个map集合返给前端
            Upload upload = new Upload();
            upload.setPath(videoUrl);
            upload.setCreatTime(new Date());
            upload.setLastVisitTime(new Date());
            uploadMapper.save(upload);
            log.info("记录保存成功！");
            return Result.uploadFileSuccess("文件上传成功");

        } catch (Exception e) {
            //在命令行打印异常信息在程序中出错的位置及原因
            e.printStackTrace();
            //返回有关异常的详细描述性消息。
            e.getMessage();
            //这时候错误了，map里面就添加了错误的状态码400并返回给前端看
            return Result.uploadFileCancel(e.getMessage());

        }
    }
}

