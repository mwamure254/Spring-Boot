package com.mfano.moe.security.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.mfano.moe.security.model.Profile;
import com.mfano.moe.security.repository.ProfileRepository;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    private final String baseDirectory = "src/main/resources/static/img/profile/";

    // Get All Profiles
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    // Get Profile By Id
    public Profile findById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    // Get Profile By Id
    public Profile findByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    // Delete Profile
    public void delete(Long id) {
        profileRepository.deleteById(id);
    }

    // Save Profile
    public void save(Profile profile) {
        profileRepository.save(profile);
    }

    // Update Profile Image
    public void updateProfileImage(Long userid, MultipartFile file, Model model) throws IOException {
        Profile existing = findByUserId(userid);
        try {
            Path path = Path.of(baseDirectory + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            String img = file.getOriginalFilename();
            existing.setImage(img);
            save(existing);
            model.addAttribute("message", "Profile image updated successsfully");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

    }

    // Update Profile Image
    public void deleteProfileImage(Long userid, Model model) throws IOException {
        Profile existing = findByUserId(userid);
        try {
            String image = existing.getImage();
            Path path = Path.of(baseDirectory + image);
            Files.delete(path);

            existing.setImage(null);
            save(existing);
            model.addAttribute("message", "Profile image deleted successfully");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

    }

    // Update Profile
    public void update(Long userid, Profile profile) {
        Profile existing = findByUserId(userid);

        // update only editable fields
        existing.setFin(profile.getFin());
        existing.setLan(profile.getLan());
        existing.setOther(profile.getOther());
        existing.setAbout(profile.getAbout());
        existing.setGender(profile.getGender());
        existing.setIDN(profile.getIDN());
        existing.setSN(profile.getSN());
        existing.setDesignation(profile.getDesignation());
        existing.setCounty(profile.getCounty());
        existing.setAddress(profile.getAddress());
        existing.setPhone(profile.getPhone());
        existing.setTwitter(profile.getTwitter());
        existing.setFacebook(profile.getFacebook());
        existing.setInstagram(profile.getInstagram());
        existing.setLinkedin(profile.getLinkedin());

        save(existing);

    }

    // check profile
    public void checkProfile(Long userid, Model model) {
        Profile profile = findByUserId(userid);
        if (profile == null) {
            profile = new Profile();
            profile.setUserid(userid);
            save(profile);
            model.addAttribute("profile", profile);
        } else {
            model.addAttribute("profile", profile);
        }
    }

}
