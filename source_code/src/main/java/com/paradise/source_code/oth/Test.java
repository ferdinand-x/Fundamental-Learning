package com.paradise.source_code.oth;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        String str1 = "ABS-MBR-001,\n" +
                "COM-ST-ER-001,\n" +
                "COM-ST-ER-002,\n" +
                "COM-ST-ER-003,\n" +
                "COM-ST-ER-005,\n" +
                "COM-ST-ER-006,\n" +
                "CON-ER-004,\n" +
                "CON-ER-011,\n" +
                "CON-SEP-006,\n" +
                "CON-SVC-TVC-003,\n" +
                "CON-TVC-001,\n" +
                "CVC-MBR-001,\n" +
                "CVC-MBR-003,\n" +
                "DCR-MPFA-ER-001,\n" +
                "DM-ALL-002,\n" +
                "DM-ER-003,\n" +
                "DM-ER-004,\n" +
                "DM-MBR-003,\n" +
                "DM-MBR-004,\n" +
                "DM-MBR-005,\n" +
                "FC-ALL-002,\n" +
                "FC-ALL-003,\n" +
                "FC-ALL-004,\n" +
                "FOR-BAL-ER-007,\n" +
                "FOR-BAL-ER-008,\n" +
                "IDR-ER-001,\n" +
                "IDR-MPFA-ER-SEP-003,\n" +
                "INC-ENR-ER-002,\n" +
                "INV-MBR-005,\n" +
                "INV-MBR-009,\n" +
                "INV-MBR-011,\n" +
                "INV-MBR-015,\n" +
                "LSP-MBR-001,\n" +
                "LSP-ER-001,\n" +
                "LSP-ER-002,\n" +
                "LSP-ER-003,\n" +
                "LSP-ER-004,\n" +
                "LSP-MBR-002,\n" +
                "LSP-MBR-003,\n" +
                "LSP-MBR-004,\n" +
                "PM-BP-ALL-002,\n" +
                "PM-BP-ALL-003,\n" +
                "PM-BP-ALL-004,\n" +
                "PM-CL-ALL-001,\n" +
                "PM-CL-ALL-002,\n" +
                "PM-CL-ALL-003,\n" +
                "PM-CL-ALL-005,\n" +
                "PM-DDA-ALL-002,\n" +
                "PM-DDA-ALL-004,\n" +
                "PM-DDA-ALL-006,\n" +
                "PM-DDA-ALL-009,\n" +
                "REG-DE-ER-001,\n" +
                "REG-DE-ER-002,\n" +
                "TRF-TR-001,\n" +
                "TRF-ER-001,\n" +
                "TRF-ER-006,\n" +
                "TRF-ER-007,\n" +
                "TRF-MBR-001,\n" +
                "TRF-MBR-010,\n" +
                "TRF-MBR-009,\n" +
                "MMB-ER-001,\n" +
                "MMB-TR-001,\n" +
                "MMB-TR-002,\n" +
                "MMB-TR-003,\n" +
                "MMB-TR-004,\n" +
                "MMB-TR-005,\n" +
                "TRF-MBR-004,\n" +
                "TRF-MBR-005,\n" +
                "TRF-MBR-006,\n" +
                "TRF-MBR-011,\n" +
                "TRF-MBR-012,\n" +
                "TRF-PA-001,\n" +
                "TRF-SEP-001,\n" +
                "UB-SPECIAL-PA-001,\n" +
                "UBE-MBR-001,\n" +
                "UBE-MBR-002,\n" +
                "UBE-MBR-003,\n" +
                "UBE-MBR-004,\n" +
                "UBE-MBR-005,\n" +
                "UBE-MBR-006,\n" +
                "UBE-MBR-007,\n" +
                "UBE-MBR-008,\n" +
                "UBE-MBR-009,\n" +
                "WDR-BK-MBR-001,\n" +
                "UNCLEAN-001,\n" +
                "WDR-ER-001,\n" +
                "WDR-ER-002,\n" +
                "WDR-MBR-002,\n" +
                "WDR-MBR-003,\n" +
                "WDR-MBR-007,\n" +
                "WDR-MBR-005,\n" +
                "WDR-MBR-006,\n" +
                "WDR-MBR-008,\n" +
                "WDR-MBR-010,\n" +
                "WDR-PW-ER-001,\n" +
                "WDR-PW-ER-002,\n" +
                "WDR-MBR-009,\n" +
                "WDR-PW-MBR-003,\n" +
                "WDR-PW-MBR-005,\n" +
                "WDR-PW-MBR-006,\n" +
                "WDR-PW-MBR-007,\n" +
                "WDR-PW-MBR-008,\n" +
                "WDR-PW-MBR-009,\n" +
                "WDR-PW-TR-001,\n" +
                "IDR-SEP-001,\n" +
                "IDR-ER-SEP-002,\n" +
                "EPASS-MBR-001,\n" +
                "WDR-BK-OT-001,\n" +
                "WDR-BK-OT-002,\n" +
                "WDR-BK-OT-003,\n" +
                "WDR-BK-OT-004,\n" +
                "WDR-BK-OT-005,\n" +
                "WDR-BK-OT-006,\n" +
                "IDR-MPFA-ER-SEP-002,\n" +
                "IDR-MPFA-ER-SEP-001,\n" +
                "ENR-TR-001,\n" +
                "DM-ER-002,\n" +
                "DM-MBR-002,\n" +
                "ENR-ER-001,\n" +
                "ENR-ER-003,\n" +
                "FC-ALL-001,\n" +
                "FC-ALL-005";

        String str2 = "ABS-MBR-001,\n" +
                "COM-ST-ER-001,\n" +
                "COM-ST-ER-005,\n" +
                "COM-ST-ER-006,\n" +
                "INC-ENR-ER-002,\n" +
                "LSP-MBR-001,\n" +
                "LSP-ER-001,\n" +
                "LSP-ER-003,\n" +
                "LSP-ER-004,\n" +
                "PM-CL-ALL-003,\n" +
                "PM-DDA-ALL-004,\n" +
                "PM-DDA-ALL-006,\n" +
                "TRF-TR-001,\n" +
                "TRF-ER-007,\n" +
                "TRF-MBR-001,\n" +
                "TRF-MBR-010,\n" +
                "MMB-ER-001,\n" +
                "MMB-TR-001,\n" +
                "MMB-TR-004,\n" +
                "MMB-TR-005,\n" +
                "TRF-MBR-004";
        List<String> nodata_list= Arrays.stream(str1.split(",\n")).collect(Collectors.toList());
        List<String> err_list= Arrays.stream(str2.split(",\n")).collect(Collectors.toList());
        List<String> nodataList = nodata_list.stream().filter(v->!err_list.contains(v)).collect(Collectors.toList());
        String collect = nodataList.stream().collect(Collectors.joining("\n"));
        System.out.println(nodataList);
    }
}
