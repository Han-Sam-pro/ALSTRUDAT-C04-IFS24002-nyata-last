# ALSTRUDAT-C04-IFS24002

Description
Diberikan sebuah undirected weighted graph dengan N node dan M edge. Kamu akan menerima Q query, setiap query berupa pasangan node (A, B).
Untuk setiap query, lakukan langkah berikut:
1. [Graph — Dijkstra] Temukan bobot jalur terpendek dari node A ke node B. Jika tidak ada jalur, nilai diabaikan (tidak dimasukkan ke hash table).
2. [Hash Table — Chaining] Masukkan bobot terpendek ke dalam Hash Table berukuran P (bilangan prima).

Hash function: h(k) = k % P
Collision resolution: chaining (linked list per bucket)
Jika nilai sudah ada di bucket → tambah frekuensinya (jangan duplikat)

3. [BST] Ambil semua pasangan (nilai, frekuensi) dari hash table, masukkan ke BST berdasarkan frekuensi.

Frekuensi lebih kecil → anak kiri
Frekuensi sama, nilai lebih kecil → anak kiri

4. Lakukan in-order traversal pada BST dan cetak nilai:frekuensi dipisahkan spasi.
Source Codes
NoFileDeskripsi1App.javaMain program + implementasi Dijkstra2Program.javaHash Table (chaining) + BST custom.