# CrimsonFps

Xposed module for device spoofing with Lsposed

## Features

- Spoof device model, brand, ABI, build info per-app
- Material 3 dark UI with floating pill navigation
- 568+ supported packages across 12 device profiles
- Stable channel updates via GitHub Releases

## Supported Devices

| Device | Packages | Spoofed As |
|--------|----------|------------|
| Samsung S25 Ultra | 44 | `M-S938B` (samsung/pa3q) |
| Lenovo Legion | 41 | `TB-9707F` (Lenovo/qcom) |
| ASUS ROG 6 | 54 | `ASUS_AI2201` (Asus/qcom) |
| Google Pixel 9 Pro | 49 | `Pixel 9 Pro XL` (Google/komodo) |
| Nubia | 56 | `NX769J` (nubia/qti) |
| OnePlus 12 Pro | 50 | `PJD110` (OnePlus/qcom) |
| OnePlus 13 | 53 | `PJZ110` (OnePlus/qcom) |
| ROG 9 PRO | 39 | `ASUSAI2501` (Asus/qcom) |
| OPPO Find X7 Ultra | 46 | `PHY110` (oppo/qcom) |
| Xiaomi 15 Ultra | 43 | `25019PNF3C` (Xiaomi/xuanyuan) |
| Poco F7 Ultra | 44 | `24122RKC7G` (Redmi/miro) |
| iQOO 13 | 49 | `I2401` (vivo/qcom) |

## Project Structure

```
app/src/main/java/io/iicebear/crimson/fps/
├── MainActivity.java      # UI + Xposed hook wiring
├── DeviceInfo.java        # Real device info reader
├── DeviceSpoof.java       # Xposed hook: Build fields
├── SpoofCatalog.java      # Per-app spoof profiles
├── CatalogStore.java      # SharedPreferences persistence
├── UpdateChecker.java     # GitHub release check
└── CRIMSOON.java          # Module entry (IXposedHookLoadPackage)
```

## Build

Built on AndroidIDE. Update AGP/dependencies if porting to Android Studio.

## License

See [LICENSE](LICENSE).
