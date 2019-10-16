package cn.itsource.gofishing.common.service.controller;

import cn.itsource.gofishing.common.service.util.VelocityUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class StaticPageController {

    @PostMapping("/page")
    public void getStaticPage(
            @RequestParam("templatePath") String templatePath,
            @RequestParam("targetPath") String targetPath,
            @RequestBody Object model){
        VelocityUtils.staticByTemplate(model,templatePath,targetPath);
    }
}
