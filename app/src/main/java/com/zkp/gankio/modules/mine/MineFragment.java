package com.zkp.gankio.modules.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zkp.gankio.R;
import com.zkp.gankio.base.fragment.BaseFragment;
import com.zkp.gankio.modules.mine.collect.CollectActivity;
import com.zkp.gankio.modules.mine.history.HistoryActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine
 * @time: 2019/5/22 9:30
 * @description:
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineFragmentContract.View {

    @BindView(R.id.tvCollect)
    TextView mTvCollect;

    @BindView(R.id.tvHistory)
    TextView mTvHistory;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.tvCollect, R.id.tvHistory})
    void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tvCollect:
                //我的收藏
                intent = new Intent(getActivity(), CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.tvHistory:
                //干货历史
                intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
