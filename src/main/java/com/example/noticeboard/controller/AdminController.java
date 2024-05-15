package com.example.noticeboard.controller;

import com.example.noticeboard.model.Notice;
import com.example.noticeboard.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private NoticeRepository noticeRepository;

    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping("/admin/notices")
    public String viewNotices(Model model) {
        List<Notice> notices = noticeRepository.findAll();
        model.addAttribute("notices", notices);
        return "admin/notices";
    }

    @GetMapping("/admin/notices/new")
    public String showNewNoticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "admin/new_notice";
    }

    @PostMapping("/admin/notices")
    public String saveNotice(@RequestParam("description") String description,
                             @RequestParam("file") MultipartFile file) throws IOException {
        Notice notice = new Notice();
        notice.setDescription(description);

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            notice.setFilePath(filePath.toString());
        }

        noticeRepository.save(notice);
        return "redirect:/admin/notices";
    }

    @GetMapping("/admin/notices/edit/{id}")
    public String showEditNoticeForm(@PathVariable("id") Long id, Model model) {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid notice Id:" + id));
        model.addAttribute("notice", notice);
        return "admin/edit_notice";
    }

    @PostMapping("/admin/notices/{id}")
    public String updateNotice(@PathVariable("id") Long id,
                               @RequestParam("description") String description,
                               @RequestParam("file") MultipartFile file) throws IOException {
        Notice notice = noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid notice Id:" + id));
        notice.setDescription(description);

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            notice.setFilePath(filePath.toString());
        }

        noticeRepository.save(notice);
        return "redirect:/admin/notices";
    }

    @GetMapping("/admin/notices/delete/{id}")
    public String deleteNotice(@PathVariable("id") Long id) {
        noticeRepository.deleteById(id);
        return "redirect:/admin/notices";
    }
}

