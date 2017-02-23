package cn.wycode.githubuser.model;

import java.util.List;

/**
 * Created by wy
 * on 2017/2/23.
 */

public class UserList {
    /*{"total_count": 22268,
            "incomplete_results": false,
            "items": [
        {
            "login": "test",
                "id": 383316,
                "avatar_url": "https://avatars.githubusercontent.com/u/383316?v=3",
                "gravatar_id": "",
                "url": "https://api.github.com/users/test",
                "html_url": "https://github.com/test",
                "followers_url": "https://api.github.com/users/test/followers",
                "following_url": "https://api.github.com/users/test/following{/other_user}",
                "gists_url": "https://api.github.com/users/test/gists{/gist_id}",
                "starred_url": "https://api.github.com/users/test/starred{/owner}{/repo}",
                "subscriptions_url": "https://api.github.com/users/test/subscriptions",
                "organizations_url": "https://api.github.com/users/test/orgs",
                "repos_url": "https://api.github.com/users/test/repos",
                "events_url": "https://api.github.com/users/test/events{/privacy}",
                "received_events_url": "https://api.github.com/users/test/received_events",
                "type": "User",
                "site_admin": false,
                "score": 66.26666
        },
        */
    public List<User> items;

}
