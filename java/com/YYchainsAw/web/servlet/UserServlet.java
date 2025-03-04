package com.YYchainsAw.web.servlet;

import com.YYchainsAw.pojo.Brand;
import com.YYchainsAw.pojo.User;
import com.YYchainsAw.service.UserService;
import com.YYchainsAw.service.impl.UserServiceImpl;
import com.YYchainsAw.util.CheckCodeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private Random random = new Random();

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }
        User user = JSON.parseObject(json.toString(), User.class);
        String account = String.valueOf(user.getAccount());
        String password = user.getPassword();

        User login = userService.login(account, password);
        response.setContentType("application/json;charset=utf-8");
        if (login != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", login);

            response.getWriter().write("success");
        }else{
            response.getWriter().write("fail");
        }
    }

    public void checkCodeServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg"); // 设置图片类型
        ServletOutputStream os = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100, 50, os, 4);
        request.getSession().setAttribute("checkCode", checkCode); // 存储验证码到session
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();

        String line = reader.readLine();

        JSONObject jsonObject = JSON.parseObject(line);

        String username = String.valueOf(jsonObject.getJSONArray("username"));
        String password = String.valueOf(jsonObject.getJSONArray("password"));
        String checkCode = String.valueOf(jsonObject.getJSONArray("checkCode"));

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);

        HttpSession session = request.getSession();

        response.setContentType("application/json;charset=utf-8");
        if(session.getAttribute("checkCode") == checkCode){
            char[] numbers = new char[11];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = (char)(48+i);
            }

            String account = new String();

            account += random.nextInt(9)+1;

            for (int i = 0; i < 9; i++) {
                account += numbers[random.nextInt(10)];
            }

            user.setAccount(account);

            userService.register(user);

            request.setAttribute("register_msg","注册成功，请登录");
            response.getWriter().write("success");
        }else {
            request.setAttribute("register_msg","验证码错误！");
            response.getWriter().write("fail");
        }


    }
}
