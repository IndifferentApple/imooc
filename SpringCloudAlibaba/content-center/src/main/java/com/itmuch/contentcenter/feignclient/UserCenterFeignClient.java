package com.itmuch.contentcenter.feignclient;

import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/17 20:47
 */
@FeignClient(name="user-center")
public interface UserCenterFeignClient {
    /**
     * http://user-center/user/{id}
     * @param id
     * @return
     */
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
