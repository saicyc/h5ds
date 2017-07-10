package com.chinait.service;

import com.chinait.domain.Apps;
import com.chinait.domain.Cases;
import com.chinait.utils.QRCodeUtils;
import com.chinait.vo.CasesVO;
import com.chinait.vo.PageVO;

public interface CaseService {

	int saveCases(String qrUrl, String fileUrl, QRCodeUtils utils, String url, boolean isSystem, String string, Apps app) throws Exception;

	void findAll(PageVO<CasesVO> pageVo);
	
	Cases findOneCases(int casesId);

	void deleteCases(int casesId);

	void findAllCase(PageVO<CasesVO> page);

	void hideCases(int casesId);
}
