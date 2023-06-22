package com.melting.util;

import java.io.File;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileService {
	
	public static String savedFile(MultipartFile upload, String uploadPath) {
		// 파일이 첨부됐을 시 저장할 폴더가 존재하는지 확인
		if(!upload.isEmpty()) {
			File path = new File(uploadPath);	// 설정정보에서 업로드 경로를 읽기. uploadPath 선언.
			if(!path.isDirectory()) // 경로가 존재하지 않으면 경로 생성.
				path.mkdirs();	
		}
		
		/*원본이름과 난수가 합쳐진 파일이름 둘다 저장*/
		
		// 원본의 파일명
		String originalFilename = upload.getOriginalFilename();
		
		// 랜덤값 발생
		String uuid = UUID.randomUUID().toString();	//유효아이디
		
		// 원본 파일명과 파일의 확장명을 분리
		String filename;			// 파일의 이름
		String ext;					// 파일의 확장명
		String savedFileName;		// 하드디스크에 저장할 이름
		
		int lastIndex = originalFilename.lastIndexOf(".");
		filename = originalFilename.substring(0, lastIndex);
		
		// 확장자가 없는 경우
		if(lastIndex == -1) ext= "";
		
		// 확장자가 있는 경우
		else {
			ext = "."+originalFilename.substring(lastIndex+1);
		}
		
		
		/*DB에 저장될 수 있도록 파일 세팅*/
		
		// 하드에 저장될 파일명 세팅
		savedFileName = filename + "_" + uuid + ext;
		
		// "저장하려는 경로" + "파일명"으로 실제 저장
		File serverFile = null;
		serverFile = new File(uploadPath + "/" + savedFileName);
		
		try {
			upload.transferTo(serverFile);
		} catch (Exception e) {
			savedFileName= null;
			e.printStackTrace();
		}
		
		return savedFileName;
	}
	
	/* 파일 삭제 여부 */
	public static boolean deleteFile(String fullPath) {
		boolean result = false;	// 삭제 여부 반환
		
		File delFile = new File(fullPath);
		
		// 파일이 fullpath 내에 존재 시 삭제
		if(delFile.isFile()) {
			delFile.delete();
			result = true;
		}
		
		return result;
	}

}

