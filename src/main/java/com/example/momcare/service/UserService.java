package com.example.momcare.service;


import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.AddUserFollowerRequest;
import com.example.momcare.payload.request.ChangePasswordRequest;
import com.example.momcare.payload.request.UserRequest;
import com.example.momcare.payload.response.UserProfile;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.repository.UserRepository;


import com.example.momcare.security.CheckAccount;
import com.example.momcare.util.Constant;
import jakarta.mail.MessagingException;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserService{

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final CheckAccount checkAccount;

    public UserService(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.checkAccount = new CheckAccount();
    }



    @Transactional
    public List<UserResponse> updateAccount(User user) throws ResourceNotFoundException {
        User check = findAccountByID(user.getId());
        if (check != null) {

            if (user.getDatePregnant() != null) {
                check.setDatePregnant(user.getDatePregnant());
                BabyHealthIndexService babyHealthIndexService = new BabyHealthIndexService(this);
                check.setBabyIndex(babyHealthIndexService.updateDatePregnant(check));
            }
            if (user.getPremium() != null)
                check.setPremium(user.getPremium());
            if (user.getBabyIndex() != null)
                check.setBabyIndex(user.getBabyIndex());
            if (user.getMomIndex() != null)
                check.setMomIndex(user.getMomIndex());
            if (user.getAvtUrl() != null)
                check.setAvtUrl(user.getAvtUrl());
            if (user.getNameDisplay() != null)
                check.setNameDisplay(user.getNameDisplay());
            List<UserResponse> users = new ArrayList<>();
            UserResponse userResponse = new UserResponse(
                    check.getId(),
                    check.getUserName(),
                    check.getEmail(),
                    check.getDatePregnant(),
                    check.getPremium(),
                    check.getAvtUrl(),
                    check.getFollower(),
                    check.getFollowing(),
                    check.getNameDisplay());

            users.add(userResponse);
            update(check);
            return users;
        }

        throw new ResourceNotFoundException(Constant.FAILURE);
    }
    public List<UserResponse> findById(String id) throws ResourceNotFoundException {
        User user = findAccountByID(id);
        if (user != null) {
            List<UserResponse> users = new ArrayList<>();
            UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
            users.add(userResponse);
            return users;
        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }
    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest) throws ResourceNotFoundException {
        User user = findAccountByID(changePasswordRequest.getId());
        if (user != null) {
            if (Objects.equals(passwordEncoder.encode(changePasswordRequest.getOldPassword()), user.getPassWord())) {
                if (checkAccount.checkPassWordstrength(changePasswordRequest.getNewPassword())) {
                    user.setPassWord(changePasswordRequest.getNewPassword());
                    save(user);
                } else
                    throw new ResourceNotFoundException(Constant.PASSWORD_NOT_STRENGTH);

            } else
                throw new ResourceNotFoundException(Constant.OLD_PASSWORD_NOT_MATCH);

        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }

    @Transactional
    public void forgotPassword(String userName) throws ResourceNotFoundException {
        User user = findAccountByUserName(userName);
        if (user != null) {
            String otp = otp();
            user.setOtp(otp);
            update(user);
            try {
                emailService.sendOTPEmail(user.getEmail(), otp);
            } catch (MessagingException e) {
                throw new ResourceNotFoundException(Constant.FAILURE);
            }
        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }

    @Transactional
    public void optLogin(String userName, String otp) throws ResourceNotFoundException {
        User user = findAccountByUserName(userName);
        if (user != null) {
            if (Objects.equals(user.getOtp(), otp)) {
                String tokenPassword = UUID.randomUUID() + "-" + user.getId();
                user.setPasswordToken(tokenPassword);
                update(user);
            }
            throw new ResourceNotFoundException(Constant.OPT_NOT_MATCH);
        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }

    @Transactional
    public List<UserResponse> createPassword(String userName, String token, String newPassword) throws ResourceNotFoundException {
        User user = findAccountByUserName(userName);
        if (user != null) {
            if (Objects.equals(user.getPasswordToken(), token)) {
                if (checkAccount.checkPassWordstrength(newPassword)) {
                    user.setPassWord(newPassword);
                    save(user);
                    List<UserResponse> users = new ArrayList<>();
                    UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
                    users.add(userResponse);
                    return users;
                }
                throw new ResourceNotFoundException(Constant.PASSWORD_NOT_STRENGTH);
            }
            throw new ResourceNotFoundException(Constant.OPT_NOT_MATCH);
        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }

    @Transactional
    public void addFollower(AddUserFollowerRequest userFollower) throws ResourceNotFoundException {
        User userFollowed = findAccountByID(userFollower.getIdFollowingUser());
        User userFollowing = findAccountByID(userFollower.getIdUser());
        if (userFollowed != null && userFollowing != null) {
            Set<String> ids = userFollowed.getFollower();
            if (userFollowed.getFollower() == null)
                ids = new HashSet<>();
            ids.add(userFollower.getIdUser());
            userFollowed.setFollower(ids);
            update(userFollowed);
            Set<String> idsing = userFollowing.getFollowing();
            if (userFollowing.getFollowing() == null)
                idsing = new HashSet<>();
            idsing.add(userFollower.getIdFollowingUser());
            userFollowing.setFollowing(idsing);
            update(userFollowing);
        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }

    @Transactional
    public void unFollow(AddUserFollowerRequest userFollower) throws ResourceNotFoundException {
        User userFollowed = findAccountByID(userFollower.getIdFollowingUser());
        User userFollowing = findAccountByID(userFollower.getIdUser());
        if (userFollowed != null && userFollowing != null) {
            Set<String> ids = userFollowed.getFollower();
            if (userFollowed.getFollower() != null) {
                ids.remove(userFollower.getIdUser());
                userFollowed.setFollower(ids);
                update(userFollowed);
            }
            Set<String> idsing = userFollowing.getFollowing();
            if (userFollowing.getFollowing() != null) {
                idsing.remove(userFollower.getIdFollowingUser());
                userFollowing.setFollowing(idsing);
                update(userFollowing);
            }
        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }
    @Transactional
    public String verifyEmail(String token) {
        User user = findAccountByToken(token);
        if (user != null) {
            user.setEnabled(true);
            update(user);
            return EmailService.SUCCESS;
        }
        return EmailService.ERROR;
    }

    public List<UserProfile> getProfile(String id) throws ResourceNotFoundException {
        User user = findAccountByID(id);
        if (user != null) {
            List<UserProfile> userProfiles = new ArrayList<>();
            UserProfile userProfile = new UserProfile(user.getId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), user.getFollower(), user.getShared());
            userProfiles.add(userProfile);
            return userProfiles;
        }
        throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
    }

    public void save (User user){
        String encoderPassword = passwordEncoder.encode(user.getPassWord());
        user.setPassWord(encoderPassword);
        this.userRepository.save(user);
    }
    public void update (User user){
        this.userRepository.save(user);
    }

    public List<UserResponse> searchUserByUserName(String keyWord){
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = userRepository.findByUserNameLike(keyWord);
        for (User user : users)
            userResponses.add(new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay()));
        return userResponses;
    }


    public User findAccountByUserName(String user){
        return this.userRepository.findUserByUserName(user);
    }

    public User findAccountByEmail(String email){ return this.userRepository.findUserByEmail(email);}

    public User findAccountByID(String id){
        ObjectId objectId = new ObjectId(id);
        return this.userRepository.findUserById(objectId);
    }
    public User findAccountByToken(String token){
        return this.userRepository.findUserByToken(token);
    }
    public int gestationalAge(String datePregnant, String dateEnd){
        if(datePregnant.isEmpty())
            return 0;
        LocalDateTime dateStart = LocalDateTime.parse(datePregnant);
        LocalDateTime dateEndTime = LocalDateTime.parse(dateEnd);
        return (int) ChronoUnit.WEEKS.between(dateStart,dateEndTime);
    }

    public String otp(){
        // Using numeric values
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);

    }
    public User convertUserRequestToUser(UserRequest user){
        User user1 = new User();
        user1.setId(user.getId());
        user1.setUserName(user.getUserName());
        user1.setEmail(user.getEmail());
        user1.setPassWord(user.getPassWord());
        user1.setAvtUrl(user.getAvtUrl());
        user1.setFollower(user.getFollower());
        user1.setFollowing(user.getFollowing());
        user1.setPremium(user.getPremium());
        user1.setDatePregnant(user.getDatePregnant());
        user1.setMomIndex(user.getMomIndex());
        user1.setBabyIndex(user.getBabyIndex());
        user1.setNameDisplay(user.getNameDisplay());
        return user1;
    }

    public List<UserResponse> getAllFollower(String id){
        User user = findAccountByID(id);
        List<UserResponse> userResponses = new ArrayList<>();
        if (user != null) {
            for (String idFollower : user.getFollower()) {
                User userFollower = findAccountByID(idFollower);
                if (userFollower != null) {
                    UserResponse userResponse = new UserResponse(userFollower.getId(), userFollower.getUserName(), userFollower.getEmail(), userFollower.getDatePregnant(), userFollower.getPremium(), userFollower.getAvtUrl(), userFollower.getFollower(), userFollower.getFollowing(), userFollower.getNameDisplay());
                    userResponses.add(userResponse);
                }
            }
        }
        return userResponses;
    }
    public List<UserResponse> getAllFollowing(String id) {
        User user = findAccountByID(id);
        List<UserResponse> userResponses = new ArrayList<>();
        if (user != null) {
            for (String idFollowing : user.getFollowing()) {
                User userFollower = findAccountByID(idFollowing);
                if (userFollower != null) {
                    UserResponse userResponse = new UserResponse(userFollower.getId(), userFollower.getUserName(), userFollower.getEmail(), userFollower.getDatePregnant(), userFollower.getPremium(), userFollower.getAvtUrl(), userFollower.getFollower(), userFollower.getFollowing(), userFollower.getNameDisplay());
                    userResponses.add(userResponse);
                }
            }
        }
        return userResponses;
    }
}