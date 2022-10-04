package com.Quan.TryJWT.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class JavaSenderService {
	
	@Autowired
	private JavaMailSender mailSender; 
	
	@Autowired
	private Environment env; 
	
	private final String ACCOUNT_SID = "ACd697f602f19de1261a6dc00a00873e25";
    private final String AUTH_TOKEN = "f20861a25dce5302f92b17bd2ac86ddf";
    private final String FROM_NUMBER = "+19403736572";
    
	public void sendMailVerifyCode(String toEmail, String body) {
		String emailFrom = env.getProperty("spring.mail.username");
		SimpleMailMessage mail = new SimpleMailMessage(); 
		
		mail.setFrom(emailFrom);
		mail.setTo(toEmail);
		mail.setText(body);
		mail.setSubject("Mã OTP xác minh tại Ministore");
		mailSender.send(mail);
		
	}
	
	public void sendSmsVerifyCode(String smsTo, String code) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      
        String msg ="Your OTP - "+code+ " please verify this OTP in Ministore.com";
        String phone = "+84"+smsTo.substring(1, smsTo.length());
       
        Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber(FROM_NUMBER), msg)
                .create();
	}
}

