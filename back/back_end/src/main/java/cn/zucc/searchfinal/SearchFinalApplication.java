package cn.zucc.searchfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tk.mybatis.spring.annotation.MapperScan;
@SpringBootApplication
//@ComponentScan(value ={"cn.zucc.searchfinal.config","cn.zucc.searchfinal.controller", "cn.zucc.searchfinal.service.impl"}, includeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SearchFinalApplication.class) })
//@ComponentScan(value="cn.zucc.searchfinal", includeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SearchFinalApplication.class) })
//@MapperScan({"cn.zucc.searchfinal.mapper","cn.zucc.searchfinal.service"})
@MapperScan(
        basePackages = {"cn.zucc.searchfinal.mapper"}
)

public class SearchFinalApplication {
    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchFinalApplication.class, args);
    }
}
