package com.huang.learn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author localuser
 * create at 2022/9/5 14:57
 * @description
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 6510343353203473775L;

    private String searchValue;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private transient Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (this.params == null) {
            this.params = new HashMap<>(1);
        }
        return params;
    }
}
