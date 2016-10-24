import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangzhongfu on 2015/5/11.
 */
public class Test {

    @org.junit.Test
    public void test(){
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }

    @org.junit.Test
    public void enumToStringTest(){
        System.out.println(MessageTypeEnum.MAIL);
        System.out.println(MessageTypeEnum.MAIL.toString());
        System.out.println(MessageTypeEnum.MAIL.name());
    }
    @org.junit.Test
    public void test2(){
        Date date = new Date();
        Date date1 = date;
        Date date2 = new Date();
        System.out.println(date == date1);
        System.out.println(date.equals(date1));
        System.out.println(date == date2);
        System.out.println(date.equals(date2));
    }
    @org.junit.Test
    public void test3(){
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        String[] b = list.toArray(new String[list.size()]);
        for(String s:b){
            System.out.println(s);
        }
    }

    @org.junit.Test
    public void fileOutPut() throws IOException {
        String source = "赵 钱 孙 李 周 吴 郑 王\n" +
                "\n" +
                "冯 陈 楮 卫 蒋 沈 韩 杨\n" +
                "\n" +
                "朱 秦 尤 许 何 吕 施 张\n" +
                "\n" +
                "孔 曹 严 华 金 魏 陶 姜\n" +
                "\n" +
                "戚 谢 邹 喻 柏 水 窦 章\n" +
                "\n" +
                "云 苏 潘 葛 奚 范 彭 郎\n" +
                "\n" +
                "鲁 韦 昌 马 苗 凤 花 方\n" +
                "\n" +
                "俞 任 袁 柳 酆 鲍 史 唐\n" +
                "\n" +
                "费 廉 岑 薛 雷 贺 倪 汤\n" +
                "\n" +
                "滕 殷 罗 毕 郝 邬 安 常\n" +
                "\n" +
                "乐 于 时 傅 皮 卞 齐 康\n" +
                "\n" +
                "伍 余 元 卜 顾 孟 平 黄\n" +
                "\n" +
                "和 穆 萧 尹 姚 邵 湛 汪\n" +
                "\n" +
                "祁 毛 禹 狄 米 贝 明 臧\n" +
                "\n" +
                "计 伏 成 戴 谈 宋 茅 庞\n" +
                "\n" +
                "熊 纪 舒 屈 项 祝 董 梁\n" +
                "\n" +
                "杜 阮 蓝 闽 席 季 麻 强\n" +
                "\n" +
                "贾 路 娄 危 江 童 颜 郭\n" +
                "\n" +
                "梅 盛 林 刁 锺 徐 丘 骆\n" +
                "\n" +
                "高 夏 蔡 田 樊 胡 凌 霍\n" +
                "\n" +
                "虞 万 支 柯 昝 管 卢 莫\n" +
                "\n" +
                "经 房 裘 缪 干 解 应 宗\n" +
                "\n" +
                "丁 宣 贲 邓 郁 单 杭 洪\n" +
                "\n" +
                "包 诸 左 石 崔 吉 钮 龚\n" +
                "\n" +
                "程 嵇 邢 滑 裴 陆 荣 翁\n" +
                "\n" +
                "荀 羊 於 惠 甄 麹 家 封\n" +
                "\n" +
                "芮 羿 储 靳 汲 邴 糜 松\n" +
                "\n" +
                "井 段 富 巫 乌 焦 巴 弓\n" +
                "\n" +
                "牧 隗 山 谷 车 侯 宓 蓬\n" +
                "\n" +
                "全 郗 班 仰 秋 仲 伊 宫\n" +
                "\n" +
                "宁 仇 栾 暴 甘 斜 厉 戎\n" +
                "\n" +
                "祖 武 符 刘 景 詹 束 龙\n" +
                "\n" +
                "叶 幸 司 韶 郜 黎 蓟 薄\n" +
                "\n" +
                "印 宿 白 怀 蒲 邰 从 鄂\n" +
                "\n" +
                "索 咸 籍 赖 卓 蔺 屠 蒙\n" +
                "\n" +
                "池 乔 阴 郁 胥 能 苍 双\n" +
                "\n" +
                "闻 莘 党 翟 谭 贡 劳 逄\n" +
                "\n" +
                "姬 申 扶 堵 冉 宰 郦 雍\n" +
                "\n" +
                "郤 璩 桑 桂 濮 牛 寿 通\n" +
                "\n" +
                "边 扈 燕 冀 郏 浦 尚 农\n" +
                "\n" +
                "温 别 庄 晏 柴 瞿 阎 充\n" +
                "\n" +
                "慕 连 茹 习 宦 艾 鱼 容\n" +
                "\n" +
                "向 古 易 慎 戈 廖 庾 终\n" +
                "\n" +
                "暨 居 衡 步 都 耿 满 弘\n" +
                "\n" +
                "匡 国 文 寇 广 禄 阙 东\n" +
                "\n" +
                "欧 殳 沃 利 蔚 越 夔 隆\n" +
                "\n" +
                "师 巩 厍 聂 晁 勾 敖 融\n" +
                "\n" +
                "冷 訾 辛 阚 那 简 饶 空\n" +
                "\n" +
                "曾 毋 沙 乜 养 鞠 须 丰\n" +
                "\n" +
                "巢 关 蒯 相 查 后 荆 红\n" +
                "\n" +
                "游 竺 权 逑 盖 益 桓 公\n" +
                "\n" +
                "万俟 司马 上官 欧阳\n" +
                "\n" +
                "夏侯 诸葛 闻人 东方\n" +
                "\n" +
                "赫连 皇甫 尉迟 公羊\n" +
                "\n" +
                "澹台 公冶 宗政 濮阳\n" +
                "\n" +
                "淳于 单于 太叔 申屠\n" +
                "\n" +
                "公孙 仲孙 轩辕 令狐\n" +
                "\n" +
                "锺离 宇文 长孙 慕容\n" +
                "\n" +
                "鲜于 闾丘 司徒 司空\n" +
                "\n" +
                "丌官 司寇 仉 督 子车\n" +
                "\n" +
                "颛孙 端木 巫马 公西\n" +
                "\n" +
                "漆雕 乐正 壤驷 公良\n" +
                "\n" +
                "拓拔 夹谷 宰父 谷梁\n" +
                "\n" +
                "晋 楚 阎 法 汝 鄢 涂 钦\n" +
                "\n" +
                "段干 百里 东郭 南门\n" +
                "\n" +
                "呼延 归 海 羊舌 微生\n" +
                "\n" +
                "岳 帅 缑 亢 况 后 有 琴\n" +
                "\n" +
                "梁丘 左丘 东门 西门\n" +
                "\n" +
                "商 牟 佘 佴 伯 赏 南宫\n" +
                "\n" +
                "墨 哈 谯 笪 年 爱 阳 佟";

        String[] strings = source.split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for(String s : strings){
            stringBuilder.append( s + "\n");
            System.out.println(s);
        }

        byte[] buff = stringBuilder.toString().getBytes();

        FileOutputStream out=new FileOutputStream("C:\\Users\\IBM_ADMIN\\Desktop\\TEMP\\out.dic");
        out.write(buff,0,buff.length);

    }

}
