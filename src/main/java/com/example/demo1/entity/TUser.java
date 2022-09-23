package com.example.demo1.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author cheng
 * @since 2022-04-24
 */
@ApiModel(value = "TUser对象", description = "")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime dateofbirth;

    private String image;

    private String name;

    private String password;

    private String personal;

    private String phone;

    private String sex;

    private String username;

    private Integer cityId;

    private Integer districtId;

    private Integer provinceId;

    public LocalDateTime getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(LocalDateTime dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public String toString() {
        return "TUser{" +
                "dateofbirth=" + dateofbirth +
                ", image=" + image +
                ", name=" + name +
                ", password=" + password +
                ", personal=" + personal +
                ", phone=" + phone +
                ", sex=" + sex +
                ", username=" + username +
                ", cityId=" + cityId +
                ", districtId=" + districtId +
                ", provinceId=" + provinceId +
                "}";
    }
}
