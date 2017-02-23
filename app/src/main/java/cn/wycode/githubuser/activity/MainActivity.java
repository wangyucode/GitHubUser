package cn.wycode.githubuser.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.wycode.githubuser.BaseActivity;
import cn.wycode.githubuser.Constants;
import cn.wycode.githubuser.R;
import cn.wycode.githubuser.adapter.UserAdapter;
import cn.wycode.githubuser.model.Repo;
import cn.wycode.githubuser.model.User;
import cn.wycode.githubuser.model.UserList;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_main)
    ListView lv_main;

    List<User> users = new ArrayList<>();
    UserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewWithDefaultTitle(R.layout.content_main, "Github User");
    }

    @Override
    protected void initView() {
        //设置状态栏
        mIvTitleBack.setVisibility(View.GONE);
        mIvTitleRight.setImageResource(R.drawable.ic_action_search);
        mIvTitleRight.setVisibility(View.VISIBLE);

        setOnClickListeners(this, mIvTitleRight);

        adapter = new UserAdapter(this, users, R.layout.item_user);
        lv_main.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        final EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("please input Github username")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(editText.getText()))
                            queryUser(editText.getText().toString());
                    }
                }).create();
        dialog.show();
    }

    private void queryUser(String text) {
        final AlertDialog loading = new AlertDialog.Builder(this)
                .setTitle("loading...")
                .setView(new ProgressBar(this))
                .create();
        loading.show();
        OkGo.get(Constants.URL_QUERY_USER + text).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    users = JSON.parseObject(s, UserList.class).items;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (users.size() > 0) {
                    adapter.setList(users);
                    getRepos();
                }
                loading.dismiss();
            }
        });
    }

    private void getRepos() {
        for (int i = 0; i < users.size(); i++) {
            OkGo.get(users.get(i).repos_url+"?client_id=2c49a0e4ef9ffa96e4c3&client_secret=c0e70e7d6474b3ed9a60719fe3b8515e16bdf2e4").execute(
                    new StringCallbackWithIndex(i)
            );
        }

    }

    class StringCallbackWithIndex extends StringCallback{
        public StringCallbackWithIndex(int index) {
            this.index = index;
        }

        int index;

        @Override
        public void onSuccess(String s, Call call, Response response) {
            List<Repo> repos = null;
            try {
                repos = JSON.parseArray(s, Repo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (repos != null && repos.size() > 0) {
                users.get(index).favo_languages = findFavoLanguages(repos);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private String findFavoLanguages(List<Repo> repos) {
        ArrayList<Language> languages = new ArrayList<>();
        for (Repo r : repos) {
            if (r.language == null)
                continue;
            if (languages.size() == 0) {
                languages.add(new Language(r.language, 1));
            } else {
                boolean isExist = false;
                for (int i=  0;i<languages.size();i++) {
                    if (languages.get(i).language.equals(r.language)) {
                        languages.get(i).count++;
                        isExist = true;
                        break;
                    }
                }
                if(!isExist) {
                    languages.add(new Language(r.language, 1));
                }
            }
        }
        Collections.sort(languages, new Comparator<Language>() {
            @Override
            public int compare(Language o1, Language o2) {
                return o2.count - o1.count;
            }
        });
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<languages.size();i++){
            builder.append(languages.get(i).language+",");
        }
        return builder.toString();
    }

    class Language {
        String language;
        int count;

        public Language(String language, int count) {
            this.language = language;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Language{" +
                    "language='" + language + '\'' +
                    '}';
        }
    }
}
