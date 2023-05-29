
import com.cabin.utils.API.GiteeUtil.GiteeAPI;
import com.cabin.utils.fileUtil.FileUtil;
import com.cabin.utils.fileUtil.empty.FileAbsolutePath;
import org.junit.jupiter.api.Test;

/**
 * @author 伍六七
 * @date 2023/5/17 14:00
 */
public class GiteeAPITest {
    private static String token = "b1e91d17fe23ac3c6e99c2b7218fc2ed";
    private static String owner = "dongfeng407";
    private static String repo = "blog";
    private static String sha = "master";
    private static String suffix = "java";
    private static String path = "README.md";

    @Test
    void getBranchesOnly() {
        System.out.println("获取仓库分支名称");
        System.out.println(GiteeAPI.getBranchesOnly(token, owner, repo));
    }

    @Test
    void getPathList() {
        System.out.println("获取所有路径");
        System.out.println(GiteeAPI.getPathList(token, owner, repo, sha, 1));
    }

    @Test
    void getFilePathList() {
        System.out.println("获取文件+路径");
        System.out.println(GiteeAPI.getFilePathList(token, owner, repo, sha, 1));
    }

    @Test
    void getFilePathListBySuffix() {
        System.out.println("获取指定后缀的文件+路径");
        System.out.println(GiteeAPI.getFilePathListBySuffix(token, owner, repo, sha, 1, suffix));
    }

    @Test
    void getCodeByPath() {
        System.out.println("获取指定文件的内容");
        System.out.println(GiteeAPI.getCodeByPath(token, owner, repo, sha, path));
    }

    /////////文件API////////////
    @Test
    void getFileByAbsolutePath1() {
        FileAbsolutePath fileAbsolutePath = FileUtil.scanFiles("F:\\study\\CodeX\\CodeX\\CodeX-user\\src\\main\\java");
        System.out.println(fileAbsolutePath);
    }

    @Test
    void getFileByAbsolutePathAndSuffix() {
        FileAbsolutePath fileAbsolutePath = FileUtil.scanFiles("F:\\study\\CodeX\\CodeX\\CodeX-user\\src\\main\\java", "java");
        System.out.println(fileAbsolutePath);
    }

}
