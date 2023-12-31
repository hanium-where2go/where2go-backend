package hanium.where2go.global.smtp;

import hanium.where2go.global.jwt.JwtProvider;
import hanium.where2go.global.response.BaseException;
import hanium.where2go.global.response.ExceptionCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;

    public MimeMessage createAuthorizationEmail(String to, String token) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.addRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("where2go 이메일 인증");
            String msg = "";
            String url = "http://localhost:8080/customer/email-verification/";
            msg += "<h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">이메일 주소 확인</h1>";
            msg += "<p style=\"font-size: 17px; padding-right: 30px; padding-left: 30px;\">아래 링크를 눌러 확인..</p>";
            msg += "<div style=\"padding-right: 30px; padding-left: 30px; margin: 32px 0 40px;\"><table style=\"border-collapse: collapse; border: 0; background-color: #F4F4F4; height: 70px; table-layout: fixed; word-wrap: break-word; border-radius: 6px;\"><tbody><tr><td style=\"text-align: center; vertical-align: middle; font-size: 30px;\">";
            msg += "<a href=\"" + url + token + "\">where2go 인증하기 </a>";
            msg += "</td></tr></tbody></table></div>";

            message.setText(msg, "utf-8", "html");
        } catch (MessagingException e) {
            throw new BaseException(ExceptionCode.EMAIL_SERVER_ERROR);
        }

        return message;
    }


    public void sendAuthorizationEmail(String to) {
        MimeMessage message = createAuthorizationEmail(to, jwtProvider.generateAccessTokenByEmail(to));
        try {
            javaMailSender.send(message);
        } catch (MailException es) {
            throw new BaseException(ExceptionCode.INVALID_EMAIL);
        }
    }

}
