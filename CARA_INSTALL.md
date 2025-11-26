# 🎮 Game Java --- *Cacing Naga*

Game sederhana berbasis Java yang dapat dijalankan langsung melalui
**Command Line (Terminal/CMD)** tanpa membutuhkan IDE seperti VS Code
atau Eclipse. Cocok untuk latihan dasar pemrograman Java dan pengelolaan
proyek sederhana.

------------------------------------------------------------------------

## 📌 Prasyarat (Prerequisites)

Sebelum menjalankan game ini, pastikan perangkat Anda memiliki:

-   **Java Development Kit (JDK)** --- Versi **8 atau lebih baru**
-   **Git** --- Untuk mengunduh (clone) repository

------------------------------------------------------------------------

## 🚀 Cara Mengunduh dan Menjalankan Game

### **1. Kloning Repositori**

Unduh project ke komputer Anda menggunakan Git:

``` bash
git clone https://github.com/[GANTI_DENGAN_USERNAME_ANDA]/cacing-naga.git
```

> Ganti `[GANTI_DENGAN_USERNAME_ANDA]` dengan username GitHub Anda.

------------------------------------------------------------------------

### **2. Masuk ke Folder Proyek**

``` bash
cd cacing-naga
cd cacingNaga
```

------------------------------------------------------------------------

### **3. Kompilasi Kode Sumber**

Kompilasi seluruh file `.java` di folder `src`:

``` bash
javac ./lib/src/*.java
```

------------------------------------------------------------------------

### **4. Jalankan Game**

Setelah kompilasi berhasil, jalankan kelas utama **GameEvolusi**:

``` bash
java lib.src.GameEvolusi
```

Game Anda sekarang akan terbuka dan siap dimainkan! 🎉🐉

------------------------------------------------------------------------

# 📁 Struktur Proyek

    cacing-naga/
    ├── .gitignore
    ├── README.md
    ├── cacingNaga/
    │   ├── lib/
    │   │   └── src/             
    │   │       ├── resources/     # Aset game (cacing.png, naga.png, dll.)
    │   │       ├── ChartPanel.java
    │   │       └── GameEvolusi.java
    └── ...

------------------------------------------------------------------------

## ✨ Fitur Game

-   Evolusi karakter dari **cacing → naga**
-   Tampilan sederhana berbasis Java Swing / AWT
-   Kontrol mudah
-   Ringan, berjalan di semua OS (Windows, Linux, macOS)

------------------------------------------------------------------------

## 🤝 Kontribusi

Pull request, ide, atau perbaikan kode sangat diterima!

------------------------------------------------------------------------

## 📜 Lisensi

Project ini menggunakan lisensi bebas --- silakan gunakan, modifikasi,
dan kembangkan.
