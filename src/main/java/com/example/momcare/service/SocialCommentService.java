package com.example.momcare.service;

import com.example.momcare.exception.ResourceNotFoundException;
import com.example.momcare.models.SocialComment;
import com.example.momcare.models.SocialPost;
import com.example.momcare.models.SocialReaction;
import com.example.momcare.models.User;
import com.example.momcare.payload.request.SocialCommentDeleteRequest;
import com.example.momcare.payload.request.SocialCommentNewRequest;
import com.example.momcare.payload.request.SocialCommentUpdateRequest;
import com.example.momcare.payload.response.SocialCommentResponse;
import com.example.momcare.payload.response.SocialReactionResponse;
import com.example.momcare.repository.SocialCommentRepository;
import com.example.momcare.util.Constant;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SocialCommentService {
    SocialCommentRepository socialCommentRepository;
    UserService userService;
    SocialPostService socialPostService;

    public SocialCommentService(SocialCommentRepository socialCommentRepository, UserService userService, SocialPostService socialPostService) {
        this.socialCommentRepository = socialCommentRepository;
        this.userService = userService;
        this.socialPostService = socialPostService;
    }

    public List<SocialComment> getAllSocialComments() {
        return findAll();
    }

    public List<SocialCommentResponse> getSocialCommentsById(String id) {
        List<SocialComment> socialComments = findAllById(id);
        List<SocialCommentResponse> socialCommentResponses = new ArrayList<>();
        for (SocialComment socialComment : socialComments) {
            User user = userService.findAccountByID(socialComment.getUserId());
            if (user != null) {
                socialCommentResponses.add(new SocialCommentResponse(socialComment.getId(), socialComment.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialComment.getPostId(), socialComment.getCommentId(), socialComment.getReactions(), socialComment.getReplies(), socialComment.getDescription(), socialComment.getTime()));
            }
        }
        return socialCommentResponses;
    }

    public List<Map<String, SocialReactionResponse>> getSocialCommentReactions(String id) throws ResourceNotFoundException {
        SocialComment socialComment = findById(id);
        Map<String, SocialReactionResponse> socialReactionResponseMap = new HashMap<>();
        User user = null;
        SocialReactionResponse socialReactionResponse = null;
        if (socialComment != null) {
            for (String userId : socialComment.getReactions().keySet()) {
                user = userService.findAccountByID(userId);
                if (user == null)
                    throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
                SocialReaction socialReaction = socialComment.getReactions().get(userId);
                socialReactionResponse = new SocialReactionResponse(user.getAvtUrl(), user.getNameDisplay(), socialReaction.getTime(), socialReaction.getReaction());
                socialReactionResponseMap.put(userId, socialReactionResponse);
            }
            List<Map<String, SocialReactionResponse>> list = new ArrayList<>();
            list.add(socialReactionResponseMap);
            return list;
        }
        throw new ResourceNotFoundException(Constant.FAILURE);
    }

    @Transactional
    public List<SocialCommentResponse> create(SocialCommentNewRequest request) throws ResourceNotFoundException {
        SocialComment socialComment = createSocialComment(request);

        if (socialComment == null) {
            throw new ResourceNotFoundException(Constant.NOT_FOUND_COMMENT);
        }

        SocialPost socialPost = socialPostService.findById(request.getPostId());
        if (socialPost == null) {
            throw new ResourceNotFoundException(Constant.NOT_FOUND_POST);
        }

        updateSocialPostComments(socialPost, socialComment);
        User user = userService.findAccountByID(socialComment.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException(Constant.USER_NOT_FOUND);
        }
        List<SocialCommentResponse> socialCommentResponses = new ArrayList<>();
        socialCommentResponses.add(new SocialCommentResponse(socialComment.getId(), socialComment.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialComment.getPostId(), socialComment.getCommentId(), socialComment.getReactions(), socialComment.getReplies(), socialComment.getDescription(), socialComment.getTime()));
        return socialCommentResponses;
    }

    private SocialComment createSocialComment(SocialCommentNewRequest request) {
        SocialComment socialComment = new SocialComment(request.getUserId(), request.getPostId(), request.getCommentId(),
                request.getDescription(), LocalDateTime.now().toString());
        SocialComment savedComment = save(socialComment);

        if (request.getCommentId() != null && !request.getCommentId().isEmpty()) {
            updateRepliesForRepliedComment(request.getCommentId(), savedComment);
        }

        return savedComment;
    }

    private void updateRepliesForRepliedComment(String commentId, SocialComment comment) {
        SocialComment repliedComment = findById(commentId);
        if (repliedComment != null) {
            List<String> repliesList = repliedComment.getReplies();
            if (repliesList == null) {
                repliesList = new ArrayList<>();
            }
            repliesList.add(comment.getId());
            repliedComment.setReplies(repliesList);
            save(repliedComment);
        }
    }

    private void updateSocialPostComments(SocialPost socialPost, SocialComment comment) {
        Set<String> commentSet = socialPost.getComments();
        if (commentSet == null) {
            commentSet = new HashSet<>();
        }
        commentSet.add(comment.getId());
        socialPost.setComments(commentSet);
        socialPostService.save(socialPost);
    }


    @Transactional
    public List<SocialCommentResponse> update(SocialCommentUpdateRequest request) throws ResourceNotFoundException {
        SocialComment socialComment = findById(request.getId());
        if (socialComment != null) {
            if (request.getDescription() != null)
                socialComment.setDescription(request.getDescription());
            if (save(socialComment) != null) {
                List<SocialCommentResponse> socialCommentResponses = new ArrayList<>();
                User user = userService.findAccountByID(socialComment.getUserId());
                socialCommentResponses.add(new SocialCommentResponse(socialComment.getId(), socialComment.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialComment.getPostId(), socialComment.getCommentId(), socialComment.getReactions(), socialComment.getReplies(), socialComment.getDescription(), socialComment.getTime()));
                return socialCommentResponses;
            } else
                throw new ResourceNotFoundException(Constant.FAILURE);
        } else
            throw new ResourceNotFoundException(Constant.NOT_FOUND_COMMENT);
    }

    @Transactional
    public List<SocialCommentResponse> addReaction(SocialCommentUpdateRequest request) throws ResourceNotFoundException {
        SocialComment socialComment = findById(request.getId());
        if (socialComment != null) {
            if (request.getReaction() != null && request.getUserIdReaction() != null) {
                if (socialComment.getReactions() == null)
                    socialComment.setReactions(new HashMap<>());
                Map<String, SocialReaction> reactions = socialComment.getReactions();
                reactions.put(request.getUserIdReaction(), request.getReaction());
                socialComment.setReactions(reactions);
                save(socialComment);
                List<SocialCommentResponse> socialCommentResponses = new ArrayList<>();
                User user = userService.findAccountByID(socialComment.getUserId());
                socialCommentResponses.add(new SocialCommentResponse(socialComment.getId(), socialComment.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialComment.getPostId(), socialComment.getCommentId(), socialComment.getReactions(), socialComment.getReplies(), socialComment.getDescription(), socialComment.getTime()));
                return socialCommentResponses;
            }
            throw new ResourceNotFoundException(Constant.NOT_FOUND_USER_OR_REACTION);
        } else
            throw new ResourceNotFoundException(Constant.NOT_FOUND_COMMENT);
    }

    @Transactional
    public List<SocialCommentResponse> deleteReaction(SocialCommentUpdateRequest request) throws ResourceNotFoundException {
        SocialComment socialComment = findById(request.getId());
        if (socialComment != null) {
            if (request.getUserIdReaction() != null) {
                Map<String, SocialReaction> reactions = socialComment.getReactions();
                reactions.remove(request.getUserIdReaction());
                socialComment.setReactions(reactions);
                save(socialComment);
                List<SocialCommentResponse> socialCommentResponses = new ArrayList<>();
                User user = userService.findAccountByID(socialComment.getUserId());
                socialCommentResponses.add(new SocialCommentResponse(socialComment.getId(), socialComment.getUserId(), user.getUserName(), user.getNameDisplay(), user.getAvtUrl(), socialComment.getPostId(), socialComment.getCommentId(), socialComment.getReactions(), socialComment.getReplies(), socialComment.getDescription(), socialComment.getTime()));
                return socialCommentResponses;
            }
            throw new ResourceNotFoundException(Constant.NOT_FOUND_USER_OR_REACTION);
        } else
            throw new ResourceNotFoundException(Constant.NOT_FOUND_COMMENT);
    }

    @Transactional
    public void delete(SocialCommentDeleteRequest socialCommentDeleteRequest) throws ResourceNotFoundException {
        SocialComment socialComment = findById(socialCommentDeleteRequest.getId());
        if (socialComment == null) {
            throw new ResourceNotFoundException(Constant.NOT_FOUND_COMMENT);
        }

        boolean deleted = deleteSocialComment(socialComment.getId());
        if (!deleted) {
            throw new ResourceNotFoundException(Constant.FAILURE);
        }

        if (!updateSocialPost(socialCommentDeleteRequest.getPostId(), socialComment.getId(), socialComment.getCommentId())) {
            save(socialComment);
            throw new ResourceNotFoundException(Constant.FAILURE);
        }
    }

    private boolean deleteSocialComment(String commentId) {
        return delete(commentId);
    }

    private boolean updateSocialPost(String postId, String commentId, String repliedCommentId) {
        SocialPost socialPost = socialPostService.findById(postId);
        if (socialPost == null) {
            return false;
        }

        // Xóa bình luận khỏi tập hợp bình luận của bài đăng
        Set<String> commentSet = socialPost.getComments();
        commentSet.remove(commentId);
        socialPost.setComments(commentSet);

        // Nếu bình luận được trả lời, xóa id bình luận khỏi danh sách phản hồi của bình luận được trả lời
        if (repliedCommentId != null && !repliedCommentId.isEmpty()) {
            SocialComment repliedComment = findById(repliedCommentId);
            if (repliedComment != null) {
                List<String> repliesList = repliedComment.getReplies();
                if (repliesList != null) {
                    repliesList.remove(commentId);
                    repliedComment.setReplies(repliesList);
                    save(repliedComment);
                }
            }
        }

        return socialPostService.save(socialPost);
    }

    public List<SocialComment> findAll() {
        return this.socialCommentRepository.findAll();
    }

    public SocialComment save(SocialComment socialComment) {
        try {
            return this.socialCommentRepository.save(socialComment);
        } catch (Exception e) {
            return null;
        }
    }

    public List<SocialComment> findAllById(String id) {
        return this.socialCommentRepository.findSocialCommentByPostId(id);
    }


    public boolean delete(String id) {
        try {
            this.socialCommentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public SocialComment findById(String id) {
        return this.socialCommentRepository.findSocialCommentById(new ObjectId(id));
    }
}
