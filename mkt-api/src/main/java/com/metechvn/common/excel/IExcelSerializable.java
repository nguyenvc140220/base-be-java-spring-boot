package com.metechvn.common.excel;

import java.util.List;

public interface IExcelSerializable {
    List<String> getValues();

    List<String> getHeader();

}
