package net.comorevi.cpapp.launchers;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.np.moneys.jobs.form.FormHandler;

public class MainActivity extends ListActivity {

    private Bundle bundle;

    public MainActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("main_title"));
        this.setContent(bundle.getString("main_content"));
        Button[] buttons = {
                new Button(bundle.getString("main_button0")), // ChestGuard
                new Button(bundle.getString("main_button1")), // ChestShop
                new Button(bundle.getString("main_button2")), // MoneySJobs
                new Button(bundle.getString("main_button3")) // TrashCan
        };
        this.addButtons(buttons);
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        switch (listResponse.getButtonIndex()) {
            case 0:
                new LaunchChestGuardActivity(getManifest()).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            case 1:
                new LaunchChestShopActivity(getManifest()).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            case 2:
                FormHandler.getInstance().sendJobsHome(listResponse.getPlayer(), FormHandler.EnumPlayerClient.CPHONE);
                return ReturnType.TYPE_IGNORE;
            case 3:
                new LaunchTrashCanActivity(getManifest()).start(bundle);
                return ReturnType.TYPE_CONTINUE;
            default:
                return ReturnType.TYPE_END;
        }
    }
}
