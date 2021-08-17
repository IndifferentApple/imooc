package com.itmuch.contentcenter.service.content;

import com.itmuch.contentcenter.dao.content.ShareMapper;
import com.itmuch.contentcenter.domain.dto.content.ShareDTO;
import com.itmuch.contentcenter.domain.entity.content.Share;
import com.itmuch.contentcenter.domain.dto.user.UserDTO;
import com.itmuch.contentcenter.feignclient.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author ：peter
 * @description：TODO
 * @date ：2021/8/10 19:36
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {
    private final ShareMapper shareMapper;
    private final UserCenterFeignClient userCenterFeignClient;

    public ShareDTO findById(Integer id){
        //获取分享详情
        //select * from share where id = #{id}
        Share share = this.shareMapper.selectByPrimaryKey(id);
        //发布人id
        Integer userId = share.getUserId();

        UserDTO userDTO = this.userCenterFeignClient.findById(userId);

        ShareDTO shareDTO = new ShareDTO();
        //消息装配
        BeanUtils.copyProperties(share,shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return shareDTO;
    }

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        //用HTTP GET方法去请求，并返回一个对象
        //restTemplate.getForObject()
        //或者restTemplate.postForObject
        //restTemplate.getForEntity()=> forEntity.getStatusCode()可以返回状态码
         ResponseEntity<String> forEntity= restTemplate.getForEntity(
                "http://localhost:8080/users/1",
                String.class
        );
        System.out.println(forEntity);

    }
}
