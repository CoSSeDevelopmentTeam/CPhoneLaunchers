package net.comorevi.cpapp.launchers;

import cn.nukkit.utils.TextFormat;
import net.comorevi.cosse.chestguard.util.DataCenter;
import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ModalResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity;

public class LaunchChestGuardActivity extends ModalActivity {

    private Bundle bundle;
    private CPhone cPhone;

    public LaunchChestGuardActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();
        this.setTitle(bundle.getString("cg_title"));
        this.setContent(bundle.getString("cg_content"));
        this.setButton1Text(bundle.getString("cg_button1"));
        this.setButton2Text(bundle.getString("cg_button2"));
    }

    @Override
    public ReturnType onStop(Response response) {
        ModalResponse modalResponse = (ModalResponse) response;
        if (modalResponse.isButton1Clicked()) {
            if (!DataCenter.existCmdQueue(modalResponse.getPlayer())) {
                DataCenter.addCmdQueue(modalResponse.getPlayer());
                modalResponse.getPlayer().sendMessage(TextFormat.GRAY + bundle.getString("prefix") + TextFormat.RESET + bundle.getString("cg_enabled"));
            } else {
                DataCenter.removeCmdQueue(modalResponse.getPlayer());
                modalResponse.getPlayer().sendMessage(TextFormat.GRAY + bundle.getString("prefix") + TextFormat.RESET + bundle.getString("cg_disabled"));
            }
            return ReturnType.TYPE_IGNORE;
        } else {
            new MainActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }
    }
}
