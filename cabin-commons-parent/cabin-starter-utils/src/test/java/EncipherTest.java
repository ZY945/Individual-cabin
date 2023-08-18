import com.cabin.utils.encipherUtil.reversible.disSymmetric.RSAEncipher;
import com.cabin.utils.encipherUtil.reversible.symmetrical.AESEncipher;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.KeyPair;
import java.util.Random;


/**
 * @author 伍六七
 * @date 2023/6/20 13:10
 */
public class EncipherTest {

    ////////////////////////disSymmetrical////////////////////////

    /**
     *
     */
    @Test
    void RSA() {
        KeyPair keyPair = RSAEncipher.generateKeyPair();
        String data = "123";
        String s = RSAEncipher.encryptedByRSA(data, keyPair);
        System.out.println(s);
        String s1 = RSAEncipher.decryptedByRSA(s, keyPair);
        System.out.println(s1);
    }

    ////////////////////////symmetrical////////////////////////
    @Test
    void AES_ECB() {
        Key secretKey = AESEncipher.generateKeyPair();
        String data = "123";
        String s = AESEncipher.encryptedByECB(data, secretKey);
        System.out.println(s);
        String s1 = AESEncipher.decryptedByECB(s, secretKey);
        System.out.println(s1);
    }

    @Test
    void AES_CBC() {
        Key secretKey = AESEncipher.generateKeyPair();
        IvParameterSpec initVector = AESEncipher.initVector();


        //生成测试数
        Random random = new Random();
        long l = random.nextLong((long) Math.pow(100, 200));
        String data = "" + l;
        //生成测试数

        String s = AESEncipher.encryptedByCBC(data, secretKey, initVector);
        System.out.println(s);
        String s1 = AESEncipher.decryptedByCBC(s, secretKey, initVector);
        System.out.println(s1);
    }
}
