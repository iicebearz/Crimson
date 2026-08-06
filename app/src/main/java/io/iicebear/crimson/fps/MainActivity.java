package io.iicebear.crimson.fps;

import io.iicebear.crimson.fps.BuildConfig;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends Activity {

    private TextView moduleStatus;
    private ImageView moduleStatusIcon;

    private int themeColor(int attrRes) {
        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(attrRes, tv, true);
        return tv.data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setDeviceInformation();
        setModuleStatusUI();
        setupLinks();
    }

    private void initializeViews() {
        findViewById(R.id.supportLink).setOnClickListener(v -> showSupportedAppsDialog());
        moduleStatus = findViewById(R.id.module_status);
        moduleStatusIcon = findViewById(R.id.moduleStatusIcon);
    }

    private void setDeviceInformation() {
        setTextSafely(R.id.moduleVersion, BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")");
        setTextSafely(R.id.systemVersion, Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ")");
        setTextSafely(R.id.systemAbi, Build.SUPPORTED_ABIS.length > 0 ? Build.SUPPORTED_ABIS[0] : "N/A");
        setTextSafely(R.id.deviceName, Build.BRAND + " " + Build.BOARD);
        setTextSafely(R.id.device, Build.MANUFACTURER + " " + Build.MODEL);
        setTextSafely(R.id.buildNumber, Build.ID + "\n(Security Patch: " + Build.VERSION.SECURITY_PATCH + ")");
        setTextSafely(R.id.baseOS, Build.VERSION.BASE_OS != null ? Build.VERSION.BASE_OS : "N/A");
        setTextSafely(R.id.Processor, getReadableSoC() + " (" + Build.HARDWARE + ")");
        setTextSafely(R.id.compatibleApps, "Aplikasi Kompatibel: " + SpoofCatalog.packageCount());
    }

    private String getReadableSoC() {
        String hw = Build.HARDWARE.toLowerCase();
        if (hw.contains("qcom") || hw.contains("sm8") || hw.contains("sm7")) return "Qualcomm Snapdragon";
        if (hw.contains("mt") || hw.contains("mediatek") || hw.contains("dimensity")) return "MediaTek";
        if (hw.contains("kirin")) return "HiSilicon Kirin";
        if (hw.contains("exynos")) return "Samsung Exynos";
        if (hw.contains("tensor")) return "Google Tensor";
        if (hw.contains("apple")) return "Apple Silicon";
        return "Unknown SoC";
    }

    private void setTextSafely(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (tv != null) {
            tv.setText(text != null && !text.isEmpty() ? text : "N/A");
        }
    }

	public static boolean isModuleActivated() {
    	return false;
	}

	private void setModuleStatusUI() {
    	boolean activated = isModuleActivated();
    	moduleStatus.setText(activated ? "Modul aktif" : "Modul tidak aktif");
    	moduleStatusIcon.setImageResource(
        	activated ? R.drawable.ic_check_active : R.drawable.ic_check_inactive
    	);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getIcon() != null) item.getIcon().setTint(themeColor(android.R.attr.textColorPrimary));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            refreshModuleStatus();
            Toast.makeText(this, "Status modul diperbarui", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.action_lsposed) {
            openLsposed();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void refreshModuleStatus() {
        setModuleStatusUI();
        setDeviceInformation();
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

        TextView title = new TextView(this);
        title.setText("Supported Apps by Spoofed Device");
        title.setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface));
        title.setTextSize(20);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setPadding(0, 16, 0, 12);
        title.setGravity(Gravity.CENTER);
        container.addView(title);

        for (String device : SpoofCatalog.deviceNames()) {
            String[] pkgs = SpoofCatalog.packagesFor(device);

            TextView header = new TextView(this);
            header.setText(device + ":");
            header.setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface));
            header.setTextSize(18);
            header.setTypeface(null, android.graphics.Typeface.BOLD);
            header.setPadding(0, 12, 0, 4);
            container.addView(header);

            StringBuilder sb = new StringBuilder();
            for (String pkg : pkgs) {
                sb.append("• ").append(pkg).append("\n");
            }
            if (sb.length() > 0) sb.setLength(sb.length() - 1);

            TextView list = new TextView(this);
            list.setText(sb.toString());
            list.setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface));
            list.setTextSize(14);
            list.setPadding(16, 0, 0, 8);
            container.addView(list);
        }

        MaterialButton closeBtn = new MaterialButton(this);
        closeBtn.setText("Close");
        closeBtn.setTextColor(themeColor(com.google.android.material.R.attr.colorOnPrimary));
        closeBtn.setTypeface(null, android.graphics.Typeface.BOLD);
        closeBtn.setAllCaps(false);
        closeBtn.setPadding(30, 14, 30, 14);
        closeBtn.setOnClickListener(v -> dialog.dismiss());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.topMargin = 24;
        lp.gravity = Gravity.CENTER_HORIZONTAL;
        closeBtn.setLayoutParams(lp);
        container.addView(closeBtn);

        dialog.show();
    }

    private void setupLinks() {
        setupLink(R.id.githubLink, "https://github.com/iicebearz");
        setupLink(R.id.telegramLink, "https://t.me/iancloudID");
        setupLink(R.id.tusukikanLink, "https://telegra.ph/Iam-a-IceBearr-02-02");
    }

    private void setupLink(int viewId, String url) {
        TextView view = findViewById(viewId);
        if (view != null) {
            view.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Tidak ada aplikasi untuk membuka tautan", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}