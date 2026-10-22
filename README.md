# 🚆 Railway Reservation System

A Java console-based reservation project with a responsive browser demo.

## 🌐 Live Website
https://poochi005.github.io/RailwayReservationSystem/

## Features in the GitHub Pages demo
- Search sample train routes
- Create demo bookings and generate a PNR
- Check demo PNR status
- Cancel demo bookings
- View bookings saved in the current browser

> **Important:** GitHub Pages hosts static files only. The website demo uses sample data and browser local storage. It is not connected to Indian Railways/IRCTC and does not make real reservations or payments. The Java console application in `src/` is separate from the browser demo.

## Run the Java console application
Requires JDK 17 or later.

```bash
./build.sh
```

On Windows, compile and run from the project root:

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName)
java -cp out railway.Main
```

## GitHub Pages setup
1. Open the repository **Settings → Pages**.
2. Under **Build and deployment**, select **Deploy from a branch**.
3. Select branch `main` and folder `/ (root)`, then save.
4. Ensure `index.html` is in the repository root.
5. Wait a few minutes and refresh the live website.

## Project structure
```text
RailwayReservationSystem/
├── index.html
├── README.md
├── build.sh
├── src/railway/
│   ├── Main.java
│   ├── model/
│   ├── service/
│   ├── util/
│   └── exception/
└── data/
```

## Author
Poochi005 — https://github.com/Poochi005
