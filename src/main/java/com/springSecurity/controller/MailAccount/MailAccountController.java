package com.springSecurity.controller.MailAccount;

import com.springSecurity.service.Mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class MailAccountController {
    @Autowired
    private MailService mailServiceImpl;

    /**
     * 发送一条消息
     * @param msg
     * @return
     */

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    public String findUserByUserName(@RequestBody String msg) {
        String s = mailServiceImpl.sendMsgToMq(msg);
        return "success";
    }

}
