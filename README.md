# Bullseye

Arcade-style 2D **target practice** game built in Java.  
Move the player vertically and **shoot arrows** to hit the bullseye while avoiding obstacles. Different arrow types have different **speed** and **score** values. Built with OOP (inheritance, enums, factory) and the **SimpleGraphics** library.

---

## 🎮 Gameplay

- **Controls:**
    - `↑ / ↓` – move the player
    - `Space` – shoot
- **Arrow types (randomized):**
    - **Blue** – very fast, medium score
    - **Green** – very slow, high score
    - **Red** – normal speed, base score
- **Obstacles:** spawn randomly; **colliding costs −10 points**.
- **Goal:** maximize your score by timing shots and managing arrow speed vs. target position.

---

## ✨ Features

- OOP design with **inheritance** and **composition**
- **Enums** for arrow configuration (sprite, speed, score)
- **Factory pattern** to create game entities
- Keyboard handling via a dedicated `MyKeyboard` class
- Simple 2D rendering with **SimpleGraphics**
- High-score tracking (`HighScoreManager`) and game state handling (`GameState`)

---

## 🗂️ Project structure

```
.
├─ jar/                        # packaged .jar 
├─ lib/                        # third-party libs (SimpleGraphics)
├─ resources/                  # sprites, sounds
├─ src/com/codeforall/online/bullseye/
│  ├─ Main.java
│  ├─ game/
│  │  ├─ Arena.java
│  │  ├─ Game.java
│  │  ├─ GameState.java
│  │  ├─ HighScoreManager.java
│  │  ├─ MyKeyboard.java
│  │  └─ Sfx.java
│  ├─ playables/
│  │  ├─ Collidables.java
│  │  ├─ Entity.java
│  │  ├─ Player.java
│  │  ├─ arrows/
│  │  │  ├─ Arrows.java
│  │  │  └─ ArrowTypes.java
│  │  ├─ obstacle/
│  │  │  ├─ Obstacle.java
│  │  │  └─ ObstacleType.java
│  │  └─ target/
│  │     ├─ Direction.java
│  │     ├─ Target.java
│  │     └─ TargetFactory.java
├─ build.xml                   # Ant build script
└─ .gitignore
```

---

## 🚀 Run locally

> Requires Java (JDK 17+ recommended) and Ant (optional, if you want to rebuild).

### Option A — Run the prebuilt JAR
```
java -jar jar/bullseye.jar
```

### Option B — Build & run with Ant
```bash
ant clean
ant jar
java -jar jar/bullseye.jar
```

If you change assets, keep paths consistent with `resources/` and the enum `ArrowTypes`.

---

## 🧠 Design notes

- **ArrowTypes enum** centralizes per-type config (sprite path, speed, score).
- **Entities** share common behavior via `Entity` + **Collidables** interface.
- **Factory** centralizes creation of playables/obstacles to keep `Game` lean.
- **Game loop** updates positions, checks collisions, and renders each frame.
- **Keyboard input** is handled by a dedicated `MyKeyboard` class, keeping the game logic separate and cleaner.


---

## 📦 Tech stack

- **Java** (OOP, enums, inheritance)
- **SimpleGraphics** for drawing and input
- **Ant** for build/packaging

---

## 🔭 Future improvements

- Difficulty scaling (spawn rates, speeds)
- Pause/menu screens and better HUD
- Power-ups and arrow inventory
- Extra SFX/music and sprite polish

---

## 👥 Credits

Developed by a team of four colleagues as part of the **Code for All** bootcamp.  
