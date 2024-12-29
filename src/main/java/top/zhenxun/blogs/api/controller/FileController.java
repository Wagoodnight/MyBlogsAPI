package top.zhenxun.blogs.api.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zhenxun.blogs.api.common.Const;
import top.zhenxun.blogs.api.common.Response;
import top.zhenxun.blogs.api.common.ResponseType;
import top.zhenxun.blogs.api.entity.SystemInfo;
import top.zhenxun.blogs.api.exception.ServiceException;
import top.zhenxun.blogs.api.mapper.SystemMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Api(tags = "文件模块接口")
@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 文件存储路径
     */
    private static final String FILE_FOLDER = Const.FILE_FOLDER_FILE;

    @Autowired
    private SystemMapper systemMapper;

    private static final Logger log = LoggerFactory.getLogger(FileController.class);


    /**
     * 上传文件
     * @param file 文件
     * @return 文件下载的 URL
     */
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Response<?> uploadFile(@ModelAttribute MultipartFile file) {
        if (file.isEmpty()) {
            throw new ServiceException(ResponseType.FILE_CAN_NOT_BE_NULL);
        }

        try {
            // 获取当前日期
            String dateFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());

            // 文件存储路径
            String jarDir = System.getProperty("user.dir");
            String storagePath = Paths.get(jarDir, FILE_FOLDER, dateFolder).toString();

            // 创建文件夹
            File folder = new File(storagePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // 获取原始文件名和拓展名
            String originalFilename = file.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(originalFilename);

            // 生成唯一文件名
            String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
            String newFilename = uuid + (extension != null ? "." + extension : "");

            // 保存文件
            File savedFile = new File(folder, newFilename);
            file.transferTo(savedFile);

            // 生成下载URL
            SystemInfo systemInfo = systemMapper.selectById(1);
            String downloadUrl = systemInfo.getSiteUrl() + "file/download/" + dateFolder + "/" + newFilename;
            return Response.success(downloadUrl);

        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new ServiceException(ResponseType.FILE_UPLOAD_FILED);
        }
    }

    /**
     * 下载文件
     * @param dateFolder 日期分类文件夹
     * @param filename 文件名
     * @return 文件资源
     */
    @GetMapping("/download/{dateFolder}/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String dateFolder, @PathVariable String filename) {
        try {
            String jarDir = System.getProperty("user.dir");
            File file = Paths.get(jarDir, Const.FILE_FOLDER_FILE, dateFolder, filename).toFile();

            if (!file.exists()) {
                throw new RuntimeException("文件不存在");
            }

            // 推断文件类型
            String mimeType = java.nio.file.Files.probeContentType(file.toPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            // 判断是否为图片类型
            if (isImageFile(filename)) {
                // 如果是图片，直接显示图片
                byte[] fileContent = java.nio.file.Files.readAllBytes(file.toPath());
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .body(fileContent);
            }

            // 如果不是图片，触发下载
            byte[] fileContent = java.nio.file.Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(fileContent);

        } catch (IOException e) {
            throw new RuntimeException("文件操作失败: " + e.getMessage());
        }
    }

    /**
     * 判断文件是否是图片类型
     */
    private boolean isImageFile(String filename) {
        return Arrays.stream(Const.IMAGE_SUFFIX_LIST)
                .anyMatch(suffix -> filename.toLowerCase().endsWith(suffix));
    }

}
