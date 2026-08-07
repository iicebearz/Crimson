package io.iicebear.crimson.fps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

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

    private int themeColor(int attrRes) {
        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(attrRes, tv, true);
        return tv.data;
    }

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
        refreshAppCount();

        deviceProcessor.setText(DeviceInfo.soc() + " (" + DeviceInfo.board() + ")");
        deviceAndroid.setText(DeviceInfo.android());
        deviceAbi.setText(DeviceInfo.abi());
        deviceModel.setText(DeviceInfo.model());
        deviceName.setText(DeviceInfo.deviceName());
        buildNumber.setText(DeviceInfo.buildNumber());
        securityPatch.setText(DeviceInfo.securityPatch());
    }

    private void refreshAppCount() {
        SpoofCatalog.fromBlob(CatalogStore.load(this));
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
        supportedCard.setOnClickListener(v -> showSupportedAppsDialog());

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
        statusView.setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface));
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
                if (!UpdateChecker.isNewer(versionCode, BuildConfig.VERSION_CODE)) {
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

        new Thread(() -> {
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            boolean complete = false;
            while (!complete) {
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
                Cursor cursor = dm.query(query);
                if (cursor == null) break;
                try {
                    if (cursor.moveToFirst()) {
                        int status = cursor.getInt(
                                cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                        if (status == DownloadManager.STATUS_SUCCESSFUL) { complete = true; }
                        else if (status == DownloadManager.STATUS_FAILED) { return; }
                    }
                } finally { cursor.close(); }
            }

            File file = getDownloadedFile(dm, downloadId);
            if (file != null && file.exists()) runOnUiThread(() -> installApk(file));
        }).start();
    }

    private File getDownloadedFile(DownloadManager dm, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = dm.query(query);
        if (cursor == null) return null;
        try {
            if (!cursor.moveToFirst()) return null;
            String uriStr = cursor.getString(
                    cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
            Uri uri = Uri.parse(uriStr);
            if ("file".equals(uri.getScheme())) return new File(uri.getPath());
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File[] apkFiles = dir.listFiles(f -> f.getName().endsWith(".apk"));
            if (apkFiles == null || apkFiles.length == 0) return null;
            File latest = apkFiles[0];
            for (File f : apkFiles) {
                if (f.lastModified() > latest.lastModified()) latest = f;
            }
            return latest;
        } finally { cursor.close(); }
    }

    private void installApk(File file) {
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
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

        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.add_package_title)
                .setView(content)
                .setPositiveButton(R.string.btn_add, (d, w) -> {
                    String pkg = pkgInput.getText().toString().trim();
                    if (pkg.isEmpty()) {
                        Toast.makeText(this, R.string.empty_package_toast, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (SpoofCatalog.exists(pkg)) {
                        Toast.makeText(this, R.string.duplicate_toast, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String device = (String) deviceSpinner.getSelectedItem();
                    SpoofCatalog.addPackage(device, pkg);
                    CatalogStore.save(this, SpoofCatalog.toBlob());
                    refreshAppCount();
                    pkgInput.setText("");
                    Toast.makeText(this, R.string.added_toast, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private void showSupportedAppsDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_supported_apps);

        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9f);
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        LinearLayout container = dialog.findViewById(R.id.container);

        android.text.SpannableStringBuilder sb = new android.text.SpannableStringBuilder();
        int titleStart = sb.length();
        sb.append("Supported Apps by Spoofed Device\n\n");
        sb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), titleStart, sb.length(), 0);

        for (String device : SpoofCatalog.deviceNames()) {
            int devStart = sb.length();
            sb.append(device).append(":\n");
            sb.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), devStart, sb.length(), 0);

            for (String pkg : SpoofCatalog.packagesFor(device)) {
                sb.append("• ").append(pkg).append("\n");
            }
            sb.append("\n");
        }

        TextView content = new TextView(this);
        content.setText(sb.toString());
        content.setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface));
        content.setTextSize(14);
        container.addView(content);

        TextView closeBtn = new TextView(this);
        closeBtn.setText("Close");
        closeBtn.setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurfaceVariant));
        closeBtn.setTextSize(13);
        closeBtn.setPadding(0, 20, 0, 0);
        closeBtn.setGravity(Gravity.CENTER);
        closeBtn.setOnClickListener(v -> dialog.dismiss());
        container.addView(closeBtn);

        dialog.show();
    }

    private void runEntranceAnimations() {
        int[] ids = {R.id.heroCard, R.id.packageCard, R.id.deviceCard, R.id.supportedCard};
        for (int id : ids) {
            View v = findViewById(id);
            if (v != null) v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        }
    }
}