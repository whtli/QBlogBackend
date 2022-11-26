package cn.li98.blog.common;

/**
 * @author: whtli
 * @date: 2022/11/25
 * @description: 统计字符串中的字数（中英文）
 */
public class WordCount {
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
}
