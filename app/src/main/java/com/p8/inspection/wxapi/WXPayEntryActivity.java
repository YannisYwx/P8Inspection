package com.p8.inspection.wxapi;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.p8.inspection.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * author : WX.Y
 * date : 2020/9/10 16:14
 * description :
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        api = WXAPIFactory.createWXAPI(this, "wxae7fab56f6fe2287");
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            int code = baseResp.errCode;
            if (code == BaseResp.ErrCode.ERR_OK) {
                //支付成功后的逻辑处理

            } else if (code == BaseResp.ErrCode.ERR_USER_CANCEL) {
                //用户取消
            } else if (code == BaseResp.ErrCode.ERR_COMM) {
                //支付错误
            }
            finish();

        }
    }
}

