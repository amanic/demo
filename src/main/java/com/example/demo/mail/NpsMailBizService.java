package com.example.demo.mail;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import freemarker.template.Configuration;

/**
 * @description:
 * @author: yuqi.liu
 * @create: 2019-12-18 17:27
 **/
@Service
@Slf4j
public class NpsMailBizService {

    @Resource
    private BosMailService bosMailService;

    @Autowired
    private Configuration configuration;


    /**
     * 多条码商品映射邮件提醒
     * @param list
     * @return
     */
    public boolean offItemMail(List<OffItemEmailTO> list) {
        try {
            Map<String, Object> paramsMap = new HashMap();
            paramsMap.put("orderList", list);

            Template template = configuration.getTemplate("off_item_alarm.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, paramsMap);

            String subject = "【超级清仓】副条码下架提醒";
            String[] mailList = new String[]{"haitao.chen@beibei.com"};
            MailInfo mailInfo = MailInfo.builder().content(text).subject(subject).toAddress(mailList).build();

            bosMailService.sendMailNewRetail(mailInfo);
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
