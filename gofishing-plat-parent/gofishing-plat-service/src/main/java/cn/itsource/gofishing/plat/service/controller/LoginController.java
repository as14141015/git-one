package cn.itsource.gofishing.plat.service.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.gofishing.plat.nterface.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录的Controller")
public class LoginController {
    /**
     * 登录接口
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public AjaxResult login(@RequestBody User user){
        if("admin".equals(user.getUsername())&&"admin".equals(user.getPassword())){
            return AjaxResult.me().setSuccess(true).setMessage("登录成功！").setObject(user);
        }
        return AjaxResult.me().setSuccess(false).setMessage("登录失败!");
    }
}