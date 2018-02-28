package com.lawu.excel.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.junit.Assert;
import org.junit.Test;

/**
 * ExcelUtils单元测试
 *
 * @author Leach
 * @since 2016/3/16
 */
public class ExcelUtilsTest {

    @Test
    public void exportExcel() {

        // 23020
        int one = 19250;
        int eight = 3745;
        int ete = 25;

        int oneP = 830;
        int eightP = 160;
        int eteP = 10;

        final int total = one + eight + ete;

        ZipSecureFile.setMinInflateRatio(0.001);
        FileOutputStream out = null;
        try {
            String path = this.getClass().getResource("/").getPath();
            String filePath = path + "excelExportTest.xlsx";
            out = new FileOutputStream("D:/students.xls");
            ExcelUtils.exportExcel(out, new ExcelExportRecordLoadCallback() {

                        private int pageSize = 1000;

                        public String[] getCellTitles() {
                            return new String[] { "入账时间", "交易单号", "商户单号", "账务类型", "收支类型", "收支金额(元)", "账户结余(元)", "资金变更提交申请人", "最后更新时间", "备注", "业务凭证号"};
                        }

                        public List<String[]> loadRecords() {
                            List<String[]> records = new ArrayList<String[]>();
                            /*for (int i = 0; i < total; i++) {
                                records.add(
                                        new String[] { "A" + currentPage + i, "B" + currentPage + i, "C" + currentPage + i});
                            }*/

                            return records;
                        }

                        public boolean isFinished() {
                            return true;
                        }

                        public int getRowAccessWindowSize() {
                            return pageSize;
                        }
                    });

        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } finally {

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*@Test
    public void importExcel() {
        String filePath = this.getClass().getResource("/").getPath() + "excelImportTest.xlsx";
        try {
            FileInputStream in = new FileInputStream(filePath);
            List<ExcelImportRowResult> excelImportRowResults = ExcelUtils.importExcel(
                    in, new ExcelImportRowCallback() {
                        @Override
                        public ExcelImportRowResult checkAndSave(int row, List<Object> cellValues) {
                            ExcelImportRowResult result = new ExcelImportRowResult();
                            result.setRowNum(row);
                            result.setCellValues(cellValues);
                            result.setErr(false);
                            result.setBlockedErr(false);

                            if (row == 6) {
                                result.setErr(true);
                                result.setMessage("Err test");
                            }

                            return result;
                        }
                    });
            Assert.assertNotNull(excelImportRowResults);
            Assert.assertEquals(1, excelImportRowResults.size());
            ExcelImportRowResult result = excelImportRowResults.get(0);
            Assert.assertEquals(6, result.getRowNum());
            Assert.assertEquals("Err test", result.getMessage());
        } catch (FileNotFoundException e) {
            Assert.fail(e.getMessage());
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        } catch (ExcelImportVerifyException e) {
            Assert.fail(e.getMessage());
        }
    }*/
}
