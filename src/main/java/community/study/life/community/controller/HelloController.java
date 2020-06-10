package community.study.life.community.controller;

import community.study.life.community.dto.PaginationDTO;
import community.study.life.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class HelloController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String hello(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search",required = false)String search)  {

        PaginationDTO paginationDTO = questionService.list(search,page,size);
        model.addAttribute("pagination", paginationDTO);
        return "index";
    }
}
