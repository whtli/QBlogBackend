package cn.li98.blog.utils;

import cn.li98.blog.model.entity.Blog;
import cn.li98.blog.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: whtli
 * @date: 2023/06/17
 * @description:
 */
@SpringBootTest
public class WordCountTest {
    @Autowired
    private BlogService blogService;

    @Test
    public void count2Test() {
        Blog blog = blogService.getById(18);
        String content = blog.getContent();
        String compare = "1118测试新增\n" +
                "+ 1118测试新增撒大苏打";
        int count1 = WordCountUtils.count(content);
        int count2 = WordCountUtils.count2(content);
        int count3 = compare.length();
        System.out.println("count1 = " + count1);
        System.out.println("count2 = " + count2);
        System.out.println("count3 = " + count3);
    }
}
