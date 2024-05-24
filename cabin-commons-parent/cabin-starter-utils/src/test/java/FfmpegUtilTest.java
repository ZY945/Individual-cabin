//import com.cabin.utils.fileUtil.FfmpegUtil;
//import org.junit.jupiter.api.Test;
//import ws.schild.jave.info.MultimediaInfo;
//
///**
// * @author 伍六七
// * @date 2023/6/13 0:01
// */
//class FfmpegUtilTest {
//
//    private static String localPath = "F:\\study\\code\\java\\Individual-cabin\\cabin-commons-parent\\cabin-starter-utils\\src\\main\\resources\\video\\old.mp4";
//    private static String path = "F:\\study\\code\\java\\Individual-cabin\\cabin-commons-parent\\cabin-starter-utils\\src\\main\\resources\\video\\";
//    private static String url = "https://mediastorage.cnr.cn/record/video/cnr/CNRCDP/2023/0612/d5ecb922642031686530173063137436/d5ecb922642031686530173063137436.mp4";
//
//    @Test
//    void getMultimediaInfo() {
//        MultimediaInfo multimediaInfo = FfmpegUtil.getMultimediaInfo(localPath);
//        System.out.println(multimediaInfo);
//    }
//
//    @Test
//    void getMultimediaInfoFromUrl() {
//        MultimediaInfo multimediaInfoFromUrl = FfmpegUtil.getMultimediaInfoFromUrl(url);
//        System.out.println(multimediaInfoFromUrl);
//    }
//
//    @Test
//    void formatAudio() {
//        boolean b = FfmpegUtil.formatAudio(url, path + "new.wav");
//        System.out.println(b);
//    }
//
//    @Test
//    void formatToMp4() {
//        boolean b = FfmpegUtil.formatToMp4(url, path + "new.mp4");
//        System.out.println(b);
//    }
//
//    @Test
//    void getTargetThumbnail() {
//        FfmpegUtil.getTargetThumbnail(localPath, path + "new.png");
//    }
//
//    @Test
//    void addSubtitle() {
//    }
//}