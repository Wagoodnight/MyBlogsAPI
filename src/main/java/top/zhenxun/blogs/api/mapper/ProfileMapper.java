package top.zhenxun.blogs.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.zhenxun.blogs.api.entity.Profile;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Repository
public interface ProfileMapper extends BaseMapper<Profile> {

    /**
     * 更新个人信息
     * @param profile 个人信息
     */
    void updateProfile(@Param("profile") Profile profile);
}
