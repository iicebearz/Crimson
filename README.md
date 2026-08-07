# CrimsonFps

Xposed module for device spoofing — Android 10+ (API 28+).

## Features

- Spoof device model, brand, ABI, build info per-app
- Material 3 dark UI with floating pill navigation
- Supports 200+ packages
- Stable channel updates via GitHub Releases

## Build

| Item | Value |
|------|-------|
| AGP | 7.5.1 |
| JDK | 17 |
| Kotlin/Java | Java |
| compileSdk | 34 |
| minSdk | 28 |
| targetSdk | 34 |

Built on AndroidIDE. Update AGP/dependencies if porting to Android Studio.

## Project Structure

```
app/src/main/java/io/iicebear/crimson/fps/
├── MainActivity.java      # UI + Xposed hook wiring
├── DeviceInfo.java        # Real device info reader
├── DeviceSpoof.java       # Xposed hook: Build fields
├── SpoofCatalog.java      # Per-app spoof profiles
├── CatalogStore.java      # JSON persistence
├── UpdateChecker.java     # GitHub release check
└── CRIMSOON.java          # Module entry (IXposedHookLoadPackage)
```

## UI Layout

```
┌─────────────────────────────────┐
│ CrimsonFps          🔄  ⚙️      │
├─────────────────────────────────┤
│ ▓▓▓ MODULE STATUS CARD ▓▓▓     │
│ ⚡ MODULE STATUS                │
│ 🔴 Active         Stable Build │
│                    30/12/25    │
├─────────────────────────────────┤
│ ▓▓▓ PACKAGE MGMT CARD ▓▓▓      │
│ ▦ PACKAGE MANAGEMENT            │
│ ▦ 104 Supported Apps       >   │
│ ─────────────────────────────── │
│ [   🔲 Input Package        ]   │
├─────────────────────────────────┤
│ ▓▓▓ DEVICE INFO CARD ▓▓▓       │
│ 📱 DEVICE INFORMATION           │
│ ▣ Processor        Snapdragon  │
│ ─────────────────────────────── │
│ 🤖 Android Version   14 API 34 │
│ ─────────────────────────────── │
│ ⟨⟩ System ABI           x86_64 │
│ ─────────────────────────────── │
│ 📲 Device Model   sdk_gphone64 │
│ ─────────────────────────────── │
│ 🏷 Device Name          emu64x │
│ ─────────────────────────────── │
│ # Build Number      SDK 14/64  │
│ ─────────────────────────────── │
│ ⚡ Security Patch    QPR1 2025 │
└─────────────────────────────────┘
```

## Developer

| | |
|---|---|
| Name | @iicebear |
| Telegram | [@iiceberr](https://t.me/iiceberr) |
| Group | [maincloudch](https://t.me/maincloudch) |
| GitHub | [iicebearz](https://github.com/iicebearz) |

## Color Palette

| Name | Hex | Usage |
|------|-----|-------|
| background | `#0F1115` | App background, status/nav bar |
| surface | `#171A20` | Card backgrounds |
| surface_variant | `#1D2128` | Dividers, secondary surfaces |
| surface_translucent | `#E6171A20` | Semi-transparent overlay |
| divider | `#262A30` | Card/row dividers |
| icon_box | `#22262E` | Device info icon containers |
| status_badge_bg | `#381E20` | Status badge background |
| primary | `#FF4A3D` | Accent, buttons, section headers |
| nav_selected | `#2AFF4A3D` | Nav pill active state bg |
| on_primary | `#FFFFFF` | Text on primary |
| text_primary | `#FFFFFF` | Headings, body text |
| text_secondary | `#9CA3AF` | Labels, captions |
| status_active | `#22C55E` | Active indicator, avatar border |
| status_inactive | `#9CA3AF` | Offline/inactive indicator |

## Drawables

| File | Purpose |
|------|---------|
| `ic_github.xml` | GitHub icon (profile + update button) |
| `ic_telegram.xml` | Telegram icon (support links) |
| `ic_heart.xml` | Heart icon (shortlink support) |
| `ic_edit.xml` | Edit pencil (avatar overlay) |
| `ic_sync.xml` | Sync/refresh icon (top bar) |
| `ic_pulse.xml` | Pulse icon (module status header, security patch) |
| `ic_grid_view.xml` | Grid icon (supported apps, input button) |
| `ic_smartphone.xml` | Smartphone icon (device info header) |
| `ic_memory.xml` | Memory/processor icon |
| `ic_android.xml` | Android icon (OS version) |
| `ic_code.xml` | Code icon (system ABI) |
| `ic_phonelink.xml` | Phonelink icon (device model) |
| `ic_local_offer.xml` | Tag/offer icon (device name) |
| `ic_tag.xml` | Tag icon (build number) |
| `bg_avatar_border.xml` | Green circle border for avatar |
| `bg_circle_green.xml` | Green circle for edit overlay |
| `bg_icon_box.xml` | Rounded box for list item icons |
| `bg_icon_box_small.xml` | Rounded box for device info icons |
| `bg_status_badge.xml` | Red-tinted badge background for status |

## License

See [LICENSE](LICENSE).
