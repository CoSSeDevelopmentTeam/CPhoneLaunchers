package net.comorevi.cpapp.launchers;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.data.StringsData;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ModalResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity;
import net.comorevi.cphone.presenter.SharingData;

public class LaunchTrashCanActivity extends ModalActivity {

    private Bundle bundle;
    private Item[] arm = new Item[4];
    private Item offhand;

    public LaunchTrashCanActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("tc_title"));
        this.setContent(bundle.getString("tc_content"));
        this.setButton1Text(bundle.getString("tc_button1"));
        this.setButton2Text(bundle.getString("tc_button2"));

        for (int i = 0; i < 4; i++) {
            arm[i] = bundle.getCPhone().getPlayer().getInventory().getArmorItem(i);
        }
        bundle.getCPhone().getPlayer().getInventory().clearAll();
        offhand = bundle.getCPhone().getPlayer().getOffhandInventory().getItem(0);
    }

    @Override
    public ReturnType onStop(Response response) {
        ModalResponse modalResponse = (ModalResponse) response;
        if (modalResponse.isButton1Clicked()) {
            clearInv(modalResponse.getPlayer());
            bundle.getCPhone().setHomeMessage(bundle.getString("tc_home_message"));
            return ReturnType.TYPE_END;
        } else {
            new MainActivity(getManifest()).start(modalResponse.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }
    }

    private void clearInv(Player player) {
        player.getInventory().clearAll();

        player.getOffhandInventory().addItem(offhand);
        player.getInventory().sendArmorContents(player);

        Item cphoneItem = Item.get(SharingData.triggerItemId);
        cphoneItem.setCustomName(StringsData.get(player, "cphone_title"));
        player.getInventory().setItem(0, cphoneItem);
    }

}
