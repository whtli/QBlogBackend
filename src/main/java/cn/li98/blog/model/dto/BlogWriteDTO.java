package cn.li98.blog.model.dto;

import cn.li98.blog.model.entity.Blog;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: whtli
 * @date: 2022/11/29
 * @description:
 */
@NoArgsConstructor
@Data
public class BlogWriteDTO {
    private Blog blog;
    private List<Object> tags;
}
