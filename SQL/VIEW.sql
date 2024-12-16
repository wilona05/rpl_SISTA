-- view info sidang secara menyeluruh
SELECT * FROM infosidang 
	WHERE idsidang = 1

CREATE OR REPLACE VIEW infoSidang AS
SELECT
	m.nama AS namaMahasiswa, m.NPM, m.email,
	s.idsidang, s.judulTA, s.jenisTA, s.jadwal, s.tempat, s.semester,
	s.tahunAjaran, s.catatanrevisi,
COALESCE
	(dPembimbingUtama.nama, '-') AS pembimbingUtama,
COALESCE
	(dPembimbingTambahan.nama, '-') AS pembimbingTambahan,
COALESCE
	(dKetuaPenguji.nama, '-') AS ketuaPenguji,
COALESCE
	(dAnggotaPenguji.nama, '-') AS anggotaPenguji
FROM
	SidangTA s
JOIN
	Mahasiswa m ON s.NPM = m.NPM
LEFT JOIN
	DosenSidang dsPembimbingUtama ON s.IDSidang = dsPembimbingUtama.idSidang
	AND dsPembimbingUtama.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Pembimbing 1')
LEFT JOIN
	Dosen dPembimbingUtama ON dsPembimbingUtama.NIP = dPembimbingUtama.NIP
LEFT JOIN
	DosenSidang dsPembimbingTambahan ON s.IDSidang = dsPembimbingTambahan.idSidang
	AND dsPembimbingTambahan.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Pembimbing 2')
LEFT JOIN
	Dosen dPembimbingTambahan ON dsPembimbingTambahan.NIP = dPembimbingTambahan.NIP
LEFT JOIN
	DosenSidang dsKetuaPenguji ON s.IDSidang = dsKetuaPenguji.idSidang
	AND dsKetuaPenguji.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Penguji 1')
LEFT JOIN
	Dosen dKetuaPenguji ON dsKetuaPenguji.NIP = dKetuaPenguji.NIP
LEFT JOIN
	DosenSidang dsAnggotaPenguji ON s.IDSidang = dsAnggotaPenguji.idSidang 
	AND dsAnggotaPenguji.idRole = (SELECT IdRole FROM RoleDosen WHERE namaRole = 'Penguji 2')
LEFT JOIN
	Dosen dAnggotaPenguji ON dsAnggotaPenguji.NIP = dAnggotaPenguji.NIP

-- view sidang dan mahasiswa
SELECT * FROM mahasiswasidang

CREATE OR REPLACE VIEW mahasiswasidang AS
SELECT
	s.*, m.nama AS namaMahasiswa
FROM
	Sidangta s
JOIN
	mahasiswa m ON s.npm = m.npm;
	
-- view untuk mencari sidang untuk role penguji
CREATE OR REPLACE VIEW sidangpenguji AS
SELECT
	s.*, ds.nip, m.nama
FROM
	Sidangta s
JOIN
	Mahasiswa m ON s.npm = m.npm
JOIN
	Dosensidang ds ON s.idsidang = ds.idsidang
WHERE
	(ds.idrole = 2 OR ds.idrole = 3) -- AND dosensidang.nip = ?

SELECT * FROM sidangpenguji
SELECT * FROM komponennilai

-- view untuk mencari sidang untuk role pembimbing
CREATE OR REPLACE VIEW sidangpembimbing AS
SELECT
	s.*, ds.nip, m.nama
FROM
	Sidangta s
JOIN
	Mahasiswa m ON s.npm = m.npm
JOIN
	Dosensidang ds ON s.idsidang = ds.idsidang
WHERE
	(ds.idrole = 4 OR ds.idrole = 5) -- AND dosensidang.nip = ?";

-- view untuk melihat list sidang berdasarkan nama
CREATE OR REPLACE VIEW listsidang AS
SELECT
	s.*, ds.nip, m.nama
FROM
	Sidangta s
JOIN
	Mahasiswa m ON s.npm = m.npm
JOIN
	Dosensidang ds ON s.idsidang = ds.idsidang

SELECT * FROM dosensidang