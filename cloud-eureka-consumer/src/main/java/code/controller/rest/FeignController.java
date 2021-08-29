package code.controller.rest;

import code.feign.GatewayFeignClient;
import code.feign.RemoteService;
import code.feign.RemoteUploadService;
import code.feign.SingleTimeOutService;
import code.suport.InMemoryMultipartFile;
import lombok.AllArgsConstructor;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

/**
 * feign 方式demo
 * @author ccy
 */

@RestController
@AllArgsConstructor
public class FeignController {

    private RemoteService remoteService;
    private SingleTimeOutService singleTimeOutService;
    private RemoteUploadService remoteUploadService;
    private GatewayFeignClient gatewayFeignClient;

    /**
     * Feign 方式
     * http://blog.didispace.com/spring-cloud-starter-dalston-2-3/
     */
    @GetMapping("/hello1")
    public String hello1(@RequestParam String name) {
        return remoteService.hello(name);
    }

    /**
     * upload文件
     * http://blog.didispace.com/spring-cloud-starter-dalston-2-4/
     */
    @GetMapping("/upload")
    public String upload() {

        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:upload.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //fileName 要与方法中的变量名相同  下面远程调用的方法中的@RequestPart(value = "file") 这个value值
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file",
                MediaType.TEXT_PLAIN_VALUE, true, file.getName());

        try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        MultipartFile multi = new CommonsMultipartFile(fileItem);

        return remoteUploadService.handleFileUpload(multi);
    }

//    /**
//     * 直接接受 ResponseEntity<Resource>不行改为接受 ResponseEntity<byte[]>
//     * https://blog.csdn.net/jasnet_u/article/details/82805073
//     * 失败
//     */
//    @GetMapping("/download")
//    public String downloadFile(){
//        ResponseEntity<Resource> responseEntity = remoteUploadService.downloadFile();
//        return responseEntity.getBody().getFilename();
//    }
//
//    /**
//     * https://blog.csdn.net/jasnet_u/article/details/82805073
//     * 失败
//     */
//    @GetMapping("/download/byte")
//    public String downloadFileWithByte(){
//        ResponseEntity<byte[]> responseEntity = remoteUploadService.downloadFileWithByte();
//        System.out.println( responseEntity.getStatusCode());
//        return responseEntity.getStatusCode().toString();
//    }

    /**
     * 成功
     * @return
     */
    @GetMapping("/download")
    public String downloadFile(){
        InMemoryMultipartFile inMemoryMultipartFile = remoteUploadService.downloadFile();
        return inMemoryMultipartFile.getName();
    }

    @GetMapping("/default")
    public String defaultMethod() {
        return gatewayFeignClient.defaultMethod();
    }

    @GetMapping("/slow2")
    public String slow2() {
        return singleTimeOutService.slow2(8000);
    }
}
