# AGENTS.md

## 1. Project Overview

Build an **Android PictureBook SDK (.aar)**.

Features:

* Image carousel
* Text carousel
* Mixed content (image + text)
* Auto-scroll + manual swipe
* Infinite loop

Output:

* Android library (`.aar`)
* Reusable SDK (not an app)

---

## 2. Tech Stack (STRICT)

* Language: **Java only (NO Kotlin)**
* Android SDK: **34**
* Java: **11**
* Min SDK: **21**
* Build: **Gradle 7.5**
* UI: Android View system (NO Compose)

---

## 3. Directory Structure (DO NOT CHANGE)

```
library/
  src/main/java/com/picturebook/
    core/
    model/
    view/
    adapter/
    controller/
    animation/
    util/
  src/main/res/
sample/
docs/
```

Rules:

* Do not rename modules
* Do not move files across layers without reason

---

## 4. Architecture (MANDATORY)

Pattern: **MVC**

```
View → Controller → Model
```

Rules:

* View: UI only
* Controller: business logic
* Model: data only
* No business logic in View

---

## 5. Core Components (MUST EXIST)

### 5.1 Model

```java
class StoryItem {
    enum Type { IMAGE, TEXT, MIXED }
    String imageUrl;
    String text;
}
```

---

### 5.2 Controller

Responsibilities:

* auto scroll
* pause / resume
* loop control
* state management

---

### 5.3 View

* RecyclerView-based
* smooth scroll
* supports multiple item types

---

### 5.4 Adapter

* multi-type support
* no business logic

---

## 6. Public API (MUST IMPLEMENT)

```java
setAutoScroll(boolean enabled);
setInterval(long millis);
setLoop(boolean enabled);
setAnimationDuration(long duration);
setData(List<StoryItem> data);
start();
stop();
```

---

## 7. Coding Rules (STRICT)

### General

* Max file length: **300 lines**
* Single responsibility per class
* No unused code

### Naming

* Class: PascalCase
* Method: camelCase
* Constant: UPPER_CASE

---

### Comments (REQUIRED for public API)

```java
/**
 * Set auto scroll interval
 */
```

---

## 8. Performance Rules (CRITICAL)

* No object creation in scroll loop
* No blocking main thread
* Avoid `notifyDataSetChanged`
* Must keep smooth scrolling (60fps target)

---

## 9. Dependencies

Allowed:

* Glide (image loading)

Forbidden:

* RxJava
* Kotlin
* heavy frameworks

---

## 10. Build & Test Commands

- DO NOT run any Gradle command
- DO NOT execute build, test, or lint
- DO NOT resolve dependencies
- DO NOT download any external artifacts

Gradle files are for declaration ONLY.

Allowed:
- edit build.gradle
- declare dependencies

Forbidden:
- ./gradlew build
- ./gradlew assemble
- ./gradlew test
- any command that triggers dependency resolution

---

## 11. Codex Working Rules (IMPORTANT)

### Before coding

1. Read related files
2. Identify impacted modules
3. Make minimal plan

---

### During coding

* Only change necessary files
* Follow existing patterns
* Do not refactor unrelated code

---

### After coding

Must pass:

* no logic error
* no syntax error
* no lint error

---

## 12. Task Execution Pattern (MANDATORY)

### For any task:

1. Analyze
2. Plan (3–5 steps)
3. Implement
4. Work in "code generation only" mode:
- Only create/edit source code and config files
- Never execute commands

---

## 13. Constraints (DO NOT VIOLATE)

* Do not introduce Kotlin
* Do not change Gradle structure
* Do not add new architecture (no MVVM)
* Do not rewrite large modules

---

## 14. Anti-Patterns (FORBIDDEN)

* God class (>500 lines)
* Business logic in View
* Memory leaks (Handler / Context)
* Full file rewrites without reason

---

## 15. Preferred Implementation Order

1. Model
2. Controller
3. Adapter
4. View

---

## 16. Example Task (REFERENCE)

```
Implement CarouselController:
- support start/stop
- support interval
- thread-safe
- no memory leak
- add unit test
- ensure build passes
```

---

## 17. Definition of Done

* Lint clean
* API usable
* No obvious performance issue

---

