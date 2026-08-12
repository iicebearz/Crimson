package io.iicebear.crimson.fps;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ManageActivity extends Activity {

    private ListView list;
    private com.google.android.material.appbar.MaterialToolbar toolbar;
    private EditText searchInput;

    private final Set<String> expandedDevices = new java.util.HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        list = findViewById(R.id.container);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.inflateMenu(R.menu.menu_manage);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_search) {
                toggleSearch();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        SpoofCatalog.fromBlob(CatalogStore.load(this));
        SpoofCatalog.fromRemovedBlob(CatalogStore.loadRemoved(this));
        DeviceSpoof.fromBlob(CatalogStore.loadDevices(this));
        toolbar.setSubtitle(SpoofCatalog.deviceNames().length + " Device · "
                + SpoofCatalog.packageCount() + " Package");
        renderList(searchInput != null ? searchInput.getText().toString() : "");
    }

    private void toggleSearch() {
        if (searchInput != null) {
            searchInput.setText("");
            toolbar.removeView(searchInput);
            searchInput = null;
            toolbar.setTitle(R.string.manage_title);
            return;
        }
        searchInput = new EditText(this);
        searchInput.setBackgroundResource(R.drawable.popup_search_bg);
        searchInput.setHint(R.string.search_hint);
        searchInput.setTextColor(themeColor(com.google.android.material.R.attr.colorOnSurface));
        searchInput.setHintTextColor(themeColor(com.google.android.material.R.attr.colorOnSurfaceVariant));
        searchInput.setTextSize(14);
        searchInput.setPadding(dp(12), 0, dp(12), 0);
        searchInput.setSingleLine(true);
        searchInput.addTextChangedListener(new android.text.TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(android.text.Editable s) {
                renderList(s.toString());
            }
        });
        toolbar.setTitle(null);
        toolbar.addView(searchInput, new com.google.android.material.appbar.MaterialToolbar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        searchInput.requestFocus();
        android.view.inputmethod.InputMethodManager imm =
                (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(searchInput, 0);
    }

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }

    private void persist() {
        CatalogStore.save(this, SpoofCatalog.toBlob());
        CatalogStore.saveRemoved(this, SpoofCatalog.removedToBlob());
        refresh();
    }

    private static class PkgRow {
        final String device;
        final String pkg;
        final boolean removed;
        final boolean matched;
        PkgRow(String device, String pkg, boolean removed, boolean matched) {
            this.device = device;
            this.pkg = pkg;
            this.removed = removed;
            this.matched = matched;
        }
    }

    private static class HeaderRow {
        final String device;
        final int count;
        final boolean matched;
        HeaderRow(String device, int count, boolean matched) {
            this.device = device;
            this.count = count;
            this.matched = matched;
        }
    }

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_DELETED_HEADER = 1;
    private static final int TYPE_ROW = 2;

    private void renderList(final String query) {
        final String q = query == null ? "" : query.trim().toLowerCase();
        final boolean searching = !q.isEmpty();
        final List<Object> items = new java.util.ArrayList<>();
        for (String device : SpoofCatalog.deviceNames()) {
            String label = SpoofCatalog.label(device);
            String[] pkgs = SpoofCatalog.packagesFor(device);
            boolean deviceMatched = searching && label.toLowerCase().contains(q);
            if (searching) {
                items.add(new HeaderRow(device, pkgs.length, deviceMatched));
                for (String p : pkgs) {
                    items.add(new PkgRow(device, p, false, deviceMatched || p.toLowerCase().contains(q)));
                }
            } else {
                items.add(new HeaderRow(device, pkgs.length, false));
                if (expandedDevices.contains(device)) {
                    for (String p : pkgs) items.add(new PkgRow(device, p, false, false));
                }
            }
        }

        Map<String, List<String>> removed = SpoofCatalog.removedEntries();
        if (!removed.isEmpty()) {
            boolean deletedHeader = false;
            for (Map.Entry<String, List<String>> e : removed.entrySet()) {
                String label = SpoofCatalog.label(e.getKey());
                for (String p : e.getValue()) {
                    if (searching && !label.toLowerCase().contains(q) && !p.toLowerCase().contains(q)) continue;
                    if (!deletedHeader) {
                        items.add(null);
                        deletedHeader = true;
                    }
                    items.add(new PkgRow(e.getKey(), p, true, false));
                }
            }
        }

        list.setAdapter(new android.widget.BaseAdapter() {
            @Override
            public int getCount() {
                return items.size();
            }

            @Override
            public Object getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public int getViewTypeCount() {
                return 3;
            }

            @Override
            public int getItemViewType(int position) {
                Object item = items.get(position);
                if (item == null) return TYPE_DELETED_HEADER;
                if (item instanceof HeaderRow) return TYPE_HEADER;
                return TYPE_ROW;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Object item = items.get(position);
                if (item == null) {
                    TextView header = convertView instanceof TextView ? (TextView) convertView
                            : new TextView(ManageActivity.this);
                    header.setText(getString(R.string.title_deleted_section));
                    header.setTextColor(themeColor(com.google.android.material.R.attr.colorPrimary));
                    header.setTextSize(13);
                    header.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
                    header.setPadding(16, 24, 16, 8);
                    return header;
                }
                if (item instanceof HeaderRow) {
                    final HeaderRow row = (HeaderRow) item;
                    TextView header = convertView instanceof TextView ? (TextView) convertView
                            : new TextView(ManageActivity.this);
                    header.setText((searching || expandedDevices.contains(row.device) ? "▾ " : "▸ ")
                            + SpoofCatalog.label(row.device) + " (" + row.count + ")");
                    header.setTextColor(themeColor(row.matched
                            ? com.google.android.material.R.attr.colorPrimary
                            : com.google.android.material.R.attr.colorOnSurface));
                    header.setTextSize(14);
                    header.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
                    header.setPadding(16, 24, 16, 8);
                    header.setOnClickListener(v -> {
                        if (!expandedDevices.remove(row.device)) expandedDevices.add(row.device);
                        renderList(query);
                    });
                    return header;
                }

                final PkgRow row = (PkgRow) item;
                View v = convertView;
                if (v == null || v.findViewById(R.id.rowPkg) == null) {
                    v = getLayoutInflater().inflate(R.layout.popup_row_package, parent, false);
                }
                ((TextView) v.findViewById(R.id.rowPkg)).setText(row.pkg);
                TextView pkgText = v.findViewById(R.id.rowPkg);
                pkgText.setTextColor(themeColor(row.matched
                        ? com.google.android.material.R.attr.colorPrimary
                        : com.google.android.material.R.attr.colorOnSurface));
                pkgText.setTypeface(android.graphics.Typeface.DEFAULT, row.matched ? android.graphics.Typeface.BOLD : android.graphics.Typeface.NORMAL);

                TextView move = v.findViewById(R.id.rowMove);
                TextView delete = v.findViewById(R.id.rowDelete);
                move.setOnClickListener(null);
                delete.setOnClickListener(null);

                if (row.removed) {
                    move.setText(R.string.btn_restore);
                    move.setTextColor(themeColor(com.google.android.material.R.attr.colorPrimary));
                    delete.setVisibility(View.GONE);
                    move.setOnClickListener(l -> {
                        SpoofCatalog.restorePackage(row.device, row.pkg);
                        persist();
                    });
                } else {
                    move.setText(R.string.btn_move);
                    delete.setVisibility(View.VISIBLE);
                    delete.setText(R.string.btn_delete);
                    move.setTextColor(themeColor(com.google.android.material.R.attr.colorPrimary));
                    delete.setTextColor(getColor(R.color.error));
                    move.setOnClickListener(l -> showMoveDialog(row.device, row.pkg));
                    delete.setOnClickListener(l -> new MaterialAlertDialogBuilder(ManageActivity.this)
                            .setTitle(R.string.btn_delete)
                            .setMessage("(" + row.pkg + ") dihapus dari (" + SpoofCatalog.label(row.device) + ")?")
                            .setPositiveButton(R.string.btn_ok, (d, w) -> {
                                SpoofCatalog.removePackage(row.device, row.pkg);
                                persist();
                            })
                            .setNegativeButton(R.string.btn_cancel, null)
                            .show());
                }
                return v;
            }
        });
    }

    private void showMoveDialog(final String from, final String pkg) {
        View content = getLayoutInflater().inflate(R.layout.popup_add_package, null);
        ((View) content.findViewById(R.id.pkgInput).getParent()).setVisibility(View.GONE);
        content.findViewById(R.id.addDeviceBtn).setVisibility(View.GONE);
        Spinner deviceSpinner = content.findViewById(R.id.deviceSpinner);
        String[] names = SpoofCatalog.deviceNames();
        deviceSpinner.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, names));
        deviceSpinner.setSelection(Math.max(0, java.util.Arrays.asList(names)
                .indexOf(SpoofCatalog.label(from))));

        new MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.btn_move) + " (" + pkg + ")")
                .setView(content)
                .setPositiveButton(R.string.btn_ok, (d, w) -> {
                    String to = SpoofCatalog.keyForLabel((String) deviceSpinner.getSelectedItem());
                    if (to.equals(from)) return;
                    SpoofCatalog.movePackage(from, pkg, to);
                    persist();
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private int themeColor(int attrRes) {
        android.util.TypedValue tv = new android.util.TypedValue();
        getTheme().resolveAttribute(attrRes, tv, true);
        return tv.data;
    }
}