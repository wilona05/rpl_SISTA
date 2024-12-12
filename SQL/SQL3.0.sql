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
DROP TABLE IF EXISTS DosenSidang CASCADE;
DROP TABLE IF EXISTS RoleDosen CASCADE;
DROP TABLE IF EXISTS bobotDosen CASCADE;

CREATE TABLE Admins (
    email VARCHAR(50) PRIMARY KEY,
    passwords VARCHAR(50) NOT NULL
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
	passwords VARCHAR(50), 
	statusKoordinator BOOLEAN
);

-- Tabel SidangTA
CREATE TABLE SidangTA (
    IDSidang int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    jenisTA INT, -- 1/2
    judulTA VARCHAR(100) NOT NULL,
    jadwal TIMESTAMP NOT NULL,-- yy-mm-dd hh-mm-ss
    tempat VARCHAR(50) NOT NULL,
    semester VARCHAR(10), --ganjil genap 
    tahunAjaran CHAR(11) NOT NULL, --xxxx/yyyy
    catatanRevisi TEXT,
    NPM CHAR(10) REFERENCES Mahasiswa(NPM), 
	nilaiKoordinator int
);

CREATE TABLE RoleDosen (
    IdRole int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    namaRole VARCHAR(50) NOT NULL --Koordinator, Penguji 1, Penguji 2, Pembimbing 1, Pembimbing 2)
);

CREATE TABLE DosenSidang (
	idSidang int REFERENCES sidangTA(IDSidang),
	NIP VARCHAR(8) REFERENCES Dosen(NIP),
	idRole int REFERENCES RoleDosen(idRole)
);

-- Tabel bobot per dosen (bukan komponen)
CREATE TABLE bobotDosen (
    idBobot int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    jenisTA int NOT NULL,
	bobot DECIMAL(5,2) NOT NULL,
	tglBerlaku DATE NOT NULL,
    idRole int REFERENCES RoleDosen(idRole)
);

-- Tabel KomponenNilai
CREATE TABLE KomponenNilai (
    IDKomp int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    komponen VARCHAR(50) NOT NULL,
    bobotPenguji DECIMAL(5, 2),
	bobotPembimbing DECIMAL(5, 2),
	tglBerlaku DATE
);

-- Tabel NilaiSidang
CREATE TABLE NilaiSidang (
    IDSidang INT REFERENCES SidangTA(IDSidang) ,
    IDKomp INT REFERENCES KomponenNilai(IDKomp) ,
    nilaiPenguji1 DECIMAL(5, 2), --nilai ketua penguji per komponen
	nilaiPenguji2 DECIMAL(5, 2), -- nilainya -1 jika hanya jika ada 1 penguji
	nilaiPembimbing DECIMAL(5, 2) --nilai per komponen (bukan total nilai dosen ini)
	
);

-- Insert data into Admins
INSERT INTO Admins (email, passwords) VALUES
('admin@sista.edu', 'admin123');

-- Insert data into Mahasiswa
INSERT INTO Mahasiswa (NPM, nama, email, passwords) VALUES
('2001001', 'Alice Bondi', '2001001@student.edu', 'password1'),
('2001002', 'Bob Bobby', '2001002@student.edu', 'password2'),
('2001003', 'Charlie Coipy', '2001003@student.edu', 'password3'),
('2001004', 'Diana Prina', '2001004@student.edu', 'password4'),
('2001005', 'Eve Young', '2001005@student.edu', 'password5'),
('2001006', 'Frank Stein', '2001006@student.edu', 'password6');

-- Insert data into Dosen
INSERT INTO Dosen (NIP, nama, email, passwords, statusKoordinator) VALUES
('19810001', 'Dr. John', '19810001@dosen.edu', 'password1', false),
('19810002', 'Prof. Smith', '19810002@dosen.edu', 'password2', true),
('19810003', 'Dr. Clara', '19810003@dosen.edu', 'password3', false),
('19810004', 'Dr. Andi', '19810004@dosen.edu', 'password3', false),
('19810005', 'Dr. Brown', '19810005@dosen.edu', 'password4', false);

--insert data roleDosen
INSERT INTO RoleDosen (namaRole) VALUES
('Koordinator'),
('Penguji 1'),
('Penguji 2'),
('Pembimbing 1'),
('Pembimbing 2');

INSERT INTO bobotDosen (jenisTA, bobot, tglBerlaku, idRole) VALUES
(2, 35.00, '2023-01-01', 2),
(2, 35.00, '2023-01-01', 3),
(2, 20.00, '2023-01-01', 4),
(2, 10.00, '2023-01-01', 1),
(1, 50.00, '2023-01-01', 2),
(1, 30.00, '2023-01-01', 4),
(1, 20.00, '2023-01-01', 1);

-- Insert data into SidangTA (3 in semester Ganjil, 3 in semester Genap)
INSERT INTO SidangTA (jenisTA, judulTA, jadwal, tempat, semester, tahunAjaran, catatanRevisi, NPM, nilaiKoordinator) VALUES
(1, 'Aplikasi Manajemen Keuangan', '2024-01-15 09:00:00', 'Ruang 101', 'Ganjil', '2022/2023', 'Revisi Bab 3',  '2001001',100.00),
(2, 'Sistem Pendukung Keputusan', '2024-01-20 10:00:00', 'Ruang 102', 'Ganjil', '2023/2024', 'Revisi Bab 2', '2001002',90.00),
(1, 'Aplikasi E-Learning', '2024-01-25 11:00:00', 'Ruang 103', 'Ganjil', '2023/2024', 'Revisi Bab 4','2001003',80.00),
(1, 'Sistem Inventory', '2024-07-15 09:00:00', 'Ruang 201', 'Genap', '2022/2023', 'Revisi Bab 1',  '2001004',100.00),
(2, 'Aplikasi Smart Home', '2024-07-20 10:00:00', 'Ruang 202', 'Genap', '2023/2024', 'Revisi Bab 2', '2001005',70.00),
(2, 'Aplikasi Monitoring Anak', '2024-07-25 11:00:00', 'Ruang 203', 'Genap', '2023/2024', 'Revisi Bab 3','2001006',80.00);

INSERT INTO DosenSidang (idSidang, NIP, idRole) VALUES
(1,19810001,2),
(1,19810003,4),
(2,19810001,2),
(2,19810003,3),
(2,19810004,4),
(2,19810005,5),
(3,19810002,2),
(3,19810003,4),
(3,19810005,5),
(4,19810004,2),
(4,19810005,4),
(5,19810003,2),
(5,19810001,3),
(5,19810002,4),
(6,19810003,2),
(6,19810004,3),
(6,19810005,4);


-- Insert data into KomponenNilai
INSERT INTO KomponenNilai (komponen, bobotPembimbing, bobotPenguji, tglBerlaku) VALUES
('Presentasi', 40.00, 30.00, '2023-01-01'),
('Dokumentasi', 30.00, 40.00,'2023-01-01'),
('Pemahaman', 30.00, 30.00,'2023-01-01')
;

-- Insert data into NilaiSidang
INSERT INTO NilaiSidang (IDSidang, IDKomp, nilaiPembimbing, nilaiPenguji1, nilaiPenguji2) VALUES
(1,1,85.00, 90.00, -1),
(1,2,80.00, 90.00,-1),
(1,3,70.00,70.00,-1),
(2,1,90.00,91.00,92.00),
(2,2,80.00,90.00,85.00),
(2,3,75.00,80.00,85.00),
(3,1,70.00,80.00,-1),
(3,2,70.00,80.00,-1),
(3,3,80.00,90.00,-1),
(4,1,70.00,80.00,-1),
(4,2,70.00,80.00,-1),
(4,3,80.00,90.00,-1),
(5,1,90.00,91.00,92.00),
(5,2,80.00,90.00,85.00),
(5,3,75.00,80.00,85.00),
(6,1,90.00,91.00,92.00),
(6,2,80.00,90.00,85.00),
(6,3,75.00,80.00,85.00);


CREATE VIEW listUser AS
SELECT 
	npm AS noInduk, nama, email, passwords
FROM 
	mahasiswa
UNION ALL
SELECT 
	nip AS noInduk, nama, email, passwords
FROM 
	dosen
UNION ALL
SELECT 
	'0' AS noInduk, 'admin' AS nama, email, passwords
FROM 
	admins;
