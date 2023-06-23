package com.cabin.oauth2.empty.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GitHubUserInfo {
    private String login;

    //github的userId
    @JsonProperty("id")
    private Long gitHubUserId;

    @JsonProperty("node_id")
    private String nodeId;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("gravatar_id")
    private String gravatarId;

    private String url;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("followers_url")
    private String followersUrl;

    @JsonProperty("following_url")
    private String followingUrl;

    @JsonProperty("gists_url")
    private String gistsUrl;

    @JsonProperty("starred_url")
    private String starredUrl;

    @JsonProperty("subscriptions_url")
    private String subscriptionsUrl;

    @JsonProperty("organizations_url")
    private String organizationsUrl;

    @JsonProperty("repos_url")
    private String reposUrl;

    @JsonProperty("events_url")
    private String eventsUrl;

    @JsonProperty("received_events_url")
    private String receivedEventsUrl;

    private String type;

    @JsonProperty("site_admin")
    private Boolean siteAdmin;

    private String name;

    private String company;

    private String blog;

    private String location;

    private String email;

    private String hireable;

    private String bio;

    @JsonProperty("twitter_username")
    private String twitterUsername;

    @JsonProperty("public_repos")
    private Long publicRepos;

    @JsonProperty("public_gists")
    private Long publicGists;

    private Long followers;

    private Long following;

    private Date created_at;

    private Date updated_at;

}
