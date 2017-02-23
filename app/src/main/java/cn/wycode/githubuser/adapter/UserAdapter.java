package cn.wycode.githubuser.adapter;

import android.content.Context;

import java.util.List;

import cn.wycode.githubuser.R;
import cn.wycode.githubuser.adapter.baseadapter.WyCommonAdapter;
import cn.wycode.githubuser.adapter.baseadapter.WyViewHolder;
import cn.wycode.githubuser.model.User;

/**
 * Created by wy
 * on 2017/1/5.
 */

public class UserAdapter extends WyCommonAdapter<User> {


    public UserAdapter(Context context, List<User> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(WyViewHolder holder, User user) {
        holder.setTextViewText(R.id.tv_item_name,user.login);
        holder.setTextViewText(R.id.tv_item_language,user.favo_languages);
        holder.setImageViewImage(R.id.iv_item_avatar,user.avatar_url);
    }
}
