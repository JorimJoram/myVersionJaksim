package com.twinkle.JakSim.model.service.account;

import com.twinkle.JakSim.model.dao.account.FileDao;
import com.twinkle.JakSim.model.dto.account.UserImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileDao fileDao;
    @Autowired
    private HttpSession session;
    public boolean updateProfileImage(MultipartFile file, String username) throws Exception{
        UserImage userImage = new UserImage();

        String projectPath = System.getProperty("user.dir") + "/JakSim/src/main/resources/static/image/profiles";
        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        userImage.setUser_id(username);
        userImage.setPath("/image/profiles/" + fileName);

        if(fileDao.create(userImage) > 0){
            session.setAttribute("session_profile", userImage);
            return true;
        }else{
            return false;
        }
    }

    public UserImage getSingeProfile(String username){
        return fileDao.getRecentImage(username);
    }

    public List<UserImage> getAllImages(String username){
        return fileDao.getAllImages(username);
    }
}
