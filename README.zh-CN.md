# fetion4j-sdk

[English](./README.md) | [简体中文](./README.zh-CN.md)

Fetion4j Protocol SDK（Spring Boot 独立）— 飞信（中国移动）协议的 Java 客户端实现，覆盖登录/认证、好友管理、即时消息与短信收发，底层基于 SIPC 协议。

## 目录

- [1. 项目概览](#1-项目概览)
- [2. 功能与状态](#2-features--status)
- [3. 环境要求与兼容性](#3-requirements--compatibility)
- [4. 架构与模块](#4-architecture--modules)
- [5. 安装](#5-installation)
- [6. 快速开始](#6-quick-start)
- [7. 配置](#7-configuration)
- [8. 核心用法 / API](#8-core-usage--api)
- [9. 测试与构建](#9-testing--build)
- [10. 版本线与分支](#10-versioning--branches)
- [11. 参与贡献与许可协议](#11-contributing--license)

## 1. 项目概览

`fetion4j-sdk`（项目描述：*Fetion4j Protocol SDK (Spring Boot Independent)*）是飞信服务协议的纯 Java 客户端。它实现 SIPC（类 SIP）协议层、认证（MD5 摘要 + 验证码支持）、在线状态与好友管理，以及即时消息与短信收发。无 Spring 依赖。

| 是什么 | 不是什么 |
|:---|:---|
| 飞信服务的协议级 Java 客户端 | Spring Boot Starter（不含自动配置） |
| SIPC 消息层 + 认证 + 好友/会话管理 | UI 或聊天应用 |
| 通过已登录会话发送 IM 与短信 | HTTP REST 封装（协议基于 TCP/SIPC） |

典型使用场景：

| 场景 | 说明 |
|:---|:---|
| 使用手机号与密码登录 | `Fetion.login(password)` / `login(password, online)`；验证码通过 `AuthFeedback` 支持 |
| 向好友发送即时消息 | `FetionConsole.sendMessage(Buddy, String)` |
| 向好友发送短信 | `FetionConsole.sendSMSMessage(Buddy, String)` |
| 管理好友列表 | `addBuddy(userId/mobileNo, localName)`、`removeBuddy(Buddy)` |
| 跟踪在线状态与通知 | `NotifyListener` 回调、在线状态模型 |
| 后台会话保活 | 心跳请求、传输层（`TcpTransfer`） |

**项目状态：** 稳定（维护模式 — 长期存在的协议 SDK）。

<a id="2-features--status"></a>
## 2. 功能与状态

| 能力 | 状态 | 说明 |
|:---|:---|:---|
| SIPC 协议层 | 可用 | `Sipc`、`SipcMessage`、`RequestMessage` / `ResponseMessage`、reader/writer、`Field` |
| 登录 / 注销 | 可用 | `Fetion.login(password[, online])`、`close()`；`MessageHelper.createLoginRequest` |
| 认证 | 可用 | `AuthDigest`、`AuthGeneratorV4`、`PasswordEncrypterV4`；验证码通过 `Captcha` / `CaptchaImpl` |
| 好友管理 | 可用 | `addBuddy`、`removeBuddy`、好友分组、联系人版本 |
| IM 与短信收发 | 可用 | `sendMessage` / `sendSMSMessage` 返回 `Result`（Type SUCCESS / FAILURE） |
| 在线状态与通知 | 可用 | `Presence`、`NotifyListener`（登录/注销/传输错误/好友变更事件） |
| 传输层 | 可用 | `TcpTransfer`、`TransferProxy`、`Transfer` 抽象 |
| 用户模型 | 可用 | `UserInfo`（Personal / Contact / Quota）、`Buddy`、`BuddyGroup`、`Relation` |
| 单元测试 | 无 | 仓库中无测试源码 |
| CI 流水线 | 未配置 | 仓库中无 CI 工作流文件 |

<a id="3-requirements--compatibility"></a>
## 3. 环境要求与兼容性

| 依赖项 | 版本 |
|:---|:---|
| JDK | 8 |
| Maven | 3.0+ |
| slf4j-api | 2.0.x（已声明） |
| Lombok | provided 作用域（构建期注解处理） |
| 网络 | 可达的飞信协议端点（TCP） |

### 版本线矩阵

| 分支 | JDK | 版本号模式 |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

<a id="4-architecture--modules"></a>
## 4. 架构与模块

```text
  应用                          fetion4j-sdk                      飞信服务器
  ----                          -----------                      ----------
  Fetion（入口） ->  ProviderFactory -> Provider（会话生命周期）
                            |
          +-----------------+------------------+
          |                |                    |
    Controller        Dispatcher            activity/*
    （命令）            （消息路由）          （ChatDialogue、MainDialogue、
          |                                 AddBuddy、DeleteBuddy、...）
          +-----------------+------------------+
                            |
                     transfer/*（TcpTransfer、TransferProxy）
                            |
                     sipc/*（Sipc、SipcMessage、Request/Response）
                            |
                            +------> 与服务端的 TCP 连接
```

单一模块，jar 打包：

| 包 | 职责 |
|:---|:---|
| `net.apexes.fetion4j.core` | 公开 API：`Fetion`、`FetionConsole`、`Provider` / `ProviderFactory`、`Account`、`UserInfo`、`Result`、`Quota`、`Captcha`、`NotifyListener`、`LogHandler` |
| `net.apexes.fetion4j.core.client` | 会话机制：`SimpleProvider`、`Controller`、`Dispatcher`、`Dialogue`、activities |
| `net.apexes.fetion4j.core.client.auth` | 认证：`AuthDigest`、`AuthGeneratorV4`、`PasswordEncrypterV4` |
| `net.apexes.fetion4j.core.client.transfer` | `TcpTransfer`、`TransferProxy` |
| `net.apexes.fetion4j.core.sipc` | SIPC 协议消息 |
| `net.apexes.fetion4j.core.user` | 用户模型：`User`、`Buddy`、`BuddyGroup`、`Contact`、`Presence`、`Personal`、`Relation` |
| `net.apexes.fetion4j.core.util` | Base64、摘要、XML 元素工具 |

<a id="5-installation"></a>
## 5. 安装

### Maven

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>fetion4j-sdk</artifactId>
    <version>2.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.easy4j:fetion4j-sdk:2.0.x.x.20260630-SNAPSHOT'
```

**可用性：** 构件发布至阿里云私有 Maven 仓库，并通过 GitHub Releases 分发；尚未发布到 Maven Central。

<a id="6-quick-start"></a>
## 6. 快速开始

```java
import net.apexes.fetion4j.core.Fetion;
import net.apexes.fetion4j.core.FetionConsole;
import net.apexes.fetion4j.core.Result;
import net.apexes.fetion4j.core.user.Buddy;
import net.apexes.fetion4j.core.user.Contact;

Fetion fetion = new Fetion(13800138000L);   // 飞信账号（手机号）
FetionConsole console = fetion.login("your-password");

Contact contact = console.getUserInfo().getContact();
Buddy buddy = contact.getBuddys().iterator().next();

Result result = console.sendSMSMessage(buddy, "Hello from fetion4j");
boolean ok = result.getType() == Result.Type.SUCCESS;

console.close();
fetion.close();
```

预期结果：通过已登录会话发送短信；`result.getType()` 返回 `SUCCESS` 或 `FAILURE`。

<a id="7-configuration"></a>
## 7. 配置

SDK 为纯 Java 库，无配置文件。运行时参数以编程方式传入：

- 账号与会话：`new Fetion(long mobileNo)`（可选自定义 `ProviderFactory`）
- 认证反馈（验证码等）：`Fetion.getAuthSupportable()` / `setAuthSupportable(AuthSupportable)`
- 诊断：`setLogHandler(LogHandler)` 接收 SIPC 收发跟踪
- 系统默认值：`SystemConfig`（通过 `getSystemConfig()` 获取）

<a id="8-core-usage--api"></a>
## 8. 核心用法 / API

### 8.1 登录与控制台

```java
Fetion fetion = new Fetion(13800138000L);
FetionConsole console = fetion.login("password", true);  // 上线
```

`FetionConsole` 操作：

| 方法 | 说明 |
|:---|:---|
| `getUserInfo()` | 已登录用户资料（Personal / Contact / Quota） |
| `addBuddy(int userId, String localName)` / `addBuddy(long mobileNo, String localName)` | 添加好友 |
| `removeBuddy(Buddy buddy)` | 删除好友 |
| `sendMessage(Buddy buddy, String message)` | 发送即时消息 |
| `sendSMSMessage(Buddy buddy, String message)` | 发送短信 |
| `isClosed()` / `close()` | 会话生命周期 |

### 8.2 通知

```java
fetion.addNotifyListener(new NotifyListener() {
    public void loginSuccessed(FetionConsole console, UserInfo userInfo) { ... }
    public void changedBuddy(Buddy buddy, String contactVersion) { ... }
    public void transfeError(String message, Exception exception) { ... }
    // ...
});
```

### 8.3 Result

`Result` 封装每次操作结果：`getStatus()`、`getStatusMessage()`、`getType()`（`Result.Type.SUCCESS` / `Result.Type.FAILURE`）、`getDescribe()`。

<a id="9-testing--build"></a>
## 9. 测试与构建

```bash
./mvnw clean verify        # 编译、运行测试、生成覆盖率报告
./mvnw clean install       # 安装到本地仓库
```

- 仓库当前不含测试源码；建议针对兼容飞信协议的端点做集成验证。
- 覆盖率由 JaCoCo Maven 插件度量（目标：90% 行覆盖率，`haltOnFailure=false`）。
- `release` profile 组装 GPG 签名 + 源码 + Javadoc + 部署（`./mvnw -Prelease clean deploy`）。

<a id="10-versioning--branches"></a>
## 10. 版本线与分支

仓库维护三条并行版本线：

| 分支 | JDK | 版本号模式 |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

维护策略：在 JDK 8 作为基线的同时，1.0.x 版本线接收缺陷修复；新功能开发主要面向 2.0.x / 3.0.x 版本线。

<a id="11-contributing--license"></a>
## 11. 参与贡献与许可协议

欢迎参与贡献——请通过 Issue 反馈问题，或向对应版本线分支提交 Pull Request（JDK 17 相关改动提交到 `feature/2.0.x`）。

本项目基于 [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0) 许可发布。详见仓库根目录的 `LICENSE` 文件。
