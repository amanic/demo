package com.example.demo.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuqi.liu on 2018/1/16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
    // 邮件的文本内容
    private String content;
    // 邮件附件的文件名
    private String[] attachFileNames;
    // 邮件类型
    // BodyPart#setContent(String, String) 的第二个参数的值
    // "text/plain;charset=utf-8"
    // "text/html;charset=utf-8"
    private String contentType;

    // 邮件发送者的地址
    private String fromAddress;
    // 邮件接收者的地址
    private String[] toAddress;

    // 邮件密送接收者的地址
    private String[] toBlindCarbonCopyAddress;
    // 邮件抄送接收者的地址
    private String[] toCarbonCopyAddress;
    // 邮件主题
    private String subject;

}
