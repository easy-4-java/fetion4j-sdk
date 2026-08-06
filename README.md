# fetion4j-sdk

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-21-orange)](https://github.com/easy-4-java/fetion4j-sdk) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](https://www.apache.org/licenses/LICENSE-2.0.txt)

Fetion4j Protocol SDK (Spring Boot independent) — a Java client implementation of the Fetion (China Mobile) protocol, covering login/authentication, buddy management, instant messaging and SMS messaging over the SIPC protocol.

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

`fetion4j-sdk` (project description: *Fetion4j Protocol SDK (Spring Boot Independent)*) is a pure Java client for the Fetion service protocol. It implements the SIPC (SIP-like) protocol layer, authentication (MD5 digest with captcha support), presence and buddy management, and both instant messages and SMS messages. It has no Spring dependency.

| What it is | What it is not |
|:---|:---|
| A protocol-level Java client for the Fetion service | A Spring Boot starter (no auto-configuration) |
| SIPC message layer + authentication + buddy/session management | A UI or chat application |
| IM and SMS message sending through a logged-in session | An HTTP REST wrapper (protocol is TCP/SIPC based) |

Typical use cases:

| Use case | Notes |
|:---|:---|
| Login with a mobile number and password | `Fetion.login(password)` / `login(password, online)`; captcha support via `AuthFeedback` |
| Send instant messages to buddies | `FetionConsole.sendMessage(Buddy, String)` |
| Send SMS messages to buddies | `FetionConsole.sendSMSMessage(Buddy, String)` |
| Manage buddy list | `addBuddy(userId/mobileNo, localName)`, `removeBuddy(Buddy)` |
| Track presence / notifications | `NotifyListener` callbacks, presence model |
| Background session maintenance | Keep-alive requests, transfer layer (`TcpTransfer`) |

**Project status:** stable (maintenance mode — a long-lived protocol SDK).

## 2. Features & Status

| Feature | Status | Notes |
|:---|:---|:---|
| SIPC protocol layer | Available | `Sipc`, `SipcMessage`, `RequestMessage` / `ResponseMessage`, reader/writer, `Field` |
| Login / logout | Available | `Fetion.login(password[, online])`, `close()`; `MessageHelper.createLoginRequest` |
| Authentication | Available | `AuthDigest`, `AuthGeneratorV4`, `PasswordEncrypterV4`; captcha via `Captcha` / `CaptchaImpl` |
| Buddy management | Available | `addBuddy`, `removeBuddy`, buddy groups, contact versioning |
| IM & SMS messaging | Available | `sendMessage` / `sendSMSMessage` returning `Result` (Type SUCCESS / FAILURE) |
| Presence & notifications | Available | `Presence`, `NotifyListener` (login/logout/transfer error/buddy change events) |
| Transfer layer | Available | `TcpTransfer`, `TransferProxy`, `Transfer` abstraction |
| User model | Available | `UserInfo` (Personal / Contact / Quota), `Buddy`, `BuddyGroup`, `Relation` |
| Unit tests | Not present | No test sources in the repository |
| CI pipeline | Not configured | No CI workflow files in the repository |

## 3. Requirements & Compatibility

| Requirement | Version |
|:---|:---|
| JDK | 8 |
| Maven | 3.0+ |
| slf4j-api | 2.0.x (declared) |
| Lombok | provided scope (annotation processing at build time) |
| Network | A reachable Fetion protocol endpoint (TCP) |

### Version lines

| Branch | JDK | Version pattern |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
  Application                     fetion4j-sdk                         Fetion server
  -----------                     ------------                         ------------
  Fetion (entry)  ->  ProviderFactory -> Provider (session lifecycle)
                            |
          +-----------------+------------------+
          |                |                    |
    Controller        Dispatcher            activity/*
    (commands)        (message routing)     (ChatDialogue, MainDialogue,
          |                                 AddBuddy, DeleteBuddy, ...)
          +-----------------+------------------+
                            |
                     transfer/* (TcpTransfer, TransferProxy)
                            |
                     sipc/* (Sipc, SipcMessage, Request/Response)
                            |
                            +------> TCP connection to the service
```

Single module, jar packaging:

| Package | Responsibility |
|:---|:---|
| `net.apexes.fetion4j.core` | Public API: `Fetion`, `FetionConsole`, `Provider` / `ProviderFactory`, `Account`, `UserInfo`, `Result`, `Quota`, `Captcha`, `NotifyListener`, `LogHandler` |
| `net.apexes.fetion4j.core.client` | Session machinery: `SimpleProvider`, `Controller`, `Dispatcher`, `Dialogue`, activities |
| `net.apexes.fetion4j.core.client.auth` | Authentication: `AuthDigest`, `AuthGeneratorV4`, `PasswordEncrypterV4` |
| `net.apexes.fetion4j.core.client.transfer` | `TcpTransfer`, `TransferProxy` |
| `net.apexes.fetion4j.core.sipc` | SIPC protocol messages |
| `net.apexes.fetion4j.core.user` | User model: `User`, `Buddy`, `BuddyGroup`, `Contact`, `Presence`, `Personal`, `Relation` |
| `net.apexes.fetion4j.core.util` | Base64, digest, XML element helpers |

## 5. Installation

### Maven

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>fetion4j-sdk</artifactId>
    <version>3.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.easy4j:fetion4j-sdk:3.0.x.x.20260630-SNAPSHOT'
```

**Availability:** the artifact is published to the Aliyun private Maven repository and distributed through GitHub Releases; it has not yet been published to Maven Central.

## 6. Quick Start

```java
import net.apexes.fetion4j.core.Fetion;
import net.apexes.fetion4j.core.FetionConsole;
import net.apexes.fetion4j.core.Result;
import net.apexes.fetion4j.core.user.Buddy;
import net.apexes.fetion4j.core.user.Contact;

Fetion fetion = new Fetion(13800138000L);   // Fetion account (mobile number)
FetionConsole console = fetion.login("your-password");

Contact contact = console.getUserInfo().getContact();
Buddy buddy = contact.getBuddys().iterator().next();

Result result = console.sendSMSMessage(buddy, "Hello from fetion4j");
boolean ok = result.getType() == Result.Type.SUCCESS;

console.close();
fetion.close();
```

Expected result: the SMS message is sent through the logged-in session; `result.getType()` reports `SUCCESS` or `FAILURE`.

## 7. Configuration

The SDK is a plain Java library with no configuration files. Runtime parameters are passed programmatically:

- Account and session: `new Fetion(long mobileNo)` (optionally with a custom `ProviderFactory`)
- Authentication feedback (captcha etc.): `Fetion.getAuthSupportable()` / `setAuthSupportable(AuthSupportable)`
- Diagnostics: `setLogHandler(LogHandler)` receives SIPC receive/transmit traces
- System defaults: `SystemConfig` (exposed via `getSystemConfig()`)

## 8. Core Usage / API

### 8.1 Login and console

```java
Fetion fetion = new Fetion(13800138000L);
FetionConsole console = fetion.login("password", true);  // online presence
```

`FetionConsole` operations:

| Method | Description |
|:---|:---|
| `getUserInfo()` | Logged-in user profile (Personal / Contact / Quota) |
| `addBuddy(int userId, String localName)` / `addBuddy(long mobileNo, String localName)` | Add a buddy |
| `removeBuddy(Buddy buddy)` | Remove a buddy |
| `sendMessage(Buddy buddy, String message)` | Send an instant message |
| `sendSMSMessage(Buddy buddy, String message)` | Send an SMS message |
| `isClosed()` / `close()` | Session lifecycle |

### 8.2 Notifications

```java
fetion.addNotifyListener(new NotifyListener() {
    public void loginSuccessed(FetionConsole console, UserInfo userInfo) { ... }
    public void changedBuddy(Buddy buddy, String contactVersion) { ... }
    public void transfeError(String message, Exception exception) { ... }
    // ...
});
```

### 8.3 Result

`Result` wraps every operation outcome: `getStatus()`, `getStatusMessage()`, `getType()` (`Result.Type.SUCCESS` / `Result.Type.FAILURE`), `getDescribe()`.

## 9. Testing & Build

```bash
./mvnw clean verify        # compile, run tests, generate coverage report
./mvnw clean install       # install into the local repository
```

- The repository currently contains no test sources; the SDK is best validated against a live Fetion-compatible endpoint.
- Coverage is measured with the JaCoCo Maven plugin (target: 90% line coverage, `haltOnFailure=false`).
- The `release` profile assembles GPG signing + sources + Javadoc + deployment (`./mvnw -Prelease clean deploy`).

## 10. Versioning & Branches

Three parallel version lines are maintained:

| Branch | JDK | Version pattern |
|:---|:---|:---|
| `feature/1.0.x` | JDK 8 | `1.0.x.*` |
| `feature/2.0.x` | JDK 17 | `2.0.x.*` |
| `feature/3.0.x` | JDK 21 | `3.0.x.*` |

Maintenance strategy: the 1.0.x line receives bug fixes while JDK 8 remains the baseline; feature development primarily targets the 2.0.x / 3.0.x lines.

## 11. Contributing & License

Contributions are welcome — open an issue or submit a pull request against the matching version-line branch (`feature/3.0.x` for JDK 21 changes).

This project is licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0). See the `LICENSE` file in the repository root for details.
