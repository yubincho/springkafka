package com.example.chatspring.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
public class MainChatController {
    private static final String CHAT_ROOM_VIEW = "chattingRoom2";
    private static final String ERROR_VIEW = "error";
    private static final Set<String> VALID_IDS = Set.of("guest", "master", "loose");

    @GetMapping("/")
    public String index(){
        return CHAT_ROOM_VIEW;
    }

    @GetMapping("/{id}")
    public String chattingRoom(@PathVariable String id, HttpSession session, Model model){
        if (VALID_IDS.contains(id)) {
            model.addAttribute("name", id);
            return CHAT_ROOM_VIEW;
        } else {
            // 에러 페이지 또는 에러 처리 로직으로 리다이렉트
            return ERROR_VIEW;
        }
    }
}
