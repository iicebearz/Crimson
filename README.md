# CrimsonFps

Xposed module for device spoofing — Android 10+ (API 28+).

## Features

- Spoof device model, brand, ABI, build info per-app
- Material 3 dark UI with floating pill navigation
- Supports 200+ packages
- Stable channel updates via GitHub Releases

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

## License

See [LICENSE](LICENSE).
