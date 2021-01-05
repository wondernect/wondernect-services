package com.wondernect.services.ums.stars.module.mail.service;

import com.wondernect.elements.common.exception.BusinessException;
import com.wondernect.elements.common.utils.ESObjectUtils;
import com.wondernect.elements.mail.client.MailClient;
import com.wondernect.elements.mail.client.util.MailSendResult;
import com.wondernect.elements.thymeleaf.StringTemplateThymeleaf;
import com.wondernect.elements.thymeleaf.UrlTemplateThymeleaf;
import com.wondernect.stars.mail.dto.mail.MailResponseDTO;
import com.wondernect.stars.mail.dto.mail.SaveMailRequestDTO;
import com.wondernect.stars.mail.dto.mail.SendMailRequestDTO;
import com.wondernect.stars.mail.dto.server.MailServerResponseDTO;
import com.wondernect.stars.mail.dto.template.MailTemplateResponseDTO;
import com.wondernect.stars.mail.service.mail.MailService;
import com.wondernect.stars.mail.service.server.MailServerService;
import com.wondernect.stars.mail.service.template.MailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Copyright (C), 2020, wondernect.com
 * FileName: MailClientService
 * Author: chenxun
 * Date: 2020-11-27 09:25
 * Description: 邮件发送服务
 */
@Service
public class MailClientService extends MailService {

    @Autowired
    private StringTemplateThymeleaf stringTemplateThymeleaf;

    @Autowired
    private UrlTemplateThymeleaf urlTemplateThymeleaf;

    @Qualifier(value = "mail_client")
    @Autowired
    private MailClient mailClient;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailServerService mailServerService;

    @Autowired
    private MailTemplateService mailTemplateService;

    @Transactional
    public MailResponseDTO send(SendMailRequestDTO sendMailRequestDTO) {
        MailServerResponseDTO mailServerResponseDTO = mailServerService.findById(sendMailRequestDTO.getMailServerId());
        if (ESObjectUtils.isNull(mailServerResponseDTO)) {
            throw new BusinessException("邮件服务不存在");
        }
        MailTemplateResponseDTO mailTemplateResponseDTO = mailTemplateService.findById(sendMailRequestDTO.getMailTemplateId());
        if (ESObjectUtils.isNull(mailTemplateResponseDTO)) {
            throw new BusinessException("邮件模板不存在");
        }
        Map<String, Object> varibles = sendMailRequestDTO.getAttachment();
        MailSendResult mailSendResult;
        switch (mailTemplateResponseDTO.getType()) {
            case PLAIN_TEXT:
            {
                mailSendResult = mailClient.sendPlainTextMail(
                        mailServerResponseDTO.getHost(),
                        mailServerResponseDTO.getPort(),
                        mailServerResponseDTO.getUsername(),
                        mailServerResponseDTO.getPassword(),
                        mailTemplateResponseDTO.getPersonal(),
                        sendMailRequestDTO.getToAddress(),
                        mailTemplateResponseDTO.getSubject(),
                        mailTemplateResponseDTO.getContent(),
                        varibles
                );
                break;
            }
            case HTML_STRING:
            {
                mailSendResult = mailClient.sendHtmlMail(
                        mailServerResponseDTO.getHost(),
                        mailServerResponseDTO.getPort(),
                        mailServerResponseDTO.getUsername(),
                        mailServerResponseDTO.getPassword(),
                        mailTemplateResponseDTO.getPersonal(),
                        sendMailRequestDTO.getToAddress(),
                        mailTemplateResponseDTO.getSubject(),
                        stringTemplateThymeleaf.generateContent(mailTemplateResponseDTO.getContent(), varibles)
                );
                break;
            }
            case HTML_TEMPLATE:
            {
                mailSendResult = mailClient.sendHtmlMail(
                        mailServerResponseDTO.getHost(),
                        mailServerResponseDTO.getPort(),
                        mailServerResponseDTO.getUsername(),
                        mailServerResponseDTO.getPassword(),
                        mailTemplateResponseDTO.getPersonal(),
                        sendMailRequestDTO.getToAddress(),
                        mailTemplateResponseDTO.getSubject(),
                        urlTemplateThymeleaf.generateContent(mailTemplateResponseDTO.getUrl(), varibles)
                );
                break;
            }
            default:{
                throw new BusinessException("邮件模板类型非法");
            }
        }
        return mailService.create(
                new SaveMailRequestDTO(
                        mailSendResult.getFromAddress(),
                        mailSendResult.getFromName(),
                        mailSendResult.getToAddress(),
                        mailSendResult.getSubject(),
                        mailSendResult.getContent(),
                        mailSendResult.getResult(),
                        mailSendResult.getMessage(),
                        mailServerResponseDTO.getId(),
                        mailTemplateResponseDTO.getId()
                )
        );
    }
}
