package com.example.charging_life.email;

import com.example.charging_life.exception.CustomException;
import com.example.charging_life.exception.ExceptionEnum;
import com.example.charging_life.member.JpaMemberRepo;
import com.example.charging_life.member.dto.EmailDto;
import com.example.charging_life.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JpaEmailRepo jpaEmailRepo;
    private final JpaMemberRepo jpaMemberRepo;
    private final JavaMailSender javaMailSender;
    @Value("${email}")
    private String from;

    public String certifyEmail(EmailDto emailDto) {

        Optional<Member> member = jpaMemberRepo.findByEmail(emailDto.getEmail());
        if (member.isPresent()) {
            throw new CustomException(ExceptionEnum.EmailDuplicated);
        } else {
            String code = makeCode();
            jpaEmailRepo.save(new Email(emailDto.getEmail(), code));
            sendCodeToEmail(code,emailDto.getEmail());
            return code;
        }
    }

    public Boolean confirmCode(String code, String email) {

        Email findEmail = jpaEmailRepo.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionEnum.EmailNotExisted));
        if (findEmail.getCreateTime().isBefore(LocalDateTime.now().plusMinutes(3))) {
            throw new CustomException(ExceptionEnum.Codeexpired);
        } else if (findEmail.getCode().equals(code)) {
            return true;
        } else {
            return false;
        }
    }

    public void reissueCode(EmailDto emailDto) {
        Email email = jpaEmailRepo.findByEmail(emailDto.getEmail())
                .orElseThrow(() -> new CustomException(ExceptionEnum.EmailNotExisted));
        sendCodeToEmail(makeCode(),email.getEmail());
    }

    public String makeCode() {

        Random random = new Random();
        String code = "";
        for (int i = 0; i < 4; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    public void sendCodeToEmail(String code, String to) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("슬기로운 충전생활 인증코드입니다.");
            message.setText("인증코드: " + code);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
