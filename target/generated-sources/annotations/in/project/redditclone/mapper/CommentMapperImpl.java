package in.project.redditclone.mapper;

import in.project.redditclone.dto.CommentDto;
import in.project.redditclone.model.Comment;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-25T12:34:15+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto mapToDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto.CommentDtoBuilder commentDto = CommentDto.builder();

        commentDto.createDate( comment.getCreateDate() );
        commentDto.id( comment.getId() );
        commentDto.text( comment.getText() );

        commentDto.postId( comment.getPost().getPostId() );
        commentDto.userName( comment.getUser().getUserName() );

        return commentDto.build();
    }

    @Override
    public Comment map(CommentDto commentDto, Post post, User user) {
        if ( commentDto == null && post == null && user == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        if ( commentDto != null ) {
            comment.text( commentDto.getText() );
        }
        comment.post( post );
        comment.user( user );
        comment.createDate( java.time.Instant.now() );

        return comment.build();
    }
}
