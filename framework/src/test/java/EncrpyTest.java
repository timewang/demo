import com.webhybird.framework.encryption.Coder;
import com.webhybird.framework.encryption.RSACoder;
import org.junit.Test;

import java.security.MessageDigest;
import java.util.Map;
import java.util.UUID;

/**
 * ***********************************************************
 *
 * @类名 ：EncrpyTest.java
 * @DESCRIPTION :
 * @AUTHOR : wangzhongfu
 * @DATE : 2016/9/9
 * ***********************************************************
 */
public class EncrpyTest {

    //16进制下数字到字符的映射数组
    private static String[] hexDigits = new String[]{"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    @Test
    public void encrpyByKey() throws Exception {

        Map<String, Object> key = RSACoder.initKey();

        String privateKeey = RSACoder.getPrivateKey(key);
        String publicKeey = RSACoder.getPublicKey(key);

        System.out.println("key：" + privateKeey);
        //公钥加密
        byte[] e = RSACoder.encryptByPublicKey("123456@qq.com".getBytes(),publicKeey);

        String s = bytesToString(e);

        System.out.println(s);
        //私钥解密
        byte[] e1 = RSACoder.decryptByPrivateKey(e,privateKeey);

        String s1 = bytesToString(e1);

        System.out.println(s1);


        //System.out.println(new String(e));
        System.out.println(new String(e1));


    }

    private String bytesToString(byte[] obj) {
        String ret = "";
        for (byte b : obj) {
            ret += String.valueOf(b);
        }
        return ret;
    }

    @Test
    public void genuuid(){
        System.out.println(UUID.randomUUID());
    }

    @Test
    public void md5Test() throws Exception {

        System.out.println(Coder.encryptMD5("admin".getBytes()));
        System.out.println(encodeByMD5("admin"));
        System.out.println("21232F297A57A5A743894A0E4A801FC3".toLowerCase().equals("21232f297a57a5a743894a0e4a801fc3"));
        //21232F297A57A5A743894A0E4A801FC3
        //21232f297a57a5a743894a0e4a801fc3
        //222222227755AAAA44884400448811CC
    }

    //对字符串进行MD5编码
    private static String encodeByMD5(String originstr)
    {
        if(originstr !=null)
        {
            try{
                //创建具有指定算法名称的信息摘要
                MessageDigest md = MessageDigest.getInstance("MD5");
                //使用指定的字节数组对摘要进行最后的更新，然后完成摘要计算
                byte[] results = md.digest(originstr.getBytes());
                //将得到的字节数组编程字符窜返回
                String resultString = byteArrayToHexString(results);
                return resultString.toUpperCase();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    //转换字节数组为十六进制字符串
    private static String byteArrayToHexString(byte[] b)
    {
        StringBuffer resultsb = new StringBuffer();
        int i=0;
        for(i=0;i<b.length;i++)
        {
            resultsb.append(byteToHexString(b[i]));
        }
        return resultsb.toString();
    }

    //将字节转化成十六进制的字符串
    private static String byteToHexString(byte b)
    {
        int n=b;
        if(n<0)
        {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n /16;
        return hexDigits[d1]+hexDigits[d2];
    }

}
