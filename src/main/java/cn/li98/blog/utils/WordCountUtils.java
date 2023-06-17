package cn.li98.blog.utils;

/**
 * @author: whtli
 * @date: 2022/11/25
 * @description: 统计字符串中的字数（中英文）
 */
public class WordCountUtils {
    public static int count(String content) {
        if (content == null) {
            return 0;
        }
        String englishString = content.replaceAll("[\u4e00-\u9fa5]", "");
        String[] englishWords = englishString.split("[\\p{P}\\p{S}\\p{Z}\\s]+");
        int chineseWordCount = content.length() - englishString.length();
        int otherWordCount = englishWords.length;
        if (englishWords.length > 0 && englishWords[0].length() < 1) {
            otherWordCount--;
        }
        if (englishWords.length > 1 && englishWords[englishWords.length - 1].length() < 1) {
            otherWordCount--;
        }
        return chineseWordCount + otherWordCount;
    }
    /**
     * @auther: whtli
     * @date: 2023/6/17
     * @param content 内容
     * @description: 统计字符串中的字数（中英文）优化count方法，减少内存开销
     */
    public static int count2(String content) {
        if (content == null) {
            return 0;
        }
        int chineseWordCount = 0;
        int otherWordCount = 0;
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (c >= 0x4e00 && c <= 0x9fa5) {
                chineseWordCount++;
            } else if (c >= 0x0000 && c <= 0x00ff) {
                otherWordCount++;
            }
        }
        return chineseWordCount + otherWordCount;
    }
}
