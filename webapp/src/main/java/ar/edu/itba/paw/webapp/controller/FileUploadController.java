package ar.edu.itba.paw.webapp.controller;
// FileUploadController.java

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class FileUploadController {

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select an image to upload.");
            return "views/addAssetView";
        }

        byte[] fileByteArray;

        try {
            fileByteArray = file.getBytes();
        } catch (Exception e) {
            //
        }

        model.addAttribute("message", "Image uploaded successfully.");
        return "views/addAssetView";
    }
}
