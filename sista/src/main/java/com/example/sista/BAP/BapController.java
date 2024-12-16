package com.example.sista.BAP;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;

@Controller
@RequestMapping("/bap")
public class BapController {

    private String uploadDir;

    @Autowired
    private bapRepo repo;

    private static final String ROOT_FOLDER = "bap-uploads";

    @Autowired
    public BapController(@Value("${bap.upload-dir}") String uploadDir) {
        this.uploadDir = uploadDir;
    }

    @GetMapping("/")
    public String showBapPage(int idSidang, Model model) {// ini harusnya ada req param id sidang tapi itu bergantung
                                                          // sama routing
        // sebelumnya kayaknya soalnya alurnya /sidang?=1/bap

        // mengambil data isi bap dan nilai dari database berdasarkan id sidang lalu
        // menambahkannya ke model
        model.addAttribute("isiBAP", repo.findIsiBAP(idSidang));
        model.addAttribute("nilai", repo.findNilaiBAP(idSidang));
        model.addAttribute("idSidang", idSidang);

        // mencari file terbaru di folder bap-uploads
        File folder = new File(ROOT_FOLDER);
        if (folder.exists() && folder.isDirectory()) {
            // memfilter file dengan prefix id sidang dan mencari file yang terakhir
            // dimodifikasi
            File latestFile = Arrays.stream(folder.listFiles())
                    .filter(file -> file.getName().startsWith(idSidang + "_")) // hanya file yang namanya diawali id
                                                                               // sidang yang diambil
                    .max(Comparator.comparingLong(File::lastModified)) // mencari file yang terbaru berdasarkan waktu
                                                                       // terakhir modifikasi
                    .orElse(null);

            if (latestFile != null) {
                // menambahkan path file terbaru ke model agar bisa ditampilkan di halaman
                model.addAttribute("latestFilePath", "/bap-uploads/" + latestFile.getName());
            } else {
                // jika tidak ada file yang ditemukan maka latestfilepath diatur null
                model.addAttribute("latestFilePath", null);
            }
        } else {
            // jika folder tidak ada atau kosong maka latestfilepath diatur null
            model.addAttribute("latestFilePath", null);
        }

        // mengembalikan tampilan halaman bap
        return "BAP/tampilanBAP";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("idSidang") int idSidang,
            Model model) {

        try {
            // membuat nama file yang unik dengan id sidang dan timestamp
            String timestamp = String.valueOf(Instant.now().toEpochMilli());
            String filename = idSidang + "_" + timestamp + ".pdf";

            // memastikan folder upload sudah ada jika belum maka akan dibuat
            Path targetDir = Paths.get(uploadDir);
            if (!targetDir.toFile().exists()) {
                targetDir.toFile().mkdirs();
            }

            // menyimpan file yang diupload ke folder target dengan nama yang sudah dibuat
            File targetFile = new File(targetDir.toFile(), filename);
            file.transferTo(targetFile);

            // menambahkan pesan sukses ke model untuk ditampilkan di halaman
            model.addAttribute("message", "file berhasil diunggah");
            model.addAttribute("latestFilePath", "/bap-uploads/" + filename);
        } catch (IOException e) {
            // jika terjadi error saat mengupload file maka menampilkan pesan error
            e.printStackTrace();
            model.addAttribute("message", "gagal mengunggah file");
        }

        // mengambil kembali data isi bap dan nilai untuk ditampilkan ulang setelah
        // proses upload
        model.addAttribute("isiBAP", repo.findIsiBAP(idSidang));
        model.addAttribute("nilai", repo.findNilaiBAP(idSidang));
        model.addAttribute("idSidang", idSidang);

        // mengembalikan tampilan halaman bap dengan data dan pesan feedback
        return "BAP/tampilanBAP";
    }
}
