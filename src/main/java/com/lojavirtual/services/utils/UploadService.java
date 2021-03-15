package com.lojavirtual.services.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lojavirtual.services.exceptions.FileException;


@Service
public class UploadService {

	public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
	
	public String uploadPicture(MultipartFile file, Long id, String prefix, String directory) {
		try {
			String extensao = FilenameUtils.getExtension(file.getOriginalFilename());
			String nomeArquivo = prefix + id.toString() + "." + extensao;
			String clientDirectory = UPLOAD_DIRECTORY + directory;
			
			new File(clientDirectory).mkdir();
			Path fileNameAndPath = Paths.get(clientDirectory, nomeArquivo);
			Files.write(fileNameAndPath, file.getBytes());
			
			return nomeArquivo;
		} catch (Exception e) {
			throw new FileException("Erro no upload da imagem!");
		}
	}

	public void showData(String picture, HttpServletResponse response) {
		try {
			File file = new File(UPLOAD_DIRECTORY + "/" + picture);
			
			try(InputStream stream = new FileInputStream(file)) {
				response.setHeader("Content-Disposition", "attachment; filename=" + picture);
				response.setContentType("application/force-download");
				IOUtils.copy(stream, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (Exception e) {
			throw new FileException("Erro ao buscar arquivo!");
		}
	}
	
}
