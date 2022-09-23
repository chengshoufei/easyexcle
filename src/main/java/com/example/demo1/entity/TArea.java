package com.example.demo1.entity;

import com.example.demo1.dictionaries.SexValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author cheng
 * @since 2022-08-17
 */
@ApiModel(value = "TArea对象", description = "")
public class TArea implements Serializable {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("fuid")
    private Integer pid;
    @SexValue
    @ApiModelProperty("mingzi")
    private String name;

    private List<TArea> chilend;

    public List<TArea> getChilend() {
        return chilend;
    }

    public void setChilend(List<TArea> chilend) {
        this.chilend = chilend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
