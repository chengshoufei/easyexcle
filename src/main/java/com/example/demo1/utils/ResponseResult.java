package com.example.demo1.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo1.dictionaries.SexValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @责任人 xz
 * @创建时间 2022/3/28 20:14
 * @描述 统一出参实体
 */
@ApiModel(value = "ResponseResult", description = "统一出参类型")
public class ResponseResult<T> {
    public final static String responseCode = "200";
    public final static String responseMessage = "未获取到返回数据";
    @SexValue
    private String code;

    private String message;

    @ApiModelProperty("返参数据")
    private List<T> data;

    @ApiModelProperty("分页总条数")
    private long total;

    private String retFlag;

    private String retMsg;

    public ResponseResult() {
    }

    public T getFirstData(Class<T>... t) {
        if (null != data && data.size() > 0) {
            return data.get(0);
        }
        return null;
    }

    public T getFirstData(Class<T> t) {
        if (null != data && data.size() > 0) {
            return data.get(0);
        }
        return (T) new Object();
    }

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(String code, String message, List<T> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseResult(String code, String message, List<T> data, long total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }

    public static boolean feignClientIsTrue(ResponseResult responseResult) {
        if (ResponseResult.responseCode.equals(responseResult.getCode()) && !CollectionUtils.isEmpty(responseResult.getData())) {
            return true;
        } else {
            return false;
        }
    }

    public static <T> ResponseResult<T> success(String message) {
        return new ResponseResult("200", message);
    }

    public static <T> ResponseResult<T> success(T t) {
        return new ResponseResult("200", "success", Arrays.asList(t));
    }

    public static <T> ResponseResult<T> success(String message, T t) {
        return new ResponseResult("200", message, Arrays.asList(t));
    }

    public static <T> ResponseResult<T> success(List<T> data) {
        return new ResponseResult("200", "success", data, data.size());
    }

    public static <T> ResponseResult<T> success(String message, List<T> data) {
        return new ResponseResult("200", message, data);
    }

    public static <T> ResponseResult<T> success(IPage page) {

        return new ResponseResult("200", "success", page.getRecords(), page.getTotal());
    }

    public static <T> ResponseResult<T> success(String message, IPage page) {

        return new ResponseResult("200", message, page.getRecords(), page.getTotal());
    }

    public static <T> ResponseResult<T> fail(String message) {
        return new ResponseResult("-1", message);
    }

    public static <T> ResponseResult<T> fail(String code, String message) {
        return new ResponseResult(code, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getRetFlag() {
        return retFlag;
    }

    public void setRetFlag(String retFlag) {
        this.retFlag = retFlag;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }


}
