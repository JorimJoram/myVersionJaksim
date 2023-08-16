package com.twinkle.JakSim.model.service.account;

import com.twinkle.JakSim.model.dao.account.UserDao;
import com.twinkle.JakSim.model.dto.account.UserDto;
import com.twinkle.JakSim.model.dto.account.UserStat;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private String key;

    @Transactional
    public int CreateMember(UserDto member){
        int result = -1;
        try{
            member.setPw(passwordEncoder.encode(member.getPw()));
            result = userDao.insertMember(member);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public UserDto findByUsername(String id) {
        Optional<UserDto> userDto = userDao.findByUserId(id);
        return userDto.orElse(null);
    }

    public UserDto findByTel(String tel) {
        return userDao.findByTel(tel);
    }

    public int update(String user_id, String pw) {
        return userDao.updatePassword(user_id, passwordEncoder.encode(pw));
    }

    public AtomicBoolean checkPassword(String username, String pw) {
        AtomicBoolean result = new AtomicBoolean(false);
        userDao.findByUserId(username).ifPresent(
                item -> {
                    result.set(passwordEncoder.matches(pw, item.getPw()));
                }
        );
        return result;
    }

    public int delete(String id) {
        return userDao.deleteUser(id);
    }

    public String validateEmail(String email) {
        createKey();
        try{
            sendEmail(email);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return this.key;
    }

    private void createKey() {
        //숫자 0-9, 문자 A-Z
        int [] numList = IntStream.concat(
                IntStream.rangeClosed(65, 90),
                IntStream.rangeClosed(48, 57)
        ).toArray();

        List<Integer> indexList = IntStream.range(0, numList.length).boxed().collect(Collectors.toList());
        Collections.shuffle(indexList);
        //특정 문자가 지나치게 중복되어 이를 해결하고자 전체 요소를 다시 섞음

        this.key = indexList.stream().limit(6).map(i -> String.format("%c", numList[i])).collect(Collectors.joining());
    }

    private MimeMessage createMailForm(String email) throws MessagingException, UnsupportedEncodingException{
        String sender = "lsd4026@naver.com";
        String title = "[작심득근] 이메일 인증";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setSubject(title);
        message.setFrom(sender);
        message.setText(String.format("이메일 인증번호: "+ this.key), "utf-8", "html");

        return message;
    }

    private void sendEmail(String toMail) throws MessagingException, UnsupportedEncodingException{
        MimeMessage emailForm = createMailForm(toMail);
        emailSender.send(emailForm);
    }

    public Optional<UserDto> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public int updateEmail(String email, String username) {
        return userDao.updateEmail(email, username);
    }

    public int updateName(String name, String username) {
        return userDao.updateName(name, username);
    }

    public int updateTel(String tel, String username) {
        return userDao.updateTel(tel, username);
    }

    public List<UserStat> getAmountAccounts(String start, String end) {
        return userDao.getAmountAccounts(getDefaultStart(start), getDefaultEnd(end));
    }

    private String getDefaultStart(String start){
        if(start == null || start.isEmpty()){
            start = LocalDate.now().plusDays(-6).toString();
        }
        return start;
    }

    private String getDefaultEnd(String end){
        if(end == null || end.isEmpty()){
            end = LocalDate.now().plusDays(1).toString();
        }
        return end;
    }

    public List<UserStat> getAmountByRole() {
        return userDao.getAmountByRole();
    }
}
