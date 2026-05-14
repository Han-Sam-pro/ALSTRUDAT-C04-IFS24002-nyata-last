package del.ifs24002;

import java.util.*;

public class Program {

    // =========================================================
    // HASH TABLE dengan Chaining (Linked List per bucket)
    // =========================================================
    public static class HashTable {

        // Node untuk linked list di setiap bucket
        static class HNode {
            int nilai;
            int frekuensi;
            HNode next;

            HNode(int nilai) {
                this.nilai     = nilai;
                this.frekuensi = 1;
                this.next      = null;
            }
        }

        int p;             // ukuran hash table (bilangan prima)
        HNode[] table;     // array of linked list head

        public HashTable(int p) {
            this.p     = p;
            this.table = new HNode[p];
        }

        // Hash function
        private int hash(int k) {
            return k % p;
        }

        // Insert nilai ke hash table
        // Jika nilai sudah ada di bucket → tambah frekuensi
        // Jika belum ada → buat node baru
        public void insert(int nilai) {
            int idx = hash(nilai);

            HNode cur = table[idx];
            while (cur != null) {
                if (cur.nilai == nilai) {
                    cur.frekuensi++;
                    return;
                }
                cur = cur.next;
            }

            // Nilai belum ada, tambah di awal linked list (prepend)
            HNode newNode = new HNode(nilai);
            newNode.next  = table[idx];
            table[idx]    = newNode;
        }

        // Ambil semua pasangan (nilai, frekuensi) dari seluruh bucket
        public List<int[]> getAllEntries() {
            List<int[]> entries = new ArrayList<>();
            for (int i = 0; i < p; i++) {
                HNode cur = table[i];
                while (cur != null) {
                    entries.add(new int[]{cur.nilai, cur.frekuensi});
                    cur = cur.next;
                }
            }
            return entries;
        }
    }

    // =========================================================
    // BST — key = frekuensi, tie-break = nilai (kecil ke kiri)
    // =========================================================
    public static class BST {

        public static class BSTNode {
            int nilai;
            int frekuensi;
            BSTNode left, right;

            BSTNode(int nilai, int frekuensi) {
                this.nilai      = nilai;
                this.frekuensi  = frekuensi;
                this.left       = null;
                this.right      = null;
            }
        }

        public BSTNode root;

        public BST() {
            this.root = null;
        }

        // Insert node baru dengan (nilai, frekuensi)
        // Aturan BST:
        //   frek baru < frek node  → ke kiri
        //   frek baru > frek node  → ke kanan
        //   frek sama, nilai kecil → ke kiri
        //   frek sama, nilai besar → ke kanan
        public void insert(int nilai, int frekuensi) {
            root = insertRec(root, nilai, frekuensi);
        }

        private BSTNode insertRec(BSTNode node, int nilai, int frekuensi) {
            if (node == null) {
                return new BSTNode(nilai, frekuensi);
            }

            if (frekuensi < node.frekuensi) {
                node.left  = insertRec(node.left, nilai, frekuensi);
            } else if (frekuensi > node.frekuensi) {
                node.right = insertRec(node.right, nilai, frekuensi);
            } else {
                // frekuensi sama → bandingkan nilai
                if (nilai < node.nilai) {
                    node.left  = insertRec(node.left, nilai, frekuensi);
                } else if (nilai > node.nilai) {
                    node.right = insertRec(node.right, nilai, frekuensi);
                }
                // nilai sama persis → abaikan (tidak mungkin terjadi)
            }

            return node;
        }

        // In-order traversal: kiri → root → kanan
        // Menghasilkan urutan frekuensi kecil ke besar
        // (frekuensi sama → nilai kecil ke besar)
        public void inOrder(BSTNode node, List<String> result) {
            if (node == null) return;
            inOrder(node.left, result);
            result.add(node.nilai + ":" + node.frekuensi);
            inOrder(node.right, result);
        }
    }
}