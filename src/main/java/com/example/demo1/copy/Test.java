package com.example.demo1.copy;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Test
 * @Date 2022/4/20 21:33
 * @Author chengshoufei
 * @Description TODO
 */
public class Test {

    public static void main(String[] args) {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId("chengs");
        user.setName("zhanga");
        userList.add(user);
        List<Usera> copy = CopyUtils.copy(userList, Usera.class);
        System.err.println(JSON.toJSONString(copy));

    }

}
