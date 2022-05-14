package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-05-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    String login(UcenterMember ucenterMember);
    void register(RegisterVo registerVo);
    UcenterMember getByOpenid(String openId);
    Integer countRegisterDay(String day);
}
