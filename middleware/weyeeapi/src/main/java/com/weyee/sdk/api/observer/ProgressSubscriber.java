package com.weyee.sdk.api.observer;

import android.content.Context;
import com.weyee.sdk.api.observer.listener.ProgressAble;
import io.reactivex.disposables.Disposable;

/**
 * <p>
 *
 * @author wuqi
 * @describe ...
 * @date 2018/12/7 0007
 */
public abstract class ProgressSubscriber<T> extends RxSubscriber<T> {

    private ProgressAble progressAble;

    public ProgressSubscriber() {
    }

    public ProgressSubscriber(ProgressAble progressAble) {
        this.progressAble = progressAble;
    }

    public ProgressSubscriber(Context progressAble) {
        if (progressAble instanceof ProgressAble) {
            this.progressAble = (ProgressAble) progressAble;
        }
    }

    @Override
    public void doOnSubscribe(Disposable d) {
        super.doOnSubscribe(d);
        if (progressAble != null) {
            progressAble.showProgress();
        }
    }

    @Override
    public void doOnCompleted() {
        super.doOnCompleted();
        if (progressAble != null) {
            progressAble.hideProgress();
        }
    }
}
