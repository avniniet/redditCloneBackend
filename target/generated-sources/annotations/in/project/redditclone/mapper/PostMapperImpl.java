package in.project.redditclone.mapper;

import in.project.redditclone.dto.PostRequest;
import in.project.redditclone.dto.PostResponse;
import in.project.redditclone.model.Post;
import in.project.redditclone.model.Subreddit;
import in.project.redditclone.model.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-25T12:34:15+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20230218-1114, environment: Java 17.0.7 (Eclipse Adoptium)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Override
    public Post map(PostRequest postRequest, Subreddit subreddit, User user) {
        if ( postRequest == null && subreddit == null && user == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        if ( postRequest != null ) {
            post.description( postRequest.getDescription() );
            if ( postRequest.getPostId() != null ) {
                post.postId( postRequest.getPostId().longValue() );
            }
            post.postName( postRequest.getPostName() );
            post.url( postRequest.getUrl() );
        }
        post.subreddit( subreddit );
        post.user( user );
        post.createDate( java.time.Instant.now() );
        post.voteCount( 0 );

        return post.build();
    }

    @Override
    public PostResponse mapToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse.PostResponseBuilder postResponse = PostResponse.builder();

        postResponse.subredditName( postSubredditName( post ) );
        postResponse.userName( postUserUserName( post ) );
        postResponse.description( post.getDescription() );
        if ( post.getPostId() != null ) {
            postResponse.postId( post.getPostId().intValue() );
        }
        postResponse.postName( post.getPostName() );
        postResponse.url( post.getUrl() );
        postResponse.voteCount( post.getVoteCount() );

        postResponse.commentCount( commentCount(post) );
        postResponse.duration( getDuration(post) );

        return postResponse.build();
    }

    private String postSubredditName(Post post) {
        if ( post == null ) {
            return null;
        }
        Subreddit subreddit = post.getSubreddit();
        if ( subreddit == null ) {
            return null;
        }
        String name = subreddit.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String postUserUserName(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String userName = user.getUserName();
        if ( userName == null ) {
            return null;
        }
        return userName;
    }
}
