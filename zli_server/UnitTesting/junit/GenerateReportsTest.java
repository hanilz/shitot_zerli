package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.Report;
import util.GenerateReports;

class GenerateReportsTest {
	
	private Report reportExistingBranch;
	private Report reportNotExistingBranch;

	@BeforeEach
	void setUp() throws Exception {
		reportExistingBranch = new Report("'2022-04-01 00:00:00' and '2022-06-29 00:00:00'", "income histogram", 1);
		reportNotExistingBranch = new Report("'2022-04-01 00:00:00' and '2022-06-31 00:00:00'", "income histogram", 4);
	}

	@Test
	void getIncomeHistogramReport_ExistingBranch() {
		Map<String,Integer> existingBranchData = getReportExistingBranchData();
		Map<String,Integer> result = GenerateReports.getIncomeHistogramReport(reportExistingBranch);
		
		assertEquals(existingBranchData, result);
	}
	
	private Map<String, Integer> getReportExistingBranchData() {
		Map<String, Integer> reportExistingBranchData = new HashMap<>();
		reportExistingBranchData.put("April",(int) 25324.03);
		reportExistingBranchData.put("May",(int) 254092.14);
		reportExistingBranchData.put("June",(int) 19387.28);
		return reportExistingBranchData;
	}

}
