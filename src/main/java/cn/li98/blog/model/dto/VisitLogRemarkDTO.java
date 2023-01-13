package cn.li98.blog.model.dto;

import lombok.*;

/**
 * @author: whtli
 * @date: 2023/01/10
 * @description: 访问日志备注
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class VisitLogRemarkDTO {
    /**
     * 访问内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;
}
