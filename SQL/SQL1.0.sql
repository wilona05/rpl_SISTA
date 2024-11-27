-- DROP DATABASE IF EXISTS SistaDB;
-- CREATE DATABASE SistaDB;

DROP TABLE IF EXISTS Admins CASCADE;
DROP TABLE IF EXISTS Mahasiswa CASCADE;
DROP TABLE IF EXISTS Dosen CASCADE;
DROP TABLE IF EXISTS DosenKoordinator CASCADE;
DROP TABLE IF EXISTS SidangTA CASCADE;
DROP TABLE IF EXISTS NilaiSidang CASCADE;
DROP TABLE IF EXISTS PembimbingSidang CASCADE;
DROP TABLE IF EXISTS PengujiSidang CASCADE;
DROP TABLE IF EXISTS KomponenNilai CASCADE;
CREATE TABLE Admins (
    email VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL
);

-- Tabel Mahasiswa
CREATE TABLE Mahasiswa (
    NPM VARCHAR(10) PRIMARY KEY,  
    nama VARCHAR(50) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,  
    passwords VARCHAR(50) NOT NULL
);

--Tabel Dosen
CREATE TABLE Dosen (
	NIP VARCHAR(8) PRIMARY KEY,
	nama VARCHAR(50),
	email VARCHAR(50),
	passwords VARCHAR(50)
);

CREATE TABLE DosenKoordinator (
	idDosenKoordinator int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	NIP VARCHAR(8) REFERENCES Dosen(NIP),
	periode VARCHAR(50)
);


-- Tabel SidangTA
CREATE TABLE SidangTA (
    IDSidang int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    jenisTA INT, 
    judulTA VARCHAR(100) NOT NULL,
    jadwal TIMESTAMP NOT NULL,
    tempat VARCHAR(50) NOT NULL,
    semester VARCHAR(10),  
    tahunAjaran CHAR(10) NOT NULL, 
    catatanRevisi TEXT,
	idDosenKoordinator int REFERENCES DosenKoordinator(idDosenKoordinator),
    NPM CHAR(10) REFERENCES Mahasiswa(NPM)  
);

CREATE TABLE PembimbingSidang (
	NIP VARCHAR(8) REFERENCES Dosen,
	urutan VARCHAR(15),
	IDSidang int REFERENCES SidangTA(IDSidang)
);

CREATE TABLE PengujiSidang (
	NIP VARCHAR(8) REFERENCES Dosen(NIP),
	jabatan VARCHAR(50),
	IDSidang int REFERENCES SidangTA(IDSidang)
);

-- Tabel KomponenNilai
CREATE TABLE KomponenNilai (
    IDKomp int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    komponen VARCHAR(50) NOT NULL,
    bobot DECIMAL(5, 2),
    roles VARCHAR(50) NOT NULL
);

-- Tabel NilaiSidang
CREATE TABLE NilaiSidang (
    IDSidang INT REFERENCES SidangTA(IDSidang) ,
    IDKomp INT REFERENCES KomponenNilai(IDKomp) ,
    nilai DECIMAL(5, 2)
);

-- Insert data into Admins
INSERT INTO Admins (email, password) VALUES
('admin@sista.edu', 'admin123');

-- Insert data into Mahasiswa
INSERT INTO Mahasiswa (NPM, nama, email, passwords) VALUES
('2001001', 'Alice', 'alice@student.edu', 'password1'),
('2001002', 'Bob', 'bob@student.edu', 'password2'),
('2001003', 'Charlie', 'charlie@student.edu', 'password3'),
('2001004', 'Diana', 'diana@student.edu', 'password4'),
('2001005', 'Eve', 'eve@student.edu', 'password5'),
('2001006', 'Frank', 'frank@student.edu', 'password6');

-- Insert data into Dosen
INSERT INTO Dosen (NIP, nama, email, passwords) VALUES
('19810001', 'Dr. John', 'john@dosen.edu', 'password1'),
('19810002', 'Prof. Smith', 'smith@dosen.edu', 'password2'),
('19810003', 'Dr. Clara', 'clara@dosen.edu', 'password3'),
('19810004', 'Dr. Brown', 'brown@dosen.edu', 'password4');

-- Insert data into DosenKoordinator
INSERT INTO DosenKoordinator (NIP, periode) VALUES
('19810001', '2023/2024'),
('19810002', '2022/2023');

-- Insert data into SidangTA (3 in semester Ganjil, 3 in semester Genap)
INSERT INTO SidangTA (jenisTA, judulTA, jadwal, tempat, semester, tahunAjaran, catatanRevisi, idDosenKoordinator, NPM) VALUES
(1, 'Aplikasi Manajemen Keuangan', '2024-01-15 09:00:00', 'Ruang 101', 'Ganjil', '2022/2023', 'Revisi Bab 3', 2, '2001001'),
(2, 'Sistem Pendukung Keputusan', '2024-01-20 10:00:00', 'Ruang 102', 'Ganjil', '2023/2024', 'Revisi Bab 2', 1, '2001002'),
(1, 'Aplikasi E-Learning', '2024-01-25 11:00:00', 'Ruang 103', 'Ganjil', '2023/2024', 'Revisi Bab 4', 1, '2001003'),
(1, 'Sistem Inventory', '2024-07-15 09:00:00', 'Ruang 201', 'Genap', '2022/2023', 'Revisi Bab 1', 2, '2001004'),
(2, 'Aplikasi Smart Home', '2024-07-20 10:00:00', 'Ruang 202', 'Genap', '2023/2024', 'Revisi Bab 2', 1, '2001005'),
(2, 'Aplikasi Monitoring Anak', '2024-07-25 11:00:00', 'Ruang 203', 'Genap', '2023/2024', 'Revisi Bab 3', 1, '2001006');

-- Insert data into PembimbingSidang
INSERT INTO PembimbingSidang (NIP, urutan, IDSidang) VALUES
('19810001', 'Pembimbing 1', 1),
('19810002', 'Pembimbing 2', 1),
('19810003', 'Pembimbing 1', 2),
('19810004', 'Pembimbing 2', 2),
('19810001', 'Pembimbing 1', 3),
('19810002', 'Pembimbing 2', 3),
('19810001', 'Pembimbing 1', 4),
('19810002', 'Pembimbing 2', 4),
('19810003', 'Pembimbing 1', 5),
('19810004', 'Pembimbing 2', 5),
('19810001', 'Pembimbing 1', 6),
('19810002', 'Pembimbing 2', 6);

-- Insert data into PengujiSidang
INSERT INTO PengujiSidang (NIP, jabatan, IDSidang) VALUES
('19810003', 'Ketua Penguji', 1),
('19810004', 'Anggota Penguji', 1),
('19810003', 'Ketua Penguji', 2),
('19810004', 'Anggota Penguji', 2),
('19810003', 'Ketua Penguji', 3),
('19810004', 'Anggota Penguji', 3),
('19810003', 'Ketua Penguji', 4),
('19810004', 'Anggota Penguji', 4),
('19810003', 'Ketua Penguji', 5),
('19810004', 'Anggota Penguji', 5),
('19810003', 'Ketua Penguji', 6),
('19810004', 'Anggota Penguji',6);

-- Insert data into KomponenNilai
INSERT INTO KomponenNilai (komponen, bobot, roles) VALUES
('Presentasi', 40.00, 'Ketua Penguji'),
('Dokumentasi', 30.00, 'Ketua Penguji'),
('Pemahaman', 30.00, 'Ketua Penguji'),
('Presentasi', 30.00, 'Koordinator'),
('Dokumentasi', 20.00, 'Koordinator'),
('Pemahaman', 50.00, 'Koordinator'),
('Presentasi', 20.00, 'Pembimbing 1'),
('Dokumentasi', 50.00, 'Pembimbing 1'),
('Pemahaman', 30.00, 'Pembimbing 1'),
('Presentasi', 20.00, 'Pembimbing 2'),
('Dokumentasi', 50.00, 'Pembimbing 2'),
('Pemahaman', 30.00, 'Pembimbing 2'),
('Presentasi', 20.00, 'Anggota Penguji'),
('Dokumentasi', 50.00, 'Anggota Penguji'),
('Pemahaman', 30.00, 'Anggota Penguji')
;

-- Insert data into NilaiSidang
INSERT INTO NilaiSidang (IDSidang, IDKomp, nilai) VALUES
(1, 1, 85.00),
(1, 2, 90.00),
(1, 3, 88.00),
(1, 4, 85.00),
(1, 5, 90.00),
(1, 6, 88.00),
(1, 7, 85.00),
(1, 8, 90.00),
(1, 9, 88.00),
(2, 1, 80.00),
(2, 2, 85.00),
(2, 3, 87.00),
(3, 1, 78.00),
(3, 2, 82.00),
(3, 3, 80.00),
(4, 1, 88.00),
(4, 2, 92.00),
(4, 3, 90.00),
(5, 1, 86.00),
(5, 2, 89.00),
(5, 3, 88.00),
(6, 1, 84.00),
(6, 2, 87.00),
(6, 3, 85.00);
