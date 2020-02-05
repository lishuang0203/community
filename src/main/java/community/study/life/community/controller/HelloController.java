package community.study.life.community.controller;

import community.study.life.community.dto.PaginationDTO;
import community.study.life.community.mapper.UserMapper;
import community.study.life.community.model.User;
import community.study.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller

public class HelloController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {


        PaginationDTO list = questionService.list(page,size);
        model.addAttribute("pagination", list);
        return "index";
    }
}