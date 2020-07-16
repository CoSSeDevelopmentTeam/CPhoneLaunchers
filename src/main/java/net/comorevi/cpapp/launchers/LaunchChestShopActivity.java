package net.comorevi.cpapp.launchers;

import cn.nukkit.utils.TextFormat;
import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ModalResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity;
import net.comorevi.moneyschestshop.util.DataCenter;

public class LaunchChestShopActivity extends ModalActivity {

    private Bundle bundle;
    private CPhone cPhone;

    public LaunchChestShopActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();
        this.setTitle(bundle.getString("cs_title"));
        this.setContent(bundle.getString("cs_content"));
        this.setButton1Text(bundle.getString("cs_button1"));
        this.setButton2Text(bundle.getString("cs_button2"));
    }

    @Override
    public ReturnType onStop(Response response) {
        ModalResponse modalResponse = (ModalResponse) response;
        if (modalResponse.isButton1Clicked()) {
            if (!DataCenter.existsEditCmdQueue(modalResponse.getPlayer())) {
                DataCenter.addEditCmdQueue(modalResponse.getPlayer());
                modalResponse.getPlayer().sendMessage(TextFormat.GRAY + bundle.getString("prefix") + TextFormat.RESET + bundle.getString("cs_enabled"));
            } else {
                DataCenter.removeEditCmdQueue(modalResponse.getPlayer());
                modalResponse.getPlayer().sendMessage(TextFormat.GRAY + bundle.getString("prefix") + TextFormat.RESET + bundle.getString("cs_disabled"));
            }
            return ReturnType.TYPE_IGNORE;
        } else {
            new MainActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }
    }
}
