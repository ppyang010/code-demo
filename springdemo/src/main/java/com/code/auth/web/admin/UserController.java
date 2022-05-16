package com.code.auth.web.admin;

import com.code.auth.domain.PageBean;
import com.code.auth.domain.PlatformReturn;
import com.code.auth.domain.User;
import com.code.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("list")
    public PlatformReturn listUser(PageBean pageBean, HttpServletRequest request){
        Page<User> page = userService.listUser(pageBean);
        return PlatformReturn.success().setPageBean(page).setItems(page.getContent());
    }

    /**
     * 添加
     * @param id
     * @param username
     * @param password
     * @param locked
     * @param request
     * @return
     */
    @PostMapping
    public PlatformReturn save(Integer id ,String username, String password, @RequestParam(name="locked",required = false ,defaultValue = "0") int locked , HttpServletRequest request){
        System.out.println(username+password+locked);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setSalt(username);
        user.setLocked(locked);
        userService.save(user);
        return PlatformReturn.success();
    }
    @GetMapping("/{id}")
    public PlatformReturn info(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return PlatformReturn.success().setItem(user);
    }
    @DeleteMapping("/{id}")
    public PlatformReturn del (@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return PlatformReturn.success();
    }

    /**
     * 修改账户的锁定状态
     * @return
     */
    @PostMapping("{id}/locked")
    public PlatformReturn updateLocked(@PathVariable("id") Integer id,int locked){
        System.out.println(id + "--" + locked);
        userService.changeLocked(id);
        return PlatformReturn.success();
    }

    /**
     * 为用户添加角色(list)
     * @param userId
     *
     * @param roleIdList
     * @return
     */
    @PostMapping("/role/add/list")
    public PlatformReturn userAddRoleList(int userId, @RequestParam(required = false, value = "roleIdList[]", defaultValue = "") List<Integer> roleIdList){
        userService.userAddRoleList(userId,roleIdList);
        return PlatformReturn.success();
    }

    /**
     * 为用户添加角色
     * @param userId
     *
     * @param roleId
     * @return
     */
    @PostMapping("/role/add")
    public PlatformReturn userAddRole(int userId, @RequestParam(required = false, value = "roleId", defaultValue = "") Integer roleId){
        userService.userAddRoleList(userId, Arrays.asList(roleId));
        return PlatformReturn.success();
    }


    /**
     * 为用户删除角色(list)
     * @param userId
     *
     * @param roleIdList
     * @return
     */
    @PostMapping("/role/delete/list")
    public PlatformReturn userDeleteRoleList(int userId, @RequestParam(required = false, value = "roleIdList[]", defaultValue = "") List<Integer> roleIdList){
        userService.userDeleteRoleList(userId,roleIdList);
        return PlatformReturn.success();
    }
    /**
     * 为用户删除角色
     * @param userId
     *
     * @param roleId
     * @return
     */
    @PostMapping("/role/delete")
    public PlatformReturn userDeleteRole(int userId, @RequestParam(required = false, value = "roleId", defaultValue = "") Integer roleId){
        userService.userDeleteRoleList(userId,Arrays.asList(roleId));
        return PlatformReturn.success();
    }


}
