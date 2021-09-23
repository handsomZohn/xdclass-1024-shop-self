package net.xdclass.service.impl;

import net.xdclass.enums.SendCodeEnum;
import net.xdclass.service.NotifyService;
import net.xdclass.utils.JsonData;
import org.springframework.stereotype.Service;

@Service
public class NotifyServiceImpl implements NotifyService {
    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {
        return null;
    }

    @Override
    public boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code) {
        return false;
    }
}
