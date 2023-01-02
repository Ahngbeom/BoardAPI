package com.perfect.community.api.service.post;

import com.google.common.base.Preconditions;
import com.perfect.community.api.dto.post.PostExtractionDTO;
import com.perfect.community.api.entity.post.PostEntity;
import com.perfect.community.api.mapper.user.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.perfect.community.api.dto.post.PostDTO;
import com.perfect.community.api.mapper.post.PostMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private static final Logger log = LogManager.getLogger(PostServiceImpl.class);
    private final PostMapper postMapper;
    private final UsersMapper usersMapper;
    private final PostUtils postUtils;

    @Override
    public List<PostDTO> getPostList(PostExtractionDTO.List postListOptions) {
        List<PostEntity> postEntities = postMapper.selectPostList(postListOptions);
        return postEntities.stream().map(this::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public PostDTO getInfoByPno(long pno) {
        Preconditions.checkState(pno > 0, "Invalid post no.");
        PostEntity postEntity = postMapper.selectPostInfoByPno(pno);
        return postEntity != null ? entityToDTO(postEntity) : null;
    }

    @Override
    public void registration(String userId, PostDTO postDTO) throws RuntimeException {
        Preconditions.checkNotNull(postDTO, "PostDTO must not be null.");
        Preconditions.checkState(postDTO.getBoardNo() > 0, "Invalid board no. [boardNo] must be greater then zero");
        Preconditions.checkState(postDTO.getType() != null, "[type] must not be null.");
        Preconditions.checkState(postDTO.getTitle() != null, "[title] must not be null.");
        Preconditions.checkState(postDTO.getContents() != null, "[contents] must not be null.");
        postDTO.setWriter(Preconditions.checkNotNull(userId, "[writer] must not be null."));
        if (postMapper.insertPost(postDTO) != 1)
            throw new RuntimeException("Failed to post registration.");
    }

    @Override
    public void modification(long postNo, String userId, PostDTO postDTO) {
        Preconditions.checkState(postNo > 0, "Invalid post no. [pno] must be greater than zero.");
        postDTO.setPno(postNo);
//        Preconditions.checkState(postUtils.checkPostVerification(userId, postNo), "You do not have permission to modify the post.");
        Preconditions.checkState(postDTO.getBoardNo() > 0, "Invalid board no. [boardNo] must be greater than zero.");
        Preconditions.checkState(postDTO.getType() != null, "[type] must not be null.");
        Preconditions.checkState(postDTO.getTitle() != null, "[title] must not be null.");
        Preconditions.checkState(postDTO.getContents() != null, "[contents] must not be null.");
        Preconditions.checkState(userId != null, "[contents] must not be null.");
        postDTO.setWriter(userId);
        if (postMapper.updatePost(postDTO) != 1) {
            throw new RuntimeException("Failed to post modification.");
        }
    }

    @Override
    public void remove(String userId, long pno) {
        Preconditions.checkState(pno > 0, "Invalid post no.");
        Preconditions.checkState(postUtils.checkPostVerification(userId, pno), "You do not have permission to remove the post.");
        postMapper.deletePost(pno);
    }

    @Override
    public boolean isWriter(long pno, String userId) {
        return postMapper.isWriter(pno, userId);
    }

    public PostDTO entityToDTO(PostEntity entity) {
        return PostDTO.builder()
                .pno(entity.getPno())
                .boardNo(entity.getBoardNo())
                .type(entity.getType())
                .writer(entity.getWriter())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .regDate(entity.getRegDate())
                .updateDate(entity.getUpdateDate())
                .build();
    }

}
