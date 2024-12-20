package top.zhenxun.blogs.api.pojo;

/**
 * @author ATRI <miko217@xnu.edu.cn>
 */

public enum Role {

    /**
     * 管理员
     */
    anonymous(0, "游客"),


    admin(1, "管理员");

    private final Integer code;

    private final String name;

    Role(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getCode() {
        return this.code;
    }

    public static String getNameByCode(Integer code) {
        Role[] values = Role.values();
        for(Role role : values) {
            if(role.code.equals(code)){
                return role.name;
            }
        }
        return null;
    }

    public static Integer getCodeByName(String name) {
        Role[] values = Role.values();
        for(Role role : values) {
            if(role.name.equals(name)) {
                return role.code;
            }
        }
        return null;
    }
    public static Role getRoleByCode(Integer code) {
        Role[] roles = Role.values();
        for(Role role : values()) {
            if(role.code.equals(code)) {
                return role;
            }
        }
        return null;
    }

}
