
package com.example.demo1.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.example.demo1.entity.DemoData;
import com.example.demo1.entity.TArea;
import com.example.demo1.mapper.AreaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BookInfoExcelListener extends AnalysisEventListener<DemoData> {
    private static final Logger logee = LoggerFactory.getLogger(BookInfoExcelListener.class);
    List<DemoData> list = new ArrayList<>();

    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        Object o = JSON.toJSON(list);
        logee.info("=====" + o);
        try {
            list.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Autowired
    private AreaMapper areaMapper;

    private List<TArea> getment(Long pid) {
        List<TArea> area = areaMapper.getAreas(pid);
        for (TArea tArea : area) {
            tArea.setChilend(getment(Long.valueOf(tArea.getId())));
        }
        return area;
    }

    public List<TArea> getArea(String pid) {
        List<TArea> getment = this.getment(Long.valueOf(pid));
      TArea area = areaMapper.getArea(Long.valueOf(pid));
        if (area!=null) {
            area.setChilend(getment);
            List<TArea> a=new ArrayList<>();
            a.add(area);
            return a;
        }else {
            return getment;
        }

    }



}
