# 💣 扫雷 (Saolei) - Compose Multiplatform

<p align="center">
  <img src="webApp/src/webMain/resources/favicon.png" width="96" height="96" alt="Saolei Logo" />
</p>

<p align="center">
  <b>现代、优雅、全平台通用的高性能扫雷游戏</b><br>
  基于 <b>Kotlin Multiplatform (KMP)</b> 与 <b>Compose Multiplatform (CMP)</b> 构建，一套代码无缝运行于所有主流操作系统与设备终端。
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Version-0.0.1-blue.svg" alt="Version" />
  <img src="https://img.shields.io/badge/Kotlin-2.4.20-7F52FF.svg?logo=kotlin&logoColor=white" alt="Kotlin" />
  <img src="https://img.shields.io/badge/Compose_Multiplatform-1.12.1-4285F4.svg?logo=jetpackcompose&logoColor=white" alt="Compose Multiplatform" />
  <img src="https://img.shields.io/badge/Platforms-Android%20%7C%20iOS%20%7C%20Desktop%20%7C%20Web-brightgreen.svg" alt="Platforms" />
  <img src="https://img.shields.io/badge/License-MIT-blue.svg" alt="License" />
  <img src="https://img.shields.io/badge/PRs-Welcome-orange.svg" alt="PRs Welcome" />
</p>

---

## 📖 项目简介

**Saolei (扫雷)** 是一套使用全新现代技术栈打造的多平台扫雷游戏。项目采用 **Kotlin Multiplatform** 实现业务逻辑与底层平台能力的 100% 共享，并通过 **Compose Multiplatform** 呈现自适应的高性能响应式声明式 UI。

无论是桌面端（Windows / macOS / Linux）、移动端（Android / iOS），还是新一代浏览器（WebAssembly / JavaScript），本项目均提供一致、丝滑、现代化的扫雷体验。

---

## ✨ 核心特性

### 🎮 经典致敬与现代玩法增强
- **多级难度挑战**：
  - 🟢 **初级 (Easy)**：9 × 9，10 颗地雷，适合新手速通。
  - 🟡 **中级 (Medium)**：16 × 16，40 颗地雷，经典标准平衡。
  - 🔴 **高级 (Hard)**：16 × 30，99 颗地雷，硬核大脑博弈。
  - ⚙️ **自定义 (Custom)**：支持自由设置 8~50 行、8~30 列及地雷总数，内置防越界与智能雷数上限保护。
- **首击防爆机制 (First-Click Safe)**：首下点击绝对保证不触雷。
- **九宫格开局保障 (First-Click Empty 3×3)**：开启后首击位置周边 3×3 区域必为空白无雷区，告别盲猜，顺畅展开局面。
- **快速双开连击 (Chording)**：在已翻开且周边标旗数符合要求的数字格上点击，一键自动掀开周边其余未标记安全格。
- **疑雷标记支持**：支持经典「地雷 🚩」与「问号 ❓」双状态切换，可在设置中灵活开关。
- **胜利自动插旗**：排雷通关瞬间，系统自动将剩余所有地雷标上红旗，仪式感满满。

### 🎨 响应式视口与智能手势控制
- **无级缩放与平移 (Zoom & Pan)**：
  - **移动端**：支持双指捏合缩放（Pinch-to-zoom）与单指拖拽平移。
  - **桌面端 / Web**：支持滚轮缩放与鼠标拖拽平移。
- **视口自适应防溢出**：
  - 无论手机竖屏、平板大屏还是超宽显示器，高级难度 30 列雷区均可智能计算最佳贴合比例（Auto-fit），彻底解决小屏裁切与大屏过小的问题。
- **交互模式一键切换**：底部浮动控制栏提供「翻开/挖雷 ⛏️」与「标记/插旗 🚩」快速模式切换，同时支持移动端长按插旗。
- **经典情绪面部状态**：上方状态栏经典表情头像（待命微笑 🙂、长按紧张 😮、胜利酷墨镜 😎、触雷阵亡 😵），点击即可快速重启本局。

### 🏆 10 套独创艺术级通关弹窗
每次赢得游戏胜利，系统将从 **10 款独具特色的主题风格** 中随机抽取一款进行华丽展示，记录荣誉时刻：
1. **赛博朋克 · 霓虹幻境** (*Cyberpunk Neon Protocol*) —— 全息霓虹流光与极客数据扫描感
2. **复古像素 · 8-Bit街机** (*Retro Pixel Arcade*) —— 经典红白机金杯通关质感
3. **荣耀加冕 · 极光流金** (*Royal Gold Supreme Glory*) —— 尊贵鎏金桂冠与王者勋章
4. **极简禅意 · 晨雾拟态** (*Minimal Glass Zen Elegance*) —— 磨砂玻璃透光拟态美学
5. **盛大狂欢 · 缤纷礼花** (*Carnival Celebration*) —— 漫天七彩粒子礼花喷射
6. **浩瀚星河 · 深空漫游** (*Cosmic Galaxy Mission*) —— 深邃星轨与开拓者宇航勋章
7. **翡翠秘境 · 自然之语** (*Emerald Nature Whispers*) —— 苍翠森林晨露与清新绿意
8. **蒸汽朋克 · 精密黄铜** (*Steampunk Chrono Gear*) —— 精巧黄铜齿轮与维多利亚工业机械
9. **甜心派对 · 马卡龙波普** (*Sweet Macaron Pop*) —— 软萌棉花糖渐变与心动气泡
10. **绝密特工 · 谍影重重** (*Top Secret Mission Dossier*) —— 机密档案袋解密与通关防伪印章

### 📊 本地排行榜 (Top 20 Leaderboard)
- 独立记录各难度前 **20 次最短通关记录**。
- 精准记录通关用时（秒级浮点）、达成日期、操作有效点击数及旗标投放数。
- 动态标识历史新纪录（Personal Best）与前三名金银铜牌勋章。
- 支持按难度独立安全重置/清空数据。

### 🎵 纯数学波形音频合成引擎 (Waveform Synthesizer)
- **零外部资源依赖**：采用内置纯数学音频合成器（`WaveformSynthesizer`），通过正弦波、白噪声、调频调制实时生成高质量 PCM 音效（Click、Dig、Flag、Unflag、Chord、Explode、Win、BGM）。
- **极小应用体积**：无需打包任何 MP3 / WAV 外部音效文件，节省体积并消除加载延迟。
- **独立音量控制**：设置中支持游戏音效 (SFX) 与背景音乐 (BGM) 独立的开关与无级音量滑动调节。
- **触感反馈 (Haptics)**：在支持的设备上提供细腻的操作震动反馈。

### 🌓 现代主题与多套配色
- 完美适配 **白天浅色 (Light)**、**暗夜深色 (Dark)** 与 **跟随系统 (System Auto)** 模式。
- 针对经典 1~8 数字深度调校的高可辨识度配色方案，即使在高对比度/夜间模式下也能一眼辨识。

### 📖 内置图文规则百科
- 集成游戏规则说明界面，图文并茂讲解扫雷基础逻辑、周边数字含义、快速双开 (Chording) 高阶技巧与扫雷概率法则，帮助新老玩家快速上手。

---

## 📱 平台支持与发行形态

| 平台 | 操作系统 / 架构 | 发布产物格式 | 运行方式 |
| :--- | :--- | :--- | :--- |
| **Android** | Android 7.0+ (API 24+)<br>`arm64-v8a`, `armeabi-v7a`, `x86_64`, `x86`, `all` | `.apk` (已签名独立包) | 直接安装至手机/平板/车机/模拟器 |
| **Windows** | Windows 10 / 11 (64-bit) | `.msi` (安装器) / `.exe` | 双击安装或直接运行 |
| **macOS** | macOS 12+<br>`Apple Silicon (arm64)` & `Intel (x86_64)` | 原生 `.dmg` 镜像安装包 | 拖入 `Applications` 即可运行 |
| **Linux** | 主流 Linux 发行版 (Ubuntu, Fedora, Arch 等) | `.AppImage` (便携包) / `.deb` (Debian包) | `chmod +x` 后双击即玩 / `dpkg -i` |
| **Desktop Universal** | 任何已安装 JRE 17+ 的操作系统 | `.jar` (可执行包) | `java -jar Saolei-*.jar` |
| **iOS** | iOS 15.0+ (`arm64`) | `.ipa` (未签名包) / `.app.zip` | 越狱设备 / TrollStore / 开发者自签 / Xcode 调试 |
| **Web** | Chrome, Edge, Safari, Firefox 等现代浏览器 | WebAssembly (Wasm-GC) + JS Fallback | 纯静态托管部署，即开即玩 |

---

## 🏗️ 技术架构

本项目遵循 **Clean Architecture** 与 **MVI (Model-View-Intent)** 单向数据流设计模式：

```
saolei/
├── shared/                              # 跨平台核心共享模块
│   ├── src/commonMain/kotlin/com/exp/saolei/
│   │   ├── engine/                      # 扫雷核心引擎与状态机 (MinesweeperEngine, BoardState)
│   │   ├── presentation/                # MVI 架构层 (GameViewModel, GameContract: Intent/State/Effect)
│   │   ├── model/                       # 领域实体模型 (Cell, Difficulty, GameSettings, VictoryStyle...)
│   │   ├── storage/                     # 本地持久化 (DataManager, PlatformStorage expect/actual)
│   │   ├── audio/                       # 音频系统 (WaveformSynthesizer, AudioPlayer expect/actual)
│   │   └── ui/                          # Compose Multiplatform 声明式界面组件与主题
│   │       ├── components/              # 雷区视口 (MinefieldView)、状态栏、控制栏、弹窗组件
│   │       ├── screens/                 # 主游戏、排行榜、规则说明、设置屏幕
│   │       └── theme/                   # 动态 Material 3 配色系统与自适应形状
│   └── src/{android,ios,jvm,wasmJs,js}Main/ # 各平台针对底层音频/存储能力的 actual 适配
├── androidApp/                          # Android 宿主工程入口 (MainActivity)
├── desktopApp/                          # Desktop JVM 原生打包工程入口 (main.kt)
├── iosApp/                              # iOS Xcode 工程宿主 (SwiftUI 容器)
└── webApp/                              # Web 网页端分发模块 (Wasm / JS HTML Canvas)
```

### 核心技术栈
- **语言**：[Kotlin 2.4.20](https://kotlinlang.org/)
- **UI 框架**：[Compose Multiplatform 1.12.1](https://www.jetbrains.com/lp/compose-multiplatform/) + Material 3
- **异步处理**：[Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) 1.11.0 & StateFlow / SharedFlow
- **构建系统**：Gradle 8.x + Kotlin DSL + Android Gradle Plugin 9.1.1
- **测试框架**：Kotlin Test, JUnit 4, Kotlin Coroutines Test

---

## 🚀 本地开发与构建指南

### 环境要求
- **JDK**：OpenJDK 21 (推荐使用 Temurin 或 Zulu)
- **Android 开发** (可选)：Android Studio Meerkat / Ladybug 及以上，Android SDK 35+
- **iOS 开发** (可选)：macOS 系统，Xcode 15+
- **Web 开发**：无需额外配置，Gradle 自动拉取 Wasm 运行时依赖

### 1. 克隆代码仓库
```bash
git clone https://github.com/your-username/saolei.git
cd saolei
```

### 2. 运行各平台应用程序

#### 💻 桌面端 (Desktop)
```bash
# 启动桌面端应用程序
./gradlew :desktopApp:run

# 带有代码热重载 (Hot Reload) 模式运行
./gradlew :desktopApp:hotRun --auto
```

#### 📱 Android 端
```bash
# 编译并生成 Debug APK
./gradlew :androidApp:assembleDebug

# 直接安装至已连接的设备或模拟器
./gradlew :androidApp:installDebug
```

#### 🌐 网页端 (Web)
```bash
# 基于现代化 WebAssembly (Wasm-GC) 编译运行（极速推荐）
./gradlew :webApp:wasmJsBrowserDevelopmentRun

# 基于经典 JavaScript 编译运行
./gradlew :webApp:jsBrowserDevelopmentRun
```

#### 🍏 iOS 端
使用 Xcode 打开 `iosApp/` 目录：
```bash
open iosApp/iosApp.xcworkspace # 或直接打开 iosApp.xcodeproj
```
选择目标模拟器或连接真机直接点击 **Run** 即可。

### 3. 执行单元测试
```bash
# 运行通用共享逻辑全套单元测试
./gradlew :shared:jvmTest

# 运行 Android 平台宿主单元测试
./gradlew :shared:testAndroidHostTest

# 运行 Web 目标单元测试
./gradlew :shared:wasmJsTest
```

---

## 📦 打包与分发构建

### 桌面端独立安装包打包 (Compose Desktop)
```bash
# 打包当前操作系统平台的安装文件（DMG / MSI / DEB 等）
./gradlew :desktopApp:packageDistributionForCurrentOS

# 生成跨平台通用可执行 JAR
./gradlew :desktopApp:packageUberJarForCurrentOS
```
打包输出路径位于 `desktopApp/build/compose/binaries/main/`。

### Android Release APK 打包
```bash
./gradlew :androidApp:assembleRelease
```
打包输出路径位于 `androidApp/build/outputs/apk/release/`。

### Web 静态页面打包
```bash
./gradlew :webApp:wasmJsBrowserDistribution
```
打包输出路径位于 `webApp/build/dist/wasmJs/productionExecutable/`，可直接部署至 GitHub Pages、Vercel 或 Nginx。

---

## 🤖 自动化 CI/CD (GitHub Actions)

项目内置完善的持续集成与自动化发布流水线：
- **CI 流水线** ([`.github/workflows/ci.yml`](.github/workflows/ci.yml))：在提交代码或发起 PR 时自动执行 Gradle 全量构建与多平台单元测试。
- **Release 发布流水线** ([`.github/workflows/release.yml`](.github/workflows/release.yml))：
  - **触发方式**：
    1. 推送以 `v` 开头的版本标签（如 `git tag v1.0.0 && git push origin v1.0.0`）。
    2. 在 GitHub Actions 页面手动触发（Workflow Dispatch）并指定版本号。
  - **产物分发**：
    - 自动打包 Android 全架构 APK（arm64、armv7、x86、x86_64、Universal 并经 CI 签名）。
    - 自动打包 macOS Apple Silicon (arm64) 及 Intel (x86_64) DMG 安装镜像。
    - 自动打包 Windows x64 MSI 安装程序与独立 EXE 执行程序。
    - 自动构建 Linux x86_64 通用 AppImage 免安装包与 DEB 包。
    - 自动编译 iOS ARM64 未签名 IPA 与 App 压缩包。
    - 自动生成 Web 静态部署 Zip 包与全平台 Universal JAR 包。
    - 自动生成所有文件的 `checksums.txt` SHA-256 校验清单并发布至 GitHub Releases。

---

## 🤝 参与贡献

欢迎对本项目提出建议、上报问题或贡献代码！

1. Fork 本项目仓库
2. 创建您的特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交您的修改 (`git commit -m 'feat: add some amazing feature'`)
4. 推送分支至您的远程仓库 (`git push origin feature/amazing-feature`)
5. 新建 Pull Request

---

## 📄 开源许可证

本项目基于 [MIT License](LICENSE) 开源协议发布。你可以自由地学习、修改、分发及用于商业用途。