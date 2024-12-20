package top.zhenxun.blogs.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zhenxun.blogs.api.mapper.UserMapper;
import top.zhenxun.blogs.api.pojo.form.LocalResetPasswordForm;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */
@Component
public class CommandUtils {

    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                    String command = scanner.nextLine();
                    input(command);
                } catch (NoSuchElementException | IllegalStateException e) {
                    break;
                }
            }
        }).start();
    }

    public void input(String a) {
        switch (a) {
            case "password":
                resetPassword();
                break;
            case "help":
            case "?":
            case "？":
                System.out.println("password: 更改密码");
                break;
            default:
                System.err.println("未知命令,输入`help`查看帮助");
                break;
        }
    }

    public void resetPassword() {
        List<String> emailList = userMapper.emailList();
        for (String email : emailList) {
            System.out.println(emailList.indexOf(email) + 1 + ". " + email);
        }
        System.out.println("请选择你需要修改密码的账号");
        System.out.println("如果存在有多个同样的邮箱，请手动去数据库修改密码，注意64位SHA-256加密");
        System.out.println("请输入序号，输入0退出");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt() - 1;
        if (id == -1) {
            System.out.println("操作取消");
            return;
        }
        String email = emailList.get(id);
        if (email == null) {
            System.out.println("账号不存在，请重新触发本命令");
            return;
        }
        System.out.println("请输入新密码");
        String password = scanner.next();
        System.out.println("请再次输入新密码");
        String rePassword = scanner.next();
        if (!password.equals(rePassword)) {
            System.out.println("两次输入的密码不一致，请重新触发本命令");
            return;
        }
        LocalResetPasswordForm localResetPasswordForm = new LocalResetPasswordForm();
        localResetPasswordForm.setEmail(email);
        localResetPasswordForm.setPassword(EncryptUtil.encrypt(password));
        if (userMapper.updatePassword(localResetPasswordForm) > 0) {
            System.out.println("修改成功,请牢记密码,您的新密码为:" + password);
        } else {
            System.out.println("修改失败");
        }
    }
}
