package com.example.noticeboard.controller;

import com.example.noticeboard.model.Notice;
import com.example.noticeboard.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping
    public String viewHomePage(Model model) {
        List<Notice> notices = noticeRepository.findAll();
        model.addAttribute("notices", notices);
        return "index";
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public Resource downloadFile(@PathVariable Long id) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid notice Id:" + id));
        Path path = Paths.get(notice.getFilePath());
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}

