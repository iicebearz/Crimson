package io.iicebear.crimson.fps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class MainActivity extends Activity {

    private TextView moduleStatus;
    private View statusDot;
    private TextView moduleVersion;
    private TextView heroAppCount;

    private TextView deviceProcessor, deviceAndroid, deviceAbi, deviceModel, deviceName, buildNumber, securityPatch;

    private View supportedCard;
    private MaterialButton manageButton;

    private View dashboardView, userView;
    private View navDashboardItem, navUserItem;
    private ImageView navDashboardIcon, navUserIcon;
    private TextView navDashboardLabel, navUserLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        setDeviceInformation();
        setModuleStatus();
        setupLinks();
        setupTopBar();
        setupBottomNav();
        runEntranceAnimations();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAppCount();
    }

    private void bindViews() {
        moduleStatus = findViewById(R.id.module_status);
        statusDot = findViewById(R.id.statusDot);
        moduleVersion = findViewById(R.id.moduleVersion);
        heroAppCount = findViewById(R.id.supportedAppsTitle);

        deviceProcessor = findViewById(R.id.Processor);
        deviceName = findViewById(R.id.deviceName);
        deviceAndroid = findViewById(R.id.systemVersion);
        deviceAbi = findViewById(R.id.systemAbi);
        deviceModel = findViewById(R.id.device);
        buildNumber = findViewById(R.id.buildNumber);
        securityPatch = findViewById(R.id.securityPatch);

        supportedCard = findViewById(R.id.supportedCard);
        manageButton = findViewById(R.id.manageButton);

        dashboardView = findViewById(R.id.dashboardView);
        userView = findViewById(R.id.userView);
        navDashboardItem = findViewById(R.id.navDashboardItem);
        navUserItem = findViewById(R.id.navUserItem);
        navDashboardIcon = findViewById(R.id.navDashboardIcon);
        navUserIcon = findViewById(R.id.navUserIcon);
        navDashboardLabel = findViewById(R.id.navDashboardLabel);
        navUserLabel = findViewById(R.id.navUserLabel);
    }

    private void setDeviceInformation() {
        moduleVersion.setText(BuildConfig.VERSION_NAME + " (v" + BuildConfig.VERSION_CODE + ")");
        ((TextView) findViewById(R.id.profileVersion))
                .setText(BuildConfig.VERSION_NAME + " (v" + BuildConfig.VERSION_CODE + ")");

        String hw = Build.HARDWARE.toLowerCase();
        String soc = hw.contains("qcom") || hw.contains("sm8") || hw.contains("sm7") ? "Qualcomm Snapdragon"
                : hw.contains("mt") || hw.contains("mediatek") || hw.contains("dimensity") ? "MediaTek"
                : hw.contains("kirin") ? "HiSilicon Kirin"
                : hw.contains("exynos") ? "Samsung Exynos"
                : hw.contains("tensor") ? "Google Tensor"
                : hw.contains("apple") ? "Apple Silicon" : "Unknown SoC";
        deviceProcessor.setText(soc + " (" + Build.BOARD + ")");
        deviceAndroid.setText(Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");
        deviceAbi.setText(Build.SUPPORTED_ABIS.length > 0 ? Build.SUPPORTED_ABIS[0] : "N/A");
        deviceModel.setText(Build.MANUFACTURER + " " + Build.MODEL);
        deviceName.setText(Build.DEVICE);
        buildNumber.setText(Build.DISPLAY);
        securityPatch.setText(Build.VERSION.SECURITY_PATCH);
    }

    private void refreshAppCount() {
        SpoofCatalog.fromBlob(CatalogStore.load(this));
        SpoofCatalog.fromRemovedBlob(CatalogStore.loadRemoved(this));
        DeviceSpoof.fromBlob(CatalogStore.loadDevices(this));
        heroAppCount.setText(getString(R.string.apps_supported_count, SpoofCatalog.packageCount()));
    }

    private void setModuleStatus() {
        boolean active = isModuleActivated();
        moduleStatus.setText(active ? getString(R.string.status_active) : getString(R.string.status_inactive));
        statusDot.setBackgroundResource(active ? R.drawable.dot_active : R.drawable.dot_inactive);
    }

    public static boolean isModuleActivated() {
        return false;
    }

    private void setupTopBar() {
        findViewById(R.id.actionRefresh).setOnClickListener(v -> {
            setModuleStatus();
            refreshAppCount();
            Toast.makeText(this, "Status modul diperbarui", Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.actionSettings).setOnClickListener(v -> openLsposed());
    }

    private void setupBottomNav() {
        navDashboardItem.setOnClickListener(v -> showPage(true));
        navUserItem.setOnClickListener(v -> showPage(false));
    }

    private void showPage(boolean dashboard) {
        dashboardView.setVisibility(dashboard ? View.VISIBLE : View.GONE);
        userView.setVisibility(dashboard ? View.GONE : View.VISIBLE);

        navDashboardItem.setBackgroundResource(dashboard ? R.drawable.nav_item_selected : 0);
        navUserItem.setBackgroundResource(dashboard ? 0 : R.drawable.nav_item_selected);

        navDashboardIcon.setColorFilter(dashboard ? getColor(R.color.primary) : getColor(R.color.text_secondary));
        navUserIcon.setColorFilter(dashboard ? getColor(R.color.text_secondary) : getColor(R.color.primary));
        navDashboardLabel.setTextColor(dashboard ? getColor(R.color.text_primary) : getColor(R.color.text_secondary));
        navUserLabel.setTextColor(dashboard ? getColor(R.color.text_secondary) : getColor(R.color.text_primary));

        (dashboard ? dashboardView : userView).startAnimation(
                AnimationUtils.loadAnimation(this, R.anim.fade_in));
    }

    private void setupLinks() {
        setupLink(R.id.githubLink, "https://github.com/iicebearz");
        setupLink(R.id.telegramLink, "https://t.me/iancloudID");

        manageButton.setOnClickListener(v -> showAddPackageDialog());
        supportedCard.setOnClickListener(v -> startActivity(new Intent(this, ManageActivity.class)));

        View updateRow = findViewById(R.id.updateLink);
        if (updateRow != null) updateRow.setOnClickListener(v -> showUpdateDialog());
    }

    private void setupLink(int viewId, String url) {
        View view = findViewById(viewId);
        if (view == null) return;
        view.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "Tidak ada aplikasi untuk membuka tautan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openLsposed() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("org.lsposed.manager");
        if (intent == null) {
            intent = getPackageManager().getLaunchIntentForPackage("org.lsposed.daemon");
        }
        if (intent != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "LSPosed tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUpdateDialog() {
        TextView statusView = new TextView(this);
        statusView.setText("Checking for updates...");
        statusView.setTextColor(Ui.themeColor(this, com.google.android.material.R.attr.colorOnSurface));
        statusView.setTextSize(15);
        statusView.setPadding(60, 40, 60, 20);

        Dialog d = new AlertDialog.Builder(this)
                .setView(statusView)
                .setCancelable(false)
                .show();

        UpdateChecker.check(new UpdateChecker.Callback() {
            @Override
            public void onResult(String versionName, int versionCode, String apkUrl, String changelog) {
                d.dismiss();
                if (!(versionCode > BuildConfig.VERSION_CODE)) {
                    Toast.makeText(MainActivity.this, "Up to date", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = "v" + versionName + " available\n\n" + changelog;
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Update Available")
                        .setMessage(msg)
                        .setPositiveButton("Download", (dialog, which) -> downloadAndInstall(apkUrl))
                        .setNegativeButton("Later", null)
                        .show();
            }

            @Override
            public void onError(String error) {
                d.dismiss();
                Toast.makeText(MainActivity.this, "Update check failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadAndInstall(String apkUrl) {
        Toast.makeText(this, "Downloading...", Toast.LENGTH_SHORT).show();
        long downloadId = UpdateChecker.downloadApk(this, apkUrl);

        registerReceiver(new android.content.BroadcastReceiver() {
            @Override
            public void onReceive(android.content.Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (id != downloadId) return;
                context.unregisterReceiver(this);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                Uri uri = dm.getUriForDownloadedFile(id);
                if (uri == null) return;
                runOnUiThread(() -> installApk(uri));
            }
        }, new android.content.IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private void installApk(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(uri, "application/vnd.android.package-archive")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (!getPackageManager().canRequestPackageInstalls()) {
                startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                        Uri.parse("package:" + getPackageName())));
                return;
            }
        }
        startActivity(intent);
    }

    @android.annotation.SuppressLint("DefaultLocale")
    private void showAddPackageDialog() {
        View content = getLayoutInflater().inflate(R.layout.popup_add_package, null);
        EditText pkgInput = content.findViewById(R.id.pkgInput);
        Spinner deviceSpinner = content.findViewById(R.id.deviceSpinner);
        deviceSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, SpoofCatalog.deviceNames()));

        content.findViewById(R.id.addDeviceBtn).setOnClickListener(v -> {
            showAddDeviceDialog();
            deviceSpinner.setAdapter(new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, SpoofCatalog.deviceNames()));
        });

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.add_package_title)
                .setView(content)
                .setPositiveButton(R.string.btn_add, (d, w) -> {
                    String pkg = pkgInput.getText().toString().trim();
                    if (pkg.isEmpty()) {
                        Toast.makeText(this, R.string.empty_package_toast, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String device = SpoofCatalog.keyForLabel((String) deviceSpinner.getSelectedItem());
                    if (existsInCatalog(pkg)) {
                        String holder = SpoofCatalog.findDeviceForPackage(pkg);
                        if (holder == null) holder = device;
                        new AlertDialog.Builder(this)
                                .setTitle("Duplicate")
                                .setMessage("(" + pkg + ") sudah ada di (" + SpoofCatalog.label(holder) + ")")
                                .setPositiveButton("OK", null)
                                .show();
                        return;
                    }
                    SpoofCatalog.addPackage(device, pkg);
                    CatalogStore.save(this, SpoofCatalog.toBlob());
                    refreshAppCount();
                    pkgInput.setText("");
                    new AlertDialog.Builder(this)
                            .setTitle("Success")
                            .setMessage("(" + pkg + ") berhasil di input ke (" + SpoofCatalog.label(device) + ")")
                            .setPositiveButton("OK", null)
                            .show();
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private boolean existsInCatalog(String pkg) {
        if (SpoofCatalog.exists(pkg)) return true;
        for (String s : getResources().getStringArray(R.array.scope)) {
            if (s.equals(pkg)) return true;
        }
        return false;
    }

    private void showAddDeviceDialog() {
        View content = getLayoutInflater().inflate(R.layout.popup_add_device, null);
        EditText nameInput = content.findViewById(R.id.deviceNameInput);
        EditText brandInput = content.findViewById(R.id.deviceBrandInput);
        EditText manufacturerInput = content.findViewById(R.id.deviceManufacturerInput);
        EditText modelInput = content.findViewById(R.id.deviceModelInput);
        EditText deviceInput = content.findViewById(R.id.deviceDeviceInput);

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.add_device_title)
                .setView(content)
                .setPositiveButton(R.string.btn_add, (d, w) -> {
                    String name = nameInput.getText().toString().trim();
                    String brand = brandInput.getText().toString().trim();
                    String manufacturer = manufacturerInput.getText().toString().trim();
                    String model = modelInput.getText().toString().trim();
                    String device = deviceInput.getText().toString().trim();
                    if (name.isEmpty() || brand.isEmpty() || manufacturer.isEmpty() || model.isEmpty() || device.isEmpty()) {
                        Toast.makeText(this, R.string.device_empty, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!SpoofCatalog.registerDevice(name)) {
                        Toast.makeText(this, R.string.device_exists, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    java.util.Map<String, String> props = new java.util.HashMap<>();
                    props.put("BRAND", brand);
                    props.put("MANUFACTURER", manufacturer);
                    props.put("MODEL", model);
                    props.put("DEVICE", device);
                    DeviceSpoof.addCustom(name, props);
                    CatalogStore.saveDevices(this, SpoofCatalog.devicesToBlob());
                    Toast.makeText(this, R.string.device_added, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private void runEntranceAnimations() {
        int[] ids = {R.id.heroCard, R.id.packageCard, R.id.deviceCard, R.id.supportedCard};
        for (int id : ids) {
            View v = findViewById(id);
            if (v != null) v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        }
    }
}