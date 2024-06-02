package com.example.momcare.service;

import com.example.momcare.models.BabyHealthIndex;
import com.example.momcare.models.MomHealthIndex;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.AddUserFollowerRequest;
import com.example.momcare.payload.request.ChangePasswordRequest;
import com.example.momcare.payload.request.UserRequest;
import com.example.momcare.payload.response.Response;
import com.example.momcare.payload.response.UserProfile;
import com.example.momcare.payload.response.UserResponse;
import com.example.momcare.repository.UserRepository;


import com.example.momcare.security.CheckAccount;
import com.example.momcare.security.Encode;
import com.example.momcare.util.Constant;
import jakarta.mail.MessagingException;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BabyHealthIndexService babyHealthIndexService;
    private final SocialPostService socialPostService;
    private final UserStoryService userStoryService;
    private final Encode encode;
    private final CheckAccount checkAccount;

    public UserService(UserRepository userRepository, EmailService emailService, BabyHealthIndexService babyHealthIndexService, SocialPostService socialPostService, UserStoryService userStoryService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.babyHealthIndexService = babyHealthIndexService;
        this.socialPostService = socialPostService;
        this.userStoryService = userStoryService;
        this.encode = new Encode();
        this.checkAccount = new CheckAccount();
    }

    @Transactional
    public Response signUpAccount(User user){
        switch (checkAccount.checkSignup(user, this)) {
            case (0):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), "User name has been used");
            case (3):
                List<MomHealthIndex> momHealthIndices = new ArrayList<>();
                List<BabyHealthIndex> babyHealthIndices = new ArrayList<>();
                user.setPremium(false);
                user.setDatePregnant("");
                user.setMomIndex(momHealthIndices);
                user.setBabyIndex(babyHealthIndices);
                user.setNameDisplay(user.getUserName());
                user.setEnabled(false);
                save(user);
                String token = UUID.randomUUID() + "-" + user.getId();
                user.setToken(token);
                update(user);
                try {
                    emailService.sendVerifyEmail(user.getEmail(), token);
                } catch (MessagingException e) {
                    return new Response(HttpStatus.EXPECTATION_FAILED.getReasonPhrase(), new ArrayList<>(), Constant.FAILURE);
                }
                List<UserResponse> users = new ArrayList<>();
                UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
                users.add(userResponse);
                return new Response(HttpStatus.OK.getReasonPhrase(), users, Constant.SUCCESS);
            case (2):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.PASSWORD_NOT_STRENGTH);
            case (1):
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.EMAIL_HAS_BEEN_USED);
            default:
                break;
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }

    public Response loginAccount(User user){
        User check = findAccountByUserName(user.getUserName());
        if (check == null)
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);


        if (Objects.equals(user.getUserName(), check.getUserName()) && Objects.equals(encode.encoderPassword(user.getPassWord()), check.getPassWord())){
            if (check.getEnabled().equals(true)) {
                List<UserResponse> users = new ArrayList<>();
                UserResponse userResponse = new UserResponse(check.getId(), check.getUserName(), check.getEmail(), check.getDatePregnant(), check.getPremium(), check.getAvtUrl(), check.getFollower(), check.getFollowing(), check.getNameDisplay());
                users.add(userResponse);
                return new Response(HttpStatus.OK.getReasonPhrase(), users, Constant.SUCCESS);
            }
            if (check.getEnabled().equals(false)) {
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.EMAIL_NOT_VERIFY);
            }
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }
    @Transactional
    public Response updateAccount(User user){
        User check = findAccountByID(user.getId());
        if (check != null) {

            if (user.getDatePregnant() != null) {
                check.setDatePregnant(user.getDatePregnant());
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
            return new Response(HttpStatus.OK.getReasonPhrase(), users, Constant.SUCCESS);
        }

        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
    }
    public Response changePassword(String id){
        User user = findAccountByID(id);
        if (user != null) {
            List<UserResponse> users = new ArrayList<>();
            UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
            users.add(userResponse);
            return new Response((HttpStatus.OK.getReasonPhrase()), users, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }
    @Transactional
    public Response changePassword(ChangePasswordRequest changePasswordRequest){
        User user = findAccountByID(changePasswordRequest.getId());
        if (user != null) {
            if (Objects.equals(encode.encoderPassword(changePasswordRequest.getOldPassword()), user.getPassWord())) {
                if (checkAccount.checkPassWordstrength(changePasswordRequest.getNewPassword())) {
                    user.setPassWord(changePasswordRequest.getNewPassword());
                    save(user);
                    return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
                } else
                    return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.PASSWORD_NOT_STRENGTH);

            } else
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.OLD_PASSWORD_NOT_MATCH);

        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @Transactional
    public Response forgotPassword(String userName){
        User user = findAccountByUserName(userName);
        if (user != null) {
            String otp = otp();
            List<String> opts = new ArrayList<>();
            user.setOtp(otp);
            save(user);
            try {
                emailService.sendOTPEmail(user.getEmail(), otp);
            } catch (MessagingException e) {
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.FAILURE);
            }
            opts.add(otp);
            return new Response((HttpStatus.OK.getReasonPhrase()), opts, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @Transactional
    public Response optLogin(String userName, String otp){
        User user = findAccountByUserName(userName);
        if (user != null) {
            if (Objects.equals(user.getOtp(), otp)) {
                String tokenPassword = UUID.randomUUID() + "-" + user.getId();
                user.setPasswordToken(tokenPassword);
                update(user);
                List<String> tokens = new ArrayList<>();
                tokens.add(tokenPassword);
                return new Response((HttpStatus.OK.getReasonPhrase()), tokens, Constant.SUCCESS);
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.OPT_NOT_MATCH);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @Transactional
    public Response createPassword(String userName, String token, String newPassword){
        User user = findAccountByUserName(userName);
        if (user != null) {
            if (Objects.equals(user.getPasswordToken(), token)) {
                if (checkAccount.checkPassWordstrength(newPassword)) {
                    user.setPassWord(newPassword);
                    save(user);
                    List<UserResponse> users = new ArrayList<>();
                    UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium(), user.getAvtUrl(), user.getFollower(), user.getFollowing(), user.getNameDisplay());
                    users.add(userResponse);
                    return new Response((HttpStatus.OK.getReasonPhrase()), users, Constant.SUCCESS);
                }
                return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.PASSWORD_NOT_STRENGTH);
            }
            return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.OPT_NOT_MATCH);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @Transactional
    public Response addFollower(AddUserFollowerRequest userFollower) {
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
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    @Transactional
    public Response unFollow(AddUserFollowerRequest userFollower) {
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
            return new Response((HttpStatus.OK.getReasonPhrase()), new ArrayList<>(), Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
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

    public Response getProfile(String id) {
        User user = findAccountByID(id);
        if (user != null) {
            List<UserProfile> userProfiles = new ArrayList<>();
            UserProfile userProfile = new UserProfile(user.getId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), user.getFollower(), socialPostService.getAllByUser(user.getId()), userStoryService.findByUserId(user.getId()), user.getShared());
            userProfiles.add(userProfile);
            return new Response((HttpStatus.OK.getReasonPhrase()), userProfiles, Constant.SUCCESS);
        }
        return new Response((HttpStatus.EXPECTATION_FAILED.getReasonPhrase()), new ArrayList<>(), Constant.USER_NOT_FOUND);
    }

    public void save (User user){
        String encoderPassword = encode.encoderPassword(user.getPassWord());
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
            userResponses.add(new UserResponse(user.getId(), user.getUserName(), user.getEmail(), user.getDatePregnant(), user.getPremium()));
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

}