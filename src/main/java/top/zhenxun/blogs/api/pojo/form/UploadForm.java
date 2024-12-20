package top.zhenxun.blogs.api.pojo.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Data
public class UploadForm {
    private MultipartFile file;
}
