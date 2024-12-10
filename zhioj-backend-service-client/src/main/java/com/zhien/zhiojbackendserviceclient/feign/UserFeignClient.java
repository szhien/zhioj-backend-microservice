package com.zhien.zhiojbackendserviceclient.feign;

import com.zhien.zhiojbackendcommon.common.ErrorCode;
import com.zhien.zhiojbackendcommon.exception.BusinessException;
import com.zhien.zhiojbackendmodel.model.entity.User;
import com.zhien.zhiojbackendmodel.model.enums.UserRoleEnum;
import com.zhien.zhiojbackendmodel.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

import static com.zhien.zhiojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @name UserFeignClient
 * @description 用户服务提供的服务:
 * 其中有些服务是非常简单的，都没有去操作数据库，只是简单的处理，这种方法，可以将具体实现作为默认实现，会有一定的性能提升，比如下面的isAdmin和getUserVO
 * @author Zhien 
 * @createDate 2024/12/05 09:24
 * @version 1.0
 */

/**
 * name: 服务名,提供下述接口的方法是属于哪个服务的
 * path: 请求路径,提供下述接口的方法是属于哪个路径的,是指类名上面的requestMapping的值(或是方法的前缀路径，有时会配置servlet.context-path)
 */
@FeignClient(name = "zhioj-backend-user-service",path = "/api/user/inner")
public interface UserFeignClient {
    /**
     * 根据 id 获取用户
     * @param userId
     * @return
     */
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    /**
     * 根据 id 获取用户列表
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user) {
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
