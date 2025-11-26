🎮 Proyek Game Java Cacing Naga
Game sederhana ini dibuat dengan Java dan dapat dijalankan langsung menggunakan Command Line (Terminal/CMD).

⬇️ Prasyarat (Prerequisites)
Untuk menjalankan game ini, Anda harus memiliki:

Java Development Kit (JDK): Versi 8 atau lebih baru.

Git: Untuk mengunduh kode sumber.

🚀 Cara Mengunduh dan Menjalankan Game
Ikuti langkah-langkah di bawah ini. Pastikan Anda sudah menginstal JDK dan Git.

Langkah 1: Kloning Repositori
Buka terminal atau Command Prompt dan gunakan perintah git clone untuk mengunduh seluruh proyek ke komputer Anda:

Bash

git clone https://github.com/[GANTI_DENGAN_USERNAME_ANDA]/cacing-naga.git
(Ganti [GANTI_DENGAN_USERNAME_ANDA] dengan nama pengguna GitHub Anda.)

Langkah 2: Navigasi ke Folder Proyek
Masuk ke folder proyek yang baru saja diunduh, lalu masuk ke folder utama kode:

Bash

cd cacing-naga
cd cacingNaga 
Langkah 3: Kompilasi Kode Sumber
Kompilasi semua file sumber Java (.java) Anda yang berada di dalam folder src untuk menghasilkan file kelas (.class):

Bash

# Kompilasi semua file .java di direktori src
javac ./lib/src/*.java
Langkah 4: Jalankan Game
Setelah kompilasi berhasil, jalankan file kelas utama Anda, yang kami asumsikan adalah GameEvolusi:

Bash

# Jalankan kelas utama (perhatikan penulisan nama paket/folder)
java lib.src.GameEvolusi
Game Cacing Naga Anda sekarang akan terbuka!

📁 Struktur Proyek (Untuk Pengembang)
cacing-naga/
├── .gitignore
├── README.md
├── cacingNaga/
│   ├── lib/
│   │   └── src/             # Folder berisi file .java utama
│   │       ├── resources/   # Aset (gambar cacing.png, naga.png, dll.)
│   │       ├── ChartPanel.java
│   │       └── GameEvolusi.java
└── ...
