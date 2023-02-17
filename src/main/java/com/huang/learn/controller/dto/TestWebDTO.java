package com.huang.learn.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author localuser
 * create at 2022/12/2 18:03
 * @description test dto 对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestWebDTO {

    @NotNull(message = "{can.not.be.null}")
    private Integer id;

    @Length(min = 1, max = 10, message = "{string.length.limit}")
    private String keyWord;
}
