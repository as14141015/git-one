package cn.itsource.gofishing.common.service.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.gofishing.common.service.util.FastDfsApiOpr;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class FileController {
    /**
     * 文件上传
     */
    @PostMapping("/file")
    public AjaxResult upload(MultipartFile file){
        try {
            byte[] content = file.getBytes();//获取文件内容
            String originalFilename = file.getOriginalFilename();//获取文件名
            int index = originalFilename.lastIndexOf(".");
            String extName = originalFilename.substring(index + 1);//根据下标获得文件拓展名
            String file_id = FastDfsApiOpr.upload(content,extName);
            return AjaxResult.me().setObject(file_id);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败！"+e.getMessage());
        }
    }

    /**
     * 文件删除
     */
    @DeleteMapping("/file")
    public AjaxResult delete(@RequestParam("fileId") String fileId){
        try {
            /*
                这里的fileId是一个/group1/M00/00/01/rBAEG12mMp6AOzL1AArBsjDcsz8758_big.jpg的字符串，
                我们需要通过方法拿到我们要拿到的内容
             */
            fileId = fileId.substring(1);
            int index = fileId.indexOf("/");
            String group = fileId.substring(0,index);
            String fileName = fileId.substring(index+1);
            FastDfsApiOpr.delete(group, fileName);
            return AjaxResult.me().setSuccess(true).setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("操作异常！！"+e.getMessage());
        }
    }
}
